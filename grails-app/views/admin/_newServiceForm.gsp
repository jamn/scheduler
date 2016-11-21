<form method="post" action="saveService" class="form-horizontal service" role="form">
	<div class="form-group">
		<label class="control-label col-sm-2" for="serviceDescription">Description:</label>
		<div class="col-sm-9 col-sm-offset-right-1">
			<input type="text" class="form-control" name="serviceDescription" placeholder="Description" required />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="servicePrice">Price:</label>
		<div class="col-sm-9 col-sm-offset-right-1">          
			<table width="100%">
				<tr>
					<td style="font-size:1.5em;padding:8px;width:20px;color: #545454;">$</td>
					<td><input type="text" class="form-control" name="servicePrice" placeholder="Price" required /></td>
				</tr>
			</table>
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="serviceDuration">Duration:</label>
		<div class="col-sm-9 col-sm-offset-right-1">          
			<g:select name="serviceDuration" from="${['15 min', '30 min', '45 min', '60 min', '90 min', '120 min']}" />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="serviceCalendarColor">Calendar Color:</label>
		<div class="col-sm-9 col-sm-offset-right-1">          
			<input type="text" class="form-control color" name="serviceCalendarColor" value="#FFFFFF" required />
		</div>
	</div>
	<div class="form-group">        
		<div class="col-sm-offset-2 col-sm-9 col-sm-offset-right-1">
			<button type="submit" class="btn green-button">Save</button>
			<button type="reset" class="btn white-button" style="margin-top:0;" onclick="hideForm();">Cancel</button>
		</div>
	</div>
</form>


<script type="text/javascript">
	$('.color').colorPicker();
	function hideForm(){
		$('.services').slideDown();
		$('#addService').fadeIn();
		$('#newServiceFormContainer').slideUp();
	}
</script>