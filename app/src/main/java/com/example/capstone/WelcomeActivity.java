package com.example.capstone;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.capstone.bean.NotificationBean;
import com.example.capstone.connect.RetrofitConnection;
import com.example.capstone.data.DataManager;
import com.example.capstone.data.NotificationData;
import com.example.capstone.user.SignIn;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WelcomeActivity extends AppCompatActivity {
    // View
    private RelativeLayout goLoginBtn;
    private FrameLayout btnWrapper;

    // Field
    private long backKeyPressedTime = 0;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnWrapper = (FrameLayout) findViewById(R.id.btnWrapper);
        goLoginBtn = (RelativeLayout) findViewById(R.id.welcomeLoginBtn);

        DataManager dataManager = new DataManager(getApplicationContext());
        dataManager.getReadableDatabase();
        dataManager.close();

        goLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        final SharedPreferences pref = getSharedPreferences("FreeTime",MODE_PRIVATE);
        final String id = pref.getString("id", null);

        if(id != null) {
            RetrofitConnection retrofitConnection = RetrofitConnection.getInstance();
            Call<NotificationBean[]> call = retrofitConnection.server.getNotification(id);
            call.enqueue(new Callback<NotificationBean[]>() {
                @Override
                public void onResponse(Call<NotificationBean[]> call, Response<NotificationBean[]> response) {
                    if(response.code() == 200 && response.body().length > 0) {
                        synchronized (this) {
                            NotificationBean[] beans = response.body();
                            NotificationData data = NotificationData.getInstance(getApplicationContext());
                            data.set(id, response.body());

                            SharedPreferences.Editor editor = pref.edit();
                            if(pref.getInt("newCount", 0) == 0) {
                                editor.putInt("newCount", response.body().length);
                            } else {
                                editor.putInt("newCount", pref.getInt("newCount", 0)+ response.body().length);
                            }
                            editor.commit();

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    Intent intent = new Intent(getApplicationContext(), MainBaseActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent);
                                }
                            }, 1000);
                        }

                    } else {
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), MainBaseActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }, 1000);
                    }
                }

                @Override
                public void onFailure(Call<NotificationBean[]> call, Throwable t) {
                    Toast.makeText(WelcomeActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    t.printStackTrace();
                    /*
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(getApplicationContext(), MainBaseActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    }, 1000);
                     */
                }
            });

        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Animation anim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.main_slide_up);
                    btnWrapper.startAnimation(anim);
                    btnWrapper.setVisibility(View.VISIBLE);
                }
            }, 1500);
        }
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2500) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "뒤로 가기 버튼을 한 번 더 누르시면 종료됩니다.", Toast.LENGTH_LONG).show();
            return;
        }
        if (System.currentTimeMillis() <= backKeyPressedTime + 2500) { finish(); }
    }
}
