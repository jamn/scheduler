package jamnApps.scheduler

//import com.twilio.sdk.TwilioRestClient
//import com.twilio.sdk.TwilioRestException
//import com.twilio.sdk.resource.factory.MessageFactory
//import com.twilio.sdk.resource.instance.Message
//import org.apache.http.NameValuePair
//import org.apache.http.message.BasicNameValuePair
 
class TextMessageService {

	//static final String ACCOUNT_SID = "ACb0f0b0592fc4feee469fc6213ddc454d"
	//static final String AUTH_TOKEN = "fbdb7ce5679ea26e65b8768681d9bf40"

	/*static void sendReminder(Appointment appointment) throws TwilioRestException {
		
		def phone = appointment.client?.phone?.replaceAll("-","")?.replaceAll(" ","")?.replaceAll("___-___-____","")
		
		if (!appointment.reminderTextSent && (phone?.size() == 10 && !phone.contains('0000000000'))) {
			def appointmentDate = appointment.appointmentDate.format('hh:mm a')
			def body = "Reminder: Your appointment for a ${appointment.service.description} @ The Den is tomorrow at ${appointmentDate}."
			
			// Build a filter for the MessageList
			List<NameValuePair> params = new ArrayList<NameValuePair>()
			params.add(new BasicNameValuePair("Body", body))
			params.add(new BasicNameValuePair("To", "+1"+phone))
			params.add(new BasicNameValuePair("From", "+18163262006"))

			TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN)
			MessageFactory messageFactory = client.getAccount().getMessageFactory()
			Message message = messageFactory.create(params)
			
			println "Reminder text sent. (${message.getSid()})"
			
			appointment.reminderTextSent = true
			appointment.save()
		}

	}*/
}