package com.example.capstone.group;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.example.capstone.R;

public class InputInvitation extends Activity {
    private ImageButton back;
    private RelativeLayout sendBtn;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_input_invitation);
        sendBtn = (RelativeLayout) findViewById(R.id.sendInvitationRequestBtn);
        back = (ImageButton) findViewById(R.id.invitationCodeBack);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), SuccessEnterInvitation.class);
                startActivity(intent);
            }
        });

    }
}
