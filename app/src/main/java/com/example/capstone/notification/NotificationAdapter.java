package com.example.capstone.notification;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.capstone.bean.NotificationBean;
import com.example.capstone.group.GroupMainCalendar;
import com.example.capstone.group.VoteActivity;
import com.example.capstone.schedule.ScheduleView;

public class NotificationAdapter extends BaseAdapter {
    private Context context;
    private NotificationBean[] beans;
    public NotificationAdapter(Context context, NotificationBean[] beans) {
        this.context = context;
        this.beans = beans;
    }
    @Override
    public int getCount() {
        return beans.length;
    }

    @Override
    public Object getItem(int position) {
        return beans[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        switch(beans[position].getType()) {
            case "schedule" : {
                NotificationElementSchedule elementView = new NotificationElementSchedule(this.context);
                elementView.setData(beans[position].getMessage1(),beans[position].getMessage2());
                return elementView;
            }
            case "vote" : {
                NotificationElementVote elementView = new NotificationElementVote(this.context);
                elementView.setData(beans[position].getMessage1());
                return elementView;
            }
            case "group" : {
                NotificationElementGroup elementView = new NotificationElementGroup(this.context);
                elementView.setData(beans[position].getMessage1());
                return elementView;
            }
            default : {
                return null;
            }
        }
    }
    public Intent getItemIntent(int position) {
        Intent intent = null;
        switch (beans[position].getType()) {
            case "schedule" : {
                intent = new Intent(this.context, ScheduleView.class);
                // TODO : 해당 뷰에서 필요로하는 정보 가져와서 전달, 내부 DB 사용할거면 그러면 됨
                break;
            }
            case "vote" : {
                intent = new Intent(this.context, VoteActivity.class);
                break;
            }
            case "group" : {
                intent = new Intent(this.context, GroupMainCalendar.class);
                break;
            }
        }

        return intent;
    }
}
