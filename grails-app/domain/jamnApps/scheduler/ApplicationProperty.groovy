package jamnApps.scheduler

class ApplicationProperty extends CoreObject {

	String name
	String value

    static constraints = {
		name(unique:true,nullable:false)
		value(nullable:false)
    }

    static mapping = {
    	value type: "text"
    }
}
