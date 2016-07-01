$(document).ready(function(){
	$('.recurring-appointment-options').hide()
});

$(document).on("click", "#recurringAppointmentCheckbox", function() {
	$(".recurring-appointment-options").fadeToggle()
});

$(document).on("click", "a[href]", function() {
	showMask()
});

$(document).on("click", ".home-link", function() {
	document.location = '/book'
});

function showMask(){
	$("#mask").fadeIn()
}