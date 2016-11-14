package jamnApps.scheduler

class ServiceType extends CoreObject {

    String description
	Long duration // in milleseconds
	User serviceProvider
	Long price
	Long displayOrder = 0
	Boolean display = true
	String calendarColor

	static constraints = {
		price nullable:true
		calendarColor nullable:true
	}

	def transients = [
    	'durationInMinutes'
    ]

    String getDurationInMinutes(){
	return (duration / 60000) + ' min'
    }

}
