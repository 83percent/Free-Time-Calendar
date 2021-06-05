package com.example.capstone.group;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;
import com.example.capstone.bean.GroupMemberBean;
import com.example.capstone.connect.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupApplierList extends AppCompatActivity {
    private ImageButton backBtn;
    private ListView listView;
    private LinearLayout moveBanList;
    private RelativeLayout banMenuFrame;
    private FrameLayout banBtn;

    // Field
    private String admin, code, id;
    private RetrofitConnection retrofitConnection = null;
    private GroupMemberBean[] listBeans;
    private Animation up, down;
    private String banID;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_apply_list);

        backBtn = (ImageButton) findViewById(R.id.backBtn);
        listView = (ListView) findViewById(R.id.applyListView);
        moveBanList = (LinearLayout) findViewById(R.id.moveBanList);
        banMenuFrame = (RelativeLayout) findViewById(R.id. banMenuFrame);
        banBtn = (FrameLayout) findViewById(R.id.banBtn);

        up = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.none_slide_up);

        Intent dataIntent = getIntent();
        this.admin = dataIntent.getStringExtra("admin");
        this.code = dataIntent.getStringExtra("groupCode");

        SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);
        this.id = pref.getString("id", null);
        if(this.id != null) {
            if(retrofitConnection == null) retrofitConnection = RetrofitConnection.getInstance();
            Call<GroupMemberBean[]> call = retrofitConnection.server.getApplierList(code);
            call.enqueue(new Callback<GroupMemberBean[]>() {
                @Override
                public void onResponse(Call<GroupMemberBean[]> call, Response<GroupMemberBean[]> response) {
                    if(response.code() == 200) {
                        if(admin != null) {
                            final GroupApplierListAdapter adapter = new GroupApplierListAdapter(getApplicationContext(), response.body(),  id.equals(admin), code);
                            listView.setAdapter(adapter);
                            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                                @Override
                                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                                    banID = adapter.getApplierId(position);
                                    banMenuFrame.setVisibility(View.VISIBLE);
                                    banBtn.startAnimation(up);
                                    down = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.none_slide_down);
                                    down.setAnimationListener(new Animation.AnimationListener() {
                                        @Override
                                        public void onAnimationStart(Animation animation) {}
                                        @Override
                                        public void onAnimationEnd(Animation animation) {
                                            banMenuFrame.setVisibility(View.GONE);
                                        }
                                        @Override
                                        public void onAnimationRepeat(Animation animation) {}
                                    });
                                    Toast.makeText(GroupApplierList.this, banID, Toast.LENGTH_SHORT).show();
                                    banMenuFrame.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            banBtn.startAnimation(down);
                                        }
                                    });
                                    return true;
                                }
                            });
                        } else {
                            GroupApplierListAdapter adapter = new GroupApplierListAdapter(getApplicationContext(), response.body(),  false, code);
                            listView.setAdapter(adapter);
                        }


                    }
                }
                @Override
                public void onFailure(Call<GroupMemberBean[]> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
        }

        moveBanList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), GroupBanList.class);
                intent.putExtra("admin", admin);
                intent.putExtra("groupCode", code);
                startActivity(intent);
            }
        });

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    } // onCreate
}
