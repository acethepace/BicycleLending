package com.mallock.bicyclelending;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RidingActivity extends AppCompatActivity {

    @InjectView(R.id.editText)
    Spinner listView;

    String [] strings={"Govindpuri Metro Station","Serco Global Services","IIIT-D","Nehru Place"};
    String standName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riding);
        ButterKnife.inject(this);
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.simple_dropdown, strings));
        listView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                standName=strings[i];
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                standName=strings[0];
            }
        });
    }

    @DebugLog
    @OnClick(R.id.button)
    public void returnBike()
    {
        RestAdapter restAdapter= new RestAdapter.Builder()
                .setEndpoint(URLs.BASEURL)
                .build();
        APIInterface api= restAdapter.create(APIInterface.class);
        api.getReturnBike(Present.currentUser.userID, standName, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Toast.makeText(RidingActivity.this, "Your cycle has been returned.", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(RidingActivity.this, LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(RidingActivity.this, "Retrofit error." + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
