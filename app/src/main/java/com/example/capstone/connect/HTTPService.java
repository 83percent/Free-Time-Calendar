package com.example.capstone.connect;

import com.example.capstone.data.SignInBean;
import com.example.capstone.data.SignUpBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface HTTPService {
    @POST("/user")
    Call<String> sendSignIn(@Body SignInBean bean);

    @POST("/user/sign")
    Call<String> sendSignUp(@Body SignUpBean bean);
}
