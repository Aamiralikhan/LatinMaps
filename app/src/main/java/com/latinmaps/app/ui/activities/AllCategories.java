package com.latinmaps.app.ui.activities;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.latinmaps.app.R;
import com.latinmaps.app.adapters.PlacesAdapter;
import com.latinmaps.app.base.LatinMaps;
import com.latinmaps.app.models.PlacesModel;
import com.latinmaps.app.ui.dialogs.MainDialog;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

public class AllCategories extends LatinMaps implements NavigationView.OnNavigationItemSelectedListener {

    PlacesModel placesModel;
    PlacesAdapter placesAdapter;
    GridView gridView;
    SwipeRefreshLayout refresher;
    ImageView businessIcon;
    RelativeLayout addBusiness;


    @Override
    public int getLayout(int id) {
        return R.layout.activity_all_categories;
    }

    @Override
    public void init() {
        super.init();

        mainDialog = new MainDialog(this, android.R.style.Theme_Light);
        mainDialog.show();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        placesAdapter = new PlacesAdapter(this, R.layout.templete_all_categories);
        gridView = (GridView) findViewById(R.id.grid);
        refresher = (SwipeRefreshLayout) findViewById(R.id.refresher);
        businessIcon = (ImageView) findViewById(R.id.business_icon);
        addBusiness = (RelativeLayout) findViewById(R.id.add_business);

        methodGetPlacesData();

        methodGridViewItemClickListener();

        refreshContent();

        setIconColor(businessIcon);

        attachClickListener(R.id.add_business, R.id.title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_business:
                gotoActivity(AddYourBusiness.class);
                break;
            case R.id.title:
                gotoActivity(Search.class);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.all_categories, menu);
       /* final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menu.findItem(R.id.action_search));
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_personal_information) {
            gotoActivity(PersonalInformation.class);
        } else if (id == R.id.nav_favorites) {

        } else if (id == R.id.nav_change_location) {

        } else if (id == R.id.nav_feedback) {
            gotoActivity(SendFeedback.class);
        } else if (id == R.id.nav_latino_challenge) {

        } else if (id == R.id.nav_video) {

        } else if (id == R.id.nav_logout) {
            ParseUser.logOut();
            gotoActivity(Login.class, true);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    public void methodGetPlacesData() {

        ParseQuery<ParseObject> query = new ParseQuery<>("LMPlaces");

        query.setLimit(1000);
        query.whereEqualTo("categoryName", "Restaurant");
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> placesList, ParseException e) {
                if (e == null) {
                    Log.d("Places_size", String.valueOf(placesList.size()));

                    for (int i = 0; i < placesList.size(); i++) {

                        try {
                            JSONArray images = placesList.get(i).getJSONArray("images");
                            Log.d("place_img", images.getJSONObject(0).get("url").toString());

                            placesModel = new PlacesModel(placesList.get(i).getObjectId()
                                    , images.getJSONObject(0).get("url").toString()
                                    , placesList.get(i).getString("title")
                                    , placesList.get(i).getString("shortDescription")
                                    , placesList.get(i).getString("address")
                                    , placesList.get(i).getString("title"));

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                        placesAdapter.add(placesModel);
                        placesAdapter.notifyDataSetChanged();

                       /* PlacesModel pm = (PlacesModel) placesAdapter.getItem(i);
                        Log.d("placeMode", pm.getTitle().toString());*/

                    }
                    gridView.setAdapter(placesAdapter);
                    mainDialog.dismiss();
                } else {

                    Log.d("Places_size", e.getMessage());

                }
            }
        });

    }

    private void methodGridViewItemClickListener() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                TextView idHolder = (TextView) view.findViewById(R.id.id_holder);

                Bundle bundle = new Bundle();
                bundle.putString("objectID", idHolder.getText().toString());

                gotoActivity(PlaceDetails.class, bundle);

            }
        });
    }

    private void refreshContent() {
        refresher.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresher.setRefreshing(false);
                placesAdapter.clearData();
                methodGetPlacesData();
            }
        });
    }

}
