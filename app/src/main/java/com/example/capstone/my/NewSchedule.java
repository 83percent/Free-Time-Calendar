package com.example.capstone.my;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone.R;
import com.example.capstone.bean.TimeBean;

public class NewSchedule extends Fragment {
    private EditText starts[] = new EditText[5], ends[] = new EditText[5], inputScheduleName;
    private Integer[] startIDs = {R.id.startYear, R.id.startMonth, R.id.startDay, R.id.startHour, R.id.startMin};
    private Integer[] endIDs = {R.id.endYear, R.id.endMonth, R.id.endDay, R.id.endHour, R.id.endMin};
    private int[] todays = new int[3];
    private String year, month, day;
    private NewBase activity;
    private RelativeLayout addBtn;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (NewBase) getActivity();

        addBtn = activity.findViewById(R.id.addBtn);
        if(addBtn.hasOnClickListeners()) addBtn.setOnClickListener(null);
        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activity.saveNewSchedule(getBean());
            }
        });
    }
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.my_new_schedule, container, false);
        Bundle extra =  this.getArguments();
        inputScheduleName = (EditText) rootView.findViewById(R.id.inputScheduleName);
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

        todays[0] = Integer.parseInt(extra.getString("year"));
        todays[1] = Integer.parseInt(extra.getString("month"));
        todays[2] = Integer.parseInt(extra.getString("day"));
        return rootView;
    }
    private TimeBean getBean() {
        TimeBean bean = new TimeBean();
        int[] __starts = new int[5];
        int[] __ends = new int[5];
        boolean pass = true;

        if(inputScheduleName.getText().toString().length() < 3) {
            Toast.makeText(activity, "일정명이 너무 짧아요", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(inputScheduleName.getText().toString().length() > 20) {
            Toast.makeText(activity, "일정명이 너무 길어요", Toast.LENGTH_SHORT).show();
            return null;
        }
        for(int i = 0; i<5; i++) {
            String s = starts[i].getText().toString();
            String e = ends[i].getText().toString();
            if(s.equals("") || e.equals("")) {
                pass = false;
                break;
            }
            __starts[i] = Integer.parseInt(s);
            __ends[i] = Integer.parseInt(e);
        }
        if(!pass) {
            Toast.makeText(activity, "시간 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
            return null;
        }
        // year 현재 연도 부터 +2 연도 까지 사용가능
        if(__starts[0] > todays[0]+2 || __starts[0] < todays[0] || __ends[0] > todays[0]+2 || __ends[0] < todays[0]) {
            Toast.makeText(activity, todays[0]+"~"+(todays[0]+2)+"사이의 연도만 지정 가능합니다.", Toast.LENGTH_SHORT).show();
            return null;
        }
        // 끝내는 연도는 시작 연도 보다 작을 수 없음
        if(__starts[0] > __ends[0]) {
            Toast.makeText(activity, "연도 시작이 큼 잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
            return null;
        }

        // 1~12월 만 사용가능
        if(__starts[1] < 1 || __starts[1] > 12 || __ends[1] < 1 || __ends[1] > 12) {
            Toast.makeText(activity, "1~12월만 존재합니다.", Toast.LENGTH_SHORT).show();
            return null;
        } else if((__starts[0] == todays[0] && __starts[1] < todays[1])) {
            // 같은 연도이면 현재 월 보다는 커야함.
            Toast.makeText(activity, "지난 날짜 지정이 불가능 합니다.", Toast.LENGTH_SHORT).show();
            return null;
        } else if(__starts[0] == __ends[0] && __starts[1] > __ends[1]) {
            // 시작과 끝 연도가 같으면, 시작 월 보다 끝나는 월이 커야한다.
            Toast.makeText(activity, "종료 날짜가 더 큽니다.", Toast.LENGTH_SHORT).show();
            return null;
        }

        // 일
        if(__starts[2] < 1 || __starts[2] > 31 || __ends[2] < 1 || __ends[2] > 31) {
            Toast.makeText(activity, "1~31일만 사용가능합니다.", Toast.LENGTH_SHORT).show();
            return null;
        } else if((__starts[1] == todays[1] && __starts[2] < todays[2])) {
            // 같은 월이면 현재 일 보다는 커야함.
            Toast.makeText(activity, "지난 날짜 지정이 불가능 합니다.", Toast.LENGTH_SHORT).show();
            return null;
        } else if(__starts[1] == __ends[1] && __starts[2] > __ends[2]) {
            // 시작 월과 끝 월이 같으면 끝일이 더 작으면 안된다.
            Toast.makeText(activity, "종료 날짜가 더 큽니다.", Toast.LENGTH_SHORT).show();
            return null;
        }
        // 시간 (Hour)

        if(__starts[3] > 23 || __starts[3] < 0 || __ends[3] > 23 || __ends[3] < 0) {
            Toast.makeText(activity, "0~23시의 시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(__starts[4] > 59 || __starts[4] < 0 || __ends[4] > 59 || __ends[4] < 0) {
            Toast.makeText(activity, "0~23시의 시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(__starts[2] == __ends[2]) {
            if(__starts[3] > __ends[3]) {
                // 날짜가 같은데 종료시간이 더 작음
                Toast.makeText(activity, "종료시간이 시작시간 보다 더 빠릅니다.", Toast.LENGTH_SHORT).show();
                return null;
            } else if(__starts[3] == __ends[3] && __starts[4] >= __ends[4]) {
                // 날짜와 Hour 이 같은데, 종료 min 이 같거나 더 작음
                Toast.makeText(activity, "종료시간이 시작시간 보다 더 빠릅니다.", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        bean.setName(inputScheduleName.getText().toString());
        bean.setStart(__starts);
        bean.setEnd(__ends);

        return bean;
    }
}
