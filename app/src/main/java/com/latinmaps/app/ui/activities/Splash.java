package com.latinmaps.app.ui.activities;


import android.os.Handler;
import android.util.Log;

import com.latinmaps.app.R;
import com.latinmaps.app.base.LatinMaps;
import com.latinmaps.app.utilities.Config;
import com.latinmaps.app.utilities.CountryData;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 1/20/2016.
 */
public class Splash extends LatinMaps implements Runnable {


    private ArrayList<String> countryCodes;
    private ArrayList<String> countryNames;

    @Override
    public int getLayout(int id) {
        return R.layout.splash;
    }

    @Override
    public void init() {
        super.init();
        countryCodes = new ArrayList<>();
        countryNames = new ArrayList<>();

        if(CountryData.getInstance().getCountryCodeList() == null){
            getCountryAndDCode();
        }
        else{
            new Handler().postDelayed(this, Config.SPLASH_TIMEOUT);
        }

    }


    @Override
    public void run() {
        if(ParseUser.getCurrentUser() != null){
            gotoActivity(AllCategories.class, true);
        }else {
            gotoActivity(Login.class, true);
        }
    }


    private void getCountryAndDCode() {

        ParseQuery<ParseObject> query = new ParseQuery<>("LMCountries");

        query.setLimit(1000);

        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> countryList, ParseException e) {
                if (e == null) {

                    for (int i = 0; i < countryList.size(); i++) {

                        Log.d("Countries_size", String.valueOf(countryList.size()));
                        Log.d("Countries", countryList.get(i).get("name").toString()+" "+countryList.get(i).get("d_code").toString());

                        countryNames.add(countryList.get(i).get("name").toString());
                        countryCodes.add(countryList.get(i).get("d_code").toString());

                    }

                    CountryData.getInstance().setCountryNameList(countryNames).setCountryCodeList(countryCodes);
                    run();

                } else {
                    toast("Connection error");
                }
            }
        });

    }


}
