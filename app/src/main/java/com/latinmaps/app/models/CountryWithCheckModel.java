package com.latinmaps.app.models;

/**
 * Created by Administrator on 2/1/2016.
 */
public class CountryWithCheckModel {

    private String countryName;
    private boolean checked;

    public CountryWithCheckModel(String countryName, boolean checked) {
        this.countryName = countryName;
        this.checked = checked;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
