package com.example.capstone.user;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainBaseActivity2 extends AppCompatActivity {
    Button groupAddBtn;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            AlertDialog.Builder builder=new AlertDialog.Builder(this);
            builder.setTitle("알람");
            builder.setMessage("그룹을 만들시겠습니까?");
            builder.setPositiveButton("수락",null);
            builder.setNegativeButton("거절",null);
            builder.setNeutralButton("차단",null);

            alertDialog = builder.create();
            alertDialog.show();
    }
}
