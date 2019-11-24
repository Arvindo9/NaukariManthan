package com.solution.naukarimanthan.data.local.db;

import com.solution.naukarimanthan.data.model.db.Flag;
import com.solution.naukarimanthan.data.model.db.job$item.JobItem;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.data.model.db.notification.Notification;
import com.solution.naukarimanthan.data.model.db.search.SearchTable;
import com.solution.naukarimanthan.data.model.db.states$city.StatesAndCity;

import java.util.List;

import io.reactivex.Flowable;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
public interface DatabaseService {

    Flowable<List<Flag>> getAllFlags();

    Flowable<Boolean> saveFlagsList(List<Flag> flagList);

    Flowable<Boolean> saveJobsData(List<JobsData> jobsDataList);

    Flowable<Boolean> saveStateCityList(List<StatesAndCity> statesAndCityList);

    Flowable<List<JobsData>> getJobsData();

    Flowable<List<JobsData>> getJobsDataById(int menuId);

    Flowable<List<JobsData>> getRecentJobs();

    Flowable<List<JobsData>> getRecommendedJobs();

    Flowable<List<JobsData>> getLocalJobs();

    Flowable<List<JobsData>> getPopularJobs();

    Flowable<List<StatesAndCity>> getAllStateCity();

    //menu----------------------------

    Flowable<Boolean> saveJobMenusList(List<JobsMenuTabs> jobsMenuTabs);

    Flowable<List<JobsMenuTabs>> getAllJobMenus();

    Flowable<List<JobsMenuTabs>> getTop3MenuId();

    Flowable<Boolean> updateMenuPriorityByMenuId(int menuId);

    //SearchTable----------------------

    Flowable<Boolean> saveSearchTablesList(List<SearchTable> searchTableList);

    //job item-------------------------

    Flowable<Boolean> saveJobItm(JobItem jobItem);

    Flowable<JobItem> loadJobItmShort(int jobId);

    Flowable<JobItem> loadJobItmLong(int jobId, int shortDescriptionId);

    //notification---------------------

    Flowable<List<Notification>> loadAllNotification();

    Flowable<Boolean> saveNotification(Notification notification);

}
