package com.example.capstone.group;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.capstone.R;
import com.example.capstone.bean.FreeBean;
import com.example.capstone.bean.GroupFreeBean;
import com.example.capstone.bean.GroupFreeBeanElement;
import com.example.capstone.bean.RequestDateBean;
import com.example.capstone.connect.RetrofitConnection;
import com.example.capstone.data.TimeData;
import com.example.capstone.lib.Date;
import com.example.capstone.my.NewBase;

import java.text.SimpleDateFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupDayDetail extends AppCompatActivity {
    // View
    private TextView dayView;
    private TextView dateView;
    private ImageButton createBtn;
    private LinearLayout barWrapper;

    // Field
    private int year;
    private int month;
    private String day;
    private String groupCode, groupName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_calendar_detail);

        FrameLayout timeWrapper = (FrameLayout) findViewById(R.id.timeWrapper);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getRealSize(size); // or getSize(size)
        timeWrapper.setMinimumWidth(size.x);
        dayView = (TextView) findViewById(R.id.myDetailDay);
        dateView = (TextView) findViewById(R.id.myDetailDate);
        createBtn = (ImageButton) findViewById(R.id.myDetailCreate);
        barWrapper = (LinearLayout) findViewById(R.id.barWrapper);

        Intent dateIntent = getIntent();
        Date date = new Date();
        if(dateIntent != null) {
            year = dateIntent.getIntExtra("year",  date.getYear());
            month = dateIntent.getIntExtra("month",  date.getMonth());
            day = dateIntent.getStringExtra("day");
            this.groupCode = dateIntent.getStringExtra("groupCode");
            this.groupName = dateIntent.getStringExtra("groupName");
        }

        if(day == null) day = String.valueOf(date.getDay());

        dayView.setText(""+day);
        dateView.setText(year+"-"+month+"-"+day);


        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GroupNewSchedule.class);
                intent.putExtra("year", String.valueOf(year));
                intent.putExtra("month", String.valueOf(month));
                intent.putExtra("day",day);
                intent.putExtra("groupName", groupName);
                intent.putExtra("groupCode", groupCode);
                startActivity(intent);
            }
        });

        if(this.groupCode != null) {
            RetrofitConnection retrofitConnection = RetrofitConnection.getInstance();
            Log.d("목록 만들기", "요청 날짜 : " + year +"/"+month+"/"+day);
            Call<GroupFreeBean[]> call = retrofitConnection.server.getGroupFree(dateIntent.getStringExtra("groupCode"), new RequestDateBean(this.year, this.month, Integer.parseInt(this.day)));
            call.enqueue(new Callback<GroupFreeBean[]>() {
                @Override
                public void onResponse(Call<GroupFreeBean[]> call, Response<GroupFreeBean[]> response) {
                    if(response.code() == 200) {
                        GroupFreeBean[] beans = response.body();
                        for(int i=0; i<beans.length; ++i) {
                            barWrapper.addView(setUserBar(beans[i].getName(), beans[i].getElements()));
                        }
                    }
                }

                @Override
                public void onFailure(Call<GroupFreeBean[]> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                }
            });
        }
    }

    private FrameLayout setUserBar(String name, GroupFreeBeanElement[] elements) {
        FrameLayout wrapper = new FrameLayout(getApplicationContext());
        FrameLayout.LayoutParams _wp = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        wrapper.setLayoutParams(_wp);
        SimpleDateFormat transFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            for(int i=0; i<elements.length; ++i) {
                java.util.Date from = transFormat.parse(elements[i].getStart());
                java.util.Date to = transFormat.parse(elements[i].getEnd());
                int __height = ((to.getHours() - from.getHours()) * 60) + to.getMinutes() - from.getMinutes();
                final int gap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ((from.getHours()*60) + from.getMinutes() + 3), getResources().getDisplayMetrics());
                final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, __height, getResources().getDisplayMetrics());


                FrameLayout bar = new FrameLayout(this);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
                if(gap != 0) params.setMargins(0,gap,20,0);
                bar.setPadding(2,0,2,0);
                bar.setLayoutParams(params);
                bar.setBackground(ContextCompat.getDrawable(this, R.drawable.free_bar));

                TextView nameFrame = new TextView(getApplicationContext());
                nameFrame.setGravity(Gravity.CENTER);
                nameFrame.setText(name);
                nameFrame.setTextColor(Color.parseColor("#ffffff"));
                nameFrame.setLines(1);

                bar.addView(nameFrame);
                wrapper.addView(bar);
            }
        } catch(Exception e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "(Make)오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }
        return wrapper;
    }
}
