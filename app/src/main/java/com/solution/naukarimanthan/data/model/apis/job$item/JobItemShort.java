package com.solution.naukarimanthan.data.model.apis.job$item;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.naukarimanthan.data.model.db.job$item.JobItem;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/21/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class JobItemShort {
    @SerializedName("data")
    @Expose
    private List<JobItem> data = null;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Message")
    @Expose
    private String message;

    public List<JobItem> getData() {
        return data;
    }

    public void setData(List<JobItem> data) {
        this.data = data;
    }

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
}
