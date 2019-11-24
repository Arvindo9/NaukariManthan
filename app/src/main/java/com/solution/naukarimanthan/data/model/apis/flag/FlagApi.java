package com.solution.naukarimanthan.data.model.apis.flag;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.naukarimanthan.data.model.db.Flag;

/**
 * Author       : Arvindo Mondal
 * Created on   : 25-12-2018
 * Email        : arvindomondal@gmail.com
 */
public class FlagApi {
    @SerializedName("Status")
    @Expose
    private String status;
    @SerializedName("Message")
    @Expose
    private String message;

    @SerializedName("List")
    @Expose
    private java.util.List<Flag> list = null;

    public java.util.List<Flag> getList() {
        return list;
    }

    public void setList(java.util.List<Flag> list) {
        this.list = list;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
