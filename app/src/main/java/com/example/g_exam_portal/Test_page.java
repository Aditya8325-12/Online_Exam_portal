package com.example.g_exam_portal;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class Test_page extends AppCompatActivity {


    TextView questionNO,question;
    Button submit,next;

    RadioGroup radioGroup;
    RadioButton radioButton;
    RadioButton option1,option2,option3,option4;
    SeekBar seekBar;
    int seekbarTimer=1;
    int questioncount=0,rightAnswers=0,WrongAnswer=0;

    CountDownTimer countDownTimer;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_page);

        toolbar=findViewById(R.id.Toolbar);
        toolbar.setTitle("Test");
        setSupportActionBar(toolbar);

        submit=findViewById(R.id.submit);
        next=findViewById(R.id.next);
        questionNO=findViewById(R.id.questionNo);
        radioGroup=findViewById(R.id.RadioGroup);
        question=findViewById(R.id.qestion);
        option1=findViewById(R.id.option1);
        option2=findViewById(R.id.option2);
        option3=findViewById(R.id.option3);
        option4=findViewById(R.id.option4);
        seekBar=findViewById(R.id.Seekbar);

        Intent intent=getIntent();
        int minutes=intent.getIntExtra("duration",0);
        int End_time=0;
        if(minutes>0)
        {
            int seekbar_max=minutes*60;
            End_time=minutes*60000;
            seekBar.setMax(seekbar_max);
        }
        else {
            int seekbar_max=60;
            End_time=60000;
            seekBar.setMax(seekbar_max);
        }

        int qes=questioncount+1;

        questionNO.setText("Question "+qes +" Of  "+ Test_instrucation.question.size());
        question.setText("q."+Test_instrucation.question.get(questioncount).getQuestion());
        option1.setText(""+Test_instrucation.question.get(questioncount).getOption1());
        option2.setText(""+Test_instrucation.question.get(questioncount).getOption2());
        option3.setText(""+Test_instrucation.question.get(questioncount).getOption3());
        option4.setText(""+Test_instrucation.question.get(questioncount).getOption4());

        countDownTimer=new CountDownTimer(End_time,1000) {
            @Override
            public void onTick(long l) {
                seekbarTimer++;
                seekBar.setProgress(seekbarTimer);
            }

            @Override
            public void onFinish() {
                Intent intent=new Intent(Test_page.this,Test_result_page.class);
                intent.putExtra("correct",rightAnswers);
                intent.putExtra("wrong",WrongAnswer);
                startActivity(intent);
                questioncount=0;
                WrongAnswer=0;
                rightAnswers=0;
            }
        };

        countDownTimer.start();


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(radioGroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(Test_page.this, "Please select The Option", Toast.LENGTH_SHORT).show();
                }
                else {
                    int check=radioGroup.getCheckedRadioButtonId();
                    radioButton=findViewById(check);
                    CheckAnswerRightOrNot(radioButton.getText().toString());
                    questioncount++;
                    checkQuestionStatus();
                }
            }
        });


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(radioGroup.getCheckedRadioButtonId()==-1)
                {
                    Toast.makeText(Test_page.this, "Please select The Option", Toast.LENGTH_SHORT).show();
                }
                else {
                    int check=radioGroup.getCheckedRadioButtonId();
                    radioButton=findViewById(check);
                    CheckAnswerRightOrNot(radioButton.getText().toString());
                    Intent intent=new Intent(Test_page.this,Test_result_page.class);
                    intent.putExtra("correct",rightAnswers);
                    intent.putExtra("wrong",WrongAnswer);
                    startActivity(intent);
                    if(countDownTimer!=null)
                    {
                        countDownTimer.cancel();
                    }
                    questioncount=0;
                    WrongAnswer=0;
                    rightAnswers=0;
                }
            }
        });


    }



    public void checkQuestionStatus()
    {
        radioGroup.clearCheck();
//            update question no
        if(questioncount+1==Test_instrucation.question.size())
        {
            submit.setVisibility(View.VISIBLE);
            next.setVisibility(View.GONE);
        }
//            check how many question are complete
        int qes=questioncount+1;
        questionNO.setText("Question "+qes +" Of  "+Test_instrucation.question.size());

        //            update question
        question.setText("");
        question.setText(""+Test_instrucation.question.get(questioncount).getQuestion());

        option1.setText(""+Test_instrucation.question.get(questioncount).getOption1());
        option2.setText(""+Test_instrucation.question.get(questioncount).getOption2());
        option3.setText(""+Test_instrucation.question.get(questioncount).getOption3());
        option4.setText(""+Test_instrucation.question.get(questioncount).getOption4());

    }

    public void CheckAnswerRightOrNot(String check)
    {

        if(check.equals(Test_instrucation.question.get(questioncount).getCoorect()))
        {
            rightAnswers++;
        }
        else
        {
            WrongAnswer++;
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        androidx.appcompat.app.AlertDialog.Builder alert=new AlertDialog.Builder(this)
                .setTitle("End Test")
                .setMessage("Do you want to end the test")
                .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent=new Intent(Test_page.this,Test_result_page.class);
                        intent.putExtra("correct",rightAnswers);
                        intent.putExtra("wrong",WrongAnswer);
                        if(countDownTimer!=null)
                        {
                            countDownTimer.cancel();
                        }
                        questioncount=0;
                        WrongAnswer=0;
                        rightAnswers=0;
                        startActivity(intent);
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
    }
}