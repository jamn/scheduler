package jamnApps.scheduler

import grails.converters.JSON
import org.apache.commons.lang.RandomStringUtils


class AccessController {

	def dateService
	def userService
	def emailService

	static layout = 'user'
	static defaultAction = 'login'

	def login(){
		if (session.user?.isAdmin){
			redirect(controller:'admin')
		}else if (session.user?.isClient && !session.caller?.contains('admin')){
			redirect(controller:'book', action:'chooseService')
		}
	}

	def attemptLogin(){
		println "attempting login..."
		if (params?.email || params?.password){
			def loginResults = userService.loginUser(request, params)
			if (loginResults?.user){
				session.user = loginResults.user
				if (params?.rememberMe){
					def loggedInCookieId = RandomStringUtils.random(20, true, true)
					new LoginLog(
						user:loginResults.user,
						loggedInCookieId: loggedInCookieId
					).save(flush:true)
					def defaultExpiration = 15552000 //6 months
					def path = '/'
            		response.setCookie('den-scheduler-1', loggedInCookieId, defaultExpiration, path)
				}
				println "LOGGED IN"
				if (session.caller){
					redirect(uri:session.caller)
					return
				}else{
					if (loginResults?.user?.isAdmin){
						redirect(controller:'admin', action:'calendar')
					}else{
						redirect(controller:'book', action:'confirmation')
					}
					return
				}
			}
			flash.error = loginResults?.errorDetails
		}else{
			flash.error = 'Email/password required.'
		}
		if (flash.error){
			println "ERROR: " + flash.error
		}
		flash.email = params?.email
		redirect(action:'login')
	}

	def logout(){
		println "logging user out: " + session.user?.fullName
		LoginLog.executeUpdate(
			"update LoginLog set deleted = true where deleted = false and user = ?", [session.user]
		)
		response.deleteCookie('den-scheduler-1', '/')
		session.user = null
		if (session.caller?.contains('confirmation') || session.caller?.contains('admin') || session.caller?.contains('attemptPasswordReset')){
			session.caller = '/book'
		}
		redirect(uri:session.caller)
	}

	def registerNewUser(){
		println "\n---- REGISTERING NEW USER ----"
		println new Date()
		println "params: " + params
		Boolean error = false
		if (params.email?.size() > 1 && params.password?.size() > 1 && params.firstName?.size() > 1 && params.lastName?.size() > 1 && params.phoneNumber?.size() > 1){
			println "CREATING NEW USER"
			def existingClient = User.findByEmail(params.email)
			if (existingClient){
				flash.error = "That email address has already been registered."
				flash.email = params.email
				flash.sendPasswordResetLink = true
				error = true
			}else{
				def newClient = userService.createNewClient(params)
				if (newClient.hasErrors()){
					println "ERROR: " + newClient.errors
					flash.error = "There was an unexpected error creating your new account. Please try again."
					error = true
				}else{
					session.user = newClient
				}
			}
		}else{
			flash.newClientRegistering = true
			flash.email = params.email
			flash.password = params.password
			flash.firstName = params.firstName
			flash.lastName = params.lastName
			flash.phoneNumber = params.phoneNumber
			flash.error = 'All fields must be completed.'
			error = true
		}
		if (error){
			redirect(controller:'book', action:'bookAppointment')
		}else{
			redirect(controller:'book', action:'confirmation')
		}
	}


	/***************
	 PASSWORD RESET
	****************/

	def resetPassword(){}

	def sendPasswordResetEmail(){
		println "\n---- PASSWORD RESET EMAIL REQUESTED ----"
		println new Date()
		println "params: " + params
		Boolean errorOccurred = true
		def errorMessage = ''

		if (params?.email?.size() > 0){
			def client = User.findByEmail(params?.email)
			if (client){
				try {
					client.passwordResetCode = RandomStringUtils.random(14, true, true)
					client.passwordResetCodeDateCreated = new Date()
					client.save(flush:true)
					emailService.sendPasswordResetLink(client)
					errorOccurred = false
				}
				catch(Exception e){
					println "ERROR: " + e
					errorMessage = "An error has occured. If you continue to receive this message please contact support."
				}
				
			}
			else{
				errorMessage = "Email not found."
			}
		}else{
			errorMessage = "Email required."
		}

		if (errorOccurred){
			println "AN ERROR OCCURRED: " + errorMessage
			flash.error = errorMessage
			redirect(action:"resetPassword")
		}else{
			render(view:"confirmation", model:[message:"A password reset link has been sent to ${params.email}."])
		}
	}

	def resetPasswordForm(){
		println "\n---- GETTING PASSWORD RESET FORM ----"
		println new Date()
		println "params: " + params
		def user
		if (params?.rc?.size() > 0 && params?.cc?.size() > 0){
			user = User.findWhere(passwordResetCode:params.rc, code:params.cc)
		}
		if (user){
			session.userUpdatingPassword = user
			return
		}else{
			flash.error = "An error occured. Please try again."
			redirect(action:"resetPassword")
		}
	}

	def attemptPasswordReset(){
		println "\n---- ATTEMPTING TO RESET PASSWORD ----"
		println new Date()
		println "params: " + params
		Boolean errorOccurred = false
		def errorMessage = ''
		if (params?.newPassword?.size() > 0 && params?.verifyNewPassword?.size() > 0){
			def password1 = params.newPassword
			def password2 = params.verifyNewPassword
			if (password1 == password2){
				def user = User.get(session.userUpdatingPassword.id)
				if (user){
					user.password = password1.encodeAsSHA256()
					user.passwordResetCode = null
					user.passwordResetCodeDateCreated = null
					user.save(flush:true)
				}
				if (user.hasErrors()){
					println "ERROR: " + user.errors
					errorOccurred = true
					errorMessage = 'Something really weird happened. Please try again.'
				}else{
					session.user = user
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
			flash.error = errorMessage
			redirect(action:"resetPassword")
		}else{
			session.userUpdatingPassword = null
			//freeHeldTimeslots()
			//resetSessionVariables()
			if (session?.bookedAppointments){
				redirect(controller:"book", action:"bookAppointment")
			}else{
				render(view:"confirmation", model:[message:"Your password has been reset.<br><a href='/book/bookAppointment'>Continue Booking</a>"])
			}
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
