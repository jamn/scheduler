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
	$('#addClientModal').on('show.bs.modal', function (event) {
		$('#scheduleAppointmentModal').modal('hide')
		getAddClientForm()
	});
});

function hideAlert(){
	$('.alert').slideUp();
}

function showMask(){
	$("#mask").fadeIn();
}

function hideMask(){
	$("#mask").fadeOut();
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
			$("#scheduleAppointmentForm").html(response);
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

$(document).on('click', '#addService', function(e) {
	$.ajax({
		type: "POST",
		url: "/admin/getNewServiceForm"
	}).done(function(response) {
		if (response.indexOf("ERROR") > -1){
			alert('An error has occured. Please try again.');
		}else{
			$('.services').slideUp();
			$('#addService').fadeOut();
			$('#newServiceFormContainer').html(response).slideDown();
		}
	});
});

$(document).on('click', '#addClient', function(e) {
	getAddClientForm()
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

function getAddClientForm(){
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
			console.log(response)
			$('#clientInfoForm').html(response).slideDown();
		}
	});
}

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


