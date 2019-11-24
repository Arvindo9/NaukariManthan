package com.solution.naukarimanthan.data.model.db.states$city;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 1/15/2019
 * Company : Roundpay Techno Media OPC Pvt Ltd
 * Designation : Programmer and Developer
 */
@Entity(tableName = "statesAndCity")
public class StatesAndCity {

    @Expose
    @PrimaryKey(autoGenerate = true)
    public Long _id;

    @SerializedName("StateId")
    @Expose
    private Integer stateId;
    @SerializedName("StateName")
    @Expose
    private String stateName;
    @SerializedName("CountryId")
    @Expose
    private Integer countryId;
    @SerializedName("State")
    @Expose
    private Integer state;
    @SerializedName("EntryDate")
    @Expose
    private String entryDate;

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public Integer getCountryId() {
        return countryId;
    }

    public void setCountryId(Integer countryId) {
        this.countryId = countryId;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }

    public Long get_id() {
        return _id;
    }

    public void set_id(Long _id) {
        this._id = _id;
    }
}
