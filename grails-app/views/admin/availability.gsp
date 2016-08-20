<html>
<head></head><body>
	<div class="availability">
		<h1>Availability</h1>
		<hr />
		<form method="post" action="${createLink(controller:'admin', action:'updateAvailability')}">
			<g:each in="${availability}" var="day">
				<g:set var="available" value="${day?.available}" />
				<g:set var="dayIndex" value="${day?.dayIndex}" />
				<div class="row day day-${dayIndex} ${available ? 'available' : 'unavailable'}">
					<div class="col-xs-3 day-of-week">
						<h2>${day.name}</h2>
						<g:if test="${available}">
							<input type="checkbox" value="available" id="available-${dayIndex}" name="available-${dayIndex}" onclick="toggleAvailabilityForDay('${dayIndex}')" checked="checked">
						</g:if>
						<g:else>
							<input type="checkbox" value="available" id="available-${dayIndex}" name="available-${dayIndex}" onclick="toggleAvailabilityForDay('${dayIndex}')">
						</g:else>
					</div>
					<div class="col-xs-9 hours">
						<div class="col-xs-5">
							<div class="form-group" class="open">
								<label for="startTime-${dayIndex}">Open</label>
								<g:select class="form-control" name="startTime-${dayIndex}" from="${timeSlots}" value="${day.startTimeString}" disabled="${!available}" />
							</div>
						</div>
						<div class="col-xs-5">
							<div class="form-group" class="close">
								<label for="endTime-${dayIndex}">Close</label>
								<g:select class="form-control" name="endTime-${dayIndex}" from="${timeSlots}" value="${day.endTimeString}" disabled="${!available}" />
							</div>
						</div>
						<div class="col-xs-2">
							<span class="glyphicon glyphicon-${available ? 'ok' : 'ban'}-circle" aria-hidden="true"></span>
						</div>
					</div>
				</div>
			</g:each>
			<button type="submit" class="btn btn-default green-button">Save</button>
		</form>
	</div>
</body></html>