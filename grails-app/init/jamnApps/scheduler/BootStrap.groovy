package jamnApps.scheduler

import java.text.SimpleDateFormat

class BootStrap {

	def defaultServiceProvider
	def defaultClient

	def init = { servletContext ->
		
		def dateService = new DateService()

		if (User.count() == 0){
			println "creating default users..."

			defaultServiceProvider = new User()
			defaultServiceProvider.username = "default-service-provider"
			defaultServiceProvider.password = "ds"
			defaultServiceProvider.firstName = "Default"
			defaultServiceProvider.lastName = "Service Provider"
			defaultServiceProvider.email = "ds"
			defaultServiceProvider.isServiceProvider = true
			defaultServiceProvider.isAdmin = true
			defaultServiceProvider.code = "dsp907201"
			defaultServiceProvider.save(flush:true)

			defaultClient = new User()
			defaultClient.username = "default-client"
			defaultClient.password = "ben"
			defaultClient.firstName = "Default"
			defaultClient.lastName = "Client"
			defaultClient.email = "ben"
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
				displayOrder: new Long(1),
				calendarColor: '#6cab99'
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
				displayOrder: new Long(2),
				calendarColor: '#FFC200'
			).save()
			new ServiceType(
				description: "Buzz Cut",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:15]),
				price: 10,
				displayOrder: new Long(3),
				calendarColor: '#4090FF'
			).save()
			new ServiceType(
				description: "Boy's Cut",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:30]),
				price: 10,
				displayOrder: new Long(4),
				calendarColor: '#8aa9ff'
			).save()
			new ServiceType(
				description: "Father & Son",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:45]),
				price: 10,
				displayOrder: new Long(5),
				calendarColor: '#d88b3c'
			).save()
			new ServiceType(
				description: "Haircut & Color Camo",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:45]),
				price: 10,
				displayOrder: new Long(6),
				calendarColor: '#f3ea35'
			).save()
			new ServiceType(
				description: "Haircut & Hot Towel Shave",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:60]),
				price: 10,
				displayOrder: new Long(7),
				calendarColor: '#7FD8FF'
			).save()
			new ServiceType(
				description: "Haircut & Brow Detail",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:45]),
				price: 10,
				displayOrder: new Long(8),
				calendarColor: '#bb4141'
			).save()
			new ServiceType(
				description: "Blocked Off Time",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:15]),
				price: 10,
				display: false,
				calendarColor: '#505050'
			).save()
		}

		if (!ApplicationProperty.findByName("HOMEPAGE_MESSAGE")){
			println "creating default homepage message..."
			new ApplicationProperty(
				name:"HOMEPAGE_MESSAGE", 
				value:"<p>Welcome! This site will make it easy for you to schedule with me. Click 'Book Now' to get started.</p>"
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

		if (Appointment.count() == 0){
			def serviceProvider = User.findWhere(username:'default-service-provider')
			def client = User.findWhere(username:'default-client')
			def service = ServiceType.findWhere(description:'Haircut')
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			new Appointment(
				appointmentDate: sdf.parse("07/14/2016 11:00"),
				serviceProvider: serviceProvider,
				client: client,
				service: service,
				notes: 'Uses #2 guard on sides.',
				code: '234lsSasdfasfasfw223ras',
				booked: true,
				sendEmailReminder: true,
				reminderEmailSent: true,
				sendTextReminder: true,
				reminderTextSent: true
			).save()
			new Appointment(
				appointmentDate: sdf.parse("07/15/2016 13:30"),
				serviceProvider: serviceProvider,
				client: client,
				service: service,
				notes: 'Uses #2 guard on sides.',
				code: '234lsSWkisisWkw992i21z',
				booked: true,
				sendEmailReminder: true,
				reminderEmailSent: true,
				sendTextReminder: true,
				reminderTextSent: true
			).save()
		}

	}
    def destroy = {
    }
}
