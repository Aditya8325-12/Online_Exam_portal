package com.example.g_exam_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class Login extends AppCompatActivity {


    EditText username,pass;
    TextView sign_up;
    Button login;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username=findViewById(R.id.username);
        pass=findViewById(R.id.pass);
        sign_up=findViewById(R.id.sign_up);
        login=findViewById(R.id.login);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);

        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Login.this,Registration.class));
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation())
                {
                    String user=username.getText().toString();
                    String password=pass.getText().toString();
                    String query="?t1=login&t2="+user+"&t3="+password;
                    get_data_from_url obj=new get_data_from_url();
                    obj.execute(getString(R.string.server)+query);
                }
            }
        });
    }


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

        else {
            return true;
        }
    }

    class  get_data_from_url extends AsyncTask<String,Void,String>
    {
        @Override
        protected String doInBackground(String... strings) {

            try {
                URL url=new URL(strings[0]);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
                return  bufferedReader.readLine();
            } catch (Exception e) {
                return    e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray=new JSONArray(s);
                JSONObject jsonObject=null;
                if(jsonArray.length()>=1)
                {
                        jsonObject=jsonArray.getJSONObject(0);
                        String user=(String) jsonObject.get("user_name");
                        String password=(String)jsonObject.get("pass");
                        String email_id=(String)jsonObject.get("email");
                        String college_id=(String)jsonObject.get("college");
                        String id=(String) jsonObject.get("id");
                        SharedPreferences.Editor edit=sharedPreferences.edit();
                        edit.putString("username",user);
                        edit.putString("password",password);
                        edit.putString("email",email_id);
                        edit.putString("college",college_id);
                        edit.putString("id",id);
                        edit.commit();
                        startActivity(new Intent(Login.this,Home.class));
                }
                else {
                    Toast.makeText(Login.this, "user not found ", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception ec)
            {
                ec.getMessage();
            }

        }
    }

}