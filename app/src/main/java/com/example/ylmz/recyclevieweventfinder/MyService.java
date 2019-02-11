package com.example.ylmz.recyclevieweventfinder;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import static com.example.ylmz.recyclevieweventfinder.MainActivity.adapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.ylmz.recyclevieweventfinder.MainActivity.myp;
import static com.example.ylmz.recyclevieweventfinder.myAdapter.ids;

public class MyService extends IntentService {


    private ContentResolver mResolver;
    MyHelper db= new MyHelper(this);
    private ProgressDialog dialog;



    public MyService(){
        super("MyService");

    }

    private void getWeatherDataFromJson(String response)
            throws JSONException {



        mResolver=this.getContentResolver();

        ids.clear();
        List<Event> events = new ArrayList<>();

        JSONObject jsonobject=new JSONObject(response);
        if(jsonobject.getString("events").equals("null")) {


            Event a= new Event("SORRY, WE COULDN'T FIND ANY EVENT FOR YOU.","Please be less selective on search","http://www.crucial.com.au/blog/wp-content/uploads/2014/12/events_medium.jpg","d");
            events.add(a);





        }
        else{
            JSONArray jsonArray =jsonobject.getJSONObject("events").getJSONArray("event");

            int size=jsonArray.length();

            for (int i = 0; i < size; i++){

               Log.v("SERVİSTENİTEM",String.valueOf(adapter.getItemCount()));
                JSONObject jo = jsonArray.getJSONObject(i);
                String a=jo.getString("id");
                ids.add(a);
                String desc;
                String imageurl;


                String eventid=jo.getString("id");

                Cursor saved = db.getWritableDatabase().rawQuery("SELECT count(*) FROM events WHERE eventid = '" +jo.getString("id") +"'",null );


                Uri CONTENT_URI = Uri.parse("content://com.example.ylmz.recyclevieweventfinder").buildUpon()
                        .appendPath("events").build();

                Uri CONTENT_URI2 = Uri.parse("content://com.example.ylmz.recyclevieweventfinder").buildUpon()
                        .appendPath("details").build();

                Log.v("SERVİSTEN",jo.getString("id"));


                saved.moveToFirst();
                int num=saved.getInt(0);

                if(num==0)
                {


                    Log.v("ilksefer","ilksefer");


                    if(jo.getString("image").equals("null")){
                        Log.v("alsana","tak");
                        imageurl="http://www.crucial.com.au/blog/wp-content/uploads/2014/12/events_medium.jpg";
                    }
                    else{
                        imageurl=jo.getJSONObject("image").getJSONObject("medium").getString("url");
                        Log.v("alsana2",imageurl);
                    }
                    if(!(imageurl.contains("http:"))){
                        imageurl="http:"+imageurl;
                    }

                    if(jo.getString("description").equals("null")){
                        desc=jo.getString("title");
                    }
                    else{
                        desc=jo.getString("description");
                    }

                    ContentValues EventValues = new ContentValues();
                    EventValues.put("eventid"   , jo.getString("id"));
                    EventValues.put("city"   , jo.getString("city_name"));
                    EventValues.put("picture"   , imageurl);
                    EventValues.put("title"   ,  jo.getString("title"));
                    EventValues.put("description"   ,  desc);
                    EventValues.put("time"   ,   jo.getString("start_time"));


                    mResolver.insert(CONTENT_URI, EventValues);




                    ContentValues detailValues = new ContentValues();
                    detailValues.put("eventid"   , jo.getString("id"));
                    detailValues.put("image"   , imageurl);
                    detailValues.put("title"   ,  jo.getString("title"));
                    detailValues.put("description"   ,  desc);



                    mResolver.insert(CONTENT_URI2, detailValues);



                }







                Log.v("SERVİSTENİTEMCOUNT2",String.valueOf(ids.size()));





                Event event=new Event("l","l","l","l");

                events.add(event);











            }



        }

        Intent broadcastIntent = new Intent();
        broadcastIntent.setAction(ResponseReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
        sendBroadcast(broadcastIntent);


    }

    @Override
    protected void onHandleIntent(Intent intent) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = intent.getStringExtra("myURL");


        Log.v("SERVİSTEN",forecastJsonStr);


        StringRequest myrequest = new StringRequest(Request.Method.GET, forecastJsonStr, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                try {


                   getWeatherDataFromJson(response);



                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


                Log.v("dds",error.getMessage());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(myrequest);






    }

    }



