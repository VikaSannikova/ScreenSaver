package com.example.screensaver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyService extends Service {
    private String CHANNEL_ID = "channel";
    private int NOTIFY_ID = 111;
    public Thread thread_for_job;
    MyPowerReceiver myPowerReceiver = new MyPowerReceiver();

    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }
    }

    private void  registerMyPowerReceiver(){
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED);
        intentFilter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(myPowerReceiver, intentFilter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("___", "START SERVICE");
        String restart = intent.getStringExtra("status");
        createNotificationChannel();
        registerMyPowerReceiver();
        if(intent.getStringExtra("restart")!= null) {
            if (intent.getStringExtra("restart").equals("true")) {
                Intent myintent = new Intent(this, MyPowerReceiver.class);
                PendingIntent pendingIntentforButton = PendingIntent.getBroadcast(this, 0, myintent, 0);
                try {
                    pendingIntentforButton.send();
                } catch (PendingIntent.CanceledException e) {
                    e.printStackTrace();
                }
            }
        } else {
            Intent intent1 = new Intent(this, ScreenSaverActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent1 = PendingIntent.getActivity(this, 0,intent1,0);


            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
            builder.setContentTitle("Foreground Service")
                    .setContentText("GO")
                    .setSmallIcon(R.drawable.cat)
                    .setContentIntent(pendingIntent1);
            notificationManager.notify(NOTIFY_ID, builder.build());
            startForeground(NOTIFY_ID, builder.build());


            Log.d("___", "NEW INTENT");

            return START_NOT_STICKY;
        }
        return START_NOT_STICKY;
    }
}