package com.solution.naukarimanthan.data.model.apis.jobs.tabs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.naukarimanthan.data.model.db.menu.JobsMenuTabs;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/11/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class JobsMenuTabsResponse {
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<JobsMenuTabs> jobsMenu = null;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<JobsMenuTabs> getData() {
        return jobsMenu;
    }

    public void setData(List<JobsMenuTabs> jobsMenu) {
        this.jobsMenu= jobsMenu;
    }

}
