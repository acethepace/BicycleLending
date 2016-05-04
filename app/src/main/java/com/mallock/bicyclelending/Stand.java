package com.mallock.bicyclelending;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Akshat1997 on 2/12/2016.
 */
public class Stand {
    String standName;
    String latitude;
    String longitude;
    String description;
    String imageURL;
    String id;
    String available;
    public Stand(JSONObject json)
    {
        try {
            latitude=json.getString("latitude");
            standName= json.getString("standName");
            longitude=json.getString("longitude");
            description=json.getString("description");
            imageURL=json.getString("imageURL");
            available=json.getString("available");
            id=json.getString("_id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public boolean available() {
        return !available.equals("0");
    }
}
