package jamnApps.scheduler

class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/"(controller: "book")
		"500"(view: "/error")
		"404"(view: "/error")
	}

}