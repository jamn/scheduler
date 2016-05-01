$( document ).ready(function() {
	getSection('fourteenDayView');
});

function getTimeSlotOptions(){
	var sId = $('#services').val();
	var aDate = $('#dateOfAppointment').val();
	var baseUrl = $('body').attr('baseUrl');
	if (sId != 'Choose a service...'){
		$.ajax({
			type: "POST",
			url: baseUrl + "/getTimeSlotOptions",
			data: { sId:sId, aDate:aDate}
		}).done(function(response) {
			if (response.indexOf("ERROR") > -1){
				$('#bookForClientButton').html("Error");
				$('#bookForClientButton').addClass('error-button');
			}else{
				$('#timeSlots').html(response);
				$('#bookForClientButton').html("Book Appointment");
				$('#bookForClientButton').removeClass('error-button');
			}
		});
	}
}

function getTimeSlotOptionsForRescheduledAppointment(aId){
	var sId = $('#servicesForRescheduledAppointment-'+aId).val();
	var aDate = $('#dateOfRescheduledAppointment-'+aId).val();
	var baseUrl = $('body').attr('baseUrl');
	if (sId != 'Choose a service...'){
		$.ajax({
			type: "POST",
			url: baseUrl + "/getTimeSlotOptions",
			data: { sId:sId, aDate:aDate}
		}).done(function(response) {
			if (response.indexOf("ERROR") > -1){
				$('#timeSlotsForRescheduledAppointment-'+aId).html("<option>No times available</option>");
			}else{
				$('#timeSlotsForRescheduledAppointment-'+aId).html(response);
			}
		});
	}
}

$(document).on("tap", ".main", function() {
	var navMenuExpanded = ($('.navbar-collapse').attr('class').indexOf("in") >= 0);
	if(navMenuExpanded){
		$(".navbar-toggle").click();
	}
});

$(document).on('tap', '.nav a', function(e) {
	var section = $(this).attr("href");
	if (section === "#logout") {
		window.location.href = "./access/logout";
	}
	else {
		getSection(section);
		$(".navbar-toggle").click();
	}
});

function getSection(section){
	$('#mask').fadeIn();
	var baseUrl = $('body').attr('baseUrl');
	$.ajax({
		type: "POST",
		url: baseUrl + "/getSection",
		data: {section:section}
	}).done(function(response) {
		$('.active').removeClass("active");
		$('a[href="#'+section+'"]').parent().addClass("active");
		$('.main').html(response);
		$('#mask').fadeOut();
	});
}

$(document).on('tap', '#addClient', function(e) {
	var baseUrl = $('body').attr('baseUrl');
	$.ajax({
		type: "POST",
		url: baseUrl + "/getClientDataForm"
	}).done(function(response) {
		if (response.indexOf("ERROR") > -1){
			alert('Dang... you broke it.');
		}else{
			$('#lastNameFilters').slideUp();
			$('#clientsDetailsSelector').slideUp();
			$('#clientDetails').slideUp();
			$('#addClient').fadeOut();
			$('#clientInfoForm').html(response).slideDown();
		}
	});
});

$(document).on('tap', '#editClient', function(e) {
	var cId = $("#clientsDetailsSelector").val();
	var baseUrl = $('body').attr('baseUrl');
	$.ajax({
		type: "POST",
		url: baseUrl + "/getClientDataForm",
		data: { cId:cId}
	}).done(function(response) {
		if (response.indexOf("ERROR") > -1){
			alert('Dang... you broke it.');
		}else{
			$('#lastNameFilters').slideUp();
			$('#clientsDetailsSelector').slideUp();
			$('#clientDetails').slideUp();
			$('#addClient').fadeOut();
			$('#clientInfoForm').html(response).slideDown();
		}
	});
});

$(document).on('tap', '.last-name-filter', function(e) {
	var lastNameStartsWith = $(e.currentTarget).attr('value');
	$("#clientsDetailsSelector").slideDown();
	if (lastNameStartsWith === "Reset"){
		getClientSelectMenuData();
		$('.reset-search').fadeOut();
	}
	else {
		$('.reset-search').fadeIn();
		getClientSelectMenuData(lastNameStartsWith);

	}
});

function getClientSelectMenuData(lastNameStartsWith){
	var baseUrl = $('body').attr('baseUrl');
	$.ajax({
		type: "POST",
		url: baseUrl+"/getClientsSelectMenu",
		data: {lastNameStartsWith: lastNameStartsWith}
	}).done(function(confirmation) {
		var success = confirmation.search('"success":false');
		if (success === -1){
			$("#clientsDetailsSelector").html(confirmation);
		}
	});

}

$(document).on('tap', '#saveClientButton', function(e) {
	var email = $('#e').val();
	var email2 = $('#e2').val();
	var password = $('#p').val();
	var firstName = $('#f').val();
	var lastName = $('#l').val();
	var phoneNumber = $('#ph').val();
	var cId = $("#cId").val(); 
	if (email && password && firstName && lastName){
		$('#saveClientButton .label').hide();
		$('#saveClientButton .spinner').show();
		var baseUrl = $('body').attr('baseUrl');
		$.ajax({
			type: "POST",
			url: baseUrl+"/saveClient",
			data: {e:email, hp:email2, p:password, f:firstName, l:lastName, ph:phoneNumber, cId:cId}
		}).done(function(confirmation) {
			var success = confirmation.search('"success":false');
			if (success === -1){
				hideClientRegistrationForm();
				alert('User saved!');
			}
		});
	}
});

$(document).on('tap', '#cancelClientRegistrationButton', function(e) {
	hideClientRegistrationForm();
});

function hideClientRegistrationForm(){
	$('#clientInfoForm').slideUp();
	$('#clientsDetailsSelector').slideDown();
	$('#lastNameFilters').slideDown();
	$('#clientDetails').slideDown();
	$('#addClient').fadeIn();
}

$(document).on('change', '#clientsDetailsSelector', function() {
	$(this).slideUp();
	$('.reset-search').fadeIn();
	var cId = $(this).val();
	var baseUrl = $('body').attr('baseUrl');
	$.ajax({
		type: "POST",
		url: baseUrl + "/getClientDetails",
		data: { cId:cId }
	}).done(function(response) {
		var success = response.search('"success":false');
		if (success === -1){
			$('#clientDetails').html(response).fadeIn();
		}
	});
});

$(document).on('tap', '#saveTextButton', function(e) {
	var message = $('#homepageText').val();
	var baseUrl = $('body').attr('baseUrl');
	$.ajax({
		type: "POST",
		url: baseUrl + "/saveHomepageMessage",
		data: { m: message }
	});
});

$(document).on('tap', '#saveClientNotesButton', function(e) {
	var notes = encodeURIComponent($('#clientNotes').val());
	var baseUrl = $('body').attr('baseUrl');
	$.ajax({
		type: "POST",
		url: baseUrl + "/saveClientNotes",
		data: { n:notes }
	});
});



$(document).on('tap', '#blockOffTimeButton', function(e) {
	var date = $('#chooseDateToBlockOff').val();
	var fromHour = $('#fromHour').val();
	var fromMinute = $('#fromMinute').val();
	var fromMorningOrAfternoon = $('#fromMorningOrAfternoon').val();
	var toHour = $('#toHour').val();
	var toMinute = $('#toMinute').val();
	var toMorningOrAfternoon = $('#toMorningOrAfternoon').val();
	var from = fromHour + ":" + fromMinute + fromMorningOrAfternoon;
	var to = toHour + ":" + toMinute + toMorningOrAfternoon;

	$('#blockOffTimeButton').html($('#waitingSpinner').html());

	var baseUrl = $('body').attr('baseUrl');
	$.ajax({
		type: "POST",
		url: baseUrl + "/blockOffTime",
		data: { date:date, from:from, to:to}
	}).done(function(response) {
		var jsonResponse = JSON.parse(response);
		if (jsonResponse.success === true){
			$('#blockOffTimeButton').html("Success");
			$('#blockOffTimeButton').removeClass('error-button');
		}
		else{
			$('#blockOffTimeButton').html("Error");
			$('#blockOffTimeButton').addClass('error-button');
		}
	});
});



$(document).on('tap', '#blockOffDaysButton', function(e) {
	var from = $('#fromWholeDay').val();
	var to = $('#toWholeDay').val();

	$(this).html($('#waitingSpinner').html());

	var baseUrl = $('body').attr('baseUrl');
	$.ajax({
		type: "POST",
		url: baseUrl + "/blockOffWholeDay",
		data: { from:from, to:to}
	}).done(function(response) {
		var jsonResponse = JSON.parse(response);
		if (jsonResponse.success === true){
			$('#blockOffDaysButton').html("Success");
			$('#blockOffDaysButton').removeClass('error-button');
		}
		else{
			$('#blockOffDaysButton').html("Error");
			$('#blockOffDaysButton').addClass('error-button');
		}
	});
});

$(document).on('tap', '.blocked-time', function(e) {
	var checkbox = $(this).find('input:checkbox');
	var tableRow = $(this);
	if (e.target.type !== 'checkbox') {
      $(checkbox).trigger('click');
    }
	var isChecked = $(checkbox).is(':checked');
	if (isChecked === true) {
		$(tableRow).addClass('deleted');
	}
	else {
		$(tableRow).removeClass('deleted');
	}
});


$(document).on('tap', '#deleteBlockedTimeslotsButton', function(e) {
	var button = $('#deleteBlockedTimeslotsButton');
	$(button).html($('#waitingSpinner').html());
	var baseUrl = $('body').attr('baseUrl');
	$('.blocked-timeslots-table tr:hidden').remove();
	$.ajax({
		type: "POST",
		url: baseUrl + "/clearBlockedTime",
		data: $('#blockedTimesForm').serialize()
	}).done(function(response) {
		var jsonResponse = JSON.parse(response);
		if (jsonResponse.success === true){
			$(button).html("Success");
			$(button).removeClass('error-button');
			var deletedTimeslots = jsonResponse.deletedTimeslots;
			var arrayLength = deletedTimeslots.length;
			for (var i = 0; i < arrayLength; i++) {
				$('#blockedTime'+deletedTimeslots[i]).fadeOut('slow');
			}
		}
		else{
			$(button).html("Error");
			$(button).addClass('error-button');
		}
	});
});

$(document).on('tap', '#recurringAppointment', function() {
	var opts = $(".recurringAppointmentAdminOptions");
	if ($(opts).is(":visible")){
		$(opts).fadeOut();
	}else{
		$(opts).fadeIn();
	}
});

$(document).on('change', '#services', function() {
	getTimeSlotOptions();
});

$(document).on('change', '#dateOfAppointment', function() {
	getTimeSlotOptions();
});

function getRescheduleOptions(appointmentId){
	var baseUrl = $('body').attr('baseUrl');
	$.ajax({
		type: "POST",
		url: baseUrl + "/getRescheduleOptions",
		data: { aId:appointmentId }
	}).done(function(response) {
		if (response.indexOf("ERROR") === -1){
			$("#edit-appointment-options").html(response);
			$('#dateOfRescheduledAppointment-'+appointmentId).datepicker( {
				minDate: 0
			});
			$('#dateOfRescheduledAppointment-'+appointmentId).datepicker("setDate", new Date());
			$('#servicesForRescheduledAppointment-'+appointmentId).on('change', function() {
				getTimeSlotOptionsForRescheduledAppointment(appointmentId);
			});
			$('#dateOfRescheduledAppointment-'+appointmentId).on('change', function() {
				getTimeSlotOptionsForRescheduledAppointment(appointmentId);
			});
			$('#rescheduleButton-'+appointmentId).on('tap', function() {
				var sId = $('#servicesForRescheduledAppointment-'+appointmentId).val();
				var aDate = $('#dateOfRescheduledAppointment-'+appointmentId).val();
				var sTime = $('#timeSlotsForRescheduledAppointment-'+appointmentId).val();
				$('#rescheduleButton-'+appointmentId).html($('.spinner').html());
				$.ajax({
					type: "POST",
					url: baseUrl + "/rescheduleAppointment",
					data: { aId:appointmentId, sId:sId, aDate:aDate, sTime:sTime }
				}).done(function(response) {
					var jsonResponse = JSON.parse(response);
					if (jsonResponse.success === true){
						$('#rescheduleButton-'+appointmentId).html('Success');
						$('#rescheduleButton-'+appointmentId).removeClass('error-button');
						setTimeout(function() {$('#rescheduleAppointmentModal').modal('toggle');},400);
						setTimeout(function() {window.location.href = "./admin";},1250);
					}else{
						$('#rescheduleButton-'+appointmentId).html('Error');
						$('#rescheduleButton-'+appointmentId).addClass('error-button');
					}
				});
			});
		}
	});
}



