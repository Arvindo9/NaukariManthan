package com.solution.naukarimanthan.ui.dashboard;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.ui.dashboard.notification.NotificationAdapter;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/8/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Module
public class DashboardModule {

    @Provides
    DashboardPagerAdapter provideDashboardPagerAdapter(Dashboard activity) {
        return new DashboardPagerAdapter(activity.getSupportFragmentManager());
    }

    @Provides
    DashboardViewModel provideDashboardModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new DashboardViewModel(dataManager, schedulerProvider);
    }

    @Provides
    NotificationAdapter provideNotificationAdapter() {
        return new NotificationAdapter(new ArrayList<>());
    }
}
