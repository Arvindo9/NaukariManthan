package com.solution.naukarimanthan.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/11/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Dao
public interface JobsMenuDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(JobsMenuTabs flag);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<JobsMenuTabs> flagList);


    @Query("SELECT * FROM jobsTabs order by priorityTab desc")
    List<JobsMenuTabs> loadAllJobsMenu();


    @Query("update jobsTabs set priorityTab =" +
            " (SELECT max(priorityTab) from (select * from jobsTabs) as b) +1 where menuId = :menuId")
    void updateMenuBySearchMenuId(int menuId);

    @Query("SELECT * FROM jobsTabs order by priorityTab desc limit 3")
    List<JobsMenuTabs> getTop3MenuId();
}
