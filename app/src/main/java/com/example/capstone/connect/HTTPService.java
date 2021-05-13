package com.example.capstone.connect;

import com.example.capstone.bean.SignInBean;
import com.example.capstone.bean.SignUpBean;
import com.example.capstone.bean.TimeBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HTTPService {
    @POST("/user")
    Call<String> sendSignIn(@Body SignInBean bean);

    @POST("/user/sign")
    Call<String> sendSignUp(@Body SignUpBean bean);

    @POST("/free/{id}")
    Call<String> sendFree(@Path("id") String id, @Body TimeBean bean);
}
