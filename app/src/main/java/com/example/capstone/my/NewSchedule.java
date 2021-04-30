package com.example.capstone.my;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone.R;

public class NewSchedule extends Fragment {
    private EditText starts[] = new EditText[5], ends[] = new EditText[5];
    private Integer[] startIDs = {R.id.startYear, R.id.startMonth, R.id.startDay, R.id.startHour, R.id.startMin};
    private Integer[] endIDs = {R.id.endYear, R.id.endMonth, R.id.endDay, R.id.endHour, R.id.endMin};
    private String year, month, day;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.my_new_schedule, container, false);
        Bundle extra =  this.getArguments();
        year = extra.getString("year");
        month = extra.getString("month");
        day = extra.getString("day");
        for(int i=0; i<5; ++i) {
            starts[i] = (EditText) rootView.findViewById(startIDs[i]);
            ends[i] = (EditText) rootView.findViewById(endIDs[i]);
        }
        starts[0].setText(year);
        starts[1].setText(month);
        starts[2].setText(day);
        ends[0].setText(year);
        ends[1].setText(month);
        ends[2].setText(day);
        return rootView;
    }
}
