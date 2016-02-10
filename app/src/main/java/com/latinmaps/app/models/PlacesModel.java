package com.latinmaps.app.models;

/**
 * Created by Administrator on 1/22/2016.
 */
public class PlacesModel {

    private String imgUri;
    private String title;
    private String miniDes;
    private String address;
    private String distance;
    private String idHolder;

    public PlacesModel(String idHolder, String imgUri, String title, String miniDes, String distance, String address) {
        this.setIdHolder(idHolder);
        this.setImgUri(imgUri);
        this.setTitle(title);
        this.setAddress(address);
        this.setMiniDes(miniDes);
        this.setDistance(distance);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUri() {
        return imgUri;
    }

    public void setImgUri(String imgUri) {
        this.imgUri = imgUri;
    }

    public String getMiniDes() {
        return miniDes;
    }

    public void setMiniDes(String miniDes) {
        this.miniDes = miniDes;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getIdHolder() {
        return idHolder;
    }

    public void setIdHolder(String idHolder) {
        this.idHolder = idHolder;
    }
}
