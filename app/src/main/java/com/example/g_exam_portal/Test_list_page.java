package com.example.g_exam_portal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;

public class Test_list_page extends AppCompatActivity {

    TextView text_type_name;
    ListView listView;
    Toolbar toolbar;

    public static ArrayList<String> check_test=new ArrayList<>();
    //    for test list
    public  static ArrayList<String> test_name=new ArrayList<>();
    public  static ArrayList<String> user_test_name=new ArrayList<>();
    //    for test information
    public static  ArrayList<Test_info_class> testInfo=new ArrayList<>();

    public static  String current_date;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list_page);
        toolbar=findViewById(R.id.Toolbar);
        toolbar.setTitle("Test list");
        setSupportActionBar(toolbar);

        text_type_name=findViewById(R.id.Test_type_name);
        listView=(ListView) findViewById(R.id.test_list);

        Intent intent=getIntent();
        String test_type= intent.getStringExtra("test_name");
        text_type_name.setText(test_type);


        String formattedDate;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Using java.time.LocalDate (API level 26 and above)
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            formattedDate = currentDate.format(formatter);
        } else {
            // Using java.text.SimpleDateFormat (for earlier versions)
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            formattedDate = formatter.format(new Date());
        }





        String test_name_query = null;
        String user_test_name_query = null;
        check_test.clear();

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        String id=sharedPreferences.getString("id","null");


        current_date=formattedDate;
        if(test_type.equals("Weekly_test"))
        {
            test_name_query ="?t1=week_test_list&t2="+formattedDate;
            user_test_name_query="?t1=record&t3=Weekly_test&t2="+id;
        }

        if(test_type.equals("Chapter_wise_test"))
        {
            test_name_query="?t1=Test_list&t2=Chapter_wise_test";
            user_test_name_query="?t1=record&t3=Chapter_wise_test&t2="+id;
        }


        get_data_from_url obj=new get_data_from_url();
        obj.execute(getString(R.string.server)+test_name_query);



        get_data_from_record obj_record=new get_data_from_record();
        obj_record.execute(getString(R.string.server)+user_test_name_query);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TextView textView1 = (TextView) view.findViewById(R.id.status);
                String s=textView1.getText().toString();

//                    validation of Chapter_wise_test list
                if(text_type_name.getText().toString().equals("Chapter_wise_test"))
                {
                    if(s.equals("complete"))
                    {
                        Toast.makeText(Test_list_page.this, "You have complete the test", Toast.LENGTH_SHORT).show();
                    }
                    else if(i==0 && s.equals("Not complete"))
                    {
                        Intent intent1=new Intent(Test_list_page.this,Test_instrucation.class);
                        intent1.putExtra("postion",i);
                        intent1.putExtra("duration",0);
                        startActivity(intent1);
                    }
                    else if(s.equals("Not complete") &&  check_test.get(i-1).toString().equals("complete"))
                    {
                        Intent intent1=new Intent(Test_list_page.this,Test_instrucation.class);
                        intent1.putExtra("postion",i);
                        intent1.putExtra("duration",0);
                        startActivity(intent1);
                    }
                    else {
                        Toast.makeText(Test_list_page.this, "Plese Complete the previous test ", Toast.LENGTH_SHORT).show();
                    }
                }

//                    validation for weel wise test list
                if(text_type_name.getText().toString().equals("Weekly_test") )
                {

                    if(s.equals("complete"))
                    {
                        Toast.makeText(Test_list_page.this, "You have complete the test", Toast.LENGTH_SHORT).show();
                    }
                    if(s.equals("Not complete") && current_date.equals(testInfo.get(i).test_date))
                    {
                        Intent intent1=new Intent(Test_list_page.this,Test_instrucation.class);
                        intent1.putExtra("postion",i);
                        intent1.putExtra("duration",5);
                        startActivity(intent1);
                    }
                    if(s.equals("Not complete") && !current_date.equals(testInfo.get(i).test_date))
                    {
                        Toast.makeText(Test_list_page.this, "test will  start soon", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });


    }


    class  get_data_from_url extends AsyncTask<String,Void,String>
    {


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONArray jsonArray=new JSONArray(s);
                JSONObject jsonObject=null;
                test_name.clear();
                testInfo.clear();
                for(int i=0;i< jsonArray.length();i++)
                {
                    jsonObject=jsonArray.getJSONObject(i);
                    String Test_name=(String) jsonObject.get("testname");
                    String Test_id=(String)jsonObject.get("id");
                    String Test_type=(String)jsonObject.get("test_type");
                    String Test_topic=(String) jsonObject.get("topic_name");
                    String Test_duration;
                    String Test_date;
                    if(Test_type.equals("Weekly_test"))
                    {
                        Test_date=(String) jsonObject.get("test_date");
                        Test_duration=(String)jsonObject.get("test_duration");
                    }else
                    {
                        Test_date=null;
                        Test_duration=null;
                    }
                    Test_info_class test_info_class=new Test_info_class(Test_id,Test_name,Test_type,Test_duration,Test_topic,Test_date);
                    testInfo.add(test_info_class);
                    test_name.add(Test_name);
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



    class  get_data_from_record extends AsyncTask<String,Void,String>
    {


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONArray jsonArray=new JSONArray(s);
                JSONObject jsonObject=null;
                user_test_name.clear();
                for(int i=0;i< jsonArray.length();i++)
                {
                    jsonObject=jsonArray.getJSONObject(i);
                    String Test_name=(String) jsonObject.get("test_name");
                    user_test_name.add(Test_name);
                }

                adapter myadpter=new adapter(getApplicationContext(),test_name,user_test_name);
                listView.setAdapter(myadpter);
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


    class  adapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> head11;

        ArrayList<String> head22;

        adapter(Context c,ArrayList<String> header1,ArrayList<String> header2)
        {
            super(c,R.layout.row,R.id.test_name,header1);
            context=c;
            this.head11=header1;
            this.head22=header2;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            LayoutInflater layoutInflater= (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(R.layout.row,parent,false);
            TextView test_name=view.findViewById(R.id.test_name);
            TextView testtopic=view.findViewById(R.id.sub_topic);
            TextView date=view.findViewById(R.id.date);
            TextView duration=view.findViewById(R.id.time);
            TextView check=view.findViewById(R.id.status);
            LinearLayout layout=view.findViewById(R.id.linear);


            String test_type=text_type_name.getText().toString();
            if(head22.contains(head11.get(position)))
            {
                test_name.setText(head11.get(position));
                layout.setBackgroundResource(R.drawable.listbox);
                check.setText("complete");
                check_test.add("complete");
            }
            else {
                test_name.setText(head11.get(position));
                layout.setBackgroundResource(R.drawable.listbox2);
                check_test.add("Not complete");
                check.setText("Not complete");
            }

            if(test_type.equals("Weekly_test"))
            {
                date.setVisibility(View.VISIBLE);
                duration.setVisibility(View.VISIBLE);
                date.setText(testInfo.get(position).test_date);
                duration.setText(testInfo.get(position).Test_duration);
            }
            testtopic.setText(testInfo.get(position).test_topic);
            return view;
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