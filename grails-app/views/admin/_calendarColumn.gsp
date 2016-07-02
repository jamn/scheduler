<%
	def appointment = schedulerService.findAppointment(appointments, dayOfWeek)
	def dayIndex = dayOfWeek.get(Calendar.DAY_OF_WEEK)
%>
<td class="${schedulerService.getCalendarClass(appointment,dayIndex)}">
	<%if (appointment){%><div id="appointment-${appointment?.id}" class="appointmentDetailsCallOut">${appointment?.service?.description}</div><%}%>
</td>