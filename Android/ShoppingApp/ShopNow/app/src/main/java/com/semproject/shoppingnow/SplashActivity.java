package com.semproject.shoppingnow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(() -> {
            Intent splashToHome = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(splashToHome);
            SplashActivity.this.finish();
        },3500);
    }
}