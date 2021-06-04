package com.example.capstone.user;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.example.capstone.R;

public class MainActivity2 extends AppCompatActivity {
    public NotificationCompat.Builder getChannel1Main;
    NotificationManager manager;

    private static String CHANNEL_ID="channel1";
    private static String CHANNEL_NAME="channel1";

    public MainActivity2(MainActivity3 activity3)  {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3_main);

        Button button=findViewById(R.id.btn_channel1);
        button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                showNoti1();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public void showNoti1(){
        manager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder builder=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.0) {
            if (manager.getNotificationChannel(CHANNEL_ID) == null){
                manager.createNotificationChannel(new NotificationChannel(
                        CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT
                ));
                builder=new NotificationCompat.Builder(this);
            }
        }

        builder.setContentTitle("알림");
        builder.setContentText("알림메세지도착");
        Notification noti=builder.build();

        manager.notify(1, noti);
    }
}
