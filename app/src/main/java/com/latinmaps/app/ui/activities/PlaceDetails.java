package com.latinmaps.app.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.TransportMode;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.model.Info;
import com.akexorcist.googledirection.model.Leg;
import com.akexorcist.googledirection.model.Route;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.latinmaps.app.R;
import com.latinmaps.app.base.LatinMaps;
import com.latinmaps.app.ui.dialogs.MainDialog;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by Administrator on 1/26/2016.
 */
public class PlaceDetails extends LatinMaps implements GoogleApiClient.OnConnectionFailedListener,GoogleApiClient.ConnectionCallbacks{

    TextView photoLength, title, distance, shortDescription, travelTiming, businessAddress, businessTiming, businessWebsite, longDescription;
    ImageView businessImage, iconFavorite, iconAddress, iconTiming, iconWebsite;
    ProgressBar pb;
    ArrayList<String> imageList;
    String latLng;
    double userLat, userLng;
    double placeLat, placeLng;
    LocationManager locationManager;
    GoogleApiClient mGoogleApiClient;
    Location mLastLocation;
    LatLng origin, destination;
    boolean DESTINATION_FLAG = true;


    @Override
    public int getLayout(int id) {
        return R.layout.activity_place_details;
    }

    @Override
    public void init() {
        super.init();

        mainDialog = new MainDialog(this, android.R.style.Theme_Light);
        mainDialog.show();

        photoLength = (TextView) findViewById(R.id.photos_length);
        title = (TextView) findViewById(R.id.APD_title);
        distance = (TextView) findViewById(R.id.APD_distance);
        shortDescription = (TextView) findViewById(R.id.APD_restatant_name);
        travelTiming = (TextView) findViewById(R.id.APD_time);
        businessAddress = (TextView) findViewById(R.id.APD_business_address);
        businessTiming = (TextView) findViewById(R.id.APD_business_open_timing);
        businessWebsite = (TextView) findViewById(R.id.APD_business_website);
        longDescription = (TextView) findViewById(R.id.APD_long_description);
        businessImage = (ImageView) findViewById(R.id.APD_business_image);
        pb = (ProgressBar) findViewById(R.id.image_progress);
        imageList = new ArrayList<>();

        iconFavorite = (ImageView) findViewById(R.id.img_favourite);
        iconAddress = (ImageView) findViewById(R.id.address_icon);
        iconTiming = (ImageView) findViewById(R.id.timing_icon);
        iconWebsite = (ImageView) findViewById(R.id.web_icon);

        attachClickListener(R.id.APD_business_image, R.id.APD_business_website, R.id.mapButton, R.id.layout_favorite);

        methodSetIconColor();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks((GoogleApiClient.ConnectionCallbacks) this)
                    .addOnConnectionFailedListener((GoogleApiClient.OnConnectionFailedListener) this)
                    .addApi(LocationServices.API)
                    .build();
        }

        methodGetPlaceData();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.APD_business_image:
                Bundle bundle = new Bundle();
                bundle.putStringArrayList("imageList", imageList);
                gotoActivity(PhotoSlider.class, bundle);
                break;
            case R.id.APD_business_website:
                Bundle bundle1 = new Bundle();
                bundle1.putString("url", businessWebsite.getText().toString());
                gotoActivity(BusinessWebsite.class, bundle1);
                break;
            case R.id.mapButton:
                Bundle bundle2 = new Bundle();
                bundle2.putString("latLng", latLng);
                gotoActivity(PlaceLocationMap.class, bundle2);
                break;
            case R.id.layout_favorite:
                methodSetAndRemoveFavorite();
                break;
        }
    }

    private void methodGetPlaceData() {

        String object = getIntent().getStringExtra("objectID");

        ParseQuery<ParseObject> query = new ParseQuery<>("LMPlaces");
        query.getInBackground(object, new GetCallback<ParseObject>() {
            public void done(ParseObject object, ParseException e) {
                if (e == null) {

                    JSONArray images = object.getJSONArray("images");
                    try {

                        for (int i = 0; i < images.length(); i++) {
                            imageList.add(images.getJSONObject(i).get("url").toString());
                        }
                        Picasso.with(PlaceDetails.this).load(imageList.get(0)).into(businessImage, new Callback() {
                            @Override
                            public void onSuccess() {
                                pb.setVisibility(View.GONE);
                                businessImage.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onError() {
                                pb.setVisibility(View.GONE);
                                businessImage.setVisibility(View.VISIBLE);
                                businessImage.setImageResource(R.drawable.no_image);

                            }
                        });
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
                    title.setText(object.get("title").toString());
                    shortDescription.setText(object.get("shortDescription").toString());
                    businessAddress.setText(object.get("address").toString());
                    businessTiming.setText(object.get("timing").toString());
                    businessWebsite.setText(object.get("websiteUrl").toString());
                    longDescription.setText(object.get("longDescription").toString());
                    latLng = object.get("location").toString();
                    photoLength.setText(images.length() + " Photos");

                    String alif = latLng.substring(latLng.indexOf("[") + 1, latLng.indexOf(","));
                    String bay = latLng.substring(latLng.indexOf(",") + 1, latLng.length() - 1);

                    origin = new LatLng(Double.parseDouble(alif), Double.parseDouble(bay));
                    Log.d("direction", String.valueOf(origin));

                } else {
                    e.printStackTrace();
                }
                mainDialog.dismiss();
            }
        });
    }

    private void methodSetIconColor() {
        setIconColor(iconWebsite);
        setIconColor(iconAddress);
        setIconColor(iconTiming);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        if (logout) {
            finish();
        }
    }

    protected void onStart() {
        mGoogleApiClient.connect();
        super.onStart();
    }

    protected void onStop() {
        mGoogleApiClient.disconnect();
        super.onStop();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            userLat = mLastLocation.getLatitude();
            userLng = mLastLocation.getLongitude();

            destination = new LatLng(userLat, userLng);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    private void methodGetDataLocation() {
        GoogleDirection.withServerKey("AIzaSyCcq6-n50OqaVSQNGl8bKLoJhh2TBn3bfs")
                .from(origin)
                .to(destination)
                .transitMode(TransportMode.DRIVING)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction) {
                        if (direction.isOK()) {

                            if (direction.getStatus().toString().equals("OK")) {
                                Log.d("direction", "status ok");
                            } else {
                                Log.d("direction", "status not ok");
                            }
                            Log.d("direction", direction.getStatus().toString());


                            Route route = direction.getRouteList().get(0);
                            Leg leg = route.getLegList().get(0);

                            Info dis = leg.getDistance();
                            Info duration = leg.getDuration();

                            if (dis.getText().toString().isEmpty()) {
                                Log.d("NewDirection", "empty");
                            } else {
                                Log.d("NewDirection", "not Empty");
                            }

                            PlaceDetails.this.distance.setText(dis.getText().toString());
                            PlaceDetails.this.travelTiming.setText(duration.getText().toString());

                            Log.d("NewDirection", distance.getText().toString());
                            Log.d("NewDirection", duration.getText().toString());

                        } else {
                            Log.d("direction", direction.getStatus().toString());
                        }
                    }

                    @Override
                    public void onDirectionFailure(Throwable t) {

                        toast("direction failure");
                        Log.d("direction", t.getMessage().toString());

                    }
                });
    }

    private void methodSetAndRemoveFavorite(){
        String tag = iconFavorite.getTag().toString();
        
        if(tag.equals("0")){
            iconFavorite.setTag("1");
            setIconColor(iconFavorite);
        }else{
            iconFavorite.setTag("0");
            removeIconColor(iconFavorite);
        }
    }

}
