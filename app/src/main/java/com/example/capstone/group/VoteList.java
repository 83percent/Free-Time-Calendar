package com.example.capstone.group;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;
import com.example.capstone.bean.GroupVoteBean;
import com.example.capstone.connect.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoteList extends AppCompatActivity {
    private String groupCode;
    private ListView voteListView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_vote_list);

        voteListView = (ListView) findViewById(R.id.voteListView);

        Intent dataIntent = getIntent();
        if(dataIntent != null) {
            groupCode = dataIntent.getStringExtra("groupCode");

            RetrofitConnection retrofitConnection = RetrofitConnection.getInstance();
            Call<GroupVoteBean[]> call = retrofitConnection.server.getVoteList(groupCode);
            call.enqueue(new Callback<GroupVoteBean[]>() {
                @Override
                public void onResponse(Call<GroupVoteBean[]> call, Response<GroupVoteBean[]> response) {
                    if(response.code() == 200) {
                        final GroupVoteListAdapter adapter = new GroupVoteListAdapter(getApplicationContext(), response.body(), groupCode);
                        voteListView.setAdapter(adapter);
                        voteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent voteIntent = adapter.getItemIntent(position);
                                startActivity(voteIntent);
                            }
                        });
                    }
                }

                @Override
                public void onFailure(Call<GroupVoteBean[]> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "목록을 불러올 수 없습니다..", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
