package jamnApps.scheduler

class UtilService {

	static String communicationBoardMessage

	def getCommunicationBoardMessage() {
		if (!communicationBoardMessage){
			communicationBoardMessage = ApplicationProperty.findByName("HOMEPAGE_MESSAGE")?.value ?: "No messages found."
		}
		return communicationBoardMessage
	}

}