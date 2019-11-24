package com.solution.naukarimanthan.data.local.db;

import com.solution.naukarimanthan.data.model.db.Flag;
import com.solution.naukarimanthan.data.model.db.job$item.JobItem;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.data.model.db.notification.Notification;
import com.solution.naukarimanthan.data.model.db.search.SearchTable;
import com.solution.naukarimanthan.data.model.db.states$city.StatesAndCity;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Flowable;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
@Singleton
public class Database implements DatabaseService {


    private final AppDatabase appDatabase;

    @Inject
    public Database(AppDatabase appDatabase){
        this.appDatabase = appDatabase;
    }

    @Override
    public Flowable<List<Flag>> getAllFlags() {
        return Flowable.fromCallable(() -> appDatabase.flagDao().loadAllFlag());
    }

    @Override
    public Flowable<Boolean> saveFlagsList(List<Flag> flagList) {
        return Flowable.fromCallable(() -> {
            appDatabase.flagDao().insertAll(flagList);
            return true;
        });
    }

    @Override
    public Flowable<List<JobsMenuTabs>> getAllJobMenus() {
        return Flowable.fromCallable(() -> appDatabase.jobsMenuDao().loadAllJobsMenu());
    }

    @Override
    public Flowable<List<JobsMenuTabs>> getTop3MenuId() {
        return Flowable.fromCallable(() -> appDatabase.jobsMenuDao().getTop3MenuId());
    }

    @Override
    public Flowable<Boolean> updateMenuPriorityByMenuId(int menuId) {
        return Flowable.fromCallable(() -> {
            appDatabase.jobsMenuDao().updateMenuBySearchMenuId(menuId);
            return true;
        });
    }

    @Override
    public Flowable<Boolean> saveJobMenusList(List<JobsMenuTabs> jobsMenuTabs) {
        return Flowable.fromCallable(() -> {
            appDatabase.jobsMenuDao().insertAll(jobsMenuTabs);
            return true;
        });
    }

    @Override
    public Flowable<Boolean> saveJobsData(List<JobsData> jobsDataList) {
        return Flowable.fromCallable(() -> {
            appDatabase.jobsDataDao().insertAll(jobsDataList);
            return true;
        });
    }

    @Override
    public Flowable<Boolean> saveStateCityList(List<StatesAndCity> stateCityList) {
        return Flowable.fromCallable(() -> {
            appDatabase.stateAndCityDao().insertAll(stateCityList);
            return true;
        });
    }

    @Override
    public Flowable<List<JobsData>> getJobsData() {
        return Flowable.fromCallable(() -> appDatabase.jobsDataDao().loadAllJobsData());
    }

    @Override
    public Flowable<List<JobsData>> getJobsDataById(int menuId) {
        return Flowable.fromCallable(() -> appDatabase.jobsDataDao().loadAllJobsByMenuId(menuId));
    }

    @Override
    public Flowable<List<JobsData>> getRecentJobs() {
        return Flowable.fromCallable(() -> appDatabase.jobsDataDao().loadRecentJobs());
    }

    @Override
    public Flowable<List<JobsData>> getRecommendedJobs() {
        return Flowable.fromCallable(() -> appDatabase.jobsDataDao().loadRecommendedJobs());
    }

    @Override
    public Flowable<List<JobsData>> getLocalJobs() {
        return Flowable.fromCallable(() -> appDatabase.jobsDataDao().loadLocalJobs());
    }

    @Override
    public Flowable<List<JobsData>> getPopularJobs() {
        return Flowable.fromCallable(() -> appDatabase.jobsDataDao().loadPolularJobs());
    }

    @Override
    public Flowable<List<StatesAndCity>> getAllStateCity() {
        return Flowable.fromCallable(() -> appDatabase.stateAndCityDao().getAllstateCity());
    }

    @Override
    public Flowable<Boolean> saveSearchTablesList(List<SearchTable> searchTableList) {
        return Flowable.fromCallable(() -> {
            appDatabase.searchTableDao().insertAll(searchTableList);
            return true;
        });
    }

    @Override
    public Flowable<Boolean> saveJobItm(JobItem jobItem) {
        return Flowable.fromCallable(() -> {
            appDatabase.jobItemDataDao().insert(jobItem);
            return true;
        });
    }

    @Override
    public Flowable<JobItem> loadJobItmShort(int jobId) {
        return Flowable.fromCallable(() -> appDatabase.jobItemDataDao().loadJobItmShort(jobId));
    }

    @Override
    public Flowable<JobItem> loadJobItmLong(int jobId, int shortDescriptionId) {
        return Flowable.fromCallable(() -> appDatabase.jobItemDataDao().loadJobItmLong(jobId, shortDescriptionId));
    }

    @Override
    public Flowable<List<Notification>> loadAllNotification() {
        return Flowable.fromCallable(() -> appDatabase.notificationTableDao().loadAllNotification());
    }

    @Override
    public Flowable<Boolean> saveNotification(Notification notification) {
        return Flowable.fromCallable(() -> {
            appDatabase.notificationTableDao().insert(notification);
            return true;
        });
    }
}
