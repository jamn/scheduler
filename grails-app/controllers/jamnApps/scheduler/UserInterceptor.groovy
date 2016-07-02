package jamnApps.scheduler


class UserInterceptor {

    boolean before() {
        session.caller = request.getHeader('referer') ?: "/"
        if (!session.user){
            redirect(controller:'book')
            return
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
