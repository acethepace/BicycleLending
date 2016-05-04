package com.mallock.bicyclelending;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
        import android.support.design.widget.FloatingActionButton;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.support.v7.widget.LinearLayoutManager;
        import android.support.v7.widget.RecyclerView;
        import android.support.v7.widget.Toolbar;
        import android.util.Log;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
        import org.json.JSONException;
        import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

import butterknife.ButterKnife;
import butterknife.InjectView;
        import butterknife.OnClick;
import hugo.weaving.DebugLog;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class DescriptionActivity extends AppCompatActivity {

    //ui control
    String TAG="DescriptionActivity";
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    RecyclerView recyclerView;

    DescriptionCardAdapter adapter;
    Stand stand;
    @InjectView(R.id.image)
    ImageView myImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description_main);
        collapsingToolbarLayout = (CollapsingToolbarLayout)     findViewById(R.id.collapsing_toolbar);
        recyclerView=(RecyclerView)findViewById(R.id.recyclerView);
        ButterKnife.inject(this);
        collapsingToolbarLayout.setTitle("Demo");
        //setSupportActionBar(toolbar);

        //recyclerview
        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(new DescriptionCardAdapter(Present.currentStand));
        initializeData();
        setImage();
    }
    private void initializeData() {
        stand=Present.currentStand;
    }

    private void setImage()
    {
        Log.e("TAG", "loading: " + stand.imageURL);
        Picasso.with(DescriptionActivity.this)
                .load(stand.imageURL)
                .into(myImage);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.fab)
    @DebugLog
    public void bookCycle() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint(URLs.BASEURL)
                .build();


        APIInterface bookingInterface = restAdapter.create(APIInterface.class);

        TypedInput in = null;
        try {
            in = new TypedByteArray("application/json", getJSONObject().toString().getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        bookingInterface.book(in, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Toast.makeText(DescriptionActivity.this,"CONGRATS on your booking",Toast.LENGTH_SHORT).show();
                Toast.makeText(DescriptionActivity.this,"Your code is: "+s,Toast.LENGTH_SHORT).show();
                Intent i= new Intent(DescriptionActivity.this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("TAG",getJSONObject().toString());
                Toast.makeText(DescriptionActivity.this,"Server Error: "+ error.getMessage()+error.getLocalizedMessage()+error.getBody(),Toast.LENGTH_SHORT).show();
            }
        });


    }

    @DebugLog
    private JSONObject getJSONObject() {
        JSONObject json=null;
        String string="{ 'stand_id' : '"+stand.id+"', 'user_id' : '"+Present.currentUser.userID+"'}";
        try {
            json= new JSONObject(string);
        } catch (JSONException e) {
            Log.e(TAG,"ERRER: "+e.toString());
            e.printStackTrace();
        }
        return  json;
    }
}