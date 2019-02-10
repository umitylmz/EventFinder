package com.example.ylmz.recyclevieweventfinder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import static com.example.ylmz.recyclevieweventfinder.LoadData.ids;


public class myAdapter extends RecyclerView.Adapter<EventHolder> {

    List<Event> events;
    private Context context;
    public static Cursor mCursor;
    Uri URI3 = Uri.parse("content://com.example.ylmz.recyclevieweventfinder").buildUpon()
            .appendPath("events").build();

    String[] projection={"title","time","picture","description"};

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public void swapItems(List<Event> change){
        this.events = change;
        notifyDataSetChanged();
    }

    public myAdapter(List<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v =LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.events,viewGroup,false);

        return new EventHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull EventHolder eventHolder, final int i) {

        //Event event=events.get(i);
        //eventHolder.title.setText(event.getTitle());
        //eventHolder.time.setText(event.getTime());
        //Picasso.with(context).load(event.getImageURL()).into(eventHolder.image);

        String title="";
        String time="";
        String image="";
        String description="";

        String id=ids.get(i);

        Cursor mcursor =context.getContentResolver().query(URI3,projection,"eventid =?",new String[]{id},null);
        mcursor.moveToPosition(0);

                title=mcursor.getString(0);

                time=mcursor.getString(1);
                image=mcursor.getString(2);
                description=mcursor.getString(3);







        eventHolder.title.setText(title);
        eventHolder.time.setText(time);

        try {
            Picasso.with(context).load(image).into(eventHolder.image);
        }
        catch(Exception e){
            Picasso.with(context).load("http://www.crucial.com.au/blog/wp-content/uploads/2014/12/events_medium.jpg").into(eventHolder.image);
        }




        final String finalTitle = title;
        final String finalDescription = description;
        final String finalImage = image;
        eventHolder.layout.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                Log.v( "ffuyt","f");

                Intent detailActivityIntent;
                detailActivityIntent = new Intent(context, Detailed_Event.class);
                String details= finalTitle +"##"+ finalDescription +"##"+ finalImage;

                detailActivityIntent.putExtra("Info", i);
                context.startActivity(detailActivityIntent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    public void swapCursor(Cursor newCursor){


        mCursor = newCursor;
        notifyDataSetChanged();
    }

}

