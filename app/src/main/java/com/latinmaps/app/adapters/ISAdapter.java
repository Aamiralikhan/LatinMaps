package com.latinmaps.app.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.latinmaps.app.R;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * Created by Administrator on 1/4/2016.
 */
public class ISAdapter extends PagerAdapter {

    private ArrayList<String> arrayList = new ArrayList<>();

    public void addToArray(String item){
        this.arrayList.add(item);
    }
    private Context ctx;
    private LayoutInflater inflater;

    public ISAdapter(Context ctx) {
        this.ctx = ctx;
    }



    @Override
    public boolean isViewFromObject(View view, Object object) {

        return (view == (RelativeLayout) object);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View item_view = inflater.inflate(R.layout.content_photo_slider, container, false);

        final ImageView imageView = (ImageView) item_view.findViewById(R.id.slide_img);

        final ProgressBar img_progress = (ProgressBar) item_view.findViewById(R.id.img_progress);


//        ImageLoader.getInstance().displayImage(arrayList.get(position), imageView);

        Picasso.with(ctx).load(arrayList.get(position)).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                new PhotoViewAttacher(imageView);
                img_progress.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });

        container.addView(item_view);

        return item_view;
    }

}
