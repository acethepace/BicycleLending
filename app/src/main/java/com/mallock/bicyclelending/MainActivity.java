package com.mallock.bicyclelending;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.tv.TvContract;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Akshat1997 on 2/12/2016.
 */
public class MainActivity extends Activity
{
    private ProgressDialog pDialog;
    private ListView listView;

    private ArrayList<Stand> stands= new ArrayList<>();
    @Override
    public void onCreate(Bundle b)
    {
        super.onCreate(b);
        setContentView(R.layout.layout_main);
        listView=(ListView)findViewById(R.id.list_view);
        pDialog= new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        startLoadingStands();
    }

    private void startLoadingStands()
    {
        getStandsGET work= new getStandsGET(URLs.GETStandsURL);
        work.execute();
    }

    private class getStandsGET extends GETMethod
    {

        public getStandsGET(String url) {
            super(url);
            stands=new ArrayList<>();
        }

        @Override
        public void onPreExecute() {
            startProgressDialog();
        }

        @Override
        public void onPostExecute(String str) {
            JSONObject s= null;
            try {
                s = new JSONObject(str);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            setStands(s);
            CustomAdapter adapter=new CustomAdapter(MainActivity.this,R.layout.list_item_main,stands);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                    Toast.makeText(MainActivity.this,"got postition"+position,Toast.LENGTH_SHORT).show();
                    Present.currentStand=stands.get(position);
                    if(Present.currentStand.available.equals("0"))
                        Toast.makeText(MainActivity.this,"No cycles available",Toast.LENGTH_SHORT).show();
                    else
                        startActivity(new Intent(MainActivity.this,DescriptionActivity.class));
                }
            });
            listView.setAdapter(adapter);
            hideProgressDialog();
        }

        private void setStands(JSONObject s)
        {
            try {
                JSONArray array= s.getJSONArray("stands");
                for(int i=0;i!=array.length();i++)
                {
                    JSONObject jsonObject= array.getJSONObject(i);
                    stands.add(new Stand(jsonObject));
                    Log.e("adding"+stands.get(i).standName,jsonObject.toString());

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void hideProgressDialog() {
        try{
            if(pDialog.isShowing())
                pDialog.hide();
            else
                throw new Exception("WUT?");        //just wondering if this ever happens
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void startProgressDialog() {
        try{
            if(!pDialog.isShowing())
                pDialog.show();
            else
                throw new Exception("WUT?");        // still wondering if that will ever happen
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private class CustomAdapter extends ArrayAdapter<Stand>
    {
        int layoutResourceID;
        Context context;
        ArrayList<Stand> data;
        public CustomAdapter(Context context, int resource, ArrayList<Stand> data) {
            super(context, resource,data);
            this.context=context;
            this.layoutResourceID=resource;
            this.data=data;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            View row =convertView;
            Holder holder=new Holder();

            if(row==null)
            {
                LayoutInflater inflater = ((Activity)context).getLayoutInflater();
                row = inflater.inflate(layoutResourceID, parent, false);
                holder.textView=(TextView)row.findViewById(R.id.text);
                holder.textView2=(TextView)row.findViewById(R.id.text2);
                if(holder.textView==null || holder.textView2==null)
                {
                    Log.e("got it","holder.textView is null");
                }
                row.setTag(holder);
            }else
            {
                holder=(Holder)row.getTag();
            }

            final Stand stand=data.get(position);
            holder.textView.setText(stand.standName);
            if(!stand.available())
                holder.textView2.setTextColor(getResources().getColor(R.color.UNAVAILABLE_LIST));
            else
                holder.textView2.setTextColor(getResources().getColor(R.color.AVAILABLE_LIST));
            holder.textView2.setText(stand.available);
            return row;
        }

        class Holder
        {
            TextView textView;
            TextView textView2;
        }
    }
}
