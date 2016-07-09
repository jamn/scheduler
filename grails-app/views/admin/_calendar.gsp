<%
	def days = []
	def endRange = startRange + 6
	for ( i in startRange..endRange ){
		Calendar cal = new GregorianCalendar()
		cal.setTime(startTime.getTime())
		if (i > 0) cal.add(Calendar.DAY_OF_WEEK, i)
		days[i] = cal
	}
%>
<g:set var="schedulerService" bean="schedulerService"/>

<div class="row date-range-picker">
	<ul>
		<li>
			<form action="calendar">
				<label id="calendarStartDateLabel" for="calendarStartDate">Start Date:</label>
				<input class="form-control" id="calendarStartDate" name="calendarStartDate" type="text" />
			</form>
		</li>
		<li>
			<ul>
				<li><span class="glyphicon glyphicon-chevron-left" aria-hidden="true" onclick="minusOneWeek();"></span></li>
				<li><h1>July</h1></li>
				<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true" onclick="plusOneWeek();"></span></li>
			</ul>
		</li>
	</ul>
	
</div>

<table id="calendarTable">
	<tr class="dateHeader">
		<td></td>
		<g:each in="${(startRange..endRange)}" var="i"> 
			<td>
				<span class="day">${days[i].getTime().format('EEE')}</span><br>
				${days[i].getTime().format('dd')}
			</td>
		</g:each>
		<td></td>
	</tr>
	<% while (days[0] < endTime){ %>
		<g:each in="${['halfHour','fifteen']}">
			<g:render template="calendarRow" model="['appointments':appointments, 'days':days, 'daysRange':(startRange..endRange), 'rowClass':it]" />
			<%for ( i in startRange..startRange+6 ){
				days[i].add(Calendar.MINUTE, 15)
			}%>
		</g:each>
	<%}%>
</table>

<div class="modal fade" id="appointmentDetailsModal" tabindex="-1" role="dialog" aria-labelledby="appointmentDetailsModalLabel" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title" id="rescheduleAppointmentModalLabel">Reschedule Appointment</h4>
			</div>
			<div class="modal-body">
				<span id="edit-appointment-options"></span> <img src="${resource(dir:'images', file:'spinner-gray.gif')}" class="spinner" style="display:none;">
			</div>
		</div>
	</div>
</div>
