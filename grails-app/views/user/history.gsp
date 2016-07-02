<html>
<head></head><body>
	

	<div class="col-xs-12 col-sm-offset-3 col-sm-6">
		<h1 class="handwriting">History</h1>

		<hr>
		<div class="table-responsive">
			<table class="table history-table">
				<g:each in="${appointments}" var="appointment">
					<tr>
						<td>${appointment.appointmentDate.format('dd/MM/yyyy @ hh:mm a')} </td>
						<td>${appointment.service.description}</td>
					</tr>
				</g:each>
			</table>
		</div>
	</div>


</body></html>