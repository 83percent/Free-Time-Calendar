package com.example.capstone.group;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;
import com.example.capstone.bean.GroupVoteBean;
import com.example.capstone.bean.TimeBean;
import com.example.capstone.connect.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupNewSchedule extends AppCompatActivity {
    // View
    private TextView groupNameFrame;
    private EditText starts[] = new EditText[5], ends[] = new EditText[5], scheduleName, minLength, memoInput;
    private int[] startIDs = {R.id.startYear, R.id.startMonth, R.id.startDay, R.id.startHour, R.id.startMin};
    private int[] endIDs = {R.id.endYear, R.id.endMonth, R.id.endDay, R.id.endHour, R.id.endMin};
    private int[] todays = new int[3];
    private RelativeLayout sendVoteBtn;

    // Field
    private String groupName, groupCode, year, month, day;
    private int memberCount;
    private RetrofitConnection retrofitConnection;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_new_schedule);
        // Memorize
        this.groupNameFrame = (TextView) findViewById(R.id.groupName);
        this.scheduleName = (EditText) findViewById(R.id.scheduleName);
        this.minLength = (EditText) findViewById(R.id.minLength);
        this.sendVoteBtn = (RelativeLayout) findViewById(R.id.sendVote);
        this.memoInput = (EditText) findViewById(R.id.memoInput);

        for(int i=0; i<5; ++i) {
            starts[i] = (EditText) findViewById(startIDs[i]);
            ends[i] = (EditText) findViewById(endIDs[i]);
        }

        // Get information in intent
        Intent getDataIntent = getIntent();
        if(getDataIntent != null) {
            this.groupName = getDataIntent.getStringExtra("groupName");
            if(this.groupName != null) this.groupNameFrame.setText(this.groupName);
            this.groupCode = getDataIntent.getStringExtra("groupCode");
            this.year = getDataIntent.getStringExtra("year");
            this.month = getDataIntent.getStringExtra("month");
            this.day = getDataIntent.getStringExtra("day");
            this.memberCount = getDataIntent.getIntExtra("memberCount", 0);
            if(this.year != null) {
                starts[0].setText(this.year);
                todays[0] = Integer.parseInt(year);

                ends[0].setText(this.year);
            }
            if(this.month != null) {
                starts[1].setText(this.month);
                todays[1] = Integer.parseInt(month);

                ends[1].setText(this.month);
            }
             if(this.day != null) {
                 starts[2].setText(this.day);
                 todays[2] = Integer.parseInt(day);

                 ends[2].setText(this.day);
             }
        }

        sendVoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(retrofitConnection == null) retrofitConnection = RetrofitConnection.getInstance();
                GroupVoteBean bean = getBean();
                if(bean != null) {
                    Call<GroupVoteBean> call = retrofitConnection.server.addVote(groupCode, bean);
                    call.enqueue(new Callback<GroupVoteBean>() {
                        @Override
                        public void onResponse(Call<GroupVoteBean> call, Response<GroupVoteBean> response) {
                            if(response.code() == 200) {
                                // 설명 부분 추가해서 전송
                                // 투표로 이동하는 코드
                                // 투표 화면에 필요한 정보 아직 모르겠음 내일 확인해보자

                                /*
                                    필요한 데이터
                                    vote ID
                                    vote Name
                                    start
                                    end
                                    memo
                                 */
                                GroupVoteBean bean = response.body();
                                Log.d("결과물", "_id : " + bean.get_id() );
                                Log.d("결과물", "GroupCode : " + bean.getGroupCode());
                                Log.d("결과물", "agree : " + bean.getAgree());
                                Log.d("결과물", "name : " + bean.getName() );
                                Log.d("결과물", "end : " + bean.getStart() );
                                Log.d("결과물", "start : " + bean.getEnd() );
                                Log.d("결과물", "len : " + bean.getMinLength() );
                                Log.d("결과물", "reg_id : " + bean.getReg_id() );

                                Intent intent = new Intent(getApplicationContext(), VoteActivity.class);
                                intent.putExtra("voteCode", bean.get_id());
                                intent.putExtra("voteName", bean.getName());
                                intent.putExtra("agree",bean.getAgree());
                                intent.putExtra("start", bean.getStart());
                                intent.putExtra("end", bean.getEnd());
                                intent.putExtra("memo", bean.getMemo());

                                finish();
                                startActivity(intent);
                            }
                        }
                        @Override
                        public void onFailure(Call<GroupVoteBean> call, Throwable t) {
                            Toast.makeText(getApplicationContext(), "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

    } // onCreate()
    private GroupVoteBean getBean() {
        GroupVoteBean bean = new GroupVoteBean();

        if(this.scheduleName.getText().toString().length() < 2) {
            Toast.makeText(getApplicationContext(), "스케줄 이름이 너무 짧아요", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(this.scheduleName.getText().toString().length() > 20) {
            Toast.makeText(getApplicationContext(), "스케줄 이름이 너무 길어요", Toast.LENGTH_SHORT).show();
            return null;
        }
        int[] __starts = new int[5];
        int[] __ends = new int[5];
        boolean pass = true;
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
            Toast.makeText(getApplicationContext(), "시간 정보를 입력해주세요", Toast.LENGTH_SHORT).show();
            return null;
        }
        // year 현재 연도 부터 +2 연도 까지 사용가능
        if(__starts[0] > todays[0]+2 || __starts[0] < todays[0] || __ends[0] > todays[0]+2 || __ends[0] < todays[0]) {
            Toast.makeText(getApplicationContext(), todays[0]+"~"+(todays[0]+2)+"사이의 연도만 지정 가능합니다.", Toast.LENGTH_SHORT).show();
            return null;
        }
        // 끝내는 연도는 시작 연도 보다 작을 수 없음
        if(__starts[0] > __ends[0]) {
            Toast.makeText(getApplicationContext(), "연도 시작이 큼 잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
            return null;
        }

        // 1~12월 만 사용가능
        if(__starts[1] < 1 || __starts[1] > 12 || __ends[1] < 1 || __ends[1] > 12) {
            Toast.makeText(getApplicationContext(), "1~12월만 존재합니다.", Toast.LENGTH_SHORT).show();
            return null;
        } else if((__starts[0] == todays[0] && __starts[1] < todays[1])) {
            // 같은 연도이면 현재 월 보다는 커야함.
            Toast.makeText(getApplicationContext(), "지난 날짜 지정이 불가능 합니다.", Toast.LENGTH_SHORT).show();
            return null;
        } else if(__starts[0] == __ends[0] && __starts[1] > __ends[1]) {
            // 시작과 끝 연도가 같으면, 시작 월 보다 끝나는 월이 커야한다.
            Toast.makeText(getApplicationContext(), "종료 날짜가 더 큽니다.", Toast.LENGTH_SHORT).show();
            return null;
        }

        // 일
        if(__starts[2] < 1 || __starts[2] > 31 || __ends[2] < 1 || __ends[2] > 31) {
            Toast.makeText(getApplicationContext(), "1~31일만 사용가능합니다.", Toast.LENGTH_SHORT).show();
            return null;
        } else if((__starts[1] == todays[1] && __starts[2] < todays[2])) {
            // 같은 월이면 현재 일 보다는 커야함.
            Toast.makeText(getApplicationContext(), "지난 날짜 지정이 불가능 합니다.", Toast.LENGTH_SHORT).show();
            return null;
        } else if(__starts[1] == __ends[1] && __starts[2] > __ends[2]) {
            // 시작 월과 끝 월이 같으면 끝일이 더 작으면 안된다.
            Toast.makeText(getApplicationContext(), "종료 날짜가 더 큽니다.", Toast.LENGTH_SHORT).show();
            return null;
        }
        // 시간 (Hour)

        if(__starts[3] > 23 || __starts[3] < 0 || __ends[3] > 23 || __ends[3] < 0) {
            Toast.makeText(getApplicationContext(), "0~23시의 시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(__starts[4] > 59 || __starts[4] < 0 || __ends[4] > 59 || __ends[4] < 0) {
            Toast.makeText(getApplicationContext(), "0~23시의 시간을 입력해주세요.", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(__starts[2] == __ends[2]) {
            if(__starts[3] > __ends[3]) {
                // 날짜가 같은데 종료시간이 더 작음
                Toast.makeText(getApplicationContext(), "종료시간이 시작시간 보다 더 빠릅니다.", Toast.LENGTH_SHORT).show();
                return null;
            } else if(__starts[3] == __ends[3] && __starts[4] >= __ends[4]) {
                // 날짜와 Hour 이 같은데, 종료 min 이 같거나 더 작음
                Toast.makeText(getApplicationContext(), "종료시간이 시작시간 보다 더 빠릅니다.", Toast.LENGTH_SHORT).show();
                return null;
            }
        }
        if(Integer.parseInt(this.minLength.getText().toString()) < 2) {
            Toast.makeText(getApplicationContext(), "참여인원은 최소 2명입니다.", Toast.LENGTH_SHORT).show();
            return null;
        }
        if(memberCount != 0 && Integer.parseInt(this.minLength.getText().toString()) > memberCount) {
            Toast.makeText(getApplicationContext(), "그룹인원보다 많은 인원을 입력하셨습니다.", Toast.LENGTH_SHORT).show();
            return null;
        }

        SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);
        String id = pref.getString("id", null);
        if(id != null) {
            bean.setName(this.scheduleName.getText().toString());
            bean.setStart(__starts);
            bean.setEnd(__ends);
            bean.setMinLength(this.minLength.getText().toString());
            bean.setReg_id(id);
            bean.setMemo(this.memoInput.getText().toString());
            return bean;
        }

        return null;
    } // getBean()
}
