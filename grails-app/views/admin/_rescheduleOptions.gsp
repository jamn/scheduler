<label style="width:36px;" id="dateOfRescheduledAppointmentLabel-${appointment.id}" for="dateOfRescheduledAppointment-${appointment.id}">Date:</label>
<input id="dateOfRescheduledAppointment-${appointment.id}" name="dateOfRescheduledAppointment-${appointment.id}" type="text" class="form-control date">
<select id="servicesForRescheduledAppointment-${appointment.id}" class="form-control">
	<option selected="selected">Choose a service...</option>
	<g:each in="${services}">
		<option value="${it?.id}">${it?.description}</option>
	</g:each>
</select>
<select id="timeSlotsForRescheduledAppointment-${appointment.id}" class="form-control"></select>
<button type="button" class="btn green-button reschedule-button" id="rescheduleButton-${appointment.id}">Reschedule</button>