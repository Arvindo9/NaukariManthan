package com.solution.naukarimanthan.ui.dashboard.jobs;

import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;

import com.solution.naukarimanthan.base.ViewModelProviderFactory;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.ui.dashboard.jobs.adapter.JobsAdapter;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/9/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Module
public class JobsModule {

    public static final String TAG = JobsModule.class.getSimpleName();

    @Provides
    JobsViewModel provideJobsViewModel(DataManager dataManager,
                                      SchedulerProvider schedulerProvider) {

        return new JobsViewModel(dataManager, schedulerProvider);
    }

//    @Provides
//    JobsAdapter provideJobsAdapter(Context context, JobsViewModel viewModel) {
//        return new JobsAdapter(context, new ArrayList<>(), viewModel);
//    }

    @Provides
    JobsAdapter provideJobsAdapter(Context context, JobsFragment jobsFragment, JobsViewModel viewModel) {
        return new JobsAdapter(context, jobsFragment, new ArrayList<>(), viewModel);
    }

    @Provides
    ViewModelProvider.Factory provideJobsViewModelFactory(JobsViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManager(JobsFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
