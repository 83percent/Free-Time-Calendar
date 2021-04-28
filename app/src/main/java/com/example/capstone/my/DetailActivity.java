package com.example.capstone.my;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;
import com.example.capstone.lib.Date;

public class DetailActivity extends AppCompatActivity {
    // View
    private TextView dayView;
    private TextView dateView;
    private ImageButton createBtn;

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
    }
}
