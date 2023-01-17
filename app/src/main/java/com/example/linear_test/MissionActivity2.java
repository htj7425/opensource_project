package com.example.linear_test;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Random;

public class MissionActivity2 extends AppCompatActivity {
    TextView answerText;
    EditText answerInput;
    Button checkButton;

    int index;
    Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission2);

        context = this;

        answerText = findViewById(R.id.answer_text);
        answerInput = findViewById(R.id.answer_input);
        checkButton = findViewById(R.id.check_button);

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        index = random.nextInt(5);

        int num_1 = random.nextInt(100);
        int num_2 = random.nextInt(100);

        answerText.setText("" + num_1 + " + " + num_2);

        answerInput.setHint("정답을 입력해주세요.");

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Integer.parseInt(answerInput.getText().toString()) == num_1 + num_2){
                    final Intent stateIntent = new Intent(context, Alarm_Receiver.class);
                    stateIntent.putExtra("state", "alarm off");
                    sendBroadcast(stateIntent);

                    finish();
                }
                else{
                    answerInput.setText("");
                    answerInput.setHint("오답입니다.");
                }
            }
        });
    }
}