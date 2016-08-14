package jamnApps.scheduler

class DateService {

	static Long HOUR = 3600000
	static Long HALF_HOUR = 1800000
	static Long MINUTE = 60000

	public static int getDaysBetween(day1, day2){

    Calendar dayOne = (Calendar) day1.clone(),
            dayTwo = (Calendar) day2.clone();

    if (dayOne.get(Calendar.YEAR) == dayTwo.get(Calendar.YEAR)) {
        return dayOne.get(Calendar.DAY_OF_YEAR) - dayTwo.get(Calendar.DAY_OF_YEAR)
    } else {
        if (dayTwo.get(Calendar.YEAR) > dayOne.get(Calendar.YEAR)) {
            //swap them
            //Calendar temp = dayOne;
            //dayOne = dayTwo;
            //dayTwo = temp;
        }
        int extraDays = 0;

        int dayOneOriginalYearDays = dayOne.get(Calendar.DAY_OF_YEAR);

        while (dayOne.get(Calendar.YEAR) > dayTwo.get(Calendar.YEAR)) {
            dayOne.add(Calendar.YEAR, -1);
            // getActualMaximum() important for leap years
            extraDays += dayOne.getActualMaximum(Calendar.DAY_OF_YEAR);
        }

        return extraDays - dayTwo.get(Calendar.DAY_OF_YEAR) + dayOneOriginalYearDays ;
    }
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

	public List getTimeSlots(){
		Calendar cal = new GregorianCalendar()
		cal.set(Calendar.HOUR_OF_DAY, 0)
		cal.set(Calendar.MINUTE, 0)
		cal.set(Calendar.SECOND, 0)
		cal.set(Calendar.MILLISECOND, 0)
	}

}