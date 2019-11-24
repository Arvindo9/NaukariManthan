package com.solution.naukarimanthan.ui.launcher.splash;


import android.app.Activity;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.util.Log;

import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.data.model.db.states$city.StatesAndCity;
import com.solution.naukarimanthan.data.model.db.test.PostTest;
import com.solution.naukarimanthan.utils.BindingMethods;
import com.solution.naukarimanthan.utils.General;
import com.solution.naukarimanthan.utils.Logger;
import com.solution.naukarimanthan.utils.location.GetLocation;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Locale;

import static com.solution.naukarimanthan.utils.AppConstants.LOCAL_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.POPULAR_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.RECENT_JOBS;
import static com.solution.naukarimanthan.utils.AppConstants.RECOMMENDED_JOBS;


/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
public class SplashViewModel extends BaseViewModel<SplashNavigator> {

    public boolean isPermissionGranted = false;
    private boolean isAppReady = false;
    private boolean isWaitComplete = false;
    private int taskId = 0;
    private final int TASK_PLAY_STORE = 1;
    private final int TASK_LOGIN = 2;
    private final int TASK_HOME = 3;
    private final int TASK_WELCOME = 4;
    private final int TASK_LOGIN_GOOGLE = 5;
    private final int TASK_LOGIN_LINKED_IN = 6;
    private static int SPLASH_TIME_OUT = 3000;

    SplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void start(String currentVersion, WeakReference<Activity> objectActivity) {
        isAppReady = false;
        isWaitComplete = false;

        getPostTestApi();

//        checkAppVersion(currentVersion);
        waitForThreadComplete();

        //----------
//        isJobsMenusListEmpty();
        BindingMethods.INSTANCE.checkMenuListInDb(this);

//        recentJobsFromApi(1);
//        recommendedJobsFromApi(0, 0, 0);
//        localJobsFromApi(1);
//        popularJobsFromApi(1);

        BindingMethods.INSTANCE.getRecentJobsFromApi(this, 1);
        BindingMethods.INSTANCE.getRecommendedJobsFromApi(this, 1, 0, 0, 0);
        BindingMethods.INSTANCE.getLocalJobsFromApi(this, 1);
        BindingMethods.INSTANCE.getPopularJobsFromApi(this, 1);

        BindingMethods.INSTANCE.saveDate(this);
//        saveDate();
        decideNextScreen();
        getCurrentLocation(objectActivity);
    }

    private void decideNextScreen(){
        if(getDataManager().getCurrentUserLoggedInMode() ==
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_FIRST_TIME.getType()){
            taskId = TASK_WELCOME;
        }
        else if(getDataManager().getCurrentUserLoggedInMode() ==
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.getType()){
//            taskId = TASK_LOGIN;
            taskId = TASK_HOME;
        }
        else{
            taskId = TASK_HOME;
        }

/*
        if(getDataManager().getCurrentUserLoggedInMode() ==
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_IN.getType()){
            taskId = TASK_HOME;
        }
        else if(getDataManager().getCurrentUserLoggedInMode() ==
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_GOOGLE.getType()){
            taskId = TASK_LOGIN_GOOGLE;
        }
        else if(getDataManager().getCurrentUserLoggedInMode() ==
                DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_LINKED_IN.getType()){
            taskId = TASK_LOGIN_LINKED_IN;
        }
*/

        isAppReady = true;
        onNextScreenDecided();
    }

    private void onNextScreenDecided(){
        if(isWaitComplete && isAppReady) {
            switch (taskId) {
                case TASK_HOME:
                    getNavigator().openHomeActivity();
                    break;
                case TASK_LOGIN:
                    getNavigator().openLoginActivity();
                    break;
                case TASK_PLAY_STORE:
                    getNavigator().openPlayStoreUpdate();
                    break;
                case TASK_WELCOME:
                    getNavigator().openWelcomeScreen();
                    break;
                case TASK_LOGIN_GOOGLE:
                    getNavigator().openLoginWithGoogle();
                    break;
                case TASK_LOGIN_LINKED_IN:
                    getNavigator().openLoginWithLinkedIn();
                    break;
            }
        }
    }

    private void waitForThreadComplete(){
         new Handler().postDelayed(() -> {
             isWaitComplete = true;
             onNextScreenDecided();
         }, SPLASH_TIME_OUT);
    }

    //-------------Additional task-------------

    private void checkAppVersion(String currentVersion){
        getCompositeDisposable().add(getDataManager()
                .appVersionCheck()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if (response.getStatus() == 1 &&
                            !currentVersion.equalsIgnoreCase(String.valueOf(response.getAppVersion()))) {
//                        decideNextScreen();
//                    } else {
//                        taskId = TASK_PLAY_STORE;
                        getNavigator().openPlayStoreUpdate();
                    }
                    isAppReady = true;
                    onNextScreenDecided();
                }, throwable -> {
                    getNavigator().handleError(throwable);
                    isAppReady = true;
                    onNextScreenDecided();
                }));
    }

    private void isJobsMenusListEmpty() {
        getCompositeDisposable().add(getDataManager()
                .getAllJobMenus()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                            if(response == null || response.isEmpty()){
                                checkMenuListApis();
                            }
                            Logger.e("do nothing");
                        },
                        throwable -> {
                            throwable.printStackTrace();
                            checkMenuListApis();
                            Logger.e("Error on db menu list");
                        }));
    }

    private void checkMenuListApis() {
        getCompositeDisposable().add(getDataManager()
                .jobTabsMenu()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        setMenusToDb(response.getData());
                    }
                }, throwable -> getNavigator().handleError(throwable)));
    }

    private void setMenusToDb(List<JobsMenuTabs> list){
        JobsMenuTabs homeTab = new JobsMenuTabs("Home", -1, -1);
        list.add(0, homeTab);

        getCompositeDisposable().add(getDataManager()
                .saveJobMenusList(list)
                .doOnComplete(() -> Logger.d("Success save jobs tabs"))
                .doOnError(throwable -> getNavigator().handleError(throwable))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    //Inserted
                    Logger.d("Success save menu list in db");
                },throwable -> getNavigator().handleError(throwable)));
    }


    //HomeFragment Adapter data setting
    //Adapter 1: Recent jobs
    private void recentJobsFromApi(int pageNo) {
        getCompositeDisposable().add(getDataManager()
                .recentJobsFromApi(pageNo)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        for(JobsData data1 : response.getData()){
                            data1.setJobSearchType(RECENT_JOBS);
                        }
                        saveRecentJobsToDb(response.getData());
                    }
                }, throwable -> {
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

    //Adapter 2: Recommended jobs
    private void recommendedJobsFromApi(int menuId1, int menuId2, int menuId3) {
        getCompositeDisposable().add(getDataManager()
                .getRecommendedJobs(1, menuId1, menuId2, menuId3)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        for(JobsData data1 : response.getData()){
                            data1.setJobSearchType(RECOMMENDED_JOBS);
                        }
                        saveRecommendedJobsToDb(response.getData());
                    }
                }, throwable -> {
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

    //Adapter 3: LocalJobs
    private void localJobsFromApi(int pageNo) {
        if(getDataManager().getStateCode() != 0) {
            getCompositeDisposable().add(getDataManager()
                    .getLocalJobs(getDataManager().getStateCode(), pageNo)
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().newThread())
                    .subscribe(response -> {
                        if (response != null && response.getStatus() == 1) {
                            for (JobsData data1 : response.getData()) {
                                data1.setJobSearchType(LOCAL_JOBS);
                            }
                            saveLocalJobsToDb(response.getData());
                        }
                    }, throwable -> {
                    }));
        }
    }

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

    //Adapter 4: Popular jobs
    private void popularJobsFromApi(int pageNo) {
        getCompositeDisposable().add(getDataManager()
                .getPopularJobs(pageNo)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        for(JobsData data1 : response.getData()){
                            data1.setJobSearchType(POPULAR_JOBS);
                        }
                        savePopularJobsToDb(response.getData());
                    }
                }, throwable -> {
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

    //Location----------------------

    public void getCurrentLocation(WeakReference<Activity> activity){

        if(taskId == TASK_HOME && isPermissionGranted) {
            if(getDataManager().getStateCode() == 0 ||
                    (General.integerDate() > General.integerDate(getDataManager().getAppDate())
                            && General.integerDate() > 7)) {
                GetLocation location = new GetLocation(activity.get());
                double Lat = location.getLatitude();
                double Lng = location.getLongitude();

                getAddress(activity.get(), Lat, Lng);
            }
        }
    }

    private void getAddress(Activity activity, double lat, double lng) {
        Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(lat, lng, 1);
            if(addresses != null && !addresses.isEmpty()) {
                Address obj = addresses.get(0);
                String add = obj.getAddressLine(0);
                String city = null;

                if(obj.getAdminArea() != null){
                    add = obj.getAdminArea();
                    city = obj.getLocality();
                }else if (obj.getLocality() != null) {
                    add = obj.getLocality();
                } else if (obj.getPostalCode() != null) {
                    add = obj.getPostalCode();
                } else if (obj.getSubAdminArea() != null) {
                    add = obj.getSubAdminArea();
                }

                saveState(add, city);

                Logger.d("IGA", "Address" + add);
            }
            // Toast.makeText(this, "Address=>" + add,
            // Toast.LENGTH_SHORT).show();

            // TennisAppActivity.showDialog(add);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private void saveState(String code, String city){
        if(code != null && !code.equals("")) {
            getCompositeDisposable().add(getDataManager()
                    .getAllStateCity()
                    .subscribeOn(getSchedulerProvider().io())
                    .observeOn(getSchedulerProvider().newThread())
                    .subscribe(response -> {
                        for(StatesAndCity data1 : response){
                            if(code.contains(data1.getStateName())){

                                String name = "";
                                if(city != null){
                                    name = city + ", " + data1.getStateName();
                                }

                                getDataManager().setStateName(name);
                                getDataManager().setStateCode(data1.getStateId());
                                break;
                            }
                        }
                        Logger.d( "Jobs data saved to Db");
                        //do noting
                    }, throwable -> {
                        Logger.d( "Jobs data failed to Db");
                        //do nothing
                    }));
        }
    }

    private void saveDate() {
        getDataManager().setAppDate(General.defaultDate());
    }










    public void getPostTestApi(){
        getCompositeDisposable().add(getDataManager()
                .getPostTest()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    List<PostTest> cf = response;
                    if (response != null) {
//                        savePostTestDb(response);
                        Logger.e("gfhgfghhg", response);
                        Log.e("fghjfhgfhgfhgfh", "dgfdfg");
                    }
                    Logger.e("gghh", response);

                    Log.e("gghh", "jhjh");
//                }, Throwable::printStackTrace));
                }, throwable -> {

                    Log.e("fgffffffffffffffffffff", "jhjh");
                    Logger.e("error=========");
                    throwable.printStackTrace();
                }));
    }
}