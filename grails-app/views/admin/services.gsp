<html>
<head></head><body>
	<h1>Services</h1>
	<div id="addService">
		<div class="add-service green-button">+</div>
		<div class="add-service-label">Add Service</div>
	</div>
	<hr />
	<div class="services">
		<g:each in="${services}" var="service" status="serviceNumber">
				<form action="saveService" method="post" class="row service service-${service.id} grey-gradient" editing="false" id="display-order-${serviceNumber+1}">
					<input type="hidden" name="serviceId" value="${service.id}">
					<div class="row">
						<div class="col-xs-12">
							<input type="text" name="serviceDescription" value="${service.description}" class="service-name">
						</div>
						
					</div>
					<div class="row">
						<div class="col-xs-4">
							$ <input type="text" name="servicePrice" value="${service.price}" style="width:70%">
						</div>
						<div class="col-xs-4">
							<g:select name="serviceDuration" from="${['15 min', '30 min', '45 min', '60 min', '90 min', '120 min']}" value="${service.durationInMinutes}" />
						</div>
						<div class="col-xs-4">
							<input type="text" name="serviceCalendarColor" value="${service.calendarColor}" class="color">
						</div>
					</div>
					<div class="row buttons edit-buttons">
						<div class="col-xs-3 button display-order">
							<p>${serviceNumber+1}</p>
							<h2>Display Order</h2>
						</div>
						<div class="col-xs-3 button move-button move-up-button">
							<span class="glyphicon glyphicon-chevron-up"></span>
							<h2>Move</h2>
						</div>
						<div class="col-xs-3 button move-button move-down-button">
							<span class="glyphicon glyphicon-chevron-down"></span>
							<h2>Move</h2>
						</div>
						<div class="col-xs-3 button delete-button">
							<span class="glyphicon glyphicon-remove"></span>
							<h2>Delete</h2>
						</div>
					</div>
					<div class="row buttons save-button">
						<div class="col-xs-6">
							<button type="reset" class="btn white-button cancel-button" value="Reset" onclick="hideButtons(this);">Cancel</button>
						</div>
						<div class="col-xs-6">
							<button type="submit" class="btn green-button save-service-button" value="Submit">Save</button>
						</div>
					</div>
				</form>
		</g:each>
	</div>

	<div>
		<div id="newServiceFormContainer"></div>
	</div>

	<!-- https://github.com/PitPik/tinyColorPicker -->
	<script type="text/javascript" src="${resource(dir:'js', file:'jqColorPicker.min.js')}"></script>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".move-up-button").click(function(event) {
				showMask();
				var currentDisplayOrder = parseInt($(event.target).closest('form').attr('id').substring(14));
				$.ajax({
					type: "POST",
					url: "/admin/moveServiceUp",
					data: { currentDisplayOrder:currentDisplayOrder}
				}).done(function(response) {
					hideMask()
					var obj = JSON && JSON.parse(response) || $.parseJSON(response);
					if (obj.success === true){
						swapUp(currentDisplayOrder);
					}else{
						alert('An error has occured. Please notify support if you continue to receive this message.');
						location.reload()
					}
				});
			});
			$(".move-down-button").click(function(event) {
				showMask();
				var currentDisplayOrder = parseInt($(event.target).closest('form').attr('id').substring(14));
				$.ajax({
					type: "POST",
					url: "/admin/moveServiceDown",
					data: { currentDisplayOrder:currentDisplayOrder}
				}).done(function(response) {
					hideMask()
					var obj = JSON && JSON.parse(response) || $.parseJSON(response);
					if (obj.success === true){
						swapDown(currentDisplayOrder);
					}else{
						alert('An error has occured. Please notify support if you continue to receive this message.');
						location.reload();
					}
				});
			});
			$(".delete-button").confirmOn({
				classPrepend: 'confirmon',
				questionText: 'Delete Service?',
				textYes: 'Yes',
				textNo: 'No'
			},'click', function(event,confirmed) {
				if (confirmed === true){
					showMask();
					var serviceId = $(event.target).closest('form').find('input[name="serviceId"]').val()
					document.location = '/admin/deleteService/'+serviceId
				}
			});
			$('.color').colorPicker({
				renderCallback: function($elm, toggled) {
					var form = $($elm).closest('form');
					editService(form);
				}
			});
			$('input').on('keyup', function() {
				var form = $(this).closest('form');
				editService(form);
			});
			$('select').on('change', function() {
				var form = $(this).closest('form');
				editService(form);
			});
		});
		
		function editService(form){
			var currentlyEditing = form.attr('editing')
			if (currentlyEditing === 'false'){
				form.attr('editing','true');
				form.find('.edit-buttons').slideUp();
				form.find('.save-button').slideDown();
			}
		}
		function hideButtons(button){
			var form = $(button).closest('form');
			form.attr('editing','false')
			form.find('.edit-buttons').slideDown();
			form.find('.save-button').slideUp();
		}
		
		var animating = false;
		function swapUp(currentDisplayOrder){
			if (animating) {
				return;
			}
			var div1 = $("#display-order-"+currentDisplayOrder),
				div2 = div1.prev(),
				distance = div1.outerHeight(),
				nextDisplayOrder = currentDisplayOrder - 1;

			if (div2.length) {
				animating = true;
				$.when(div1.animate({
						top: -distance
					}, 600),
					div2.animate({
						top: distance
					}, 600))
				.done(function () {
					div2.css('top', '0px');
					div1.css('top', '0px');
					div1.insertBefore(div2);
					div1.find('.display-order p').html(nextDisplayOrder);
					div2.find('.display-order p').html(currentDisplayOrder);
					div1.attr('id','display-order-'+nextDisplayOrder);
					div2.attr('id','display-order-'+currentDisplayOrder);
					div1.attr
					animating = false;
				});
			}
		}
		function swapDown(currentDisplayOrder){
			if (animating) {
				return;
			}
			var div1 = $("#display-order-"+currentDisplayOrder),
				div2 = div1.next(),
				distance = div1.outerHeight(),
				nextDisplayOrder = currentDisplayOrder + 1;

			if (div2.length) {
				animating = true;
				$.when(div1.animate({
						top: distance
					}, 600),
					div2.animate({
						top: -distance
					}, 600)
				).done(function () {
					div2.css('top', '0px');
					div1.css('top', '0px');
					div2.insertBefore(div1);
					div1.find('.display-order p').html(nextDisplayOrder);
					div2.find('.display-order p').html(currentDisplayOrder);
					div1.attr('id','display-order-'+nextDisplayOrder);
					div2.attr('id','display-order-'+currentDisplayOrder);
					animating = false;
				});
			}
		}
	</script>

</body></html>