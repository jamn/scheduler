<g:if test="${timeSlots}">
	<option selected="selected">Choose a timeslot...</option>
	<g:each in="${timeSlots}">
		<%def slash = it?.timeSlot?.indexOf('/')%>
		<option value="${it?.timeSlot.substring(0,slash-1)}">${it?.timeSlot}</option>
	</g:each>
</g:if>
<g:else>
	<option class="no-timeslots-available">No timeslots available</option>
</g:else>