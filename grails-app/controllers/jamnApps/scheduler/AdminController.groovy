package jamnApps.scheduler

import org.apache.commons.lang.RandomStringUtils
import java.text.SimpleDateFormat
import grails.converters.JSON


class AdminController {

	SimpleDateFormat dateFormatter = new SimpleDateFormat("EE dd MMM yyyy @ hh:mm a")
	SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM/dd/yyyyhh:mma")
	SimpleDateFormat dateFormatter3 = new SimpleDateFormat("MM/dd/yyyy")

	def dateService
	def schedulerService
	def emailService
	def userService

	/*********************************
				NAVIGATION
	**********************************/

	def sendReminders() {
		def job = new SendAppointmentRemindersJob()
		job.triggerNow()
	}

    def index() { 
    	println "\n" + new Date()
		println "params: " + params
		def model = getUpcomingAppointments() + getServiceProviderInfo()
    	return model
    }

    def getSection() {
    	println "\n" + new Date()
		println "params: " + params

    	List allowedSections = [
    		"homepageMessage", 
    		"clients",
    		"fourteenDayView",
    		"upcomingAppointments",
    		"bookAppointment",
    		"blockOffTime",
    		"log"
    	]

    	def template = ""
    	def model = [:]

    	// ASIGN TEMPLATE

    	def section = params?.section.replace("#", "")
    	if (section in allowedSections){
    		template = section
    	}

    	// RETRIEVE DATA FOR MODEL

    	if (template == "homepageMessage"){
    		model = getHomepageText()
    	}
    	else if (template == "clients"){
			model = getClients()
		}
		else if (template == "fourteenDayView"){
			model = getUpcomingAppointments() + getServiceProviderInfo() + [startRange:0]
		}
		else if (template == "upcomingAppointments"){
			model = getUpcomingAppointments() + getServiceProviderInfo()
		}
		else if (template == "bookAppointment"){
			model = getClients() + getServices()
		}
		else if (template == "blockOffTime"){
			model = getBlockedOffTimes()
		}
		else if (template == "log"){
			model = getLog()
		}

    	try {
    		render(template: template, model: model)
    	}
    	catch(Exception e) {
    		println "ERROR: " + e
    		render(template: "error")
    	}
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

    private Map getUpcomingAppointments(){
		Calendar today = new GregorianCalendar()
		today.set(Calendar.HOUR_OF_DAY, 0)
		today.set(Calendar.MINUTE, 0)
		today.set(Calendar.SECOND, 0)
		today.set(Calendar.MILLISECOND, 0)
		def appointments = Appointment.executeQuery("from Appointment a where a.appointmentDate >= :today and a.booked = true and a.deleted = false", [today:today.getTime()])?.sort{it.appointmentDate}
		return [appointments:appointments]
    }

    private Map getServiceProviderInfo(){
    	def serviceProvider = User.findByCode("dsp907201")
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
		return Appointment.findAllWhere(client:client, booked:true, deleted:false)?.sort{it.appointmentDate}?.reverse()
	}



	/*********************************
				ENDPOINTS
	**********************************/

	def saveHomepageMessage(){
		println "\n" + new Date()
		println "params: " + params
		def homepageText = ApplicationProperty.findByName("HOMEPAGE_MESSAGE")
		homepageText.value = params.m.replace("\r", "<br />").replace("\n", "<br />")
		homepageText.save(flush:true)
		if (homepageText.hasErrors()){
			println "ERROR: new message not saved."
			println homepageText.errors
		}
		else{
			println "SAVED!"
		}
		return true
	}

	def getClientsSelectMenu(){
    	def clientData = getClients(params.lastNameStartsWith)
    	render(template:"clientsSelectMenu", model:clientData)
    }

	def getClientDetails(){
		println "\n" + new Date()
		println "params: " + params
		if (params?.cId){
			def client = User.get(params.cId)
			session.editClient = client
			def appointments = getAppointmentsForClient(client)
			if (client){
				render (template: "client", model: [client:client, appointments:appointments])
			}
		}
		return false
	}

	def getClientDataForm(){
		println "\n" + new Date()
		println "params: " + params
    	def client
    	def submitText = "Register Client"
    	if (params?.cId){
    		client = User.get(params.cId)
    		if (client){
    			submitText = "Save"
    		}
    	}
    	render(template: "clientInfoForm", model: [client:client, submitText:submitText])
    }

	def saveClientNotes(){
		println "\n" + new Date()
		println "params: " + params
		if (params?.n){
			def coder = new org.apache.commons.codec.net.URLCodec() 
			session.editClient.notes = coder.decode(params.n)
			session.editClient.save(flush:true)
			return [success:true] as JSON
		}
		else{
			return [success:true] as JSON
		}
	}

	def blockOffTime(){
		println "\n" + new Date()
		println "params: " + params
		Boolean success = false
		Boolean appointmentFailedToSave = false
		try {
			def from = dateFormatter2.parse(params?.date+params?.from)
			def to = dateFormatter2.parse(params?.date+params?.to)

			def serviceProvider = User.findByCode("dsp907201")
			def service = ServiceType.findByDescription("Blocked Off Time")
			Calendar currentDate = new GregorianCalendar()
			currentDate.setTime(from)

			while (currentDate.getTime() < to){
				def appointment = new Appointment()
				appointment.appointmentDate = currentDate.getTime()
				appointment.serviceProvider = serviceProvider
				appointment.service = service
				appointment.code = RandomStringUtils.random(14, true, true)
				appointment.client = serviceProvider
				appointment.booked = true
				appointment.sendEmailReminder = false
				appointment.sendTextReminder = false
				appointment.save(flush:true)
				if (appointment.hasErrors()){
					appointmentFailedToSave = true
					println "ERROR!"
					println appointment.errors
				}else{
					success = true
				}
				println "appointment: " + appointment
				currentDate.add(Calendar.MINUTE, 15)
			}
		}
		catch(Exception e) {
			println "ERROR: " + e
		}
		if (success && !appointmentFailedToSave){
			render ('{"success":true}') as JSON
		}else{
			render ('{"success":false}') as JSON
		}
	}

	def blockOffWholeDay(){
		println "\n" + new Date()
		println "params: " + params
		Boolean success = false
		Boolean dayOffFailedToSave = false
		try {
			def from = dateFormatter3.parse(params?.from)
			def to = dateFormatter3.parse(params?.to)

			def serviceProvider = User.findByCode("dsp907201")
			Calendar currentDate = new GregorianCalendar()
			currentDate.setTime(from)
			currentDate.set(Calendar.HOUR_OF_DAY, 0)
			currentDate.set(Calendar.MINUTE, 0)
			currentDate.set(Calendar.SECOND, 0)
			currentDate.set(Calendar.MILLISECOND, 0)
			Calendar endDate = new GregorianCalendar()
			endDate.setTime(to)
			endDate.set(Calendar.HOUR_OF_DAY, 0)
			endDate.set(Calendar.MINUTE, 0)
			endDate.set(Calendar.SECOND, 0)
			endDate.set(Calendar.MILLISECOND, 0)

			while (currentDate.getTime() <= endDate.getTime()){
				def dayOff = new DayOff()
				dayOff.dayOffDate = currentDate.getTime()
				dayOff.serviceProvider = serviceProvider
				dayOff.save(flush:true)
				if (dayOff.hasErrors()){
					dayOffFailedToSave = true
					println "ERROR!"
					println dayOff.errors
				}else{
					success = true
				}
				println "dayOff: " + dayOff
				currentDate.add(Calendar.DAY_OF_YEAR, 1)
			}
		}
		catch(Exception e) {
			println "ERROR: " + e
		}

		if (success && !dayOffFailedToSave){
			render ('{"success":true}') as JSON
		}else{
			render ('{"success":false}') as JSON
		}
	}

	def clearBlockedTime(){
		println "\n" + new Date()
		println "params: " + params
		Boolean success = false
		def deletedTimeslots = []
		def timeSlotsToDelete = params?.blockedOffTime ?: []
		if (timeSlotsToDelete instanceof String) {
			timeSlotsToDelete = [timeSlotsToDelete]
		}
		timeSlotsToDelete?.each(){
			def appointment = Appointment.get(it.toLong())
			appointment.deleted = true
			appointment.save(flush:true)
			if (appointment.hasErrors()){
				success = false
				println "        ERROR: " + appointment.errors
			}
			else {
				println "    - deleted blocked timeslot: " + appointment.appointmentDate.format('E MMM dd, yyyy @ hh:mm a')
				deletedTimeslots.add(appointment.id)
				success = true
			}
		}
		render ('{"success":'+success+', "deletedTimeslots":'+deletedTimeslots+'}') as JSON
	}

	def bookForClient(){
		println "\n" + new Date()
		println "params: " + params
		Boolean success = false
		if (params?.cId && params?.sId && params?.aDate && params?.sTime){
			success = schedulerService.bookForClient(params)
		}
		if (success){
			render ('{"success":true}') as JSON
		}
		else{
			render ('{"success":false}') as JSON
		}
	}

	def rescheduleAppointment(){
		println "\n" + new Date()
		println "params: " + params
		Boolean success = false
		if (params?.aId && params?.sId && params?.aDate && params?.sTime){
			try {
				def existingApointment = Appointment.get(new Long(params.aId))
				params["cId"] = existingApointment.client.id
				params["rescheduledAppointment"] = "TRUE"
				success = schedulerService.bookForClient(params)
				if (success){
					existingApointment.deleted = true
					existingApointment.save(flush:true)
					if (existingApointment.hasErrors()){
						success = false
						println "ERROR: " + existingApointment.errors
					}
				}
			}
			catch(Exception e) {
				println "ERROR: " + e
			}
		}
		if (success){
			println "SUCCESS!"
			render ('{"success":'+success+'}') as JSON
		}
		else{
			println "ERROR!"
			render ('{"success":'+success+'}') as JSON
		}
	}

	def getTimeSlotOptions(){
		println "\n" + new Date()
		println "params: " + params
		def timeSlots = []
		if (params?.aDate && params?.sId){
			def requestedDate = dateFormatter3.parse(params.aDate)
			def service = ServiceType.get(new Long(params.sId))
			def serviceProvider = User.findByCode("dsp907201")
			schedulerService.getTimeSlotsAvailableMap(requestedDate, serviceProvider, service)?.each(){ k,v ->
				timeSlots += v
			}
		}
		render (template: "timeSlotOptions", model: [timeSlots:timeSlots])
	}

	def getRescheduleOptions(){
		println "\n" + new Date()
		println "params: " + params
		def appointment
		if (params?.aId){
			appointment = Appointment.get(new Long(params.aId))
		}
		if (appointment){
			def services = ServiceType.list().sort{it.displayOrder}.findAll{it.description != "Blocked Off Time"}
			render (template: "rescheduleOptions", model: [appointment:appointment, services:services])
		}else{
			render "No appointment found"
		}
	}

	def saveClient(){
		println "\n" + new Date()
		println "params: " + params
		Boolean success = false

		if (params?.cId) {
			def existingClient = User.get(params.cId)
			if (existingClient) {
				success = userService.updateClient(existingClient, params)
			}
		}
		else {
			def newClient = userService.createNewClient(params)
			if (newClient.hasErrors()){
				println "ERROR: " + newClient.errors
			}
			else{
				success = true
			}
		}
		render ('{"success":'+success+'}') as JSON
	}

	def cancelAppointment(){
		println "\n" + new Date()
		println "params: " + params
		Boolean success = false
		if (params.c){ // params.c = appointment.code
			def appointment = Appointment.findByCode(params.c.trim())
			println "appointment(${appointment.id}): " + appointment.client?.getFullName() + " | " + appointment.service?.description + " on " + appointment.appointmentDate.format('MM/dd/yy @ hh:mm a [E]')
			if (appointment){
				appointment.deleted = true
				appointment.save(flush:true)
				if (!appointment.hasErrors()){
					emailService.sendCancellationNotices(appointment)
					success = true
				}
			}
		}
		render ('{"success":'+success+'}') as JSON
	}

	def emailClient(){
		println "\n" + new Date()
		println "params: " + params
		Boolean success = false
		if (params?.e?.size() > 0 && params.m?.size() > 0){
			success = emailService.sendEmail(params.e, params.m)
		}
		render ('{"success":'+success+'}') as JSON
	}

	def getClientHistory(){
		println "\n" + new Date()
		println "params: " + params
		def appointments = []
		if (params?.cId){
			def client = User.get(params.cId)
			appointments = getAppointmentsForClient(client)
		}
		render (template: "clientHistory", model: [appointments:appointments])
	}

}
