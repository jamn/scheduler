package jamnApps.scheduler

import org.apache.commons.lang.RandomStringUtils

class AccessInterceptor {

    boolean before() {
        def caller = params?.caller ?: request.getHeader('referer')
        if (caller && !caller?.contains('login') && !caller?.contains('bookAppointment')){
            session.caller = caller
        }else{
            session.caller = null
        }
        
        def cookie = request.getCookie('den-scheduler-1')
        if (!session.user && cookie){
            session.user = LoginLog.findWhere(loggedInCookieId:cookie, deleted:false)?.user
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
