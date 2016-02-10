package com.latinmaps.app.utilities;

import java.util.ArrayList;

/**
 * Created by Administrator on 1/18/2016.
 */
public class CountryData {

    ///////////////////////////////////////
    // PRIVATE VAR
    ///////////////////////////////////////

    private ArrayList<String> countryNameList, countryCodeList;
    private static CountryData INSTANCE;

    ///////////////////////////////////////
    // STATIC INSTANCE
    ///////////////////////////////////////

    public static CountryData getInstance(){
        return (INSTANCE == null) ? INSTANCE = new CountryData() : INSTANCE;
    }

    ///////////////////////////////////////
    // CONSTRUCTOR
    ///////////////////////////////////////

    public CountryData(){}

    ///////////////////////////////////////
    // SETTER'S
    ///////////////////////////////////////

    public CountryData setCountryCodeList(ArrayList<String> countryCodeList){
        this.countryCodeList = countryCodeList;
        return this;
    }

    public CountryData setCountryNameList(ArrayList<String> countryNameList){
        this.countryNameList = countryNameList;
        return this;
    }

    ///////////////////////////////////////
    // GETTER'S
    ///////////////////////////////////////

    public ArrayList<String> getCountryCodeList(){
        return this.countryCodeList;
    }

    public ArrayList<String> getCountryNameList(){
        return this.countryNameList;
    }
}
