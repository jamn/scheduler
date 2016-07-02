<%
	def days = []
	for ( i in 0..13 ){
		Calendar cal = new GregorianCalendar()
		cal.setTime(startTime.getTime())
		if (i > 0) cal.add(Calendar.DAY_OF_WEEK, i)
		days[i] = cal
	}
%>
<g:set var="schedulerService" bean="schedulerService"/>
<table id="fourteenDayView-week1">
	<tr class="dateHeader">
		<td></td>
		<g:each in="${(0..6)}" var="i"> 
			<td>${days[i].getTime().format('EEE dd')}</td>
		</g:each>
		<td></td>
	</tr>
	<% while (days[0] < endTime){ %>
		<g:each in="${['halfHour','fifteen']}">
			<g:render template="calendarRow" model="['appointments':appointments, 'days':days, 'daysRange':(0..6), 'rowClass':it]" />
			<%for ( i in 0..6 ){
				days[i].add(Calendar.MINUTE, 15)
			}%>
		</g:each>
	<%}%>
</table>

<br />
<br />
<g:render template="upcomingAppointments" />