package jamnApps.scheduler

class UserController {

	static layout = 'user'
	static defaultAction = 'profile'

	def userService

	def history(){
		def appointments = Appointment.findAllByClient(session.user)
		appointments = appointments.sort{it.appointmentDate}
		return [appointments:appointments]
	}

	def profile(){}

	def updateProfile(){
		println "\n--------------- UPDATING PROFILE --------------"
		println "params: " + params
		def userUpdated = true
		userService.updateExistingClient(session.user, params)
		if (userUpdated){
			session.user = User.get(session.user.id)
			flash.success = "Your profile has been updated."
		}else{
			flash.error = "There was an error updating your profile."
		}
		redirect(action:'profile')
	}

}