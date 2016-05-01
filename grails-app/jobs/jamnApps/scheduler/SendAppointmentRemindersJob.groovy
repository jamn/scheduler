package jamnApps.scheduler

class SendAppointmentRemindersJob {

    def reminderService

    def concurrent = false

    static triggers = {
        cron name: 'SendAppointmentRemindersTrigger', cronExpression: "0 0/15 * * * ?"
    }

    def execute() {
        def active = ApplicationProperty.findByName("JOBS__SEND_APPOINTMENT_REMINDERS_JOB__ACTIVE")?.value ?: "0"
        def date = new Date()
        if (active == "1"){
            //println("Starting SendAppointmentRemindersJob: ${date.format('MM/dd/yy @ HH:mm:ss')}")
            reminderService.sendReminders()
            date = new Date()
            //println("Ending SendAppointmentRemindersJob: ${date.format('MM/dd/yy @ HH:mm:ss')}")
        }
        else{
            println "SendAppointmentRemindersJob IS DISABLED | ${date.format('MM/dd/yy @ HH:mm:ss')}"
        }
    }

}
