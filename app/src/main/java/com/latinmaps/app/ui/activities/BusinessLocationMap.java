package com.latinmaps.app.ui.activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.view.View;

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

public class BusinessLocationMap extends LatinMaps implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,OnMapReadyCallback{
    private GoogleMap mMap, googleMap;
    FloatingActionButton myLocationButton;
    GoogleApiClient mGoogleApiClient;
    double lat, lng;
    Location mLastLocation;
    LocationManager locationManager;
    String businessLat, businessLng;

    @Override

    public int getLayout(int id) {
        return R.layout.activity_business_location_map;
    }

    @Override
    public void init() {
        super.init();

        attachClickListener(R.id.SL_done, R.id.myLocation);

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

        methodAddMarkerOnPosition();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.SL_done:
                methodDone();
                break;
            case R.id.myLocation:
                methodGetLocation();
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
            lat = mLastLocation.getLatitude();
            lng = mLastLocation.getLongitude();
        }

        LatLng myPlace = new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPlace));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));

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

        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.getUiSettings().setMyLocationButtonEnabled(false);
        mMap.setMyLocationEnabled(true);
    }

    private void methodAddMarkerOnPosition() {
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

                    businessLat = String.valueOf(latLng.latitude);
                    businessLng = String.valueOf(latLng.longitude);
                }

            });
        }
    }

    @Override
    protected void onResume() {

        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }else
        super.onResume();
    }

    private void methodDone(){
        Intent returnIntent = new Intent();
        returnIntent.putExtra("lat",businessLat);
        returnIntent.putExtra("lng",businessLng);
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void methodGetLocation(){
        LatLng myPlace = new LatLng(lat, lng);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(myPlace));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10.0f));
    }
}