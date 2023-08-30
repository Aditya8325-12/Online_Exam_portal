package com.example.g_exam_portal;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import java.util.ArrayList;

public class user_test_list_page extends AppCompatActivity {

    Toolbar toolbar;

    LinearLayout chapter,week;


    SharedPreferences sharedPreferences;

    ArrayList<String> list_data=new ArrayList<>();


    ListView listView;
    public static ArrayList<record_class> save_data=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_test_list_page);
        toolbar=findViewById(R.id.Toolbar);
        toolbar.setTitle("Completed Test ");
        setSupportActionBar(toolbar);

        chapter=findViewById(R.id.chapter);
        week=findViewById(R.id.week);
        listView=findViewById(R.id.listview);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
        String id=sharedPreferences.getString("id","");

        chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(null);
                String query="?t1=record&t2="+id+"&t3=Chapter_wise_test";
                get_data_from_url obj=new get_data_from_url();
                obj.execute(getString(R.string.server)+query);
            }
        });

        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(null);
                String query="?t1=record&t2="+id+"&t3=Weekly_test";
                get_data_from_url obj=new get_data_from_url();
                obj.execute(getString(R.string.server)+query);
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(user_test_list_page.this,user_Progress_page.class);
                intent.putExtra("position",i);
                startActivity(intent);
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
                list_data.clear();
                save_data.clear();
                for(int i=0;i< jsonArray.length();i++)
                {
                    jsonObject=jsonArray.getJSONObject(i);
                    String Test_name=(String) jsonObject.get("test_name");
                    String Test_type=(String) jsonObject.get("Test_type");
                    String Total_marks=(String) jsonObject.get("Total_marks");
                    String Total_question=(String) jsonObject.get("Total_question");
                    String marks_obtain=(String) jsonObject.get("marks_obtain");
                    String u_id=(String) jsonObject.get("id");
                    String id=(String) jsonObject.get("user_id");
                    list_data.add(Test_name);
                    record_class record_data=new record_class(Test_name,Test_type,Total_marks,Total_question,marks_obtain,id,u_id);
                    save_data.add(record_data);

                    adapter myadpter=new adapter(getApplicationContext(),list_data);
                    listView.setAdapter(myadpter);
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

    class  adapter extends ArrayAdapter<String> {

        Context context;
        ArrayList<String> head11;

        ArrayList<String> head22;

        adapter(Context c,ArrayList<String> header1)
        {
            super(c,R.layout.row,R.id.test_name,header1);
            context=c;
            this.head11=header1;
        }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater= (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view=layoutInflater.inflate(R.layout.row,parent,false);
            TextView test_name=view.findViewById(R.id.test_name);
            TextView testtopic=view.findViewById(R.id.sub_topic);
            LinearLayout layout=view.findViewById(R.id.linear);
            test_name.setText(head11.get(position));
            layout.setBackgroundResource(R.drawable.listbox);
            testtopic.setText(save_data.get(position).getTest_name());
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