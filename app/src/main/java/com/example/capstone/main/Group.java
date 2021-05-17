package com.example.capstone.main;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.capstone.MainBaseActivity;
import com.example.capstone.R;
import com.example.capstone.bean.GroupListBean;
import com.example.capstone.connect.RetrofitConnection;
import com.example.capstone.group.AddNewGroup;
import com.example.capstone.group.GroupListAdapter;
import com.example.capstone.group.InputInvitation;
import com.example.capstone.group.SuccessCreateGroup;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Group extends Fragment {
    // View
    private LinearLayout groupAddOptionWrapper;
    private LinearLayout groupAddOptionCloser;
    private RelativeLayout invitationBtn;
    private RelativeLayout newGroupBtn;
    private ListView groupListView;
    MainBaseActivity activity;
    ImageButton addBtn;

    // Field
    private GroupListAdapter adapter;

    public boolean isOptionOpen = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (MainBaseActivity) getActivity();
}
    @Override
    public void onDetach() {
        super.onDetach();
        activity = null;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.main_group, container, false);
        addBtn = (ImageButton) rootView.findViewById(R.id.groupAddBtn);
        groupAddOptionWrapper = (LinearLayout) activity.findViewById(R.id.addGroupWrapper);
        groupAddOptionCloser = (LinearLayout) activity.findViewById(R.id.addGroupWrapperCloser);
        invitationBtn = (RelativeLayout) activity.findViewById(R.id.invitationCodeBtn);
        newGroupBtn = (RelativeLayout) activity.findViewById(R.id.newGroupBtn);
        groupListView = (ListView) rootView.findViewById(R.id.groupListView);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAddGroupOption(true);
            }
        });

        // Group Add Option
        groupAddOptionCloser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAddGroupOption(false);
            }
        });
        invitationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAddGroupOption(false);
                Intent intent = new Intent(activity.getApplicationContext(), InputInvitation.class);
                startActivity(intent);
            }
        });
        newGroupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleAddGroupOption(false);
                Intent intent = new Intent(activity.getApplicationContext(), AddNewGroup.class);
                startActivityForResult(intent, 8081);
            }
        });
        groupListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent groupIntent = adapter.getItemIntent(position);
                startActivity(groupIntent);
            }
        });

        createGroupList();
        return rootView;
    }
    public void toggleAddGroupOption(boolean toggle) {
        if (toggle) {
            if (groupAddOptionWrapper.getVisibility() == View.GONE)
                groupAddOptionWrapper.setVisibility(View.VISIBLE);
        } else {
            if (groupAddOptionWrapper.getVisibility() == View.VISIBLE)
                groupAddOptionWrapper.setVisibility(View.GONE);
        }
        isOptionOpen = toggle;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 8081 && resultCode == activity.RESULT_OK) {
            if(data.getBooleanExtra("isCreate", false)) {
                String createGroupCode = data.getStringExtra("groupCode");
                if(createGroupCode != null) {
                    Intent intent = new Intent(activity.getApplicationContext(), SuccessCreateGroup.class);
                    intent.putExtra("groupCode",createGroupCode);
                    startActivity(intent);
                }
            }
        }
    }

    // @param String id 사용자 고유 id
    private void createGroupList() {
        RetrofitConnection retrofitConnection = RetrofitConnection.getInstance();
        Call<GroupListBean[]> call = retrofitConnection.server.getGroupList(activity.getID());
        call.enqueue(new Callback<GroupListBean[]>() {
            @Override
            public void onResponse(Call<GroupListBean[]> call, Response<GroupListBean[]> response) {
                if(response.code() == 200) {
                    GroupListBean[] beans = response.body();

                    adapter = new GroupListAdapter(getActivity(), beans);
                    Log.d("List View", "adapter : " + adapter.getCount());
                    Log.d("List View", "list view : " + groupListView);
                    groupListView.setAdapter(adapter);
                } else {

                }
            }

            @Override
            public void onFailure(Call<GroupListBean[]> call, Throwable t) {
                Toast.makeText(activity.getApplicationContext(), "목록을 불러올 수 없습니다.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });

    }
}
