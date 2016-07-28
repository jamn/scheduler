package jamnApps.scheduler


class UserInterceptor {

    boolean before() {
        def caller = request.getHeader('referer') ?: "/user"
        session.caller = caller
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
