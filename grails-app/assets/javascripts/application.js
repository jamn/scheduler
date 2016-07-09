$(document).on("tap", ".home-link", function() {
	window.location.href = "./site";
});

$(document).on("tap", "#newAddress", function() {
	$('html, body').animate({
		scrollTop: $("#address").offset().top
	}, 2000);
	if ($(".google-map").is(":visible")){
		// $(".google-map").slideUp();
	}
	else{
		$(".google-map").slideDown();
	}
});

$(document).on("tap", ".address", function() {
	if ($(".google-map").is(":visible")){
		$(".google-map").slideUp();
	}
	else{
		$(".google-map").slideDown();
	}
});

$(document).on("tap", ".google", function() {
	var url = "https://local.google.com/maps/ms?msid=203990239811952052030.0004de4cdb5c11e4fc186&msa=0";
	var windowName = "Directions to The Den";
	window.open(url, windowName, "height=600,width=900");
});

$(document).on("tap", "#bookNowButton", function() {
	var disabled = $(this).attr("disabled");
	if (disabled != "disabled") {
		disableButton(this);
		var data = { u: "kp" }
		var nextPage = "services";
		getPageContent(this, data, nextPage);
	}
});

$(document).on("tap", ".service", function() {
	var disabled = $(this).attr("disabled");
	if (disabled != "disabled") {
		disableButton(this);
		var service = this.getAttribute("service");
		var date = $('#chooseDate').val();
		var data = {s: service, d: date}
		var nextPage = "timeSlots";
		getPageContent(this, data, nextPage);
	}
});

$(document).on("tap", ".time-slot", function() {
	var disabled = $(this).attr("disabled");
	if (disabled != "disabled") {
		disableButton(this);
		var date = this.getAttribute("starttime");
		var recurringAppointment = $('#recurringAppointmentCheckbox').is(':checked');
		var repeatDuration = $('#repeatDuration').val();
		var repeatNumberOfAppointments = $('#repeatNumberOfAppointments').val();
		var data = {d: date, r: recurringAppointment, dur: repeatDuration, num: repeatNumberOfAppointments}
		var nextPage = "loginForm";
		getPageContent(this, data, nextPage);
	}
});

$(document).on("tap", "#registerLink", function() {
	$('.right-divider').show();
	$('#password-Book').show();
	$('#resetPassword').show();
	$('#showLoginForm').show();

	$('.left-divider').hide();
	$('#registerLink').hide();

	$('.new-user').slideDown();

	$('.errorDetails').slideUp();

	$('#loginButton').attr("sendPasswordResetEmail", false);
	$('#loginButton').attr("attemptPasswordReset", false);

	$('#loginButton').removeClass('errorButton');
	$('#loginButton .as-button-label').text("Register & Book");

	$('.reminders').slideDown();
	togglePhoneNumber();
});

$(document).on("tap", "#resetPassword", function() {
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
	
	$('#loginButton').attr("sendPasswordResetEmail", true);
	$('#loginButton').attr("attemptPasswordReset", false);

	$('#loginButton').removeClass('errorButton');
	$('#loginButton .as-button-label').text("Send Reset Email");
});

$(document).on("tap", "#showLoginForm", function() {
	$('.right-divider').hide();
	$('.left-divider').show();

	$('#password-Book').show();

	$('#resetPassword').show();
	
	$('#registerLink').show();
	
	$('#showLoginForm').hide();
	
	$('.new-user').slideUp();

	$('.errorDetails').slideUp();
	
	$('#loginButton').attr("sendPasswordResetEmail", false);
	$('#loginButton').attr("attemptPasswordReset", false);

	$('#loginButton').removeClass('errorButton');
	$('#loginButton .as-button-label').text("Book Appointment");

	$('.reminders').slideDown();
	togglePhoneNumber();
});

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

$(document).on("tap", "#loginButton", function() {
	var disabled = $(this).attr("disabled");
	$('#loginButton').removeClass('error-button');

	if (disabled != "disabled"){
		$(this).attr("disabled", "disabled");
		if ($(this).attr("sendPasswordResetEmail") === "true"){
			sendPasswordResetEmail();
		}else if ($(this).attr("attemptPasswordReset") === "true"){
			attemptPasswordReset();
		}else{
			bookAppointment();
		}
	}
});

$(document).on("tap", "#cancelAppointmentLoginButton", function() {
	var disabled = $(this).attr("disabled");
	$('#cancelAppointmentLoginButton').removeClass('error-button');
	if (disabled != "disabled"){
		$(this).attr("disabled", "disabled");
		cancelAppointment();
	}
});

$(document).on("keypress", "#password-Book", function() {
	if (event.keyCode === 13) {
		$('#loginButton').removeClass('error-button');
		bookAppointment();
	}
});

$(document).on("keypress", "#password-Cancel", function() {
	if (event.keyCode === 13) {
		$('#cancelAppointmentLoginButton').removeClass('error-button');
		cancelAppointment();
	}
});

$(document).on("keypress", "#verifyNewPassword", function() {
	if (event.keyCode === 13) {
		$('#loginButton').removeClass('error-button');
		attemptPasswordReset();
	}
});


function sendPasswordResetEmail(){
	var email = $('#email').val();
	$('#loginButton .as-button-label').hide();
	$('#loginButton .spinner').show();
	$.ajax({
		type: "POST",
		url: "sendPasswordResetEmail",
		data: {e:email}
	}).done(function(confirmation) {
		processResults(confirmation);
	});
}

function attemptPasswordReset(){
	var p1 = $('#newPassword').val();
	var p2 = $('#verifyNewPassword').val();
	$('#loginButton .as-button-label').hide();
	$('#loginButton .spinner').show();
	$.ajax({
		type: "POST",
		url: "attemptPasswordReset",
		data: {p1:p1, p2:p2}
	}).done(function(confirmation) {
		processResults(confirmation)
	});
}

function bookAppointment(){
	var loggedIn = $('#loggedIn').val();
	var email = $('#email').val();
	var password = $('#password-Book').val();
	var firstName = $('#firstName').val();
	var lastName = $('#lastName').val();
	var phoneNumber = $('#phoneNumber').val();
	var emailReminder = $('#emailReminder').is(':checked');
	var textMessageReminder = $('#textMessageReminder').is(':checked');
	var rememberMe = $('#rememberMe').is(':checked');
	$('#loginButton .as-button-label').hide();
	$('#loginButton .spinner').show();
	$.ajax({
		type: "POST",
		url: "bookAppointment",
		data: {loggedIn:loggedIn, e:email, p:password, f:firstName, l:lastName, ph:phoneNumber, eRmndr:emailReminder, tRmndr:textMessageReminder, remember:rememberMe}
	}).done(function(confirmation) {
		processResults(confirmation);
	});
}

function processResults(confirmation){
	var success = confirmation.search('"success":false')
	if (success > -1){
		var results = JSON && JSON.parse(confirmation) || $.parseJSON(confirmation);
		$('#loginButton .spinner').hide();
		$('#loginButton .as-button-label').show();
		$('#loginButton').attr("disabled", false);
		$('#loginButton').addClass('error-button').fadeIn();

		$('.errorDetails').html(results.errorMessage);
		$('.errorDetails').slideDown();
	}
	else{
		loadPageContent("loginForm", "confirmation", confirmation);
	}
}

function processCancelAppointmentResults(confirmation){
	var success = confirmation.search('"success":false')
	if (success > -1){
		var results = JSON && JSON.parse(confirmation) || $.parseJSON(confirmation);
		$('#cancelAppointmentLoginButton .spinner').hide();
		$('#cancelAppointmentLoginButton .as-button-label').show();
		$('#cancelAppointmentLoginButton').attr("disabled", false);
		$('#cancelAppointmentLoginButton').addClass('error-button');

		$('.errorDetails').html(results.errorMessage);
		$('.errorDetails').slideDown();
	}
	else{
		loadPageContent("loginForm", "confirmation", confirmation);
	}
}

function cancelAppointment(){
	var cancelAppointmentLoginButton = $('#cancelAppointmentLoginButton');
	var disabled = cancelAppointmentLoginButton.attr("disabled");
	cancelAppointmentLoginButton.removeClass('error-button');
	if (disabled != "disabled"){
		cancelAppointmentLoginButton.attr("disabled", "disabled");
		var email = $('#email').val();
		var password = $('#password-Cancel').val();
		$('#cancelAppointmentLoginButton .as-button-label').hide();
		$('#cancelAppointmentLoginButton .spinner').show();
		$.ajax({
			type: "POST",
			url: "cancelAppointment",
			data: {e:email, p:password}
		}).done(function(confirmation) {
			processCancelAppointmentResults(confirmation);
		});
	}
}

function disableButton(button){
	$(button).attr("disabled", "disabled");
	$(button).find('.as-button-label').hide();
	$(button).find('.spinner').show();
}

function getPageContent(button, data, nextPage){
	var currentPage = $(button).closest(".page").attr("page");
	if (typeof currentPage != 'undefined' && typeof nextPage != 'undefined'){
		var action = "get" + nextPage.charAt(0).toUpperCase() + nextPage.slice(1)
		$.ajax({
			type: "POST",
			url: action,
			data: data
		}).done(function(response) {
			loadPageContent(currentPage, nextPage, response);
		}).fail(function(){
			$(button).html("Error");
			$(button).addClass('error-button');
		});
	}
}

function loadPageContent(currentPage, nextPage, pageContent){
	$('.main-content').css("background-color", "white");
	$('.main-content .page[page="'+currentPage+'"]').empty();
	$('.main-content .page[page="'+currentPage+'"]').slideUp();

	$('.main-content .page[page="'+nextPage+'"]').append(pageContent);
	$('.main-content .page[page="'+nextPage+'"]').slideDown();
}
