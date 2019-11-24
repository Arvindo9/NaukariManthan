package com.solution.naukarimanthan.ui.dashboard.home.adapter;

import android.databinding.ObservableField;

import com.solution.naukarimanthan.data.model.db.jobs.JobsData;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/14/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class HomeAdapterViewModel {

    private final JobsData model;

    public final ObservableField<Integer> id;
    public final ObservableField<String> title;
    public final ObservableField<String> aboutJob;
    public final ObservableField<String> menu;
    public final ObservableField<String> date;
    public final ObservableField<String> time;
    public final ObservableField<Integer> menuId;
    public final ObservableField<String> imageUrl;
    public final ObservableField<String> mobileImgBanner;

    public HomeAdapterViewModel(JobsData model){
        this.model = model;
        this.id = new ObservableField<>(model.getId());
        this.title = new ObservableField<>(model.getTitle());
        this.aboutJob = new ObservableField<>(model.getAboutJob());
        this.menu = new ObservableField<>(model.getMenu());
        this.date = new ObservableField<>(model.getDate());
        this.time = new ObservableField<>(model.getTime());
        this.menuId = new ObservableField<>(model.getMenuId());
        this.imageUrl = new ObservableField<>(model.getImageUrl());
        this.mobileImgBanner = new ObservableField<>(model.getMobileImgBanner());
    }
}
