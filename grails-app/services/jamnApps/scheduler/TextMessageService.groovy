package jamnApps.scheduler

import grails.util.Environment
import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
 
class TextMessageService {

	static final String ACCOUNT_SID = "AC698b760c0ae5de295ecc4d99ee0014de"
	static final String AUTH_TOKEN = "410d76c16d6e4ea879ae44a171011e1d"

	public static void sendReminderToClient(Appointment appointment){
		def phone = appointment.client?.phone?.replaceAll("-","")?.replaceAll("\\(","")?.replaceAll("\\)","")?.replaceAll(" ","")?.replaceAll("___-___-____","")
		if (!appointment.reminderTextSent && (phone?.size() == 10 && !phone.contains('0000000000'))) {
			println "sending text to client"
			def appointmentDate = appointment.appointmentDate.format('hh:mm a')
			def to = "+1" + phone
			def from = "+18162664723"
			def body = "Reminder: Your appointment for a ${appointment.service.description} @ The Den is tomorrow at ${appointmentDate}."
			sendMessage(to,from,body)
			appointment.reminderTextSent = true
			appointment.save()
		}
	}

	public static void sendCancellationNoticeToServiceProvider(Appointment appointment){
		def phone = appointment.serviceProvider?.phone?.replaceAll("-","")?.replaceAll("\\(","")?.replaceAll("\\)","")?.replaceAll(" ","")?.replaceAll("___-___-____","")
		println "phone: " + phone
		println "appointment.cancellationTextSentToServiceProvider: " + appointment.cancellationTextSentToServiceProvider
		if (!appointment.cancellationTextSentToServiceProvider && (phone?.size() == 10 && !phone.contains('0000000000'))) {
			println "    sending text to service provider"
			def appointmentDate = appointment.appointmentDate.format('hh:mm a')
			def to = "+1" + phone
			def from = "+18162664723"
			def body = "Appointment Canceled: ${appointment.client.fullName} -- ${appointment.service.description} @ ${appointmentDate}."
			sendMessage(to,from,body)
			appointment.cancellationTextSentToServiceProvider = true
			appointment.save()
		}
	}



	private static void sendMessage(to, from, messageBody){
		def enabled = true
		if (enabled){
			if (Environment.current == Environment.DEVELOPMENT){
				to = "913-205-5949"
			}
			try {
				println "to: " + to
				println "from: " + from
				println "message: " + messageBody
				Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
				Message message = Message.creator(
					new PhoneNumber(to),
					new PhoneNumber(from), 
					messageBody
				).create();
				println "Text sent through Twilio. (${message.getSid()})"
			}
			catch(Exception e) {
				println "ERROR: " + e
			}
		}
	}
}