package com.example.capstone.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone.MainBaseActivity;
import com.example.capstone.R;

public class Setting extends Fragment {
    private LinearLayout signOutBtn;
    private MainBaseActivity activity;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.main_setting, container, false);
        signOutBtn = (LinearLayout) rootView.findViewById(R.id.signOut);

        signOutBtn.setOnClickListener(new View.OnClickListener() {
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
