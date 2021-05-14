package com.example.capstone.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone.MainBaseActivity;
import com.example.capstone.R;
import com.example.capstone.bean.FreeBean;
import com.example.capstone.data.TimeData;
import com.example.capstone.lib.Date;
import com.example.capstone.my.DetailActivity;
import com.example.capstone.my.NewBase;

import static android.app.Activity.RESULT_OK;

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
    private MainBaseActivity activity;
    private TextView __onMonthView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainBaseActivity) getActivity();
    }

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
        attachFree(activity.getID(), activity.getApplicationContext(), __year, __month);

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
                startActivityForResult(intent, 8080);
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
        Log.d("asdf", "시간 계산 : "+ (firstDayOfWeek));
        Log.d("asdf", "시간 계산 : "+ (day));
        int loopDay = 0;
        for(int week = 0; week<6; ++week) {
            LinearLayout weeksLayout = WEEKS[week];
            while(dayOfWeek != 7) {
                if(__days[loopDay] != null) __days[loopDay].setOnClickListener(null);
                TextView text = (TextView) ((LinearLayout) weeksLayout.getChildAt(dayOfWeek)).getChildAt(0);
                Log.d("TEST", "createCalendar: 위치 : 줄 = "+week+" / 칸 = " + dayOfWeek);
                FrameLayout frameLayout = (FrameLayout) ((LinearLayout) weeksLayout.getChildAt(dayOfWeek)).getChildAt(1);
                frameLayout.removeAllViewsInLayout();
                Log.d("TEST", "createCalendar: Frame : " + frameLayout);
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
        attachFree(activity.getID(), activity.getApplicationContext(), year, month);
    }
    private void attachFree(String id, Context context, int year, int month) {
        TimeData timeData = new TimeData(id, context);
        FreeBean[] beans = timeData.get(year, month);
        /*
            TODO : 화면에 붙이는 작업
            몇 번째 줄 위치 = |(날짜 + firstDayOfWeek(첫번쨰 1일에 위치를 구하는 값) - 1) / 7|
            몇 번째 요소 위치 = |(날짜 + firstDayOfWeek(첫번쨰 1일에 위치를 구하는 값) - 1) % 7|
         */

        if(beans != null) {
            LinearLayout parent = null;
            LinearLayout child = null;
            int firstDayOfWeek = date.getFirstDayOfWeek();

            for(int i=0; i<beans.length; i++) {
                int _o = beans[i].getsDay()+firstDayOfWeek-1;
                parent = WEEKS[Math.abs(_o / 7)];
                child = (LinearLayout) parent.getChildAt(Math.abs(_o%7));
                Log.d("Data", "income day : " + beans[i].getsDay());
                Log.d("Data", "income start Hour : " + beans[i].getsHour());
                Log.d("Data", "income end Hour : " + beans[i].geteHour());
                if(beans[i].getsDay() == beans[i].geteDay()) {
                    ((FrameLayout) child.getChildAt(1)).addView(createBar(beans[i].getsHour(), beans[i].getsMin(), beans[i].geteHour(), beans[i].geteMin(), true));
                } else {
                    ((FrameLayout) child.getChildAt(1)).addView(createBar(beans[i].getsHour(), beans[i].getsMin(), 23, 0, true));
                }
            }
        }
    }
    private FrameLayout createBar(int startHour, int startMin, int endHour, int endMin, boolean isFree) {
        int gap = 0;
        if(startHour == endHour) {
            gap = 3;
        } else {
            gap = (endHour - startHour);
            // 30분 이상이면 일정 분량 더 추가해줌
            if(startMin >= 30) { gap += 1.5; }
            if(endMin >= 30) { gap += 1.5; }
        }
        if(gap > 0) {
            final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, gap*3, getResources().getDisplayMetrics());
            FrameLayout frameLayout = new FrameLayout(activity);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, height);
            params.setMargins(0,startHour*3,0,0);

            frameLayout.setLayoutParams(params);
            if(isFree) frameLayout.setBackgroundColor(Color.parseColor("#32BE38"));
            else frameLayout.setBackgroundColor(Color.parseColor("#FF5151"));
            return frameLayout;
        }
        return null;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 8080 && resultCode == RESULT_OK) {
            int saveMonth = data.getIntExtra("month",0);
            if(__month == saveMonth) {
                attachFree(activity.getID(), activity.getApplicationContext(), __year, saveMonth);
            }
        }
    }
}
