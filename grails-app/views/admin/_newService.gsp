<form method="post" class="form-horizontal new-service-form" role="form">
	<div class="form-group">
		<label class="control-label col-sm-2" for="e">Description:</label>
		<div class="col-sm-9 col-sm-offset-right-1">
			<input type="email" class="form-control" id="e" placeholder="Enter email" required />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="p">Password:</label>
		<div class="col-sm-9 col-sm-offset-right-1">          
			<input type="Password" class="form-control" id="p" placeholder="Enter password" required />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="f">First Name:</label>
		<div class="col-sm-9 col-sm-offset-right-1">          
			<input type="text" class="form-control" id="f" placeholder="Enter first name" required />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="l">Last Name:</label>
		<div class="col-sm-9 col-sm-offset-right-1">          
			<input type="text" class="form-control" id="l" placeholder="Enter last name" required />
		</div>
	</div>
	<div class="form-group">
		<label class="control-label col-sm-2" for="ph">Phone #:</label>
		<div class="col-sm-9 col-sm-offset-right-1">          
			<input type="text" class="form-control" id="ph" placeholder="Enter phone #"  />
		</div>
	</div>
	<div class="form-group">        
		<div class="col-sm-offset-2 col-sm-9 col-sm-offset-right-1">
			<button type="submit" class="btn green-button" id="saveClientButton">${submitText}</button>
			<button type="button" class="btn white-button" id="cancelClientRegistrationButton" style="margin-top:0;">Cancel</button>
		</div>
	</div>
</form>
<div class="error-details"></div>