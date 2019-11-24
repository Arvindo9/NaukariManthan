package com.solution.naukarimanthan.ui.dashboard.jobs;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/10/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */

@Module
public abstract class JobsProvider {

    @ContributesAndroidInjector(modules = JobsModule.class)
    abstract JobsFragment provideJobsFragmentFactory();
}
