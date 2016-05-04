package com.mallock.bicyclelending;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignUpActivity extends AppCompatActivity {

    private ProgressDialog pDialog;
    private String TAG="SignupActivity";
    private TextView errorTV;
    private String userName;
    private String firstName;
    private String lastName;
    private String address;
    private String password;
    private String password2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);
        ButterKnife.inject(this);
        pDialog= new ProgressDialog(this);
    }

    @OnClick(R.id.signUpButton)
    public void startSignUp(View v)
    {
        userName=((EditText)findViewById(R.id.userNameET)).getText().toString();
         firstName=((EditText)findViewById(R.id.firstNameET)).getText().toString();
         lastName=((EditText)findViewById(R.id.lastNameET)).getText().toString();
         password=((EditText)findViewById(R.id.PasswordET)).getText().toString();
         password2=((EditText)findViewById(R.id.PasswordET2)).getText().toString();
         address=((EditText)findViewById(R.id.postalAddressET)).getText().toString();
        errorTV=(TextView)findViewById(R.id.textView2);
        if(!password.equals(password2))
        {
            errorTV.setText(R.string.password_mismatch);
            return;
        }
        String url=URLs.GETCheckUserNameURL+userName;
        CheckUserNameAvailability work= new CheckUserNameAvailability(url);
        work.execute();

    }

    private class CheckUserNameAvailability extends GETMethod
    {
        public CheckUserNameAvailability(String url) {
            super(url);
        }

        @Override
        public void onPreExecute() {
            showDialog("Checking userName...");
        }

        @Override
        public void onPostExecute(String s) {
            hideDialog();
            Log.e(TAG,"Recieved: "+s);
            if(s.equals("0"))
                invalidUsername();
            else
                validUsername();
        }
    }

    private void validUsername()
    {
        JSONObject json= new JSONObject();
        try {
            json.put("userName", userName);
            json.put("password", password);
            json.put("firstName", firstName);
            json.put("lastName", lastName);
            json.put("userID", userName);
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        SignUpPOST work= new SignUpPOST(json.toString(),URLs.POSTSignUp);
        work.execute();
    }

    private void invalidUsername()
    {
        errorTV.setText(R.string.username_taken);
    }

    private void showDialog(String message)
    {
        if(pDialog.isShowing())
        {
            pDialog.hide();
        }
        pDialog.setMessage(message);
        pDialog.show();
    }

    private void hideDialog()
    {
        if(pDialog.isShowing())
            pDialog.hide();
    }

    private class SignUpPOST extends POSTMethod
    {

        public SignUpPOST(String j, String u) {
            super(j, u);
        }

        @Override
        public void onPreExecute() {
            showDialog("Signing up...");
        }

        @Override
        public void onPostExecute(String o) {
            hideDialog();
            Toast.makeText(SignUpActivity.this,"Signed up successfully!",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SignUpActivity.this, MainActivity.class));
        }
    }
}
