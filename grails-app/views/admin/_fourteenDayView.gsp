<%
Calendar dayOne = new GregorianCalendar()
dayOne.setTime(startTime.getTime())
Calendar dayTwo = new GregorianCalendar()
dayTwo.setTime(startTime.getTime())
dayTwo.add(Calendar.DAY_OF_WEEK, 1)
Calendar dayThree = new GregorianCalendar()
dayThree.setTime(startTime.getTime())
dayThree.add(Calendar.DAY_OF_WEEK, 2)
Calendar dayFour = new GregorianCalendar()
dayFour.setTime(startTime.getTime())
dayFour.add(Calendar.DAY_OF_WEEK, 3)
Calendar dayFive = new GregorianCalendar()
dayFive.setTime(startTime.getTime())
dayFive.add(Calendar.DAY_OF_WEEK, 4)
Calendar daySix = new GregorianCalendar()
daySix.setTime(startTime.getTime())
daySix.add(Calendar.DAY_OF_WEEK, 5)
Calendar daySeven = new GregorianCalendar()
daySeven.setTime(startTime.getTime())
daySeven.add(Calendar.DAY_OF_WEEK, 6)
def now = new Date()
%>
<g:set var="schedulerService" bean="schedulerService"/>
<table id="fourteenDayView-week1">
<tr class="dateHeader">
	<td></td>
	<td>${dayOne.getTime().format('EEE dd')}</td>
	<td>${dayTwo.getTime().format('EEE dd')}</td>
	<td>${dayThree.getTime().format('EEE dd')}</td>
	<td>${dayFour.getTime().format('EEE dd')}</td>
	<td>${dayFive.getTime().format('EEE dd')}</td>
	<td>${daySix.getTime().format('EEE dd')}</td>
	<td>${daySeven.getTime().format('EEE dd')}</td>
	<td></td>
</tr>
<% while (dayOne < endTime){ %>
	<tr class="halfHour" time="${dayOne.getTime().format('hh:mm a')}">
		<td rowspan="2" class="time">${dayOne.getTime().format('hh:mm a')}</td>
		<%
			def dayOneAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayOne.getTime() >= startDate.getTime() && dayOne.getTime() < endDate.getTime()){
					dayOneAppointment = it
				}
			}
			def dayOneDay = dayOne.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(dayOneAppointment,dayOneDay)}">
			<%if (dayOneAppointment){%><div id="appointment-${dayOneAppointment?.id}" class="appointmentDetailsCallOut">${dayOneAppointment?.service?.description}</div><%}%>
		</td>

		<%
			def dayTwoAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayTwo.getTime() >= startDate.getTime() && dayTwo.getTime() < endDate.getTime()){
					dayTwoAppointment = it
				}
			}
			def dayTwoDay = dayTwo.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(dayTwoAppointment,dayTwoDay)}">
			<%if (dayTwoAppointment){%><div id="appointment-${dayTwoAppointment?.id}" class="appointmentDetailsCallOut">${dayTwoAppointment?.service?.description}</div><%}%>
		</td>

		<%
			def dayThreeAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayThree.getTime() >= startDate.getTime() && dayThree.getTime() < endDate.getTime()){
					dayThreeAppointment = it
				}
			}
			def dayThreeDay = dayThree.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(dayThreeAppointment,dayThreeDay)}">
			<%if (dayThreeAppointment){%><div id="appointment-${dayThreeAppointment?.id}" class="appointmentDetailsCallOut">${dayThreeAppointment?.service?.description}</div><%}%>
		</td>

		<%
			def dayFourAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayFour.getTime() >= startDate.getTime() && dayFour.getTime() < endDate.getTime()){
					dayFourAppointment = it
				}
			}
			def dayFourDay = dayFour.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(dayFourAppointment,dayFourDay)}">
			<%if (dayFourAppointment){%><div id="appointment-${dayFourAppointment?.id}" class="appointmentDetailsCallOut">${dayFourAppointment?.service?.description}</div><%}%>
		</td>

		<%
			def dayFiveAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayFive.getTime() >= startDate.getTime() && dayFive.getTime() < endDate.getTime()){
					dayFiveAppointment = it
				}
			}
			def dayFiveDay = dayFive.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(dayFiveAppointment,dayFiveDay)}">
			<%if (dayFiveAppointment){%><div id="appointment-${dayFiveAppointment?.id}" class="appointmentDetailsCallOut">${dayFiveAppointment?.service?.description}</div><%}%>
		</td>

		<%
			def daySixAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (daySix.getTime() >= startDate.getTime() && daySix.getTime() < endDate.getTime()){
					daySixAppointment = it
				}
			}
			def daySixDay = daySix.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(daySixAppointment,daySixDay)}">
			<%if (daySixAppointment){%><div id="appointment-${daySixAppointment?.id}" class="appointmentDetailsCallOut">${daySixAppointment?.service?.description}</div><%}%>
		</td>

		<%
			def daySevenAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (daySeven.getTime() >= startDate.getTime() && daySeven.getTime() < endDate.getTime()){
					daySevenAppointment = it
				}
			}
			def daySevenDay = daySeven.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(daySevenAppointment,daySevenDay)}">
			<%if (daySevenAppointment){%><div id="appointment-${daySevenAppointment?.id}" class="appointmentDetailsCallOut">${daySevenAppointment?.service?.description}</div><%}%>
		</td>
		

		<td rowspan="2" class="time">${dayOne.getTime().format('hh:mm a')}</td>
	</tr>

	<%dayOne.add(Calendar.MINUTE, 15)%>
	<%dayTwo.add(Calendar.MINUTE, 15)%>
	<%dayThree.add(Calendar.MINUTE, 15)%>
	<%dayFour.add(Calendar.MINUTE, 15)%>
	<%dayFive.add(Calendar.MINUTE, 15)%>
	<%daySix.add(Calendar.MINUTE, 15)%>
	<%daySeven.add(Calendar.MINUTE, 15)%>

	<tr class="fifteen" time="${dayOne.getTime().format('hh:mm a')}">
		<%
			dayOneAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayOne.getTime() >= startDate.getTime() && dayOne.getTime() < endDate.getTime()){
					dayOneAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(dayOneAppointment,dayOneDay)}">
			<%if (dayOneAppointment){%><div id="appointment-${dayOneAppointment?.id}" class="appointmentDetailsCallOut">${dayOneAppointment?.service?.description}</div><%}%>
		</td>

		<%
			dayTwoAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayTwo.getTime() >= startDate.getTime() && dayTwo.getTime() < endDate.getTime()){
					dayTwoAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(dayTwoAppointment,dayTwoDay)}">
			<%if (dayTwoAppointment){%><div id="appointment-${dayTwoAppointment?.id}" class="appointmentDetailsCallOut">${dayTwoAppointment?.service?.description}</div><%}%>
		</td>

		<%
			dayThreeAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayThree.getTime() >= startDate.getTime() && dayThree.getTime() < endDate.getTime()){
					dayThreeAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(dayThreeAppointment,dayThreeDay)}">
			<%if (dayThreeAppointment){%><div id="appointment-${dayThreeAppointment?.id}" class="appointmentDetailsCallOut">${dayThreeAppointment?.service?.description}</div><%}%>
		</td>

		<%
			dayFourAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayFour.getTime() >= startDate.getTime() && dayFour.getTime() < endDate.getTime()){
					dayFourAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(dayFourAppointment,dayFourDay)}">
			<%if (dayFourAppointment){%><div id="appointment-${dayFourAppointment?.id}" class="appointmentDetailsCallOut">${dayFourAppointment?.service?.description}</div><%}%>
		</td>

		<%
			dayFiveAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayFive.getTime() >= startDate.getTime() && dayFive.getTime() < endDate.getTime()){
					dayFiveAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(dayFiveAppointment,dayFiveDay)}">
			<%if (dayFiveAppointment){%><div id="appointment-${dayFiveAppointment?.id}" class="appointmentDetailsCallOut">${dayFiveAppointment?.service?.description}</div><%}%>
		</td>

		<%
			daySixAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (daySix.getTime() >= startDate.getTime() && daySix.getTime() < endDate.getTime()){
					daySixAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(daySixAppointment,daySixDay)}">
			<%if (daySixAppointment){%><div id="appointment-${daySixAppointment?.id}" class="appointmentDetailsCallOut">${daySixAppointment?.service?.description}</div><%}%>
		</td>

		<%
			daySevenAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (daySeven.getTime() >= startDate.getTime() && daySeven.getTime() < endDate.getTime()){
					daySevenAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(daySevenAppointment,daySevenDay)}">
			<%if (daySevenAppointment){%><div id="appointment-${daySevenAppointment?.id}" class="appointmentDetailsCallOut">${daySevenAppointment?.service?.description}</div><%}%>
		</td>
	</tr>
	<%dayOne.add(Calendar.MINUTE, 15)%>
	<%dayTwo.add(Calendar.MINUTE, 15)%>
	<%dayThree.add(Calendar.MINUTE, 15)%>
	<%dayFour.add(Calendar.MINUTE, 15)%>
	<%dayFive.add(Calendar.MINUTE, 15)%>
	<%daySix.add(Calendar.MINUTE, 15)%>
	<%daySeven.add(Calendar.MINUTE, 15)%>
<%}%>
</table>

<%
Calendar dayEight = new GregorianCalendar()
dayEight.setTime(startTime.getTime())
dayEight.add(Calendar.DAY_OF_WEEK, 7)
Calendar dayEightEndTime = new GregorianCalendar()
dayEightEndTime.setTime(endTime.getTime())
dayEightEndTime.add(Calendar.DAY_OF_WEEK, 7)
Calendar dayNine = new GregorianCalendar()
dayNine.setTime(startTime.getTime())
dayNine.add(Calendar.DAY_OF_WEEK, 8)
Calendar dayTen = new GregorianCalendar()
dayTen.setTime(startTime.getTime())
dayTen.add(Calendar.DAY_OF_WEEK, 9)
Calendar dayEleven = new GregorianCalendar()
dayEleven.setTime(startTime.getTime())
dayEleven.add(Calendar.DAY_OF_WEEK, 10)
Calendar dayTwelve = new GregorianCalendar()
dayTwelve.setTime(startTime.getTime())
dayTwelve.add(Calendar.DAY_OF_WEEK, 11)
Calendar dayThirteen = new GregorianCalendar()
dayThirteen.setTime(startTime.getTime())
dayThirteen.add(Calendar.DAY_OF_WEEK, 12)
Calendar dayFourteen = new GregorianCalendar()
dayFourteen.setTime(startTime.getTime())
dayFourteen.add(Calendar.DAY_OF_WEEK, 13)
%>
<table id="fourteenDayView-week2">
<tr class="dateHeader">
	<td></td>
	<td>${dayEight.getTime().format('EEE dd')}</td>
	<td>${dayNine.getTime().format('EEE dd')}</td>
	<td>${dayTen.getTime().format('EEE dd')}</td>
	<td>${dayEleven.getTime().format('EEE dd')}</td>
	<td>${dayTwelve.getTime().format('EEE dd')}</td>
	<td>${dayThirteen.getTime().format('EEE dd')}</td>
	<td>${dayFourteen.getTime().format('EEE dd')}</td>
	<td></td>
</tr>
<% while (dayEight < dayEightEndTime){ %>
	<tr class="halfHour" time="${dayEight.getTime().format('hh:mm a')}">
		<td rowspan="2" class="time">${dayEight.getTime().format('hh:mm a')}</td>
		<%
			def dayEightAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayEight.getTime() >= startDate.getTime() && dayEight.getTime() < endDate.getTime()){
					dayEightAppointment = it
				}
			}
			def dayEightDay = dayEight.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(dayEightAppointment,dayEightDay)}">
			<%if (dayEightAppointment){%><div id="appointment-${dayEightAppointment?.id}" class="appointmentDetailsCallOut">${dayEightAppointment?.service?.description}</div><%}%>
		</td>

		<%
			def dayNineAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayNine.getTime() >= startDate.getTime() && dayNine.getTime() < endDate.getTime()){
					dayNineAppointment = it
				}
			}
			def dayNineDay = dayNine.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(dayNineAppointment,dayNineDay)}">
			<%if (dayNineAppointment){%><div id="appointment-${dayNineAppointment?.id}" class="appointmentDetailsCallOut">${dayNineAppointment?.service?.description}</div><%}%>
		</td>

		<%
			def dayTenAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayTen.getTime() >= startDate.getTime() && dayTen.getTime() < endDate.getTime()){
					dayTenAppointment = it
				}
			}
			def dayTenDay = dayTen.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(dayTenAppointment,dayTenDay)}">
			<%if (dayTenAppointment){%><div id="appointment-${dayTenAppointment?.id}" class="appointmentDetailsCallOut">${dayTenAppointment?.service?.description}</div><%}%>
		</td>

		<%
			def dayElevenAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayEleven.getTime() >= startDate.getTime() && dayEleven.getTime() < endDate.getTime()){
					dayElevenAppointment = it
				}
			}
			def dayElevenDay = dayEleven.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(dayElevenAppointment,dayElevenDay)}">
			<%if (dayElevenAppointment){%><div id="appointment-${dayElevenAppointment?.id}" class="appointmentDetailsCallOut">${dayElevenAppointment?.service?.description}</div><%}%>
		</td>

		<%
			def dayTwelveAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayTwelve.getTime() >= startDate.getTime() && dayTwelve.getTime() < endDate.getTime()){
					dayTwelveAppointment = it
				}
			}
			def dayTwelveDay = dayTwelve.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(dayTwelveAppointment,dayTwelveDay)}">
			<%if (dayTwelveAppointment){%><div id="appointment-${dayTwelveAppointment?.id}" class="appointmentDetailsCallOut">${dayTwelveAppointment?.service?.description}</div><%}%>
		</td>

		<%
			def dayThirteenAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayThirteen.getTime() >= startDate.getTime() && dayThirteen.getTime() < endDate.getTime()){
					dayThirteenAppointment = it
				}
			}
			def dayThirteenDay = dayThirteen.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(dayThirteenAppointment,dayThirteenDay)}">
			<%if (dayThirteenAppointment){%><div id="appointment-${dayThirteenAppointment?.id}" class="appointmentDetailsCallOut">${dayThirteenAppointment?.service?.description}</div><%}%>
		</td>

		<%
			def dayFourteenAppointment
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayFourteen.getTime() >= startDate.getTime() && dayFourteen.getTime() < endDate.getTime()){
					dayFourteenAppointment = it
				}
			}
			def dayFourteenDay = dayFourteen.get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(dayFourteenAppointment,dayFourteenDay)}">
			<%if (dayFourteenAppointment){%><div id="appointment-${dayFourteenAppointment?.id}" class="appointmentDetailsCallOut">${dayFourteenAppointment?.service?.description}</div><%}%>
		</td>
		

		<td rowspan="2" class="time">${dayEight.getTime().format('hh:mm a')}</td>
	</tr>

	<%dayEight.add(Calendar.MINUTE, 15)%>
	<%dayNine.add(Calendar.MINUTE, 15)%>
	<%dayTen.add(Calendar.MINUTE, 15)%>
	<%dayEleven.add(Calendar.MINUTE, 15)%>
	<%dayTwelve.add(Calendar.MINUTE, 15)%>
	<%dayThirteen.add(Calendar.MINUTE, 15)%>
	<%dayFourteen.add(Calendar.MINUTE, 15)%>

	<tr class="fifteen" time="${dayEight.getTime().format('hh:mm a')}">
		<%
			dayEightAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayEight.getTime() >= startDate.getTime() && dayEight.getTime() < endDate.getTime()){
					dayEightAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(dayEightAppointment,dayEightDay)}">
			<%if (dayEightAppointment){%><div id="appointment-${dayEightAppointment?.id}" class="appointmentDetailsCallOut">${dayEightAppointment?.service?.description}</div><%}%>
		</td>

		<%
			dayNineAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayNine.getTime() >= startDate.getTime() && dayNine.getTime() < endDate.getTime()){
					dayNineAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(dayNineAppointment,dayNineDay)}">
			<%if (dayNineAppointment){%><div id="appointment-${dayNineAppointment?.id}" class="appointmentDetailsCallOut">${dayNineAppointment?.service?.description}</div><%}%>
		</td>

		<%
			dayTenAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayTen.getTime() >= startDate.getTime() && dayTen.getTime() < endDate.getTime()){
					dayTenAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(dayTenAppointment,dayTenDay)}">
			<%if (dayTenAppointment){%><div id="appointment-${dayTenAppointment?.id}" class="appointmentDetailsCallOut">${dayTenAppointment?.service?.description}</div><%}%>
		</td>

		<%
			dayElevenAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayEleven.getTime() >= startDate.getTime() && dayEleven.getTime() < endDate.getTime()){
					dayElevenAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(dayElevenAppointment,dayElevenDay)}">
			<%if (dayElevenAppointment){%><div id="appointment-${dayElevenAppointment?.id}" class="appointmentDetailsCallOut">${dayElevenAppointment?.service?.description}</div><%}%>
		</td>

		<%
			dayTwelveAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayTwelve.getTime() >= startDate.getTime() && dayTwelve.getTime() < endDate.getTime()){
					dayTwelveAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(dayTwelveAppointment,dayTwelveDay)}">
			<%if (dayTwelveAppointment){%><div id="appointment-${dayTwelveAppointment?.id}" class="appointmentDetailsCallOut">${dayTwelveAppointment?.service?.description}</div><%}%>
		</td>

		<%
			dayThirteenAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayThirteen.getTime() >= startDate.getTime() && dayThirteen.getTime() < endDate.getTime()){
					dayThirteenAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(dayThirteenAppointment,dayThirteenDay)}">
			<%if (dayThirteenAppointment){%><div id="appointment-${dayThirteenAppointment?.id}" class="appointmentDetailsCallOut">${dayThirteenAppointment?.service?.description}</div><%}%>
		</td>

		<%
			dayFourteenAppointment = null
			appointments.each(){
				Calendar startDate = new GregorianCalendar()
				startDate.setTime(it.appointmentDate)
				Calendar endDate = new GregorianCalendar()
				endDate.setTime(it.appointmentDate)
				endDate.add(Calendar.MILLISECOND, new BigDecimal(it.service.duration).intValueExact())
				if (dayFourteen.getTime() >= startDate.getTime() && dayFourteen.getTime() < endDate.getTime()){
					dayFourteenAppointment = it
				}
			}
		%>
		<td class="${schedulerService.getCalendarClass(dayFourteenAppointment,dayFourteenDay)}">
			<%if (dayFourteenAppointment){%><div id="appointment-${dayFourteenAppointment?.id}" class="appointmentDetailsCallOut">${dayFourteenAppointment?.service?.description}</div><%}%>
		</td>
	</tr>
	<%dayEight.add(Calendar.MINUTE, 15)%>
	<%dayNine.add(Calendar.MINUTE, 15)%>
	<%dayTen.add(Calendar.MINUTE, 15)%>
	<%dayEleven.add(Calendar.MINUTE, 15)%>
	<%dayTwelve.add(Calendar.MINUTE, 15)%>
	<%dayThirteen.add(Calendar.MINUTE, 15)%>
	<%dayFourteen.add(Calendar.MINUTE, 15)%>
<%}%>
</table>

<br />
<br />
<g:render template="upcomingAppointments" />