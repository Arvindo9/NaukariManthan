package com.solution.naukarimanthan.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.solution.naukarimanthan.data.model.db.job$item.JobItem;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/21/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Dao
public interface JobItemDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(JobItem jobItemData);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<JobItem> jobItemDataList);

    @Query("SELECT * FROM jobsItemData where jobId = :jobId")
    JobItem loadJobItmShort(int jobId);

    @Query("SELECT * FROM jobsItemData where jobId = :jobId and shordescId = :shortDescriptionId")
    JobItem loadJobItmLong(int jobId, int shortDescriptionId);

}
