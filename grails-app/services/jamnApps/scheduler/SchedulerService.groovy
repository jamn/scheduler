package jamnApps.scheduler

import java.text.SimpleDateFormat
import org.apache.commons.lang.RandomStringUtils
import groovy.sql.Sql
import org.joda.time.*

class SchedulerService {

	/******************************************************************

		Day			| Database	|   Calendar	| 	JS 	|  DateTime
		--------------------------------------------------------------
		Sunday		|	0		|		1		|	0	|     7
		Monday		|	1		|		2		|	1	|	  1
		Tuesday		|	2		|		3		|	2	|	  2
		Wednesday	|	3		|		4		|	3	|	  3
		Thursday	|	4		|		5		|	4	|	  4
		Friday		|	5		|		6		|	5	|	  5
		Saturday	|	6		|		7		|	6	|	  6
		--------------------------------------------------------------
	*******************************************************************/

	static Long HOUR = 3600000
	static Long MINUTE = 60000

	def dateService
	def emailService
	def sessionFactory
	def notificationService

	SimpleDateFormat dateFormatter = new SimpleDateFormat("MM/dd/yyyy")

	public Map getTimeSlotsAvailableMap(Date requestedDate, User serviceProvider, ServiceType service){

		def timeSlotsMap = [:]

		def startDate = new DateTime(requestedDate, DateTimeZone.forID("America/Chicago"))
		def endDate = new DateTime(requestedDate, DateTimeZone.forID("America/Chicago"))

		def serviceProviderDayOfTheWeek = getServiceProviderDayOfTheWeek(serviceProvider, startDate)
		
		def dayIsAvailableByDefault = serviceProviderDayOfTheWeek?.available
		def dayIsNotBlockedOff = DayOff.findWhere(serviceProvider:serviceProvider, dayOffDate:requestedDate) ? false : true

		if (dayIsAvailableByDefault && dayIsNotBlockedOff){

			startDate = startDate.withMillisOfDay(serviceProviderDayOfTheWeek.startTime.intValue()).toLocalDateTime().toDate()
			endDate = endDate.withMillisOfDay(serviceProviderDayOfTheWeek.endTime.intValue()).toLocalDateTime().toDate()
			
			def durationInMinutes = service.duration / MINUTE
			//println "duration: " + durationInMinutes
			//println "startDate: " + startDate
			//println "endDate: " + endDate

			def appointments = Appointment.executeQuery("FROM Appointment a WHERE a.serviceProvider = :serviceProvider AND a.appointmentDate >= :startDate AND a.appointmentDate <= :endDate AND a.deleted = false ORDER BY appointmentDate", [serviceProvider:serviceProvider, startDate:startDate, endDate:endDate])
			def timeSlotStart = new DateTime(startDate)
			def timeSlotEnd = new DateTime(startDate).plusMinutes(durationInMinutes.intValue())
			def count = 1
			while(timeSlotEnd.toLocalDateTime().toDate() <= endDate) {

				//println "----------------------------------------------------"
				//println "1) timeSlotStart: " + timeSlotStart
				//println "1) timeSlotEnd: " + timeSlotEnd

				// DOES AN EXISTING APPOINTMENT FALL IN THIS TIME RANGE?
				def existingAppointment = appointments.find{ it.appointmentDate >= timeSlotStart.toLocalDateTime().toDate() && it.appointmentDate < timeSlotEnd.toLocalDateTime().toDate() }
				def existingAppointmentDurationInMinutes
				while (existingAppointment){
					existingAppointmentDurationInMinutes = existingAppointment.service.duration / MINUTE
					timeSlotStart = new DateTime(existingAppointment.appointmentDate).plusMinutes(existingAppointmentDurationInMinutes.intValue())
					timeSlotEnd = new DateTime(timeSlotStart).plusMinutes(durationInMinutes.intValue())
					//println "${existingAppointment.id} | ${existingAppointment.client?.fullName} | ${existingAppointment.service?.description} | " + existingAppointment.appointmentDate.format('HH:mm')
					//println "existingAppointmentDurationInMinutes: " + existingAppointmentDurationInMinutes
					//println "----------------------------------------------------"
					//println "2) timeSlotStart: " + timeSlotStart
					//println "2) timeSlotEnd: " + timeSlotEnd
					existingAppointment = appointments.find{ it.appointmentDate >= timeSlotStart.toLocalDateTime().toDate() && it.appointmentDate < timeSlotEnd.toLocalDateTime().toDate() }
				}

				def anHourFromNow = new DateTime(DateTimeZone.forID("America/Chicago")).plusMinutes(60)

				//println "<= end date: " + (timeSlotEnd.toLocalDateTime().toDate() <= endDate)
				//println "> 1 hour: " + (timeSlotStart.toLocalDateTime().getLocalMillis() > anHourFromNow.toLocalDateTime().getLocalMillis())
				
				if (timeSlotEnd.toLocalDateTime().toDate() <= endDate && timeSlotStart.toLocalDateTime().getLocalMillis() > anHourFromNow.toLocalDateTime().getLocalMillis()){
					def timeSlot = timeSlotStart.toLocalDateTime().toDate().format('h:mma').replace(':00', '') + " / " + timeSlotEnd.toLocalDateTime().toDate().format('h:mma').replace(':00', '')
					if (timeSlotStart.toLocalDateTime().getHourOfDay() < 11){
						List morning = timeSlotsMap.get("morning") ?: []
						morning.add([startTime:timeSlotStart.toLocalDateTime().toDate().format('MMddyyyyHHmm'), timeSlot: timeSlot, id:count])
						timeSlotsMap.put("morning", morning)
					}
					else if (timeSlotStart.toLocalDateTime().getHourOfDay() < 14){
						List lunch = timeSlotsMap.get("lunch") ?: []
						lunch.add([startTime:timeSlotStart.toLocalDateTime().toDate().format('MMddyyyyHHmm'), timeSlot: timeSlot, id:count])
						timeSlotsMap.put("lunch", lunch)
					}
					else{
						List afternoon = timeSlotsMap.get("afternoon") ?: []
						afternoon.add([startTime:timeSlotStart.toLocalDateTime().toDate().format('MMddyyyyHHmm'), timeSlot: timeSlot, id:count])
						timeSlotsMap.put("afternoon", afternoon)
					}
				}

				timeSlotStart = new DateTime(timeSlotStart).plusMinutes(15)
				timeSlotEnd = new DateTime(timeSlotEnd).plusMinutes(15)
				count++
			}
		}

		return timeSlotsMap
	}

	private getServiceProviderDayOfTheWeek(User serviceProvider, DateTime startDate){
		def serviceProviderDayOfTheWeek
		serviceProvider?.daysOfTheWeek?.each(){
			def dayIndex = (it.dayIndex == 0) ? 7 : it.dayIndex
			if(dayIndex == startDate.getDayOfWeek()){
				serviceProviderDayOfTheWeek = it
			}
		}
		return serviceProviderDayOfTheWeek
	}

	public Boolean bookForClient(Map params = [:]){
		Boolean success = false
		if (params.sTime && params.cId && params.sId && params.aDate){
			def client = User.get(new Long(params.cId))
			def serviceProvider = User.findByUsername("kpfanmiller")
			def service = ServiceType.get(new Long(params.sId))

			def startTimeString = params.sTime
			def amIndex = startTimeString.indexOf("AM")
			def pmIndex = startTimeString.indexOf("PM")
			def minutesIndex = startTimeString.indexOf(":")
			Long hour = 0
			Long minute = 0

			def repeatDuration = new Integer(1)
			def repeatNumberOfAppointments = 1

			if (params?.r?.toLowerCase() == "true"){ // r = recurringAppointment
				println "Recurring Appointment"
				repeatDuration = new Integer(params?.dur)
				repeatNumberOfAppointments = new Integer(params?.num)
			}

			if (amIndex > -1){
				startTimeString = startTimeString.substring(0,amIndex)
			}
			else if (pmIndex > -1){
				startTimeString = startTimeString.substring(0,pmIndex)
			}
			
			if (minutesIndex > -1){
				minute = new Long(startTimeString.substring(minutesIndex+1))
			}
			def tempHour
			if (minutesIndex > -1){
				tempHour = startTimeString.substring(0,minutesIndex)?.padLeft(2, "0")
			}
			else if (amIndex > -1){
				tempHour = startTimeString.substring(0,amIndex)?.padLeft(2, "0")
			}else if (pmIndex > -1){
				tempHour = startTimeString.substring(0,pmIndex)?.padLeft(2, "0")
			}
			hour = new Long(tempHour)
			if (pmIndex > -1 && hour != 12){
				hour = hour + 12
			}
			Calendar tempDate = new GregorianCalendar()
			tempDate.setTime(dateFormatter.parse(params.aDate))
			tempDate.set(Calendar.HOUR_OF_DAY, hour.intValue())
			tempDate.set(Calendar.MINUTE, minute.intValue())
			tempDate.set(Calendar.SECOND, 0)
			tempDate.set(Calendar.MILLISECOND, 0)

			def appointmentDate = tempDate.getTime()
			def count = 1
			List appointmentsScheduled = []

			println "appointmentDate: " + appointmentDate

			while (count <= repeatNumberOfAppointments){
				if (client && serviceProvider && service && appointmentDate){
					def existingAppointment = Appointment.findWhere(appointmentDate:appointmentDate, deleted:false, booked:true)
					if (!existingAppointment){
						def appointment = new Appointment()
						appointment.appointmentDate = appointmentDate
						appointment.serviceProvider = serviceProvider
						appointment.service = service
						appointment.client = client
						appointment.code = RandomStringUtils.random(14, true, true)
						appointment.booked = true
						appointment.save(flush:true)
						if (appointment.hasErrors()){
							println "ERROR!"
							println appointment.errors
						}
						else{
							success = true
							if (params?.rescheduledAppointment?.toString()?.toUpperCase() == "TRUE"){
								emailService.sendRescheduledConfirmationToClient(appointment)
							}
							else{
								appointmentsScheduled.add(appointment)
							}
						}
					}
					else{
						println "ERROR: existing appointment found for this time slot. Unable to book appointment."
						println "existingAppointment: " + existingAppointment
					}
				}
				else {
					println "ERROR! Missing params:"
					println "client: " + client
					println "serviceProvider: " + serviceProvider
					println "service: " + service
					println "appointmentDate: " + appointmentDate

				}

				tempDate.add(Calendar.WEEK_OF_YEAR, repeatDuration)
				appointmentDate = tempDate.getTime()
				count++
			}

			if (appointmentsScheduled.size() > 0){
				notificationService.sendBookingConfirmations(appointmentsScheduled)
			}
		}
		return success
	}

	public List getServicesForServiceProvider(User serviceProvider = null){
		def serviceList = []
		if (serviceProvider){
			def services = ServiceType.executeQuery("FROM ServiceType s WHERE s.serviceProvider = :serviceProvider AND display = true AND deleted = false ORDER BY s.displayOrder", [serviceProvider:serviceProvider])
			services?.each(){
				//def duration = dateService.getTimeString(it.duration)
				def price = it.price
				def description = it.description
				serviceList.add([price:price, description:description, id:it.id])
			}
		}
		return serviceList
	}

	public void deleteStaleAppointments(){
		def numberOfTimeSlotsFreed = 0
		/*def lastAppointmentUserAttemptedToBook = Appointment.get(session?.appointmentId)
		if (lastAppointmentUserAttemptedToBook && lastAppointmentUserAttemptedToBook.booked == false){
			lastAppointmentUserAttemptedToBook.deleted = true
			lastAppointmentUserAttemptedToBook.updatedBy = 0
			lastAppointmentUserAttemptedToBook.save(flush:true)
			numberOfTimeSlotsFreed++
		}*/
		Calendar calendarObject = new GregorianCalendar()
		calendarObject.add(Calendar.MINUTE, -10)
		def tenMinutesAgo = calendarObject.getTime()
		def now = new Date()
		numberOfTimeSlotsFreed += Appointment.executeUpdate("update Appointment a set a.deleted = true, a.updatedBy = 0, a.dateLastUpdated = :now where a.booked = false and a.deleted = false and a.dateCreated < :tenMinutesAgo", [now:now, fiveMinutesAgo:fiveMinutesAgo])	
		sessionFactory.currentSession.flush()
		if (numberOfTimeSlotsFreed > 0){
			println "Deleted ${numberOfTimeSlotsFreed} stale appointments"
		}
	}

	public List getDaysForCalendar(DateTime startDate){
		// SETUP DateTime INSTANCES FOR EACH DAY ON THE CALENDAR
    	// THESE WILL BE USED TO ITERATE OVER WHEN BUILDING THE CALENDAR
		def days = []
		for ( i in 0..6 ){
			days[i] = startDate.plusDays(i)
		}
		return days
	}

	public Long getNumberOfRowsForCalendar(serviceProviderAvailability){
		def earliestStartMillis
		def latestEndMillis
		serviceProviderAvailability.each(){
			if (!earliestStartMillis || earliestStartMillis > it.startTime){
				earliestStartMillis = it.startTime
			}
			if (!latestEndMillis || latestEndMillis < it.endTime){
				latestEndMillis = it.endTime
			}
    	}
		return ((latestEndMillis / HOUR) - (earliestStartMillis / HOUR)) * 2
	}

	public String getCalendarClass(appointment, DateTime dayOfWeek, serviceProviderAvailability){
		DayOfTheWeek config = serviceProviderAvailability.find{it.name == dayOfWeek.toString('EEEE')}
		def cssClass = "available"
		if(appointment){
			cssClass = "booked"
		}else if(config && (config.available == false || 
		 	config.startTime > dayOfWeek.getMillisOfDay() || 
		 	config.endTime <= dayOfWeek.getMillisOfDay())) {
		 		cssClass = "unavailable"
		 }
		return cssClass
	}

	public getCalendarColumnRowspanCount(appointment){
		def rowCount = 1 
		if (appointment){
			rowCount = new BigDecimal(appointment.service.duration).intValueExact() / (15 * 60000)
		}
		return rowCount
	}

	public isBeginningOfAppointment(appointment, calendarTimeslot){
		Boolean isBeginningOfAppointment = true
		if (appointment && calendarTimeslot){
			DateTime appointmentStart = new DateTime(appointment.appointmentDate)
			if (calendarTimeslot != appointmentStart){
				isBeginningOfAppointment = false
			}
		}
		return isBeginningOfAppointment
	}

	public findAppointment(appointments, DateTime calendarTimeslot){
		def appointment
		appointments.each(){
			DateTime appointmentStart = new DateTime(it.appointmentDate)
			DateTime tempAppointmentEnd = new DateTime(it.appointmentDate)
			DateTime appointmentEnd = tempAppointmentEnd.plusMillis(new BigDecimal(it.service.duration).intValueExact())
			if (calendarTimeslot >= appointmentStart && calendarTimeslot < appointmentEnd){
				appointment = it
			}
		}
		return appointment
	}

}