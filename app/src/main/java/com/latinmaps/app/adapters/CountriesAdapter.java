package com.latinmaps.app.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.latinmaps.app.R;
import com.latinmaps.app.models.CountryWithCheckModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2/1/2016.
 */
public class CountriesAdapter extends ArrayAdapter {

    static class DataHandler {
        TextView countryName;
        CheckBox checkBox;
    }

    List list = new ArrayList();

    public CountriesAdapter(Context context, int resource) {
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
            view = inflater.inflate(R.layout.templete_change_location, parent, false);
            handler = new DataHandler();

            handler.countryName = (TextView) view.findViewById(R.id.CC_countryName);
            handler.checkBox = (CheckBox) view.findViewById(R.id.CC_checkbox);
            view.setTag(handler);
        } else {
            handler = (DataHandler) view.getTag();
        }

        CountryWithCheckModel adModel = (CountryWithCheckModel) this.getItem(position);

        handler.countryName.setText(adModel.getCountryName());
        handler.checkBox.isChecked();

        return view;
    }
}
