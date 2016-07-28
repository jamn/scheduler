<html>
<head></head><body>
	<%
		def days = []
		for ( i in 0..6 ){
			Calendar cal = new GregorianCalendar()
			cal.setTime(startTime.getTime())
			cal.add(Calendar.DAY_OF_WEEK, i+startRange)
			days[i] = cal
		}
		endTime.add(Calendar.DAY_OF_WEEK, startRange)
	%>
	<g:set var="schedulerService" bean="schedulerService"/>

	<div class="row date-range-picker">
		<ul>
			<li>
				<form action="calendar">
					<label id="calendarStartDateLabel" for="calendarStartDate">Start Date:</label>
					<input class="form-control calendar-start-date" id="calendarStartDate" name="calendarStartDate" type="text" />
				</form>
			</li>
			<li>
				<ul>
					<li><span class="glyphicon glyphicon-chevron-left" aria-hidden="true" onclick="minusOneWeek();"></span></li>
					<li><h1>${startDate.format('MMMM')}</h1></li>
					<li><span class="glyphicon glyphicon-chevron-right" aria-hidden="true" onclick="plusOneWeek();"></span></li>
				</ul>
			</li>
		</ul>
		
	</div>

	<table id="calendarTable">
		<tr class="dateHeader">
			<td></td>
			<g:each in="${(0..6)}" var="i"> 
				<td>
					<span class="day">${days[i].getTime().format('EEE')}</span><br>
					${days[i].getTime().format('dd')}
				</td>
			</g:each>
			<td></td>
		</tr>
		<% while (days[0] < endTime){ %>
			<g:each in="${['halfHour','fifteen']}">
				<g:render template="calendarRow" model="['appointments':appointments, 'days':days, 'rowClass':it]" />
				<%for ( i in 0..6 ){
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


	<script type="text/javascript">
		var selectedDate = new Date('${startDate}')
		$(document).ready(function(){
			$('#calendarStartDate').datepicker( {
				showButtonPanel: true,
				onSelect: function(date) {
					$("#mask").fadeIn();
					document.location = "?startDate="+encodeURIComponent(date)
				}
			});
			$('#calendarStartDate').datepicker("setDate", selectedDate)
		})
		function plusOneWeek(){
			$("#mask").fadeIn()
			var nextWeek = selectedDate.setDate(selectedDate.getDate()+7)
			document.location = "?startDate="+encodeURIComponent(dateFormat(nextWeek, "mm/dd/yyyy"))
		}
		function minusOneWeek(){
			$("#mask").fadeIn()
			var lastWeek = selectedDate.setDate(selectedDate.getDate()-7)
			document.location = "?startDate="+encodeURIComponent(dateFormat(lastWeek, "mm/dd/yyyy"))
		}
		// hack to make "today" button in calendar actually select today's date
		$.datepicker._gotoToday = function(id) {
			var target = $(id);
			var inst = this._getInst(target[0]);
			if (this._get(inst, 'gotoCurrent') && inst.currentDay) {
				inst.selectedDay = inst.currentDay;
				inst.drawMonth = inst.selectedMonth = inst.currentMonth;
				inst.drawYear = inst.selectedYear = inst.currentYear;
			}
			else {
				var date = new Date();
				inst.selectedDay = date.getDate();
				inst.drawMonth = inst.selectedMonth = date.getMonth();
				inst.drawYear = inst.selectedYear = date.getFullYear();
				// the below two lines are new
				this._setDateDatepicker(target, date);
				this._selectDate(id, this._getDateDatepicker(target));
			}
			this._notifyChange(inst);
			this._adjustDate(target);
		}
	</script>

</body></html>	