package jamnApps.scheduler

import grails.converters.JSON
import org.apache.commons.lang.RandomStringUtils


class AccessController {

	def dateService

	def index() {
		render(view:'login')
	}

	def login = {
		println "params: " + params
		
		def user
		def loggedIn = false
		def loggedInCookieId = request.getCookie('den1')
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
				response.deleteCookie('den1')
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
				response.setCookie('den1', loggedInCookieId)
			}
			redirect (controller:'admin', action:'index')
		}
		else{
			render(view:'login')
		}
	}

	def logout = {

		if (session.user){
			session.invalidate()
			response.deleteCookie('den1')
		}
		if (session.adminUser){
			session.invalidate()
			response.deleteCookie('den1')
		}
		render(view:'login')
	}
}
