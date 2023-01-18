package com.example.linear_test;


import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

public class RingtonePlayingService extends Service {
    MediaPlayer mediaPlayer;
    int startId;
    boolean isRunning;

    @Override
    public void onCreate() {
        super.onCreate();



        if(Build.VERSION.SDK_INT >= 26){
            String CHANNEL_ID = "default";
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("알람시작")
                    .setContentText("알림음이 재생됩니다.")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .build();
            startForeground(1, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String getState = intent.getExtras().getString("state");

        assert getState != null;

        if(getState.equals("alarm on")){
            mediaPlayer = MediaPlayer.create(this,R.raw.ouu);
            mediaPlayer.start();
            this.isRunning = true;

            Intent stateIntent = new Intent(getApplicationContext(), MainActivity.class);
            stateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            stateIntent.putExtra("state", this.isRunning);
            startActivity(stateIntent);
        }
        else if(getState.equals("alarm off")) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();

            this.isRunning = false;

            Intent stateIntent = new Intent(this, MainActivity.class);
            stateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            stateIntent.putExtra("state", this.isRunning);
            startActivity(stateIntent);
        }
        else if(getState.equals("want state")){
            Intent stateIntent = new Intent(getApplicationContext(), MainActivity.class);
            stateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            stateIntent.putExtra("state", this.isRunning);
            startActivity(stateIntent);
        }
        else {
            this.isRunning = false;
            Intent stateIntent = new Intent(this, MainActivity.class);
            stateIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);

            stateIntent.putExtra("state", this.isRunning);
            startActivity(stateIntent);
        }
        return START_NOT_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        Log.d("onDestory() 실행", "서비스 파괴");

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
