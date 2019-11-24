package com.solution.naukarimanthan.ui.category;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.ui.category.adapter.CategoryAdapter;
import com.solution.naukarimanthan.ui.category.adapter.CategoryAdapterViewModel;
import com.solution.naukarimanthan.ui.dashboard.home.HomeFragment;
import com.solution.naukarimanthan.ui.dashboard.home.recent$jobs.RecentJobsAdapter;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/17/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Module
public class CategoryModule {

    @Provides
    CategoryViewModel provideCategoryViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new CategoryViewModel(dataManager, schedulerProvider);
    }

    @Provides
    CategoryAdapter provideCategoryAdapterAdapter() {
        return new CategoryAdapter(new ArrayList<>());
    }
}
