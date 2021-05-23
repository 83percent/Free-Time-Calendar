package com.example.capstone.group;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.capstone.bean.GroupMemberBean;

public class GroupApplierListAdapter extends BaseAdapter {

    private Context context;
    private GroupMemberBean[] listBeans;
    private boolean isAdmin;

    public GroupApplierListAdapter(Context context, GroupMemberBean[] listBeans, boolean isAdmin) {
        this.context = context;
        this.listBeans = listBeans;
        this.isAdmin = isAdmin;
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
            if(isAdmin) {
                GroupApplierListElementForAdmin elementView;
                if(convertView == null) {
                    elementView = new GroupApplierListElementForAdmin(this.context);
                } else {
                    elementView = (GroupApplierListElementForAdmin) convertView;
                }
                elementView.setName(listBeans[position].getName());
                return elementView;
            } else {
                GroupApplierListElementForNormal elementView;
                if(convertView == null) {
                    elementView = new GroupApplierListElementForNormal(this.context);
                } else {
                    elementView = (GroupApplierListElementForNormal) convertView;
                }
                elementView.setName(listBeans[position].getName());
                return elementView;
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getApplierId(int position) {
        return listBeans[position].getId();
    }
}
