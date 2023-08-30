package com.example.g_exam_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Registration extends AppCompatActivity {

    EditText username,pass,email,college;
    Button sign;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        username=findViewById(R.id.username);
        pass=findViewById(R.id.pass);
        email=findViewById(R.id.email);
        college=findViewById(R.id.college_name);
        sign=findViewById(R.id.signup);


        sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation())
                {
                    String user_name=username.getText().toString();
                    String password=pass.getText().toString();
                    String e_mail=email.getText().toString();
                    String Collage_id=college.getText().toString();
                    String query="?t1=signup&t2="+user_name+"&t3="+password+"&t4="+e_mail+"&t5="+Collage_id;
                    send_data_to_url obj=new send_data_to_url();
                    obj.execute(getString(R.string.server)+query);
                }
            }
        });
    }

//    check string empty or not
    public  boolean validation()
    {
       if(username.getText().toString().equals(""))
       {
           username.setError("Please enter value");
            return false;
       }
        if(pass.getText().toString().equals(""))
        {
            pass.setError("Please enter value");
            return false;
        }
        if(email.getText().toString().equals(""))
        {
            email.setError("Please enter value");
            return false;
        }
        if(college.getText().toString().equals(""))
        {
            college.setError("Please enter value");
            return false;
        }
        else {
            return true;
        }
    }


//    send data to the database
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
            Toast.makeText(Registration.this, ""+s, Toast.LENGTH_SHORT).show();
            username.setText("");
            pass.setText("");
            college.setText("");
            email.setText("");
            Intent intent=new Intent(Registration.this,Login.class);
            startActivity(intent);
            Toast.makeText(Registration.this, "Register successfully completed", Toast.LENGTH_SHORT).show();
        }
    }


}