package com.example.g_exam_portal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class Test_instrucation extends AppCompatActivity {


    Button start;

    Toolbar toolbar;
    SharedPreferences sharedPreferences;
    public static ArrayList<question_class> question=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_instrucation);

        toolbar=findViewById(R.id.Toolbar);
        toolbar.setTitle("Test instructions");
        setSupportActionBar(toolbar);
        start=findViewById(R.id.Start);


        Intent intent=getIntent();
        int test_postion=intent.getIntExtra("postion",0);
        int test_duration=intent.getIntExtra("duration",0);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putInt("postion",test_postion);
        editor.commit();

        Toast.makeText(this, "asd"+Test_list_page.testInfo.get(test_postion).getTest_id(), Toast.LENGTH_SHORT).show();
        String query="?t1=Test_question&t2="+Test_list_page.testInfo.get(test_postion).getTest_id();
        get_the_data_from_url obj=new get_the_data_from_url();
        obj.execute(getString(R.string.server)+query);

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(Test_instrucation.this, "size"+question.size(), Toast.LENGTH_SHORT).show();
                Intent intent1=new Intent(Test_instrucation.this,Test_page.class);

                if(test_duration>0)
                {
                    intent1.putExtra("duration",test_duration);
                }
                startActivity(intent1);
            }
        });

    }


    class  get_the_data_from_url extends AsyncTask<String,Void,String>
    {
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {

                JSONArray jsonArray=new JSONArray(s);
                JSONObject jsonObject=null;
                question.clear();
                for(int i=0;i< jsonArray.length();i++)
                {
                    jsonObject=jsonArray.getJSONObject(i);
                    String question_name=(String) jsonObject.get("que");
                    String option1=(String)jsonObject.get("o1");
                    String option2=(String)jsonObject.get("o2");
                    String option3=(String)jsonObject.get("o3");
                    String option4=(String)jsonObject.get("o4");
                    String correct=(String) jsonObject.get("coorect");
                    question_class Quest=new question_class(question_name,option1,option2,option3,option4,correct);
                    question.add(Quest);
                }
            }
            catch (Exception ec)
            {
                ec.getMessage();
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url=new URL(strings[0]);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                String s=convert_byte_to_string(bufferedReader);
                return s;
            }
            catch (Exception ex)
            {
                return ex.getMessage();
            }
        }
    }


    public String convert_byte_to_string(BufferedReader bufferedReader)
    {
        StringBuilder s=new StringBuilder();
        try {
            int i=bufferedReader.read();

            while (i!=-1)
            {
                s.append((char) i);
                i=bufferedReader.read();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return s.toString();
    }
}