package jamnApps.scheduler

class DateService {

	static Long HOUR = 3600000
	static Long MINUTE = 60000

	public String getTimeOfDayString(Long time){

		def hours = 0
		def minutes = 0
		def amPmIndicator = "AM"
		def timeString = ""

		if (time % HOUR == 0){
			hours = time / HOUR
		}else{
			minutes = time % HOUR
			hours = (time - minutes) / HOUR
		}

		if (hours > 12){
			hours = hours - 12
			amPmIndicator = "PM"
		}

		if (minutes > 0){
			timeString = hours + ":" + minutes + " " + amPmIndicator
		}else{
			timeString = hours + " " + amPmIndicator
		}

		return timeString

	}

	public String getTimeString(Long time){
		def timeString = ""
		if (time == HOUR){
			timeString = "1h"
		}
		else{
			timeString = (time / MINUTE) + "m"
		}
		return timeString
	}

	public Long getMillis(Map params){
		def minutes = params?.minutes ?: 0
		def hours = params?.hours ?: 0
		return ((minutes * MINUTE) + (hours * HOUR))
	}

	public Map get24HourTimeValues(Long time){
		def hour = 0
		def minute = 0

		if (time % HOUR == 0){
			hour = time / HOUR
		}else{
			minute = (time % HOUR)
			hour = (time - minute) / HOUR
			minute = minute / MINUTE
		}

		return [hour:hour, minute:minute]
	}

	public Date getDateFourMonthsAgo(){
		Calendar thirtyDaysAgo = new GregorianCalendar()
		thirtyDaysAgo.add(Calendar.DAY_OF_YEAR, -120)
		return thirtyDaysAgo.getTime()
	}

}