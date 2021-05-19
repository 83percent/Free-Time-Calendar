package com.example.capstone.group;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone.R;
import com.example.capstone.bean.GroupMemberBean;
import com.example.capstone.connect.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupMenu extends Fragment {
    String groupCode;
    private ListView memberListView;
    public GroupMenu(String groupCode) {
        this.groupCode = groupCode;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.group_menu, container, false);
        memberListView = (ListView) rootView.findViewById(R.id.memberListView);
        getGroupList(groupCode);
        return rootView;
    }

    private void getGroupList(String groupCode) {
        RetrofitConnection retrofitConnection = RetrofitConnection.getInstance();
        Call<GroupMemberBean[]> call = retrofitConnection.server.getGroupMember(groupCode);
        Toast.makeText(getActivity(), "요청코드 : " + groupCode, Toast.LENGTH_SHORT).show();
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
