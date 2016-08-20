<div class="modal-header">
	<button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
	<h4 class="modal-title" id="rescheduleAppointmentModalLabel">${appointment.client.fullName}</h4>
</div>
<div class="modal-body reschedule-options">
	<h2>${appointment.service.description}</h2>
	<h1>${appointment.appointmentDate.format('EEE, MMM d @ hh:mm a')}</h1>
	<br>

	<label style="width:36px;" id="dateOfRescheduledAppointmentLabel-${appointment.id}" for="dateOfRescheduledAppointment-${appointment.id}">Date:</label>
	<input id="dateOfRescheduledAppointment-${appointment.id}" date="${appointment.appointmentDate}" name="dateOfRescheduledAppointment-${appointment.id}" type="text" class="form-control date">

	<g:select id="servicesForRescheduledAppointment-${appointment.id}" name="servicesForRescheduledAppointment-${appointment.id}" from="${services}" value="${appointment.service.id}" optionKey="id" optionValue="description" class="form-control"></g:select>
	<select id="timeSlotsForRescheduledAppointment-${appointment.id}" class="form-control">
		<g:render template="timeSlotOptions"/>
	</select>

	<button type="button" class="btn btn-default reschedule-button" id="rescheduleButton-${appointment.id}">Reschedule</button>
	<button type="button" class="btn btn-default error-button cancelAppointmentButton" c="${appointment.code}">Cancel Appointment</button>
</div>

	

<script type="text/javascript">
	$('#dateOfRescheduledAppointment-'+${appointment.id}).datepicker( {
		minDate: 0,
		showButtonPanel: true,
		beforeShowDay: function(date) {
		 	var day = date.getDay();
		 	return [${availability}.indexOf(day) > -1];
		}
	});
	var date = $('#dateOfRescheduledAppointment-'+${appointment.id}).attr('date')

	$('#dateOfRescheduledAppointment-'+${appointment.id}).datepicker("setDate", new Date(date));
	$('#servicesForRescheduledAppointment-'+${appointment.id}).on('change', function() {
		getTimeSlotOptionsForRescheduledAppointment(${appointment.id});
	});
	$('#dateOfRescheduledAppointment-'+${appointment.id}).on('change', function() {
		getTimeSlotOptionsForRescheduledAppointment(${appointment.id});
	});
	$('#rescheduleButton-'+${appointment.id}).on('click', function() {
		rescheduleAppointment(${appointment.id});
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
				url: "/admin/cancelAppointment",
				data: { c: c }
			}).done(function(response) {
				var success = response.search('"success":false');
				if (success === -1){
					location.reload();
				}
				else{
					alert('That didn\'t work. Dang.');
				}
			});
		}
	});
</script>