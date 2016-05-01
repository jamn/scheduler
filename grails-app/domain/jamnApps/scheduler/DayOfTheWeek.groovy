package jamnApps.scheduler

class DayOfTheWeek extends CoreObject {

	Long dayOfTheWeek // java.Calendar -- 1=Sunday, 2=Monday, etc.
	Boolean available = false
	Long startTime // 1 hour = 3,600,000
	Long endTime

	static belongsTo = [serviceProvider: User]

	/*def getStartTime(){
		return startTime * 3600000
	}

	def getEndTime(){
		return endTime * 36000000
	}*/

}