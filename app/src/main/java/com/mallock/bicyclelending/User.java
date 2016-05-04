package com.mallock.bicyclelending;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Akshat1997 on 2/10/2016.
 */
public class User implements Serializable{
    String username;
    String firstName;
    String lastName;
    String userID;
    String status;
    ArrayList<Book> history;
    public User()
    {
        history= new ArrayList<>();
    }
    public User(JSONObject json)
    {
        history=new ArrayList<>();
        try {
            username = json.getString("userName");
            firstName=json.getString("firstName");
            lastName=json.getString("lastName");
            userID=json.getString("_id");        //TODO:get the object id from MongoDB
            status=json.getString("status");
            JSONArray jsonArray=json.getJSONArray("history");
            for(int i=0;i!=jsonArray.length();i++)
            {
                history.add(new Book((JSONObject) jsonArray.get(i)));
            }
            Log.e("TAG",this.userID);
        }catch(Exception AIDS)
        {
            AIDS.printStackTrace();
            //Oh NO! You've caught AIDS :/
        }
    }
}
