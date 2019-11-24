package com.solution.naukarimanthan.ui.location;

import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/16/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Module
public class LocationModule {

    @Provides
    LocationViewModel provideLocationViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new LocationViewModel(dataManager, schedulerProvider);
    }
}
