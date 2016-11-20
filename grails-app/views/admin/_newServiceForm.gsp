<form method="post" class="form-horizontal" role="form">
	<div class="form-group">
		<label class="control-label col-sm-2" for="description">Description:</label>
		<div class="col-sm-9 col-sm-offset-right-1">
			<input type="text" class="form-control" name="description" placeholder="Description" required />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="price">Price:</label>
		<div class="col-sm-9 col-sm-offset-right-1">          
			<input type="Password" class="form-control" name="price" placeholder="Price" required />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="duration">Duration:</label>
		<div class="col-sm-9 col-sm-offset-right-1">          
			<input type="text" class="form-control" name="duration" placeholder="Duration" required />
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