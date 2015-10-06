package com.kv.stepcounter;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service implements SensorEventListener {
    private final String STEPS_BEFORE_RESTART = "STEPS_BEFORE_RESTART";
    private final String PREF_FILE_NAME = "STEP_COUNTER_FILE_KV";
    private final String LOG_TAG = "STEPPER_LOG_RECEIVER";
    private SensorManager mSensorManager;
    private Sensor mSensorCounter;
    private int totalStepsBeforeRestart;

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        registerStepCounterSensor();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterStepCounterSensor();
    }



    private void registerStepCounterSensor()
    {
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensorCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensorManager.registerListener(this, mSensorCounter, SensorManager.SENSOR_DELAY_FASTEST);

    }

    private void unregisterStepCounterSensor()
    {
        mSensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Log.v("StepCounterLog", "Sensor change event");
        if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER)
        {
            Log.v("StepCounterLog", "StepCounter start");
            totalStepsBeforeRestart = (int)event.values[0];
            SharedPreferences sharedPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putInt(STEPS_BEFORE_RESTART, totalStepsBeforeRestart);
            editor.commit();
            Log.v("StepCounterLog", "StepCounter end");
            this.stopSelf();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
