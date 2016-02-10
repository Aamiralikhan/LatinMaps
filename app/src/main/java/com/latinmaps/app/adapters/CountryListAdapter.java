package com.latinmaps.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.latinmaps.app.R;
import com.latinmaps.app.models.CountryListModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 1/22/2016.
 */
public class CountryListAdapter extends ArrayAdapter {

    static class DataHandler {
        TextView countryName;
        TextView countryCode;
    }

    List list = new ArrayList();

    public CountryListAdapter(Context context, int resource) {
        super(context, resource);
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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        DataHandler handler;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.templete_country_and_code_list, parent, false);
            handler = new DataHandler();

            handler.countryName = (TextView) view.findViewById(R.id.countryName);
            handler.countryCode = (TextView) view.findViewById(R.id.countryCode);
            view.setTag(handler);
        } else {
            handler = (DataHandler) view.getTag();
        }

        CountryListModel adModel = (CountryListModel) this.getItem(position);

        handler.countryName.setText(adModel.getCountryName());
        handler.countryCode.setText(adModel.getCountryCode());

        return view;
    }

}
