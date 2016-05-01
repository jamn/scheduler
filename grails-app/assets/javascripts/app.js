$(document).ready(function(){
	$('.recurring-appointment-options').hide();
});

$(document).on("click", "#recurringAppointmentCheckbox", function() {
	$(".recurring-appointment-options").fadeToggle();
});

$(document).on("click", "a[href]", function() {
	$("#mask").fadeIn();
});