package com.example.capstone;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity2 extends AppCompatActivity {
    public NotificationCompat.Builder getChannel1Main;
    NotificationManager manager;

    private static String CHANNEL_ID="channel1";
    private static String CHANNEL_NAME="Channel1";

    public MainActivity2(MainActivity3 mainActivity3) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_main);

        Button button=findViewById(R.id.btn_channel1);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNoti1();
            }
        });
    }

    public void showNoti1() {
        manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=null;
        if (Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {
            if (manager.getNotificationChannel(CHANNEL_ID)==null) {
                manager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                ));

                builder=new NotificationCompat.Builder(this, CHANNEL_ID);
            }
        }
        builder.setContentTitle("알림");
        builder.setContentText("알림메세지도착");
        Notification noti=builder.build();

        manager.notify(1, noti);
    }
}
