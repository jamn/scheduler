	<h1>Block Off Time</h1>
	<label id="chooseDateToBlockOffText" for="chooseDateToBlockOff">Choose a date:</label>
	<br />
	<input id="chooseDateToBlockOff" name="chooseDateToBlockOff" type="text" class="date">
	<br />
	<br />
	<label id="fromText" for="from">&nbsp;&nbsp;&nbsp;From:</label>
	<br />
	<select id="fromHour">
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
		<option value="11">11</option>
		<option value="12">12</option>
	</select>
	:
	<select id="fromMinute">
		<option value="00">00</option>
		<option value="15">15</option>
		<option value="30">30</option>
		<option value="45">45</option>
	</select>
	<select id="fromMorningOrAfternoon">
		<option value="am">AM</option>
		<option value="pm">PM</option>
	</select>
	<br />
	<br />
	<label id="toText" for="to">&nbsp;&nbsp;&nbsp;To:</label>
	<br />
	<select id="toHour">
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
		<option value="11">11</option>
		<option value="12">12</option>
	</select>
	:
	<select id="toMinute">
		<option value="00">00</option>
		<option value="15">15</option>
		<option value="30">30</option>
		<option value="45">45</option>
	</select>
	<select id="toMorningOrAfternoon">
		<option value="am">AM</option>
		<option value="pm">PM</option>
	</select>
	<br />
	<br />
	<div id="blockOffTimeButton" class="btn green-button">Block Off Time</div>

	<hr />

	<h1>Block Off Whole Day</h1>
	<label id="fromText" for="fromWholeDay">&nbsp;&nbsp;&nbsp;From:</label> <br />
	<input id="fromWholeDay" name="fromWholeDay" type="text" class="date"><br />

	<label id="toText" for="toWholeDay">&nbsp;&nbsp;&nbsp;To:</label> <br />
	<input id="toWholeDay" name="toWholeDay" type="text" class="date">

	<div id="blockOffDaysButton" class="btn green-button">Block Off Days</div>



	<hr />

	<h1>Blocked Timeslots</h1>
	<form id="blockedTimesForm">
		<table class="blocked-timeslots-table">
			<thead>
				<tr>
					<td></td>
					<td>Date/Time:</td>
				</tr>
			</thead>
			<tbody>
				<g:each in="${blockedOffTimes}">
					<tr class="blocked-time" id="blockedTime${it.id}">
						<td style="text-align:center;"><input type="checkbox" name="blockedOffTime" value="${it.id}" /></td>
						<td>${it.appointmentDate.format('MMM dd, yyyy | hh:mm a (E)')}</td>
					</tr>
				</g:each>
			</tbody>
		</table>

		<div id="deleteBlockedTimeslotsButton" class="btn green-button">Delete Blocked Timeslots</div>
	</form>


<script type="text/javascript">
	
	$('#chooseDateToBlockOff').datepicker( {
		minDate: 0
	});

	$('#fromWholeDay').datepicker( {
		minDate: 0
	});

	$('#toWholeDay').datepicker( {
		minDate: 0
	});
</script>
	