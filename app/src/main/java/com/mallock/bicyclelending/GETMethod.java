package com.mallock.bicyclelending;

import android.os.AsyncTask;
import android.util.Log;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

/**
 * Initialized with URL.
 * GETs the data from the said URL. returns it.
 */
public abstract class GETMethod extends AsyncTask<Void,Void,String>{
    String url;
    public GETMethod(String url)
    {
        this.url=url;
    }
    @Override
    protected String doInBackground(Void[] params) {
        String ret=null;
        DefaultHttpClient httpClient = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(url);
        //TODO set serviceID and bookingID
        // httpGet.addHeader("Content-type","application/json");
        ResponseHandler<String> resHandler = new BasicResponseHandler();
        try {
            ret = httpClient.execute(httpGet, resHandler);
        }catch (ClientProtocolException e)
        {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.e("response", ret);
        return ret;
    }

    public abstract void onPreExecute();
    public abstract void onPostExecute(String s);
}
