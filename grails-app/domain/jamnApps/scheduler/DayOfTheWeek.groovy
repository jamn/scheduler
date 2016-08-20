package jamnApps.scheduler

import org.joda.time.LocalTime

class DayOfTheWeek extends CoreObject {

	String name // Sunday, Monday, etc.
	Long dayIndex // java.Calendar -- 1=Sunday, 2=Monday, etc.
	Long startTime // 1 hour = 3,600,000
	Long endTime
	Boolean available = false

	static belongsTo = [serviceProvider: User]

	static transients = ['startTimeString', 'endTimeString']

	String getStartTimeString(){
		return LocalTime.fromMillisOfDay(startTime).toString('h:mm a')
	}

	String getEndTimeString(){
		return LocalTime.fromMillisOfDay(endTime).toString('h:mm a')
	}


}