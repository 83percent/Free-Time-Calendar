package com.example.capstone.connect;

import com.example.capstone.bean.CreateGroupBean;
import com.example.capstone.bean.GroupAdminControlBean;
import com.example.capstone.bean.GroupFreeBean;
import com.example.capstone.bean.GroupListBean;
import com.example.capstone.bean.GroupMemberBean;
import com.example.capstone.bean.GroupScheduleBean;
import com.example.capstone.bean.GroupVoteBean;
import com.example.capstone.bean.IDReturnBean;
import com.example.capstone.bean.RequestDateBean;
import com.example.capstone.bean.SignInBean;
import com.example.capstone.bean.SignInReturnBean;
import com.example.capstone.bean.SignUpBean;
import com.example.capstone.bean.TimeBean;
import com.example.capstone.bean.VoteBean;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface HTTPService {
    @POST("/user")
    Call<SignInReturnBean> sendSignIn(@Body SignInBean bean);

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

    // 그룹 지원자 목록 가져오기
    @GET("/group/apply/list/{GroupCode}")
    Call<GroupMemberBean[]> getApplierList(@Path("GroupCode") String code);

    @POST("/group/apply/list/{GroupCode}")
    Call<Integer> acceptApplier(@Path("GroupCode") String code, @Body IDReturnBean bean);

    @HTTP(method= "DELETE", path="/group/apply/list/{GroupCode}", hasBody= true)
    Call<Integer> rejectApplier(@Path("GroupCode") String code, @Body IDReturnBean bean);

    /*
        ====================
                ban
        ====================
     */

    @GET("/group/ban/list/{GroupCode}")
    Call<GroupMemberBean[]> getBanList(@Path("GroupCode") String code);

    @POST("/group/ban/list/{GroupCode}")
    Call<Integer> addBan(@Path("GroupCode") String code, @Body IDReturnBean bean);

    @HTTP(method= "DELETE", path="/group/ban/list/{GroupCode}", hasBody= true)
    Call<Integer> deleteBan(@Path("GroupCode") String code, @Body IDReturnBean bean);

    /*
        ====================
           Group Free & Schedule
        ====================
     */
    @POST("/group/free/{GroupCode}")
    Call<GroupFreeBean[]> getGroupFree(@Path("GroupCode")String code, @Body RequestDateBean bean);

    @GET("/group/vote/{GroupCode}")
    Call<GroupVoteBean[]> getVoteList(@Path("GroupCode")String code);

    @POST("/group/vote/{GroupCode}")
    Call<GroupVoteBean> addVote(@Path("GroupCode")String code, @Body GroupVoteBean bean);

    // 그룹 스케줄 목록 불러오기
    @GET("/group/schedule/{GroupCode}")
    Call<GroupScheduleBean[]> getGroupSchedule(@Path("GroupCode")String code);

    @POST("/vote/{VoteCode}")
    Call<Boolean> sendVote(@Path("VoteCode") String code, @Body VoteBean bean);

    @POST("/vote/complete/{VoteCode}")
    Call<GroupVoteBean> sendCompleteVote(@Path("VoteCode")String code);
}
