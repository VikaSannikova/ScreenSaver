package com.example.screensaver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class ScreenSaverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_saver);
        getSupportActionBar().hide();
    }
}