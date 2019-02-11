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

public class AlarmReceiver2 extends BroadcastReceiver {
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10002";
    public AlarmReceiver2() {
    }

    @Override
    public void onReceive(Context mContext, Intent intent) {

        System.out.println("calling Alarm receiver2");


        /**Creates an explicit intent for an Activity in your app**/
        Intent resultIntent = new Intent(mContext , MainActivity.class);
        resultIntent.putExtra("NotificationMessage", "second");
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        PendingIntent resultPendingIntent = PendingIntent.getActivity(mContext,
                0 /* Request code */, resultIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent first = new Intent(mContext, MainActivity.class);
        first.putExtra("NotificationMessage", "second");
        first.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent firstone=PendingIntent.getActivity(mContext,7,first,PendingIntent.FLAG_UPDATE_CURRENT);

        Intent second = new Intent(mContext, search.class);
        second.putExtra("NotificationMessage", "second");
        second.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent secondone=PendingIntent.getActivity(mContext,8,second,PendingIntent.FLAG_UPDATE_CURRENT);






        mBuilder = new NotificationCompat.Builder(mContext);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle("Hey!! These Week's Events!!")
                .setContentText("You should check these week's popular events :)")
                .setAutoCancel(false)
                .setContentIntent(resultPendingIntent)
                .addAction(R.drawable.ic_ring_volume_black_24dp, "Show me!", firstone)
                .addAction(R.drawable.ic_ring_volume_black_24dp, "Search more..", secondone);



        mNotificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME2", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(1 /* Request Code */, mBuilder.build());




    }
}