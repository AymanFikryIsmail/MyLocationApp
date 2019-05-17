package com.objects.mylocation.mylocation.utils;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.objects.mylocation.mylocation.R;
import com.objects.mylocation.mylocation.model.pojo.AddressPojo;
import com.objects.mylocation.mylocation.view.ui.addressList.AddressListActivity;

/**
 * Created by ayman on 2019-02-17.
 */

public class NotificationHelper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";

    private NotificationManager mManager;
    Context context;

    public NotificationHelper(Context base) {
        context = base;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        } else {
            if (mManager == null) {
                mManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        mManager = context.getSystemService(NotificationManager.class);
        mManager.createNotificationChannel(channel);
    }

    public void createNotification(AddressPojo addressPojo) {
        // content intent
        Intent alarmIntent = new Intent(context, AddressListActivity.class);
        PendingIntent alarmPendingIntent = PendingIntent.getActivity(context, addressPojo.getId(), alarmIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);


        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelID)
                .setOngoing(true)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.app_logo)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND)
                .setContentInfo("Info")
                .setContentTitle("Address added successfully ")
                .setStyle(new NotificationCompat.BigTextStyle())
                .setLights(Color.BLUE, 500, 500)
                .setContentText(addressPojo.getRegionName() + " is added ")
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .setContentIntent(alarmPendingIntent);
        mManager.notify(addressPojo.getId(), mBuilder.build());
    }

}
