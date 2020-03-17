<g:set var="appVersion" value="${grailsApplication.metadata.getApplicationVersion()}" />
<!-- 
 
 +-+-+-+-+-+-+-+-+-+-+-+
 |S|c|h|e|d|u|l|e|P|r|o|
 +-+-+-+-+-+-+-+-+-+-+-+

 v${appVersion}

 -->
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html><head>
<meta charset="UTF-8">
<title><g:layoutTitle default="The Den Barbershop :: KC" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> -->

<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'bootstrap-3.2.0.min.css')}" >
<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'jquery-ui-1.10.3.custom.min.css')}" />
<!-- <link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'jquery.confirmon.css')}" /> -->

<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'style.css')}?v${appVersion}" />
<link media="handheld, only screen" href="${resource(dir:'css', file:'media.css')}?v${appVersion}" type="text/css" rel="stylesheet" />

<link rel="apple-touch-icon" sizes="57x57" href="${resource(dir:'images', file:'apple-icon-57x57.png')}" />
<link rel="apple-touch-icon" sizes="72x72" href="${resource(dir:'images', file:'apple-icon-72x72.png')}" />
<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir:'images', file:'apple-icon-114x114.png')}" />
<link rel="apple-touch-icon" sizes="144x144" href="${resource(dir:'images', file:'apple-icon-144x144.png')}" />

<script src="${resource(dir:'js', file:'jquery-1.10.2.min.js')}" type="text/javascript"></script>
<script src="${resource(dir:'js', file:'jquery-ui-1.10.3.custom.min.js')}" type="text/javascript"></script>
<g:layoutHead />
</head><body>

	<g:set var="util" bean="utilService"/>

	<div class="container">
		<div class="row header">
			<div class="col-xs-12">
				<img class="img-responsive logo link home-link" id="logoPlain" src="${resource(dir:'images',file:'logo-plain.png')}" />
				<div class="address">1013 W 47th Street<br/>KCMO, 64112</div>
				<div class="user-links">
					<g:if test="${session.user}">
						<g:render template="/user/menu" />
					</g:if>
					<g:else>
						<a class="login-link" href="${createLink(controller:'access',action:'login')}">
							Login <span class="glyphicon glyphicon-user" aria-hidden="true"></span>
						</a>
					</g:else>
				</div>
			</div>
		</div>
		<div class="row grey-box">
			<div class="col-xs-12 col-sm-offset-1 col-sm-6">
				<div class="message-board-backing">
					<div class="message-board">${raw(util.getCommunicationBoardMessage())}</div>
				</div>
			</div>
			<div class="col-sm-4">
				<img class="logo" src="${resource(dir:'images',file:'logo-large.png')}">
			</div>
		</div>
		<div class="row main-content">
			<div class="page">
				<g:if test="${flash.error}">
					<div class="col-xs-offset-2 col-xs-8 col-sm-offset-3 col-sm-6">
						<div class="alert alert-danger alert-dismissible" role="alert">
							<button type="button" class="close" aria-label="Close" onclick="hideAlert();"><span aria-hidden="true">&times;</span></button>
							${flash.error.encodeAsHTML()}
						</div>
					</div>	
				</g:if>
				<g:if test="${flash.success}">
					<div class="col-xs-offset-2 col-xs-8 col-sm-offset-3 col-sm-6">
						<div class="alert alert-success alert-dismissible" role="alert">
							<button type="button" class="close" aria-label="Close" onclick="hideAlert();"><span aria-hidden="true">&times;</span></button>
							<strong>Success!</strong> ${flash.success.encodeAsHTML()}
						</div>
					</div>
				</g:if>
				<div style="clear:both;"></div>
				<g:layoutBody />
			</div>
		</div>

		<g:render template="/layouts/footer" />


	</div>

	<div id="mask"><img class="loader" src="${resource(dir:'images',file:'loading.gif')}" /></div>
	
	<div id="ss-mask" style="display">
		<div id="ss-banner-message">
			<button type="button" id="ss-banner-close-btn">X</button>
			<img src="${resource(dir:'images',file:'ss-logo.png')}" />
			<div id="ss-signup-ask">
				<p>
				Would you like to receive information about my new line of men's products?
				</p>
				<form method="post" class="login-box" id="ssSignupForm">
					<input class="form-control" placeholder="Email" type="email" name="email" id="ssEmail" autofocus="autofocus" required="true" />
					<button id="ss-signup-btn" type="submit">Sign Up</button> 
				</form>
			</div>
			<div id="ss-signup-thank-you" style="margin:72px 0;">
				<p>
					Thank you.
				</p>
			</div>
			<div id="ss-signup-error" style="margin:72px 0;">
				<p>
					Something went wrong.
				</p>
			</div>
		</div>
	</div>

	<script src="${resource(dir:'js', file:'bootstrap-3.2.0.min.js')}" type="text/javascript"></script>
	<!-- <script src="${resource(dir:'js', file:'jquery.confirmon.js')}"></script> -->
	<!-- <script src="${resource(dir:'js', file:'jquery-validate-min.js')}"></script> -->
	<!-- <script src="${resource(dir:'js', file:'application.min.js')}?v${appVersion}" type="text/javascript"></script> -->
	<script src="${resource(dir:'js', file:'masked-input-plugin.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir:'js', file:'jquery.cookie.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir:'js', file:'app.js')}" type="text/javascript"></script>
	<script type="text/javascript">
		$( document ).ready(function() {

			if ($.cookie('ss-signup') != '1') {
    			//show popup here
				$("#ss-mask").fadeIn();
			}

			$(document).on("click", "#ss-banner-close-btn", function() {
					$("#ss-mask").fadeOut();
					$.cookie('ss-signup', '1');
			});

			$("#ssSignupForm").submit(function( event ) {
				event.preventDefault();
				var ssEmail = $("#ssEmail").val(); 
				$.post("book/ssSignup",
				{
					email: ssEmail
				},
				function(data, status){
					console.log("Data: " + data + "\nStatus: " + status);
					if (data === "200"){
						$.cookie('ss-signup', '1');
						$("#ss-signup-ask").fadeOut();
						$("#ss-signup-thank-you").delay(500).fadeIn();
						$("#ss-mask").delay(3000).fadeOut();
					}
					if (data === "422" || data === "500"){
						$("#ss-signup-ask").fadeOut();
						$("#ss-signup-error").delay(500).fadeIn();
						$("#ss-signup-ask").delay(3000).fadeIn();
					}
				});
			});
		});
	
	</script>
</body></html>