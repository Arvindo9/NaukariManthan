package com.solution.naukarimanthan.ui.special_jobs;

import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/19/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Module
public class SpecialJobsModule {

    @Provides
    SpecialJobsViewMode provideSpecialJobsViewMode(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new SpecialJobsViewMode(dataManager, schedulerProvider);
    }
}
