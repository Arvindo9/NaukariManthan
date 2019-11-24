package com.solution.naukarimanthan.ui.launcher.access.login;

import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import dagger.Module;
import dagger.Provides;

/**
 * Author       : Arvindo Mondal
 * Created on   : 28-12-2018
 * Email        : arvindomondal@gmail.com
 */
@Module
public class LoginModule {
    @Provides
    LoginViewModel provideSplashViewModel(DataManager dataManager, SchedulerProvider schedulerProvider) {
        return new LoginViewModel(dataManager, schedulerProvider);
    }
}
