package com.mallock.bicyclelending;

import android.os.AsyncTask;
import android.util.Log;


import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;


public abstract class POSTMethod extends AsyncTask<Void,Void, String> {
    String json;
    String url;
    String TAG;
    public POSTMethod(String j, String u)
    {
        json=j;
        url=u;
        TAG="POSTING";
    }

    @Override
    public String doInBackground(Void[] params) {
        Log.e(TAG,"starting backkground task. - Sending "+ json+" to "+url);
        InputStream inputStream = null;
        String result = "";
        try {

            // 1. create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // 2. make POST request to the given URL
            HttpPost httpPost = new HttpPost(url);


            // 3. build jsonObject

            // 4. convert JSONObject to JSON to String
            // ** Alternative way to convert Person object to JSON string usin Jackson Lib
            // ObjectMapper mapper = new ObjectMapper();
            // json = mapper.writeValueAsString(person);

            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);

            // 6. set httpPost Entity
            httpPost.setEntity(se);

            // 7. Set some headers to inform server about the type of the content
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");

            // 8. Execute POST request to the given URL
            HttpResponse httpResponse = httpclient.execute(httpPost);

            // 9. receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // 10. convert inputstream to string
            if(inputStream != null)
            {
                BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
                String line = "";
                String response = "";
                while((line = bufferedReader.readLine()) != null)
                    response += line;
                inputStream.close();
                return response;
            }
            else
                return("Did not work!");

        } catch (Exception e) {
            e.printStackTrace();
            return (null);
        }
    }
    public abstract void onPreExecute();

    public abstract void onPostExecute(String o);
}
