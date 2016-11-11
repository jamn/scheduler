<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html><head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<style type="text/css">
		.logo {
			top: 20%;
		}
		.logo,
		.server-error-message {
			position: fixed;
			left: 50%;
			-webkit-transform: translate(-50%, -50%);
			   -moz-transform: translate(-50%, -50%);
			        transform: translate(-50%, -50%);
			text-align: center;
		}
		.server-error-message {
			top: 50%;
			font-family: 'Helvetica Neue', Helvetica, Arial, sans-serif;
		}
	</style>
</head><body>

	<a href="/"><img class="logo" id="logoPlain" src="${resource(dir:'images',file:'logo-plain.png')}" /></a>

	<div class="server-error-message">An error has occured. Click <a href="#" onclick="history.back();">here</a> to go back.</div>

</body></html>