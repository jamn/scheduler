<!-- 
           .-"-.
         .'     '.
         |       |
          \     /
         [_______]
          |##' ,|
          |#' ,#|
          |' ,##|
          | ,###|		SHAVE AND A HAIRCUT,
          |,###'|		AND A BEER TOO.
          |###' |
          |##' ,|
          |#' ,#|
          |'_,##|
         [_______]
          \     /
           |   |
 -->

 <!-- Made by Ben Jacobi | benjacobi.com -->
 <!-- v${grailsApplication.metadata.'app.version'} -->

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<html><head>
<meta charset="UTF-8">
<title>The Den Barbershop | Modify Appointment</title>
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no"> -->

<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'bootstrap-3.2.0.min.css')}" >
<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'jquery-ui-1.10.3.custom.min.css')}" />
<!-- <link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'jquery.confirmon.css')}" /> -->

<link rel="stylesheet" type="text/css" href="${resource(dir:'css', file:'stylz-new.css')}?v${grailsApplication.metadata.'app.version'}" />
<link media="handheld, only screen" href="${resource(dir:'css', file:'media.css')}?v${grailsApplication.metadata.'app.version'}" type="text/css" rel="stylesheet" />

<script src="${resource(dir:'js', file:'jquery-1.10.2.min.js')}" type="text/javascript"></script>
<script type="text/javascript">
	$(document).bind('mobileinit',function(){
		$.mobile.loadingMessage = false; 	// Hide the jquery mobile loading message.
											// Must be done before loading jquery mobile.
	});
</script>
<script src="${resource(dir:'js', file:'jquery.mobile-1.3.2.min.js')}" type="text/javascript"></script>
<script src="${resource(dir:'js', file:'jquery-ui-1.10.3.custom.min.js')}" type="text/javascript"></script>

<link rel="apple-touch-icon" sizes="57x57" href="${resource(dir:'images', file:'apple-icon-57x57.png')}" />
<link rel="apple-touch-icon" sizes="72x72" href="${resource(dir:'images', file:'apple-icon-72x72.png')}" />
<link rel="apple-touch-icon" sizes="114x114" href="${resource(dir:'images', file:'apple-icon-114x114.png')}" />
<link rel="apple-touch-icon" sizes="144x144" href="${resource(dir:'images', file:'apple-icon-144x144.png')}" />

</head><body baseUrl="${createLink()}">

	<div class="existingAppointmentInfo"><b>Existing Appointment:</b> ${appointment.service.description} | ${appointment.appointmentDate.format('EEEE MMMM dd, yyyy')} @ ${appointment.appointmentDate.format('hh:mm a')}</div>

	<div class="container-fluid">
		<div class="row header">
			<div class=".col-xs-12">
				<img class="img-responsive logo link home-link" id="logoPlain" src="${resource(dir:'images',file:'logo-plain.png')}">
				<div class="link address" id="headerAddressLink">1013 W 47th Street<br/>KCMO, 64112</div>
			</div>
		</div>
		<div class="row grey-box">
			<div class="col-xs-12 col-sm-offset-1 col-sm-6">
				<div class="message-board-backing">
					<div class="message-board">${raw(message)}</div>
				</div>
			</div>
			<div class="col-sm-4">
				<img class="logo" src="${resource(dir:'images',file:'logo-large.png')}">
			</div>
		</div>
		<div class="row main-content">
			<div class="page" page="home"></div>
			<div class="page" page="services"></div>
			<div class="page" page="timeSlots">
				<g:render template="datePicker" />
				<g:render template="timeSlots" />
			</div>
			<div class="page" page="loginForm"></div>
			<div class="page confirmation" page="confirmation"></div>
		</div>
		
		<g:render template="footer" />

	</div>

	<div id="mask"><img class="loader" src="${resource(dir:'images',file:'loading.gif')}" /></div>

	
	
	<script type="text/javascript">
		$(document).ready(function(){
			$('#email').bind('keyup', function(){
				var value = $(this).val()
				$(this).val(value.replace(/\s+/g, ''));
			});
			var d = new Date('${appointment.appointmentDate.format("MM/dd/yyyy")}');
			$('#dateText').empty();
			$('#dateText').append(weekday[d.getDay()]).slideDown();
			$('#chooseDate').datepicker("setDate", d);
			$('.main-content .page[page="timeSlots"]').fadeIn();
		});
	</script>
	<script src="${resource(dir:'js', file:'bootstrap-3.2.0.min.js')}" type="text/javascript"></script>
	<!-- <script src="${resource(dir:'js', file:'jquery.confirmon.js')}"></script> -->
	<!-- <script src="${resource(dir:'js', file:'jquery-validate-min.js')}"></script> -->
	<script src="${resource(dir:'js', file:'application.min.js')}?v${grailsApplication.metadata.'app.version'}" type="text/javascript"></script>
	<script src="${resource(dir:'js', file:'masked-input-plugin.min.js')}" type="text/javascript"></script>


</body></html>