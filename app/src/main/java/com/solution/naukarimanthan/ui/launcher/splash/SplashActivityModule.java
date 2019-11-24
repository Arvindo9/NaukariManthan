package com.solution.naukarimanthan.ui.launcher.splash;

import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
@Module
public class SplashActivityModule {

    @Provides
    SplashViewModel provideSplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new SplashViewModel(dataManager, schedulerProvider);
    }
}
