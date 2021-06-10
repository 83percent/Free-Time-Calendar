package com.example.capstone.my;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
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
import com.example.capstone.bean.TimeBean;
import com.example.capstone.connect.RetrofitConnection;
import com.example.capstone.data.TimeData;
import com.example.capstone.lib.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
    private TimeBean[] beans[];
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

        SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);
        String id = pref.getString("id", null);
        RetrofitConnection retrofitConnection = RetrofitConnection.getInstance();
        Call<TimeBean[]> call = retrofitConnection.server.getUserTimeForDate(id, String.valueOf(this.year), String.valueOf(this.month), this.day);
        call.enqueue(new Callback<TimeBean[]>() {
            @Override
            public void onResponse(Call<TimeBean[]> call, Response<TimeBean[]> response) {
                if(response.code() == 200 && response.body().length > 0) {
                    TimeBean[] beans = response.body();
                    if(beans != null) {
                        for(int i=0; i<beans.length; i++) {
                            barWrapper.addView(createBar(beans[i].getsHour(), beans[i].getsMin(), beans[i].geteHour(), beans[i].geteMin(), beans[i].getType(), beans[i].getName()));
                        }
                    }

                }

            }

            @Override
            public void onFailure(Call<TimeBean[]> call, Throwable t) {
                Toast.makeText(DetailActivity.this, "일정을 불러올 수 없어요", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private FreeBean[] getData(int year, int month, int day) {
        SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);
        String id = pref.getString("id", null);
        TimeData timeData = new TimeData(id, getApplicationContext());
        return timeData.get(year, month, day);
    }
    private FrameLayout createBar(int startHour, int startMin, int endHour, int endMin, String type, String name) {
        int __height = ((endHour - startHour)*60) + endMin - startMin;
        final int gap = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, ((startHour*60) + startMin + 3), getResources().getDisplayMetrics());
        final int width = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 25, getResources().getDisplayMetrics());
        final int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, __height, getResources().getDisplayMetrics());
        FrameLayout frameLayout = new FrameLayout(this);
        FrameLayout.LayoutParams params = null;

        if(type.equals("free")) {
            params = new FrameLayout.LayoutParams(width, height);
            if(gap != 0) params.setMargins(0,gap,20,0);
            frameLayout.setLayoutParams(params);
            frameLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.free_bar));

        } else if(type.equals("schedule")) {
            params = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
            if(gap != 0) params.setMargins(0,gap,20,0);
            frameLayout.setLayoutParams(params);
            TextView nameFrame = new TextView(getApplicationContext());
            nameFrame.setGravity(Gravity.CENTER);
            nameFrame.setText(name);
            nameFrame.setTextColor(Color.parseColor("#ffffff"));
            nameFrame.setLines(1);

            frameLayout.addView(nameFrame);
            frameLayout.setBackground(ContextCompat.getDrawable(this, R.drawable.schedule_bar));
        } else return null;



        return frameLayout;
    }
}
