package com.latinmaps.app.ui.activities;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.latinmaps.app.R;
import com.latinmaps.app.base.LatinMaps;

/**
 * Created by Administrator on 2/3/2016.
 */
public class ChangeLocation extends LatinMaps {

    ListView countriesList;

    @Override
    public int getLayout(int id) {
        return R.layout.activity_change_location;
    }

    @Override
    public void init() {
        super.init();

        methodInitializeReferences();

        methodCountriesListOnClickListener();

        methodGetCountriesList();
    }

    private void methodGetCountriesList() {

    }

    private void methodCountriesListOnClickListener() {
        countriesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void methodInitializeReferences() {
        countriesList = (ListView) findViewById(R.id.list_countries);
    }


}
