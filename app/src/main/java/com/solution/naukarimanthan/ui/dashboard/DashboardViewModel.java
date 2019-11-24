package com.solution.naukarimanthan.ui.dashboard;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.data.model.db.notification.Notification;
import com.solution.naukarimanthan.utils.BindingMethods;
import com.solution.naukarimanthan.utils.BindingUtils;
import com.solution.naukarimanthan.utils.Logger;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/8/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class DashboardViewModel extends BaseViewModel<DashboardNavigator> {

    public final ObservableList<Notification> notificationObservableArrayList = new ObservableArrayList<>();

    private final MutableLiveData<List<Notification>> notificationListLiveData;

    public static final MutableLiveData<String> areaLiveData = new MutableLiveData<>();

    public DashboardViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        notificationListLiveData = new MutableLiveData<>();
        loadJobsMenusTabs();
        setLocation();
        loadNotificationData();
    }

    private void setLocation(){
        String area = getDataManager().getStateName();
        if(area == null || area.equals("")){
            areaLiveData.setValue("Enter place");
//            BindingUtils.areaLocation.set("Enter place");
        }else {
            areaLiveData.setValue(getDataManager().getStateName());
//            BindingUtils.areaLocation.set(getDataManager().getStateName());
        }
    }

    public void start(String currentVersion) {
        checkAppVersion(currentVersion);
    }

    private void loadJobsMenusTabs() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getAllJobMenus()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response != null && !response.isEmpty()){
                        JobsMenuTabs homeTab = new JobsMenuTabs("Home", -1, -1);
                        response.add(0, homeTab);

                        getNavigator().tabsMenuList(response);
                        Logger.d("Success to Load menus", String.valueOf(response.size()));
                    }
                    else {
                        response = new ArrayList<>();
                        JobsMenuTabs homeTab = new JobsMenuTabs("Home", -1, -1);
                        response.add(0, homeTab);

                        getNavigator().tabsMenuList(response);

                        BindingMethods.INSTANCE.checkMenuListInDb(this);
                        Logger.e("Failed to Load menus");
                    }
                    setIsLoading(false);
                },throwable ->{
                        Logger.d("Failed to Load menus");
                        setIsLoading(false);
                        getNavigator().handleError(throwable);
                        getNavigator().tabsMenuList(null);
                        throwable.printStackTrace();
                        BindingMethods.INSTANCE.checkMenuListInDb(this);
                }));
    }

    MutableLiveData<String> getAreaLiveData() {
        return areaLiveData;
    }

    void updateArea(String area){
        BindingUtils.areaLocation.set(area);
    }

    //check on UI refresh------------------------------

    private void isJobsMenusListEmpty() {
        getCompositeDisposable().add(getDataManager()
                .getAllJobMenus()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                            if(response == null || response.isEmpty()){
                                checkMenuListApis();
                            }
                            else{
                                loadJobsMenusTabs();
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
                    loadJobsMenusTabs();
                    Logger.d("Success save menu list in db");
                },throwable -> getNavigator().handleError(throwable)));
    }

    //Notification data--------------------------------

    private void loadNotificationData() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .loadAllNotification()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response != null && !response.isEmpty()){
                        notificationListLiveData.setValue(response);
                    }
                    setIsLoading(false);
                },throwable ->{
                    setIsLoading(false);
                    Logger.d("Failed to Load menus");
                    getNavigator().handleError(throwable);
                }));
    }

    public MutableLiveData<List<Notification>> getNotificationListLiveData() {
        return notificationListLiveData;
    }

    public void addJobsItemsToList(List<Notification> jobs) {
        notificationObservableArrayList.clear();
        notificationObservableArrayList.addAll(jobs);
    }


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
                }, throwable -> getNavigator().handleError(throwable)));
    }

    //Additional task----------------------------------

    public void onHomeButtonClick(){
        getNavigator().onHomeButtonClick();
    }

    public void onCategoryClick(){
        getNavigator().onCategoryClick();
    }

    public void onRefreshClick(){
        getNavigator().onRefreshButtonClick();
        BindingMethods.INSTANCE.checkMenuListInDb(this);
        setLocation();
//        BindingUtils.areaLocation.set(getDataManager().getStateName());
    }
}
