package com.solution.naukarimanthan.ui.dashboard.jobs.adapter;

import android.databinding.ObservableField;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.widget.TextView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.data.model.db.notification.Notification;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.util.Locale;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/10/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class JobsAdapterViewModel{

    private JobsData model;

    public static final ObservableField<String> textFilter = new ObservableField<>("");
    public final ObservableField<Integer> id;
    public final ObservableField<String> title;
    public final ObservableField<String> aboutJob;
    public final ObservableField<String> menu;
    public final ObservableField<String> date;
    public final ObservableField<String> time;
    public final ObservableField<Integer> menuId;
    public final ObservableField<String> imageUrl;

    public JobsAdapterViewModel(JobsData model){
        this.model = model;
        this.id = new ObservableField<>(model.getId());
        this.title = new ObservableField<>(model.getTitle());
        this.aboutJob = new ObservableField<>(model.getAboutJob());
        this.menu = new ObservableField<>(model.getMenu());
        this.date = new ObservableField<>(model.getDate());
        this.time = new ObservableField<>(model.getTime());
        this.menuId = new ObservableField<>(model.getMenuId());
        this.imageUrl = new ObservableField<>(model.getImageUrl());
    }

    public JobsAdapterViewModel(Notification model){
        int id = Integer.parseInt(model.getKey());
        this.id = new ObservableField<>(id);
        this.title = new ObservableField<>(model.getJobTitle());
        this.aboutJob = new ObservableField<>(model.getJobTitle());
        this.menu = new ObservableField<>(model.getMenu());
        this.date = new ObservableField<>(model.getDate());
        this.time = new ObservableField<>();
        this.menuId = new ObservableField<>(Integer.parseInt(model.getMenuId()));
        this.imageUrl = new ObservableField<>(model.getImage());
    }
}
