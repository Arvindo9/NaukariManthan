package com.solution.naukarimanthan.di.builder;

import com.solution.naukarimanthan.firebase.FirebaseModule;
import com.solution.naukarimanthan.firebase.MyFirebaseMessagingService;
import com.solution.naukarimanthan.ui.category.CategoryActivity;
import com.solution.naukarimanthan.ui.category.CategoryModule;
import com.solution.naukarimanthan.ui.dashboard.Dashboard;
import com.solution.naukarimanthan.ui.dashboard.DashboardModule;
import com.solution.naukarimanthan.ui.dashboard.home.HomeProvider;
import com.solution.naukarimanthan.ui.dashboard.jobs.JobsProvider;
import com.solution.naukarimanthan.ui.jobs_item.JobItemActivity;
import com.solution.naukarimanthan.ui.jobs_item.JobItemModule;
import com.solution.naukarimanthan.ui.special_jobs.SpecialJobsActivity;
import com.solution.naukarimanthan.ui.special_jobs.SpecialJobsModule;
import com.solution.naukarimanthan.ui.launcher.access.login.LoginActivity;
import com.solution.naukarimanthan.ui.launcher.access.login.LoginModule;
import com.solution.naukarimanthan.ui.launcher.splash.SplashActivity;
import com.solution.naukarimanthan.ui.launcher.splash.SplashActivityModule;
import com.solution.naukarimanthan.ui.launcher.welcome.WelcomeActivity;
import com.solution.naukarimanthan.ui.launcher.welcome.WelcomeModule;
import com.solution.naukarimanthan.ui.location.LocationActivity;
import com.solution.naukarimanthan.ui.location.LocationModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = SplashActivityModule.class)
    abstract SplashActivity bindSplashActivity();

    @ContributesAndroidInjector(modules = WelcomeModule.class)
    abstract WelcomeActivity bindWelcomeActivity();

    @ContributesAndroidInjector(modules = LocationModule.class)
    abstract LocationActivity bindLocationActivity();

    @ContributesAndroidInjector(modules = LoginModule.class)
    abstract LoginActivity bindLoginActivity();

    @ContributesAndroidInjector(modules = CategoryModule.class)
    abstract CategoryActivity bindCategoryActivity();

    @ContributesAndroidInjector(modules = {SpecialJobsModule.class,
            JobsProvider.class})
    abstract SpecialJobsActivity bindSpecialJobsActivity();

    @ContributesAndroidInjector(modules = JobItemModule.class)
    abstract JobItemActivity bindJobItemActivity();

    @ContributesAndroidInjector(modules = {
            DashboardModule.class,
            JobsProvider.class,
            HomeProvider.class})
    abstract Dashboard bindDashboardActivity();

    @ContributesAndroidInjector(modules = FirebaseModule.class)
    abstract MyFirebaseMessagingService bindFirebaseMessagingService();
}
