package jamnApps.scheduler

class BootStrap {

	def superUser
	def defaultServiceProvider
	def defaultClient

	def init = { servletContext ->
		
		def dateService = new DateService()

		if (User.count() == 0){
			println "creating default users..."
			superUser = new User()
			superUser.username = "super-user"
			superUser.password = "su2016"
			superUser.isAdmin = true
			superUser.firstName = "Super"
			superUser.lastName = "User"
			superUser.code = "su987123"
			superUser.email = "su@schedulepro.online"
			superUser.save(flush:true)

			defaultServiceProvider = new User()
			defaultServiceProvider.username = "default-service-provider"
			defaultServiceProvider.password = "dsp2016"
			defaultServiceProvider.firstName = "Default"
			defaultServiceProvider.lastName = "Service Provider"
			defaultServiceProvider.email = "dsp@schedulepro.online"
			defaultServiceProvider.isServiceProvider = true
			defaultServiceProvider.code = "dsp907201"
			defaultServiceProvider.save(flush:true)

			defaultClient = new User()
			defaultClient.username = "default-client"
			defaultClient.password = "dc2016"
			defaultClient.firstName = "Default"
			defaultClient.lastName = "Client"
			defaultClient.email = "dc@schedulepro.online"
			defaultClient.isClient = true
			defaultClient.code = "dc12391"
			defaultClient.save(flush:true)
			
		}

		if (ServiceType.count() == 0){
			println "creating default services..."
			def newServiceType = new ServiceType(
				description: "Haircut",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:30]),
				price: 10,
				displayOrder: new Long(1)
			)
			if (!newServiceType.save(flush:true)){
				newServiceType.errors.allErrors.each(){
					println "ERROR: " + it + "\n"
				}
			}
			new ServiceType(
				description: "Hot Towel Shave",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:30]),
				price: 10,
				displayOrder: new Long(2)
			).save()
			new ServiceType(
				description: "Buzz Cut",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:15]),
				price: 10,
				displayOrder: new Long(3)
			).save()
			new ServiceType(
				description: "Boy's Cut",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:30]),
				price: 10,
				displayOrder: new Long(4)
			).save()
			new ServiceType(
				description: "Father & Son",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:45]),
				price: 10,
				displayOrder: new Long(5)
			).save()
			new ServiceType(
				description: "Haircut & Color Camo",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:45]),
				price: 10,
				displayOrder: new Long(6)
			).save()
			new ServiceType(
				description: "Haircut & Hot Towel Shave",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:60]),
				price: 10,
				displayOrder: new Long(7)
			).save()
			new ServiceType(
				description: "Haircut & Brow Detail",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:45]),
				price: 10,
				displayOrder: new Long(8)
			).save()
			new ServiceType(
				description: "Blocked Off Time",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:15]),
				price: 10,
				display: false
			).save()
		}

		if (!ApplicationProperty.findByName("HOMEPAGE_MESSAGE")){
			println "creating default homepage message..."
			new ApplicationProperty(
				name:"HOMEPAGE_MESSAGE", 
				value:"<p>Welcome! This site will make it easy for you to schedule with me. Click 'Book Now' to get started."
			).save()
		}

		if (DayOfTheWeek.count() == 0){
			def serviceProvider = User.findWhere(username:'default-service-provider')
			new DayOfTheWeek(
				dayOfTheWeek: 1,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
			new DayOfTheWeek(
				dayOfTheWeek: 2,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
			new DayOfTheWeek(
				dayOfTheWeek: 3,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
			new DayOfTheWeek(
				dayOfTheWeek: 4,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
			new DayOfTheWeek(
				dayOfTheWeek: 5,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
			new DayOfTheWeek(
				dayOfTheWeek: 6,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
			new DayOfTheWeek(
				dayOfTheWeek: 7,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
		}

	}
    def destroy = {
    }
}
