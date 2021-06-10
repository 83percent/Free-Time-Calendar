package com.example.capstone.main;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone.MainBaseActivity;
import com.example.capstone.R;
import com.example.capstone.bean.NotificationBean;
import com.example.capstone.data.NotificationData;
import com.example.capstone.notification.NotificationAdapter;
import com.example.capstone.notification.NotificationElementGroup;
import com.example.capstone.notification.NotificationElementSchedule;
import com.example.capstone.notification.NotificationElementVote;

public class Notification extends Fragment {
    // View
    private ListView listView;
    private TextView removeBtn;
    private LinearLayout listLinear;
    // Field
    private MainBaseActivity activity;
    private String id;
    private int notificationCount = 0;
    private NotificationAdapter adapter;
    private NotificationData notificationData;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainBaseActivity) getActivity();
        id = activity.getID();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.main_notification, container, false);
        this.listView = (ListView) rootView.findViewById(R.id.listView);
        this.removeBtn = (TextView) rootView.findViewById(R.id.removeBtn);
        //this.listLinear = (LinearLayout) rootView.findViewById(R.id.listLinear);

        notificationData = NotificationData.getInstance(getActivity().getApplicationContext());
        if(id == null) id = activity.getID();
        if(id != null) {
            NotificationBean[] beans = notificationData.get(id);
            if(beans != null && beans.length > 0) {

                this.notificationCount = beans.length;

                adapter = new NotificationAdapter(getActivity().getApplicationContext(), beans);
                listView.setAdapter(adapter);

                /*
                for(int i=0; i<notificationCount; ++i) {

                    switch(beans[i].getType()) {
                        case "group" : {
                            NotificationElementGroup elementView = new NotificationElementGroup(getActivity().getApplicationContext());
                            listLinear.addView(elementView);
                            break;
                        }
                        case "schedule" : {
                            NotificationElementSchedule elementView = new NotificationElementSchedule(getActivity().getApplicationContext());
                            listLinear.addView(elementView);
                            break;
                        }
                        case "vote" : {
                            NotificationElementVote elementView = new NotificationElementVote(getActivity().getApplicationContext());
                            listLinear.addView(elementView);
                            break;
                        }
                        default : {return null;}
                    }
                }
                 */
            }
        }


        this.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(notificationCount == 0) {
                    Toast.makeText(activity, "삭제할 정보가 없어요", Toast.LENGTH_SHORT).show();
                } else {
                    /*
                    TODO : Notification Data 삭제 코드
                     */
                    notificationData.delete(id);
                    listView.removeAllViewsInLayout();
                }
            }
        });


        return rootView;
    }
}
