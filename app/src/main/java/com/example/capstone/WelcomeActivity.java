package com.example.capstone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.data.DataManager;
import com.example.capstone.user.SignIn;

public class WelcomeActivity extends AppCompatActivity {
    private RelativeLayout goLoginBtn;
    private FrameLayout btnWrapper;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnWrapper = (FrameLayout) findViewById(R.id.btnWrapper);
        goLoginBtn = (RelativeLayout) findViewById(R.id.welcomeLoginBtn);

        DataManager dataManager = new DataManager(getApplicationContext());
        dataManager.getReadableDatabase();
        dataManager.close();

        goLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);
        String id = pref.getString("id", null);

        if(id != null) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), MainBaseActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }, 1000);
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.main_slide_up);

                    btnWrapper.startAnimation(anim);
                    btnWrapper.setVisibility(View.VISIBLE);
                }
            }, 1500);
        }


    }
}
