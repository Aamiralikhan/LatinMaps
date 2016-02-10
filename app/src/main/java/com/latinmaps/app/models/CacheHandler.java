package com.latinmaps.app.models;

import android.graphics.Bitmap;
import android.widget.TextView;

/**
 * Created by aamir on 2/10/16.
 */
public class CacheHandler {

    private String photoLength, title, distance, shortDescription, travelTiming, businessAddress, businessTiming, businessWebsite, longDescription;

    private Bitmap image;

    public CacheHandler(String photoLength, String title, String distance, String shortDescription, String travelTiming, String businessAddress, String businessTiming, String businessWebsite, String longDescription, Bitmap image) {
        this.photoLength = photoLength;
        this.title = title;
        this.distance = distance;
        this.shortDescription = shortDescription;
        this.travelTiming = travelTiming;
        this.businessAddress = businessAddress;
        this.businessTiming = businessTiming;
        this.businessWebsite = businessWebsite;
        this.longDescription = longDescription;
        this.image = image;
    }

    public String getPhotoLength() {
        return photoLength;
    }

    public void setPhotoLength(String photoLength) {
        this.photoLength = photoLength;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public String getTravelTiming() {
        return travelTiming;
    }

    public void setTravelTiming(String travelTiming) {
        this.travelTiming = travelTiming;
    }

    public String getBusinessAddress() {
        return businessAddress;
    }

    public void setBusinessAddress(String businessAddress) {
        this.businessAddress = businessAddress;
    }

    public String getBusinessTiming() {
        return businessTiming;
    }

    public void setBusinessTiming(String businessTiming) {
        this.businessTiming = businessTiming;
    }

    public String getBusinessWebsite() {
        return businessWebsite;
    }

    public void setBusinessWebsite(String businessWebsite) {
        this.businessWebsite = businessWebsite;
    }

    public String getLongDescription() {
        return longDescription;
    }

    public void setLongDescription(String longDescription) {
        this.longDescription = longDescription;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
