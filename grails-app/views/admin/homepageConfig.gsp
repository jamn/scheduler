<html>
<head></head><body>
	<g:set var="util" bean="utilService"/>
	
	<form method="POST">
		<h1 class="page-header">Homepage Message</h1>
		<textarea class="form-control" id="homepageText">${homepageText}</textarea>
		<div id="saveTextButton" class="btn green-button">Save</div>
	</form>

	<form method="POST" enctype="multipart/form-data" action="saveHomepageImage">
		<h1 class="page-header">Homepage Image</h1>
		<div class="homepage-image-container">
			<img src="${util.getHomepageImageUrl()}">
		</div>
		<label class="control-label">Select image</label>
		<input id="homepageImage" name="homepageImage" type="file" class="file">
		<input type="submit" name="saveHomepageImage" value="Save" class="btn green-button">
	</form>

	<form method="POST" enctype="multipart/form-data" action="saveLogo">
		<h1 class="page-header">Logo</h1>
		<div class="logo-image-container">
			<img src="">
		</div>
		<label class="control-label">Select image</label>
		<input id="logo" name="logo" type="file" class="file">
		<input type="submit" name="saveLogo" value="Save" class="btn green-button">
	</form>

	<form method="POST" enctype="multipart/form-data" action="importClients">
		<h1 class="page-header">Import Clients</h1>
		<label class="control-label">Select .csv file</label>
		<input id="clientsFile" name="clientsFile" type="file" class="file">
		<input type="submit" name="importClients" value="Import Clients" class="btn green-button">
	</form>
</body></html>