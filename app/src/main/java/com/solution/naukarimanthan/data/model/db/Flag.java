package com.solution.naukarimanthan.data.model.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.solution.naukarimanthan.R;
import com.squareup.picasso.Picasso;


/**
 * Author : Arvindo Mondal
 * Email : arvindomondal@gmail.com
 * Created on : 01-Nov-18
 */
@Entity(tableName = "flag")
public class Flag {

    @Expose
    @PrimaryKey
    public Long _id;

    @SerializedName("Id")
    @Expose
    @ColumnInfo(name = "Id")
    private String id;
    @SerializedName("CountryName")
    @Expose
    @ColumnInfo(name = "CountryName")
    private String countryName;
    @SerializedName("ISOCode1")
    @Expose
    @ColumnInfo(name = "ISOCode1")
    private String iSOCode1;
    @SerializedName("ISOCode2")
    @Expose
    @ColumnInfo(name = "ISOCode2")
    private String iSOCode2;
    @SerializedName("Capital")
    @Expose
    @ColumnInfo(name = "Capital")
    private String capital;
    @SerializedName("PhonePrefix")
    @Expose
    @ColumnInfo(name = "PhonePrefix")
    private String phonePrefix;
    @SerializedName("ImagePath")
    @Expose
    @ColumnInfo(name = "updated_at")
    private String imagePath;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }



    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getISOCode1() {
        return "("+iSOCode1+")";
    }

    public void setISOCode1(String iSOCode1) {
        this.iSOCode1 = iSOCode1;
    }

    public String getISOCode2() {
        return iSOCode2;
    }

    public void setISOCode2(String iSOCode2) {
        this.iSOCode2 = iSOCode2;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public String getPhonePrefix() {

        if(phonePrefix.contains("+")){
            return phonePrefix;
        }else{
            return "+"+phonePrefix;
        }

    }

    public void setPhonePrefix(String phonePrefix) {
        this.phonePrefix = phonePrefix;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
