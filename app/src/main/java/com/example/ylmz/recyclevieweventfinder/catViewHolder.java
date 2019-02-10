package com.example.ylmz.recyclevieweventfinder;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class catViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private ImageView pic;
    private TextView text1;

    private ListItemClickListener mListener;

    public catViewHolder(View itemView,ListItemClickListener listener) {
        super(itemView);


        pic = itemView.findViewById(R.id.viewimage);
        itemView.setOnClickListener(this);
        mListener = listener;

    }
    void bind(Object[] a){


        pic.setImageResource((int)a[0]);
    }
    public void onClick(View view) {
        int clickedPosition = getAdapterPosition();
        Log.v("ViewHolder","Item#"+Integer.toString(clickedPosition));
        mListener.onListItemClick(clickedPosition);
    }
}