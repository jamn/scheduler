<html>
<head></head><body>
	<h1>Services</h1>
	<div id="addService">
		<div class="add-service green-button">+</div>
		<div class="add-service-label">Add Service</div>
	</div>
	<hr />
	<div class="services">
		<g:each in="${services}">
				<form action="saveService" method="post" class="row service service-${it.id} grey-gradient" editing="false">
					<input type="hidden" name="serviceId" value="${it.id}">
					<div class="row">
						<div class="col-xs-12">
							<input type="text" name="serviceDescription" value="${it.description}" class="service-name">
						</div>
						
					</div>
					<div class="row">
						<div class="col-xs-4">
							$ <input type="text" name="servicePrice" value="${it.price}" style="width:70%">
						</div>
						<div class="col-xs-4">
							<g:select name="serviceDuration" from="${['15 min', '30 min', '45 min', '60 min', '90 min', '120 min']}" value="${it.durationInMinutes}" />
						</div>
						<div class="col-xs-4">
							<input type="text" name="serviceCalendarColor" value="${it.calendarColor}" class="color">
						</div>
					</div>
					<div class="row buttons edit-buttons">
						<div class="col-xs-3 button display-order">
							<p>${it.displayOrder}</p>
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
	</script>

</body></html>