package jamnApps.scheduler


class AccessInterceptor {

    boolean before() {
        def caller = request.getHeader('referer') ?: "/book"
        println "caller: " + caller
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
