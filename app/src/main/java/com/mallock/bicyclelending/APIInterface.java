package com.mallock.bicyclelending;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.mime.TypedInput;

/**
 * Created by Akshat on 29-04-2016.
 */
public interface APIInterface {

    @POST(URLs.POSTBookingURL)
    void book(@Body TypedInput body, Callback<String> callback);

    @GET(URLs.GETCodeURL)
    void getCode(@Path("userId")String userId,Callback<String> callback);

    @GET(URLs.GETCancel)
    void getCancelBooking(@Path("username")String userId,Callback<String> callback);

    @GET(URLs.GETReturnBike)
    void getReturnBike(@Path("user_id")String username,@Path("standname")String stand,Callback<String> callback);
}
