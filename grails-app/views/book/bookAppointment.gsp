<html>
<head></head><body>

	<g:set var="formAction" value="${cancelAppointment ? 'Cancel' : 'Book'}" />
	<g:set var="buttonId" value="${cancelAppointment ? 'cancelAppointmentLoginButton' : 'loginButton'}" />

	<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6">
		<form method="post" class="login-box" action="confirmation" id="loginForm">

			<g:set var="plural" value="${session?.bookedAppointments?.size() > 1 ? 's' : ''}" />

			<div class="reminders">
				<g:if test="${!cancelAppointment && session?.bookedAppointments?.size() > 0}">
					<h2>Confirm ${session?.bookedAppointments[0]?.service?.description}${plural}:</h2>
					<ul>
						<g:each in='${session.bookedAppointments}'>
							<li>${it.appointmentDate.format('EEEE, MMMM dd @ hh:mm a')}</li>
						</g:each>
					</ul>
				</g:if>
				<g:if test="${session.appointmentToDelete}">
					<h2>Confirm Cancellation:</h2>
					<ul>
						<li> ${session.appointmentToDelete.service.description}: ${session.appointmentToDelete.appointmentDate.format('EEEE, MMMM dd @ hh:mm a')}</li>
					</ul>
				</g:if>
				<g:if test="${!cancelAppointment}">
					<label>
						<input type="checkbox" name="emailReminder" id="emailReminder" checked> Send email reminder${plural}?
					</label>
					<label>
						<input type="checkbox" name="textMessageReminder" id="textMessageReminder" checked> Send text message reminder${plural}?
					</label>
					<div class="reminders-note">Reminders are sent 24 hours before your appontment.</div>
				</g:if>

				<div class="no-show-policy">There will be a $20 charge at your following visit if you cancel within 4 hours of your appointment. Unless previous arrangements have been made, anything past 10 minutes late will be considered a no show and you will need to reschedule.</div>
			</div>

			<g:if test="${!session.user}">
				<g:render template="login" />
			</g:if>

			<input type="submit" class="btn green-button login-button" id="${buttonId}" value="${formAction} Appointment${plural}" onclick="showMask()" />
			

		</form>


	</div>

	<script type="text/javascript">
		<g:if test="${error}">
			$('.error-details').fadeIn();
		</g:if>
		$('#textMessageReminder').click(function() {
			togglePhoneNumber();
		});
	</script>

</body></html>