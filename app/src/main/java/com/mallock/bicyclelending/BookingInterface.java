package com.mallock.bicyclelending;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.mime.TypedInput;

/**
 * Created by Akshat on 29-04-2016.
 */
public interface BookingInterface {

    @POST(URLs.POSTBookingURL)
    void book(@Body TypedInput body, Callback<String> callback);
}
