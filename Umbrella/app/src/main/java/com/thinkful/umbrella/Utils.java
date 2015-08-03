package com.thinkful.umbrella;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by ardokusuma on 7/23/15.
 */
public class Utils {
    public static PendingIntent getMainActivityPendingIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public static PendingIntent getBroadcastActivityPendingIntentAlarm(Context context, double lat, double lng) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(NotifyService.LATITUDE, lat);
        intent.putExtra(NotifyService.LONGITUDE, lng);
        return PendingIntent.getBroadcast(context, 0, intent, 0);
    }

    public static PendingIntent getBroadcastActivityPendingIntent(Context context, String latitude, String longitude) {
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        intent.putExtra(NotifyService.LATITUDE, latitude);
        intent.putExtra(NotifyService.LONGITUDE, longitude);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0);
        return(pendingIntent);
    }
}
