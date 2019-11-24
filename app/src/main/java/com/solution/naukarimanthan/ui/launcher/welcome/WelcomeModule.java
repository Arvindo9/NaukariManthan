package com.solution.naukarimanthan.ui.launcher.welcome;


import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Author       : Arvindo Mondal
 * Created on   : 25-12-2018
 * Email        : arvindomondal@gmail.com
 */
@Module
public class WelcomeModule {

    @Provides
    WelcomeViewModel provideSplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new WelcomeViewModel(dataManager, schedulerProvider);
    }

    @Provides
    WelcomeAdapter provideFeedPagerAdapter(WelcomeActivity activity) {
        return new WelcomeAdapter(activity);
    }
}
