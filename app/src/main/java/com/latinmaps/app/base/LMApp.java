package com.latinmaps.app.base;

import android.content.Context;
import android.support.multidex.MultiDex;

import com.parse.Parse;

/**
 * Created by Administrator on 1/25/2016.
 */
public class LMApp extends android.app.Application {

    @Override
    public void onCreate() {
        super.onCreate();

        Parse.initialize(this, "IAJFtDe9gZdEsCtopRZdKmkpgCn8mgSTjiVob486", "tlOvwkp1xaVLtZByrOkFWVOn6Ww4km4tKP1wZFJr");

        MultiDex.install(this);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
