package com.solution.naukarimanthan.ui.dashboard.home;


import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-01-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 */
@Module
public abstract class HomeProvider {

    @ContributesAndroidInjector(modules = HomeModule.class)
    abstract HomeFragment provideHomeFragmentFactory();
}
