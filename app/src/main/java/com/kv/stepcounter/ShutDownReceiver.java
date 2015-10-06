package com.kv.stepcounter;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ShutDownReceiver extends BroadcastReceiver {
    public ShutDownReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.v("ShutDownLog", "Shutting start");
//        Toast.makeText(context, "Shutdown receiver", Toast.LENGTH_SHORT).show();
//        Intent myIntent = new Intent(context, StepCounterService.class);
//        context.startService(myIntent);

        context.stopService(new Intent(context, MyService.class));
        Log.v("ShutDownLog", "Shutting end");
    }
}
