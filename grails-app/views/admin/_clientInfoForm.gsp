<div class="row">
	<form class="form-horizontal client-info-form" role="form">
		<input type="hidden" id="cId" value="${client?.id}" />
		<div class="form-group">
			<label class="control-label col-sm-2" for="e">Email:</label>
			<div class="col-sm-9 col-sm-offset-right-1">
				<input type="email" class="form-control" id="e" placeholder="Enter email" value="${client?.email}" required />
				<input type="email" class="form-control" id="e2" style="display:none;">
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="p">Password:</label>
			<div class="col-sm-9 col-sm-offset-right-1">          
				<input type="text" class="form-control" id="p" placeholder="Enter password" value="${client?.password}" required />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="f">First Name:</label>
			<div class="col-sm-9 col-sm-offset-right-1">          
				<input type="text" class="form-control" id="f" placeholder="Enter first name" value="${client?.firstName}" required />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="l">Last Name:</label>
			<div class="col-sm-9 col-sm-offset-right-1">          
				<input type="text" class="form-control" id="l" placeholder="Enter last name" value="${client?.lastName}" required />
			</div>
		</div>
		<div class="form-group">
			<label class="control-label col-sm-2" for="ph">Phone #:</label>
			<div class="col-sm-9 col-sm-offset-right-1">          
				<input type="text" class="form-control" id="ph" placeholder="Enter phone #"  value="${client?.phone}" />
			</div>
		</div>
		<div class="form-group">        
			<div class="col-sm-offset-2 col-sm-9 col-sm-offset-right-1">
				<button type="submit" class="btn green-button" id="saveClientButton">${submitText}</button>
				<button type="button" class="btn white-button" id="cancelClientRegistrationButton">Cancel</button>
			</div>
		</div>
	</form>
	<div class="errorDetails"></div>
</div>