package com.example.capstone.notification;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstone.R;

public class NotificationElementVote extends LinearLayout {
    private TextView voteNameFrame;
    public NotificationElementVote(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.notification_element_vote, this, true);

        this.voteNameFrame = (TextView) findViewById(R.id.message1);
    }
    public void setData(String voteName) {
        this.voteNameFrame.setText(voteName);
    }
}
