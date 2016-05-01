package jamnApps.scheduler

class UserService {

	public Map loginClient(Map params = [:]){
		def results = [
			'error': false,
			'errorDetails': null,
			'client': null
		]
		//if (params.size() > 0){

			def existingUser
			def loggedInCookieId = params?.loggedInCookieId
			println "EXISTING loggedInCookieId: " + loggedInCookieId

			if (loggedInCookieId) {
				def loginLog = LoginLog.findByLoggedInCookieId(loggedInCookieId)
				//def fourMonthsAgo = dateService.getDateFourMonthsAgo()
				//println "Four Months Ago: " + fourMonthsAgo
				//if (loginLog?.dateCreated > fourMonthsAgo){
					//println "last login was less than four months ago"
				if (loginLog.user){
					existingUser = loginLog.user
				}else{
					println "last login was more than four months ago"
				}
			}

			if (!existingUser && params?.e?.size() > 1 && params?.p?.size() > 1){
				existingUser = User.findByEmail(params.e.toLowerCase())
				if (params?.f?.size() > 1 && params?.l?.size() > 1){
					if (!existingUser){
						println "CREATING NEW USER"
						def newClient = createNewClient(params)
						if (newClient.hasErrors()){
							println "ERROR: " + newClient.errors
							results['error'] = true
							results['errorDetails'] = "There was an error creating a new user. Please try again."
						}
						else{
							results['client'] = newClient
						}
					}else if (existingUser.password == params.p){
						println "1) USER LOGGED IN CORRECTLY"
						results['client'] = existingUser
					}else if (existingUser){
						results['error'] = true
						results['errorDetails'] = "Email address exists, but the wrong password was used."
					}
				}
				else if (existingUser && existingUser.password == params.p){
					println "2b) USER LOGGED IN CORRECTLY"
					results['client'] = existingUser
				}
				else if (existingUser && existingUser.password != params.p){
					println "BAD PASSWORD"
					results['error'] = true
					results['errorDetails'] = "Incorrect password."
				}
				else if (!existingUser){
					println "NO USER FOUND"
					results['error'] = true
					results['errorDetails'] = "Email/password not found."
				}
			}
			else if (existingUser) {
				println "2a) USER LOGGED IN CORRECTLY"
				results['client'] = existingUser
			}
			else{
				println "1) USERNAME AND PASSWORD REQUIRED"
				results['error'] = true
				results['errorDetails'] = "Username and password are required."
			}
		//}
		//else{
		//	println "2) USERNAME AND PASSWORD REQUIRED"
		//	results['error'] = true
		//	results['errorDetails'] = "Username and password are required."
		//}
		return results
	}

	public User createNewClient(Map params){
		def newClient = new User()
		newClient.firstName = params.f
		newClient.lastName = params.l
		newClient.email = params.e.toLowerCase()?.replace(' @', '@')?.replace('@ ', '@')
		newClient.password = params.p
		newClient.phone = params.ph
		newClient.code = newClient.firstName.substring(0,1).toLowerCase() + newClient.lastName.substring(0,1).toLowerCase() + new Date().getTime()
		newClient.save(flush:true)
		return newClient
	}

	public Boolean updateClient(User client, Map params) {
		Boolean success = false
		client.firstName = params?.f
		client.lastName = params?.l
		client.password = params?.p
		client.email = params?.e
		client.phone = params?.ph
		client.save()
		if (client.hasErrors()){
			println "ERROR! " + client.errors
		}
		else {
			success = true
		}
		return success
	}

}
