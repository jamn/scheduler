<html>
<head></head><body>
	
	<g:set var="today" value="${new Date()}"/>

	<div class="col-xs-12">
		<h1 class="handwriting">History</h1>
	</div>
	<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6">
		<hr>
		<div class="padded-box">
			<g:if test="${appointments.size() > 0}">
				<div class="table-responsive">
					<table class="table history-table">
						<g:each in="${appointments}" var="appointment">
							<tr>
								<td>
									<g:if test="${appointment.appointmentDate >= today}">
										<button type="button" class="btn btn-danger" title="Cancel this appointment" data-toggle="modal" data-target="#cancelAppointmentModal" data-appointmentCode="${appointment.code}">Cancel</button>
										<button type="button" class="btn btn-success" title="Reschedule this appointment" onclick="${createLink(controller:'book', action:'modifyAppointment')}">Reschedule</button>
									</g:if>
									<g:else>
										<button type="button" class="btn btn-default" disabled="disabled">Cancel</button>
										<button type="button" class="btn btn-default" disabled="disabled">Reschedule</button>
									</g:else>
								</td>
								<td>
									<strong>${appointment.appointmentDate.format('MM/dd/yyyy @ hh:mm a')}</strong> :: ${appointment.service.description}
								</td>
							</tr>
						</g:each>
					</table>
				</div>
				<div class="modal fade" id="cancelAppointmentModal" tabindex="-1" role="dialog" aria-labelledby="cancelAppointmentModalLabel">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-body">
								<h1>Cancel this appointment?</h1>
								<h1>
									<button type="button" class="btn btn-default" data-dismiss="modal">No</button>
									<button type="button" class="btn btn-primary cancel-this-appointment">Yes</button>
								</h1>
							</div>
						</div>
					</div>
				</div>
			</g:if>
			<g:else>
				<p class="no-appointments">You haven't booked any appointments yet. <a href="${createLink(controller:'book', action:'chooseService')}">Book now</a>?</p>
			</g:else>
		</div>
	</div>

</body></html>