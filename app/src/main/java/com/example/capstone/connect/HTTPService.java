package com.example.capstone.connect;

import com.example.capstone.bean.CreateGroupBean;
import com.example.capstone.bean.GroupListBean;
import com.example.capstone.bean.GroupMemberBean;
import com.example.capstone.bean.IDReturnBean;
import com.example.capstone.bean.SignInBean;
import com.example.capstone.bean.SignUpBean;
import com.example.capstone.bean.TimeBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HTTPService {
    @POST("/user")
    Call<String> sendSignIn(@Body SignInBean bean);

    @POST("/user/sign")
    Call<String> sendSignUp(@Body SignUpBean bean);

    @POST("/free/{id}")
    Call<String> sendFree(@Path("id") String id, @Body TimeBean bean);

    // 그룹생성
    @POST("/group")
    Call<String> createGroup(@Body CreateGroupBean bean);

    // 그룹 나가기
    @HTTP(method= "DELETE", path="/group/{GroupCode}", hasBody= true)
    Call<Integer> deleteGroup(@Path("GroupCode") String code, @Body IDReturnBean bean);

    // 그룹 목록 받아오기
    @GET("/group/list/{id}")
    Call<GroupListBean[]> getGroupList(@Path("id") String id);

    // 그룹 참여자 목록 받기
    @GET("/group/member/{GroupCode}")
    Call<GroupMemberBean[]> getGroupMember(@Path("GroupCode") String code);

    // 그룹 참여 신청
    @POST("/group/apply/{GroupCode}")
    Call<Integer> sendApplyGroup(@Path("GroupCode") String code, @Body IDReturnBean bean);

    // 그룹 admin 가져오기
    @GET("/group/apply/list/{GroupCode}")
    Call<GroupMemberBean[]> getApplierList(@Path("GroupCode") String code);
}
