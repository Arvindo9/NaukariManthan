package com.solution.naukarimanthan.data.model.db.menu;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/11/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Entity(tableName = "jobsTabs")
public class JobsMenuTabs {
    @Expose
    @PrimaryKey(autoGenerate = true)
    public Long _id;

    @SerializedName("Menu")
    @Expose
    @ColumnInfo(name = "menu")
    private String menu;
    @SerializedName("MenuId")
    @Expose
    @ColumnInfo(name = "menuId")
    private Integer menuId;
    @SerializedName("count")
    @Expose
    @ColumnInfo(name = "count")
    private Integer count;

    @ColumnInfo(name = "priorityTab")
    private int priorityTab;

    public int getPriorityTab() {
        return priorityTab;
    }

    public void setPriorityTab(int priorityTab) {
        this.priorityTab = priorityTab;
    }

    public JobsMenuTabs(String menu, int menuId, int count){
        this.menu = menu;
        this.menuId = menuId;
        this.count = count;
    }

    @Ignore
    public JobsMenuTabs(String menu, int menuId){
        this.menu = menu;
        this.menuId = menuId;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getMenu() {
        return menu;
    }

    public void setMenu(String menu) {
        this.menu = menu;
    }

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
