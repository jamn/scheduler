package jamnApps.scheduler

class ErrorController {

	static layout = 'shell'

	def index() {
		def message = ApplicationProperty.findByName("HOMEPAGE_MESSAGE")?.value ?: "No messages found."
		return [message:message]
	}

	
}
