package com.example.screensaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.StatusBarManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.MediaController;
import android.widget.VideoView;

public class ScreenSaverActivity extends AppCompatActivity {
    VideoView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("___","CREATE SSA");
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.getWindow().setDecorFitsSystemWindows(false);
        setContentView(R.layout.activity_screen_saver);
        getSupportActionBar().hide();



        vv = (VideoView) findViewById(R.id.videoView);
        String videoPath = "android.resource://" + this.getPackageName() + "/" + R.raw.video;
        Uri uri = Uri.parse(videoPath);
        vv.setVideoURI(uri);
        vv.setMediaController(new MediaController(this));
        vv.requestFocus(0);

        MediaController ctrl = new MediaController(this);
        ctrl.setVisibility(View.GONE);
        vv.setMediaController(ctrl);

        vv.start(); // начинаем воспроизведение автоматически
        vv.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                vv.start();
            }
        });
    }

    @Override
    public void onUserInteraction() {
        super.onUserInteraction();
        Log.d("___", "USER DO SMTH");
        //TODO ругается на
        // E/WindowManager: android.view.WindowLeaked: Activity com.example.screensaver.ScreenSaverActivity has leaked window DecorView@bcd87d9[] that was originally added here
        // как это поправить не поняла и не нагуглила
        // дальнейшей работе не мешает
        finish();
        Intent intent = new Intent(this, MyService.class);
        intent.putExtra("status", "restart");
        startService(intent);

        Log.d("___", "CLOSE VIDEO");
    }


}