package com.example.g_exam_portal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity  {


    DrawerLayout drawerLayout;

    NavigationView navigationView;

    ActionBarDrawerToggle actionBarDrawerToggle;

    Toolbar toolbar;

    LinearLayout chapter,week,progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        drawerLayout=findViewById(R.id.darwar);
        navigationView=findViewById(R.id.nav_view);
        View header=navigationView.getHeaderView(0);
        toolbar=findViewById(R.id.Toolbar);

        chapter=findViewById(R.id.chapter);
        week=findViewById(R.id.week);
        progress=findViewById(R.id.progress);


        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);
        actionBarDrawerToggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if(menuItem.getItemId()==R.id.home1)
                {
                    startActivity(new Intent(Home.this,Home.class));
                }
                if (menuItem.getItemId()==R.id.logout1)
                {
                    SharedPreferences sharedPreferences;
                    sharedPreferences=getSharedPreferences("Login",MODE_PRIVATE);
                    SharedPreferences.Editor editor=sharedPreferences.edit();
                    editor.clear();
                    editor.commit();
                    Intent intent=new Intent(Home.this,Login.class);
                    startActivity(intent);
                }
                return true;
            }
        });


        chapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Test_list_page.class);
                intent.putExtra("test_name","Chapter_wise_test");
                startActivity(intent);
            }
        });
        week.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,Test_list_page.class);
                intent.putExtra("test_name","Weekly_test");
                startActivity(intent);
            }
        });
        progress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Home.this,user_test_list_page.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle("Quit")
                .setMessage("Do you want Quit")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finishAffinity();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }
}