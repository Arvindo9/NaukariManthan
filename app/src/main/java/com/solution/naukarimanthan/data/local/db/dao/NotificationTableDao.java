package com.solution.naukarimanthan.data.local.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.solution.naukarimanthan.data.model.db.notification.Notification;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/23/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */

@Dao
public interface NotificationTableDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Notification notification);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Notification> notificationList);


    @Query("SELECT * FROM notification order by _id desc limit 50")
    List<Notification> loadAllNotification();
}
