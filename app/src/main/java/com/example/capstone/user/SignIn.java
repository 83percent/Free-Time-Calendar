package com.example.capstone.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.MainBaseActivity;
import com.example.capstone.R;
import com.example.capstone.lib.Regexp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignIn extends AppCompatActivity {
    private RelativeLayout signIn;
    private TextView createAccountBtn;
    private ImageButton back;
    private EditText inputEmail, inputPassword;
    private Regexp regexp = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);

        signIn = (RelativeLayout) findViewById(R.id.signInBtn);
        createAccountBtn = (TextView) findViewById(R.id.createAccountBtn);
        back = (ImageButton) findViewById(R.id.signInBack);
        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);

        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(regexp == null) regexp = new Regexp();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                if(regexp.email(email)) {
                    Log.d("abs", ""+email);
                    if(password.length() > 7 && password.length() < 21) {
                        /*
                            TODO : Send Request
                         */
                        Intent intent = new Intent(getApplicationContext(), MainBaseActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } else { Toast.makeText(SignIn.this, "비밀번호 8~20자리를 입력해주세요",Toast.LENGTH_SHORT).show(); } // Wrong Password
                } else { Toast.makeText(SignIn.this, "이메일을 확인해주세요", Toast.LENGTH_SHORT).show(); } // Wrong Email
            }
        });
        createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
