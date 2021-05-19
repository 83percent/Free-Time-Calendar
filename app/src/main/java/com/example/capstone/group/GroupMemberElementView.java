package com.example.capstone.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstone.R;

public class GroupMemberElementView extends LinearLayout {
    private TextView name;
    public GroupMemberElementView(Context context) {
        super(context);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.group_member_list, this, true);

        name = (TextView) findViewById(R.id.name);
    }
    public void setName(String name) { this.name.setText(name); }
}
