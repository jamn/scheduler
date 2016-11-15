package jamnApps.scheduler

import grails.util.Environment
import java.io.IOException
import com.sendgrid.*

class EmailService {

	def grailsLinkGenerator
	def groovyPageRenderer

	static SENDGRID_API_KEY = "SG.-gXcHLvSQ_6kLAce7Wl0qw.GkRZPX6VRc_BWy7CZ0nZL0i_pwUnUHG7pAwQ6weX6IA"

	public sendClientConfirmation(List appointments){
		println "Sending email confirmation for appointment(s): "
		def from = "kalin@thedenbarbershop-kc.com"
		def to = "${appointments[0].client.email}"
		def subject = "Appointment Booked @ The Den Barbershop"
		def body = "<p><img style='height:120px;width:120px;' src='${getLink()}/static/logo.png'></p><p>"+appointments[0].client.firstName+",</p><p>The following appointment has been scheduled for you:</p><ul>"
		appointments.each(){ appointment ->
			println "    - " + appointment.client.getFullName() + " | " + appointment.service.description + " on " + appointment.appointmentDate.format('E MM/dd @ hh:mm a')
			body += "<li>A <b>${appointment.service.description}</b> on ${appointment.appointmentDate.format('E MM/dd @ hh:mm a')}<br/>"
			body += "&nbsp;&nbsp;&nbsp;&nbsp;reschedule:<br/>"
			body += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='${getLink()}/book/modifyAppointment?a="+appointment.id+"&cc="+appointment.client.code+"'>${getLink()}/book/modifyAppointment?a="+appointment.id+"&cc="+appointment.client.code+"</a><br/>"
			body += "<br/>"
			body += "&nbsp;&nbsp;&nbsp;&nbsp;cancel:<br/>"
			body += "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a href='${getLink()}/book/cancelAppointment?c="+appointment.code+"'>${getLink()}/book/cancelAppointment?c="+appointment.code+"</a></li>"
		}
		body += "</ul><p>See you then!</p><p>Kalin</p>"
		body += "<p><b>Please Note:</b> <i>I am having an issue with last minute cancelations. Starting 3/1/15 I will be implementing a cancelation policy. I need 4 hours notice for a cancelation/rescheduled appointment. This gives me time to potentially fill that gap. There will be a \$20 charge at your following appointment if you cancel within 4 hours of your appointment. Thank you for understanding.</i></p>"
		try {
			sendMailUsingSendGrid(from,to,subject,body)
		}
		catch(Exception e) {
			println "ERROR"
			println e
		}
	}

	private sendServiceProviderConfirmation(List appointments){
		println "    sending confirmation to service provider"
		try {
			def from = "${appointments[0].client.email}"
			def to = "kalin@thedenbarbershop-kc.com"
			def subject = "New Appointment [${appointments[0].appointmentDate.format('E MM/dd @ hh:mm a')}]"
			def body = "<p><img style='height:120px;width:120px;' src='${getLink()}/static/logo.png'></p><p><b>Client:</b> ${appointments[0].client.firstName} ${appointments[0].client.lastName}<br/><b>Phone:</b> ${appointments[0].client.phone}<br/><b>Email:</b> <a href='mailto:${appointments[0].client.email}'>${appointments[0].client.email}</a><br/><b>Service:</b> ${appointments[0].service.description}<br/><b>Time(s):</b> "
			appointments.eachWithIndex(){ appointment,index ->
				if (index > 0){
					body += " | "
				}
				body += "${appointment.appointmentDate.format('E MM/dd @ hh:mm a')}"
			}
			body += "</p>"
			sendMailUsingSendGrid(from,to,subject,body)
		}
		catch(Exception e) {
			println "ERROR"
			println e
		}
	}

	private sendCancellationNoticeToClient(Appointment appointment){
		println "    sending email to client"
		def from = "kalin@thedenbarbershop-kc.com"
		def to = "${appointment.client.email}"
		def subject = "** Appointment Cancelled ** [${appointment.appointmentDate.format('E MM/dd @ hh:mm a')}]"     
		def body = "<p><img style='height:120px;width:120px;' src='${getLink()}/static/logo.png'></p><p>Your appointment for a ${appointment.service.description} on ${appointment.appointmentDate.format('E MM/dd @ hh:mm a')} has been cancelled. Thank you.</p>"
		try {
			sendMailUsingSendGrid(from,to,subject,body)
		}
		catch(Exception e) {
			println "ERROR"
			println e
		}
	}

	private sendCancellationNoticeToServiceProvider(Appointment appointment){
		println "    sending email to service provider"
		def from = "${appointment.client.email}"
		def to = "kalin@thedenbarbershop-kc.com"
		def subject = "** Appointment Cancelled ** [${appointment.appointmentDate.format('E MM/dd @ hh:mm a')}]"
		def body = "<p><img style='height:120px;width:120px;' src='${getLink()}/static/logo.png'></p><p><b>Client:</b> ${appointment.client.firstName} ${appointment.client.lastName}<br/><b>Time:</b> ${appointment.appointmentDate.format('E MM/dd @ hh:mm a')}<br/><b>Service:</b> ${appointment.service.description}</p>"
		try {
			sendMailUsingSendGrid(from,to,subject,body)
		}
		catch(Exception e) {
			println "ERROR"
			println e
		}
	}

	public sendRescheduledConfirmationToClient(Appointment appointment){
		println "Sending reschedule confirmation for appointment: " + appointment.client.getFullName() + " | " + appointment.service.description + " on " + appointment.appointmentDate.format('E MM/dd @ hh:mm a')
		def from = "kalin@thedenbarbershop-kc.com"
		def to = "${appointment.client.email}"
		def subject = "Appointment Rescheduled @ The Den Barbershop"
		def body = "<p><img style='height:120px;width:120px;' src='${getLink()}/static/logo.png'></p><p>Hi ${appointment.client.firstName},</p><p>Your appointment for a ${appointment.service.description} has been rescheduled. Your new appointment date is: <b>${appointment.appointmentDate.format('E MM/dd @ hh:mm a')}</b>. If you need to reschedule this appointment please use this link:</p><p><a href='${getLink()}/book/modifyAppointment?a="+appointment.id+"&cc="+appointment.client.code+"'>${getLink()}/book/modifyAppointment?a="+appointment.id+"&cc="+appointment.client.code+"</a></p><p>To cancel your appointment, please use the following link:</p><p><a href='${getLink()}/book/cancelAppointment?c="+appointment.code+"'>${getLink()}/book/cancelAppointment?c="+appointment.code+"</a></p><p>Thanks,<br>Kalin</p>"
			body += "<p><b>Please Note:</b> <i>I am having an issue with last minute cancelations. Starting 3/1/15 I will be implementing a cancelation policy. I need 4 hours notice for a cancelation/rescheduled appointment. This gives me time to potentially fill that gap. There will be a \$20 charge at your following appointment if you cancel within 4 hours of your appointment. Thank you for understanding.</i></p>"
		try {
			sendMailUsingSendGrid(from,to,subject,body)
		}
		catch(Exception e) {
			println "ERROR"
			println e
		}
	}

	public sendReminder(Appointment appointment){
		if (!appointment.reminderEmailSent){
			def from = "kalin@thedenbarbershop-kc.com"
			def to = "${appointment.client.email}"
			def subject = "Appointment Reminder :: The Den Barbershop"
			def body = "<p><img style='height:120px;width:120px;' src='${getLink()}/static/logo.png'></p><p>Hi ${appointment.client.firstName},</p><p>This is a friendly reminder that your appointment tomorrow for a ${appointment.service.description} is at <b>${appointment.appointmentDate.format('hh:mm a')}</b>. In the event you need to reschedule, please use this link:</p><p><a href='${getLink()}/book/modifyAppointment?a="+appointment.id+"&cc="+appointment.client.code+"'>${getLink()}/book/modifyAppointment?a="+appointment.id+"&cc="+appointment.client.code+"</a></p><p>To cancel your appointment, please use the following link:</p><p><a href='${getLink()}/book/cancelAppointment?c="+appointment.code+"'>${getLink()}/book/cancelAppointment?c="+appointment.code+"</a></p><p>Thanks,<br>Kalin</p>"
				body += "<p><b>Please Note:</b> <i>I am having an issue with last minute cancelations. Starting 3/1/15 I will be implementing a cancelation policy. I need 4 hours notice for a cancelation/rescheduled appointment. This gives me time to potentially fill that gap. There will be a \$20 charge at your following appointment if you cancel within 4 hours of your appointment. Thank you for understanding.</i></p>"
			try {
				sendMailUsingSendGrid(from,to,subject,body)
				println "Reminder email sent."
				appointment.reminderEmailSent = true
				appointment.save()
			}
			catch(Exception e) {
				println "ERROR"
				println e
			}
		}
	}

	public sendPasswordResetLink(User client){
		println "\n" + new Date()
		println "Sending password reset link to: " + client.getFullName()
		def from = "kalin@thedenbarbershop-kc.com"
		def to = "${client.email}"
		def subject = "Password Reset Link :: The Den Barbershop"
		def body = "<p><img style='height:120px;width:120px;' src='${getLink()}/static/logo.png'></p><p>The following link can be used to reset your password:</p><ul><li><a href='${getLink()}/access/resetPasswordForm?rc="+client.passwordResetCode+"&cc="+client.code+"'>${getLink()}/access/resetPasswordForm?rc="+client.passwordResetCode+"&cc="+client.code+"</a></li></ul>"
		try {
			sendMailUsingSendGrid(from,to,subject,body)
		}
		catch(Exception e) {
			println "ERROR"
			println e
		}
	}

	public getLink(){
		return grailsLinkGenerator.getServerBaseURL()
	}

	public Boolean sendEmail(clientEmail, message){
		println "\n" + new Date()
		println "EMAILING: " + clientEmail
		println "-------------------------------------------------------------------"
		println message
		println "-------------------------------------------------------------------"
		def from = "kalin@thedenbarbershop-kc.com"
		def to = "${clientEmail}"
		def subject = "Message from Kalin @ The Den Barbershop"
		def body = "<p><img style='height:120px;width:120px;' src='${getLink()}/static/logo.png'></p><p>${message}</p>"
		Boolean success = false
		try {
			sendMailUsingSendGrid(from,to,subject,body)
			success = true
		}
		catch(Exception e) {
			println "ERROR"
			println e
			success = false
		}
		return success
	}

	public sendMailUsingSendGrid(fromEmail, toEmail, subject, body) throws IOException {
		def enabled = true
		if (enabled){
			if (Environment.current == Environment.DEVELOPMENT){
				toEmail = "bjacobi@gmail.com"
			}
			Email from = new Email(fromEmail);
			Email to = new Email(toEmail);
			Content content = new Content("text/html", body);
			Mail mail = new Mail(from, subject, to, content);

			SendGrid sg = new SendGrid(SENDGRID_API_KEY);
			Request request = new Request();
			request.method = Method.POST;
			request.endpoint = "mail/send";
			request.body = mail.build();
			try {
				Response response = sg.api(request);
			}
			catch(IOException ex) {
				println "ERROR: " + ex
				throw ex
			}
		}
	}

	/*public sendClientsEmail(){
		def clients = User.findAllWhere(isClient:true)
		def from = 'kalin@thedenbarbershop-kc.com'
		def subject = 'New Appointment Scheduling System'
		def to = ''
		def body = ''
		clients.each(){
			to = it.email
			body = groovyPageRenderer.render(view:'/email/newSchedulingSystem', model:[clientName:it.firstName])
			sendMailUsingSendGrid(from,to,subject,body)
		}
	}*/

}