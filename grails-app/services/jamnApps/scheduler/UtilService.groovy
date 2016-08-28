package jamnApps.scheduler

class UtilService {

	static String communicationBoardMessage
	static String homepageImageUrl

	def getCommunicationBoardMessage() {
		if (!communicationBoardMessage){
			communicationBoardMessage = ApplicationProperty.findByName("HOMEPAGE_MESSAGE")?.value ?: "No messages found."
		}
		return communicationBoardMessage
	}

	def getHomepageImageUrl(){
		if (!homepageImageUrl){
			homepageImageUrl = ApplicationProperty.findByName("HOMEPAGE_IMAGE_URL")?.value ?: "image-not-found.jpg"
		}
		return homepageImageUrl
	}

	def resetSession(){
		session.invalidate()
	}

}