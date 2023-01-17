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

public class MissionActivity extends AppCompatActivity {
    TextView answerText;
    EditText answerInput;
    Button checkButton;
    /*
    String answer_list[] =
            {"오늘 걷지 않으면 내일은 뛰어야 한다. " +
                    "지금 잠을 자면 꿈을 꾸지만 잠을 자지 않으면 꿈을 이룬다. " +
                    "-도스토예프스키",
                    "스스로 알을 깨면 한 마리 병아리가 되지만 남이 깨주면 프라이가 된다. " +
                            "-J.허슬러",
            "위대한 인물에게는 목표가 있고 평범한 사람들에게는 소원이 있을 뿐이다. " +
                    "-워싱턴 어빙",
            "일생동안 나는 항상 무언가 대단한 사람이되길 원했다." +
                    "그런데 지금에 와서 보니 그 당시 좀더 구체적인 목표를" +
                    "세웠어야 했었다는 것을 알았다. -크리스티",
            "실수하지 않는 사람이 되는 것보다 포기하지 않는 사람이 되는 것이 중요하다" +
                    "-보도셰퍼"};*/
    String answer_list[] = {"a","b","c","d","e"};
    int index;
    Context context;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission);

        context = this;

        answerText = findViewById(R.id.answer_text);
        answerInput = findViewById(R.id.answer_input);
        checkButton = findViewById(R.id.check_button);

        Random random = new Random();
        random.setSeed(System.currentTimeMillis());
        index = random.nextInt(5);

        answerText.setText(answer_list[index]);
        answerInput.setHint("입력 : " + answerText.getText());

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(answerText
                        .getText()
                        .toString()
                        .replaceAll(" ","")
                        .equals(answerInput
                                .getText()
                                .toString()
                                .replaceAll(" ", ""))){

                    final Intent stateIntent = new Intent(context, Alarm_Receiver.class);
                    stateIntent.putExtra("state", "alarm off");
                    sendBroadcast(stateIntent);

                    finish();
                }
                else{
                    answerInput.setText("");
                    answerInput.setHint("잘 못 입력하셨습니다.");
                }
            }
        });
    }
}