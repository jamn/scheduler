<tr class="${rowClass}" time="${days[0].toString('hh:mm a')}">
	<g:if test="${rowClass == 'halfHour'}">
		<td rowspan="2" class="time">${days[0].toString('hh:mm a')}</td>
	</g:if>
	<g:each in="${0..6}" var="i"> 
		<%
			def dayOfWeek = days[i]
			def appointment = schedulerService.findAppointment(appointments, dayOfWeek)
			def calendarClass = schedulerService.getCalendarClass(appointment, dayOfWeek, serviceProviderAvailability)
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
					<div class="editable-cell book-new-appointment" data-toggle="modal" data-target="#scheduleAppointmentModal" datetime="${dayOfWeek.toString('MM/dd/yyyyhh:mma')}">
						<span class="glyphicon glyphicon-plus" aria-hidden="true"></span>
					</div>
				</g:if>
			</td>
		</g:if>
		<g:elseif test="${isBeginningOfAppointment}">
			<td class="${calendarClass}" style="background-color:${appointment.service.calendarColor};" rowspan="${columnRowspanCount}">
				<g:if test="${appointment.isBlockedTime()}">
					<div class="editable-cell" id="appointment-${appointment?.id}" c="${appointment.code}" data-toggle="modal" data-target="#clearBlockedTimeModal">
						<span class="glyphicon glyphicon-remove" aria-hidden="true"></span>
					</div>
				</g:if>
				<g:else>
					<h6 class="large-screen">
						${appointment.client.fullName}
					</h6>
					<h6 class="small-screen">
						${raw(appointment.client.shortName)}
					</h6>
					<div class="editable-cell" id="appointment-${appointment?.id}" data-toggle="modal" data-target="#appointmentDetailsModal" onclick="getRescheduleOptions(${appointment.id});">
						<span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
					</div>
				</g:else>
				
			</td>
		</g:elseif>
	</g:each>
	<g:if test="${rowClass == 'halfHour'}">
		<td rowspan="2" class="time">${days[0].toString('hh:mm a')}</td>
	</g:if>
</tr>