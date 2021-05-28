package com.example.capstone.group;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.capstone.bean.GroupMemberBean;
import com.example.capstone.bean.GroupVoteBean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GroupVoteListAdapter extends BaseAdapter {

    private Context context;
    private GroupVoteBean[] listBeans;
    private String groupCode;

    public GroupVoteListAdapter(Context context, GroupVoteBean[] listBeans, String groupCode) {
        this.context = context;
        this.listBeans = listBeans;
        this.groupCode = groupCode;
    }

    @Override
    public int getCount() {
        if(listBeans != null) return this.listBeans.length;
        else return -1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        try {
            GroupVoteListElement elementView;
            if(convertView == null) {
                elementView = new GroupVoteListElement(this.context);
            } else {
                elementView = (GroupVoteListElement) convertView;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

            elementView.setScheduleName(listBeans[position].getName());
            elementView.setScheduleDate(listBeans[position].getStart() + " ~ " + listBeans[position].getEnd());
            return elementView;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Intent getItemIntent(int position) {
        Intent resultIntent = new Intent(context, VoteActivity.class);
        resultIntent.putExtra("voteCode", listBeans[position].get_id());
        resultIntent.putExtra("voteName", listBeans[position].getName());
        resultIntent.putExtra("agree", listBeans[position].getAgree());
        resultIntent.putExtra("start", listBeans[position].getStart());
        resultIntent.putExtra("end", listBeans[position].getEnd());
        resultIntent.putExtra("memo", listBeans[position].getMemo());
        return resultIntent;
    }
}
