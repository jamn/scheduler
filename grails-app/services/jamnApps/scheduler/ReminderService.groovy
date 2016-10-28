package jamnApps.scheduler

class ReminderService {

	def emailService
	def textMessageService
	
	public sendReminders() {
		Calendar almostTwentyFourHoursFromNow = new GregorianCalendar()
		almostTwentyFourHoursFromNow.add(Calendar.HOUR, 24)
		almostTwentyFourHoursFromNow.add(Calendar.MINUTE, -5)

		Calendar aLittleMoreThanTwentyFourHoursFromNow = new GregorianCalendar()
		aLittleMoreThanTwentyFourHoursFromNow.add(Calendar.HOUR, 24)
		aLittleMoreThanTwentyFourHoursFromNow.add(Calendar.MINUTE, 5)

		def appointments = Appointment.findAllByAppointmentDateBetween(almostTwentyFourHoursFromNow.getTime(), aLittleMoreThanTwentyFourHoursFromNow.getTime())
		
		appointments.each() { appointment ->
			if (appointment.deleted == false){
				println "\n" + new Date()
				println "Attempting to send reminders: " + appointment.client.getFullName() + " | " + appointment.service.description + " on " + appointment.appointmentDate.format('E MM/dd @ hh:mm a')
				try {
					if (appointment.sendEmailReminder){
						emailService.sendReminder(appointment)
					}
					else {
						println "  -- email reminder disabled"
					}
					if (appointment.sendTextReminder){
						textMessageService.sendReminder(appointment)
					}
					else {
						println "  -- text reminder disabled"
					}
				}
				catch(Exception e) {
					println "ERROR: " + e
				}
				
			}
		}
	}
}