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
<meta name="viewport" content="width=device-width, initial-scale=1">
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

	<div class="container-fluid">
		<div class="row header">
			<div class=".col-xs-12">
				<img class="img-responsive logo link home-link" id="logoPlain" src="${resource(dir:'images',file:'logo-plain.png')}" />
				<div class="link address" id="headerAddressLink">1013 W 47th Street<br/>KCMO, 64112</div>
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
				<g:layoutBody />
			</div>
		</div>

		<g:render template="/layouts/footer" />


	</div>

	<div id="mask"><img class="loader" src="${resource(dir:'images',file:'loading.gif')}" /></div>

	<script src="${resource(dir:'js', file:'bootstrap-3.2.0.min.js')}" type="text/javascript"></script>
	<!-- <script src="${resource(dir:'js', file:'jquery.confirmon.js')}"></script> -->
	<!-- <script src="${resource(dir:'js', file:'jquery-validate-min.js')}"></script> -->
	<!-- <script src="${resource(dir:'js', file:'application.min.js')}?v${appVersion}" type="text/javascript"></script> -->
	<script src="${resource(dir:'js', file:'masked-input-plugin.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir:'js', file:'app.js')}" type="text/javascript"></script>
</body></html>