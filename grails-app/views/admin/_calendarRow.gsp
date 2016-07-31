<tr class="${rowClass}" time="${days[0].getTime().format('hh:mm a')}">
	<g:if test="${rowClass == 'halfHour'}">
		<td rowspan="2" class="time">${days[0].getTime().format('hh:mm a')}</td>
	</g:if>
	<g:each in="${0..6}" var="i"> 
		<%
			def dayOfWeek = days[i]
			def appointment = schedulerService.findAppointment(appointments, dayOfWeek)
			def javaDayOfWeekIndex = dayOfWeek.get(Calendar.DAY_OF_WEEK)
			def calendarClass = schedulerService.getCalendarClass(appointment, javaDayOfWeekIndex)
			def columnRowspanCount = 1
			def isBeginningOfAppointment = true
			if (appointment){
				columnRowspanCount = schedulerService.getCalendarColumnRowspanCount(appointment)
				isBeginningOfAppointment = schedulerService.isBeginningOfAppointment(appointment, dayOfWeek)
			}
		%>
		<g:if test="${!appointment}">
			<td class="${calendarClass}">
				<g:if test="${calendarClass.toUpperCase() != 'UNAVAILABLE'}">
					<div class="editable-cell" data-toggle="modal" data-target="#scheduleAppointmentModal">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					</div>
				</g:if>
			</td>
		</g:if>
		<g:elseif test="${isBeginningOfAppointment}">
			<td class="${calendarClass}" style="background-color:${appointment.service.calendarColor};" rowspan="${columnRowspanCount}">
				<h6>
					<g:if test="${!appointment.service?.description?.toUpperCase()?.contains('BLOCKED')}">
						${appointment.client.fullName}
					</g:if>
				</h6>
				<div class="editable-cell" id="appointment-${appointment?.id}" data-toggle="modal" data-target="#appointmentDetailsModal" onclick="getRescheduleOptions(${appointment.id});">
					<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
				</div>
			</td>
		</g:elseif>
	</g:each>
	<g:if test="${rowClass == 'halfHour'}">
		<td rowspan="2" class="time">${days[0].getTime().format('hh:mm a')}</td>
	</g:if>
</tr>