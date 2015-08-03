package com.thinkful.umbrella;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v7.app.NotificationCompat;

/**
 * Created by ardokusuma on 7/23/15.
 */
public class Notifier {
    private int notificationID = 100;

    public void createNotification(Context context, String title, String msg) {
        //Build your notification
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context);
        nBuilder.setContentTitle(title);
        nBuilder.setContentText(msg);
        nBuilder.setSmallIcon(R.mipmap.ic_launcher);
        nBuilder.setAutoCancel(true);

        //Add a notification action
        nBuilder.setContentIntent(Utils.getMainActivityPendingIntent(context));

        //post notification
        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(notificationID, nBuilder.build());
    }
}
