package com.example.capstone.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstone.R;

public class GroupApplierListElementForNormal extends LinearLayout {
    private TextView name;
    public GroupApplierListElementForNormal(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.group_applier_element_normal, this, true);
        this.name = (TextView) findViewById(R.id.name);
    }
    public void setName(String name) { this.name.setText(name);}
}
