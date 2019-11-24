package com.solution.naukarimanthan.utils;


import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.data.model.db.states$city.StatesAndCity;

import java.util.List;

import static com.solution.naukarimanthan.utils.AppConstants.LOCAL_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.POPULAR_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.RECENT_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.RECOMMENDED_JOBS;

/**
 * Author       : Arvindo Mondal
 * Created on   : 19-01-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * Project URL  :
 */
public enum BindingMethods {

    INSTANCE;

    public static final String TAG = BindingMethods.class.getSimpleName();

    //menus handling-------------------------------------------------

    /**
     * check for menu list in database, if not found automatically download if network will
     * available
     *
     * @param viewModel Reference of view model
     * @param <V> Class name of view model
     */
    public <V extends BaseViewModel> void checkMenuListInDb(V viewModel){
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .getAllJobMenus()
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if(response == null || response.isEmpty()){
                        getMenuListFromApi(viewModel);
                    }
                    Logger.e("do nothing");
                },
                throwable -> {
                    throwable.printStackTrace();
                    getMenuListFromApi(viewModel);
                    Logger.e("Error on db menu list");
                }));
    }

    /**
     * Download menus from server and save to database
     *
     * @param viewModel Reference of view model
     * @param <V> Class name of view model
     */
    public <V extends BaseViewModel> void getMenuListFromApi(V viewModel){
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .jobTabsMenu()
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        saveMenuListToDb(viewModel, response.getData());
                    }
                }, Throwable::printStackTrace));
    }

    /**
     * save menus list into the database
     *
     * @param viewModel Reference of view model
     * @param <V> Class name of view model
     */
    public <V extends BaseViewModel> void saveMenuListToDb(V viewModel, List<JobsMenuTabs> list){
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .saveJobMenusList(list)
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().newThread())
                .subscribe(response -> {
                    //Inserted
                    Logger.d("Success save menu list in db");
                },Throwable::printStackTrace));
    }


    public <V extends BaseViewModel> void saveRecentMenuIdDb(V viewModel, int menuId){
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .updateMenuPriorityByMenuId(menuId)
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().newThread())
                .subscribe(response -> {
                    //Inserted
                    Logger.d("Success save menu list in db");
                },Throwable::printStackTrace));
    }

    //-----------------------End-------------------------------------

    //Location, area handing-----------------------------------------

    public <V extends BaseViewModel>  void getStateCodeFromApi(V viewModel){
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .getAreaCodeApi()
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        saveStateCodeToDb(viewModel, response.getData());
                    }
                }, throwable -> {
                    Logger.e(TAG, " error");
                    throwable.printStackTrace();
                }));
    }

    public <V extends BaseViewModel> void saveStateCodeToDb(V viewModel,
                                                             List<StatesAndCity> data) {
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .saveStateCityList(data)
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().newThread())
                .subscribe(response -> {
                    //Inserted
                },Throwable::printStackTrace));
    }

    //-----------------------End-------------------------------------

    //Saving app data to pref----------------------------------------

    public <V extends BaseViewModel> void saveDate(V viewModel) {
        viewModel.getDataManager().setAppDate(General.defaultDate());
    }

    //-----------------------End-------------------------------------

    //HomeFragment Adapter data setting------------------------------
    //Adapter 1: Recent jobs
    public <V extends BaseViewModel> void getRecentJobsFromApi(V viewModel, int pageNo) {
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .recentJobsFromApi(pageNo)
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        for(JobsData data1 : response.getData()){
                            data1.setJobSearchType(RECENT_JOBS);
                        }
                        saveRecentJobsToDb(viewModel, response.getData());
                    }
                }, throwable -> {
                }));
    }

    public <V extends BaseViewModel> void saveRecentJobsToDb(V viewModel, List<JobsData> data) {
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .saveJobsData(data)
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().newThread())
                .subscribe(response -> {
                    Logger.d( "Jobs data saved to Db");
                    //do noting
                }, throwable -> {
                    Logger.e( "Jobs data failed to Db");
                    //do nothing
                }));
    }

    //Adapter 2: Recommended jobs
    public <V extends BaseViewModel> void getRecommendedJobsFromApi(V viewModel, int pageNo,
                                                         int menuId1, int menuId2, int menuId3) {
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .getRecommendedJobs(pageNo, menuId1, menuId2, menuId3)
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        for(JobsData data1 : response.getData()){
                            data1.setJobSearchType(RECOMMENDED_JOBS);
                        }
                        saveRecommendedJobsToDb(viewModel, response.getData());
                    }
                }, throwable -> {
                }));
    }

    public <V extends BaseViewModel> void saveRecommendedJobsToDb(V viewModel,
                                                                   List<JobsData> data) {
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .saveJobsData(data)
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().newThread())
                .subscribe(response -> {
                    Logger.d( "Jobs data saved to Db");
                    //do noting
                }, throwable -> {
                    Logger.d( "Jobs data failed to Db");
                    //do nothing
                }));
    }

    //Adapter 3: LocalJobs
    public <V extends BaseViewModel> void getLocalJobsFromApi(V viewModel, int pageNo) {
        if(viewModel.getDataManager().getStateCode() != 0) {
            viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                    .getLocalJobs(viewModel.getDataManager().getStateCode(), pageNo)
                    .subscribeOn(viewModel.getSchedulerProvider().io())
                    .observeOn(viewModel.getSchedulerProvider().newThread())
                    .subscribe(response -> {
                        if (response != null && response.getStatus() == 1) {
                            for (JobsData data1 : response.getData()) {
                                data1.setJobSearchType(LOCAL_JOBS);
                            }
                            saveLocalJobsToDb(viewModel, response.getData());
                        }
                    }, throwable -> {
                    }));
        }
    }

    public <V extends BaseViewModel> void saveLocalJobsToDb(V viewModel, List<JobsData> data) {
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .saveJobsData(data)
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().newThread())
                .subscribe(response -> {
                    Logger.d( "Jobs data saved to Db");
                    //do noting
                }, throwable -> {
                    Logger.d( "Jobs data failed to Db");
                    //do nothing
                }));
    }

    //Adapter 4: Popular jobs
    public <V extends BaseViewModel> void getPopularJobsFromApi(V viewModel, int pageNo) {
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .getPopularJobs(pageNo)
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        for(JobsData data1 : response.getData()){
                            data1.setJobSearchType(POPULAR_JOBS);
                        }
                        savePopularJobsToDb(viewModel, response.getData());
                    }
                }, throwable -> {
                }));
    }

    public <V extends BaseViewModel> void savePopularJobsToDb(V viewModel, List<JobsData> data) {
        viewModel.getCompositeDisposable().add(viewModel.getDataManager()
                .saveJobsData(data)
                .subscribeOn(viewModel.getSchedulerProvider().io())
                .observeOn(viewModel.getSchedulerProvider().newThread())
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

    //-----------------------End-------------------------------------
}
