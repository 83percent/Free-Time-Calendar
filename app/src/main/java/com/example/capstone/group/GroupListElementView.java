package com.example.capstone.group;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.capstone.R;
import com.example.capstone.bean.GroupListBean;

public class GroupListElementView extends LinearLayout {
    private TextView gName, gCount1, gCount2, gsCount;
    public GroupListElementView(Context context) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.main_group_list_element, this, true);

        gName = (TextView) findViewById(R.id.gname);
        gCount1 = (TextView) findViewById(R.id.gcount1);
        gCount2 = (TextView) findViewById(R.id.gcount2);
        gsCount = (TextView) findViewById(R.id.gscount);
    }
    public void setData(GroupListBean bean) {
        gName.setText(bean.getName());
        Log.d("ListView", "Member 수 : " + bean.getMemberCount());
        Log.d("ListView", "Member 들어갈 View : " + gCount1);

        gCount1.setText(String.valueOf(bean.getMemberCount()));
        gCount2.setText(String.valueOf(bean.getMemberCount()-1));
        gsCount.setText(String.valueOf(bean.getScheduleCount()));
        /*
        gCount1.setText(bean.getMemberCount());
        gCount2.setText(bean.getMemberCount()-1);
        gsCount.setText(bean.getScheduleCount());
         */
    }
}
