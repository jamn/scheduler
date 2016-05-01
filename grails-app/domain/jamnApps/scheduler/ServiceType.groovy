package jamnApps.scheduler

class ServiceType extends CoreObject {

    String description
	Long duration
	User serviceProvider
	Long price
	Long displayOrder = 0
	Boolean display = true
}
