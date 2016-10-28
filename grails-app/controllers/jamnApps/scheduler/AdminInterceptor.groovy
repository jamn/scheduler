package jamnApps.scheduler


class AdminInterceptor {

    boolean before() {
        println "\n" + new Date()
        println "params: " + params

        def cookie = request.getCookie('den-scheduler-1')
        if (!session.user && cookie){
            session.user = LoginLog.findWhere(loggedInCookieId:cookie, deleted:false)?.user
        }
        
        def clientName = request.serverName
        if (clientName.contains(".")) {
            clientName = clientName.substring(0, clientName.indexOf("."))
            //request.currentClient = Client.findByClientName(clientName) // e.g.
        }
        //println "clientName: " + clientName
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
