<!DOCTYPE html>
<html><head>
<meta charset="UTF-8">
<title>The Den Admin | Login</title>
<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'bootstrap-3.2.0.min.css')}" >
<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'jquery-ui-1.10.3.custom.min.css')}" />
<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'admin.css')}" />
</head><body>
	<div class="content-wrapper">
		<div class="col-xs-offset-1 col-xs-10 col-xs-offset-right-1 col-sm-offset-3 col-sm-6 col-sm-offset-right-3 col-lg-offset-4 col-lg-4 col-lg-offset-right-4">
			<div class="logo-small"><img width="240px" height="240px" id="logoSmall" src="${resource(dir:'images',file:'logo.png')}" /></div>
			<div class="login-box">
				<input class="form-control" type="text" name="username" id="username" placeholder="username" autofocus>
				<input class="form-control" type="password" name="password" id="password" placeholder="password">
				<div class="btn green-button" id="loginButton">
					Login
				</div>
			</div>
		</div>
	</div>
	<div id="waitingSpinner" style="display:none;"><img width='20px' height'20px' src="${resource(dir:'images', file:'spinner.gif')}" class="spinner"></div> 

<script src="${resource(dir:'js', file:'jquery-1.10.2.min.js')}" type="text/javascript"></script>
<script src="${resource(dir:'js', file:'jquery-ui-1.10.3.custom.min.js')}" type="text/javascript"></script>
<script type="text/javascript">
	$(document).bind('mobileinit',function(){
		$.mobile.loadingMessage = false; 	// Hide the jquery mobile loading message.
											// Must be done before loading jquery mobile.
	});
</script>
<script src="${resource(dir:'js', file:'jquery.mobile-1.3.2.min.js')}" type="text/javascript"></script>
<script src="${resource(dir:'js', file:'bootstrap-3.2.0.min.js')}" type="text/javascript"></script>
<script src="${resource(dir:'js', file:'jquery-validate-min.js')}"></script>
<script type="text/javascript">
	$(document).ready(function(){
		$('#username').bind('keyup', function(){
			var value = $(this).val()
			$(this).val(value.replace(/\s+/g, ''));
		});
		$('#password').keypress(function(e) {
			var user = $('#username').val();
			var password = $('#password').val();
			if (e.keyCode === 13){
				disableLoginButton();
				window.location.href = "../access/login?u="+user+"&p="+password
			}
		});
	});
	$(document).on('tap', '#loginButton', function(e) {
		disableLoginButton();
		var user = $('#username').val();
		var password = $('#password').val();
		window.location.href = "../access/login?u="+user+"&p="+password
	});
	function disableLoginButton(){
		$('#loginButton').attr("disabled", "disabled");
		$('#loginButton').html($('#waitingSpinner').html());
	}

</script>
</body></html>