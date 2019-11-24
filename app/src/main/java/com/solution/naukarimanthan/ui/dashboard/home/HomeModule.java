package com.solution.naukarimanthan.ui.dashboard.home;


import android.arch.lifecycle.ViewModelProvider;
import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;

import com.solution.naukarimanthan.base.ViewModelProviderFactory;
import com.solution.naukarimanthan.data.DataManager;
import com.solution.naukarimanthan.ui.dashboard.home.adapter.HomeAdapter;
import com.solution.naukarimanthan.ui.dashboard.home.recent$jobs.RecentJobsAdapter;
import com.solution.naukarimanthan.utils.rx.SchedulerProvider;

import java.util.ArrayList;

import dagger.Module;
import dagger.Provides;

/**
 * Author       : Arvindo Mondal
 * Created on   : 13-01-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * Project URL  :
 */
@Module
public class HomeModule {

    @Provides
    HomeViewModel provideJobsViewModel(DataManager dataManager,
                                       SchedulerProvider schedulerProvider) {

        return new HomeViewModel(dataManager, schedulerProvider);
    }

    @Provides
    HomeAdapter provideHomeAdapter() {
        return new HomeAdapter(new ArrayList<>());
    }

    @Provides
    RecentJobsAdapter provideHomeRecentJobsAdapter(Context context) {
        return new RecentJobsAdapter(context, new ArrayList<>());
    }

    @Provides
    ViewModelProvider.Factory provideHomeViewModelFactory(HomeViewModel viewModel) {
        return new ViewModelProviderFactory<>(viewModel);
    }

    @Provides
    LinearLayoutManager provideLinearLayoutManagerHome(HomeFragment fragment) {
        return new LinearLayoutManager(fragment.getActivity());
    }
}
