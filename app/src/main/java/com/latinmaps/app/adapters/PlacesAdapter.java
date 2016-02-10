package com.latinmaps.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.latinmaps.app.R;
import com.latinmaps.app.models.PlacesModel;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 1/22/2016.
 */
public class PlacesAdapter extends ArrayAdapter {

    Context ctx;

    static class DataHandler {
        TextView title, minDesc, distance, address, idHolder;
        ImageView placeImg;
        ProgressBar pb;
    }

    List list = new ArrayList();

    public PlacesAdapter(Context context, int resource) {
        super(context, resource);
        this.ctx = context;
    }

    @Override
    public void add(Object object) {
        list.add(object);
    }

    @Override
    public int getCount() {
        return this.list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.list.get(position);
    }

    public void clearData(){
        list.clear();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        final DataHandler handler;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.templete_all_categories, parent, false);
            handler = new DataHandler();

            handler.title = (TextView) view.findViewById(R.id.title_template);
            handler.minDesc = (TextView) view.findViewById(R.id.minDesc_template);
            handler.distance = (TextView) view.findViewById(R.id.distance_template);
            handler.placeImg = (ImageView) view.findViewById(R.id.place_image);
            handler.address = (TextView) view.findViewById(R.id.address_template);
            handler.pb = (ProgressBar) view.findViewById(R.id.img_progress);
            handler.idHolder = (TextView) view.findViewById(R.id.id_holder);

            view.setTag(handler);

        } else {
            handler = (DataHandler) view.getTag();
        }

        PlacesModel placesModel = (PlacesModel) this.getItem(position);

        Picasso.with(ctx).load(placesModel.getImgUri()).into(handler.placeImg, new Callback() {
            @Override
            public void onSuccess() {
                handler.pb.setVisibility(View.GONE);
            }

            @Override
            public void onError() {

            }
        });

        handler.title.setText(placesModel.getTitle());
        handler.minDesc.setText(placesModel.getMiniDes());
        handler.distance.setText(placesModel.getDistance());
        handler.address.setText(placesModel.getAddress());
        handler.idHolder.setText(placesModel.getIdHolder());

        return view;
    }
}
