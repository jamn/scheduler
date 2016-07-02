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
	<tr class="halfHour" time="${days[0].getTime().format('hh:mm a')}">
		<td rowspan="2" class="time">${days[0].getTime().format('hh:mm a')}</td>
		<g:each in="${(0..6)}" var="i"> 
			<%
				def appointment = schedulerService.findAppointment(appointments, days[i])
				def dayIndex = days[i].get(Calendar.DAY_OF_WEEK)
			%>
			<td class="${schedulerService.getCalendarClass(appointment,dayIndex)}">
				<%if (appointment){%><div id="appointment-${appointment?.id}" class="appointmentDetailsCallOut">${appointment?.service?.description}</div><%}%>
			</td>
		</g:each>
		<td rowspan="2" class="time">${days[0].getTime().format('hh:mm a')}</td>
	</tr>
	<%
		for ( i in 0..6 ){
			days[i].add(Calendar.MINUTE, 15)
		}
	%>
	<tr class="fifteen" time="${days[0].getTime().format('hh:mm a')}">
		<g:each in="${(0..6)}" var="i"> 
			<%
				def appointment = schedulerService.findAppointment(appointments, days[i])
				def dayIndex = days[i].get(Calendar.DAY_OF_WEEK)
			%>
			<td class="${schedulerService.getCalendarClass(appointment,dayIndex)}">
				<%if (appointment){%><div id="appointment-${appointment?.id}" class="appointmentDetailsCallOut">${appointment?.service?.description}</div><%}%>
			</td>
		</g:each>
	</tr>
	<%
		for ( i in 0..6 ){
			days[i].add(Calendar.MINUTE, 15)
		}
	%>
<%}%>
</table>


<table id="fourteenDayView-week2">
<tr class="dateHeader">
	<td></td>
	<g:each in="${(7..13)}" var="i"> 
		<td>${days[i].getTime().format('EEE dd')}</td>
	</g:each>
	<td></td>
</tr>
<% 
	Calendar day8EndTime = new GregorianCalendar()
	day8EndTime.setTime(endTime.getTime())
	day8EndTime.add(Calendar.DAY_OF_WEEK, 7)
%>
<% while (days[7] < day8EndTime) {%>
	<tr class="halfHour" time="${days[0].getTime().format('hh:mm a')}">
		<td rowspan="2" class="time">${days[0].getTime().format('hh:mm a')}</td>
		<g:each in="${(7..13)}" var="i"> 
			<%
				def appointment = schedulerService.findAppointment(appointments, days[i])
				def dayIndex = days[i].get(Calendar.DAY_OF_WEEK)
			%>
			<td class="${schedulerService.getCalendarClass(appointment,dayIndex)}">
				<%if (appointment){%><div id="appointment-${appointment?.id}" class="appointmentDetailsCallOut">${appointment?.service?.description}</div><%}%>
			</td>
		</g:each>

		<td rowspan="2" class="time">${days[0].getTime().format('hh:mm a')}</td>
	</tr>
	<%
		for ( i in 7..13 ){
			days[i].add(Calendar.MINUTE, 15)
		}
	%>
	<tr class="fifteen" time="${days[0].getTime().format('hh:mm a')}">
		<g:each in="${(7..13)}" var="i"> 
			<%
				def appointment = schedulerService.findAppointment(appointments, days[i])
				def dayIndex = days[i].get(Calendar.DAY_OF_WEEK)
			%>
			<td class="${schedulerService.getCalendarClass(appointment,dayIndex)}">
				<%if (appointment){%><div id="appointment-${appointment?.id}" class="appointmentDetailsCallOut">${appointment?.service?.description}</div><%}%>
			</td>
		</g:each>
	</tr>
	<%
		for ( i in 7..13 ){
			days[i].add(Calendar.MINUTE, 15)
		}
	%>
<%}%>
</table>

<br />
<br />
<g:render template="upcomingAppointments" />