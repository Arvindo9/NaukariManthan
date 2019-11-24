package com.solution.naukarimanthan.ui.dashboard.notification;

import android.databinding.ObservableField;

import com.solution.naukarimanthan.data.model.db.notification.Notification;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/23/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class NotificationViewModel {

    public static final ObservableField<String> textFilter = new ObservableField<>("");
    public final ObservableField<String> id;
    public final ObservableField<String> title;
    public final ObservableField<String> aboutJob;
    public final ObservableField<String> menu;
    public final ObservableField<String> date;
    public final ObservableField<String> time;
    public final ObservableField<String> menuId;
    public final ObservableField<String> imageUrl;

    public NotificationViewModel(Notification model){
        this.id = new ObservableField<>(model.getKey());
        this.title = new ObservableField<>(model.getJobTitle());
        this.aboutJob = new ObservableField<>(model.getJobTitle());
        this.menu = new ObservableField<>(model.getMenu());
        this.date = new ObservableField<>(model.getDate());
        this.time = new ObservableField<>();
        this.menuId = new ObservableField<>(model.getMenuId());
        this.imageUrl = new ObservableField<>(model.getImage());
    }
}
