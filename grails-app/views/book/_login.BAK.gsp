<div class="row">
	<div class="col-xs-offset-2 col-xs-8 col-sm-offset-3 col-sm-6">
		<form class="login-box">
			<input class="form-control" placeholder="Email" type="text" name="email" id="email" value="${client?.email}" autofocus="autofocus" />
			<input class="form-control" placeholder="Verify Email" type="text" name="email2" id="email2" style="display:none;" />

			<input class="form-control" placeholder="Password" type="password" name="password" id="password" value="${client?.password}" />

			%{--<input placeholder="First Name" type="text" name="first-name" id="firstName" class="form-control new-user" />

			<input placeholder="Last Name" type="text" name="last-name" id="lastName" class="form-control new-user" />

			<input placeholder="Phone #" type="text" name="phone" id="phoneNumber" class="form-control new-user" />--}%
			
			<div style="text-align:center;"> 
				%{--<span id="registerLink">New Client?</span>
				<span class="left-divider"> | </span>--}%
				<span id="resetPassword">Reset Password</span>
				<span class="right-divider"> | </span>
				<span id="showLoginForm">Login Form</span>
			</div>
			
			<div class="btn green-button" id="loginButton"><div class="as-button-label">Book Appointment</div><img width='20px' height'20px' src="${resource(dir:'images', file:'spinner.gif')}" style="display:none;" class="spinner"></div>
		</form>

		<div class="errorDetails"></div>

	</div>
</div>

<script type="text/javascript">
	$('#email').bind('keyup', function(){
		var value = $(this).val()
		$(this).val(value.replace(/\s+/g, ''));
	});
	$(document).ready(function(){
		$("#phoneNumber").mask("999-999-9999");
	});
</script>

