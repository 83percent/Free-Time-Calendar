package com.example.capstone.group;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;
import com.example.capstone.bean.VoteBean;
import com.example.capstone.connect.RetrofitConnection;

import java.util.Arrays;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoteActivity extends AppCompatActivity {
    // View
    private ImageButton goodBtn, badBtn;
    private RelativeLayout finishBtn;
    private TextView voteNameFrame, memoFrame, dateFrame;

    // Anim
    private Animation thumbStart, thumbEnd;

    // Field
    private String voteCode, memo, voteName, id, start, end;
    private String[] agree;
    private RetrofitConnection retrofitConnection;
    private int voteState = 0; // 1: good, 0: none, -1:bad
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_schedule_vote);
        // Memorize
        goodBtn = (ImageButton) findViewById(R.id.goodBtn);
        badBtn = (ImageButton) findViewById(R.id.badBtn);
        finishBtn = (RelativeLayout) findViewById(R.id.finishBtn);

        voteNameFrame = (TextView) findViewById(R.id.voteNameFrame);
        memoFrame = (TextView) findViewById(R.id.memoFrame);
        dateFrame = (TextView) findViewById(R.id.dateFrame);

        // Anim
        thumbStart = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.thumb_anim);

        SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);
        id = pref.getString("id", null);

        // Income data in intent
        Intent dataIntent = getIntent();
        if(dataIntent != null) {
            voteCode = dataIntent.getStringExtra("voteCode");
            voteName = dataIntent.getStringExtra("voteName");
            agree = dataIntent.getStringArrayExtra("agree");
            start = dataIntent.getStringExtra("start");
            end = dataIntent.getStringExtra("end");
            memo = dataIntent.getStringExtra("memo");

            if(voteName != null) voteNameFrame.setText(voteName);
            if(memo != null) memoFrame.setText(memo);
            if(start != null && end != null) dateFrame.setText(start + " ~ " + end);
            if(agree.length > 0) {
                if(Arrays.binarySearch(agree, id) > -1) {
                    goodBtn.setBackgroundResource(R.drawable.ic_baseline_thumb_up_24);
                    goodBtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.active));
                    voteState = 1;
                }
            }
        }

        goodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(voteState != 1) {
                    sendVote(id, true);
                    goodToggle(true);
                    voteState = 1;
                } else {
                    sendVote(id, false);
                    goodToggle(false);
                    voteState = 0;
                }
            }
        });

        badBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(voteState != -1) {
                    if(voteState == 1) sendVote(id, false);
                    badToggle(true);
                    voteState = -1;
                } else {
                    badToggle(false);
                    voteState = 0;
                }
            }
        });
        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    } // onCreate
    private void sendVote(String id, boolean isAgree) {
        if(retrofitConnection == null) retrofitConnection = RetrofitConnection.getInstance();
        Call<Boolean> call = retrofitConnection.server.sendVote(voteCode, new VoteBean(id, isAgree));
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {

            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {

            }
        });

    } // sendVote
    private void badToggle(boolean toggle) {
        if(toggle) {
            if(voteState == 1) {
                goodToggle(false);
            }
            badBtn.setBackgroundResource(R.drawable.ic_baseline_thumb_down_24);
            badBtn.startAnimation(thumbStart);
        } else {
            badBtn.setBackgroundResource(R.drawable.ic_outline_thumb_down_24);
        }
    }
    private void goodToggle(boolean toggle) {
        if(toggle) {
            if(voteState == -1) {
                badToggle(false);
            }
            goodBtn.setBackgroundResource(R.drawable.ic_baseline_thumb_up_24);
            goodBtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.active));
            goodBtn.startAnimation(thumbStart);
        } else {
            goodBtn.setBackgroundResource(R.drawable.ic_outline_thumb_up_24);
            goodBtn.setBackgroundTintList(getApplicationContext().getResources().getColorStateList(R.color.unActive));
        }
    }
}
