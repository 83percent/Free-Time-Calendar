package com.example.capstone.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstone.R;

public class GroupApplierListElementForAdmin extends LinearLayout {
    private TextView name;
    public GroupApplierListElementForAdmin(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.group_applier_element_admin, this, true);

        this.name = (TextView) findViewById(R.id.name);
    }
    public void setName(String name) { this.name.setText(name);}
}
