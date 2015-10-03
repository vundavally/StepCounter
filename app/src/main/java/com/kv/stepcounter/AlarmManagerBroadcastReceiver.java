package com.kv.stepcounter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by KV026205 on 10/2/2015.
 */
public class AlarmManagerBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

//        Toast.makeText(context, "Service started by alarm manager", Toast.LENGTH_LONG).show();
        Intent myIntent = new Intent(context, StepCounterService.class);
        context.startService(myIntent);
    }

    public void SetAlarm(Context context)
    {

//        Toast.makeText(context, "creating alarm", Toast.LENGTH_SHORT).show();
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmManagerBroadcastReceiver.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
        //After after 5 seconds
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 1000 * 60, pi);
    }
}
