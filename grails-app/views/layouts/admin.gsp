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
	<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'admin.css')}?v${appVersion}" />
	<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'admin-media.css')}?v${appVersion}" />

	<link rel="apple-touch-icon" sizes="57x57" href="${resource(dir:'images', file:'apple-icon-57x57.png')}" />
	<link rel="apple-touch-icon" sizes="72x72" href="${resource(dir:'images', file:'apple-icon-72x72.png')}" />
	<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir:'images', file:'apple-icon-114x114.png')}" />
	<link rel="apple-touch-icon" sizes="144x144" href="${resource(dir:'images', file:'apple-icon-144x144.png')}" />

	<script src="${resource(dir:'js', file:'jquery-1.10.2.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir:'js', file:'jquery-ui-1.10.3.custom.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir:'js', file:'bootstrap-3.2.0.min.js')}" type="text/javascript"></script>
	<script src="${resource(dir:'js', file:'jquery.confirmon.js')}"></script>
	<script src="${resource(dir:'js', file:'jquery-validate-min.js')}"></script>
	<%-- http://blog.stevenlevithan.com/archives/date-time-format --%>
	<script src="${resource(dir:'js', file:'date.format.js')}"></script>
	<script src="${resource(dir:'js', file:'a.min.js')}?v${appVersion}" type="text/javascript"></script>

<g:layoutHead />
</head><body>



	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div class="navbar-header">
				<button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
					<span class="sr-only">Toggle navigation</span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
					<span class="icon-bar"></span>
				</button>
				<a class="navbar-brand" href="#">The Den Barbershop</a>
			</div>
			<div id="navbar" class="navbar-collapse collapse">
				<ul class="nav navbar-nav">
					<li class="${(params.action=='calendar') ? 'active' : ''}"><a href="/admin/calendar">Calendar</a></li>
					<li class="${(params.action=='upcomingAppointments') ? 'active' : ''}"><a href="/admin/upcomingAppointments">All Appointments</a></li>
					<li class="${(params.action=='blockOffTime') ? 'active' : ''}"><a href="/admin/blockOffTime">Block Off Time</a></li>
					<li class="dropdown">
						<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">More <span class="caret"></span></a>
						<ul class="dropdown-menu">
							<li class="${(params.action=='clients') ? 'active' : ''}"><a href="/admin/clients">Clients</a></li>
							<li class="${(params.action=='bookAppointment') ? 'active' : ''}"><a href="/admin/bookAppointment">Book Appointment</a></li>
							<li class="${(params.action=='homepageMessage') ? 'active' : ''}"><a href="/admin/homepageMessage">Homepage Message</a></li>
							<li role="separator" class="divider"></li>
							<li><a href="/book">Client Site</a></li>
						</ul>
					</li>
				</ul>
				<ul class="nav navbar-nav navbar-right">
					<li><a href="/access/logout">Logout <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span></a></li>
				</ul>
			</div><!--/.nav-collapse -->
		</div>
	</nav>

	<div class="container">
		<div class="main col-xs-12">
			<div class="row">
				<g:if test="${flash.error}">
					<div class="alert alert-danger alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<strong>Error!</strong> ${flash.error.encodeAsHTML()}
					</div>
				</g:if>
				<g:if test="${flash.success}">
					<div class="alert alert-success alert-dismissible" role="alert">
						<button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
						<strong>Success!</strong> ${flash.success.encodeAsHTML()}
					</div>
				</g:if>
			</div>
			<g:layoutBody />
		</div>
	</div>


	<div id="mask"><img class="loader" src="${resource(dir:'images',file:'loading.gif')}" /></div>
	<div id="waitingSpinner" style="display:none;"><img width='20px' height'20px' src="${resource(dir:'images', file:'spinner.gif')}" class="spinner"></div> 


</body></html>