package com.example.ylmz.recyclevieweventfinder;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import static com.example.ylmz.recyclevieweventfinder.MainActivity.adapter;


public class ResponseReceiver extends BroadcastReceiver {
    public static final String ACTION_RESP = "com.saaranga.intent.action.MESSAGE_PROCESSED";
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.v("SERVİSTENBİTTİ",String.valueOf(adapter.getItemCount()));
        adapter.notifyDataSetChanged();



    }

}