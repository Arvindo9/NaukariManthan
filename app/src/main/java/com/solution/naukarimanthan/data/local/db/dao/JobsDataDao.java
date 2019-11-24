package com.solution.naukarimanthan.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.solution.naukarimanthan.data.model.db.jobs.JobsData;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/11/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Dao
public interface JobsDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(JobsData jobsData);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<JobsData> jobsDataList);


    @Query("SELECT * FROM jobsData order by _id desc limit 300")
    List<JobsData> loadAllJobsData();


    @Query("SELECT * FROM jobsData where menuId = :menuId order by _id desc limit 300")
    List<JobsData> loadAllJobsByMenuId(int menuId);

    @Query("SELECT * FROM jobsData where jobSearchType = 2 order by _id desc limit 100")
    List<JobsData> loadRecommendedJobs();

    @Query("SELECT * FROM jobsData where jobSearchType = 4 order by _id desc limit 100")
    List<JobsData> loadLocalJobs();

    @Query("SELECT * FROM jobsData where jobSearchType = 1 order by _id desc limit 100")
    List<JobsData> loadRecentJobs();

    @Query("SELECT * FROM jobsData where jobSearchType = 3 order by _id desc limit 100")
    List<JobsData> loadPolularJobs();
}
