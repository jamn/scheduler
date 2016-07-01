package jamnApps.scheduler


class UserInterceptor {

    boolean before() {
        if (!session.client){
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
