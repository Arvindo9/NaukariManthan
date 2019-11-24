package com.solution.naukarimanthan.ui.dashboard.jobs;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;

import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.utils.BindingMethods;
import com.solution.naukarimanthan.utils.Logger;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.solution.naukarimanthan.utils.AppConstants.JOBS_BY_MENU_ID;
import static com.solution.naukarimanthan.utils.AppConstants.LOCAL_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.POPULAR_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.RECENT_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.RECOMMENDED_JOBS;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/9/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class JobsViewModel extends BaseViewModel<JobsNavigator> {

    private static final String TAG = JobsViewModel.class.getSimpleName();
    public final ObservableList<JobsData> jobsObservableArrayList = new ObservableArrayList<>();

    private final MutableLiveData<List<JobsData>> jobsListLiveData;

    private AtomicReference<List<JobsMenuTabs>> menus = new AtomicReference<>();
    private int menuId = 0;
    private int jobsId;
    private int maxPageCount = -1;
    private int menuId1 = 0;
    private int menuId2 = 0;
    private int menuId3 = 0;

    public JobsViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        jobsListLiveData = new MutableLiveData<>();
        getTop3Menu();
//        fetchJobs();

    }

    //case 1: Initialise Adapter, first time-----------

    public final void start(int index) {
        loadJobsMenusTabs(index);
    }

    //1 First load menu from db and get current menuID
    private void loadJobsMenusTabs(int index) {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getAllJobMenus()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if(response != null && !response.isEmpty()){
                        menus.set(response);
                        loadJobsFromDb(index);
                    }
                    setIsLoading(false);
                },throwable ->{
                    setIsLoading(false);
                    Logger.d("Failed to Load menus");
                    getNavigator().handleError(throwable);
                }));
    }

    //2 Second load data from db and to observer live data
    private void loadJobsFromDb(int tabId){
        setIsLoading(true);
        for(JobsMenuTabs tabs: menus.get()){
            if(tabId == tabs.getMenuId()){
                menuId = tabs.getMenuId();
                break;
            }
        }

//        int finalMenuId = menuId;
        getCompositeDisposable().add(getDataManager()
                .getJobsDataById(menuId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && !response.isEmpty()) {
                        jobsListLiveData.setValue(response);
                    }
                    else {
                        fetchJobs(menuId, 1);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    fetchJobs(menuId, 1);
                    setIsLoading(false);
                }));
    }

    //3 Third if case second db is empty then load data from server using menuId
    private void fetchJobs(int menuId, int pageNo) {
        if((maxPageCount < 0) || pageNo <= maxPageCount) {
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager()
                    .loadJobsFromApi(pageNo, menuId)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if (response != null && response.getStatus() == 1) {
                            maxPageCount = response.getMaxPage();
                            jobsListLiveData.setValue(response.getData());
                            saveJobsToDb(response.getData());
                        }
                        setIsLoading(false);
                    }, throwable -> {
                        setIsLoading(false);
                        getNavigator().handleError(throwable);
                        getNavigator().setCurrentPage(pageNo);
                    }));
        }
        else{
            int page = maxPageCount;
            if(page < 0){
                page = 1;
            }
            getNavigator().setCurrentPage(page);
        }
    }

    private void saveJobsToDb(List<JobsData> data){
        getCompositeDisposable().add(getDataManager()
                .saveJobsData(data)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    Logger.d(TAG, "Jobs data saved to Db");
                    //do noting
                }, throwable -> {
                    Logger.d(TAG, "Jobs data failed to Db");
                    //do nothing
                }));
    }

    public final MutableLiveData<List<JobsData>> getJobsListLiveData() {
        return jobsListLiveData;
    }

    public void addJobsItemsToList(List<JobsData> jobs) {
//        jobsObservableArrayList.clear();
        int size = jobs.size();

        int j = 0;
        if(size > 2){
            j = 2;
            jobs.set(j, null);
        }

        for(int i=j+4; i < size; i += 5){
            jobs.set(i, null);
        }
        jobsObservableArrayList.addAll(jobs);
    }

    public ObservableList<JobsData> getJobsObservableArrayList() {
        return jobsObservableArrayList;
    }

    //----------------------------------------

    //TODO case 2: hit api for searching for latest data and set to top of adapter and save to db
    //case 2: hit api for searching for latest data and set to top of adapter and save to db
    public void hitApiForLatestData(int menuId, int pageNo){
    }

    //----------------------------------------

    //TODO on scroll down, hit api for available data and set to db, if same found in db the
    //case 3: on scroll down, hit api for available data and set to db, if same found in db the
    // stop hitting api and load data form db
    public void hitApiForNextLoadData(int pageNo){
        if((maxPageCount < 0) || pageNo <= maxPageCount){
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager()
                    .loadJobsFromApi(pageNo,menuId)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if (response != null && response.getStatus() == 1) {
                            maxPageCount = response.getMaxPage();
                            jobsListLiveData.setValue(response.getData());
                            saveJobsToDb(response.getData());
                        }
                        else {
                            maxPageCount = 0;
                        }
                        setIsLoading(false);
                    }, throwable -> {
                        setIsLoading(false);
                        getNavigator().handleError(throwable);
                        getNavigator().setCurrentPage(pageNo);
                    }));
        }
        else{
            int page = maxPageCount;
            if(page < 0){
                page = 1;
            }
            getNavigator().setCurrentPage(page);
        }
    }

    public void onRefreshButtonClick() {
        //TODO onRefreshClick
        fetchJobs(menuId, 1);
    }

    //-----------------------------------------
    //Special type jobs search-----------------
    public void startSpecial(int jobsId, int menuId) {
        this.jobsId = jobsId;
        loadSpecialJobsFromDb(jobsId, 1, menuId);
    }

    private void loadSpecialJobsFromDb(int jobsId, int pageNo, int menuId) {
        getCompositeDisposable().add(getDataManager()
                .getSpecialJobsFromDb(jobsId, pageNo, menuId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && !response.isEmpty()) {
                        jobsListLiveData.setValue(response);
                    }
                    else {
                        fetchSpecialJobs(jobsId, pageNo, menuId);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    fetchSpecialJobs(jobsId, pageNo, menuId);
                    setIsLoading(false);
                }));
    }

    public void fetchSpecialJobs(int jobsId, int pageNo, int menuId) {
        switch (jobsId){
            case RECENT_JOBS: recentJobsFromApi(pageNo);    break;
            case RECOMMENDED_JOBS: recommendedJobsFromApi(pageNo,1, 2, 3); break;
            case POPULAR_JOBS: popularJobsFromApi(pageNo); break;
            case LOCAL_JOBS: localJobsFromApi(pageNo); break;
            case JOBS_BY_MENU_ID: getJobsByMenuIdApi(menuId, pageNo); break;
        }
    }

    //HomeFragment Adapter data setting
    //Adapter 1: Recent jobs
    private void recentJobsFromApi(int pageNo) {
        if((maxPageCount < 0) || pageNo <= maxPageCount) {
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager()
                    .recentJobsFromApi(pageNo)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if (response != null && response.getStatus() == 1) {
                            for (JobsData data1 : response.getData()) {
                                data1.setJobSearchType(RECENT_JOBS);
                            }
                            maxPageCount = response.getMaxPage();

                            jobsListLiveData.setValue(response.getData());
                            saveRecentJobsToDb(response.getData(), pageNo);
                        }

                        setIsLoading(false);
                    }, throwable -> {
                        getNavigator().setCurrentPage(pageNo);
                        setIsLoading(false);
                    }));
        }
        else{
            int page = maxPageCount;
            if(page < 0){
                page = 1;
            }
            getNavigator().setCurrentPage(page);
        }
    }

    private void saveRecentJobsToDb(List<JobsData> data, int pageNO) {
        getCompositeDisposable().add(getDataManager()
                .saveJobsData(data)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
//                    loadSpecialJobsFromDb(jobsId, pageNO, menuId);
                    Logger.d( "Jobs data saved to Db");
                    //do noting
                }, throwable -> {
                    Logger.d( "Jobs data failed to Db");
                    //do nothing
                }));
    }

    //Adapter 2: Recommended jobs
    private void getTop3Menu() {
            getCompositeDisposable().add(getDataManager()
                    .getTop3MenuId()
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().newThread())
                    .subscribe(response -> {
                        if (response != null && response.size() > 1) {
                            menuId1 = response.get(0).getMenuId();
                            menuId2 = response.get(1).getMenuId();
                            menuId3 = response.get(2).getMenuId();
                        }
                    }, throwable -> {
                    }));
    }

    private void recommendedJobsFromApi(int pageNo, int menuId1, int menuId2, int menuId3) {
        if((maxPageCount < 0) || pageNo <= maxPageCount) {
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager()
                    .getRecommendedJobs(pageNo, this.menuId1, this.menuId2, this.menuId3)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if (response != null && response.getStatus() == 1) {
//                            for (JobsData data1 : response.getData()) {
//                                data1.setJobSearchType(RECOMMENDED_JOBS);
//                            }
                            maxPageCount = response.getMaxPage();

                            jobsListLiveData.setValue(response.getData());
//                            saveRecommendedJobsToDb(response.getData(), pageNo);
                        }
                        setIsLoading(false);
                    }, throwable -> {
                        getNavigator().setCurrentPage(pageNo);
                        setIsLoading(false);
                    }));
        }

        else{
            int page = maxPageCount;
            if(page < 0){
                page = 1;
            }
            getNavigator().setCurrentPage(page);
        }
    }

    private void saveRecommendedJobsToDb(List<JobsData> data, int pageNo) {
        getCompositeDisposable().add(getDataManager()
                .saveJobsData(data)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    Logger.d( "Jobs data saved to Db");
//                    loadSpecialJobsFromDb(jobsId, pageNo, menuId);
                    //do noting
                }, throwable -> {
                    Logger.d( "Jobs data failed to Db");
                    //do nothing
                }));
    }

    //Adapter 3: LocalJobs
    private void localJobsFromApi(int pageNo) {
        if((maxPageCount < 0) || pageNo <= maxPageCount) {
            setIsLoading(true);
            if (getDataManager().getStateCode() != 0) {
                getCompositeDisposable().add(getDataManager()
                        .getLocalJobs(getDataManager().getStateCode(), pageNo)
                        .subscribeOn(getSchedulerProvider().io())
                        .observeOn(getSchedulerProvider().newThread())
                        .subscribe(response -> {
                            if (response != null && response.getStatus() == 1) {
                                for (JobsData data1 : response.getData()) {
                                    data1.setJobSearchType(LOCAL_JOBS);
                                }
                                maxPageCount = response.getMaxPage();

                                jobsListLiveData.setValue(response.getData());
                                saveLocalJobsToDb(response.getData(), pageNo);
                            }
                            setIsLoading(false);
                        }, throwable -> {
                            getNavigator().setCurrentPage(pageNo);
                            setIsLoading(false);
                        }));
            }
        }
        else{
            int page = maxPageCount;
            if(page < 0){
                page = 1;
            }
            getNavigator().setCurrentPage(page);
        }
    }

    private void saveLocalJobsToDb(List<JobsData> data, int pageNo) {
        getCompositeDisposable().add(getDataManager()
                .saveJobsData(data)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    Logger.d( "Jobs data saved to Db");
//                    loadSpecialJobsFromDb(jobsId, pageNo, menuId);
                    //do noting
                }, throwable -> {
                    Logger.d( "Jobs data failed to Db");
                    //do nothing
                }));
    }

    //Adapter 4: Popular jobs
    private void popularJobsFromApi(int pageNo) {
        if((maxPageCount < 0) || pageNo <= maxPageCount) {
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager()
                    .getPopularJobs(pageNo)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().newThread())
                    .subscribe(response -> {
                        if (response != null && response.getStatus() == 1) {
                            for (JobsData data1 : response.getData()) {
                                data1.setJobSearchType(POPULAR_JOBS);
                            }
                            maxPageCount = response.getMaxPage();

                            jobsListLiveData.setValue(response.getData());
                            savePopularJobsToDb(response.getData(), pageNo);
                        }

                        setIsLoading(false);
                    }, throwable -> {
                        getNavigator().setCurrentPage(pageNo);
                        setIsLoading(false);
                    }));
        }
        else{
            int page = maxPageCount;
            if(page < 0){
                page = 1;
            }
            getNavigator().setCurrentPage(page);
        }
    }

    private void savePopularJobsToDb(List<JobsData> data, int pageNo) {
        getCompositeDisposable().add(getDataManager()
                .saveJobsData(data)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    for(JobsData data1 : data){
                        data1.setJobSearchType(POPULAR_JOBS);
                        Logger.d("Jobs data search", String.valueOf(data1.getJobSearchType()));
                    }

//                    loadSpecialJobsFromDb(jobsId, pageNo, menuId);
                    Logger.d( "Jobs data saved to Db");
                    //do noting
                }, throwable -> {
                    Logger.d( "Jobs data failed to Db");
                    //do nothing
                }));
    }

    //Load jobs by menuId-
    private void getJobsByMenuIdApi(int menuId, int pageNo){
        if((maxPageCount < 0) || pageNo <= maxPageCount) {
            setIsLoading(true);
            getCompositeDisposable().add(getDataManager()
                    .loadJobsFromApi(pageNo, menuId)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().ui())
                    .subscribe(response -> {
                        if (response != null && !response.getData().isEmpty()) {

                            maxPageCount = response.getMaxPage();
                            jobsListLiveData.setValue(response.getData());
                        }
                        setIsLoading(false);
                }, throwable ->{
                    getNavigator().setCurrentPage(pageNo);
                    setIsLoading(false);
                }));
        }
        else{
            int page = maxPageCount;
            if(page < 0){
                page = 1;
            }
            getNavigator().setCurrentPage(page);
        }


        /*
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getJobsDataById(menuId)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response != null && !response.isEmpty()) {
                        jobsListLiveData.setValue(response);
                    }
                    else {
                        fetchJobs(menuId, pageNo);
                    }
                    setIsLoading(false);
                }, throwable -> {
                    fetchJobs(menuId, 1);
                    setIsLoading(false);
                }));
        */
    }
}
