<html>
<head></head><body>

	<h1>Select A Service</h1>
	<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6">
		<g:if test="${services.size() > 0}">
			<g:each in="${services}" var="service">
				<a href="${createLink(action:'saveServiceSelection', params:[id:service.id])}">
					<div service="${service.description}" class="as-button green-button service" id="service-${service.id}"><div class="as-button-label">${service.description}</div></div>
				</a>
			</g:each>
		</g:if>
		<g:else>
			<p class="padded-box centered">Services have not been setup.</p>
		</g:else>
	</div>

</body></html>