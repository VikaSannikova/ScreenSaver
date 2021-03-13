package com.example.screensaver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.PowerManager;
import android.util.Log;

import static android.content.Context.SENSOR_SERVICE;

public class MyPowerReceiver extends BroadcastReceiver implements SensorEventListener{
    Thread thread;
    Context mContext;
    float xy_prev = 0, xz_prev = 0, zy_prev = 0;

    @Override
    public void onReceive(Context context, Intent intent){
        Log.d("___", "onReceive " + intent.getAction());
        mContext = context.getApplicationContext();

        SensorManager sensorManager;
        Sensor defaultAccelerometer;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
            defaultAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
            sensorManager.registerListener(this, defaultAccelerometer, SensorManager.SENSOR_DELAY_NORMAL);
        }
        Intent intent1 = new Intent(context, ScreenSaverActivity.class);
        intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent1, 0);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                if(!thread.isInterrupted()){
                    try {
                        Thread.sleep(5*1000);
                        Log.d("___","im in thread");

//                        mContext.startActivity(intent1);
                        try {
                            pendingIntent.send();
                        } catch (PendingIntent.CanceledException e) {
                            e.printStackTrace();
                        }

                        //context.startActivity(intent1);
                        Log.d("___","draw activity");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.run();
        //context.startService(new Intent(context, MyService.class));
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float xy_angle = event.values[0]; //Плоскость XY
        float xz_angle = event.values[1]; //Плоскость XZ
        float zy_angle = event.values[2]; //Плоскость ZY
        if(xy_prev != xy_angle || xz_prev != xy_angle || zy_prev !=zy_angle) {
            Log.d("___", xy_angle  + " " + xz_angle + " " + zy_angle);
            xy_prev = xy_angle;
            xz_prev = xz_angle;
            zy_prev = zy_angle;
            thread.interrupt();
            Intent intent = new Intent(mContext, MyService.class);
            intent.putExtra("restart", "true");
            mContext.startService(intent);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
    }
}