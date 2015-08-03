package com.thinkful.umbrella;

import android.app.AlarmManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;

import java.util.Calendar;

/**
 * Created by ardokusuma on 7/23/15.
 */
public class Alarm {
    public void setAlarm(Context context, AlarmSetParams alarmSetParams, UmbrellaLocation loc) {
        // Get reference to AlarmManager
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // RTC alarm repeating
        Calendar calendar = Calendar.getInstance();

        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, alarmSetParams.getHourOfDay());
        calendar.set(Calendar.MINUTE, alarmSetParams.getMinuteOfHour());
        calendar.set(Calendar.SECOND, alarmSetParams.getSecondOfMinute());

        long milliseconds = calendar.getTimeInMillis();

        alarmMgr.setInexactRepeating(AlarmManager.RTC, milliseconds,
                AlarmManager.INTERVAL_DAY, Utils.getBroadcastActivityPendingIntentAlarm(context,
                        loc.getLatitude(),
                        loc.getLongitude()));

//        setBootReceiverComponentEnabled(context, PackageManager.COMPONENT_ENABLED_STATE_ENABLED);
    }

    public void cancelAlarm(Context context, UmbrellaLocation loc) {
        AlarmManager alarmMgr = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmMgr.cancel(Utils.getBroadcastActivityPendingIntentAlarm(context,
                loc.getLatitude(),
                loc.getLongitude()));
//        setBootReceiverComponentEnabled(context, PackageManager.COMPONENT_ENABLED_STATE_DISABLED);
    }

    private void setBootReceiverComponentEnabled(Context context, int componentEnabledState) {
        ComponentName receiver = new ComponentName(context, AlarmBroadcastReceiver.class);
        PackageManager pm = context.getPackageManager();
        pm.setComponentEnabledSetting(receiver,
                componentEnabledState,
                PackageManager.DONT_KILL_APP);
    }
}
