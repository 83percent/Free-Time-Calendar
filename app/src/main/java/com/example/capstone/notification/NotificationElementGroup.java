package com.example.capstone.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstone.R;

public class NotificationElementGroup extends LinearLayout {
    private TextView groupNameFrame;
    public NotificationElementGroup(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.notification_element_group, this, true);

        this.groupNameFrame = (TextView) findViewById(R.id.message1);
    }
    public void setData(String groupName) {
        this.groupNameFrame.setText(groupName);
    }
}
