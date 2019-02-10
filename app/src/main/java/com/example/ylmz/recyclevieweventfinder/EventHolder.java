package com.example.ylmz.recyclevieweventfinder;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.Constraints;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class EventHolder extends RecyclerView.ViewHolder {

    public TextView title;
    public TextView time;
    public ImageView image;
    public ConstraintLayout layout;

    @SuppressLint("WrongViewCast")
    public EventHolder(@NonNull View itemView) {
        super(itemView);

        title=itemView.findViewById(R.id.title);
        time=itemView.findViewById(R.id.time);
        image=(ImageView)itemView.findViewById(R.id.image);
        layout=itemView.findViewById(R.id.parent_layout);


    }
}
