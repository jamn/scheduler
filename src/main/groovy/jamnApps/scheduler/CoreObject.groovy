package jamnApps.scheduler

public abstract class CoreObject implements java.io.Serializable {

	Date dateCreated = new Date()
	Date dateLastUpdated
	Long createdBy
	Long updatedBy
	Boolean deleted = false

    static mapping = {
        //tablePerHierarchy false
    }

    static constraints = {
        createdBy(nullable:true)
		dateLastUpdated(nullable:true)
		updatedBy(nullable:true)
    }

    def beforeUpdate = {
    	//updatedBy = session.user.id
    	dateLastUpdated = new Date()
    }
}
