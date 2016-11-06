<html>
<head></head><body>
	<div class="clearfix"></div>
	<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6">
		<form method="post" class="login-box" action="/access/attemptLogin">

			<input class="form-control" placeholder="Email" type="email" name="email" id="email" value="${flash?.email}" autofocus />

			<input class="form-control" placeholder="Password" type="password" name="password" id="password" />

			<label><input type="checkbox" id="rememberMe"  name="rememberMe" checked> Remember me</label><br>

			<a href="resetPassword" style="padding:6px;">Reset Password</a>

			<input type="submit" class="btn green-button login-button" id="loginButton" value="Login" onclick="showMask()" />

		</form>
	</div>

</body></html>