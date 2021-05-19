package com.example.capstone.group;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.capstone.bean.GroupMemberBean;

public class GroupMemberAdapter extends BaseAdapter {
    private Context context;
    private GroupMemberBean[] memberBeans;
    public GroupMemberAdapter(Context context, GroupMemberBean[] memberBeans) {
        this.context = context;
        this.memberBeans = memberBeans;
    }
    @Override
    public int getCount() {
        if(memberBeans != null) return this.memberBeans.length;
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
        GroupMemberElementView elementView;
        if(convertView == null) {
            elementView = new GroupMemberElementView(this.context);
        } else {
            elementView = (GroupMemberElementView) convertView;
        }
        elementView.setName(memberBeans[position].getName());

        return elementView;
    }
}
