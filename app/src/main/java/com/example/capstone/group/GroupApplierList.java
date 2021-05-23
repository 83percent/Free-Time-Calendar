package com.example.capstone.group;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;
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

    // Field
    private String admin, code, id;
    private RetrofitConnection retrofitConnection = null;
    private GroupMemberBean[] listBeans;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_apply_list);

        backBtn = (ImageButton) findViewById(R.id.backBtn);
        listView = (ListView) findViewById(R.id.applyListView);


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
                            GroupApplierListAdapter adapter = new GroupApplierListAdapter(getApplicationContext(), response.body(),  id.equals(admin), code);
                            listView.setAdapter(adapter);
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

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    } // onCreate
}
