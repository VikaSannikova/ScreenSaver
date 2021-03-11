package com.example.screensaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.StatusBarManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

public class ScreenSaverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("___","CREATE SSA");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_screen_saver);
        getSupportActionBar().hide();

    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.d("___", "USER DO SMTH");
        finish();
        Log.d("___", "CLOSE VIDEO");

    }

//    @Override
//    public void onConfigurationChanged(@NonNull Configuration newConfig) {
//        super.onConfigurationChanged(newConfig);
//        Log.d("___", "USER ROTATE");
//        finish();
//        Log.d("___", "CLOSE VIDEO");
//    }
}