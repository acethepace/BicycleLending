package com.mallock.bicyclelending;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends AppCompatActivity{
    String TAG= "Login Activity";
    User currentUser;
    private ProgressDialog pDialog;
    private final String USER_FILE_NAME="userFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        try {
            // TODO: read Username, password from file and setText. NOTE: need to getFilesDir() in android first.
        } catch (Exception ball)
        {
            Log.e(TAG,"Congrats!!! You caught a ball");
        }
    }

    public void checkLogin(View v)
    {
        String userName= ((EditText)findViewById(R.id.EditTextUsername)).getText().toString();
        String password=((EditText)findViewById(R.id.EditTextPassword)).getText().toString();
        //TODO: if checkbox is checked, write the userName and password to the file.
//        String hashedPassword="";
//        try {
//            MessageDigest messageDigest= MessageDigest.getInstance("SHA-512");
//            messageDigest.update(password.getBytes());
//            byte[] digest= messageDigest.digest();
//            hashedPassword=new String(digest);
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        }
        JSONObject jsonObject= new JSONObject();
        try{
            jsonObject.put("userName",userName);
            jsonObject.put("password",password);
        }catch(Exception ball)
        {
            //CONGRATS! You caught a ball!
        }
        Log.e(TAG,"sending: "+jsonObject.toString());
        CheckLoginPOST checkLoginPOST= new CheckLoginPOST(jsonObject.toString(),URLs.POSTLoginURL);
        checkLoginPOST.execute();
    }

    private class CheckLoginPOST extends POSTMethod
    {
        public CheckLoginPOST(String j, String u) {
            super(j, u);
        }

        @Override
        public void onPreExecute() {
            showDialog();
        }

        @Override
        public void onPostExecute(String o) {
            Log.e(TAG,"recieved: "+o);
            hideDialog();
            if(o.equals("0"))
            {
                Toast.makeText(LoginActivity.this,"Please enter a valid E-mail ID and password",Toast.LENGTH_SHORT).show();
            }
            else {
                try {
                    currentUser = new User(new JSONObject(o));
                    Present.currentUser=currentUser;
                    Toast.makeText(LoginActivity.this, "Welcome back, "+currentUser.firstName+'!', Toast.LENGTH_SHORT).show();
                    Log.e("TAG",currentUser.userID);
                    Intent intent=null;
                    if(currentUser.status.equals("FREE"))
                        intent= new Intent(LoginActivity.this, MainActivity.class);
                    else if(currentUser.status.equals("BOOKED"))
                        intent = new Intent(LoginActivity.this, BookedLayoutActivity.class);
                    else if(currentUser.status.equals("RIDING"))
                        intent = new Intent(LoginActivity.this, RidingActivity.class);
                    else
                        Log.e(TAG,"status mismatch error. "+currentUser.status);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void hideDialog()
    {
        if(pDialog.isShowing())
            pDialog.hide();
    }

    private void showDialog()
    {
        if(!pDialog.isShowing())
            pDialog.show();
    }

    public void forgotPassword(View v)
    {
        startActivity(new Intent(this,SignUpActivity.class));
    }
}