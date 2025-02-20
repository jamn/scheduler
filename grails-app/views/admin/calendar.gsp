<html>
<head></head><body>

	<g:set var="schedulerService" bean="schedulerService"/>

	<div class="calendar">
		<div class="row date-range-picker">
			<div class="col-xs-4 col-sm-6 today" onclick="today();">
				Today
			</div>
			<div class="col-xs-8 col-sm-6 date-selector">
					<div class="col-xs-3 arrow" onclick="minusOneWeek();"><span class="glyphicon glyphicon-chevron-left" aria-hidden="true"></span></div>
					<div class="col-xs-6 calendar-form">
						<form action="calendar">
							<label id="calendarStartDateLabel" for="calendarStartDate">Start Date:</label>
							<input class="form-control calendar-start-date" id="calendarStartDate" name="calendarStartDate" type="text" />
						</form>
					</div>
					<div class="col-xs-3 arrow arrow-right" onclick="plusOneWeek();"><span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span></div>
			</div>
		</div>

		<table id="calendarTable" class="calendar-table">
			<tr class="dateHeader">
				<td></td>
				<g:each in="${(0..6)}" var="i"> 
					<td>
						<span class="day">${days[i].toString('EEE')}</span><br>
						${days[i].toString('dd')}
					</td>
				</g:each>
				<td></td>
			</tr>
			<% for (i=0; i<numberOfRows; i++){ %>
				<g:each in="${['halfHour','fifteen']}">
					<g:render template="calendarRow" model="['rowClass':it]" />
					<%for ( j in 0..6 ){
						days[j] = days[j].plusMinutes(15)
					}%>
				</g:each>
			<%}%>
		</table>

		<hr>

		<div class="calendar-key">
			<ul>
				<g:each in="${services}" var="service">
					<li>
						<div class="color-swatch" style="background-color:${service.calendarColor};"></div> ${service.description}
					</li>
				</g:each>
				<li>
					<div class="color-swatch" style="background-color:#505050;"></div> Blocked
				</li>
				<li>
					<div class="color-swatch unavailable" style="background-color:#929292;"></div> Unavailable
				</li>
			</ul>
		</div>
	</div>


	<div class="modal fade" id="appointmentDetailsModal" tabindex="-1" role="dialog" aria-labelledby="appointmentDetailsModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content" id="edit-appointment-options"></div>
		</div>
	</div>

	<div class="modal fade" id="scheduleAppointmentModal" tabindex="-1" role="dialog" aria-labelledby="scheduleAppointmentModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="rescheduleAppointmentModalLabel">Book Appointment</h4>
				</div>
				<div class="modal-body">
					<div id="scheduleAppointmentForm"></div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="clearBlockedTimeModal" tabindex="-1" role="dialog" aria-labelledby="clearBlockedTimeModalLabel" aria-hidden="true">
		<div class="modal-dialog" role="document">
			<div class="modal-content">
				<div class="modal-body">
					<h1>Clear blocked time?</h1>
					<h1>
						<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
						<button type="button" class="btn btn-primary cancel-this-appointment">Yes</button>
					</h1>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade" id="addClientModal" tabindex="-1" role="dialog" aria-labelledby="addClientModalLabel" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
					<h4 class="modal-title" id="addClientModalLabel">Add Client</h4>
				</div>
				<div class="modal-body">
					<div id="clientInfoForm"></div>
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
		function today(){
			$("#mask").fadeIn()
			var today = new Date()
			document.location = "?startDate="+encodeURIComponent(dateFormat(today, "mm/dd/yyyy"))
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