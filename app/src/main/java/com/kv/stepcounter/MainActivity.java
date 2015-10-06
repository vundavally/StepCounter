package com.kv.stepcounter;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity implements SensorEventListener{
    private final String STEP_DETECTOR = "STEP_DETECTOR";
    private final String STEP_COUNTER = "STEP_COUNTER";
    private final String STEPS_BEFORE_RESTART = "STEPS_BEFORE_RESTART";
    private final String LOG_TAG = "STEPPER_LOG";
    private final String PREF_FILE_NAME = "STEP_COUNTER_FILE_KV";

    private SensorManager mSensorManager;
    private Sensor mSensorCounter;
    private Sensor mSensorDetector;
    private TextView stepDetectorView;
    private TextView stepCounterView;
    private TextView stepsBeforeRestartView;
    private int mSteps;
    private int totalStepsPrev;
    private int totalStepsCurrent;
    private int totalStepsBeforeRestart;
    private boolean isInitialLoad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        mSensorCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensorDetector = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        stepDetectorView = (TextView)findViewById(R.id.current_steps);
        stepCounterView = (TextView)findViewById(R.id.total_steps);
        stepsBeforeRestartView = (TextView)findViewById(R.id.steps_before_restart);

//        IntentFilter filter = new IntentFilter(Intent.ACTION_SHUTDOWN);
//        registerReceiver(new ShutDownReceiver(), filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
        SharedPreferences sharedPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(STEP_DETECTOR, mSteps);
        editor.putInt(STEP_COUNTER, totalStepsCurrent);
        editor.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sharedPref = getSharedPreferences(PREF_FILE_NAME, Context.MODE_PRIVATE);
        mSteps = sharedPref.getInt(STEP_DETECTOR, 0);
        stepDetectorView.setText(String.valueOf(mSteps));
        totalStepsPrev = sharedPref.getInt(STEP_COUNTER, 0);
        totalStepsBeforeRestart = sharedPref.getInt(STEPS_BEFORE_RESTART, -2);
        stepsBeforeRestartView.setText(String.valueOf(totalStepsBeforeRestart));
        mSensorManager.registerListener(this, mSensorDetector, SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, mSensorCounter, SensorManager.SENSOR_DELAY_NORMAL);
        isInitialLoad = true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        if(event.sensor.getType() == Sensor.TYPE_STEP_DETECTOR)
        {
            if(!isInitialLoad) {
                mSteps += (int)event.values.length;
                stepDetectorView.setText(String.valueOf(mSteps));
            }
        }
        else if (event.sensor.getType() == Sensor.TYPE_STEP_COUNTER)
        {
            totalStepsCurrent = (int)event.values[0];
            if(isInitialLoad && totalStepsPrev > 0) {
                int stepsDiff = totalStepsCurrent - totalStepsPrev;
                Log.v(LOG_TAG, "current value of step counter: " + totalStepsCurrent);
                Log.v(LOG_TAG, "previous value of step counter: " +totalStepsPrev);
                Log.v(LOG_TAG, "step detector count: " + mSteps);
                mSteps = mSteps + stepsDiff;
                stepDetectorView.setText(String.valueOf(mSteps));
                isInitialLoad = false;
            }

            stepCounterView.setText(String.valueOf(totalStepsCurrent));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
