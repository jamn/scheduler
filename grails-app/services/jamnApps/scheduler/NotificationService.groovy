package jamnApps.scheduler

class NotificationService {

	def emailService
	def textMessageService
	
	public sendCancellationNotices(Appointment appointment, Boolean sendToServiceProvider = true){
		if (!appointment.isBlockedTime()){
			println "Sending cancellation notices: " + appointment.client.getFullName() + " | " + appointment.service.description + " on " + appointment.appointmentDate.format('MM/dd/yy @ hh:mm a')
			emailService.sendCancellationNoticeToClient(appointment)
			//emailService.sendCancellationNoticeToServiceProvider(appointment)
			if (sendToServiceProvider){
				textMessageService.sendCancellationNoticeToServiceProvider(appointment)
			}
		}
	}

	public sendBookingConfirmations(List appointments){
		emailService.sendServiceProviderConfirmation(appointments)
		emailService.sendClientConfirmation(appointments)
		textMessageService.sendNewBookingNoticeToServiceProvider(appointments)
	}

}