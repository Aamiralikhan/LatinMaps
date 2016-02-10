package com.latinmaps.app.models;

import java.util.ArrayList;

/**
 * Created by Administrator on 1/22/2016.
 */
public class CountryListModel {

    private String countryName;
    private String countryCode;

    public CountryListModel(String countryName, String countryCode) {
        this.setCountryName(countryName);
        this.setCountryCode(countryCode);
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }
}

