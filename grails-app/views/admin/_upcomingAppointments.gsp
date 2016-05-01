<h1>Upcoming Appointments</h1>
<hr />
<div class="row">
	<table class="appointments">
		<g:each in="${appointments}">
			<%if (it.service.description != 'Blocked Off Time'){%>
				<tr class="appointment-data appointment-data-${it.id}" id="${it.id}">
					<td<%if(it.client.isNewUser()){%> class="new-user"<%}%>>
						<div class="col-xs-4">
							<h2>${it.client.fullName}</h2>
							<h4><b>${it.service.description}:</b> ${it.appointmentDate.format('MM/dd @ hh:mm a [E]')}</h4>
						</div>
						<div class="col-xs-8 edit-appointment edit-appointment-${it.id}">
							<div class="col-xs-4 center">
								<button type="button" class="btn white-button" data-toggle="modal" data-target="#rescheduleAppointmentModal" onclick="getRescheduleOptions(${it.id});"><span class="glyphicon glyphicon-refresh" aria-hidden="true"></span><span class="glyphicon-class">Reschedule</span></button>
							</div>
							<div class="col-xs-4 center">
								<button type="button" class="btn white-button" data-toggle="modal" data-target="#clientDetailsModal" data-name="${it.client.fullName}" data-email="${it.client.email}" data-phone="${it.client.phone}" data-id="${it.client.id}"><span class="glyphicon glyphicon-user" aria-hidden="true"></span><span class="glyphicon-class">Contact</span></button>
							</div>
							<div class="col-xs-4 center">
								<button type="button" c="${it.code}" class="btn white-button error-button cancelAppointmentButton"><span class="glyphicon glyphicon-remove" aria-hidden="true"></span><span class="glyphicon-class">Cancel</span></button>
							</div>
						</div>
					</td>
				</tr>
			<%}%>
		</g:each>
	</table>
</div>


<div class="modal fade" id="clientDetailsModal" tabindex="-1" role="dialog" aria-labelledby="client-name" aria-hidden="true">
	<div class="modal-dialog">
		<div class="modal-content">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
				<h4 class="modal-title client-name"></h4>
			</div>
			<div class="modal-body">
				<h1>Phone</h1>
				<div class="client-phone"></div>
				<h1>Send Email</h1>
				<form id="emailClientForm">
					<input type="hidden" id="clientEmail">
					<div class="form-group">
						<label for="recipient-name" class="control-label">To:</label>
						<input type="text" class="form-control recipient" id="recipient-name" disabled>
					</div>
					<div class="form-group">
						<label for="message-text" class="control-label">Message:</label>
						<textarea class="form-control" id="messageText"></textarea>
					</div>
					<button id="emailClientButton" type="button" class="btn green-button">Send message</button>
				</form>
				<h1>History</h1>
				<div class="client-history"></div>
			</div>
		</div>
	</div>
</div>







<div class="modal fade" id="rescheduleAppointmentModal" tabindex="-1" role="dialog" aria-labelledby="rescheduleAppointmentModalLabel" aria-hidden="true">
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
	var baseUrl = $('body').attr('baseUrl');

	$('#clientDetailsModal').on('show.bs.modal', function (event) {
		var button = $(event.relatedTarget);
		var clientName = button.data('name');
		var clientEmail = button.data('email');
		var clientPhone = button.data('phone');
		var modal = $(this);
		modal.find('.modal-header .client-name').html(clientName);
		modal.find('.modal-body input').val(clientName + " (" + clientEmail + ")");
		modal.find('.modal-body #clientEmail').val(clientEmail);
		modal.find('.modal-body .client-phone').html("<a href='tel:"+clientPhone+"'>"+clientPhone+"</a>");
		var cId = button.data('id');
		$.ajax({
			type: "POST",
			url: baseUrl + "/getClientHistory",
			data: {cId:cId}
		}).done(function(response) {
			modal.find('.modal-body .client-history').html(response);
		});
	});

	$('#emailClientButton').on('tap', function() {
		var e = $('.modal-body #clientEmail').val();
		var m = $('.modal-body #messageText').val();
		$('#emailClientButton').html($('.spinner').html());
		$.ajax({
			type: "POST",
			url: baseUrl + "/emailClient",
			data: {m:m, e:e}
		}).done(function(response) {
			var jsonResponse = JSON.parse(response);
			if (jsonResponse.success === true){
				$('#emailClientButton').html('Success');
				$('#emailClientButton').removeClass('error-button');
				$('#emailClientButton').attr("disabled", "disabled");
				setTimeout(function() {$('#clientDetailsModal').modal('toggle');},400);
			}else{
				$('#emailClientButton').html('Error');
				$('#emailClientButton').addClass('error-button');
			}
		});
	});


	$('.cancelAppointmentButton').confirmOn({
		classPrepend: 'confirmon',
		questionText: 'Cancel Appointment?',
		textYes: 'Yes',
		textNo: 'No'
	},'click', function(e, confirmed){
		if(confirmed){
			var c = $(e.currentTarget).attr('c');
			$.ajax({
				type: "POST",
				url: baseUrl + "/cancelAppointment",
				data: { c: c }
			}).done(function(response) {
				var success = response.search('"success":false');
				if (success === -1){
					alert('*Appointment Deleted*');
					location.reload();
				}
				else{
					alert('That didn\'t work. Dang.');
				}
			});
		}
	});
</script>