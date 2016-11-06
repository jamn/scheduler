package jamnApps.scheduler

class Appointment extends CoreObject {

	Date appointmentDate
	User serviceProvider
	User client
	ServiceType service
	String notes
	String code
	Boolean booked = false
	Boolean sendEmailReminder = true
	Boolean reminderEmailSent = false
	Boolean sendTextReminder = true
	Boolean reminderTextSent = false
	Boolean cancellationTextSentToServiceProvider = false

	static constraints = {
    	client(nullable:true)
    	notes(nullable:true)
    }

    static mapping = {
    	sort appointmentDate: "desc"
    }

    def isBlockedTime(){
    	return service.description.toUpperCase().contains('BLOCKED')
    }

}

/*

ALTER TABLE `salon_manager`.`appointment` 
ADD INDEX `IDX_DATE_SERVICE` (`appointment_date` ASC, `service_id` ASC),
ADD INDEX `IDX_DATE_BOOKED` (`appointment_date` ASC, `booked` ASC),
ADD INDEX `IDX_DATE` (`appointment_date` ASC),
ADD INDEX `IDX_BOOKED` (`booked` ASC);

*/