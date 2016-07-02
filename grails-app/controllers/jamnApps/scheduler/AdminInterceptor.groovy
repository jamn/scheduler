package jamnApps.scheduler


class AdminInterceptor {

    boolean before() {
        if (!session.user?.isAdmin){
            redirect(controller:'access', action:'login')
            return false
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
