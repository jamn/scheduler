package jamnApps.scheduler

import java.text.SimpleDateFormat
import org.joda.time.format.*
import org.joda.time.*
import org.apache.commons.lang.RandomStringUtils
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest.StandardMultipartFile

class AdminService {

	def dateService
	def schedulerService
	def emailService
	def userService

	SimpleDateFormat dateFormatter = new SimpleDateFormat("EE dd MMM yyyy @ hh:mm a")
	SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM/dd/yyyyhh:mma")
	SimpleDateFormat dateFormatter3 = new SimpleDateFormat("MM/dd/yyyy")
	DateTimeFormatter dateFormatter4 = DateTimeFormat.forPattern("MM/dd/yyyy")

	private Boolean updateAvailabilityForServiceProvider(serviceProvider, params = [:]){
		def success = false
		def updatedTimesMap = [:]
		for (int i = 1; i <= 7; i++) {
			def startTime = params["startTime-"+i]
			def endTime = params["endTime-"+i]
			def startTimeInMillis = dateService.getMillisForTimeString(startTime)
			def endTimeInMillis = dateService.getMillisForTimeString(endTime)
			updatedTimesMap.put(i,[startTime:startTimeInMillis, endTime:endTimeInMillis])
		}
		def timesMap
		def dayAvailable
		DayOfTheWeek.findAllByServiceProvider(serviceProvider).each(){ day ->
			timesMap = updatedTimesMap.get(day?.dayIndex?.intValue())
			dayAvailable = params["available-"+day?.dayIndex]
			if (dayAvailable){
				day.available = true
				day.startTime = new Long(timesMap.startTime)
				day.endTime = new Long(timesMap.endTime)
				
			}
			else {
				day.available = false
			}
			try {
				day.save(flush:true)
				success = true
			}
			catch(Exception e) {
				println "ERROR: " + e
				success = false
			}
		}
		return success
	}

    private Map getHomepageText(){
    	def homepageText = ApplicationProperty.findByName("HOMEPAGE_MESSAGE")?.value ?: "ERROR: HOMEPAGE_MESSAGE record not found in the database. Tell Ben. He's good at fixing that stuff."
	    homepageText = homepageText.replace("<br />","\r")
	    return [homepageText:homepageText]
    }

    private Map getClients(lastNameStartsWith = null){
		def clients = []
		User.list()?.each(){ user ->
			if (user.isClient && (!lastNameStartsWith || user.lastName.substring(0,1).toUpperCase() == lastNameStartsWith.toUpperCase()) ){
				clients.add(user)
			}
		}
		clients.sort{it.lastName}
		def filterLetters = clients.collect{it.lastName.substring(0,1).toUpperCase()}?.unique()
		return [clients:clients, filterLetters:filterLetters]
    }

    private Map getServices(){
    	def services = ServiceType.list()?.sort{it?.displayOrder}.findAll{it?.description != "Blocked Off Time"} ?: []
    	return [services:services]
    }

	private DateTime getStartDate(params = [:], serviceProviderAvailability){
		DateTime startDate
		def tempStartDate

		// GET EARLIEST START TIME
		def earliestStartMillis
		serviceProviderAvailability.each(){
			if (!earliestStartMillis || earliestStartMillis > it.startTime){
				earliestStartMillis = it.startTime
			}
    	}

    	// FIND THE START DATE
		if (params.startDate){
			tempStartDate = dateFormatter4.parseDateTime(params.startDate)
		}else{
			tempStartDate = new DateTime()
		}

		// SET IT TO THE START TIME
		startDate = tempStartDate?.withMillisOfDay(earliestStartMillis.intValue())
		return startDate
	}

	private getCalendarStartRange(Date tempStartDate){
		def today = Calendar.getInstance()
		def startDate = Calendar.getInstance()
		startDate.setTime(tempStartDate)
		def startRange = dateService.getDaysBetween(startDate, today)
		return startRange
	}

    private Map getUpcomingAppointments(DateTime startDate, User serviceProvider){
		def appointments = Appointment.executeQuery("from Appointment a where a.appointmentDate >= :today and a.booked = true and a.deleted = false and a.serviceProvider = :serviceProvider", [today:startDate.toDate(), serviceProvider:serviceProvider])?.sort{it.appointmentDate}
		return [appointments:appointments]
    }

    private List getServiceProviderAvailability(User serviceProvider){
    	def availability = []
    	try {
    		availability = DayOfTheWeek.findAllByServiceProvider(serviceProvider)
    	}
    	catch(Exception e) {
			println "ERROR: " + e    		
    	}
    	return availability
    }

    private Map getBlockedOffTimes(){
    	Calendar today = new GregorianCalendar()
		today.set(Calendar.HOUR_OF_DAY, 0)
		today.set(Calendar.MINUTE, 0)
		today.set(Calendar.SECOND, 0)
		today.set(Calendar.MILLISECOND, 0)
		def service = ServiceType.findByDescription("Blocked Off Time")
		def blockedOffTimes = Appointment.executeQuery("from Appointment a where a.appointmentDate >= :today and a.service = :service and a.deleted = false", [today:today.getTime(), service:service])?.sort{it.appointmentDate}
    	return [blockedOffTimes:blockedOffTimes]
    }

    private Map getLog(){
		String log = ''
		try {
			log = new File('/var/log/tomcat7/catalina.out').text.replace('\n', '<br />').replace('\r', '<br />')
		}
		catch(Exception e) {
			println "ERROR: " + e
			log = 'No log to show.'
		}
		return [log:log]
	}

	private List getAppointmentsForClient(User client){
		return Appointment.findAllWhere(client:client, booked:true)?.sort{it.appointmentDate}?.reverse()
	}

	private importClients(StandardMultipartFile clientsFile){
		def convertedFile = multipartToFile(clientsFile)
		def newClient
		def count = 0
		convertedFile.splitEachLine(","){ data ->
			if (count > 0){
				newClient = new User()
				newClient.username = data[3]?.trim()
				newClient.password = "!Atlantis3"
				newClient.firstName = data[1]?.trim()?.capitalize()
				newClient.lastName = data[2]?.trim()?.capitalize()
				newClient.email = data[3]?.trim()
				newClient.phone = data[7]?.trim()
				newClient.address1 = data[5]?.trim()
				newClient.address2 = data[6]?.trim()
				newClient.city = data[10]?.trim()?.capitalize()
				newClient.state = data[11]?.trim()?.capitalize()
				newClient.zip = data[12] ? data[12]?.trim()?.toLong() : null
				newClient.isClient = true
				newClient.code = RandomStringUtils.random(8, true, true)
				newClient.dateCreated = data[0] ? dateFormatter3.parse(data[0]?.trim()) : new Date()
				newClient.birthday = data[4] ? dateFormatter3.parse(data[4]?.trim()) : null
				newClient.notes = data[13]?.trim()
				newClient.save()
			}
			count++
		}

	}

	private File multipartToFile(StandardMultipartFile file) throws IllegalStateException, IOException {
		File convFile = new File(file.getOriginalFilename())
		convFile.createNewFile() 
		FileOutputStream fos = new FileOutputStream(convFile) 
		fos.write(file.getBytes())
		fos.close() 
		return convFile
	}

}