package com.example.capstone;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.capstone.main.Group;
import com.example.capstone.main.My;
import com.example.capstone.main.Notification;
import com.example.capstone.main.Setting;

public class MainBaseActivity extends AppCompatActivity {

    // Footer Btn
    private RelativeLayout myBtn, groupBtn, notificationBtn, settingBtn;
    private TextView notificationCount;

    // Fragment
    private My myFragment;
    private Group groupFragment;
    private Notification notificationFragment;
    private Setting settingFragment;

    // Field
    private String viewFrame = null;
    private boolean isOptionOpen = false;
    private long backKeyPressedTime = 0;
    private RelativeLayout onMenu = null;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_base);

        // View
        // Footer Btn
        myBtn = (RelativeLayout) findViewById(R.id.myBtn);
        groupBtn = (RelativeLayout) findViewById(R.id.groupBtn);
        notificationBtn = (RelativeLayout) findViewById(R.id.notificationBtn);
        settingBtn = (RelativeLayout) findViewById(R.id.settingBtn);
        myBtn.getChildAt(0).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.active)));
        notificationCount = (TextView) findViewById(R.id.count);

        onMenu = myBtn;

        // Fragment
        myFragment = new My();
        groupFragment = new Group();
        notificationFragment = new Notification();
        settingFragment = new Setting();


        // Come to Data
        final SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);
        int newCount = pref.getInt("newCount", 0);
        if(newCount != 0) {
            notificationCount.setVisibility(View.VISIBLE);
            if(newCount < 10) notificationCount.setText(String.valueOf(newCount));
            else notificationCount.setText("9+");
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, myFragment).commit();
        viewFrame = "my";

        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewFrame != "my") {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, myFragment).commit();
                    menuChange(myBtn);
                    viewFrame = "my";
                }
            }
        });
        groupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewFrame != "group") {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, groupFragment).commit();
                    menuChange(groupBtn);
                    viewFrame = "group";
                }
            }
        });
        notificationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewFrame != "notification") {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, notificationFragment).commit();
                    menuChange(notificationBtn);
                    viewFrame = "notification";
                    if(notificationCount.getVisibility() == View.VISIBLE) {
                        notificationCount.setVisibility(View.GONE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putInt("newCount", 0);
                        editor.commit();
                    }
                }
            }
        });
        settingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(viewFrame != "setting") {
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentFrame, settingFragment).commit();
                    menuChange(settingBtn);
                    viewFrame = "setting";
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(isOptionOpen) {
            groupFragment.toggleAddGroupOption(false);
        } else {
            if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
                backKeyPressedTime = System.currentTimeMillis();
                Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2500) { finish(); }
        }
    }
    private void menuChange(RelativeLayout menu) {
        if(onMenu != null) {
            onMenu.getChildAt(0).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.unActive)));
            onMenu = menu;
            onMenu.getChildAt(0).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.active)));
        }
    }

    public boolean signOut() {
        SharedPreferences pref = getSharedPreferences("FreeTime" , MODE_PRIVATE);
        String id = pref.getString("id", null);
        if(id != null) {
            SharedPreferences.Editor editor = pref.edit();
            editor.remove("id");
            editor.commit();
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

            return true;
        } else return false;
    }
    public String getID() {
        SharedPreferences pref = getSharedPreferences("FreeTime" , MODE_PRIVATE);
        String id = pref.getString("id", null);
        return id;
    }
    public Context getContext() {
        return getApplicationContext();
    }

}


