package com.example.serviceexamples;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService2 extends Service {
    public MyService2() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    boolean isRunning = false;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 30; i++) {
                    try {
                        if (!isRunning)
                            break;

                        Thread.sleep(1000);

                        // Gui thong diep sang activity
                        Intent intentMsg = new Intent("com.example.serviceexamples.MESSAGE");
                        intentMsg.putExtra("data", "data-" + i);
                        sendBroadcast(intentMsg);

                        Log.v("TAG", "data-" + i);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }

                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel("channel-01", "Channel Name", NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(mChannel);
                }

                Intent intent = new Intent(getApplicationContext(), BlueActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);

                Notification.Builder builder = new Notification.Builder(getApplicationContext(), "channel-01");
                builder.setContentText("Hello")
                        .setContentText("This is a test.")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentIntent(pIntent);

                notificationManager.notify(1, builder.build());
            }
        });

        isRunning = true;
        thread.start();

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        isRunning = false;
        super.onDestroy();
    }
}