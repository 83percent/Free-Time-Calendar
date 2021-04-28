package com.example.capstone.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone.MainBaseActivity;
import com.example.capstone.R;

public class Group extends Fragment {
    MainBaseActivity activity;
    ImageButton addBtn;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainBaseActivity) getActivity();
    }
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.main_group, container, false);
        addBtn = (ImageButton) rootView.findViewById(R.id.groupAddBtn);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.toggleAddGroupOption(true);
            }
        });
        return rootView;
    }
}
