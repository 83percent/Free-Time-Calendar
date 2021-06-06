package com.example.capstone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

public class MainActivity3 extends AppCompatActivity {

    private EditText eTitle;
    private EditText eMessage;
    private Button channel1Btn;
    private MainActivity2 mMainactivity2;

    @Override
    protected void onCreate(Bundle savedlnstanceState) {
        super.onCreate(savedlnstanceState);
        setContentView(R.layout.activity3_main);

        eTitle=findViewById(R.id.edit_title);
        eMessage=findViewById(R.id.edit_message);
        channel1Btn=findViewById(R.id.btn_channel1);

        mMainactivity2=new MainActivity2(this);

        channel1Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title=eTitle.getText().toString();
                String message=eMessage.getTag().toString();
                        sendOnChannel1(title, message);
            }
        });
    }
    public void sendOnChannel1(String title, String message){
        NotificationCompat.Builder nb=mMainactivity2.getChannel1Main;
    }
}
