package com.solution.naukarimanthan.data.remote;

import com.solution.naukarimanthan.data.model.apis.action.Subscription;
import com.solution.naukarimanthan.data.model.apis.adgebraads.AdgebraViewModel;
import com.solution.naukarimanthan.data.model.apis.flag.FlagApi;
import com.solution.naukarimanthan.data.model.apis.job$item.JobItemLong;
import com.solution.naukarimanthan.data.model.apis.job$item.JobItemShort;
import com.solution.naukarimanthan.data.model.apis.jobs.JobsResponse;
import com.solution.naukarimanthan.data.model.apis.jobs.tabs.JobsMenuTabsResponse;
import com.solution.naukarimanthan.data.model.apis.splash.AppVersionCheck;
import com.solution.naukarimanthan.data.model.apis.states$city.ResponseStateCity;
import com.solution.naukarimanthan.data.model.db.test.PostTest;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 */
public interface APIService {

    @GET("/Api/appversion")
    Single<AppVersionCheck> appVersionCheck(
    );

    @GET("/api/MobileApi/GetCountry")
    Flowable<FlagApi> countryCode();

    @GET("/Api/getCategory")
    Flowable<JobsMenuTabsResponse> jobTabsMenu();

    @GET("/Api/CategoryWiseJobs?")
    Flowable<JobsResponse> loadJobsFromApi(
            @Query("pageno") int pageNo,
            @Query("categoryId") int menuId
    );

    @GET("/Api/getstates")
    Flowable<ResponseStateCity> getAreaCodeApi();

    @GET("/Api/getJobDetail?")
    Flowable<JobsResponse> recentJobsFromApi(
            @Query("pageno") int pageNo
    );

    @GET("/Api/top10jobs?")
    Flowable<JobsResponse> getRecommendedJobs(
            @Query("pageno") int pageNo,
            @Query("cat1") int menuIdFirst,
            @Query("cat2") int menuIdSecond,
            @Query("cat3") int menuIdThird
    );

    @GET("/Api/statewiseJobs?")
    Flowable<JobsResponse> getLocalJobs(
            @Query("StateId") int stateId,
            @Query("pageNo") int pageNo
    );

    @GET("/Api/getJobDetail?")
    Flowable<JobsResponse> getPopularJobs(
            @Query("pageNo") int pageNo
    );

    @GET("/Api/shorDescApi?")
    Flowable<JobItemShort> getJobsItemShort(
            @Query("jobId") int jobId
    );

    @GET("/Api/LongDescApi?")
    Flowable<JobItemLong> getJobsItemLong(
            @Query("jobdetailid") int jobId,
            @Query("shortdescid") int shortDescriptionId
    );


    @FormUrlEncoded
    @POST("/Api/insertSubscribers")
    Flowable<Subscription> subscriptionApi(
            @Field("userName") String userName,
            @Field("userMobile") String userMobile,
            @Field("userEmail") String userEmail,
            @Field("fieldOfInterest") String fieldOfInterest
    );

    @GET("/AdServing/PushNativeAds?")
    Flowable<List<AdgebraViewModel>> getAdgebraAdsApi(
            @Query("pid") String pid,
            @Query("dcid") String dcid,
            @Query("uid") String uid,
            @Query("nads") String nads,
            @Query("deviceId") String deviceId,
            @Query("ip") String ip,
            @Query("url") String url,
            @Query("pnToken") String pnToken
    );

    @GET("GetIp.aspx")
    Single<String> getIpAddressApi();

//    @GET("GetIp.aspx")
//    Call<String> getIpAddressApiTmp();

    @FormUrlEncoded
    @POST("/GetIp.aspx")
    Call<String> getIpAddressApiTmp();


    @GET("/posts")
    Flowable<List<PostTest>> getPostTest();
}
