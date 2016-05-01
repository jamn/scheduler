<div class="row date-picker">
	<div class="col-xs-offset-2 col-xs-8 col-sm-offset-3 col-sm-6 choose-a-time">
		<h1 id="dateText">Today</h1>
		<label id="chooseDateText" for="chooseDate">Choose a date:</label>
		<input class="form-control" id="chooseDate" name="chooseDate" type="text">
		<%--<br />
		<div class="checkbox">
			<label>
				<input type="checkbox" name="recurringAppointment" id="recurringAppointmentCheckbox"> Recurring Appointment?
			</label>
		</div>
		<br />
		<div id="recurringAppointmentOptions">
			<ul>
				<li class="recurring-appointment-options">Repeat every</li>
				<li class="recurring-appointment-options">
					<select  class="form-control" id="repeatDuration">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
						<option value="6">6</option>
						<option value="7">7</option>
						<option value="8">8</option>
						<option value="9">9</option>
						<option value="10">10</option>
					</select>
				</li>
				<li class="recurring-appointment-options">
					week(s) for
				</li>
				<li class="recurring-appointment-options">
					<select  class="form-control" id="repeatNumberOfAppointments">
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
						<option value="5">5</option>
					</select>
				</li>
				<li class="recurring-appointment-options">
					appointments total.
				</li>
			</ul>
		</div>--%>
	</div>
</div>

<script type="text/javascript">
	var weekday=new Array(7)
	weekday[0]="Sunday"
	weekday[1]="Monday"
	weekday[2]="Tuesday"
	weekday[3]="Wednesday"
	weekday[4]="Thursday"
	weekday[5]="Friday"
	weekday[6]="Saturday"
	var selectedDate = new Date('${selectedDate.format("MM/dd/yyyy")}')
	$(document).ready(function(){
		$('#dateText').empty()
		var today = new Date()
		today.setHours(0,0,0,0)
		var dateText = (selectedDate.getTime() === today.getTime()) ? 'Today' : weekday[selectedDate.getDay()]
		$('#dateText').append(dateText).slideDown()
		$('#chooseDate').datepicker( {
			onSelect: function(date) {
				var date = $('#chooseDate').val();
				$("#mask").fadeIn();
				document.location = "?date="+encodeURIComponent(date)
			},
			minDate: 0,
			beforeShowDay: function(date) {
			 	var day = date.getDay();
			 	return [(day != 6 && day != 0)];
			}
		});
		$('#chooseDate').datepicker("setDate", selectedDate)
	})
</script>