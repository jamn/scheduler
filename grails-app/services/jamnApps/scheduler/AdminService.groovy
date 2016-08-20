package jamnApps.scheduler

import java.text.SimpleDateFormat
import org.joda.time.format.*
import org.joda.time.*

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

	private DateTime getStartDate(params = [:]){
		DateTime startDate
		if (params.startDate){
			startDate = dateFormatter4.parseDateTime(params.startDate)
		}else{
			startDate = new DateTime()
		}
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

}