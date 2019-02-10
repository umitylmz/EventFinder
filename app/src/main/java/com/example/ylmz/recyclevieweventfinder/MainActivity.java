package com.example.ylmz.recyclevieweventfinder;

import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.net.Uri;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TimeUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{



    private RecyclerView RW;
    private myAdapter adapter;
    private List<Event> events;


    public Uri CONTENT_URI = Uri.parse("content://com.example.ylmz.recyclevieweventfinder").buildUpon()
            .appendPath("events").build();

    private String URL_DATA_final;


    private Cursor mDetailCursor;

    private int mPosition;
    private Uri mUri;
    private static final int CURSOR_LOADER_ID = 0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()){
                case R.id.categories:
                    Intent intent=new Intent(MainActivity.this,Categories.class);
                    startActivity(intent);

                    break;
                case R.id.events:

                    break;
                case R.id.searching:

                    Intent intent2=new Intent(MainActivity.this,search.class);
                    startActivity(intent2);
                    break;

            }

            return false;
        }
    };




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RW = findViewById(R.id.recyclerview);
        RW.setHasFixedSize(true);


        getLoaderManager().initLoader(0, null, this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Event Finder");






        RW.setLayoutManager(new LinearLayoutManager(this));
        events=new ArrayList<>();



        adapter = new myAdapter(events,this);



        BottomNavigationView navig=(BottomNavigationView)findViewById(R.id.navigation2);
        navig.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Menu menu=navig.getMenu();
        MenuItem menuItem=menu.getItem(1);
        menuItem.setChecked(true);


        RW.setAdapter(adapter);


        String category="music";
        String date="Future";
        String keyword="";
        String location="san+diego";

        Bundle extras = getIntent().getExtras();
        if(extras!=null){
            if(extras.getString("startcat")!=null){
                category = extras.getString("startcat");

                Log.v("catttt",category);
            }
            else{
                category=extras.getString("catg");
                category=category.replaceAll(" ", "+");
                category=category.toLowerCase();
                Log.v("catttt2",category);
                keyword=extras.getString("key");
                keyword=keyword.replaceAll(" ", "+");
                Log.v("catttt2",keyword);
                location=extras.getString("location");
                location=location.replaceAll(" ", "+");
                Log.v("catttt2",location);
                date=extras.getString("date");
                date=date.replaceAll(" ", "+");
                Log.v("catttt2",date);
            }

            }


        URL_DATA_final = "http://api.eventful.com/json/events/search?app_key=WRRCckF42n7ZzTmp&location="+location+"&t="+date+"&page_size=15&sort_order=popularity&c="+category+"&q="+keyword;



        LoadData load = new LoadData((myAdapter) adapter, this);
        load.execute(URL_DATA_final);
        adapter.notifyDataSetChanged();


        Log.v("catttt2",URL_DATA_final);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        Context context = getApplicationContext();
        int duration    = Toast.LENGTH_SHORT;

        if (id == R.id.action_settings) {

            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);

        } else if (id == R.id.action_help){
            Toast toast = Toast.makeText(context, "I was just joking, I can't help you dude", duration);
            toast.show();
        } else if (id == R.id.action_refresh){
            String location="new york";
            String keyword="";
            String catg="music";
            String date="future";

            SharedPreferences prefs       = PreferenceManager.getDefaultSharedPreferences(this);
            location    = prefs.getString("location", "New+York");


            location=location.replaceAll(" ", "+");
            Log.v( "loc1",location);

            SharedPreferences prefs2       = PreferenceManager.getDefaultSharedPreferences(this);
            keyword   = prefs2.getString("keyword", "");
            keyword=keyword.replaceAll(" ", "+");
            Log.v( "loc2",keyword);
            SharedPreferences prefs3      = PreferenceManager.getDefaultSharedPreferences(this);
            date   = prefs3.getString("dates", "Future");
            date=date.replaceAll(" ", "+");
            Log.v( "loc3",date);

            SharedPreferences prefs4       = PreferenceManager.getDefaultSharedPreferences(this);
            catg   = prefs4.getString("categories", "Music");
            catg=catg.replaceAll(" ", "+");
            Log.v( "loc4",catg);

            catg=catg.toLowerCase();


            URL_DATA_final = "http://api.eventful.com/json/events/search?app_key=WRRCckF42n7ZzTmp&location="+location+"&t="+date+"&page_size=15&sort_order=popularity&c="+catg+"&q="+keyword;




            events.clear();
            Log.v(  "sonhal",URL_DATA_final);
            LoadData load = new LoadData((myAdapter) adapter, this);

            load.execute(URL_DATA_final);


            adapter.notifyDataSetChanged();

            Toast toast = Toast.makeText(context, "Refreshing", duration);
            toast.show();
        }

        return super.onOptionsItemSelected(item);
    }
    public void clearData() {
        events.clear(); //clear list
        adapter.notifyDataSetChanged(); //let your adapter know about the changes and reload view.
    }

    String[] projection={"eventid","title","time","picture","description"};

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle args) {

        return new CursorLoader(this,
                CONTENT_URI,
                projection,
                null,
                null,
                null);




    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

       adapter.swapCursor(data);





    }


    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }
}
