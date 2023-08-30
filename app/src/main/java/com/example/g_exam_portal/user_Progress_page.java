package com.example.g_exam_portal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class user_Progress_page extends AppCompatActivity {


    TextView test_name,test_type,total_question,total_marks,marks_obtain;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_progress_page);
        toolbar=findViewById(R.id.Toolbar);
        toolbar.setTitle("Progress");
        setSupportActionBar(toolbar);

        test_name=findViewById(R.id.test_name);
        total_question=findViewById(R.id.total_question);
        total_marks=findViewById(R.id.total_marks);
        marks_obtain=findViewById(R.id.marks_obtain);
        test_type=findViewById(R.id.test_type);

        Intent intent=getIntent();
        int position=intent.getIntExtra("position",0);
        Toast.makeText(this, ""+position, Toast.LENGTH_SHORT).show();
        marks_obtain.setText("Marks  obtain : "+user_test_list_page.save_data.get(position).marks_obtain);
        test_name.setText(""+user_test_list_page.save_data.get(position).test_name);
        total_question.setText("Total question  :"+user_test_list_page.save_data.get(position).getTotal_question());
        total_marks.setText("Total Marks  :"+user_test_list_page.save_data.get(position).getTotal_question());
        if(user_test_list_page.save_data.get(position).getTest_type().equals("Chapter_wise_test"))
        {
            test_type.setText("Chapter Test");
        }
        else {
            test_type.setText("Week Tests");
        }

    }
}