package jamnApps.scheduler


class UserInterceptor {

    boolean before() {
        session.caller = request.getHeader('referer') ?: "/user"
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
