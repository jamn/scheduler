<html>
<head></head><body>
	<div class="availability">
		<h1>Availability</h1>
		<hr />
		<form>
			<g:each in="${availability}" var="day">
				<g:set var="lowercaseDayName" value="${day.key.toLowerCase()}" />
				<g:set var="available" value="${day.value.available}" />
				<div class="row day day-${lowercaseDayName} ${available ? 'available' : 'unavailable'}">
					<div class="col-xs-3 day-of-week">
						<h2>${day.key}</h2>
						<g:if test="${available}">
							<input type="checkbox" id="${lowercaseDayName}-available" onclick="toggleAvailabilityForDay('${lowercaseDayName}')" checked="checked">
						</g:if>
						<g:else>
							<input type="checkbox" id="${lowercaseDayName}-available" onclick="toggleAvailabilityForDay('${lowercaseDayName}')">
						</g:else>
					</div>
					<div class="col-xs-9 hours">
						<div class="col-xs-5">
							<div class="form-group" class="open">
								<label for="${lowercaseDayName}-open">Open</label>
								<g:select class="form-control" name="${lowercaseDayName}-open-timeslots" from="${timeSlots}" value="${day.value.startTime}" disabled="${!available}" />
							</div>
						</div>
						<div class="col-xs-5">
							<div class="form-group" class="close">
								<label for="${lowercaseDayName}-close">Close</label>
								<g:select class="form-control" name="${lowercaseDayName}-close-timeslots" from="${timeSlots}" value="${day.value.endTime}" disabled="${!available}" />
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