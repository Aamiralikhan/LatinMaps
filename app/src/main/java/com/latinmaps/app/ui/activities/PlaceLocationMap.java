package com.latinmaps.app.ui.activities;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;

import com.akexorcist.googledirection.DirectionCallback;
import com.akexorcist.googledirection.GoogleDirection;
import com.akexorcist.googledirection.constant.AvoidType;
import com.akexorcist.googledirection.model.Direction;
import com.akexorcist.googledirection.util.DirectionConverter;
import com.google.android.gms.ads.internal.ClientApi;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.latinmaps.app.R;
import com.latinmaps.app.base.LatinMaps;

import java.util.ArrayList;

/**
 * Created by Administrator on 1/28/2016.
 */
public class PlaceLocationMap extends LatinMaps implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap, googleMap;
    FloatingActionButton myLocationButton;
    GoogleApiClient mGoogleApiClient;
    double userLat, userLng;
    Location mLastLocation;
    LocationManager locationManager;
    double placeLat, placeLng;
    LatLng origin;
    LatLng destination;

    @Override
    public int getLayout(int id) {
        return R.layout.activity_place_map;
    }

    @Override
    public void init() {
        super.init();

        attachClickListener(R.id.myLocation);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        myLocationButton = (FloatingActionButton) findViewById(R.id.myLocation);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        googleMap = mapFragment.getMap();

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

//        methodAddMarkerOnPosition();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.myLocation:
                showMapsDirection();
                break;
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

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        String placeLatLng = getIntent().getStringExtra("latLng");
        String alif = placeLatLng.substring(placeLatLng.indexOf("[") + 1, placeLatLng.indexOf(","));
        String bay = placeLatLng.substring(placeLatLng.indexOf(",") + 1, placeLatLng.length() - 1);

        Log.d("LatLng", alif.toString());
        Log.d("LatLng", bay.toString());

        placeLat = Double.parseDouble(alif);
        placeLng = Double.parseDouble(bay);

        origin = new LatLng(placeLat, placeLng);
        mMap.addMarker(new MarkerOptions().position(origin).title("me"));
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(origin));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);
    }

    /*    private void methodAddMarkerOnPosition() {
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            } else {
                googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                    @Override
                    public void onMapClick(LatLng latLng) {
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(latLng);
                        googleMap.clear();
                        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
                        googleMap.addMarker(markerOptions);

                    }

                });
            }
        }*/
    @Override
    protected void onResume() {

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        } else
            super.onResume();
    }

    private void showMapsDirection() {

        GoogleDirection.withServerKey("AIzaSyCcq6-n50OqaVSQNGl8bKLoJhh2TBn3bfs")
                .from(origin)
                .to(destination)
                .execute(new DirectionCallback() {
                    @Override
                    public void onDirectionSuccess(Direction direction) {
                        if (direction.isOK()) {

                            ArrayList<LatLng> directionPositionList = direction.getRouteList().get(0).getLegList().get(0).getDirectionPoint();
                            googleMap.addPolyline(DirectionConverter.createPolyline(PlaceLocationMap.this, directionPositionList, 5, Color.RED));

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



}
