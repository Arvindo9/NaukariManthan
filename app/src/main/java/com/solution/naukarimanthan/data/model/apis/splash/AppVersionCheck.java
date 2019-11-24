package com.solution.naukarimanthan.data.model.apis.splash;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author       : Arvindo Mondal
 * Created on   : 24-12-2018
 * Email        : arvindomondal@gmail.com
 */
public class AppVersionCheck {

    @SerializedName("AppVersion")
    @Expose
    private String appVersion;
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Message")
    @Expose
    private String message;

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
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
