<g:set var="loggedIn" value="${(!cancelAppointment && session?.client) ? 'logged-in' : ''}" />

<input type="hidden" name="loggedIn" id="loggedIn" value="${loggedIn}" />

<div class="error-details">${error}</div>

<div class="password-reset-instructions">Send a password reset link to:</div>

<input class="form-control" placeholder="Email" type="email" name="email" id="email" value="${client?.email}" autofocus="autofocus"/>

<input class="form-control" placeholder="Password" type="password" name="password" id="password-${formAction}" value="" />

<g:if test="${!cancelAppointment}">
	<input class="form-control new-user" placeholder="Phone" type="tel" name="phoneNumber" id="phoneNumber" value="${client?.phone}" />

	<input placeholder="First Name" type="text" name="first-name" id="firstName" class="form-control new-user">

	<input placeholder="Last Name" type="text" name="last-name" id="lastName" class="form-control new-user">
</g:if>

<br>

<g:if test="${!cancelAppointment}">
	<!-- <label class="reminders">		
		<input type="checkbox" name="rememberMe" id="rememberMe" checked> Remember Me
	</label> -->
</g:if>

<div class="reset-password-links">
	<span id="registerLink" onclick="showRegistrationForm();">New Client?</span>
	<span class="left-divider"> | </span>
	<span id="resetPassword" onclick="showResetPasswordForm();">Reset Password</span>
	<span class="right-divider"> | </span>
	<span id="showLoginForm" onclick="showLoginForm();">Login Form</span>
</div>

<script type="text/javascript">
	$(document).ready(function(){
		$("#phoneNumber").mask("999-999-9999");
		$('#email').bind('keyup', function(){
			var value = $(this).val()
			$(this).val(value.replace(/\s+/g, ''));
		});
	});
</script>