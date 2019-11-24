package com.solution.naukarimanthan.ui.category;

import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;

import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.utils.BindingMethods;
import com.solution.naukarimanthan.utils.BindingUtils;
import com.solution.naukarimanthan.utils.Logger;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/17/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class CategoryViewModel extends BaseViewModel<CategoryNavigator> {

    private static final String TAG = CategoryViewModel.class.getSimpleName();
    public final ObservableList<JobsMenuTabs> menuTabsArrayList =
            new ObservableArrayList<>();

//    public final ObservableField<String> areaLocation = new
//            ObservableField<>();

    private final MutableLiveData<List<JobsMenuTabs>> menuTabsLiveData;
    public static final MutableLiveData<String> areaLiveData = new MutableLiveData<>();

    public CategoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
        menuTabsLiveData = new MutableLiveData<>();
        loadJobsMenusTabs();
        setLocation();
    }

    public void updateArea(String area){
            BindingUtils.areaLocation.set(area);
    }

    private void setLocation(){
        String area = getDataManager().getStateName();
        if(area == null || area.equals("")){
            areaLiveData.setValue("Select for auto update location");
        }else {
            areaLiveData.setValue(getDataManager().getStateName());
        }
    }

    public final MutableLiveData<List<JobsMenuTabs>> getMenuListLiveData() {
        return menuTabsLiveData;
    }

    public void addMenuItemsToList(List<JobsMenuTabs> menus) {
        menuTabsArrayList.clear();
        menuTabsArrayList.addAll(menus);
    }

    public ObservableList<JobsMenuTabs> getMenusObservableArrayList() {
        return menuTabsArrayList;
    }

    public MutableLiveData<String> getAreaLiveData() {
        return areaLiveData;
    }



    //Menu list----------------------------------------

    //1 First load menu from db and get current menuID
    private void loadJobsMenusTabs() {
        setIsLoading(true);
        getCompositeDisposable().add(getDataManager()
                .getAllJobMenus()
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().ui())
                .subscribe(response -> {
                    if(response != null && !response.isEmpty()){
                        menuTabsLiveData.setValue(response);
                    }
                    else{
                        BindingMethods.INSTANCE.checkMenuListInDb(this);
                    }
                    setIsLoading(false);
                },throwable ->{
                    setIsLoading(false);
                    Logger.d("Failed to Load menus");
                }));
    }

    //Additional task----------------------------------

    public void onHomeButtonClick(){
    }

    public void onCategoryClick(){
    }

    public void onRefreshClick(){
        loadJobsMenusTabs();
        getNavigator().onCheckInternetConnection();
        setLocation();
    }

    public void onLocationButtonClick(){
        getNavigator().onLocationButtonClick();
    }
}
