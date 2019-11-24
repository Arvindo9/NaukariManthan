package com.solution.naukarimanthan.firebase;

import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/23/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Module
public class FirebaseModule {

    @Provides
    FirebaseViewModel provideFirebaseViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new FirebaseViewModel(dataManager, schedulerProvider);
    }
}
