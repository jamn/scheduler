<html>
<head></head><body>
	
	<g:set var="today" value="${new Date()}"/>

	<div class="col-xs-12 col-sm-offset-3 col-sm-6">
		<h1 class="handwriting">History</h1>

		<hr>
		<div class="table-responsive">
			<table class="table history-table">
				<g:each in="${appointments}" var="appointment">
					<tr>
						<td>
							<g:if test="${appointment.appointmentDate >= today}">
								<a href="#" style="float:left;"><span class="glyphicon glyphicon-remove" aria-hidden="true" style="top:2px;"></span></a>
							</g:if>
						</td>
						<td>
							<strong>${appointment.appointmentDate.format('MM/dd/yyyy @ hh:mm a')}</strong> :: ${appointment.service.description}
						</td>
					</tr>
				</g:each>
			</table>
		</div>
	</div>


</body></html>