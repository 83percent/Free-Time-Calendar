package com.example.capstone;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.capstone.group.AddNewGroup;
import com.example.capstone.group.InputInvitation;
import com.example.capstone.main.Group;
import com.example.capstone.main.My;
import com.example.capstone.main.Notification;
import com.example.capstone.main.Setting;

public class MainBaseActivity extends AppCompatActivity {
    // View
    private LinearLayout groupAddOptionWrapper;
    private LinearLayout groupAddOptionCloser;
    private RelativeLayout invitationBtn;
    private RelativeLayout newGroupBtn;

    // Footer Btn
    private RelativeLayout myBtn;
    private RelativeLayout groupBtn;
    private RelativeLayout notificationBtn;
    private RelativeLayout settingBtn;

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
        //fragmentFrame = (FrameLayout) findViewById(R.id.fragmentFrame);
        groupAddOptionWrapper = (LinearLayout) findViewById(R.id.addGroupWrapper);
        groupAddOptionCloser = (LinearLayout) findViewById(R.id.addGroupWrapperCloser);
        invitationBtn = (RelativeLayout) findViewById(R.id.invitationCodeBtn);
        newGroupBtn = (RelativeLayout) findViewById(R.id.newGroupBtn);

        // Footer Btn
        myBtn = (RelativeLayout) findViewById(R.id.myBtn);
        groupBtn = (RelativeLayout) findViewById(R.id.groupBtn);
        notificationBtn = (RelativeLayout) findViewById(R.id.notificationBtn);
        settingBtn = (RelativeLayout) findViewById(R.id.settingBtn);
        myBtn.getChildAt(0).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.active)));
        onMenu = myBtn;

        // Fragment
        myFragment = new My();
        groupFragment = new Group();
        notificationFragment = new Notification();
        settingFragment = new Setting();

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

        // Group Add Option
        groupAddOptionCloser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAddGroupOption(false);
            }
        });
        invitationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAddGroupOption(false);
                Intent intent = new Intent(getApplicationContext(), InputInvitation.class);
                startActivity(intent);
            }
        });
        newGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAddGroupOption(false);
                Intent intent = new Intent(getApplicationContext(), AddNewGroup.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public void onBackPressed() {
        if(isOptionOpen) {
            toggleAddGroupOption(false);
        } else {
            if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
                backKeyPressedTime = System.currentTimeMillis();
                Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();
                return;
            }
            if (System.currentTimeMillis() <= backKeyPressedTime + 2500) { finish(); }
        }
    }
    public void toggleAddGroupOption(boolean toggle) {
        if(toggle) {
            if(groupAddOptionWrapper.getVisibility() == View.GONE) groupAddOptionWrapper.setVisibility(View.VISIBLE);
        } else {
            if(groupAddOptionWrapper.getVisibility() == View.VISIBLE) groupAddOptionWrapper.setVisibility(View.GONE);
        }
        isOptionOpen = toggle;
    }
    private void menuChange(RelativeLayout menu) {
        if(onMenu != null) {
            onMenu.getChildAt(0).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.unActive)));
            onMenu = menu;
            onMenu.getChildAt(0).setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getApplicationContext(), R.color.active)));
        }
    }
}


