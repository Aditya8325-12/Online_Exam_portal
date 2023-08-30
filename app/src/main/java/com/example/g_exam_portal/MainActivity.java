package com.example.g_exam_portal;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if(sharedPreferences.contains("username") && sharedPreferences.contains("password"))
                {
                    Intent intent=new Intent(MainActivity.this,Home.class);
                    startActivity(intent);
                }
                else {
                    Intent intent=new Intent(MainActivity.this,Login.class);
                    startActivity(intent);
                }
            }
        },1000);

    }
}