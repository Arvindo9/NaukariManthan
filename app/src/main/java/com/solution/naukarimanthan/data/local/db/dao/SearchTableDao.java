package com.solution.naukarimanthan.data.local.db.dao;


import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.solution.naukarimanthan.data.model.db.search.SearchTable;

import java.util.List;

/**
 * Author       : Arvindo Mondal
 * Created on   : 15-01-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 * Skills       : Algorithms and logic
 * Project URL  :
 */
@Dao
public interface SearchTableDao {
    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void insert(SearchTable searchTables);


    @Insert(onConflict = OnConflictStrategy.ROLLBACK)
    void insertAll(List<SearchTable> searchTablesList);


    @Query("SELECT * FROM searchTable")
    List<SearchTable> loadAllsearchTableData();

    @Query("select menuId from " +
            "(select * from searchtable ORDER by _id desc limit 500) as tb" +
            " group by menuId order by count(menuId) desc ")
    List<Integer> maximumSearchedMemuIdMulti();

    @Query("select menuId from " +
            "(select * from searchtable ORDER by _id desc limit 500) as tb " +
            "group by menuId order by count(menuId) desc Limit 1")
    Integer maximumSearchedMemuId();


    /*@Query("select searchText, count(searchText) as CountT from " +
            "(select * from searchtable ORDER by _id desc limit 500) as tb group by" +
            " searchText order by count(searchText) desc ")*/

    @Query("select searchText from " +
            "(select * from searchtable ORDER by _id desc limit 500) as tb group by" +
            " searchText order by count(searchText) desc ")
    List<String> maximumSearchedTextMulti();

    @Query("select searchText from " +
            "(select * from searchtable ORDER by _id desc limit 500) as tb group by" +
            " searchText order by count(searchText) desc limit 1")
    String maximumSearchedText();

    @Query("select searchUrl from " +
            "(select * from searchtable ORDER by _id desc limit 500) as tb group by" +
            " searchUrl order by count(searchUrl) desc ")
    List<String> maximumSearchUrlMulti();

    @Query("select searchUrl from " +
            "(select * from searchtable ORDER by _id desc limit 500) as tb group by" +
            " searchUrl order by count(searchUrl) desc limit 1")
    String maximumSearchUrl();

    @Query("SELECT menu FROM searchTable")
    String activeTime();

    @Query("SELECT menu FROM searchTable")
    String activeTimeDayNight();

    @Query("SELECT menu FROM searchTable")
    String activeTimeDelay();
}
