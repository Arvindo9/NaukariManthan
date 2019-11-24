package com.solution.naukarimanthan.ui.category.adapter;

import android.databinding.ObservableField;

import com.solution.naukarimanthan.base.BaseViewModel;
import com.solution.naukarimanthan.data.AppDataManager;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/17/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class CategoryAdapterViewModel{

    public static final int VIEW_TYPE_EMPTY = 1;
    public static final int VIEW_TYPE_NORMAL = 2;
    public static final int VIEW_TYPE_LOCATION = 3;
    public static final ObservableField<String> textFilter = new ObservableField<>("");

    public final ObservableField<String> textTitle;

    public CategoryAdapterViewModel(JobsMenuTabs data) {
        textTitle = new ObservableField<>(data.getMenu());
//        textTitle.set(data.getMenu());
    }
}
