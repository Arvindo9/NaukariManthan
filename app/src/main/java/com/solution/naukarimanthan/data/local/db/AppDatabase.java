package com.solution.naukarimanthan.data.local.db;


import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

import com.solution.naukarimanthan.data.local.db.dao.FlagDao;
import com.solution.naukarimanthan.data.local.db.dao.JobItemDataDao;
import com.solution.naukarimanthan.data.local.db.dao.JobsDataDao;
import com.solution.naukarimanthan.data.local.db.dao.JobsMenuDao;
import com.solution.naukarimanthan.data.local.db.dao.NotificationTableDao;
import com.solution.naukarimanthan.data.local.db.dao.SearchTableDao;
import com.solution.naukarimanthan.data.local.db.dao.StateAndCityDao;
import com.solution.naukarimanthan.data.local.db.utils.DateConverter;
import com.solution.naukarimanthan.data.model.db.Flag;
import com.solution.naukarimanthan.data.model.db.job$item.JobItem;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;
import com.solution.naukarimanthan.data.model.db.notification.Notification;
import com.solution.naukarimanthan.data.model.db.search.SearchTable;
import com.solution.naukarimanthan.data.model.db.states$city.StatesAndCity;

/**
 * Author       : Arvindo Mondal
 * Created on   : 23-12-2018
 * Email        : arvindomondal@gmail.com
 */
@Database(entities = {Flag.class, JobsMenuTabs.class, JobsData.class, SearchTable.class,
        StatesAndCity.class, JobItem.class, Notification.class}, version = 5, exportSchema = false)
@TypeConverters({DateConverter.class})
public abstract class AppDatabase extends RoomDatabase{

    public abstract FlagDao flagDao();

    public abstract JobsMenuDao jobsMenuDao();

    public abstract JobsDataDao jobsDataDao();

    public abstract StateAndCityDao stateAndCityDao();

    public abstract SearchTableDao searchTableDao();

    public abstract JobItemDataDao jobItemDataDao();

    public abstract NotificationTableDao notificationTableDao();
}
