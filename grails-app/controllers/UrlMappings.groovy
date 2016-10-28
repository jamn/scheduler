class UrlMappings {

	static mappings = {
		"/$controller/$action?/$id?"{
			constraints {
				// apply constraints here
			}
		}
		"/"(controller: "book")
		"/error"(uri: "/static/error.html")
	}

}