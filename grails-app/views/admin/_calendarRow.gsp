<!-- DETERMINE IF BEGGINING OF APPOINTMENT, FIND OUT HOW LONG APPOINTMENT IS -->
<!-- ADD ROWSPAN, & DON'T DRAW ROW IF NOT BEGINNING OF APPOINTMENT -->

<tr class="${rowClass}" time="${days[0].getTime().format('hh:mm a')}">
	<g:if test="${rowClass == 'halfHour'}">
		<td rowspan="2" class="time">${days[0].getTime().format('hh:mm a')}</td>
	</g:if>
	<g:each in="${daysRange}" var="i"> 
		<%
			def appointment = schedulerService.findAppointment(appointments, days[i])
			def dayIndex = days[i].get(Calendar.DAY_OF_WEEK)
		%>
		<td class="${schedulerService.getCalendarClass(appointment,dayIndex)}">
			<%if (appointment){%><div id="appointment-${appointment?.id}" class="appointmentDetailsCallOut">${appointment?.service?.description}</div><%}%>
		</td>
	</g:each>
	<g:if test="${rowClass == 'halfHour'}">
		<td rowspan="2" class="time">${days[0].getTime().format('hh:mm a')}</td>
	</g:if>
</tr>