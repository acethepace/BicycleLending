
package com.mallock.bicyclelending;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import hugo.weaving.DebugLog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class BookedLayoutActivity extends AppCompatActivity {
    @InjectView(R.id.code_text)
    TextView codeTV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.booked_layout);
        ButterKnife.inject(this);
        startQuery();
    }

    @DebugLog
    private void startQuery() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URLs.BASEURL)
                .build();
        APIInterface api=restAdapter.create(APIInterface.class);
        api.getCode(Present.currentUser.username, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                codeTV.setText(s);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(BookedLayoutActivity.this, "Retrofit has experienced an error", Toast.LENGTH_SHORT).show();
                Log.e("Retrofit- Booked", error.getMessage());
            }
        });
    }

    @OnClick(R.id.button)
    public void cancelBooking()
    {
        RestAdapter restAdapter= new RestAdapter.Builder()
                .setEndpoint(URLs.BASEURL)
                .build();
        APIInterface api= restAdapter.create(APIInterface.class);
        api.getCancelBooking(Present.currentUser.username, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Toast.makeText(BookedLayoutActivity.this,"Your booking has been cancelled.",Toast.LENGTH_SHORT).show();
                Intent i = new Intent(BookedLayoutActivity.this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }

            @Override
            public void failure(RetrofitError error) {
                Toast.makeText(BookedLayoutActivity.this,"Retrofit error.",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
