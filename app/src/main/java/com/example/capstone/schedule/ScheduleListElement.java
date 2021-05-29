package com.example.capstone.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstone.R;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ScheduleListElement extends LinearLayout {
    private TextView dayFrame, dateFrame, titleFrame;
    public ScheduleListElement(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.schedule_element, this, true);

        this.titleFrame = (TextView) findViewById(R.id.titleFrame);
        this.dateFrame = (TextView) findViewById(R.id.dateFrame);
        this.dayFrame = (TextView) findViewById(R.id.dayFrame);
    }
    public void setData(String title, String start, String end, int dday) {
        this.titleFrame.setText(title);
        SimpleDateFormat trans = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        //this.dateFrame.setText(trans.format(new Date(start)) + " ~ " + trans.format(new Date(end)));
        this.dateFrame.setText(start + " ~ " + end);
        if(dday > 0) {
            this.dayFrame.setText("+" + dday);
        } else if (dday == 0){
            this.dayFrame.setText("-day");
        } else {
            this.dayFrame.setText(String.valueOf(dday));
        }
    }
}
