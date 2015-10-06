package com.kv.stepcounter;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p/>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class StepCounterService extends IntentService implements SensorEventListener{
    // TODO: Rename actions, choose action names that describe tasks that this
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_FOO = "com.kv.stepcounter.action.FOO";
    private static final String ACTION_BAZ = "com.kv.stepcounter.action.BAZ";

    // TODO: Rename parameters
    private static final String EXTRA_PARAM1 = "com.kv.stepcounter.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.kv.stepcounter.extra.PARAM2";

    private final String STEPS_BEFORE_RESTART = "STEPS_BEFORE_RESTART";
    private final String PREF_FILE_NAME = "STEP_COUNTER_FILE_KV";
    private final String LOG_TAG = "STEPPER_LOG_RECEIVER";
    private SensorManager mSensorManager;
    private Sensor mSensorCounter;
    private int totalStepsBeforeRestart;

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
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

    /**
     * Starts this service to perform action Foo with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionFoo(Context context, String param1, String param2) {
        Intent intent = new Intent(context, StepCounterService.class);
        intent.setAction(ACTION_FOO);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    /**
     * Starts this service to perform action Baz with the given parameters. If
     * the service is already performing a task this action will be queued.
     *
     * @see IntentService
     */
    // TODO: Customize helper method
    public static void startActionBaz(Context context, String param1, String param2) {
        Intent intent = new Intent(context, StepCounterService.class);
        intent.setAction(ACTION_BAZ);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }

    public StepCounterService() {
        super("StepCounterService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_FOO.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionFoo(param1, param2);
            } else if (ACTION_BAZ.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionBaz(param1, param2);
            }


        }
    }

    /**
     * Handle action Foo in the provided background thread with the provided
     * parameters.
     */
    private void handleActionFoo(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /**
     * Handle action Baz in the provided background thread with the provided
     * parameters.
     */
    private void handleActionBaz(String param1, String param2) {
        // TODO: Handle action Baz
        throw new UnsupportedOperationException("Not yet implemented");
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
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
