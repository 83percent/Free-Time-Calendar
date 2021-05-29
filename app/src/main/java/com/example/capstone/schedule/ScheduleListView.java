package com.example.capstone.schedule;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;
import com.example.capstone.bean.GroupVoteBean;
import com.example.capstone.connect.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
    Group 의 경우
    groupCode 와 groupName 을 전달 받아야함.
 */

public class ScheduleListView extends AppCompatActivity {
    // View
    private ListView scheduleListView;
    private TextView titleFrame;

    // Field
    RetrofitConnection retrofitConnection;
    private String groupCode;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_list);

        // memorize
        this.scheduleListView = (ListView) findViewById(R.id.scheduleListView);
        this.titleFrame = (TextView) findViewById(R.id.titleFrame);

        // Income data from intent
        Intent dataIntent = getIntent();
        if(dataIntent != null) {
            this.groupCode = dataIntent.getStringExtra("groupCode");
            if(groupCode != null) {
                // GroupSchedule 보여주기
                titleFrame.setText(dataIntent.getStringExtra("groupName"));
                getGroupSchedule(this.groupCode);
            }
        } else {
            SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);
            String id = pref.getString("id", null);
            if(id != null) {

            } else {
                Toast.makeText(this, "정보륿 불러올 수 없습니다", Toast.LENGTH_SHORT).show();
            }
        }
    } // onCreate

    private void getGroupSchedule(String groupCode) {
        if(retrofitConnection == null) retrofitConnection = RetrofitConnection.getInstance();
        Call<GroupVoteBean[]> call = retrofitConnection.server.getGroupSchedule(groupCode);
        call.enqueue(new Callback<GroupVoteBean[]>() {
            @Override
            public void onResponse(Call<GroupVoteBean[]> call, Response<GroupVoteBean[]> response) {
                
            }

            @Override
            public void onFailure(Call<GroupVoteBean[]> call, Throwable t) {

            }
        });
    }
    private void getUserSchedule(String id) {

    }
}
