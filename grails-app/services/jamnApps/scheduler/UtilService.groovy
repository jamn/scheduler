package jamnApps.scheduler

import org.apache.tomcat.jdbc.pool.DataSource

class UtilService {

	def dataSourceUnproxied

	static String communicationBoardMessage
	static String homepageImageUrl

	def checkConnections(){
		DataSource tomcatDataSource = dataSourceUnproxied
		return "$tomcatDataSource.active active (max $tomcatDataSource.maxActive, initial $tomcatDataSource.initialSize), $tomcatDataSource.idle idle (max $tomcatDataSource.maxIdle, min $tomcatDataSource.minIdle)"
	}

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

	def resetApplicationPropertyVariables(){
		communicationBoardMessage = null
		homepageImageUrl = null
	}

}