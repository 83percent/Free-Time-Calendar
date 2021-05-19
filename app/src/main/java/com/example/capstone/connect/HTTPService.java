package com.example.capstone.connect;

import com.example.capstone.bean.CreateGroupBean;
import com.example.capstone.bean.GroupListBean;
import com.example.capstone.bean.GroupMemberBean;
import com.example.capstone.bean.SignInBean;
import com.example.capstone.bean.SignUpBean;
import com.example.capstone.bean.TimeBean;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HTTPService {
    @POST("/user")
    Call<String> sendSignIn(@Body SignInBean bean);

    @POST("/user/sign")
    Call<String> sendSignUp(@Body SignUpBean bean);

    @POST("/free/{id}")
    Call<String> sendFree(@Path("id") String id, @Body TimeBean bean);

    @POST("/group")
    Call<String> createGroup(@Body CreateGroupBean bean);

    @GET("/group/list/{id}")
    Call<GroupListBean[]> getGroupList(@Path("id") String id);

    @GET("group/member/{GroupCode}")
    Call<GroupMemberBean[]> getGroupMember(@Path("GroupCode") String code);
}
