$(document).ready(function(){
	$('.recurring-appointment-options').hide()
	
	$(document).on("click", "#recurringAppointmentCheckbox", function() {
		$(".recurring-appointment-options").fadeToggle()
	});

	$(document).on("click", "a[href]", function() {
		showMask()
	});

	$(document).on("click", ".home-link", function() {
		document.location = '/book'
	});

	$('#cancelAppointmentModal').on('show.bs.modal', function (event) {
		var button = $(event.relatedTarget)
		var modal = $(this)
		modal.find('.cancel-this-appointment').click(function(){
			document.location = '/book/confirmedCancelAppointment?c='+button.data('appointmentCode')
		})
	})
});


function showMask(){
	$("#mask").fadeIn()
}