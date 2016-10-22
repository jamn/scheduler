package jamnApps.scheduler

import com.twilio.Twilio
import com.twilio.rest.api.v2010.account.Message
import com.twilio.type.PhoneNumber
 
class TextMessageService {

	static final String ACCOUNT_SID = "AC698b760c0ae5de295ecc4d99ee0014de"
	static final String AUTH_TOKEN = "410d76c16d6e4ea879ae44a171011e1d"

	public static void sendReminder(Appointment appointment){	
		def phone = appointment.client?.phone?.replaceAll("-","")?.replaceAll(" ","")?.replaceAll("___-___-____","")
		if (!appointment.reminderTextSent && (phone?.size() == 10 && !phone.contains('0000000000'))) {
			def appointmentDate = appointment.appointmentDate.format('hh:mm a')
			def to = "+1" + phone
			def from = "+18163262006"
			def body = "Reminder: Your appointment for a ${appointment.service.description} @ The Den is tomorrow at ${appointmentDate}."
			sendMessage(to,from,body)
			appointment.reminderTextSent = true
			appointment.save()
		}
	}

	public static void test(){
		def to = "+19132055949"
		def from = "+18162664723"
		def body = "Reminder: Your appointment for a Haircut @ The Den is tomorrow at 4:30 PM."
		sendMessage(to,from,body)
	}

	private static void sendMessage(to, from, messageBody){
		try {
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