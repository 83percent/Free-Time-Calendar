package com.example.capstone.group;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.capstone.R;
import com.example.capstone.bean.IDReturnBean;
import com.example.capstone.connect.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputInvitation extends Activity {
    private ImageButton back;
    private RelativeLayout sendBtn;
    private EditText codeInputView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_input_invitation);
        sendBtn = (RelativeLayout) findViewById(R.id.sendInvitationRequestBtn);
        codeInputView = (EditText) findViewById(R.id.codeInputView);

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    String code = codeInputView.getText().toString();
                    if(code == null || code.length() != 24) {
                        Toast.makeText(InputInvitation.this, "유효한 초대코드 아닙니다.", Toast.LENGTH_SHORT).show();
                    } else {
                        SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);
                        final String id = pref.getString("id", null);
                        if(id != null) {
                            RetrofitConnection retrofitConnection = RetrofitConnection.getInstance();
                            Call<Integer> call = retrofitConnection.server.sendApplyGroup(code, new IDReturnBean(id));
                            call.enqueue(new Callback<Integer>() {
                                @Override
                                public void onResponse(Call<Integer> call, Response<Integer> response) {
                                    switch (response.code()) {
                                        case 200 : {
                                            if(response.body() == 1) {
                                                finish();
                                                Intent intent = new Intent(getApplicationContext(), SuccessEnterInvitation.class);
                                                startActivity(intent);
                                            } else if(response.body() == 0) {
                                                Toast.makeText(InputInvitation.this, "이미 참여중인 그룹입니다.", Toast.LENGTH_SHORT).show();
                                            } else if(response.body() == -1){
                                                Toast.makeText(InputInvitation.this, "이미 승인 대기중인 초대코드입니다.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(InputInvitation.this, "존재하지 않는 초대코드 입니다.", Toast.LENGTH_SHORT).show();
                                            }
                                            break;
                                        }
                                        case 404 : {
                                            Toast.makeText(InputInvitation.this, "존재하지 않는 초대코드 입니다.", Toast.LENGTH_SHORT).show();
                                            break;
                                        }
                                        case 412 :
                                        case 500 :
                                        default : {
                                            Toast.makeText(InputInvitation.this, "오류가 발생했습니다. ", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                }
                                @Override
                                public void onFailure(Call<Integer> call, Throwable t) {
                                    Toast.makeText(InputInvitation.this, "오류가 발생했습니다. ", Toast.LENGTH_SHORT).show();
                                }
                            });

                        }
                    }
                } catch(Exception e) {
                    return;
                }
            }
        });

    }
}
