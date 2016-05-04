package com.mallock.bicyclelending;

/**
 * Created by Akshat1997 on 2/10/2016.
 */
public class URLs {

    final static String BASEURL="http://192.168.65.105:3000";
    final static String POSTLoginURL=BASEURL+"/login/";
    final static String GETStandsURL=BASEURL+"/getStands/";
    final static String POSTSignUp=BASEURL+"/signup/";      //userName, firstName, lastName, password
                                                                                //taken - 0, successful - 1
    final static String POSTBookingURL="/bookBike/";
    final static String GETCheckUserNameURL=BASEURL+"/checkUserName/";
    final static String GETCodeURL="/checkOTP/{userId}/";
    final static String GETCancel="/cancelBooking/{username}/";
    final static String GETReturnBike="/returnBike/{user_id}/{standname}";

}
