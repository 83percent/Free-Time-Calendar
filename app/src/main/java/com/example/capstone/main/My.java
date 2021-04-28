package com.example.capstone.main;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone.R;
import com.example.capstone.lib.Date;
import com.example.capstone.my.DetailActivity;
import com.example.capstone.my.NewBase;

public class My extends Fragment {
    // View
    private ImageButton createBtn;
    private LinearLayout weeksWrapper;
    private LinearLayout[] WEEKS = new LinearLayout[6];
    private Date date = null;

    private TextView year, month;

    // Calendar
    private LinearLayout[] __days = new LinearLayout[42];

    // Overview
    private LinearLayout overviewOnBtn;
    private LinearLayout overviewWrapper;
    private FrameLayout overviewCloser;
    private TextView overviewYear;
    private ImageButton overviewYearMinus, overviewYearPlus;

    // Field
    private int __year, __month;
    private int __newYear, __newMonth;

    private TextView __onMonthView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.main_my, container, false);
        date = new Date();
        TextView[] overviewMonthBtn = new TextView[12];
        Integer[] monthIDs = {R.id.month1,R.id.month2,R.id.month3, R.id.month4, R.id.month5, R.id.month6
                            ,R.id.month7, R.id.month8, R.id.month9, R.id.month10, R.id.month11, R.id.month12};

        year = (TextView) rootView.findViewById(R.id.myCalYear);
        month = (TextView) rootView.findViewById(R.id.myCalMonth);

        weeksWrapper = (LinearLayout) rootView.findViewById(R.id.weeksWrapper);

        overviewOnBtn = (LinearLayout) rootView.findViewById(R.id.calendarOverviewBtn);
        overviewWrapper = (LinearLayout) rootView.findViewById(R.id.calendarOverviewWrapper);
        overviewCloser = (FrameLayout) rootView.findViewById(R.id.calendarOverviewCloser);
        overviewYear = (TextView) rootView.findViewById(R.id.overviewYear);

        __year = date.getYear();
        __newYear = __year;
        __month = date.getMonth();
        __newMonth = __month;
        year.setText(""+__year);
        month.setText(""+__month);


        // OverView
        overviewYearMinus = (ImageButton) rootView.findViewById(R.id.overviewYearMinus);
        overviewYearPlus = (ImageButton) rootView.findViewById(R.id.overviewYearPlus);
        overviewYear.setText(""+__year);

        for(int i=0; i<12; ++i) {
            overviewMonthBtn[i] = (TextView) rootView.findViewById(monthIDs[i]);
            if(i == __month-1) {
                overviewMonthBtn[i].setTextColor(Color.parseColor("#0177FC"));
                __onMonthView = overviewMonthBtn[i];
            }
            overviewMonthBtn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    __onMonthView.setTextColor(Color.parseColor("#000000"));
                    __onMonthView = (TextView) v;
                    __onMonthView.setTextColor(Color.parseColor("#0177FC"));
                    __newMonth = Integer.parseInt(__onMonthView.getText().toString());
                }
            });
        }

        for(int i=0; i<WEEKS.length; ++i) { WEEKS[i] = (LinearLayout) weeksWrapper.getChildAt(i); }
        createCalendar();

        // overview Event
        overviewOnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { overviewWrapper.setVisibility(View.VISIBLE); }
        });
        overviewCloser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(__year != __newYear || __month != __newMonth) {
                    __year = __newYear;
                    __month = __newMonth;
                    changeCalendar(__year, __month);
                    year.setText(""+__year);
                    month.setText(""+__month);
                }
                overviewWrapper.setVisibility(View.GONE);
            }
        });
        overviewYearMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                __newYear--;
                overviewYear.setText(""+__newYear);
            }
        });
        overviewYearPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                __newYear++;
                overviewYear.setText(""+__newYear);
            }
        });

        createBtn = (ImageButton) rootView.findViewById(R.id.myMainCreate);
        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), NewBase.class);
                intent.putExtra("year",__year);
                intent.putExtra("month", __month);
                startActivity(intent);
            }
        });

        return rootView;
    }
    public void createCalendar() {
        if(date == null) date = new Date();
        int daysMax = date.getActualMaximum();
        int firstDayOfWeek = date.getFirstDayOfWeek();
        int dayOfWeek = 0;
        int day = 1 - firstDayOfWeek;
        int loopDay = 0;
        for(int week = 0; week<6; ++week) {
            LinearLayout weeksLayout = WEEKS[week];
            while(dayOfWeek != 7) {
                if(__days[loopDay] != null) __days[loopDay].setOnClickListener(null);
                TextView text = (TextView) ((LinearLayout) weeksLayout.getChildAt(dayOfWeek)).getChildAt(0);
                if(day < daysMax+1 && day > 0) {

                    __days[day-1] = (LinearLayout) weeksLayout.getChildAt(dayOfWeek);
                    __days[day-1].setTag(day);
                    __days[day-1].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getContext(), DetailActivity.class);
                            intent.putExtra("year", __year);
                            intent.putExtra("month", __month);
                            intent.putExtra("day", v.getTag().toString());
                            startActivity(intent);
                        }
                    });
                    text.setText(""+day);
                } else { text.setText(""); }
                ++day;
                ++dayOfWeek;
                ++loopDay;
            }
            dayOfWeek = 0;
        }
    }
    public void changeCalendar(int year, int month) {
        if(date == null) date = new Date();
        date.setDate(year, month, 1);
        createCalendar();
    }
}
