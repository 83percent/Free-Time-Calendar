package com.example.capstone.group;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.capstone.R;
import com.example.capstone.bean.CreateGroupBean;
import com.example.capstone.connect.RetrofitConnection;
import com.example.capstone.data.GroupData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class AddNewGroup extends Activity {
    private ImageButton back;
    private RelativeLayout createBtn;
    private EditText inputGroupName;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_new_group);

        back = (ImageButton) findViewById(R.id.newGroupBack);
        createBtn = (RelativeLayout) findViewById(R.id.createNewGroupBtn);
        inputGroupName = (EditText) findViewById(R.id.input_group_name);

        createBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 1. 그룹 명 확인
                // 2. 서버 전송
                // 3. 반환 아이디 db 저장

                SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);
                final String id = pref.getString("id", null);
                if(id == null) return;
                final String groupName = inputGroupName.getText().toString();

                if(groupName != null && (groupName.length() > 1 && groupName.length() < 21)) {
                    CreateGroupBean bean = new CreateGroupBean(groupName, id);

                    RetrofitConnection retrofitConnection = RetrofitConnection.getInstance();
                    Call<String> call = retrofitConnection.server.createGroup(bean);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            if(response.code() == 200) {
                                // Group DB 에 넣기
                                GroupData groupData = GroupData.getInstance(getApplicationContext());
                                boolean isCreate = groupData.set(response.body(), groupName, id);
                                if(isCreate) {
                                    Intent intent = new Intent();
                                    intent.putExtra("isCreate", isCreate);
                                    intent.putExtra("groupCode", response.body());
                                    setResult(RESULT_OK, intent);
                                    finish();
                                } else {
                                    Toast.makeText(AddNewGroup.this, "오류로 인해 생성할 수 없습니다", Toast.LENGTH_SHORT).show();
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Toast.makeText(AddNewGroup.this, "오류로 인해 생성할 수 없습니다", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
