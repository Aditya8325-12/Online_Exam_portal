package com.example.g_exam_portal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Test_result_page extends AppCompatActivity {


    TextView test_name,test_type,marks,score;
    LinearLayout star;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_test_result_page);
        test_name=findViewById(R.id.test_name);
        test_type=findViewById(R.id.test_type);
        marks=findViewById(R.id.marks);
        star=findViewById(R.id.star);
        score=findViewById(R.id.score);


        Intent intent=getIntent();
        int correct_question=intent.getIntExtra("correct",0);
        int total_question= Test_instrucation.question.size();

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        int postion=sharedPreferences.getInt("postion",0);
        String user_id=sharedPreferences.getString("id","");



        marks.setText("Correct Answer = "+correct_question);
        test_name.setText(""+Test_list_page.testInfo.get(postion).getTest_name());


        if(Test_list_page.testInfo.get(postion).getTest_type().equals("Chapter_wise_test"))
        {
            test_type.setText("Chapter Test");
        }
        else {
            test_type.setText("Week Tests");
        }

        if(score_validation(total_question,correct_question))
        {

            String path="?t1=test_result&t2="+Test_list_page.testInfo.get(postion).getTest_name()+"&t3="+Test_list_page.testInfo.get(postion).getTest_type()+"&t4="+total_question+"&t5="+total_question+"&t6="+correct_question+"&t7="+user_id;

            send_data_to_url onk2=new send_data_to_url();
            onk2.execute(getString(R.string.server)+path);
        }
    }


    class send_data_to_url extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {
            try {
                URL url=new URL(strings[0]);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                return  bufferedReader.readLine();
            }catch (Exception e)
            {
                return e.getMessage();
            }
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Toast.makeText(Test_result_page.this, "Register Done", Toast.LENGTH_SHORT).show();
        }

    }



    public boolean score_validation(int total,int marks)
    {
        int result=(marks*100)/total;

        if(result<50 &&  result>=25)
        {
            ImageView imageView=new ImageView(this);
            imageView.setLayoutParams(new LinearLayout.LayoutParams(200,200));
            int id=12;
            imageView.setId(id);
            imageView.setImageResource(R.drawable.star);
            star.addView(imageView);
            score.setText("good");
        }
        if(result>=50 &&  result<75 )
        {

            for(int i=1;i<=2;i++)
            {
                ImageView imageView=new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(200,200));
                int id=12;
                imageView.setId(id+i);
                imageView.setImageResource(R.drawable.star);
                star.addView(imageView);
            }
            score.setText(" better");
        }
        if(result<=100 &&  result>=75)
        {


            for(int i=1;i<=3;i++)
            {
                ImageView imageView=new ImageView(this);
                imageView.setLayoutParams(new LinearLayout.LayoutParams(200,200));
                int id=12;
                imageView.setId(id+i);
                imageView.setImageResource(R.drawable.star);
                star.addView(imageView);
            }
            score.setText("best");
            return true;

        }

        if(result>=0 && result<25)
        {
            score.setText("better luck next time");
        }

        return false;

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(Test_result_page.this, Home.class));
    }
}