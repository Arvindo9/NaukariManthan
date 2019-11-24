package com.solution.naukarimanthan.data.local.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.solution.naukarimanthan.data.model.db.Flag;

import java.util.List;

/**
 * Author       : Arvindo Mondal
 * Created on   : 25-12-2018
 * Email        : arvindomondal@gmail.com
 */
@Dao
public interface FlagDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Flag flag);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Flag> flagList);


    @Query("SELECT * FROM flag")
    List<Flag> loadAllFlag();
}
