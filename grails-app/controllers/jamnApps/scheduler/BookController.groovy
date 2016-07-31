package jamnApps.scheduler

//import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.apache.commons.lang.RandomStringUtils
import java.text.SimpleDateFormat
import grails.converters.JSON
import groovy.sql.Sql

class BookController {

	def dateService
	def emailService
	def schedulerService
	def userService
	def sessionFactory

	static layout = 'book'
	static Long HOUR = 3600000
	static Long MINUTE = 60000

	SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd")
	SimpleDateFormat dateFormatter2 = new SimpleDateFormat("MMddyyyyHHmm")
	SimpleDateFormat dateFormatter3 = new SimpleDateFormat("MM/dd/yyyy")

	def index(){}

	def chooseService(){
		//schedulerService.deleteStaleAppointments()
		println "\n---- GETTING SERVICES ----"
		println new Date()
		session.serviceProvider = User.findWhere(isServiceProvider:true) ?: null
		def services = schedulerService.getServicesForServiceProvider(session.serviceProvider)
		return [services:services]
	}

	def saveServiceSelection(){
		println "\n---- SAVING SERVICE SELECTION ----"
		println new Date()
		if (params?.id){
			//service = ServiceType.findWhere(serviceProvider:serviceProvider, description:params?.s)
			def service = ServiceType.get(params.id)
			println "service: " + service.description
			session.service = service
			redirect(action:'chooseTime')
		}else{
			redirect(action:'chooseService')
		}	
	}

	def chooseTime(){
		println "\n---- GETTING AVAILABLE TIMESLOTS ----"
		println new Date()
		println params
		def timeSlotsMap = [:]
		def selectedDate
		def service = session.service
		def serviceProvider = User.get(session.serviceProvider.id)
		session.serviceProvider = serviceProvider
		Boolean dontRenderTemplate = false
		Boolean renderDatePicker = false

		if (params?.date){
			selectedDate = dateFormatter3.parse(params?.date)
		}
		else if (session?.selectedDate){
			selectedDate = session.selectedDate
			dontRenderTemplate = true
		}
		else{
			renderDatePicker = true
			selectedDate =  new Date()
		}
		println "selectedDate: " + selectedDate
		println "serviceProvider: " + serviceProvider?.getFullName()

		if (selectedDate && serviceProvider && service){
			timeSlotsMap = schedulerService.getTimeSlotsAvailableMap(selectedDate, serviceProvider, service)
			return [selectedDate:selectedDate, timeSlotsMap:timeSlotsMap]
		}
		else {
			println "ERROR: unable to process params -> " + params
			redirect(action:"index")
		}
	}

	def holdTimeslot(){
		println "\n---- HOLDING TIMESLOT ----"
		println new Date()
		println "params: " + params

		def appointmentDate = params?.startTime ? dateFormatter2.parse(params.startTime) : null
		def repeatDuration = new Integer(1)
		def repeatNumberOfAppointments = 1

		if (params?.r?.toLowerCase() == "true"){ // r = recurringAppointment
			println "Recurring Appointment"
			repeatDuration = new Integer(params?.dur)
			repeatNumberOfAppointments = new Integer(params?.num)
		}
		Calendar startDate = new GregorianCalendar()
		startDate.setTime(appointmentDate)
		def count = 1
		List existingAppointments = []
		List newAppointments = []
		def nextAppointment
		while (count <= repeatNumberOfAppointments){
			def existingAppointment = Appointment.findWhere(appointmentDate:appointmentDate, deleted:false)
			if (existingAppointment && count == 1){
				println "existingAppointment(${existingAppointment.id}): " + existingAppointment.client?.getFullName() + " | " + existingAppointment.service?.description + " on " + existingAppointment.appointmentDate.format('MM/dd/yy @ hh:mm a [E]')
				render ('{"success":false}') as JSON
			}
			else if (existingAppointment && count > 1){
				println "existingAppointment(${existingAppointment.id}): " + existingAppointment.client?.getFullName() + " | " + existingAppointment.service?.description + " on " + existingAppointment.appointmentDate.format('MM/dd/yy @ hh:mm a [E]')
				existingAppointments.add(existingAppointment)
			}

			if (appointmentDate && !existingAppointment){
				def serviceProvider = session.serviceProvider
				def service = session.service
				def appointment = new Appointment()
				appointment.appointmentDate = appointmentDate
				appointment.serviceProvider = serviceProvider
				appointment.service = service
				appointment.code = RandomStringUtils.random(14, true, true)
				appointment.save(flush:true)
				newAppointments.add(appointment)
				if (count == 1){
					nextAppointment = appointment
				}else{
					session.multipleAppointmentsScheduled = true
				}
				println "saved appointment(${appointment.id}): " + appointment.service?.description + " on " + appointment.appointmentDate.format('MM/dd/yy @ hh:mm a [E]')
			}
			startDate.add(Calendar.WEEK_OF_YEAR, repeatDuration)
			appointmentDate = startDate.getTime()
			count++
		}
		session.nextAppointment = nextAppointment
		session.existingAppointments = existingAppointments
		session.newAppointments = newAppointments
		redirect(action:"bookAppointment")
	}

	def bookAppointment(){
		println "\n---- GETTING BOOK APPOINTMENT/LOGIN SCREEN ----"
		println new Date()
		def loginResults = userService.loginUser(request, params)
		def error = params?.error
		println "params: " + params
		if (loginResults?.user){
			session.user = loginResults.user
		}
		return [loggedIn:loginResults.loggedIn, error:error]
	}

	def confirmation(){
		println "\n---- CONFIRMING BOOKING ----"
		println new Date()
		println "params: " + params
		
		Boolean errorOccurred = false
		def errorMessage = ''
		def appointments = []
		def client
		def service = ServiceType.get(session.service.id)
		def serviceProvider = User.get(session.serviceProvider.id)
		def tempAppointment = Appointment.get(session.nextAppointment.id)

		if (session?.user){
			client = User.get(session.user.id)
		}else{
			def loginResults = userService.loginUser(request, params)
			if (loginResults?.user){
				client = loginResults.user
				session.user = client
			}else{
				redirect(action:"bookAppointment", params:[error:loginResults.errorDetails])
				return
			}
		}
		
		println "client: " + client?.getFullName()
		println "service: " + service?.description
		println "serviceProvider: " + serviceProvider?.getFullName()
		
		if (session.multipleAppointmentsScheduled && tempAppointment){
			println "...multiple appointments scheduled and appointment in session..."
			def now = new Date()
			Appointment.findAllWhere(client:client, deleted:false)?.each(){
				if (it.booked == false && it.appointmentDate > now){
					println "adding appointment."
					appointments.add(it)
				}
			}
		}else if (tempAppointment){
			appointments.add(tempAppointment)
		}

		println "appointments: " + appointments

		if (appointments.size() > 0){
			appointments.each(){ appointment ->
				appointment.client = client
				appointment.sendEmailReminder = params?.eRmndr == "true" ? true : false
				appointment.sendTextReminder = params?.tRmndr == "true" ? true : false
				appointment.booked = true
				appointment.save(flush:true)
				if (appointment.hasErrors() || appointment.booked == false){
					println "ERROR: " + appointment?.errors
					errorOccurred = true
					errorMessage = "An error occured trying to save your appointment. Sorry about that, we'll get to the bottom of it. In the meantime please try booking again from the start."
				}
				else{
					println "saved appointment(${appointment.id}): " + appointment.client?.getFullName() + " | " + appointment.service?.description + " on " + appointment.appointmentDate.format('MM/dd/yy @ hh:mm a')
				}
			}
			emailService.sendEmailConfirmation(appointments)

			if (session.existingAppointmentId){
				def existingAppointment = Appointment.get(session.existingAppointmentId)
				if (existingAppointment.client == client){
					println "Deleting existing appointment..."
					existingAppointment.deleted = true
					existingAppointment.save(flush:true)
					session.existingAppointmentId = null
					emailService.sendCancellationNotices(existingAppointment)
				}
			}
		}

		return [appointments:appointments, existingAppointments:session.existingAppointments]

	}

	def modifyAppointment(){
		println "\n---- MODIFY APPOINTMENT ----"
		println new Date()
		println "params: " + params
		if (params.a && params.cc){ // params.a = appointmentId | params.cc = clientCode
			def appointment = Appointment.findWhere(id:new Long(params.a), deleted:false)
			if (appointment && appointment?.client?.code?.toUpperCase() == params.cc.toString().toUpperCase().trim()){
				session.appointmentId = null
				session.requestedDate = appointment.appointmentDate
				session.service = appointment.service
				session.serviceProvider = appointment.serviceProvider
				session.existingAppointmentId = appointment.id
				redirect(action:'chooseTime')
				return
			}
			else{
				println "ERROR: appointment doesn't exist"
			}
		}
		else{
			println "ERROR: required params not passed"
		}
		flash.error = "There was an error rescheduling your appointment. If the problem persists please email the shop."
		redirect(controller:'user', action:'history')
	}

	def cancelAppointment(){
		println "\n---- CANCEL APPOINTMENT? ----"
		println new Date()
		println "params: " + params
		
		if (params?.c){ // params.c = appointment.code
			def appointment = Appointment.findWhere(code:params.c.trim(), deleted:false)
			if (appointment){
				session.appointmentToDelete = appointment
				println "appointment(${appointment?.id}): " + appointment?.client?.getFullName() + " | " + appointment?.service?.description + " on " + appointment?.appointmentDate?.format('MM/dd/yy @ hh:mm a [E]')
			}
		}

		println "session: " + session

		def message = ApplicationProperty.findByName("HOMEPAGE_MESSAGE")?.value ?: "No messages found."
		render (template: "cancelAppointment", model: [message: message])
	}

	def confirmAppointmentCancellation(){
		println "\n---- CANCEL APPOINTMENT CONFIRMED ----"
		println new Date()
		println "params: " + params
		Boolean appointmentDeleted = false
		if (session?.appointmentToDelete){
			def appointment = session.appointmentToDelete
			appointment.deleted = true
			appointment.save(flush:true)
			if (appointment.hasErrors()){
				println "ERROR: " + appointment.error
			}
			else{
				appointmentDeleted = true
				session.appointmentToDelete = null
				emailService.sendCancellationNotices(appointment)
			}
		}
		def message = ApplicationProperty.findByName("HOMEPAGE_MESSAGE")?.value ?: "No messages found."
		render(template: "cancelAppointmentConfirmation", model: [message: message, appointmentDeleted:appointmentDeleted])
	}

	def confirmedCancelAppointment(){
		println "\n---- CANCEL APPOINTMENT CONFIRMED ----"
		println new Date()
		println "params: " + params
		if (params.c){
			def appointment = Appointment.findByCode(params.c)
			if (appointment){
				appointment.deleted = true
				appointment.save(flush:true)
				if (appointment.hasErrors()){
					println "ERROR: " + appointment.error
					flash.error = "There was an error cancelling this appointment."
				}
				else{
					flash.success = "Appointment canceled."
					//emailService.sendCancellationNotices(appointment)
				}
			}
		}
		redirect(controller: 'user', action:'history')
	}




	/***************
	 PASSWORD RESET
	****************/


	def sendPasswordResetEmail(){
		println "\n---- PASSWORD RESET EMAIL REQUESTED ----"
		println new Date()
		println "params: " + params
		Boolean errorOccurred = false
		def errorMessage = ''

		if (params?.e?.size() > 0){
			def email = params?.e
			def client = User.findByEmail(email)
			if (client){
				try {
					client.passwordResetCode = RandomStringUtils.random(14, true, true)
					client.passwordResetCodeDateCreated = new Date()
					client.save(flush:true)
					def appointment = Appointment.get(session.appointmentId)
					println "deleting appointment: " + appointment
					appointment.deleted = true
					appointment.save()
					emailService.sendPasswordResetLink(client)
				}
				catch(Exception e){
					println "ERROR: " + e
					errorOccurred = true
					errorMessage = "Oops, something didn't work right. Try again please."
				}
				
			}
			else{
				errorOccurred = true
				errorMessage = "Email not found."
			}
		}else{
			errorOccurred = true
			errorMessage = "Email required."
		}

		if (errorOccurred){
			println "AN ERROR OCCURRED: " + errorMessage
			def jsonString = '{"success":false,"errorMessage":"'+errorMessage+'"}'
			render(jsonString)
		}else{
			render(template: "confirmation", model: [passwordReset:true])
		}
	}

	def resetPasswordForm(){
		println "\n---- GETTING PASSWORD RESET FORM ----"
		println new Date()
		println "params: " + params

		if (params?.rc?.size() > 0 && params?.cc?.size() > 0){
			def user = User.findWhere(passwordResetCode:params.rc, code:params.cc)
			if (user){
				session.userUpdatingPassword = user
				def message = ApplicationProperty.findByName("HOMEPAGE_MESSAGE")?.value ?: "No messages found."
				render(template: "resetPassword", model: [message:message])
			}
		}else{
			println "ERROR: required params not passed."
		}
	}

	def attemptPasswordReset(){
		println "\n---- ATTEMPTING TO RESET PASSWORD ----"
		println new Date()
		println "params: " + params
		Boolean errorOccurred = false
		def errorMessage = ''
		if (params?.p1?.size() > 0 && params?.p2?.size() > 0){
			def password1 = params.p1
			def password2 = params.p2
			if (password1 == password2){
				session.userUpdatingPassword.password = password1
				session.userUpdatingPassword.passwordResetCode = null
				session.userUpdatingPassword.passwordResetCodeDateCreated = null
				session.userUpdatingPassword.save(flush:true)
				if (session.userUpdatingPassword.hasErrors()){
					println "ERROR: " + session.userUpdatingPassword.errors
					errorOccurred = true
					errorMessage = 'Something really weird happened. Please try again.'
				}
			}else{
				errorOccurred = true
				errorMessage = 'Password fields do not match.'
			}
		}
		else{
			errorOccurred = true
			errorMessage = 'Please enter a new password.'
		}

		if (errorOccurred){
			println "AN ERROR OCCURRED: " + errorMessage
			def jsonString = '{"success":false,"errorMessage":"'+errorMessage+'"}'
			render(jsonString)
		}else{
			freeHeldTimeslots()
			resetSessionVariables()
			render(template: "confirmation", model: [passwordReset:true, success:true])
		}
	}





	/********
	 HELPERS
	 ********/


	private freeHeldTimeslots(){
		session?.bookedAppointments?.each(){
			def appointment = Appointment.get(it.id)
			if (appointment && appointment.booked == false){
				appointment.deleted = true
				appointment.save(flush:true)
			}
		}
	}

	private resetSessionVariables(){
		session?.bookedAppointments = null
		session?.serviceProviderId = null
		session?.serviceId = null
		session?.userUpdatingPassword = null
		session?.existingAppointments = null
		session?.appointmentId = null
	}

}
