package com.example.capstone.schedule;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;

public class ScheduleView extends AppCompatActivity {
    // View
    private RelativeLayout wrapper;
    private TextView titleFrame, dayFrame, dateFrame, memoFrame;

    // Field
    private String title, start, end, memo;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_view);

        this.wrapper = (RelativeLayout) findViewById(R.id.wrapper);
        this.titleFrame = (TextView) findViewById(R.id.titleFrame);
        this.dayFrame = (TextView) findViewById(R.id.dayFrame);
        this.dateFrame = (TextView) findViewById(R.id.dateFrame);
        this.memoFrame = (TextView) findViewById(R.id.memoFrame);

        Intent dataIntent = getIntent();
        if(dataIntent != null) {
            this.title = dataIntent.getStringExtra("name");
            this.start = dataIntent.getStringExtra("start");
            this.end = dataIntent.getStringExtra("end");
            this.memo = dataIntent.getStringExtra("memo");

            if(this.title != null) this.titleFrame.setText(this.title);
            if(this.memo != null) this.memoFrame.setText(this.memo);
            if(this.start != null && this.end != null) this.dateFrame.setText((this.start + " ~ " + this.end));

        }

        this.wrapper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
