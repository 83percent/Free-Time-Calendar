package com.example.capstone.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstone.R;

public class NotificationElementSchedule extends LinearLayout {
    private TextView groupNameFrame, scheduleNameFrame;
    public NotificationElementSchedule(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.notification_element_schedule, this, true);

        this.groupNameFrame = (TextView) findViewById(R.id.message1);
        this.scheduleNameFrame = (TextView) findViewById(R.id.message2);
    }
    public void setData(String groupName, String scheduleName) {
        this.groupNameFrame.setText(groupName);
        this.scheduleNameFrame.setText(scheduleName);
    }
}
