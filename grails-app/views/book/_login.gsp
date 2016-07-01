<g:set var="loggedIn" value="${(!cancelAppointment && session?.client) ? 'logged-in' : ''}" />

<input type="hidden" name="loggedIn" id="loggedIn" value="${loggedIn}" />

<g:if test="${!cancelAppointment}">
	<input class="form-control ${loggedIn}" placeholder="Phone" type="text" name="phoneNumber" id="phoneNumber" value="${client?.phone}" autofocus="autofocus" />
</g:if>

<input class="form-control ${loggedIn}" placeholder="Email" type="text" name="email" id="email" value="${client?.email}" />

<input class="form-control ${loggedIn}" placeholder="Password" type="password" name="password" id="password-${formAction}" value="${client?.password}" />

<input placeholder="First Name" type="text" name="first-name" id="firstName" class="form-control new-user ${loggedIn}">

<input placeholder="Last Name" type="text" name="last-name" id="lastName" class="form-control new-user ${loggedIn}"><br>

<g:if test="${!cancelAppointment}">
	<label class="reminders ${loggedIn}">		
		<input type="checkbox" name="rememberMe" id="rememberMe" checked> Remember Me
	</label>
</g:if>

<div class="reset-password-links ${loggedIn}">
	<span id="registerLink">New Client?</span>
	<span class="left-divider"> | </span>
	<span id="resetPassword">Reset Password</span>
	<span class="right-divider"> | </span>
	<span id="showLoginForm">Login Form</span>
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