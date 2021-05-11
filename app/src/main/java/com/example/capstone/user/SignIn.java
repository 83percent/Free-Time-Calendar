package com.example.capstone.user;

import android.content.Intent;
import android.os.AsyncTask;
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
import com.example.capstone.connect.Connect;
import com.example.capstone.connect.RetrofitConnection;
import com.example.capstone.data.ResponseBean;
import com.example.capstone.data.SignInBean;
import com.example.capstone.data.SignUpBean;
import com.example.capstone.lib.Regexp;

import org.json.JSONObject;

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

                try {
                    Log.d("Connect Test", "Send Start");
                    SignInBean bean = new SignInBean();
                    bean.setEmail(email);
                    bean.setPassword(password);
                    RetrofitConnection retrofitConnection = RetrofitConnection.getInstance();
                    Call<String> call = retrofitConnection.server.sendSignIn(bean);
                    call.enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.d("Connect Test", "income Status " + response.code());
                            Log.d("Connect Test", "income boolean " + response.isSuccessful());
                            Log.d("Connect Test", "income String " + response.body());
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("Connect Test", "연결 실패");
                            Log.d("Connect Test", t.getMessage());

                        }
                    });


                    /*SendRequest send = new SendRequest();
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("email",email);
                    jsonObject.put("password", password);
                    ResponseBean responseBean = (ResponseBean) send.execute(jsonObject).get();
                    Log.d("Connect Test", "get Status: " + responseBean.getStatus());
                    Log.d("Connect Test", "get Data: " + responseBean.getData());*/
                } catch(Exception e) {
                    e.printStackTrace();
                    Log.d("Connect Test", "onClick: "+false);
                }
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

    /* private class SendRequest extends AsyncTask {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected boolean doInBackground(Object[] objects) {
            if(objects[0] == null) return false;
            SignUpBean bean = null;
            boolean result = false;
            try {
                bean = (SignUpBean) objects[0];
                RetrofitConnection retrofitConnection = RetrofitConnection.getInstance();
                Call<String> call = retrofitConnection.server.sendSignin(bean);
                call.enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        Log.d("Connect Test", "income Status " + response.code());
                        Log.d("Connect Test", "income boolean " + response.isSuccessful());
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Log.d("Connect Test", "연결 실패");
                    }
                });
                return result;
            } catch(Exception e) {
                e.printStackTrace();
                return result;
            }

        }
    }*/
}