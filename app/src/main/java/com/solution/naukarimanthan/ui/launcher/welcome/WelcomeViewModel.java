package com.solution.naukarimanthan.ui.launcher.welcome;

import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.data.model.db.Flag;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.data.model.db.states$city.StatesAndCity;
import com.solution.naukarimanthan.utils.BindingMethods;
import com.solution.naukarimanthan.utils.BindingUtils;
import com.solution.naukarimanthan.utils.General;
import com.solution.naukarimanthan.utils.Logger;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.util.List;

/**
 * Author       : Arvindo Mondal
 * Created on   : 25-12-2018
 * Email        : arvindomondal@gmail.com
 */
public class WelcomeViewModel extends BaseViewModel<WelcomeNavigator> {


    public WelcomeViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
//        checkMenuListApis();
    }

    public void start() {
        setLaunchMode();
        BindingMethods.INSTANCE.getStateCodeFromApi(this);
        BindingMethods.INSTANCE.saveDate(this);
//        loadStateCity();
//        setDate();
    }

    private void setDate() {
        getDataManager().setAppDate(General.defaultDate());
    }

    private void setLaunchMode() {
        getDataManager().setCurrentUserLoggedInMode(DataManager.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT);
    }

    public void onNextClick(){
        getNavigator().onNextClick();
    }

    public void onSkipClick(){
        getNavigator().onSkipClick();
    }

    private void apisFlag() {
        getCompositeDisposable().add(getDataManager()
                .countryCode()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response.getStatus().equals("1")) {
                        setToDb(response.getList());
                    }
                },throwable -> getNavigator().handleError(throwable)));
    }

    private void setToDb(List<Flag> list){
        getCompositeDisposable().add(getDataManager()
                .saveFlagsList(list)
                .doOnComplete(() -> Logger.d("Success save flag"))
                .doOnError(throwable -> getNavigator().handleError(throwable))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    //Inserted
                },throwable -> getNavigator().handleError(throwable)));
    }

    //-------------Additional task-------------

    //-----------------------------------

    private void loadStateCity() {
        getCompositeDisposable().add(getDataManager()
                .getAreaCodeApi()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if (response != null && response.getStatus() == 1) {
                        saveStateCity(response.getData());
                    }
                }, throwable -> getNavigator().handleError(throwable)));
    }

    private void saveStateCity(List<StatesAndCity> data) {
        getCompositeDisposable().add(getDataManager()
                .saveStateCityList(data)
                .doOnComplete(() -> Logger.d("Success save jobs tabs"))
                .doOnError(throwable -> getNavigator().handleError(throwable))
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    //Inserted
                },throwable -> getNavigator().handleError(throwable)));
    }
}
