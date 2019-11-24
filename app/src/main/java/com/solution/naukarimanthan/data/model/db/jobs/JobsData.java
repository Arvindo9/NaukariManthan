package com.solution.naukarimanthan.data.model.db.jobs;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BindingAdapter;
import android.text.Html;
import android.widget.ImageView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.naukarimanthan.R;
import com.squareup.picasso.Picasso;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/11/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Entity(tableName = "jobsData")
public class JobsData {
    @Expose
    @PrimaryKey(autoGenerate = true)
    public Long _id;

    @SerializedName("Id")
    @Expose
    private Integer id;
    @SerializedName("Title")
    @Expose
    private String title;
    @SerializedName("aboutJob")
    @Expose
    private String aboutJob;
    @SerializedName("Menu")
    @Expose
    private String menu;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("MenuId")
    @Expose
    private Integer menuId;
    @SerializedName("ImageUrl")
    @Expose
    private String imageUrl;


    @SerializedName("mobileImgBanner")
    @Expose
    private String mobileImgBanner;

    @Expose
    private int jobSearchType = 0;

    public String getMobileImgBanner() {
        return mobileImgBanner;
    }

    public void setMobileImgBanner(String mobileImgBanner) {
        this.mobileImgBanner = mobileImgBanner;
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return Html.fromHtml(title).toString();
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAboutJob() {
        return Html.fromHtml(aboutJob).toString();
    }

    public void setAboutJob(String aboutJob) {
        this.aboutJob = aboutJob;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getJobSearchType() {
        return jobSearchType;
    }

    public void setJobSearchType(int jobSearchType) {
        this.jobSearchType = jobSearchType;
    }
}
