package com.example.capstone.group;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.MainBaseActivity;
import com.example.capstone.R;
import com.example.capstone.lib.Date;
import com.example.capstone.my.DetailActivity;
import com.example.capstone.my.NewBase;

import org.w3c.dom.Text;

public class GroupMainCalendar extends AppCompatActivity {
    // View
    private ImageButton menuBtn;
    private LinearLayout weeksWrapper;
    private LinearLayout[] WEEKS = new LinearLayout[6];
    private Date date = null;
    private TextView year, month, groupTitle;

    // menu
    private LinearLayout menuWrapper = null;
    private FrameLayout menuFragment, menuCloser;
    private Animation menuOpenAnim, menuCloseAnim;
    private GroupMenu groupMenu;

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
    private String groupID, groupName;
    private String admin;
    private boolean isOpenOption = false;
    private int memberCount = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_calendar);


        date = new Date();
        TextView[] overviewMonthBtn = new TextView[12];
        Integer[] monthIDs = {R.id.month1,R.id.month2,R.id.month3, R.id.month4, R.id.month5, R.id.month6
                ,R.id.month7, R.id.month8, R.id.month9, R.id.month10, R.id.month11, R.id.month12};

        year = (TextView) findViewById(R.id.myCalYear);
        month = (TextView) findViewById(R.id.myCalMonth);

        weeksWrapper = (LinearLayout) findViewById(R.id.weeksWrapper);
        overviewOnBtn = (LinearLayout) findViewById(R.id.calendarOverviewBtn);
        overviewWrapper = (LinearLayout) findViewById(R.id.calendarOverviewWrapper);
        overviewCloser = (FrameLayout) findViewById(R.id.calendarOverviewCloser);
        overviewYear = (TextView) findViewById(R.id.overviewYear);

        __year = date.getYear();
        __newYear = __year;
        __month = date.getMonth();
        __newMonth = __month;
        year.setText(""+__year);
        month.setText(""+__month);


        Intent intent = getIntent();
        if(intent.getStringExtra("groupName") != null) {
            groupTitle = (TextView) findViewById(R.id.groupTitle);
            groupName = intent.getStringExtra("groupName");
            groupTitle.setText(groupName);
            groupID = intent.getStringExtra("groupID");
            admin = intent.getStringExtra("admin");
            memberCount = intent.getIntExtra("memberCount", 0);
        }

        // OverView
        overviewYearMinus = (ImageButton) findViewById(R.id.overviewYearMinus);
        overviewYearPlus = (ImageButton) findViewById(R.id.overviewYearPlus);
        overviewYear.setText(""+__year);

        for(int i=0; i<12; ++i) {
            overviewMonthBtn[i] = (TextView) findViewById(monthIDs[i]);
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

        menuBtn = (ImageButton) findViewById(R.id.menuBtn);
        menuBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuToggle(true);
            }
        });
    }
    public void createCalendar() {
        if(date == null) date = new Date();
        int daysMax = date.getActualMaximum();
        int firstDayOfWeek = date.getFirstDayOfWeek();
        int dayOfWeek = 0;
        int day = 1 - firstDayOfWeek;
        //Log.d("asdf", "시간 계산 : "+ (firstDayOfWeek));
        //Log.d("asdf", "시간 계산 : "+ (day));
        int loopDay = 0;
        for(int week = 0; week<6; ++week) {
            LinearLayout weeksLayout = WEEKS[week];
            while(dayOfWeek != 7) {
                if(__days[loopDay] != null) __days[loopDay].setOnClickListener(null);
                TextView text = (TextView) ((LinearLayout) weeksLayout.getChildAt(dayOfWeek)).getChildAt(0);
                //Log.d("TEST", "createCalendar: 위치 : 줄 = "+week+" / 칸 = " + dayOfWeek);
                FrameLayout frameLayout = (FrameLayout) ((LinearLayout) weeksLayout.getChildAt(dayOfWeek)).getChildAt(1);
                frameLayout.removeAllViewsInLayout();
                //Log.d("TEST", "createCalendar: Frame : " + frameLayout);
                if(day < daysMax+1 && day > 0) {

                    __days[day-1] = (LinearLayout) weeksLayout.getChildAt(dayOfWeek);
                    __days[day-1].setTag(day);
                    __days[day-1].setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(getApplicationContext(), GroupDayDetail.class);
                            intent.putExtra("year", __year);
                            intent.putExtra("month", __month);
                            intent.putExtra("day", v.getTag().toString());
                            intent.putExtra("groupCode", groupID);
                            intent.putExtra("groupName", groupName);
                            intent.putExtra("memberCount", memberCount);
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

    // Anim
    private void menuToggle(boolean toggle) {
        if(menuWrapper == null) {
            menuWrapper = (LinearLayout) findViewById(R.id.menuWrapper);
            menuFragment = (FrameLayout) findViewById(R.id.menuFragment);
            menuCloser = (FrameLayout) findViewById(R.id.menuCloser);
            // Menu
            groupMenu = new GroupMenu(this.groupID);

            menuCloser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    menuToggle(false);
                }
            });

            getSupportFragmentManager().beginTransaction().replace(R.id.menuFragment, groupMenu).commit();

            menuOpenAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_open_slide);
            menuCloseAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.menu_close_slide);
            menuCloseAnim.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) { }

                @Override
                public void onAnimationEnd(Animation animation) {
                    menuWrapper.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) { }
            });
        }
        if(toggle) {
            menuFragment.startAnimation(menuOpenAnim);
            menuWrapper.setVisibility(View.VISIBLE);
        } else {
            menuFragment.startAnimation(menuCloseAnim);
        }
        isOpenOption = toggle;
    }
    private SharedPreferences pref = null;
    public String getUserId() {
        if(pref == null) pref = getSharedPreferences("FreeTime" , MODE_PRIVATE);
        return pref.getString("id", null);
    }
    public String getAdmin() {
        return this.admin;
    }

    @Override
    public void onBackPressed() {
        if(isOpenOption) {
            menuToggle(false);
        } else {
            finish();
        }
    }
}
