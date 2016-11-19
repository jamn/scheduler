<g:set var="emailClass" value="${flash.newClientRegistering && !flash.email ? 'error' : ''}"/>
<g:set var="passwordClass" value="${flash.newClientRegistering && !flash.password ? 'error' : ''}"/>
<g:set var="firstNameClass" value="${flash.newClientRegistering && !flash.firstName ? 'error' : ''}"/>
<g:set var="lastNameClass" value="${flash.newClientRegistering && !flash.lastName ? 'error' : ''}"/>
<g:set var="phoneNumberClass" value="${flash.newClientRegistering && !flash.phoneNumber ? 'error' : ''}"/>

<div class="password-reset-instructions">Send a password reset link to:</div>

<input class="form-control ${emailClass}" placeholder="Email" type="email" name="email" id="email" value="${flash.email}" autofocus="autofocus"/>

<input class="form-control ${passwordClass}" placeholder="Password" type="password" name="password" id="password-${formAction}" value="${flash.password}" />

<g:if test="${!cancelAppointment}">
	<input class="form-control new-user ${phoneNumberClass}" placeholder="Phone" type="tel" name="phoneNumber" id="phoneNumber" value="${flash.phoneNumber}" />

	<input placeholder="First Name" type="text" name="firstName" id="firstName" class="form-control new-user ${firstNameClass}" value="${flash.firstName}">

	<input placeholder="Last Name" type="text" name="lastName" id="lastName" class="form-control new-user ${lastNameClass}" value="${flash.lastName}">
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
		<%if (flash.newClientRegistering){%>
			showRegistrationForm();
		<%}%>
		<%if (flash.sendPasswordResetLink){%>
			showResetPasswordForm();
		<%}%>
	});
</script>