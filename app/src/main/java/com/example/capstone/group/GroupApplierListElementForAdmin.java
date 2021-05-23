package com.example.capstone.group;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.capstone.R;
import com.example.capstone.bean.IDReturnBean;
import com.example.capstone.connect.RetrofitConnection;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GroupApplierListElementForAdmin extends LinearLayout {
    private TextView name;
    private ImageButton acceptBtn, rejectBtn;
    private RetrofitConnection retrofitConnection = null;
    private Context context;
    private LinearLayout frame;
    public GroupApplierListElementForAdmin(Context context,final String id, final String groupCode) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.group_applier_element_admin, this, true);


        this.context = context;
        frame = this;
        this.name = (TextView) findViewById(R.id.name);
        this.acceptBtn = (ImageButton) findViewById(R.id.acceptBtn);
        this.rejectBtn = (ImageButton) findViewById(R.id.rejectBtn);

        this.acceptBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                controlApplier(groupCode, id, true, frame);
            }
        });

        this.rejectBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                controlApplier(groupCode, id, false, frame);
            }
        });
    }
    public void setName(String name) { this.name.setText(name);}

    private void controlApplier(String groupCode, String id, boolean isAccept, final LinearLayout frame) {
        if(retrofitConnection == null) retrofitConnection = RetrofitConnection.getInstance();
        Call<Integer> call = isAccept ? retrofitConnection.server.acceptApplier(groupCode, new IDReturnBean(id)) : retrofitConnection.server.rejectApplier(groupCode, new IDReturnBean(id));
        call.enqueue(new Callback<Integer>() {
            @Override
            public void onResponse(Call<Integer> call, Response<Integer> response) {
                if(response.code() == 200 && response.body() == 1) {
                    frame.removeAllViews();
                }
            }

            @Override
            public void onFailure(Call<Integer> call, Throwable t) {
                Toast.makeText(context, "오류가 발생했습니다.", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
