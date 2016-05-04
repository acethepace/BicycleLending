package com.mallock.bicyclelending;

import org.json.JSONObject;

/**
 * Created by Akshat1997 on 2/10/2016.
 */
public class Book {
    String startingPoint;
    String destination;
    String time;
    String bikeNumber;
    public Book(JSONObject json) {
        try{

            startingPoint=json.getString("startingPoint");
            destination=json.getString("destination");
            time=json.getString("time");
            bikeNumber=json.getString("bikeNumber");
        }catch(Exception e)
        {
            e.printStackTrace();
        }
    }
}
