<html>
<head></head><body>
	<div class="clearfix"></div>
	<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6 col-lg-offset-4 col-lg-4">
		<form method="post" class="reset-password-box" action="attemptPasswordReset">

			<input placeholder="New Password" type="password" name="newPassword" 
			id="newPassword" class="form-control">

			<input placeholder="Verify New Password" type="password" name="verifyNewPassword" id="verifyNewPassword" class="form-control">

			<input type="submit" class="btn green-button login-button" value="Reset Password" onclick="showMask()" />

		</form>
	</div>

</body></html>