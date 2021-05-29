package com.example.capstone.user;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import com.example.capstone.bean.SignInReturnBean;
import com.example.capstone.connect.RetrofitConnection;
import com.example.capstone.bean.SignInBean;
import com.example.capstone.lib.Regexp;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SignIn extends AppCompatActivity {
    // View
    private RelativeLayout signIn;
    private TextView createAccountBtn;
    private ImageButton back;
    private EditText inputEmail, inputPassword;

    // Field
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
                    if(password.length() > 7 && password.length() < 21) {
                        send(email, password);
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
    private void send(final String email,String password) {
        SignInBean bean = new SignInBean(email, password);
        RetrofitConnection retrofitConnection = RetrofitConnection.getInstance();
        Call<SignInReturnBean> call = retrofitConnection.server.sendSignIn(bean);
        call.enqueue(new Callback<SignInReturnBean>() {
            @Override
            public void onResponse(Call<SignInReturnBean> call, Response<SignInReturnBean> response) {
                switch (response.code()) {
                    case 200 : {
                        SignInReturnBean bean = response.body();
                        SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);

                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("id", bean.get_id());
                        editor.putString("name", bean.getName());
                        editor.putString("email", email);
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), MainBaseActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);

                        break;
                    }
                    case 401 : {
                        Toast.makeText(SignIn.this, "이메일 또는 비밀번호를 확인해주세요.", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    default : { Toast.makeText(SignIn.this, "(D)잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show(); }
                }
            }

            @Override
            public void onFailure(Call<SignInReturnBean> call, Throwable t) {
                Toast.makeText(SignIn.this, "(F)잠시 후 다시 시도해주세요.", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }
}