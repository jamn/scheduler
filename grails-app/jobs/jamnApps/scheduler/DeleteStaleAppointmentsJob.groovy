package jamnApps.scheduler

class DeleteStaleAppointmentsJob {

    def schedulerService

    def concurrent = false

    static triggers = {
        cron name: 'DeleteStaleAppointmentsTrigger', cronExpression: "0 0/5 * * * ?"
    }

    def execute() {
        schedulerService.deleteStaleAppointments()
    }

}
