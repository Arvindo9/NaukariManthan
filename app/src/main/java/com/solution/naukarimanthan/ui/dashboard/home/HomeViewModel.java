package com.solution.naukarimanthan.ui.dashboard.home;


import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.utils.Logger;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.solution.naukarimanthan.utils.AppConstants.LOCAL_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.POPULAR_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.RECENT_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.RECOMMENDED_JOBS;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-01-2019
 * Email        : arvindomondal@gmail.com
 * Project URL  :
 */
public class HomeViewModel extends BaseViewModel<HomeNavigator> {

    private static final String TAG = HomeViewModel.class.getSimpleName();

    private static boolean isRecommendedJobsLoaded = false;

    public final ObservableField<String> alertTextObservable = new ObservableField<>();

    public final ObservableList<JobsData> homeRecentObservableList = new ObservableArrayList<>();
    public final ObservableList<JobsData> homeRecommendedObservableList = new ObservableArrayList<>();
    public final ObservableList<JobsData> homeLocalObservableList = new ObservableArrayList<>();
    public final ObservableList<JobsData> homePopularObservableList = new ObservableArrayList<>();

    private final MutableLiveData<List<JobsData>> recentListLiveData;
    private final MutableLiveData<List<JobsData>> recommendedListLiveData;
    private final MutableLiveData<List<JobsData>> localListLiveData;
    private final MutableLiveData<List<JobsData>> popularListLiveData;

    private AtomicReference<List<JobsMenuTabs>> menus = new AtomicReference<>();

    public HomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);

        recentListLiveData = new MutableLiveData<>();
        recommendedListLiveData = new MutableLiveData<>();
        localListLiveData = new MutableLiveData<>();
        popularListLiveData = new MutableLiveData<>();

//        recentJobsFromApi(1);
//        recommendedJobsFromApi(1);
//        localJobsFromApi(1);
//        popularJobsFromApi(1);

        recommendedJobsFromDb();
        recentJobsFromDb();
        localJobsFromDb();
        popularJobsFromDb();
    }


    //Initially load jobs from db only

    //Adapter 1: Recent jobs
    private void recentJobsFromDb() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getRecentJobs()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        getNavigator().getRecentJobsPageNo(response.size());
                        recentListLiveData.setValue(response);
                        String alertText = "";
                        if(!isRecommendedJobsLoaded){
                            recommendedListLiveData.setValue(response);
                        }

                        for(JobsData data2: response){
                            alertText = " " + alertText + " " + data2.getTitle() + "; ";
                        }
                        alertTextObservable.set(alertText);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    //Adapter 2: Recommended jobs
    private void recommendedJobsFromDb() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getRecommendedJobs()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && !response.isEmpty()) {
                        recommendedListLiveData.setValue(response);
                        isRecommendedJobsLoaded = true;
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    //Adapter 3: LocalJobs
    private void localJobsFromDb() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getLocalJobs()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        localListLiveData.setValue(response);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    //Adapter 4: Popular jobs
    private void popularJobsFromDb() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getPopularJobs()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null) {
                        popularListLiveData.setValue(response);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }



    //HomeFragment Adapter data update on Refresh-------------------------
    //Adapter 1: Recent jobs
    private void recentJobsFromApi(int pageNo) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .recentJobsFromApi(pageNo)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        for(JobsData data1 : response.getData()){
                            data1.setJobSearchType(RECENT_JOBS);
                        }

                        recommendedListLiveData.setValue(response.getData());
                        if(!isRecommendedJobsLoaded){
                            recommendedListLiveData.setValue(response.getData());
                        }
                        saveRecentJobsToDb(response.getData());
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    //Adapter 2: Recommended jobs
    private void recommendedJobsFromApi(int pageNo, int menuId1, int menuId2, int menuId3) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getRecommendedJobs(pageNo, menuId1, menuId2, menuId3)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        for(JobsData data1 : response.getData()){
                            data1.setJobSearchType(RECOMMENDED_JOBS);
                        }

                        recommendedListLiveData.setValue(response.getData());
                        saveRecommendedJobsToDb(response.getData());
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }

    //Adapter 3: LocalJobs
    private void localJobsFromApi(int pageNo) {
        if(getDataManager().getStateCode() != 0) {
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager()
                    .getLocalJobs(getDataManager().getStateCode(), pageNo)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if (response != null && response.getStatus() == 1) {
                            for (JobsData data1 : response.getData()) {
                                data1.setJobSearchType(LOCAL_JOBS);
                            }

                            localListLiveData.setValue(response.getData());
                            saveLocalJobsToDb(response.getData());
                        }
                        setIsLoading(false);
                    }, throwable -> {
                        setIsLoading(false);
                        getNavigator().handleError(throwable);
                    }));
        }
    }

    //Adapter 4: Popular jobs
    private void popularJobsFromApi(int pageNo) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getPopularJobs(pageNo)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        for(JobsData data1 : response.getData()){
                            data1.setJobSearchType(POPULAR_JOBS);
                        }
                        popularListLiveData.setValue(response.getData());
                        savePopularJobsToDb(response.getData());
                    }
                    setIsLoading(false);
                }, throwable -> {
                    setIsLoading(false);
                    getNavigator().handleError(throwable);
                }));
    }


    //Save jobs to database-----------------------------------

    private void saveLocalJobsToDb(List<JobsData> data) {
        getCompositeDisposable().add(getDataManager()
                .saveJobsData(data)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    Logger.d( "Jobs data saved to Db");
                    //do noting
                }, throwable -> {
                    Logger.d( "Jobs data failed to Db");
                    //do nothing
                }));
    }

    private void saveRecentJobsToDb(List<JobsData> data) {
        getCompositeDisposable().add(getDataManager()
                .saveJobsData(data)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    Logger.d( "Jobs data saved to Db");
                    //do noting
                }, throwable -> {
                    Logger.d( "Jobs data failed to Db");
                    //do nothing
                }));
    }

    private void saveRecommendedJobsToDb(List<JobsData> data) {
        getCompositeDisposable().add(getDataManager()
                .saveJobsData(data)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    Logger.d( "Jobs data saved to Db");
                    //do noting
                }, throwable -> {
                    Logger.d( "Jobs data failed to Db");
                    //do nothing
                }));
    }

    private void savePopularJobsToDb(List<JobsData> data) {
        getCompositeDisposable().add(getDataManager()
                .saveJobsData(data)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    for(JobsData data1 : data){
                        data1.setJobSearchType(POPULAR_JOBS);
                        Logger.d("Jobs data search", String.valueOf(data1.getJobSearchType()));
                    }
                    Logger.d( "Jobs data saved to Db");
                    //do noting
                }, throwable -> {
                    Logger.d( "Jobs data failed to Db");
                    //do nothing
                }));
    }

    //------------Subscribe jobs------------------------------
    //MutableLiveData-----------------------

    public MutableLiveData<List<JobsData>> getRecentListLiveData() {
        return recentListLiveData;
    }

    public MutableLiveData<List<JobsData>> getRecommendedListLiveData() {
        return recommendedListLiveData;
    }

    public MutableLiveData<List<JobsData>> getLocalListLiveData() {
        return localListLiveData;
    }

    public MutableLiveData<List<JobsData>> getPopularListLiveData() {
        return popularListLiveData;
    }

    //ObservableList-----------------------

    public ObservableList<JobsData> getHomeRecentObservableList() {
        return homeRecentObservableList;
    }

    public ObservableList<JobsData> getHomeRecommendedObservableList() {
        return homeRecommendedObservableList;
    }

    public ObservableList<JobsData> getHomeLocalObservableList() {
        return homeLocalObservableList;
    }

    public ObservableList<JobsData> getHomePopularObservableList() {
        return homePopularObservableList;
    }

    //addJobsItemsToList------------------

    public void addRecentJobsItemsToList(List<JobsData> jobs) {
        homeRecentObservableList.clear();
        homeRecentObservableList.addAll(jobs);
    }

    public void addRecommendedJobsItemsToList(List<JobsData> jobs) {
        homeRecommendedObservableList.clear();
        homeRecommendedObservableList.addAll(jobs);
    }

    public void addLocalJobsItemsToList(List<JobsData> jobs) {
        homeLocalObservableList.clear();
        homeLocalObservableList.addAll(jobs);
    }

    public void addPopularJobsItemsToList(List<JobsData> jobs) {
        homePopularObservableList.clear();
        homePopularObservableList.addAll(jobs);
    }

    //UI click events listeners---------------

    public void onRecentMoreClick(){
        getNavigator().onRecentMoreClick();
    }

    public void onRecommendedMoreClick(){
        getNavigator().onRecommendedMoreClick();
    }

    public void onLocalMoreClick(){
        getNavigator().onLocalMoreClick();
    }

    public void onPopularMoreClick(){
        getNavigator().onPopularMoreClick();
    }

    public void onRefreshButtonClick() {
        //TODO onRefreshClick

        Logger.d("HomeViewModel ", "onRefreshButtonClick");

        recentJobsFromApi(1);
        recommendedJobsFromApi(1,0, 0, 0);
        localJobsFromApi(1);
        popularJobsFromApi(1);
    }

    public void onFeedbackClick(){
        getNavigator().onFeedbackClick();
    }

    public void onSubscriptionClick(){
        getNavigator().onSubscriptionClick();
    }

    public void subscribeToApi(String nameS, String emailS, String mobile, String field){
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .subscriptionApi(nameS, mobile, emailS, field)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    Logger.d( "Jobs data saved to Db");
                    getNavigator().onSubscriptionDone(response.getMessage());
                    setIsLoading(false);
                    //do noting
                }, throwable -> {
                    Logger.d( "Jobs data failed to Db");
                    getNavigator().onSubscriptionDone("Try again later");
                    setIsLoading(false);
                    //do nothing
                }));
    }
}