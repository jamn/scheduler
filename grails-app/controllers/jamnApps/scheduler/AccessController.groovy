package jamnApps.scheduler

import grails.converters.JSON
import org.apache.commons.lang.RandomStringUtils


class AccessController {

	def dateService
	def userService

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
				println "LOGGED IN, REDIRECTING TO: " + session.caller
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
}
