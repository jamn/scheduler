<select id="clientsDetailsSelector" class="form-control">
	<g:if test="${filterLetters.size() > 1}" >
		<option selected="selected"> -- select an client -- </option>
	</g:if>
	<g:else>
		<option selected="selected"> -- ${filterLetters[0]} -- </option>
	</g:else>
	<g:each in="${clients}" var="client" status="i">
		<g:set var="currentLastNameStartsWith" value="${client.lastName.substring(0,1)}" />
		
		<g:if test="${(i == 0)}">
			<g:set var="previousLastNameStartedWith" value="${client.lastName.substring(0,1)}" />
			<optgroup label="${currentLastNameStartsWith}">
				<option value="${client.id}">${client.lastName}, ${client.firstName}</option>
		</g:if>
		<g:elseif test="${(i == clients.size())}">
			</optgroup>
		</g:elseif>
		<g:elseif test="${(currentLastNameStartsWith != previousLastNameStartedWith)}">
			</optgroup>
			<optgroup label="${currentLastNameStartsWith}">
				<option value="${client.id}">${client.lastName}, ${client.firstName}</option>
		</g:elseif>
		<g:else>
			<option value="${client.id}">${client.lastName}, ${client.firstName}</option>
		</g:else>

		<g:set var="previousLastNameStartedWith" value="${client.lastName.substring(0,1)}" />
	</g:each>
</select>