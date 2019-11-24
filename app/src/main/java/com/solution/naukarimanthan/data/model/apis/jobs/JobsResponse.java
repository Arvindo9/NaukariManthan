
package com.solution.naukarimanthan.data.model.apis.jobs;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.naukarimanthan.data.model.db.jobs.JobsData;

import java.util.List;

public class JobsResponse {
    @SerializedName("Status")
    @Expose
    private Integer status;
    @SerializedName("Message")
    @Expose
    private String message;
    @SerializedName("Data")
    @Expose
    private List<JobsData> data = null;

    @SerializedName(value = "MaxPage", alternate = "Maxpage")
    @Expose
    private Integer maxPage;
    @SerializedName("Menu")
    @Expose
    private String menu;
    @SerializedName("MenuId")
    @Expose
    private Integer menuId;
    @SerializedName("pageno")
    @Expose
    private Integer pageno;

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

    public List<JobsData> getData() {
        return data;
    }

    public void setData(List<JobsData> data) {
        this.data = data;
    }

    public Integer getMaxPage() {
        return maxPage;
    }

    public void setMaxPage(Integer maxPage) {
        this.maxPage = maxPage;
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

    public Integer getPageno() {
        return pageno;
    }

    public void setPageno(Integer pageno) {
        this.pageno = pageno;
    }


}
