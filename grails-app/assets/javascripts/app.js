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
			document.location = '/book/confirmedCancelAppointment?c='+button.attr('data-appointmentCode')
		})
	})
});


function showMask(){
	$("#mask").fadeIn()
}

function showRegistrationForm() {
	$('.right-divider').show();
	$('#password-Book').show();
	$('#resetPassword').show();
	$('#showLoginForm').show();

	$('.left-divider').hide();
	$('#registerLink').hide();

	$('.new-user').slideDown();

	$('.errorDetails').slideUp();

	$('#loginButton').removeClass('errorButton');
	$('#loginButton').attr("value", "Register & Book");

	$('.reminders').slideDown();
	togglePhoneNumber();

	$('#loginForm').attr("action", "attemptLogin")
}

function showResetPasswordForm() {
	$('.right-divider').hide();
	$('.left-divider').show();

	$('#password-Book').hide();

	$('#resetPassword').hide();

	$('#registerLink').show();

	$('#showLoginForm').show();

	$('.new-user').slideUp();
	
	$('.errorDetails').slideUp();

	$('#phoneNumber').slideUp();

	$('.reminders').slideUp();

	$('#loginButton').removeClass('errorButton');
	$('#loginButton').attr("value", "Send Reset Email");

	$('#loginForm').attr("action", "sendPasswordResetEmail")
}

function showLoginForm() {
	$('.right-divider').hide();
	$('.left-divider').show();

	$('#password-Book').show();

	$('#resetPassword').show();
	
	$('#registerLink').show();
	
	$('#showLoginForm').hide();
	
	$('.new-user').slideUp();

	$('.errorDetails').slideUp();

	$('#loginButton').removeClass('errorButton');
	$('#loginButton').attr("value", "Book Appointment");

	$('.reminders').slideDown();
	togglePhoneNumber();
	
	$('#loginForm').attr("action", "attemptLogin")
}

function logout() {
	$(".user-details").slideUp();
	$(".logged-in").slideDown();
	$('.new-user').slideUp();
	$("#loggedIn").val("false");
}

function togglePhoneNumber(){
	if( $('#textMessageReminder').is(':checked')) {
		$("#phoneNumber").slideDown();
	} else {
		$("#phoneNumber").slideUp();
	}
}
