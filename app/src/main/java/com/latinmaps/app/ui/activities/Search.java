package com.latinmaps.app.ui.activities;

import android.widget.ListView;

import com.latinmaps.app.R;
import com.latinmaps.app.base.LatinMaps;

/**
 * Created by Administrator on 2/1/2016.
 */
public class Search extends LatinMaps {

    ListView listSearch, listCategories;

    @Override
    public int getLayout(int id) {
        return R.layout.activity_search;
    }

    @Override
    public void init() {
        super.init();

        initializeReferences();

    }

    private void initializeReferences() {
        listCategories =(ListView) findViewById(R.id.list_categories);
        listSearch = (ListView) findViewById(R.id.list_search);
    }
}
