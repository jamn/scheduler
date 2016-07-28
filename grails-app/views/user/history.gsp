<html>
<head></head><body>
	
	<g:set var="today" value="${new Date()}"/>

	<div class="col-xs-12">
		<h1 class="handwriting">History</h1>
	</div>
	<div class="col-xs-12 col-sm-offset-3 col-sm-6">
		<hr>
		<div class="padded-box">
			<g:if test="${appointments.size() > 0}">
				<div class="table-responsive">
					<table class="table history-table">
						<g:each in="${appointments}" var="appointment">
							<tr>
								<td>
									<g:if test="${appointment.appointmentDate >= today}">
										<button type="button" class="btn btn-danger">Cancel</button>
									</g:if>
									<g:else>
										<button type="button" class="btn btn-default" disabled="disabled">Cancel</button>
									</g:else>
								</td>
								<td>
									<strong>${appointment.appointmentDate.format('MM/dd/yyyy @ hh:mm a')}</strong> :: ${appointment.service.description}
								</td>
							</tr>
						</g:each>
					</table>
				</div>
			</g:if>
			<g:else>
				<p class="no-appointments">You haven't booked any appointments yet. <a href="${createLink(controller:'book', action:'chooseService')}">Book now</a>?</p>
			</g:else>
		</div>
	</div>


</body></html>