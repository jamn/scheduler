package jamnApps.scheduler

class DeleteStaleAppointmentsJob {

    def schedulerService

    def concurrent = false

    static triggers = {
		simple repeatInterval: 300000l // every 5 minutes
    }

    def execute() {
        schedulerService.deleteStaleAppointments()
    }

}
