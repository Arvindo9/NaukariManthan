package com.solution.naukarimanthan.ui.jobs_item;

import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/21/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Module
public class JobItemModule {

    @Provides
    JobItemViewModel provideJobItemViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new JobItemViewModel(dataManager, schedulerProvider);
    }
}
