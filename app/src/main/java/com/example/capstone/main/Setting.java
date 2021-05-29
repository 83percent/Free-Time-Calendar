package com.example.capstone.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone.MainBaseActivity;
import com.example.capstone.R;

public class Setting extends Fragment {
    private LinearLayout signOutBtn;
    private MainBaseActivity activity;
    private TextView accountNameFrame, accountIDFrame;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.main_setting, container, false);
        this.signOutBtn = (LinearLayout) rootView.findViewById(R.id.signOut);
        this.accountNameFrame = (TextView) rootView.findViewById(R.id.accountNameFrame);
        this.accountIDFrame = (TextView) rootView.findViewById(R.id.accountIDFrame);

        SharedPreferences pref = getActivity().getSharedPreferences("FreeTime",getActivity().MODE_PRIVATE);
        String ID = pref.getString("email", null);
        String name = pref.getString("name", null);

        if(ID != null) this.accountIDFrame.setText(ID);
        if(name != null) this.accountNameFrame.setText(name);

        this.signOutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.signOut();
            }
        });
        return rootView;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(activity == null) activity = (MainBaseActivity) getActivity();
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }
}
