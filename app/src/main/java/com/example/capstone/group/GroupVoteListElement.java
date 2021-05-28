package com.example.capstone.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstone.R;

public class GroupVoteListElement extends LinearLayout {
    private TextView nameFrame, dateFrame;
    public GroupVoteListElement(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.group_vote_element, this, true);

        this.nameFrame = (TextView) findViewById(R.id.scheduleNameFrame);
        this.dateFrame = (TextView) findViewById(R.id.dateFrame);
    }
    public void setScheduleName(String scheduleName) {
        this.nameFrame.setText(scheduleName);
    }
    public void setScheduleDate(String scheduleDate) {
        this.dateFrame.setText(scheduleDate);
    }
}
