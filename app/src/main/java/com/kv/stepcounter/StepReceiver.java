package com.kv.stepcounter;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

public class StepReceiver extends BroadcastReceiver{

    private final String STEPS_BEFORE_RESTART = "STEPS_BEFORE_RESTART";
    private final String PREF_FILE_NAME = "STEP_COUNTER_FILE_KV";
    private final String LOG_TAG = "STEPPER_LOG_RECEIVER";
    private SensorManager mSensorManager;
    private Sensor mSensorCounter;
    private int totalStepsBeforeRestart;



    public StepReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {

//        Toast.makeText(context, "Started alarm manager", Toast.LENGTH_SHORT).show();
        AlarmManagerBroadcastReceiver alarm = new AlarmManagerBroadcastReceiver();
        alarm.SetAlarm(context);
    }
}
