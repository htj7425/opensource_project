package com.example.linear_test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class Alarm_Receiver extends BroadcastReceiver {

    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;

        String get_yout_string = intent.getExtras().getString("state");

        Intent service_intent = new Intent(context, RingtonePlayingService.class);

        service_intent.putExtra("state", get_yout_string);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            this.context.startForegroundService(service_intent);
        } else{
            this.context.startService(service_intent);
        }
    }
}
