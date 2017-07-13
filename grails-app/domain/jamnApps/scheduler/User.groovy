package jamnApps.scheduler

import java.text.SimpleDateFormat

class User extends CoreObject {

	String username
	String password
	String firstName
	String lastName
	String email
    String phone
	String address1
    String address2
    String city
    String state
    Long zip

    String code // users initials or some short code to pass as a URL param
    String passwordResetCode
    Date passwordResetCodeDateCreated
    
    String notes
    Date birthday

    Boolean isAdmin = false
    Boolean isServiceProvider = false
    Boolean isClient = false

	static hasMany = [services:ServiceType, daysOfTheWeek:DayOfTheWeek, logins:LoginLog]

    static constraints = {
    	username(nullable:true, unique:true)
		password(nullable:true)
		firstName(nullable:true)
		lastName(nullable:true)
        email(nullable:true)
        address1(nullable:true)
        address2(nullable:true)
        city(nullable:true)
        state(nullable:true)
        zip(nullable:true)
		phone(nullable:true)
        passwordResetCode(nullable:true)
        passwordResetCodeDateCreated(nullable:true)
        notes(nullable:true)
        birthday(nullable:true)
    }

    def transients = [
    	'fullName',
        'shortName'
    ]

    String getFullName(){
    	return firstName + " " + lastName
    }

    String getShortName(){
        if (fullName.size() > 8) {
            return fullName.substring(0,8) + "&hellip;"
        }else{
            return fullName
        }
    }

    Boolean isNewUser(){
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy")
        Date dateNewUsersWereEnabled = sdf.parse("07/16/2015")
        return dateCreated > dateNewUsersWereEnabled
    }

}	