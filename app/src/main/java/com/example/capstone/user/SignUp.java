package com.example.capstone.user;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.R;
import com.example.capstone.data.SignUpBean;
import com.example.capstone.lib.Regexp;

public class SignUp extends AppCompatActivity {
    private RelativeLayout signUp;
    private ImageButton back;
    private EditText inputEmail, inputPassword, inputRePassword, inputName;

    private Regexp regexp = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        inputEmail = (EditText) findViewById(R.id.inputEmail);
        inputPassword = (EditText) findViewById(R.id.inputPassword);
        inputRePassword = (EditText) findViewById(R.id.inputRePassword);
        inputName = (EditText) findViewById(R.id.inputName);
        signUp = (RelativeLayout) findViewById(R.id.signUpBtn);
        back = (ImageButton) findViewById(R.id.signUpBack);

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(regexp == null) regexp = new Regexp();
                String email = inputEmail.getText().toString();
                String password = inputPassword.getText().toString();
                String rePassword = inputRePassword.getText().toString();
                String name = inputName.getText().toString();

                if(!regexp.email(email)) {
                    Toast.makeText(SignUp.this, "이메일 형식이 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!regexp.password(password)) {
                    Toast.makeText(SignUp.this, "비밀번호는 8~20자로 작성해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!password.equals(rePassword) ) {
                    Toast.makeText(SignUp.this, "비밀번호가 일치하지 않습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(name.length() < 2) {
                    Toast.makeText(SignUp.this, "이름이 너무 짧습니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(name.length() > 10) {
                    Toast.makeText(SignUp.this, "이름이 너무 깁니다", Toast.LENGTH_SHORT).show();
                    return;
                }
                SignUpBean bean = new SignUpBean();
                bean.setEmail(email);
                bean.setPasssword(password);
                bean.setName(name);

                /*

                    TODO : Send "sign up" request

                */
                finish();
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
