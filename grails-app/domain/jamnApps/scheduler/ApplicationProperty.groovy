package jamnApps.scheduler

class ApplicationProperty {

	String name
	String value

    static constraints = {
		name(unique:true,nullable:false)
		value(nullable:false, size: 1..2000)
    }
}
