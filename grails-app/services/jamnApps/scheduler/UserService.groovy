package jamnApps.scheduler

class UserService {

	public Map loginUser(request , Map params){
		def results = [
			'error': false,
			'errorDetails': null,
			'user': null
		]

		def existingUser
		def cookie = request.getCookie('den-scheduler-1')
		println "cookie: " + cookie
		if (cookie) {
			def loginLog = LoginLog.findWhere(loggedInCookieId:cookie, deleted:false)
			existingUser = loginLog?.user
			//def fourMonthsAgo = dateService.getDateFourMonthsAgo()
			//if (loginLog?.dateCreated > fourMonthsAgo){
			//println "last login was more than four months ago"
		}

		if (!existingUser && params?.email?.size() > 1 && params?.password?.size() > 1){
			existingUser = User.findByEmail(params.email.toLowerCase())
			if (!existingUser){
				println "NO USER FOUND"
				results['error'] = true
				results['errorDetails'] = "Email was not found."
			}else{
				if (existingUser.password == params.password){
					println "USER LOGGED IN CORRECTLY"
					results['user'] = existingUser
				}else{
					println "BAD PASSWORD"
					results['error'] = true
					results['errorDetails'] = "Incorrect password."
				}
			} 
		}else{
			println "USERNAME AND PASSWORD REQUIRED"
			results['error'] = true
			results['errorDetails'] = "Username and password are required."
		}
		println "login results: " + results
		return results
	}

	public User createNewClient(Map params){
		def newClient = new User()
		newClient.firstName = params.firstName
		newClient.lastName = params.lastName
		newClient.email = params.email.toLowerCase()?.replace(' @', '@')?.replace('@ ', '@')
		newClient.password = params.password
		newClient.phone = params.phone
		newClient.code = newClient.firstName.substring(0,1).toLowerCase() + newClient.lastName.substring(0,1).toLowerCase() + new Date().getTime()
		newClient.isClient = true
		newClient.save(flush:true)
		return newClient
	}

	public Boolean updateExistingClient(existingClient, params) {
		def client = User.get(existingClient.id)
		Boolean success = false
		client.firstName = params?.firstName
		client.lastName = params?.lastName
		client.password = params?.password
		client.email = params?.email
		client.phone = params?.phoneNumber
		client.save(flush:true)
		if (client.hasErrors()){
			println "ERROR! " + client.errors
		}
		else {
			success = true
		}
		return success
	}

}
