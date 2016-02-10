package com.latinmaps.app.ws;

import android.content.Context;

import com.latinmaps.app.base.LPWebService;
import com.latinmaps.app.utilities.Api;

/**
 * Created by aamir on 1/13/2016.
 */
public class Twillio extends LPWebService<Twillio> {

    //////////////////////////////////////////
    // PRIVATE VAR
    //////////////////////////////////////////

    //////////////////////////////////////////
    // FACTORY METHOD
    //////////////////////////////////////////

    public static Twillio getInstance(Context context){
        return new Twillio(context);
    }

    //////////////////////////////////////////
    // CONSTRUCTOR
    //////////////////////////////////////////

    public Twillio(Context context){
        super(context);
    }

    //////////////////////////////////////////
    // IMPLEMENTATIONS
    //////////////////////////////////////////

    @Override
    public String getApiUrl() {
        return Api.HOST;
    }

    @Override
    public String getProgressMessage() {
        return "Please wait ...";
    }

    @Override
    public boolean isShowProgressDialog() {
        return true;
    }

    @Override
    public boolean isProcessProgressDialog() {
        return true;
    }

    @Override
    public boolean dataIsJSONObject() {
        return true;
    }

    //////////////////////////////////////////
    // GET THIS INSTANCE
    //////////////////////////////////////////

    @Override
    public Twillio getThis() {
        return this;
    }


}