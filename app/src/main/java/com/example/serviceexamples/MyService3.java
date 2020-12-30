package com.example.serviceexamples;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

public class MyService3 extends Service {
    public MyService3() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    MediaPlayer player;

    @Override
    public void onCreate() {
        super.onCreate();

        player = MediaPlayer.create(getApplicationContext(), R.raw.test);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        player.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        player.stop();
        player.release();
        player = null;

        super.onDestroy();
    }
}