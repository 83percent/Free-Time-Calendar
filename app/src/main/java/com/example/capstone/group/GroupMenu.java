package com.example.capstone.group;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone.R;
import com.example.capstone.bean.GroupMemberBean;
import com.example.capstone.bean.IDReturnBean;
import com.example.capstone.connect.RetrofitConnection;
import com.example.capstone.schedule.ScheduleListView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupMenu extends Fragment {
    // View
    private ListView memberListView;
    private LinearLayout outOfGroup, applierListBtn, voteListBtn, scheduleListBtn;
    private RetrofitConnection retrofitConnection = RetrofitConnection.getInstance();

    // Field
    String groupCode;
    private GroupMainCalendar activity;

    public GroupMenu(String groupCode) {
        this.groupCode = groupCode;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        activity = (GroupMainCalendar) getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // View
        final ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.group_menu, container, false);
        memberListView = (ListView) rootView.findViewById(R.id.memberListView);
        outOfGroup = (LinearLayout) rootView.findViewById(R.id.outOfGroup);
        voteListBtn = (LinearLayout) rootView.findViewById(R.id.voteListBtn);
        applierListBtn = (LinearLayout) rootView.findViewById(R.id.applierListBtn);
        scheduleListBtn = (LinearLayout) rootView.findViewById(R.id.scheduleListBtn);

        getGroupList(groupCode); // 참여자 목록 생성

        outOfGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  그룹에서 나가기
                Log.d("TAG", "보내려는 유저 정보 : " + activity.getUserId());
                IDReturnBean bean = new IDReturnBean(activity.getUserId());
                Call<Integer> call = retrofitConnection.server.deleteGroup(groupCode, bean);
                call.enqueue(new Callback<Integer>() {
                    @Override
                    public void onResponse(Call<Integer> call, Response<Integer> response) {
                        switch(response.code()) {
                            case 200 : {
                                Intent intent = new Intent();
                                intent.putExtra("isChange", true);
                                activity.setResult(200, intent);
                                activity.finish();
                                break;
                            }
                            case 404 :
                            case 500 :
                            default : {
                                Toast.makeText(getActivity(), "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Integer> call, Throwable t) {
                        Toast.makeText(getActivity(), "서버 오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
                        Log.d("문제", "onFailure: " + t);
                    }
                });
            }
        }); // OutOfGroup onClick
        voteListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), VoteList.class);
                intent.putExtra("groupCode", groupCode);
                startActivity(intent);
            }
        });
        applierListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = getActivity().getIntent();
                String admin = intent.getStringExtra("admin");

                intent = new Intent(getActivity(), GroupApplierList.class);
                intent.putExtra("admin", admin);
                intent.putExtra("groupCode", groupCode);
                startActivity(intent);
            }
        });
        scheduleListBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ScheduleListView.class);
                intent.putExtra("groupCode", activity.groupID);
                intent.putExtra("groupName", activity.groupName);
                startActivity(intent);
            }
        });
        return rootView;
    }

    private void getGroupList(String groupCode) {

        Call<GroupMemberBean[]> call = retrofitConnection.server.getGroupMember(groupCode);
        call.enqueue(new Callback<GroupMemberBean[]>() {
            @Override
            public void onResponse(Call<GroupMemberBean[]> call, Response<GroupMemberBean[]> response) {
                if(response.code() == 200) {
                    GroupMemberAdapter adapter = new GroupMemberAdapter(getActivity(), response.body());
                    memberListView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<GroupMemberBean[]> call, Throwable t) {
                Log.d("연결 요청", "onResponse: 실패" );
            }
        });
}
}
