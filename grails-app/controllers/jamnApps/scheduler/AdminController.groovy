package jamnApps.scheduler

import org.apache.commons.lang.RandomStringUtils
import java.text.SimpleDateFormat
import grails.converters.JSON
import org.joda.time.*


class AdminController {

	static layout = 'admin'
	static defaultAction = 'calendar'

	SimpleDateFormat dateFormatter = new SimpleDateFormat("EE dd MMM yyyy @ hh:mm a")
	SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MM/dd/yyyyhh:mma")
	SimpleDateFormat dateFormatter3 = new SimpleDateFormat("MM/dd/yyyy")
	SimpleDateFormat dateFormatter4 = new SimpleDateFormat("H:m")

	def adminService
	def schedulerService
	def emailService
	def userService
	def utilService
	def dateService
	def amazonWebService
	def notificationService
	

	/*********************************
				NAVIGATION
	**********************************/


    def calendar(){
    	List serviceProviderAvailability = adminService.getServiceProviderAvailability(session.user)
    	def startDate = adminService.getStartDate(params, serviceProviderAvailability)
    	def services = adminService.getServices(session.user)?.services
    	Map upcomingAppointments = adminService.getUpcomingAppointments(startDate, session.user)
    	def numberOfRows = schedulerService.getNumberOfRowsForCalendar(serviceProviderAvailability)
    	def days = schedulerService.getDaysForCalendar(startDate)
    	return upcomingAppointments + [startDate:startDate, services:services, serviceProviderAvailability:serviceProviderAvailability, numberOfRows:numberOfRows, days:days]
    }

    def upcomingAppointments(){
    	List serviceProviderAvailability = adminService.getServiceProviderAvailability(session.user)
    	def startDate = adminService.getStartDate(params, serviceProviderAvailability)
    	return adminService.getUpcomingAppointments(startDate, session.user)
    }

    def services(){
    	return adminService.getServices(session.user)
    }

    def homepageConfig(){
    	return adminService.getHomepageText()
    }

    def bookAppointment(){
    	return adminService.getClients() + adminService.getServices(session.user)
    }

    def blockOffTime(){
    	return adminService.getBlockedOffTimes()
    }

    def clients(){
    	return adminService.getClients()
    }

    def availability(){
    	return  [timeSlots:dateService.getTimeSlots(),
    				availability:adminService.getServiceProviderAvailability(session.user)]
    }

    // def log(){
    // 	return adminService.getLog()
    // }



	/*******************************
			AJAX ENDPOINTS
	********************************/

	def saveHomepageMessage(){
		def success = false
		def homepageText = ApplicationProperty.findByName("HOMEPAGE_MESSAGE")
		homepageText.value = params.m.replace("\r", "<br />").replace("\n", "<br />")
		homepageText.save(flush:true)
		if (homepageText.hasErrors()){
			flash.error = "New homepage message not saved."
			println homepageText.errors
		}
		else{
			flash.success = "The homepage message has been updated."
			success = true
			utilService.communicationBoardMessage = homepageText.value
		}
		render ('{"success":'+success+'}') as JSON
	}

	def saveLogo(){
	}

	def saveHomepageImage(){
		def file = request.getFile('homepageImage')
		def results = amazonWebService.saveFile(file)
		if (results.success){
			def homepageImage = ApplicationProperty.findByName("HOMEPAGE_IMAGE_URL")
			homepageImage.value = results.url
			homepageImage.save()
			utilService.resetApplicationPropertyVariables()
		}
		redirect(action:'homepageConfig')
	}

	def getScheduleAppointmentForm(){
		println "params: " + params
		def datetime = dateFormatter2.parse(params.d) ?: new Date()
		def availability = DayOfTheWeek.findAllWhere(serviceProvider:session.user, available:true)?.collect{ it.dayIndex }
		def model = adminService.getClients() + adminService.getServices(session.user) + [datetime:datetime, availability:availability]
		render(template:"schedulingForm", model:model)
	}

	def saveService(){
		println "params: " + params
		def service = ServiceType.get(params.long('serviceId'))
		if (service){
			service = adminService.updateService(service,params)
		}else{
			service = adminService.createNewService(params)
		}
		if (service.hasErrors()){
			println "ERROR: " + service.errors
			flash.error = "An error has occured. Please try again."
		}else{
			flash.success = "Your changes have been saved."
		}
		redirect(action:'services')
	}

	def importClients(){
		def clientsFile = request.getFile('clientsFile')
		if (clientsFile.empty) {
			flash.error = 'File cannot be empty.'
			redirect(action: 'homepageConfig')
			return
		}else{
			adminService.importClients(clientsFile)
		}
		flash.success = "Clients were successfully imported."
		redirect(action:'homepageConfig')
	}

	def importAppointments(){
		def appointmentsFile = request.getFile('appointmentsFile')
		if (appointmentsFile?.empty) {
			flash.error = 'File cannot be empty.'
			redirect(action: 'homepageConfig')
			return
		}else{
			adminService.importAppointments(appointmentsFile, session.user)
		}
		flash.success = "Appointments were successfully imported."
		redirect(action:'homepageConfig')
	}

	def getClientsSelectMenu(){
    	def clientData = adminService.getClients(params.lastNameStartsWith)
    	render(template:"clientsSelectMenu", model:clientData)
    }

	def getClientDetails(){
		if (params?.cId){
			def client = User.get(params.cId)
			session.editClient = client
			def appointments = adminService.getAppointmentsForClient(client)
			if (client){
				render (template: "client", model: [client:client, appointments:appointments])
			}
		}
		return false
	}

	def getNewServiceForm(){

		render(template: "newServiceForm")
	}

	def getClientDataForm(){
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
		if (params?.n){
			def coder = new org.apache.commons.codec.net.URLCodec()
			def client = User.get(session.editClient.id)
			client.notes = coder.decode(params.n)
			client.save(flush:true)
			return [success:true] as JSON
		}
		else{
			return [success:true] as JSON
		}
	}

	def saveBlockedTime(){
		Boolean success = false
		Boolean appointmentFailedToSave = false
		try {
			def from = dateFormatter2.parse(params?.date+params?.from)
			def to = dateFormatter2.parse(params?.date+params?.to)

			def serviceProvider = User.findByUsername("kpfanmiller")
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
				appointment.createdBy = session.user.id
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
		Boolean success = false
		Boolean dayOffFailedToSave = false
		try {
			def from = dateFormatter3.parse(params?.from)
			def to = dateFormatter3.parse(params?.to)

			def serviceProvider = User.findByUsername("kpfanmiller")
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
		Boolean success = false
		def deletedTimeslots = []
		def timeSlotsToDelete = params?.blockedOffTime ?: []
		if (timeSlotsToDelete instanceof String) {
			timeSlotsToDelete = [timeSlotsToDelete]
		}
		timeSlotsToDelete?.each(){
			def appointment = Appointment.get(it.toLong())
			appointment.deleted = true
			appointment.updatdBy = session.user.id
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
		Boolean success = false
		if (params?.cId && params?.sId && params?.aDate && params?.sTime){
			success = schedulerService.bookForClient(params)
		}
		if (success){
			flash.success = "Appointment scheduled."
			render ('{"success":true}') as JSON
		}
		else{
			flash.error = "There was an error scheduling this appointment."
			render ('{"success":false}') as JSON
		}
	}

	def rescheduleAppointment(){
		Boolean success = false
		if (params?.aId && params?.sId && params?.aDate && params?.sTime){
			try {
				def existingApointment = Appointment.get(new Long(params.aId))
				params["cId"] = existingApointment.client.id
				params["rescheduledAppointment"] = "TRUE"
				success = schedulerService.bookForClient(params)
				if (success){
					existingApointment.deleted = true
					existingApointment.updatedBy = session.user.id
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
		def timeSlots = []
		if (params?.aDate && params?.sId){
			def requestedDate = dateFormatter3.parse(params.aDate)
			def service = ServiceType.get(new Long(params.sId))
			def serviceProvider = User.get(session.user.id)
			schedulerService.getTimeSlotsAvailableMap(requestedDate, serviceProvider, service)?.each(){ k,v ->
				timeSlots += v
			}
		}
		render (template: "timeSlotOptions", model: [timeSlots:timeSlots])
	}

	def getRescheduleOptions(){
		def appointment
		def timeSlots = []
		if (params?.aId){
			appointment = Appointment.get(new Long(params.aId))
		}
		if (appointment){
			def requestedDate = appointment.appointmentDate
			def service = appointment.service
			def serviceProvider = User.get(session.user.id)
			schedulerService.getTimeSlotsAvailableMap(requestedDate, serviceProvider, service)?.each(){ k,v ->
				timeSlots += v
			}
			def services = ServiceType.list().sort{it.displayOrder}.findAll{it.description != "Blocked Off Time"}
			def availability = DayOfTheWeek.findAllWhere(serviceProvider:serviceProvider, available:true)?.collect{ it.dayIndex }
			render (template: "rescheduleOptions", model: [appointment:appointment, services:services, timeSlots:timeSlots, availability:availability])
		}else{
			render "No appointment found"
		}
	}

	def saveClient(){
		Boolean success = false

		if (params?.cId) {
			def existingClient = User.get(params.cId)
			if (existingClient) {
				success = userService.updateExistingClient(existingClient, params)
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
		Boolean success = false
		if (params.c){ // params.c = appointment.code
			def appointment = Appointment.findByCode(params.c.trim())
			println "appointment(${appointment.id}): " + appointment.client?.getFullName() + " | " + appointment.service?.description + " on " + appointment.appointmentDate.format('MM/dd/yy @ hh:mm a [E]')
			if (appointment){
				appointment.deleted = true
				appointment.updatedBy = session.user.id
				appointment.save(flush:true)
				if (!appointment.hasErrors()){
					notificationService.sendCancellationNotices(appointment, false)
					success = true
					if (appointment.isBlockedTime()){
						flash.success = "Blocked time removed."
					}
					else{
						flash.success = "Appointment deleted."
					}
				}
				else {
					flash.error = "There was an error while attempting to delete the appointment. If the error persists please contact support."
				}
			}
		}
		render ('{"success":'+success+'}') as JSON
	}

	def emailClient(){
		Boolean success = false
		if (params?.e?.size() > 0 && params.m?.size() > 0){
			success = emailService.sendEmail(params.e, params.m)
		}
		render ('{"success":'+success+'}') as JSON
	}

	def getClientHistory(){
		def appointments = []
		if (params?.cId){
			def client = User.get(params.cId)
			appointments = adminService.getAppointmentsForClient(client)
		}
		render (template: "clientHistory", model: [appointments:appointments])
	}

	def updateAvailability(){
		println "\nUPDATING AVAILABILITY"
		println "" + new Date()
		def success = adminService.updateAvailabilityForServiceProvider(session.user, params)
		if (success){
			flash.success = "Availability updated."
		}
		else{
			flash.error = "An error occured attempting to update availability."
		}
		println "success: " + success
		redirect(action:'availability')
	}

}
