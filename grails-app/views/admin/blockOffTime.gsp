<html>
<head></head><body>
	<div class="block-off-time">


		<h1>Block Off Time</h1>
		<form method="post" action="saveBlockedTime">
			<label for="dateToBlock">Choose a date:</label>
			<br />
			<input class="form-control" name="dateToBlock" id="dateToBlock" type="text" class="date">
			<br />
			<br />
			<label for="fromHour">From:</label>
			<br />
			<select class="form-control" name="fromHour">
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
			<select class="form-control" name="fromMinute">
				<option value="00">00</option>
				<option value="15">15</option>
				<option value="30">30</option>
				<option value="45">45</option>
			</select>
			<select class="form-control" name="fromMorningOrAfternoon">
				<option value="am">AM</option>
				<option value="pm">PM</option>
			</select>
			<br />
			<br />
			<label for="toHour">To:</label>
			<br />
			<select class="form-control" name="toHour">
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
			<select class="form-control" name="toMinute">
				<option value="00">00</option>
				<option value="15">15</option>
				<option value="30">30</option>
				<option value="45">45</option>
			</select>
			<select class="form-control" name="toMorningOrAfternoon">
				<option value="am">AM</option>
				<option value="pm">PM</option>
			</select>
			<br />
			<br />
			<button type="submit" class="btn green-button">Block Off Time</button>
		</form>


		<hr />
		<h1>Block Off Whole Day</h1>
		<form method="post" action="blockOffWholeDay">
			<label id="fromText" for="fromWholeDay">&nbsp;&nbsp;&nbsp;From:</label>
			<input class="form-control date" id="fromWholeDay" name="fromWholeDay" type="text">

			<label id="toText" for="toWholeDay">&nbsp;&nbsp;&nbsp;To:</label>
			<input class="form-control date" id="toWholeDay" name="toWholeDay" type="text">

			<button type="submit" class="btn green-button">Block Off Days</button>
		</form>


		<hr />
		<h1>Blocked Timeslots</h1>
		<form method="post" action="clearBlockedTime">
			<table class="blocked-timeslots-table">
				<thead>
					<tr>
						<td></td>
						<td style="padding-left:10px;">Date/Time:</td>
					</tr>
				</thead>
				<tbody>
					<g:each in="${blockedOffTimes}">
						<tr class="blocked-time" id="blockedTime${it.id}">
							<td style="text-align:center;"><input class="form-control" type="checkbox" name="blockedOffTime" value="${it.id}" /></td>
							<td>${it.appointmentDate.format('MMM dd, yyyy | hh:mm a (E)')}</td>
						</tr>
					</g:each>
				</tbody>
			</table>
			<button type="submit" class="btn green-button">Delete Blocked Timeslots</button>
		</form>
	</div>


<script type="text/javascript">


	$(document).on('click', '.btn', function() {
		showMask()
	});
	
	$('#dateToBlock').datepicker( {
		minDate: 0
	});

	$('#fromWholeDay').datepicker( {
		minDate: 0
	});

	$('#toWholeDay').datepicker( {
		minDate: 0
	});
</script>

</body></html>