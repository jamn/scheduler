<html>
<head></head><body>

	<div class="col-xs-12 col-sm-offset-3 col-sm-6">
		<form class="login-box" action="login">

			<input class="form-control" placeholder="Email" type="text" name="email" id="email" value="${flash?.email}" />

			<input class="form-control" placeholder="Password" type="password" name="password" id="password-${formAction}" />

			<div class="reset-password-links">
				<span id="registerLink">New Client?</span>
				<span class="left-divider"> | </span>
				<span id="resetPassword">Reset Password</span>
				<span class="right-divider"> | </span>
				<span id="showLoginForm">Login Form</span>
			</div>

			<input type="submit" class="btn green-button login-button" id="loginButton" value="Login" onclick="showMask()" />
			
			<g:if test="${flash.error}">
				<div class="errorDetails">${flash.error}</div>
			</g:if>

		</form>
	</div>

</body></html>