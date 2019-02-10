package com.example.ylmz.recyclevieweventfinder;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

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


public class LoadData extends AsyncTask<String, Void, List<Event> > {

    myAdapter mWeatherAdapter;
    Context mContext;
    ContentResolver mResolver;
    String[] selected;

    public static ArrayList<String> ids=new ArrayList<String>();;




    private ProgressDialog dialog;
    MyHelper db;


    public LoadData(myAdapter weatherAdapter, Context context) {
        mWeatherAdapter = weatherAdapter;
        mContext = context;
        dialog = new ProgressDialog(context);
        db=new MyHelper(context);


        mResolver  = mContext.getContentResolver();
    }

    @Override
    protected List<Event> doInBackground(String... urlStrings) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String forecastJsonStr = null;

        try {
            URL weatherURL = new URL(urlStrings[0]);
            urlConnection = (HttpURLConnection) weatherURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if (inputStream != null) {
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }
                if (buffer.length() != 0) {
                    forecastJsonStr = buffer.toString();
                }
            }
        } catch (IOException e) {
            Log.e("MainActivity", "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {



                    Log.e("MainActivity", "Error closing stream", e);
                }
            }
        }

        try {
            return getWeatherDataFromJson(forecastJsonStr);
        } catch (JSONException e) {

            Log.e("FetchWeatherTask", e.getMessage(), e);
        }
        List<Event> events = new ArrayList<>();
        Event a= new Event("SORRY, WE COULDN'T FIND ANY EVENT FOR YOU.","Please be less selective on search","http://www.crucial.com.au/blog/wp-content/uploads/2014/12/events_medium.jpg","d");
        events.add(a);

        return events;

    }
    protected void onPreExecute() {
        this.dialog.setMessage("Calm down, I am loading your events...");
        this.dialog.show();
    }




    @Override
    protected void onPostExecute(List<Event> eventlist) {
        super.onPostExecute(eventlist);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        Log.v("GÖRELİM",eventlist.get(0).getTitle());

        mWeatherAdapter.setEvents(eventlist);
        mWeatherAdapter.notifyDataSetChanged();
    }


    private List<Event> getWeatherDataFromJson(String response)
            throws JSONException {


        ids.clear();
        List<Event> events = new ArrayList<>();

        JSONObject jsonobject=new JSONObject(response);
        if(jsonobject.getString("events").equals("null")) {


            Event a= new Event("SORRY, WE COULDN'T FIND ANY EVENT FOR YOU.","Please be less selective on search","http://www.crucial.com.au/blog/wp-content/uploads/2014/12/events_medium.jpg","d");
            events.add(a);
            return events;




        }
        else{
            JSONArray jsonArray =jsonobject.getJSONObject("events").getJSONArray("event");

            int size=jsonArray.length();

            for (int i = 0; i < size; i++){

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













                Event event=new Event("l","l","l","l");

                events.add(event);










            }



        }

        return events;
    }

}