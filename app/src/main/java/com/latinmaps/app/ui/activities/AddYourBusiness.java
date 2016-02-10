package com.latinmaps.app.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import com.latinmaps.app.R;
import com.latinmaps.app.base.LatinMaps;
import com.latinmaps.app.helpers.Help;
import com.latinmaps.app.ui.dialogs.MainDialog;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.rengwuxian.materialedittext.MaterialEditText;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 1/26/2016.
 */
public class AddYourBusiness extends LatinMaps {

    Spinner businessCategory;
    ArrayList<String> businessCategoryNames;
    MainDialog mainDialog;
    MaterialEditText location, businessName, primaryContactPerson, title, phone, email, website, address;
    String lat, lng;
    Toolbar toolbar;

    @Override
    public int getLayout(int id) {
        return R.layout.activity_add_business;
    }

    private void initializeReferences(){
        businessName = (MaterialEditText) findViewById(R.id.ADD_businessName);
        primaryContactPerson = (MaterialEditText) findViewById(R.id.ADD_primary_contact_person);
        title = (MaterialEditText) findViewById(R.id.ADD_title_position);
        phone = (MaterialEditText) findViewById(R.id.ADD_direct_phone);
        website = (MaterialEditText) findViewById(R.id.ADD_business_website);
        address = (MaterialEditText) findViewById(R.id.ADD_business_address);
        businessCategory = (Spinner) findViewById(R.id.AAD_business_type);
        location = (MaterialEditText) findViewById(R.id.ADD_business_location);
        businessCategoryNames = new ArrayList<>();
        mainDialog = new MainDialog(this, android.R.style.Theme_Light);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    @Override
    public void init() {
        super.init();

        initializeReferences();

        mainDialog.show();

        setSupportActionBar(toolbar);

        getBusinessCategories();

        methodLocationOnFocusListener();

        attachClickListener(R.id.ADD_add_button);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ADD_add_button:
                if(isNetworkAvailable()){
                    if(Toasts())
                        methodAddBusiness();
                }else {
                    startAlert("Network Error","Please check your connection",this);
                }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {

        }

        return super.onOptionsItemSelected(item);
    }

    private void getBusinessCategories() {
        ParseQuery<ParseObject> query = new ParseQuery<>("LMCategories");
        query.setLimit(1000);
        query.findInBackground(new FindCallback<ParseObject>() {
            public void done(List<ParseObject> placesList, ParseException e) {
                if (e == null) {
                    Log.d("Places_size", String.valueOf(placesList.size()));

                    for (int i = 0; i < placesList.size(); i++) {
                        businessCategoryNames.add(placesList.get(i).get("name").toString());
                    }
                    methodSetSpinner();
                    mainDialog.dismiss();
                } else {

                    Log.d("Places_size", e.getMessage());

                }
            }
        });
    }

    private void methodSetSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, businessCategoryNames);
        businessCategory.setAdapter(adapter);
    }

    private void methodLocationOnFocusListener() {
        location.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    methodGetLocationResult();
                }
            }
        });

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                methodGetLocationResult();
            }
        });
    }

    private void methodGetLocationResult() {
        Intent i = new Intent(this, BusinessLocationMap.class);
        startActivityForResult(i, 1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                lat = data.getStringExtra("lat");
                lng = data.getStringExtra("lng");

                location.setText("Lat :" + lat + ", Lng :" + lng);
            }
        }

    }

    private boolean Toasts(){
        boolean status = true;
        if (Help.isEmptyEditText(location)) {
            location.setError("can't be empty");
            status = false;
        }
        if (Help.isEmptyEditText(businessName)) {
            businessName.setError("can't be empty");
            status = false;
        }
        if (!Help.isValidEmailAddress(email)) {
            email.setError("invalid email address");
            status = false;
        }
        if (Help.isEmptyEditText(primaryContactPerson)) {
            primaryContactPerson.setError("password can't be empty");
            status = false;
        }
        if (Help.isEmptyEditText(title)) {
            title.setError("password not confirmed");
            status = false;
        }
        if (Help.isEmptyEditText(phone)) {
            phone.setError("password not confirmed");
            status = false;
        }
        if (Help.isEmptyEditText(website)) {
            website.setError("password not confirmed");
            status = false;
        }
        if (Help.isEmptyEditText(address)) {
            address.setError("password not confirmed");
            status = false;
        }
        return status;
    }

    private void methodAddBusiness(){

    }
}
