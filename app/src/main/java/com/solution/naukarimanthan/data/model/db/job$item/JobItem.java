package com.solution.naukarimanthan.data.model.db.job$item;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/21/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Entity(tableName = "jobsItemData")
public class JobItem {
    @PrimaryKey(autoGenerate = true)
    public Long _id;

    @SerializedName("shortdesc")
    @Expose
    private String shortdesc;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("aboutJob")
    @Expose
    private String aboutJob;
    @SerializedName("Menu")
    @Expose
    private String menu;
    @SerializedName("MenuId")
    @Expose
    private Integer menuId;
    @SerializedName("JobId")
    @Expose
    private Integer jobId;
    @SerializedName("shordescId")
    @Expose
    private Integer shordescId;
    @SerializedName("longdesc")
    @Expose
    private String longdesc;

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;


    public String getLongdesc() {
        return longdesc;
    }

    public void setLongdesc(String longdesc) {
        this.longdesc = longdesc;
    }
    public String getShortdesc() {
        return shortdesc;
    }

    public void setShortdesc(String shortdesc) {
        this.shortdesc = shortdesc;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAboutJob() {
        return aboutJob;
    }

    public void setAboutJob(String aboutJob) {
        this.aboutJob = aboutJob;
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

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public Integer getShordescId() {
        return shordescId;
    }

    public void setShordescId(Integer shordescId) {
        this.shordescId = shordescId;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
