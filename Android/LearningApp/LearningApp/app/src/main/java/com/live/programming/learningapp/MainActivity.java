package com.live.programming.learningapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.live.programming.learningapp.common.LocalSession;

public class MainActivity extends AppCompatActivity {

    LocalSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        session = new LocalSession(MainActivity.this);

        new Handler().postDelayed(()->{
            if(session.getLoginStatus()){
                startActivity(new Intent(MainActivity.this, DrawerHomeActivity.class));
            }else{
            Intent navIntent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(navIntent);
            }
            MainActivity.this.finish(); //To destroy the activity
        },3500);
    }
}