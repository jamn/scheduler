package jamnApps.scheduler

import grails.converters.JSON
import org.apache.commons.lang.RandomStringUtils


class AccessController {

	def dateService
	def userService

	static layout = 'user'
	static defaultAction = 'login'

	def login(){
		if (session.client){
			redirect(controller:'book')
		}
	}

	def attemptLogin(){
		if (params?.email || params?.password){
			def loginResults = userService.loginUser(request, params)
			if (loginResults?.client){
				session.client = loginResults.client
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
		session.client = null
		//response.deleteCookie('scheduler-1')
		redirect(uri:session.caller)
	}

	def checkCredentials(){
		def user
		def loggedIn = false
		def loggedInCookieId = request.getCookie('scheduler-1')
		println "EXISTING loggedInCookieId: " + loggedInCookieId
		
		if (loggedInCookieId){
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

		println 'admin user: ' + user?.getFullName()

		if (user?.hasPermission('admin')){
			session.adminUser = user
			if (!loggedIn){
				loggedInCookieId = RandomStringUtils.random(20, true, true)
				println "NEW loggedInCookieId: " + loggedInCookieId
				new LoginLog(
					user:user,
					loggedInCookieId: loggedInCookieId
				).save(flush:true)
				response.setCookie('scheduler-1', loggedInCookieId)
			}
			redirect (controller:'admin', action:'index')
		}
		else{
			redirect(controller:'book', action:'bookAppointment')
		}
	}
}
