<html>
<head></head><body>
	<h1>Services</h1>
	<hr />
	<div class="services">
		<g:each in="${services}">
				<div class="row service service-${it.id} grey-gradient" id="${it.id}">
					<div class="row">
						<div class="col-xs-12">
							<input type="text" name="serviceDescription-${it.id}" value="${it.description}" class="service-name">
						</div>
						
					</div>
					<div class="row">
						<div class="col-xs-4">
							$ <input type="text" name="servicePrice-${it.id}" value="${it.price}" style="width:80%">
						</div>
						<div class="col-xs-4">
							<input type="text" name="serviceDuration-${it.id}" value="${it.duration}">
						</div>
						<div class="col-xs-4">
							<input type="text" name="serviceCalendarColor-${it.id}" value="${it.calendarColor}" class="color">
						</div>
					</div>
					<div class="row buttons">
						<div class="col-xs-3 button active-button">
							<g:if test="${!available}">
								<span class="glyphicon glyphicon-ok-circle"></span>
							</g:if>
							<g:else>
								<span class="glyphicon glyphicon-ban-circle"></span>
							</g:else>
							<h2>Active</h2>
						</div>
						<div class="col-xs-3 button move-button">
							<span class="glyphicon glyphicon-chevron-up"></span>
							<h2>Move</h2>
						</div>
						<div class="col-xs-3 button move-button">
							<span class="glyphicon glyphicon-chevron-down"></span>
							<h2>Move</h2>
						</div>
						<div class="col-xs-3 button delete-button">
							<span class="glyphicon glyphicon-remove"></span>
							<h2>Delete</h2>
						</div>
					</div>
				</div>
		</g:each>
	</div>

	<!-- https://github.com/PitPik/tinyColorPicker -->
	<script type="text/javascript" src="${resource(dir:'js', file:'jqColorPicker.min.js')}"></script>
	<script type="text/javascript">
		$('.color').colorPicker();
	</script>

</body></html>