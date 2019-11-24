package com.solution.naukarimanthan.data.model.apis.states$city;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.naukarimanthan.data.model.db.states$city.StatesAndCity;

import java.util.List;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/15/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
public class ResponseStateCity {

    @SerializedName("data")
    @Expose
    private List<StatesAndCity> data = null;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Message")
    @Expose
    private String message;

    public List<StatesAndCity> getData() {
        return data;
    }

    public void setData(List<StatesAndCity> data) {
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
