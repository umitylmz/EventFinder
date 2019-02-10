package com.example.ylmz.recyclevieweventfinder;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class catAdapter extends RecyclerView.Adapter<catViewHolder> {
    Object[][] list;
    private final ListItemClickListener mOnClickListener;

    public catAdapter(Object[][] a,  ListItemClickListener listener){
        list=a;
        mOnClickListener=listener;

    }

    public catViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.categories_layout;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, viewGroup, shouldAttachToParentImmediately);
        catViewHolder viewHolder = new catViewHolder(view,mOnClickListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull catViewHolder dataViewHolder, int i) {
        dataViewHolder.bind(list[i]);
    }

    @Override
    public int getItemCount() {
        return list.length;
    }
}
