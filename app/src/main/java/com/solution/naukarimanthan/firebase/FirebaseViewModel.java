package com.solution.naukarimanthan.firebase;

import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.data.model.db.notification.Notification;
import com.solution.naukarimanthan.utils.BindingMethods;
import com.solution.naukarimanthan.utils.Logger;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.util.Map;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/23/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class FirebaseViewModel extends BaseViewModel<FireBaseNavigator> {

    public FirebaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public void saveData(Map<String, String> data) {


        Notification notification = new Notification(
                                    data.get("MenuId"),
                                    data.get("Image"),
                                    data.get("Url"),
                                    data.get("JobTitle"),
                                    data.get("Key"),
                                    data.get("Date"),
                                    data.get("Menu"),
                                    data.get("Type"));

        getCompositeDisposable().add(getDataManager()
                .saveNotification(notification)
                .subscribeOn(getSchedulerProvider().io())
                .observeOn(getSchedulerProvider().newThread())
                .subscribe(response -> {
                    if(response){
                        Logger.e("FirebaseViewModel", " SAVED notification yes");
                    }
                    else {
                        Logger.e("FirebaseViewModel", " SAVED notification fail");
                    }
                },throwable -> Logger.e("FirebaseViewModel", " SAVED notification fail")));
    }
}
