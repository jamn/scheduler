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
<title><g:layoutTitle default="SchedulePro" /></title>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">

<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'bootstrap-3.2.0.min.css')}" >
<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'jquery-ui-1.10.3.custom.min.css')}" />
<!-- <link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'jquery.confirmon.css')}" /> -->

<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'stylz-new.css')}?v${appVersion}" />
<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'user.css')}?v${appVersion}" />
<link media="handheld, only screen" href="${resource(dir:'css', file:'media.css')}?v${appVersion}" type="text/css" rel="stylesheet" />

<link href='https://fonts.googleapis.com/css?family=Dancing+Script' rel='stylesheet' type='text/css'>
<link href='https://fonts.googleapis.com/css?family=Source+Sans+Pro:400,200' rel='stylesheet' type='text/css'>

<link rel="apple-touch-icon" sizes="57x57" href="${resource(dir:'images', file:'apple-icon-57x57.png')}" />
<link rel="apple-touch-icon" sizes="72x72" href="${resource(dir:'images', file:'apple-icon-72x72.png')}" />
<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir:'images', file:'apple-icon-114x114.png')}" />
<link rel="apple-touch-icon" sizes="144x144" href="${resource(dir:'images', file:'apple-icon-144x144.png')}" />

<script src="${resource(dir:'js', file:'jquery-1.10.2.min.js')}" type="text/javascript"></script>
<script src="${resource(dir:'js', file:'jquery-ui-1.10.3.custom.min.js')}" type="text/javascript"></script>

<g:layoutHead />
</head><body>

	<g:set var="util" bean="utilService"/>

	<div class="container-fluid">
		<div class="row header">
			<div class=".col-xs-12">
				<div id="headerLogoBoard" class="header-logo closed">
					<img class="header-board" src="${resource(dir:'images',file:'header-board.png')}" />
					<img class="logo link home-link" id="logoPlain" src="${resource(dir:'images',file:'logo-plain.png')}" />
				</div>
				<g:if test="${session.user}">
					<div class="user-links">
						<g:render template="/user/menu" />
					</div>
				</g:if>
			</div>
		</div>

		<div class="row main-content">
			<div class="page">

				<g:if test="${flash.error}">
					<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6">
						<div class="alert alert-danger alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<strong>Error!</strong> ${flash.error}
						</div>
					</div>
				</g:if>
				<g:if test="${flash.success}">
					<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6">
						<div class="alert alert-success alert-dismissible" role="alert">
							<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
							<strong>Success!</strong> ${flash.success}
						</div>
					</div>
				</g:if>


				<g:layoutBody />
			</div>
		</div>

		<div class="row footer">
			<div class="col-xs-12 col-sm-offset-2 col-sm-8 col-md-offset-3 col-md-6">
				<hr>
				<p>Made in Kansas City</p>
				<span class="glyphicon glyphicon-leaf" aria-hidden="true"></span>
			</div>
		</div>


	</div>

	<div id="mask"><img class="loader" src="${resource(dir:'images',file:'loading.gif')}" /></div>

	<script src="${resource(dir:'js', file:'bootstrap-3.2.0.min.js')}" type="text/javascript"></script>
	<!-- <script src="${resource(dir:'js', file:'jquery.confirmon.js')}"></script> -->
	<!-- <script src="${resource(dir:'js', file:'jquery-validate-min.js')}"></script> -->
	<!-- <script src="${resource(dir:'js', file:'application.min.js')}?v${appVersion}" type="text/javascript"></script> -->
	<script src="${resource(dir:'js', file:'masked-input-plugin.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir:'js', file:'app.js')}" type="text/javascript"></script>
	<script type="text/javascript">
		function toggleMenu(){
			$('#dropdownMenu').toggleClass("closed");
		}
		$(document).ready(function(){
			$('#headerLogoBoard').removeClass("closed");
		});
	</script>
</body></html>