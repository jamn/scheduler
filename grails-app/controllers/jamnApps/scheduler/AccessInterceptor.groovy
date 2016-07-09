package jamnApps.scheduler


class AccessInterceptor {

    boolean before() {
        def caller = params?.caller ?: request.getHeader('referer')
        if (!caller.contains('login')){
            session.caller = caller
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
