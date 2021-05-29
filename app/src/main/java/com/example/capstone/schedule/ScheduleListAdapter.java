package com.example.capstone.schedule;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.capstone.bean.GroupScheduleBean;

public class ScheduleListAdapter extends BaseAdapter {
    private Context context;
    private GroupScheduleBean[] listBeans;
    public ScheduleListAdapter(Context context, GroupScheduleBean[] listBeans) {
        this.context = context;
        this.listBeans = listBeans;
    }
    @Override
    public int getCount() {
        return listBeans.length;
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
            ScheduleListElement elementView;
            if(convertView == null) {
                elementView = new ScheduleListElement(this.context);
            } else {
                elementView = (ScheduleListElement) convertView;
            }
            GroupScheduleBean bean = listBeans[position];
            elementView.setData(bean.getName(), bean.getStart(), bean.getEnd(), bean.getDday());
            return elementView;
        } catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Intent getItemIntent(int position) {
        Intent intent = new Intent(this.context, ScheduleView.class);
        intent.putExtra("name", listBeans[position].getName());
        intent.putExtra("start", listBeans[position].getStart());
        intent.putExtra("end", listBeans[position].getEnd());
        intent.putExtra("memo", listBeans[position].getMemo());
        intent.putExtra("dday", listBeans[position].getDday());
        return intent;
    }
}
