package com.example.serviceexamples;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService1 extends Service {
    public MyService1() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("TAG", "Service onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("TAG", "Service onStartCommand");
        String param1 = intent.getStringExtra("param1");
        int param2 = intent.getIntExtra("param2", 0);

        Log.v("TAG", "param1: " + param1);
        Log.v("TAG", "param2: " + param2);

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Log.v("TAG", "Service onDestroy");
        super.onDestroy();
    }
}