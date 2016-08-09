production {
	dataSource {
		uri = new URI(System.env.DATABASE_URL ?: "mysql://foo:bar@localhost")
		username = uri.userInfo ? uri.userInfo.split(":")[0] : ""
		password = uri.userInfo ? uri.userInfo.split(":")[1] : ""
		url = "jdbc:mysql://" + uri.host + uri.path
	}
	properties {
        dbProperties {
            autoReconnect = true
        }
    }
}