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
		}else if (session.user?.isClient && !session.caller.contains('admin')){
			redirect(controller:'book', action:'chooseService')
		}
	}

	def attemptLogin(){
		if (params?.email || params?.password){
			def loginResults = userService.loginUser(request, params)
			if (loginResults?.user){
				session.user = loginResults.user
				println "LOGGED IN, REDIRECTING TO: " + session.caller ?: '/book'
				if (session.caller){
					redirect(uri:session.caller)
					return
				}else{
					redirect(controller:'book')
					return
				}
			}
			flash.error = loginResults?.errorDetails
		}else{
			flash.error = 'Email/password required.'
		}
		flash.email = params?.email
		redirect(action:'login')
	}

	def logout(){
		println "session: " + session
		session.user = null
		if (session.caller.contains('confirmation') || session.caller.contains('admin')){
			session.caller = '/book'
		}
		//response.deleteCookie('scheduler-1')
		redirect(uri:session.caller)
	}

	def checkCredentials(){
		def user
		def loggedIn = false
		def loggedInCookieId = request.getCookie('scheduler-1')
		if (loggedInCookieId){
			println "EXISTING loggedInCookieId: " + loggedInCookieId
			loggedIn = true
			def loginLog = LoginLog.findByLoggedInCookieId(loggedInCookieId)
			//def fourMonthsAgo = dateService.getDateFourMonthsAgo()
			//println "Four Months Ago: " + fourMonthsAgo
			if (loginLog.user){
			//if (loginLog.user && loginLog?.dateCreated > fourMonthsAgo){
				//println "last login was less than four months ago"
				user = loginLog.user
			}else if (!loginLog){
				response.deleteCookie('scheduler-1')
			}else{
				println "last login was more than four months ago"
			}
		}
		if (!user){
			user = User.findWhere(
				username: params.u,
				password: params.p,
				deleted: false
			)
		}
		println 'user: ' + user?.getFullName()
		if (user?.isAdmin){
			redirect (controller:'admin', action:'index')
		}
		else{
			redirect(controller:'book', action:'bookAppointment')
		}
	}

	def registerNewUser(){
		if (params?.email || params?.password){
			def loginResults = userService.loginUser(request, params)
			if (loginResults?.user){
				session.user = loginResults.user
				println "LOGGED IN, REDIRECTING TO: " + session.caller ?: '/book'
				if (session.caller){
					redirect(uri:session.caller)
					return
				}else{
					redirect(controller:'book')
					return
				}
			}
			flash.error = loginResults?.errorDetails
		}else{
			flash.error = 'Email/password required.'
		}
		flash.email = params?.email
		redirect(action:'login')
	}


	/***************
	 PASSWORD RESET
	****************/

	def resetPassword(){}

	def sendPasswordResetEmail(){
		println "\n---- PASSWORD RESET EMAIL REQUESTED ----"
		println new Date()
		println "params: " + params
		Boolean errorOccurred = false
		def errorMessage = ''

		if (params?.email?.size() > 0){
			def client = User.findByEmail(params?.email)
			if (client){
				try {
					client.passwordResetCode = RandomStringUtils.random(14, true, true)
					client.passwordResetCodeDateCreated = new Date()
					client.save(flush:true)
					def appointment = Appointment.get(session.appointmentId)
					//println "deleting appointment: " + appointment
					//appointment.deleted = true
					//appointment.save()
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
			flash.error = errorMessage
			redirect(action:"resetPassword")
		}else{
			render(view:"confirmation", model:[message:"A password reset link has been sent to your email."])
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
				user.password = password1
				user.passwordResetCode = null
				user.passwordResetCodeDateCreated = null
				user.save(flush:true)
				session.user = user
				session.userUpdatingPassword = null
				if (user.hasErrors()){
					println "ERROR: " + user.errors
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
			flash.error = errorMessage
			redirect(action:"resetPassword")
		}else{
			freeHeldTimeslots()
			resetSessionVariables()
			render(view:"confirmation", model:[message:"Your password has been reset.<br><a href='/book/bookAppointment'>Continue Booking</a>"])
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
