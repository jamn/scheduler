$(document).ready(function(){
	setTimeout(hideAlert, 2000);
	$('#scheduleAppointmentModal').on('show.bs.modal', function (event) {
		var modal = $(this)
		var clickTarget = $(event.relatedTarget)
		var datetime = clickTarget.attr('datetime');
		getScheduleAppointmentForm(datetime, modal)
	});
	$('#clearBlockedTimeModal').on('show.bs.modal', function (event) {
		var modal = $(this)
		var clickTarget = $(event.relatedTarget)
		modal.find('.cancel-this-appointment').click(function(){
			$.ajax({
				type: "POST",
				url: "/admin/cancelAppointment",
				data: { c: clickTarget.attr('c') }
			}).done(function() {
				location.reload()
			});
		});
	});
});

function hideAlert(){
	$('.alert').slideUp()
}

function getTimeSlotOptions(){
	var sId = $('#services').val();
	var aDate = $('#dateOfAppointment').val();
	if (sId != 'Choose a service...'){
		$.ajax({
			type: "POST",
			url: "/admin/getTimeSlotOptions",
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

function toggleAvailabilityForDay(dayIndex){
	console.log(dayIndex);
	var checked = $('#available-'+dayIndex).is(':checked');
	var icon = $('.day-'+dayIndex).find('.glyphicon');
	var times = $('.day-'+dayIndex).find('select');
	if (checked){
		$('.day-'+dayIndex).removeClass('unavailable');
		$('.day-'+dayIndex).addClass('available');
		icon.removeClass('glyphicon-ban-circle');
		icon.addClass('glyphicon-ok-circle');
		times.prop('disabled', false);
	}else{
		$('.day-'+dayIndex).removeClass('available');
		$('.day-'+dayIndex).addClass('unavailable');
		icon.removeClass('glyphicon-ok-circle');
		icon.addClass('glyphicon-ban-circle');
		times.prop('disabled', true);
	}
}

$(document).on('click', '.availability .day-of-week', function(e) {
	if (e.target.type !== 'checkbox') {
		$(this).find('input:checkbox').trigger('click');
	}
});

$(document).on('click', '.availability .glyphicon', function(e) {
	if (e.target.type !== 'checkbox') {
		$(this).closest('.day').find('input:checkbox').trigger('click');
	}
});

function getScheduleAppointmentForm(datetime, modal){
	$.ajax({
		type: "POST",
		url: "/admin/getScheduleAppointmentForm",
		data: { d:datetime }
	}).done(function(response) {
		if (response.indexOf("ERROR") === -1){
			$(".modal-body").html(response);
		}
	});
}

function getTimeSlotOptionsForRescheduledAppointment(aId){
	var sId = $('#servicesForRescheduledAppointment-'+aId).val();
	var aDate = $('#dateOfRescheduledAppointment-'+aId).val();
	if (sId != 'Choose a service...'){
		$.ajax({
			type: "POST",
			url: "/admin/getTimeSlotOptions",
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

$(document).on('click', '#addClient', function(e) {
	$.ajax({
		type: "POST",
		url: "/admin/getClientDataForm"
	}).done(function(response) {
		if (response.indexOf("ERROR") > -1){
			alert('An error has occured. Please try again.');
		}else{
			$('#lastNameFilters').slideUp();
			$('#clientsDetailsSelector').slideUp();
			$('#clientDetails').slideUp();
			$('#addClient').fadeOut();
			$('#clientInfoForm').html(response).slideDown();
		}
	});
});

$(document).on('click', '#editClient', function(e) {
	var cId = $("#clientsDetailsSelector").val();
	$.ajax({
		type: "POST",
		url: "/admin/getClientDataForm",
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

$(document).on('click', '.last-name-filter', function(e) {
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
	$.ajax({
		type: "POST",
		url: "/admin/getClientsSelectMenu",
		data: {lastNameStartsWith: lastNameStartsWith}
	}).done(function(confirmation) {
		var success = confirmation.search('"success":false');
		if (success === -1){
			$("#clientsDetailsSelector").html(confirmation);
		}
	});

}

$(document).on('click', '#saveClientButton', function(e) {
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
		$.ajax({
			type: "POST",
			url: "/admin/saveClient",
			data: {email:email, hp:email2, password:password, firstName:firstName, lastName:lastName, phoneNumber:phoneNumber, cId:cId}
		}).done(function(confirmation) {
			var success = confirmation.search('"success":false');
			if (success === -1){
				hideClientRegistrationForm();
				alert('User saved!');
			}
		});
	}
});

$(document).on('click', '#cancelClientRegistrationButton', function(e) {
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
	$.ajax({
		type: "POST",
		url: "/admin/getClientDetails",
		data: { cId:cId }
	}).done(function(response) {
		var success = response.search('"success":false');
		if (success === -1){
			$('#clientDetails').html(response).fadeIn();
		}
	});
});

$(document).on('click', '#saveTextButton', function(e) {
	var message = $('#homepageText').val();
	$.ajax({
		type: "POST",
		url: "/admin/saveHomepageMessage",
		data: { m: message }
	}).done(function(response) {
		var jsonResponse = JSON.parse(response);
		if (jsonResponse.success === true){
			$('#saveTextButton').html("Success");
			$('#saveTextButton').removeClass('error-button');
		}
		else{
			$('#saveTextButton').html("Error");
			$('#saveTextButton').addClass('error-button');
		}
		window.location.reload()
	});

});

$(document).on('click', '#saveClientNotesButton', function(e) {
	var notes = encodeURIComponent($('#clientNotes').val());
	$.ajax({
		type: "POST",
		url: "/admin/saveClientNotes",
		data: { n:notes }
	});
});



$(document).on('click', '#blockOffTimeButton', function(e) {
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

	$.ajax({
		type: "POST",
		url: "/admin/saveBlockedTime",
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



$(document).on('click', '#blockOffDaysButton', function(e) {
	var from = $('#fromWholeDay').val();
	var to = $('#toWholeDay').val();

	$(this).html($('#waitingSpinner').html());

	$.ajax({
		type: "POST",
		url: "/admin/blockOffWholeDay",
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

$(document).on('click', '.blocked-time', function(e) {
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


$(document).on('click', '#deleteBlockedTimeslotsButton', function(e) {
	var button = $('#deleteBlockedTimeslotsButton');
	$(button).html($('#waitingSpinner').html());
	$('.blocked-timeslots-table tr:hidden').remove();
	$.ajax({
		type: "POST",
		url: "/admin/clearBlockedTime",
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

$(document).on('click', '#recurringAppointment', function() {
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
	$.ajax({
		type: "POST",
		url: "/admin/getRescheduleOptions",
		data: { aId:appointmentId }
	}).done(function(response) {
		if (response.indexOf("ERROR") === -1){
			$("#edit-appointment-options").html(response);
		}
	});
}

function rescheduleAppointment(appointmentId){
	var sId = $('#servicesForRescheduledAppointment-'+appointmentId).val();
	var aDate = $('#dateOfRescheduledAppointment-'+appointmentId).val();
	var sTime = $('#timeSlotsForRescheduledAppointment-'+appointmentId).val();
	$('#rescheduleButton-'+appointmentId).html($('.spinner').html());
	$.ajax({
		type: "POST",
		url: "/admin/rescheduleAppointment",
		data: { aId:appointmentId, sId:sId, aDate:aDate, sTime:sTime }
	}).done(function(response) {
		var jsonResponse = JSON.parse(response);
		if (jsonResponse.success === true){
			$('#rescheduleButton-'+appointmentId).html('Success');
			$('#rescheduleButton-'+appointmentId).removeClass('error-button');
			setTimeout(function() {$('#rescheduleAppointmentModal').modal('toggle');},400);
			setTimeout(function() {window.location.reload();},1250);
		}else{
			$('#rescheduleButton-'+appointmentId).html('Error');
			$('#rescheduleButton-'+appointmentId).addClass('error-button');
		}
	});
}


