package com.water.xiaoshuweather;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.water.xiaoshuweather.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by water on 2017/5/5.
 */

public class WeatherFragment extends Fragment {

    TextView cityName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_wea_layout, container, false);
        Log.e("MainActivity", "城市是"+MainActivity.locCity);
        cityName = (TextView) view.findViewById(R.id.title_city);
        cityName.setText(MainActivity.locCity);
        return view;
    }



}
