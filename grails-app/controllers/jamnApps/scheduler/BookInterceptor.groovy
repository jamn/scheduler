package jamnApps.scheduler


class BookInterceptor {

    boolean before() { 

        def caller = request.getHeader('referer') ?: "/book"
        session.caller = caller

        def cookie = request.getCookie('den-scheduler-1')
        if (!session.user && cookie){
            session.user = LoginLog.findWhere(loggedInCookieId:cookie, deleted:false)?.user
        }

        def serviceProvider = session?.serviceProvider
        def service = session?.service
        def serviceDate = session?.nextAppointment
        def client = session?.client
        def requestedAction = params?.action ?: 'index'
        
        //steps (in order) and requirements for each
        def readyTo = [
            'saveServiceSelection': serviceProvider ? true : false,
            'chooseTime': (serviceProvider && service),
            'holdTimeslot': (serviceProvider && service),
            'bookAppointment': (serviceProvider && service && serviceDate),
            'confirmation': (serviceProvider && service && serviceDate),
        ]
        
        def protectedActions = readyTo.keySet()

        if (requestedAction == 'index' || !protectedActions.contains(requestedAction) || readyTo[requestedAction]){
            return true
        }else{
            redirect(action:'index')
        }

    }

    boolean after() { 
    	true 
    }

    void afterView() {
        // no-op
    }
}
