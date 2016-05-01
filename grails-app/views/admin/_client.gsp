<h2>${client.fullName}</h2>
<a id="editClient">[Edit]</a>

<div class="row">
	<h3>Notes:</h3>
</div>
<div class="row">
	<textarea class="form-control" id="clientNotes">${client.notes}</textarea>
</div>
<div class="row">
	<div id="saveClientNotesButton" class="btn green-button">Save</div>
</div>
<div class="row">
	<h3>Contact:</h3>
</div>
<div class="row">
	<g:if test="${client.phone}">
		<a href="tel:${client?.phone?.replace('-', '')}">${client.phone}</a> | 
	</g:if>
	<a href="mailto:${client.email}">${client.email}</a>
</div>
<div class="row">
	<h3>Password:</h3>
</div>
<div class="row">
	<p>${client.password}</p>
</div>
<div class="row">
	<h3>History:</h3>
</div>
<div class="row">
	<g:render template="clientHistory" model="['appointments':appointments]" />
</div>