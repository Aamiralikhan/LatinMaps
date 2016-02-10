package com.latinmaps.app.ui.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;

import com.latinmaps.app.base.LatinMaps;
import com.latinmaps.app.R;
import com.latinmaps.app.adapters.ISAdapter;

import java.util.ArrayList;

public class PhotoSlider extends LatinMaps {

    private ViewPager viewPager;
    private ISAdapter isAdapter;
    private ArrayList<String> imageList;

    @Override
    public int getLayout(int id) {
        return R.layout.activity_photo_slider;
    }


    @Override
    public void init() {
        super.init();

        isAdapter = new ISAdapter(this);
        viewPager = (ViewPager) findViewById(R.id.myViewPager);
        viewPager.setAdapter(isAdapter);

        attachClickListener(R.id.frame_back);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frame_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void initWithBundle(Bundle savedInstanceState) {
        super.initWithBundle(savedInstanceState);

        if(savedInstanceState == null){
            imageList = getIntent().getStringArrayListExtra("imageList");
            bindImages();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putStringArrayList("imageList", imageList);
        outState.putInt("position", viewPager.getCurrentItem());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        imageList = savedInstanceState.getStringArrayList("imageList");
        bindImages();
        viewPager.setCurrentItem(savedInstanceState.getInt("position"));
    }

    private void bindImages(){
        for(String image : imageList){
            isAdapter.addToArray(image);
        }

        isAdapter.notifyDataSetChanged();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        try {
            return super.dispatchTouchEvent(ev);
        }catch (IllegalArgumentException e){
            e.printStackTrace();
            return false;
        }
    }
}
