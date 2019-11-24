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

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Author       : Arvindo Mondal
 * Created on   : 24-12-2018
 * Email        : arvindomondal@gmail.com
 */
public class APIs implements APIService {

    private final APIService apiService;
    private final APIService apiServiceForIP;
    private final APIService apiServiceAdgebra;
    private final APIService apiServiceUtils;

    @Inject
//    public APIs(Retrofit retrofit, Retrofit retrofitIp) {
    public APIs(@Named("APP") Retrofit retrofit, @Named("IPAddress") Retrofit retrofitIp,
                @Named("Adgebra") Retrofit adgebra, @Named("UTILS") Retrofit retrofitUtils) {
        this.apiService = retrofit.create(APIService.class);
        this.apiServiceForIP = retrofitIp.create(APIService.class);
        this.apiServiceAdgebra = adgebra.create(APIService.class);
        this.apiServiceUtils = retrofitUtils.create(APIService.class);
    }

    @Override
    public Single<AppVersionCheck> appVersionCheck() {
        return apiService.appVersionCheck();
    }

    @Override
    public Flowable<FlagApi> countryCode() {
        return apiService.countryCode();
    }

    @Override
    public Flowable<JobsMenuTabsResponse> jobTabsMenu() {
        return apiService.jobTabsMenu();
    }

    @Override
    public Flowable<JobsResponse> loadJobsFromApi(int pageNo, int menuId) {
        return apiService.loadJobsFromApi(pageNo, menuId);
    }

    @Override
    public Flowable<ResponseStateCity> getAreaCodeApi() {
        return apiService.getAreaCodeApi();
    }

    @Override
    public Flowable<JobsResponse> recentJobsFromApi(int pageNo) {
        return apiService.recentJobsFromApi(pageNo);
    }

    @Override
    public Flowable<JobsResponse> getRecommendedJobs(int pageNo, int menuIdFirst, int menuIdSecond,
                                                     int menuIdThird) {
        return apiService.getRecommendedJobs(pageNo, menuIdFirst, menuIdSecond, menuIdThird);
    }

    @Override
    public Flowable<JobsResponse> getLocalJobs(int stateId, int pageNo) {
        return apiService.getLocalJobs(stateId, pageNo);
    }

    @Override
    public Flowable<JobsResponse> getPopularJobs(int pageNo) {
        return apiService.getPopularJobs(pageNo);
    }

    @Override
    public Flowable<JobItemShort> getJobsItemShort(int jobId) {
        return apiService.getJobsItemShort(jobId);
    }

    @Override
    public Flowable<JobItemLong> getJobsItemLong(int jobId, int shortDescriptionId) {
        return apiService.getJobsItemLong(jobId, shortDescriptionId);
    }

    @Override
    public Flowable<Subscription> subscriptionApi(String userName, String userMobile, String userEmail,
                                                  String fieldOfInterest) {
        return apiService.subscriptionApi(userName, userMobile, userEmail, fieldOfInterest);
    }

    @Override
    public Flowable<List<AdgebraViewModel>> getAdgebraAdsApi(String pid, String dcid, String uid, String nads,
                                                          String deviceId, String ip, String url, String pnToken) {
        return apiServiceAdgebra.getAdgebraAdsApi(pid, dcid, uid, nads, deviceId, ip, url, pnToken);
    }

    @Override
    public Single<String> getIpAddressApi() {
        return apiServiceForIP.getIpAddressApi();
    }

    @Override
    public Call<String> getIpAddressApiTmp() {
        return apiServiceForIP.getIpAddressApiTmp();
    }

    @Override
    public Flowable<List<PostTest>> getPostTest() {
        return apiServiceUtils.getPostTest();
    }
}
