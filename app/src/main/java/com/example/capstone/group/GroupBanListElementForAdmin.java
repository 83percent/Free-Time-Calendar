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

public class GroupBanListElementForAdmin extends LinearLayout {
    private TextView name;
    private RetrofitConnection retrofitConnection = null;
    private Context context;
    private LinearLayout frame;
    public GroupBanListElementForAdmin(Context context, final String id, final String groupCode) {
        super(context);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.group_ban_element_admin, this, true);


        this.context = context;
        frame = this;
        this.name = (TextView) findViewById(R.id.name);
    }
    public void setName(String name) { this.name.setText(name);}
}
