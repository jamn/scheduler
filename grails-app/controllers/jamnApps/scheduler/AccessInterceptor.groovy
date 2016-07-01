package jamnApps.scheduler


class AccessInterceptor {

    boolean before() {
        session.caller = request.getHeader('referer') ?: "/"
        true
    }

    boolean after() { 
    	true 
    }

    void afterView() {
        // no-op
    }
}
