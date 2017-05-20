package com.water.xiaoshuweather.adapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.water.xiaoshuweather.R;
import com.water.xiaoshuweather.entity.City;

import java.util.List;

/**
 * Created by water on 2017/5/11.
 */

public class CityAdapter extends ArrayAdapter<City> {
    private int resourceId;

    public CityAdapter(Context context, int resource, List<City> objects) {
        super(context, resource, objects);
        this.resourceId = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        City city = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceId, parent, false);
        ImageView temImage = (ImageView) view.findViewById(R.id.temperature_image);
        TextView cityName = (TextView) view.findViewById(R.id.city_name);
        TextView min_max_Tem = (TextView) view.findViewById(R.id.min_max_temperature);
        temImage.setImageResource(city.getImageId());
        cityName.setText(city.getCityName());
        min_max_Tem.setText(city.getTemperature());
        return view;
    }

}
