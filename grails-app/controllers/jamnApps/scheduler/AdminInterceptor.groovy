package jamnApps.scheduler


class AdminInterceptor {

    boolean before() {
        println "request.serverName: " + request.serverName
        def clientName = request.serverName
        if (clientName.contains(".")) {
            clientName = clientName.substring(0, clientName.indexOf("."))
            //request.currentClient = Client.findByClientName(clientName) // e.g.
            println "clientName: " + clientName
        }
        if (!session.user?.isAdmin){
            redirect(controller:'access', action:'login', params: [caller:'/admin'])
            return false
        }
        true
    }

    boolean after() { 
    	true 
    }

    void afterView() {
        // no-op
    }
}
