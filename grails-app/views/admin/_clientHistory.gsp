<%if (appointments.size() > 0){%>
	<table>
		<thead>
			<tr>
				<td style="width:206px;">Date:</td>
				<td>Service:</td>
			</tr>
		</thead>
		<tbody>
			<g:each in="${appointments}" var="appointment">
				<tr class="${(appointment.deleted) ? 'cancelled-appointment' : ''}">
					<td>${appointment.appointmentDate.format('MM/dd/yy - hh:mma')}</td>
					<td>${appointment.service.description}</td>
				</tr>
			</g:each>
		</tbody>
	</table>
<%}else{%>
	<p> No past appointments found.</p>
<%}%>