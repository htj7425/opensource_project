package com.example.linear_test;


import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.example.linear_test.adapter.LinearPickerAdapter;
import com.example.linear_test.adapter.TimeAdapter;
import com.example.linear_test.dialog.LinearTimePickerDialog;
import com.example.linear_test.view.LinearPickerView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    // 튜토리얼을 한번만 보여주도록 체크하는 변수
    boolean first_tutorial = true;
    // 설정한 알람 시간을 담아줄 TextView
    private TextView timeTextView;
    private ImageView delete_image;
    private TextView stopAlarm;
    int h = 1;
    int m = 50;
    boolean state = false;
    AlarmManager alarmManager;
    Context context;
    PendingIntent pendingIntent;
    int pendingFlag = 0;
    int index;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final int backgroundDark = ResourcesCompat.getColor(getResources(), R.color.background_dark, getTheme());
        final int foregroundDark = ResourcesCompat.getColor(getResources(), R.color.foreground_dark, getTheme());
        final int colorAccent = ResourcesCompat.getColor(getResources(), R.color.colorAccent, getTheme());


        LinearPickerView v = new LinearPickerView(this);
        Paint textPaint = new Paint();
        textPaint.setColor(Color.WHITE);

        //LinearPickerAdapter dateAdap = new DateAdapter(this, textPaint);
        LinearPickerAdapter timeAdap = new TimeAdapter(this, textPaint);
        LinearPickerAdapter colorAdap = new ColorAdapter(this, textPaint);
        LinearPickerAdapter graColorAdap = new GradientColorAdapter(this, textPaint);
        v.setAdapter(timeAdap);

//        setContentView(v);

        v.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        v.setLineColor(Color.GRAY);
        v.setActiveLineColor(Color.WHITE);
        v.setHandleBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
//        v.setHandleBackgroundColor(Color.TRANSPARENT);

        setContentView(R.layout.activity_main);

        this.context = this;

        // 알람매니저 설정
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        //캘린더 객체 생성
        final Calendar calendar = Calendar.getInstance();

        //알람리시버 intent 생성
        final Intent my_intent = new Intent(this.context, Alarm_Receiver.class);

        //설정한 알람 TextView id find
        timeTextView = findViewById(R.id.set_alarm_time);
        delete_image = findViewById(R.id.time_delete_btn);
        stopAlarm = findViewById(R.id.stop_alarm);


        stopAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random random = new Random();
                random.setSeed(System.currentTimeMillis());
                index = random.nextInt(2);

                if (state && index == 0){
                    timeTextView.setText("_ _ : _ _");
                    pendingFlag--;
                    Intent mIntent = new Intent(context, MissionActivity.class);
                    startActivity(mIntent);
                }
                else if(state && index == 1){
                    timeTextView.setText("_ _ : _ _");
                    pendingFlag--;
                    Intent mIntent = new Intent(context, MissionActivity2.class);
                    startActivity(mIntent);
                }
            }
        });


        findViewById(R.id.ltp_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(first_tutorial) {
                    LinearTimePickerDialog dialog = LinearTimePickerDialog.Builder.with(MainActivity.this)
                            .setDialogBackgroundColor(foregroundDark)
                            .setPickerBackgroundColor(backgroundDark)
                            .setLineColor(Color.argb(64, 255, 255, 255))
                            .setTextColor(Color.WHITE)
                            .setShowTutorial(true)
                            .setTextBackgroundColor(Color.argb(16, 255, 255, 255))
                            .setButtonCallback(new LinearTimePickerDialog.ButtonCallback() {
                                @Override
                                public void onPositive(DialogInterface dialog, int hour, int minutes) {
                                    delete_image.setClickable(true);

                                    //Toast.makeText(MainActivity.this, "" + hour + ":" + minutes, Toast.LENGTH_SHORT).show();

                                    h = hour;
                                    m = minutes;

                                    set_alarm(calendar, my_intent, h, m);

                                }

                                @Override
                                public void onNegative(DialogInterface dialog) {

                                }
                            })
                            .build();
                    dialog.show();
                    first_tutorial = false;
                }
                else{
                    LinearTimePickerDialog dialog = LinearTimePickerDialog.Builder.with(MainActivity.this)
                            .setDialogBackgroundColor(foregroundDark)
                            .setPickerBackgroundColor(backgroundDark)
                            .setLineColor(Color.argb(64, 255, 255, 255))
                            .setTextColor(Color.WHITE)
                            .setTextBackgroundColor(Color.argb(16, 255, 255, 255))
                            .setButtonCallback(new LinearTimePickerDialog.ButtonCallback() {
                                @Override
                                public void onPositive(DialogInterface dialog, int hour, int minutes) {
                                    h = hour;
                                    m = minutes;
                                    pendingIntent.cancel();
                                    set_alarm(calendar, my_intent, h, m);
                                }

                                @Override
                                public void onNegative(DialogInterface dialog) {

                                }
                            })
                            .build();
                    dialog.show();
                }
            }
        });


        /*findViewById(R.id.ltp_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinearDatePickerDialog.Builder.with(MainActivity.this)
                        .setDialogBackgroundColor(foregroundDark)
                        .setPickerBackgroundColor(backgroundDark)
//                        .setLineColor(Color.argb(64, 255, 255, 255))
                        .setTextColor(Color.WHITE)
//                        .setTextBackgroundColor(Color.argb(16, 255, 255, 255))
                        .setYear(2017)
                        .setMinYear(2000)
                        .setMaxYear(2030)
                        .setShowTutorial(true)
                        .setButtonCallback(new LinearDatePickerDialog.ButtonCallback() {
                            @Override
                            public void onPositive(DialogInterface dialog, int year, int month, int day) {
                                Toast.makeText(MainActivity.this, "" + year + "-" + month + "-" + day, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNegative(DialogInterface dialog) {

                            }
                        })
                        .build()
                        .show();
            }
        });*/
    }

    public void set_alarm(Calendar calendar, Intent my_intent, int h, int m){
        Toast.makeText(MainActivity.this, "" + h + ":" + m, Toast.LENGTH_SHORT).show();
        calendar.set(Calendar.HOUR_OF_DAY, h);
        calendar.set(Calendar.MINUTE, m);

        timeTextView.setText("" + (h < 10 ? "0"+h : "0"+(h%12)) + " : " + (m<10 ? "0"+m : m) + (h<12 ? " AM" : " PM"));

        my_intent.putExtra("state","alarm on");
        //sendBroadcast(my_intent);

        if(pendingFlag == 0) {
            pendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0, my_intent,
                    PendingIntent.FLAG_IMMUTABLE);
            pendingFlag++;
        }
        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                pendingIntent);
    }
    public void stop_alarm(Intent my_intent){
        timeTextView.setText("_ _ : _ _");
        my_intent.putExtra("state","alarm off");
        sendBroadcast(my_intent);
    }

    public void alarm_delete(View view) {
        timeTextView.setText("_ _ : _ _");
        if(pendingFlag == 1){
            pendingIntent.cancel();
            pendingFlag--;
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        processCommand(intent);
        super.onNewIntent(intent);
    }

    private void processCommand(Intent intent){
        if(intent != null){
            boolean state = intent.getBooleanExtra("state", false);
            this.state = state;
        }
    }
}