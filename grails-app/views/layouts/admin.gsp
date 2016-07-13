<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
		"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html><head>
	<meta charset="UTF-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
	<title><g:layoutTitle default="The Den Barbershop :: Admin" /></title>

	<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'bootstrap-3.2.0.min.css')}" >
	<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'jquery-ui-1.10.3.custom.min.css')}" />
	<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'jquery.confirmon.css')}" />
	<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'admin.css')}?v${grailsApplication.metadata.'app.version'}" />
	<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'admin-media.css')}?v${grailsApplication.metadata.'app.version'}" />

	<link rel="apple-touch-icon" sizes="57x57" href="${resource(dir:'images', file:'apple-icon-57x57.png')}" />
	<link rel="apple-touch-icon" sizes="72x72" href="${resource(dir:'images', file:'apple-icon-72x72.png')}" />
	<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir:'images', file:'apple-icon-114x114.png')}" />
	<link rel="apple-touch-icon" sizes="144x144" href="${resource(dir:'images', file:'apple-icon-144x144.png')}" />

	<script src="${resource(dir:'js', file:'jquery-1.10.2.min.js')}" type="text/javascript"></script>

<g:layoutHead />
</head><body>

	<!-- HEADER NAVIGATION (MOBILE ONLY) -->

	<div class="navbar navbar-inverse navbar-fixed-top" role="navigation">
		<div class="container-fluid">
			<div class="navbar-header">
				<h1 class="brand">The Den Barbershop</h1>
				<button type="button" class="navbar-toggle collapsed glyphicon glyphicon-th-large" data-toggle="collapse" data-target=".navbar-collapse" />
				<!-- <g:img class="brand" dir="images" file="logo-large.png" /> -->
				<div class="logged-in-user">Whaddup Ben | <a id="logout" href="${createLink(controller:'access', action:'logout')}">Logout</a></div>
			</div>

			<div class="navbar-collapse collapse" style="height: 1px;">
				<ul class="nav navbar-nav navbar-right">
					<g:render template="navigation" />
					<li><a href="${createLink(controller:'access', action:'logout')}">Logout</a></li>
				</ul>
			</div>
		</div>
	</div>


	<div class="container-fluid">
		<div class="row">

			<!-- SIDEBAR NAVIGATION (>=768px) -->

			<div class="col-sm-3 col-md-2 sidebar">
				<ul class="nav nav-sidebar">
					<g:render template="navigation" />
					<li><a href="${createLink(controller:'access', action:'logout')}">Logout</a></li>
				</ul>
			</div>

			<!-- CONTENT -->

			<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
				<g:layoutBody />
			</div>

		</div>
	</div>


	<div id="mask"><img class="loader" src="${resource(dir:'images',file:'loading.gif')}" /></div>
	<div id="waitingSpinner" style="display:none;"><img width='20px' height'20px' src="${resource(dir:'images', file:'spinner.gif')}" class="spinner"></div> 

	<script src="${resource(dir:'js', file:'jquery-ui-1.10.3.custom.min.js')}" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).bind('mobileinit',function(){
			$.mobile.loadingMessage = false; 	// Hide the jquery mobile loading message.
												// Must be done before loading jquery mobile.
		});
	</script>
	<script src="${resource(dir:'js', file:'jquery.mobile-1.3.2.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir:'js', file:'bootstrap-3.2.0.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir:'js', file:'jquery.confirmon.js')}"></script>
	<script src="${resource(dir:'js', file:'jquery-validate-min.js')}"></script>
	<script src="${resource(dir:'js', file:'a.min.js')}?v${grailsApplication.metadata.'app.version'}" type="text/javascript"></script>

</body></html>