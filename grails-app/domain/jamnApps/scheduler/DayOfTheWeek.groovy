package jamnApps.scheduler

class DayOfTheWeek extends CoreObject {

	String name // Sunday, Monday, etc.
	Long dayIndex // java.Calendar -- 1=Sunday, 2=Monday, etc.
	Long startTime // 1 hour = 3,600,000
	Long endTime
	Boolean available = false

	static belongsTo = [serviceProvider: User]

}