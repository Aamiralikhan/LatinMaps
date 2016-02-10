package com.latinmaps.app.base;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.view.View;

import com.latinmaps.app.R;
import com.latinmaps.app.ui.dialogs.MainDialog;
import com.parse.Parse;

/**
 * Created by Administrator on 1/20/2016.
 */
public abstract class LatinMaps extends BaseActivity {

    private SharedPreferences.Editor editor;
    private SharedPreferences prefs;
    public MainDialog mainDialog;

    @Override
    public final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // set content view
        setContentView(getLayout(R.layout.splash));

        editor = getSharedPreferences("com.latinMaps.app", MODE_PRIVATE).edit();
        prefs = getSharedPreferences("com.latinMaps.app", MODE_PRIVATE);
        // call init
        init();

        initWithBundle(savedInstanceState);

    }

    public SharedPreferences.Editor getEditor() {
        return editor;
    }

    public void addToPref(String key, String val)
    {
        editor.putString(key, val);
        editor.commit();
    }

    public String getFromPref(String key)
    {
        return prefs.getString(key, "");
    }

    public void init() {
    }

    public void initWithBundle(Bundle savedInstanceState){}

    public abstract int getLayout(int id);

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}