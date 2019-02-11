package com.example.ylmz.recyclevieweventfinder;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.widget.Toast;

import java.util.Random;

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    public AlarmReceiver() {
    }

    @Override
    public void onReceive(Context mContext, Intent intent) {

        System.out.println("calling Alarm receiver1");


        /**Creates an explicit intent for an Activity in your app**/
        Intent resultIntent = new Intent(mContext , MainActivity.class);
        resultIntent.putExtra("NotificationMessage", "first");
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                1 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent first = new Intent(mContext, MainActivity.class);
        first.putExtra("NotificationMessage", "first");
        first.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent firstone=PendingIntent.getActivity(mContext,9,first,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent second = new Intent(mContext, search.class);
        second.putExtra("NotificationMessage", "first");
        second.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent secondone=PendingIntent.getActivity(mContext,10,second,PendingIntent.FLAG_UPDATE_CURRENT);






        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Hey!! Popular Events in Ankara!!")
                .setContentText("You should check these popular events in Ankara :)")
                .setAutoCancel(false)
                .setContentIntent(resultPendingIntent)
                .addAction(R.drawable.ic_ring_volume_black_24dp, "Show me!", firstone)
                .addAction(R.drawable.ic_ring_volume_black_24dp, "Search more..", secondone);



        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;

        mNotificationManager.notify(0 /* Request Code */, mBuilder.build());




    }
}