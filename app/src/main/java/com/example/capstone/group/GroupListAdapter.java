package com.example.capstone.group;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.capstone.bean.GroupListBean;

public class GroupListAdapter extends BaseAdapter {
    private Context context;
    private GroupListBean[] listBeans;
    public GroupListAdapter(Context context, GroupListBean[] listBeans) {
        this.context = context;
        this.listBeans = listBeans;
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
            GroupListElementView elementView;
            if(convertView == null) {
                elementView = new GroupListElementView(this.context);
            } else {
                elementView = (GroupListElementView) convertView;
            }
            elementView.setData(listBeans[position]);
            return elementView;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public Intent getItemIntent(final int position) {
        Intent resultIntent = new Intent(context, GroupMainCalendar.class);
        resultIntent.putExtra("groupID", listBeans[position].getId());
        resultIntent.putExtra("groupName", listBeans[position].getName());
        resultIntent.putExtra("admin", listBeans[position].getAdmin());

        return resultIntent;
    }
}
