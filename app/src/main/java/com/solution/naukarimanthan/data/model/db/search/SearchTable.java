package com.solution.naukarimanthan.data.model.db.search;


import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;

import java.util.Date;

/**
 * Author       : Arvindo Mondal
 * Created on   : 15-01-2019
 * Email        : arvindomondal@gmail.com
 * Designation  : Programmer
 */

@Entity(tableName = "searchTable")
public class SearchTable {
    @Expose
    @PrimaryKey(autoGenerate = true)
    private Long _id;


    @ColumnInfo(name = "MenuId")
    public Integer menuId ;

    @ColumnInfo(name = "Menu")
    public String menu;

    @ColumnInfo(name = "SearchText")
    public String searchText;

    @ColumnInfo(name = "SearchUrl")
    public String searchUrl;

    @ColumnInfo(name = "ActiveTime")
    public String activeTime;

    @ColumnInfo(name = "ActiveTimeDelay")
    public String activeTimeDelay;


//    @TypeConverters(DateConverter.class)
    @ColumnInfo(name = "DateTime")
    public Date dateTime;

    public SearchTable(){
    }


    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public String getSearchText() {
        return searchText;
    }

    public void setSearchText(String searchText) {
        this.searchText = searchText;
    }

    public String getSearchUrl() {
        return searchUrl;
    }

    public void setSearchUrl(String searchUrl) {
        this.searchUrl = searchUrl;
    }

    public String getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(String activeTime) {
        this.activeTime = activeTime;
    }

    public String getActiveTimeDelay() {
        return activeTimeDelay;
    }

    public void setActiveTimeDelay(String activeTimeDelay) {
        this.activeTimeDelay = activeTimeDelay;
    }
}
