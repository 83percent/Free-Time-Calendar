package com.example.capstone.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.capstone.R;

public class SuccessCreateGroup extends Activity {

    private LinearLayout back;
    private TextView invitationCode, groupNameFrame;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_success_new_group);

        invitationCode = (TextView) findViewById(R.id.invitationCode);
        groupNameFrame = (TextView) findViewById(R.id.groupNameFrame);
        back = (LinearLayout) findViewById(R.id.successNewGroupBack);

        Intent dataIntent = getIntent();
        if(dataIntent != null) {
            final String code = dataIntent.getStringExtra("groupCode");
            String name = dataIntent.getStringExtra("groupName");
            if(code != null) {
                invitationCode.setText(code);
            }
            if(name != null) groupNameFrame.setText(name);
            invitationCode.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", code);
                    clipboard.setPrimaryClip(clip);

                    Toast.makeText(SuccessCreateGroup.this, "복사되었습니다", Toast.LENGTH_SHORT).show();
                }
            });
        }






        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
