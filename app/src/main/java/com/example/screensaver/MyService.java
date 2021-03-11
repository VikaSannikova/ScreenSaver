package com.example.screensaver;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
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

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String input = intent.getStringExtra("inputExtra");
        createNotificationChannel();
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

        Intent intent1 = new Intent(this, ScreenSaverActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent1 = PendingIntent.getActivity(this, 0,intent1,0);


        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID);
        builder.setContentTitle("Foreground Service")
                .setContentText(input)
                .setSmallIcon(R.drawable.cat)
                .setContentIntent(pendingIntent1);
        notificationManager.notify(NOTIFY_ID, builder.build());
        startForeground(NOTIFY_ID, builder.build());

        SystemClock.sleep(5000);
        Log.d("___", "NEW INTENT");

        Intent myintent = new Intent(this, MyReceiver.class);
        //PendingIntent pendingIntentforButton = PendingIntent.getBroadcast(this, 0, myintent, 0);
        sendBroadcast(myintent);


        //this.startActivity(intent1);
        //notificationManager.notify(NOTIFY_ID, builder.build());
        Log.d("___", "GO VIDEO");


        return super.onStartCommand(intent, flags, startId);
    }
}