package com.example.capstone.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.capstone.R;
import com.example.capstone.bean.FreeBean;
import com.example.capstone.data.TimeData;
import com.example.capstone.lib.Date;

public class DetailActivity extends AppCompatActivity {
    // View
    private TextView dayView;
    private TextView dateView;
    private ImageButton createBtn;
    private LinearLayout barWrapper;

    // Field
    private int year;
    private int month;
    private String day;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_calendar_detail);

        dayView = (TextView) findViewById(R.id.myDetailDay);
        dateView = (TextView) findViewById(R.id.myDetailDate);
        createBtn = (ImageButton) findViewById(R.id.myDetailCreate);
        barWrapper = (LinearLayout) findViewById(R.id.barWrapper);

        Intent dateIntent = getIntent();
        Date date = new Date();
        year = dateIntent.getIntExtra("year",  date.getYear());
        month = dateIntent.getIntExtra("month",  date.getMonth());
        day = dateIntent.getStringExtra("day");
        if(day == null) day = String.valueOf(date.getDay());

        dayView.setText(""+day);
        dateView.setText(year+"-"+month+"-"+day);


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), NewBase.class);
                intent.putExtra("year", year);
                intent.putExtra("month", month);
                intent.putExtra("day",day);
                startActivity(intent);
            }
        });

        FreeBean[] beans = getData(year, month, Integer.parseInt(day));
        if(beans != null) {
            for(int i=0; i<beans.length; i++) {
                barWrapper.addView(createBar(beans[i].getsHour(), beans[i].getsMin(), beans[i].geteHour(), beans[i].geteMin(), true));
            }
        }
    }
    private FreeBean[] getData(int year, int month, int day) {
        SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);
        String id = pref.getString("id", null);
        TimeData timeData = new TimeData(id, getApplicationContext());
        return timeData.get(year, month, day);
    }
    private FrameLayout createBar(int startHour, int startMin, int endHour, int endMin, boolean isFree) {
        int __height = ((endHour - startHour)*60) + endMin - startMin;
        int gap = (startHour*60) + startMin;

        final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 20, getResources().getDisplayMetrics());
        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, __height, getResources().getDisplayMetrics());
        FrameLayout frameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        if(gap != 0) params.setMargins(0,gap,0,0);
        frameLayout.setLayoutParams(params);

        if(isFree) frameLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.free_bar));
        else frameLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.schedule_bar));
        return frameLayout;
    }
}
