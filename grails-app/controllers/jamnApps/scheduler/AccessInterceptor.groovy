package jamnApps.scheduler


class AccessInterceptor {

    boolean before() {
        def caller = params?.caller ?: request.getHeader('referer')
        if (caller && !caller?.contains('login') && !caller?.contains('bookAppointment')){
            println "caller: " + caller
            session.caller = caller
        }else{
            session.caller = null
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
