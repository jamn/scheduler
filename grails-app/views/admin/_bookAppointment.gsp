<div class="row book-for-client">
	<h1>Book for Client</h1>
	<select class="form-control" id="clients">
		<option selected="selected">Choose a client...</option>
		<g:each in="${clients}" var="client" status="i">
			<g:set var="currentLastNameStartsWith" value="${client.lastName.substring(0,1)}" />
			<g:if test="${(i == 0)}">
				<g:set var="previousLastNameStartedWith" value="${client.lastName.substring(0,1)}" />
				<optgroup label="${currentLastNameStartsWith}">
					<option value="${client.id}">${client.lastName}, ${client.firstName}</option>
			</g:if>
			<g:elseif test="${(i == clients.size())}">
				</optgroup>
			</g:elseif>
			<g:elseif test="${(currentLastNameStartsWith != previousLastNameStartedWith)}">
				</optgroup>
				<optgroup label="${currentLastNameStartsWith}">
					<option value="${client.id}">${client.lastName}, ${client.firstName}</option>
			</g:elseif>
			<g:else>
				<option value="${client.id}">${client.lastName}, ${client.firstName}</option>
			</g:else>

			<g:set var="previousLastNameStartedWith" value="${client.lastName.substring(0,1)}" />
		</g:each>
	</select>
	<select class="form-control" id="services">
		<option selected="selected">Choose a service...</option>
		<g:each in="${services}">
			<option value="${it?.id}">${it?.description}</option>
		</g:each>
	</select>
	<select class="form-control" id="timeSlots">
		<option class="no-timeslots-available">No timeslots available</option>
	</select>
	<label id="dateOfAppointmentLabel" for="dateOfAppointment">Date:</label>
	<input class="form-control date" id="dateOfAppointment" name="dateOfAppointment" type="text" />
	
	<label>Recurring Appointment?</label>
	<div id="recurringAppointmentAdmin">
		<ul style="margin-bottom:0;">
			<li><input type="checkbox" name="recurringAppointment" id="recurringAppointment" /></li>
			<li class="recurringAppointmentAdminOptions">Repeat every</li>
			<li class="recurringAppointmentAdminOptions">
				<select class="form-control" id="repeatDuration">
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
			<li class="recurringAppointmentAdminOptions">
				week(s) for
			</li>
			<li class="recurringAppointmentAdminOptions">
				<select class="form-control" id="repeatNumberOfAppointments">
					<option value="2">2</option>
					<option value="3">3</option>
					<option value="4">4</option>
					<option value="5">5</option>
				</select>
			</li>
			<li class="recurringAppointmentAdminOptions">
				appointments total.
			</li>
		</ul>
	</div>
	<div id="bookForClientButton" class="btn green-button">Book Appointment</div>
</div>
<script type="text/javascript">
	$(document).ready(function(){
		$(".recurringAppointmentAdminOptions").fadeOut();
	});

	$('#dateOfAppointment').datepicker( {
		minDate: 0
	});
	$('#dateOfAppointment').datepicker("setDate", new Date());
	$('#bookForClientButton').confirmOn({
		classPrepend: 'confirmon',
		questionText: 'Book for client?',
		textYes: 'Yes',
		textNo: 'No'
	},'click', function(e, confirmed){
		if(confirmed){
			var cId = $('#clients').val();
			var sId = $('#services').val();
			var aDate = $('#dateOfAppointment').val();
			var sTime = $('#timeSlots').val();
			var recurringAppointment = $('#recurringAppointment').is(':checked');
			var repeatDuration = $('#repeatDuration').val();
			var repeatNumberOfAppointments = $('#repeatNumberOfAppointments').val();
			var baseUrl = $('body').attr('baseUrl');

			$('#bookForClientButton').html($('#waitingSpinner').html());

			$.ajax({
				type: "POST",
				url: baseUrl+"/bookForClient",
				data: { cId:cId, sId:sId, aDate:aDate, sTime:sTime, r:recurringAppointment, dur:repeatDuration, num:repeatNumberOfAppointments}
			}).done(function(response) {
				var jsonResponse = JSON.parse(response);
				if (jsonResponse.success === true){
					$('#bookForClientButton').html("Success");
					$('#bookForClientButton').removeClass('error-button animated fadeIn');
				}
				else{
					$('#bookForClientButton').html("Error");
					$('#bookForClientButton').addClass('error-button animated fadeIn');
				}
			});
		}
});
</script>