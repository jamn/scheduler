package jamnApps.scheduler


class UserInterceptor {

    boolean before() {
        def caller = request.getHeader('referer') ?: "/user"
        session.caller = caller

        def cookie = request.getCookie('den-scheduler-1')
        if (!session.user && cookie){
            session.user = LoginLog.findWhere(loggedInCookieId:cookie, deleted:false)?.user
        }

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
