package com.example.serviceexamples;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    Intent intentService1;
    Intent intentService2;
    Intent intentService3;

    Service2Receiver receiver;

    TextView textData;

    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textData = findViewById(R.id.text_data);

        // Start service
        intentService1 = new Intent(this, MyService1.class);
        intentService2 = new Intent(this, MyService2.class);
        intentService3 = new Intent(this, MyService3.class);

        findViewById(R.id.btn_start_service_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentService1.putExtra("param1", "value1");
                intentService1.putExtra("param2", 22);
                startService(intentService1);
            }
        });

        findViewById(R.id.btn_stop_service_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intentService1);
            }
        });

        findViewById(R.id.btn_start_service_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(intentService2);
            }
        });

        findViewById(R.id.btn_stop_service_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intentService2);
            }
        });

        // mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.test);

        findViewById(R.id.btn_start_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startService(intentService3);
            }
        });

        findViewById(R.id.btn_stop_music).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stopService(intentService3);
            }
        });


        IntentFilter intentFilter = new IntentFilter("com.example.serviceexamples.MESSAGE");
        receiver = new Service2Receiver();
        registerReceiver(receiver, intentFilter);

        findViewById(R.id.btn_notify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                    NotificationChannel mChannel = new NotificationChannel("channel-01", "Channel Name", NotificationManager.IMPORTANCE_HIGH);
                    notificationManager.createNotificationChannel(mChannel);
                }

                Intent intent = new Intent(MainActivity.this, BlueActivity.class);
                PendingIntent pIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

                Notification.Builder builder = new Notification.Builder(MainActivity.this, "channel-01");
                builder.setContentText("Hello")
                        .setContentText("This is a test.")
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentIntent(pIntent);

                notificationManager.notify(1, builder.build());
            }
        });
    }

    @Override
    protected void onStop() {
        unregisterReceiver(receiver);
        super.onStop();
    }

    private class Service2Receiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String data = intent.getStringExtra("data");
            textData.append("\n" + data);
        }
    }
}