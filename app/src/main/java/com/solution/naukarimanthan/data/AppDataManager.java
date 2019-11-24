package com.solution.naukarimanthan.data;


import android.content.Context;

import com.google.gson.Gson;
import com.solution.naukarimanthan.data.local.db.DatabaseService;
import com.solution.naukarimanthan.data.local.prefs.PreferencesService;
import com.solution.naukarimanthan.data.model.apis.action.Subscription;
import com.solution.naukarimanthan.data.model.apis.adgebraads.AdgebraViewModel;
import com.solution.naukarimanthan.data.model.apis.flag.FlagApi;
import com.solution.naukarimanthan.data.model.apis.job$item.JobItemLong;
import com.solution.naukarimanthan.data.model.apis.job$item.JobItemShort;
import com.solution.naukarimanthan.data.model.apis.jobs.JobsResponse;
import com.solution.naukarimanthan.data.model.apis.jobs.tabs.JobsMenuTabsResponse;
import com.solution.naukarimanthan.data.model.apis.splash.AppVersionCheck;
import com.solution.naukarimanthan.data.model.apis.states$city.ResponseStateCity;
import com.solution.naukarimanthan.data.model.db.Flag;
import com.solution.naukarimanthan.data.model.db.job$item.JobItem;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.data.model.db.notification.Notification;
import com.solution.naukarimanthan.data.model.db.search.SearchTable;
import com.solution.naukarimanthan.data.model.db.states$city.StatesAndCity;
import com.solution.naukarimanthan.data.model.db.test.PostTest;
import com.solution.naukarimanthan.data.remote.APIService;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;

import static com.solution.naukarimanthan.utils.AppConstants.JOBS_BY_MENU_ID;
import static com.solution.naukarimanthan.utils.AppConstants.LOCAL_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.POPULAR_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.RECENT_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.RECOMMENDED_JOBS;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
@Singleton
public class AppDataManager implements DataManager {

    private final APIService apiService;
    private final Context context;
    private final DatabaseService dbService;
    private final Gson gson;
    private final PreferencesService pref;

    @Inject
    public AppDataManager(Context context, PreferencesService preferencesService,
                          APIService apiHelper, DatabaseService dbService, Gson gson) {
        this.context = context;
        pref = preferencesService;
        this.dbService = dbService;
        this.gson = gson;
        this.apiService = apiHelper;
    }

    @Override
    public Flowable<List<JobsData>> getSpecialJobsFromDb(int jobsId, int pageNo, int menuId) {
        switch (jobsId){
            case RECENT_JOBS: return getRecentJobs();
            case RECOMMENDED_JOBS: return getRecommendedJobs();
            case POPULAR_JOBS: return getPopularJobs();
            case LOCAL_JOBS: return getLocalJobs();
            case JOBS_BY_MENU_ID: return getJobsDataById(menuId);
        }
        return getRecentJobs();
    }

    @Override
    public void updateUserInfo(String accessToken, Long userId, LoggedInMode loggedInMode,
                               String userName, String email, String profilePicPath) {
    }


    //Preferences----------------------------
    @Override
    public int getCurrentUserLoggedInMode() {
        return pref.getCurrentUserLoggedInMode();
    }

    @Override
    public void setCurrentUserLoggedInMode(LoggedInMode mode) {
        pref.setCurrentUserLoggedInMode(mode);
    }

    @Override
    public void setMenuCount(int menuCount) {
        pref.setMenuCount(menuCount);
    }

    @Override
    public int getMenuCount() {
        return pref.getMenuCount();
    }

    @Override
    public void setStateCode(int code) {
        pref.setStateCode(code);
    }

    @Override
    public int getStateCode() {
        return pref.getStateCode();
    }

    @Override
    public void setAppDate(String date) {
        pref.setAppDate(date);
    }

    @Override
    public String getAppDate() {
        return pref.getAppDate();
    }

    @Override
    public String getStateName() {
        return pref.getStateName();
    }

    @Override
    public void setStateName(String stateName) {
        pref.setStateName(stateName);
    }

    @Override
    public void setFirebaseAppId(String key) {
        pref.setFirebaseAppId(key);
    }

    @Override
    public String getFirebaseAppId() {
        return pref.getFirebaseAppId();
    }

    @Override
    public String getIpAddress() {
        return pref.getIpAddress();
    }

    @Override
    public void setIpAddress(String ipAddress) {
        pref.setIpAddress(ipAddress);
    }

    @Override
    public String getUniqueId() {
        return pref.getUniqueId();
    }

    @Override
    public void setUniqueId(String uniqueId) {
        pref.setUniqueId(uniqueId);
    }


    //Network----------------------------------

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
    public Flowable<JobsResponse> recentJobsFromApi(int pageNo) {
        return apiService.recentJobsFromApi(pageNo);
    }

    @Override
    public Flowable<JobsResponse> getRecommendedJobs(int pageNo, int menuIdFirst, int menuIdSecond, int menuIdThird) {
        return apiService.getRecommendedJobs(pageNo, menuIdFirst, menuIdSecond, menuIdThird);
    }

    @Override
    public Flowable<ResponseStateCity> getAreaCodeApi() {
        return apiService.getAreaCodeApi();
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
        return apiService.getAdgebraAdsApi(pid, dcid, uid, nads, deviceId, ip, url, pnToken);
    }

    @Override
    public Single<String> getIpAddressApi() {
        return apiService.getIpAddressApi();
    }

    @Override
    public Call<String> getIpAddressApiTmp() {
        return apiService.getIpAddressApiTmp();
    }

    @Override
    public Flowable<List<PostTest>> getPostTest() {
        return apiService.getPostTest();
    }

    //Database---------------------------------

    @Override
    public Flowable<List<Flag>> getAllFlags() {
        return dbService.getAllFlags();
    }

    @Override
    public Flowable<Boolean> saveFlagsList(List<Flag> flagList) {
        return dbService.saveFlagsList(flagList);
    }

    @Override
    public Flowable<List<JobsMenuTabs>> getAllJobMenus() {
        return dbService.getAllJobMenus();
    }

    @Override
    public Flowable<List<JobsMenuTabs>> getTop3MenuId() {
        return dbService.getTop3MenuId();
    }

    @Override
    public Flowable<Boolean> updateMenuPriorityByMenuId(int menuId) {
        return dbService.updateMenuPriorityByMenuId(menuId);
    }

    @Override
    public Flowable<Boolean> saveJobMenusList(List<JobsMenuTabs> jobsMenuTabs) {
        return dbService.saveJobMenusList(jobsMenuTabs);
    }

    @Override
    public Flowable<Boolean> saveJobsData(List<JobsData> jobsDataList) {
        return dbService.saveJobsData(jobsDataList);
    }

    @Override
    public Flowable<Boolean> saveStateCityList(List<StatesAndCity> statesAndCityList) {
        return dbService.saveStateCityList(statesAndCityList);
    }

    @Override
    public Flowable<List<JobsData>> getJobsData() {
        return dbService.getJobsData();
    }

    @Override
    public Flowable<List<JobsData>> getJobsDataById(int menuId) {
        return dbService.getJobsDataById(menuId);
    }

    @Override
    public Flowable<List<JobsData>> getRecentJobs() {
        return dbService.getRecentJobs();
    }

    @Override
    public Flowable<List<JobsData>> getRecommendedJobs() {
        return dbService.getRecommendedJobs();
    }

    @Override
    public Flowable<List<JobsData>> getLocalJobs() {
        return dbService.getLocalJobs();
    }

    @Override
    public Flowable<List<JobsData>> getPopularJobs() {
        return dbService.getPopularJobs();
    }

    @Override
    public Flowable<List<StatesAndCity>> getAllStateCity() {
        return dbService.getAllStateCity();
    }

    @Override
    public Flowable<Boolean> saveSearchTablesList(List<SearchTable> searchTableList) {
        return dbService.saveSearchTablesList(searchTableList);
    }

    @Override
    public Flowable<Boolean> saveJobItm(JobItem jobItem) {
        return dbService.saveJobItm(jobItem);
    }

    @Override
    public Flowable<JobItem> loadJobItmShort(int jobId) {
        return dbService.loadJobItmShort(jobId);
    }

    @Override
    public Flowable<JobItem> loadJobItmLong(int jobId, int shortDescriptionId) {
        return dbService.loadJobItmLong(jobId, shortDescriptionId);
    }

    @Override
    public Flowable<List<Notification>> loadAllNotification() {
        return dbService.loadAllNotification();
    }

    @Override
    public Flowable<Boolean> saveNotification(Notification notification) {
        return dbService.saveNotification(notification);
    }
}
