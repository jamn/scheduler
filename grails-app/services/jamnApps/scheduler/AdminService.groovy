package jamnApps.scheduler

import java.text.SimpleDateFormat

class AdminService {

	def dateService
	def schedulerService
	def emailService
	def userService

	SimpleDateFormat dateFormatter = new SimpleDateFormat("EE dd MMM yyyy @ hh:mm a")
	SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM/dd/yyyyhh:mma")
	SimpleDateFormat dateFormatter3 = new SimpleDateFormat("MM/dd/yyyy")

	

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

	private Date getStartDate(params = [:]){
		def startDate
		if (params.startDate){
			startDate = dateFormatter3.parse(params.startDate)
		}else{
			startDate = new Date()
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

    private Map getUpcomingAppointments(Date startDate, User serviceProvider){
		Calendar today = new GregorianCalendar()
		today.setTime(startDate)
		today.set(Calendar.HOUR_OF_DAY, 0)
		today.set(Calendar.MINUTE, 0)
		today.set(Calendar.SECOND, 0)
		today.set(Calendar.MILLISECOND, 0)
		def appointments = Appointment.executeQuery("from Appointment a where a.appointmentDate >= :today and a.booked = true and a.deleted = false and a.serviceProvider = :serviceProvider", [today:today.getTime(), serviceProvider:serviceProvider])?.sort{it.appointmentDate}
		return [appointments:appointments]
    }

    private Map getServiceProviderInfo(User serviceProvider){
    	def dayOfTheWeek = DayOfTheWeek.findByServiceProvider(serviceProvider)
    	def serviceProviderStartTime = dateService.get24HourTimeValues(dayOfTheWeek.startTime)
		def serviceProviderEndTime = dateService.get24HourTimeValues(dayOfTheWeek.endTime)

		Calendar startTime = new GregorianCalendar()
		startTime.set(Calendar.HOUR_OF_DAY, serviceProviderStartTime.hour.intValue())
		startTime.set(Calendar.MINUTE, serviceProviderStartTime.minute.intValue())
		startTime.set(Calendar.SECOND, 0)
		startTime.set(Calendar.MILLISECOND, 0)

		Calendar endTime = new GregorianCalendar()
		endTime.set(Calendar.HOUR_OF_DAY, serviceProviderEndTime.hour.intValue())
		endTime.set(Calendar.MINUTE, serviceProviderEndTime.minute.intValue())
		endTime.set(Calendar.SECOND, 0)
		endTime.set(Calendar.MILLISECOND, 0)

		return [startTime:startTime, endTime:endTime]
    }

    private Map getServiceProviderAvailability(User serviceProvider){
    	def availability = [:]
    	def daysOfTheWeek = DayOfTheWeek.findAllByServiceProvider(serviceProvider)
    	daysOfTheWeek?.each(){
    		def startTime = dateService.getTimeOfDayString(it.startTime)
    		def endTime = dateService.getTimeOfDayString(it.endTime)
    		availability.put(it.name, [startTime:startTime, endTime:endTime, available:it.available])
    	}
    	return [availability:availability]
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