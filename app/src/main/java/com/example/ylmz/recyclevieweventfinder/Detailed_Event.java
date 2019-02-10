package com.example.ylmz.recyclevieweventfinder;

import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.example.ylmz.recyclevieweventfinder.LoadData.ids;

public class Detailed_Event extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    public Uri CONTENT_URI2 = Uri.parse("content://com.example.ylmz.recyclevieweventfinder").buildUpon()
            .appendPath("details").build();

    Cursor mycur;
    ImageView img1;
    TextView title;
    TextView desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Intent intentThatStartedThisActivity;
        String forecastStr;

        getLoaderManager().initLoader(2, null, this);



        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_event);

         img1=findViewById(R.id.eventimg);
         title=findViewById(R.id.texttitle);
         desc=findViewById(R.id.textdesc);





        //intentThatStartedThisActivity = getIntent();


       // if (intentThatStartedThisActivity.hasExtra("Info")){

         //   forecastStr = intentThatStartedThisActivity.getStringExtra("Info");
           // String[] infos = forecastStr.split("##");

            //title.setText(infos[0]);
            //desc.setText(infos[1]);
            //Log.v("extrasss",infos[2]);
            //Picasso.with(this).load(infos[2]).into(img1);

        }





    String[] projection={"eventid","title","image","description"};
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        return new CursorLoader(this,
                CONTENT_URI2,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mycur=data;

        int m=0;

        if (getIntent().hasExtra("Info")) {

            m = getIntent().getIntExtra("Info",0);
        }

        String id=ids.get(m);
        for(int k=0;k<mycur.getCount();k++){
            mycur.moveToPosition(k);
            if(mycur.getString(0).equals(id)){

                title.setText(mycur.getString(1));
                desc.setText(mycur.getString(3));

                Picasso.with(this).load(mycur.getString(2)).into(img1);

            }


        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {


    }
}



