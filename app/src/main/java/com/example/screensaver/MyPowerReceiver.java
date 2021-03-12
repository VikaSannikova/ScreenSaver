package com.example.screensaver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.util.Log;

public class MyPowerReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("___", "onReceive " + intent.getAction());

        Intent intent1 = new Intent(context, ScreenSaverActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5*1000);
                    Log.d("___","im in thread");
                    context.startActivity(intent1);
                    Log.d("___","draw activity");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.run();
        //context.startService(new Intent(context, MyService.class));
    }
}