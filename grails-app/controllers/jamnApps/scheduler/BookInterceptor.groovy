package jamnApps.scheduler


class BookInterceptor {

    boolean before() { 

        def caller = request.getHeader('referer') ?: "/book"
        session.caller = caller

        def serviceProvider = session?.serviceProvider
        def service = session?.service
        def serviceDate = session?.nextAppointment
        def client = session?.client
        def requestedAction = params?.action ?: 'index'
        
        //steps (in order) and requirements for each
        def readyTo = [
            'index': true, //prevents an endless loop
            'login': true,
            'logout': true,
            'chooseService': true,
            'saveServiceSelection': (serviceProvider),
            'chooseTime': (serviceProvider && service),
            'holdTimeslot': (serviceProvider && service),
            'bookAppointment': (serviceProvider && service && serviceDate),
            'confirmation': (serviceProvider && service && serviceDate)
        ]
    
        println "\n---------------------------------"
        println "requestedAction: " + requestedAction
        println "readyTo: " + readyTo
        println "---------------------------------"

        if (readyTo[requestedAction]){
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
