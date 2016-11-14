<html>
<head></head><body>

	<h1>Client Details</h1>
	<div id="addClient">
		<div class="add-client green-button">+</div>
		<div class="add-client-label">Add Client</div>
	</div>

	<div class="row" id="lastNameFilters">
		<div class="col-sm-12">
			<g:each in="${filterLetters}" var="letter">
				<a class="last-name-filter" value="${letter}">${letter}</a>
			</g:each>
			<span class="spacer reset-search"> &oline; </span>
			<a class="last-name-filter reset-search reset" value="Reset">Reset Search</a>
		</div>
	</div>
	<div class="row">
		<div class="col-sm-4 col-sm-offset-right-8">
			<g:render template="clientsSelectMenu" />
		</div>
	</div>
	<div>
		<div id="clientDetails"></div>
	</div>
	<div>
		<div id="clientInfoForm"></div>
	</div>
</body></html>