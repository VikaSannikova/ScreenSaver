package com.example.screensaver;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button start_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        start_button = (Button) findViewById(R.id.start_service_button);
        start_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("___", "foreground click");
                Intent serviceIntent = new Intent(MainActivity.this, MyService.class);
                serviceIntent.putExtra("inputExtra", "Foreground Service started !!!");
                serviceIntent.putExtra("status", "start");
                ContextCompat.startForegroundService(MainActivity.this, serviceIntent);
                //finish();
            }
        });

    }
}