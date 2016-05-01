<div class="row">
	<h1 class="page-header">Client Details</h1>
	<div id="addClient">
		<div class="add-client green-button">+</div>
		<div class="add-client-label">Add Client</div>
	</div>
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
<div class="row">
	<div id="clientDetails"></div>
</div>
<div class="row">
	<div id="clientInfoForm"></div>
</div>

