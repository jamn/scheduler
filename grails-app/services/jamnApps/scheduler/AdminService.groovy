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
	DateTimeFormatter dateFormatter5 = DateTimeFormat.forPattern("yyyyMMdd'T'HHmmssZ")

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
		def count = 0
		convertedFile.splitEachLine(","){ data ->
			if (count > 0){
				new User(
					username: data[3]?.trim(),
					password: "!Atlantis3",
					firstName: data[1]?.trim()?.capitalize(),
					lastName: data[2]?.trim()?.capitalize(),
					email: data[3]?.trim(),
					phone: data[7]?.trim(),
					address1: data[5]?.trim(),
					address2: data[6]?.trim(),
					city: data[10]?.trim()?.capitalize(),
					state: data[11]?.trim()?.capitalize(),
					zip: data[12] ? data[12]?.trim()?.toLong() : null,
					isClient: true,
					code: RandomStringUtils.random(8, true, true),
					dateCreated: data[0] ? dateFormatter3.parse(data[0]?.trim()) : new Date(),
					birthday: data[4] ? dateFormatter3.parse(data[4]?.trim()) : null,
					notes: data[13]?.trim()
				).save()
			}
			count++
		}

	}

	private importAppointments(StandardMultipartFile appointmentsFile, User serviceProvider){
		def convertedFile = multipartToFile(appointmentsFile)
		def count = 0
		def key
		def value
		def newEvent = false
		def appointmentDate
		def dateCreated
		def clientName
		def client
		def serviceName
		def service
		def summarySeperatorIndex
		convertedFile.splitEachLine(':'){ data ->
			key = data[0]
			value = data[1]
			if (key == "DTSTART"){
				appointmentDate = dateFormatter5.parseDateTime(value)
				//appointmentDate = appointmentDate.withZone(DateTimeZone.forID("America/Chicago"));
			}
			if (key == "DTSTAMP"){
				dateCreated = dateFormatter5.parseDateTime(value)
			}
			if (key == "SUMMARY"){
				if (value == "Personal Time Off"){
					serviceName = "Blocked Off Time"
				}else{
					summarySeperatorIndex = value.indexOf('-')
					clientName = value.substring(0,summarySeperatorIndex - 1)?.split()
					serviceName = value.substring(summarySeperatorIndex + 2)
				}
				if (clientName){
						client = User.findWhere(firstName:clientName[0], lastName:clientName[1])
					}
				if (serviceName){
					service = ServiceType.findWhere(description:serviceName)
				}
			}
			if (key == "END" && value == "VEVENT"){
				if (appointmentDate && client && service && serviceProvider && dateCreated){
					new Appointment(
						appointmentDate: appointmentDate,
						serviceProvider: serviceProvider,
						client: client,
						service: service,
						code: RandomStringUtils.random(14, true, true),
						booked: true,
						sendEmailReminder: false,
						sendTextReminder: false,
						dateCreated: dateCreated
					).save()
					appointmentDate = null
					dateCreated = null
					client = null
					service = null
				}
			}
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