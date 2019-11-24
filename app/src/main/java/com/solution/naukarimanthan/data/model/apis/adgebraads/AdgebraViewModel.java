package com.solution.naukarimanthan.data.model.apis.adgebraads;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdgebraViewModel implements Serializable {

    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("notificationMessage")
    @Expose
    private String notificationMessage;
    @SerializedName("trackerUrl")
    @Expose
    private String trackerUrl;
    @SerializedName("notificationTitle")
    @Expose
    private String notificationTitle;
    @SerializedName("icon")
    @Expose
    private String icon;
    @SerializedName("lifeOfAd")
    @Expose
    private String lifeOfAd;
    @SerializedName("notificationImage")
    @Expose
    private String notificationImage;
    @SerializedName("advId")
    @Expose
    private String advId;
    @SerializedName("campId")
    @Expose
    private String campId;
    @SerializedName("pricingModel")
    @Expose
    private String pricingModel;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("brandingLine")
    @Expose
    private String brandingLine;
    @SerializedName("brandingImageUrl")
    @Expose
    private String brandingImageUrl;
    @SerializedName("clientImpressionTracker")
    @Expose
    private String clientImpressionTracker;
    @SerializedName("uId")
    @Expose
    private String uId;
    @SerializedName("img990x505")
    @Expose
    private String img990x505;

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(String notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public String getTrackerUrl() {
        return trackerUrl;
    }

    public void setTrackerUrl(String trackerUrl) {
        this.trackerUrl = trackerUrl;
    }

    public String getNotificationTitle() {
        return notificationTitle;
    }

    public void setNotificationTitle(String notificationTitle) {
        this.notificationTitle = notificationTitle;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getLifeOfAd() {
        return lifeOfAd;
    }

    public void setLifeOfAd(String lifeOfAd) {
        this.lifeOfAd = lifeOfAd;
    }

    public String getNotificationImage() {
        if(notificationImage!=null && !notificationImage.isEmpty()) {
            return notificationImage;
        }
        else {
            return "";
        }
    }

    public void setNotificationImage(String notificationImage) {
        this.notificationImage = notificationImage;
    }

    public String getAdvId() {
        return advId;
    }

    public void setAdvId(String advId) {
        this.advId = advId;
    }

    public String getCampId() {
        return campId;
    }

    public void setCampId(String campId) {
        this.campId = campId;
    }

    public String getPricingModel() {
        return pricingModel;
    }

    public void setPricingModel(String pricingModel) {
        this.pricingModel = pricingModel;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getBrandingLine() {
        return brandingLine;
    }

    public void setBrandingLine(String brandingLine) {
        this.brandingLine = brandingLine;
    }

    public String getBrandingImageUrl() {
        return brandingImageUrl;
    }

    public void setBrandingImageUrl(String brandingImageUrl) {
        this.brandingImageUrl = brandingImageUrl;
    }

    public String getClientImpressionTracker() {
        return clientImpressionTracker;
    }

    public void setClientImpressionTracker(String clientImpressionTracker) {
        this.clientImpressionTracker = clientImpressionTracker;
    }

    public String getUId() {
        return uId;
    }

    public void setUId(String uId) {
        this.uId = uId;
    }

    public String getImg990x505() {
        return img990x505;
    }

    public void setImg990x505(String img990x505) {
        this.img990x505 = img990x505;
    }

}
