package com.example.capstone.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;
import com.example.capstone.bean.FreeBean;
import com.example.capstone.bean.ScheduleBean;
import com.example.capstone.bean.TimeBean;
import com.example.capstone.data.TimeData;
import com.example.capstone.lib.Date;

public class NewBase extends AppCompatActivity {
    // View
    private ImageButton back;
    private RelativeLayout option_free;
    private RelativeLayout option_schedule;
    private RelativeLayout addBtn;

    // Fragment
    private NewSchedule scheduleFragment;
    private NewFree freeFragment;

    //Field
    private String nowView = "Free";
    private RelativeLayout nowViewLayout;
    private int year;
    private int month;
    private String day;
    private String id = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);
        id = pref.getString("id", null);

        setContentView(R.layout.my_new);
        Date date = new Date();
        Intent intent = getIntent();
        year = intent.getIntExtra("year", date.getYear());
        month = intent.getIntExtra("month", date.getMonth());
        day = intent.getStringExtra("day");
        if(day == null) day = String.valueOf(date.getDay());

        // View
        back = (ImageButton) findViewById(R.id.myNewBack);
        addBtn = (RelativeLayout) findViewById(R.id.addBtn);
        option_free = (RelativeLayout) findViewById(R.id.option_free);
        option_schedule = (RelativeLayout) findViewById(R.id.option_schedule);
        nowViewLayout = option_free;

        // Date Default Setting
        final Bundle bundle = new Bundle();
        bundle.putString("year", String.valueOf(year));
        bundle.putString("month", String.valueOf(month));
        bundle.putString("day", day);

        // Fragment
        scheduleFragment = new NewSchedule();
        freeFragment = new NewFree();

        scheduleFragment.setArguments(bundle);
        freeFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction().replace(R.id.newFragmentBase, freeFragment).commit();




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        option_free.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nowView != "Free") {
                    getSupportFragmentManager().beginTransaction().replace(R.id.newFragmentBase, freeFragment).commit();
                    nowView = "Free";

                    // 비활성화
                    option_schedule.setBackgroundResource(R.drawable.my_new_round);
                    ((TextView) option_schedule.getChildAt(0)).setTextColor(Color.parseColor("#888888"));

                    // 활성화
                    option_free.setBackgroundResource(R.drawable.my_new_round_active);
                    ((TextView) option_free.getChildAt(0)).setTextColor(Color.parseColor("#ffffff"));
                }
            }
        });
        option_schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(nowView != "Schedule") {
                    getSupportFragmentManager().beginTransaction().replace(R.id.newFragmentBase, scheduleFragment).commit();
                    nowView = "Schedule";

                    // 비활성화
                    option_free.setBackgroundResource(R.drawable.my_new_round);
                    ((TextView) option_free.getChildAt(0)).setTextColor(Color.parseColor("#888888"));

                    // 활성화
                    option_schedule.setBackgroundResource(R.drawable.my_new_round_active);
                    ((TextView) option_schedule.getChildAt(0)).setTextColor(Color.parseColor("#ffffff"));
                }
            }
        });
    }
    protected boolean saveNewFree(TimeBean bean) {
        TimeData timeData = new TimeData(id, getApplicationContext());
        timeData.set(bean);
        if(bean != null) {
            final int month = bean.getsMonth();
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent();
                    intent.putExtra("month", month);
                    setResult(RESULT_OK, intent);
                    finish();
                }
            }, 500);
        }
        return true;
    }
    protected boolean saveNewSchedule(ScheduleBean bean) {
        return true;
    }
}
