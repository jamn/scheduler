package jamnApps.scheduler

import java.text.SimpleDateFormat
import org.joda.time.*

class BootStrap {

	def defaultCompany
	def defaultServiceProvider
	def defaultClient

	def init = { servletContext ->

		TimeZone.setDefault(TimeZone.getTimeZone("America/Chicago"))
		
		def dateService = new DateService()

		if (Company.count() == 0){
			println "creating default company..."

			defaultCompany = new Company()
			defaultCompany.name = "The Den Barbershop"
			defaultCompany.address1 = "123 Main Street"
			defaultCompany.city = "Kansas City`"
			defaultCompany.state = "MO"
			defaultCompany.zip = "64108"
			defaultCompany.email = "info@thedenbarbershop-kc.com"
			defaultCompany.phone = "816-000-0000"
		}

		if (Image.count == 0) {
			def image = new Image()
			image.url = "https://schedulepro.s3-us-west-2.amazonaws.com/customerName_5927c911-de61-40ac-9592-6a4411fb07cf"
			image.uuid = "customerName_5927c911-de61-40ac-9592-6a4411fb07cf"
			image.company = defaultCompany

			new ApplicationProperty(
				name:"HOMEPAGE_IMAGE_URL", 
				value:image.url
			).save()
		}


		if (User.count() == 0){
			println "creating default users..."

			defaultServiceProvider = new User()
			defaultServiceProvider.username = "kpfanmiller"
			defaultServiceProvider.password = "kjp620300"
			defaultServiceProvider.firstName = "Kalin"
			defaultServiceProvider.lastName = "Pfanmiller"
			defaultServiceProvider.email = "kalin@thedenbarbershop-kc.com"
			defaultServiceProvider.isServiceProvider = true
			defaultServiceProvider.isAdmin = true
			defaultServiceProvider.code = "kp907201"
			defaultServiceProvider.save(flush:true)

			// defaultClient = new User()
			// defaultClient.username = "bjacobi"
			// defaultClient.password = "ben"
			// defaultClient.firstName = "Ben"
			// defaultClient.lastName = "Jacobi"
			// defaultClient.email = "bjacobi@gmail.com"
			// defaultClient.isClient = true
			// defaultClient.code = "bj33201871"
			// defaultClient.save(flush:true)
			
		}

		if (ServiceType.count() == 0){
			println "creating default services..."
			new ServiceType(
				description: "Haircut",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:30]),
				price: 10,
				displayOrder: new Long(1),
				calendarColor: '#6cab99'
			).save()
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
			new ServiceType(
				description: "Cut & Color Camo",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:45]),
				display: false,
				deleted: true,
				calendarColor: '#6d3939'
			).save()
			new ServiceType(
				description: "Head Shave",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:30]),
				display: false,
				deleted: true,
				calendarColor: '#566d39'
			).save()
			new ServiceType(
				description: "Haircut & Beard Trim",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:45]),
				display: false,
				deleted: true,
				calendarColor: '#396d69'
			).save()
			new ServiceType(
				description: "Father & Two Kids",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:60]),
				display: false,
				deleted: true,
				calendarColor: '#39466d'
			).save()
			new ServiceType(
				description: "Haircut & Brow Wax",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:45]),
				display: false,
				deleted: true,
				calendarColor: '#6d3966'
			).save()
			new ServiceType(
				description: "Beard Trim",
				serviceProvider: defaultServiceProvider,
				duration: dateService.getMillis([minutes:15]),
				display: false,
				deleted: true,
				calendarColor: '#A46F8C'
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
			def serviceProvider = User.findWhere(username:'kpfanmiller')
			new DayOfTheWeek(
				name: 'Sunday',
				dayIndex: 0,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
			new DayOfTheWeek(
				name: 'Monday',
				dayIndex: 1,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
			new DayOfTheWeek(
				name: 'Tuesday',
				dayIndex: 2,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
			new DayOfTheWeek(
				name: 'Wednesday',
				dayIndex: 3,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
			new DayOfTheWeek(
				name: 'Thursday',
				dayIndex: 4,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
			new DayOfTheWeek(
				name: 'Friday',
				dayIndex: 5,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
			new DayOfTheWeek(
				name: 'Saturday',
				dayIndex: 6,
				available: true,
				startTime: 36000000,
				endTime: 68400000,
				serviceProvider: serviceProvider
			).save()
		}

		if (Appointment.count() == 0){
			// def serviceProvider = User.findWhere(username:'kpfanmiller')
			// def client = User.findWhere(username:'default-client')
			// def service = ServiceType.findWhere(description:'Haircut')
			// SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			// new Appointment(
			// 	appointmentDate: sdf.parse("07/14/2016 11:00"),
			// 	serviceProvider: serviceProvider,
			// 	client: client,
			// 	service: service,
			// 	notes: 'Uses #2 guard on sides.',
			// 	code: '234lsSasdfasfasfw223ras',
			// 	booked: true,
			// 	sendEmailReminder: true,
			// 	reminderEmailSent: true,
			// 	sendTextReminder: true,
			// 	reminderTextSent: true
			// ).save()
			// new Appointment(
			// 	appointmentDate: sdf.parse("07/15/2016 13:30"),
			// 	serviceProvider: serviceProvider,
			// 	client: client,
			// 	service: service,
			// 	notes: 'Uses #2 guard on sides.',
			// 	code: '234lsSWkisisWkw992i21z',
			// 	booked: true,
			// 	sendEmailReminder: true,
			// 	reminderEmailSent: true,
			// 	sendTextReminder: true,
			// 	reminderTextSent: true
			// ).save()
		}

	}
    def destroy = {
    }
}
