package jamnApps.scheduler

class UserController {

	static layout = 'user'
	static defaultAction = 'profile'

	def userService

	def profile(){}

	def history(){}

	def updateProfile(){
		println "\n--------------- UPDATING PROFILE --------------"
		println "params: " + params
		def userUpdated = true
		userService.updateExistingClient(session.client, params)
		if (userUpdated){
			session.client = User.get(session.client.id)
			flash.success = "Your profile has been updated."
		}else{
			flash.error = "There was an error updating your profile."
		}
		redirect(action:'profile')
	}

}