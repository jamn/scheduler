<html>
<head></head><body>
	<g:set var="util" bean="utilService"/>
	
	<form method="POST">
		<h1 class="page-header">Homepage Message</h1>
		<textarea class="form-control" id="homepageText">${homepageText}</textarea>
		<div id="saveTextButton" class="btn green-button">Save</div>
	</form>

	<form method="POST" enctype="multipart/form-data" action="importClients">
		<h1 class="page-header">Import Clients</h1>
		<label class="control-label">Select .csv file</label>
		<input id="clientsFile" name="clientsFile" type="file" class="file">
		<input type="submit" name="importClients" value="Import Clients" class="btn green-button">
	</form>

	<form method="POST" enctype="multipart/form-data" action="importAppointments">
		<h1 class="page-header">Import Appointments</h1>
		<label class="control-label">Select iCal (.ics) file</label>
		<input id="appointmentsFile" name="appointmentsFile" type="file" class="file">
		<input type="submit" name="importAppointments" value="Import Appointments" class="btn green-button">
	</form>
</body></html>