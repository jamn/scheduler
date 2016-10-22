<html>
<head></head><body>

	<div class="confirm-appointment-cancellation">
		<g:if test="${session?.appointmentToDelete}">
			<h2>Confirm Cancellation</h2>
			<small>${session.appointmentToDelete.service.description}: ${session.appointmentToDelete.appointmentDate.format('EEEE, MMMM dd @ hh:mm a')}</small>
			<br />
			<button type="button" class="btn btn-success" onclick="window.location='./confirmAppointmentCancellation'">Yes</button>
			<button type="button" class="btn btn-danger" onclick="window.location='./cancelAttemptToCancelAppointment'">No</button>
		</g:if>
		<g:else>
			<p>This appointment has already been deleted.</p>
		</g:else>
	</div>

</body></html>