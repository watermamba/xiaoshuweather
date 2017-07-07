package com.water.xiaoshuweather;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
import android.os.Bundle;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.bumptech.glide.Glide;
import com.water.xiaoshuweather.entity.City;
import com.water.xiaoshuweather.json.Forecast;
import com.water.xiaoshuweather.json.Weather;
import com.water.xiaoshuweather.util.HttpUtil;
import com.water.xiaoshuweather.util.LogUtil;
import com.water.xiaoshuweather.util.Utility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by water on 2017/5/5.
 */

public class WeatherFragment extends Fragment {

    private String locCity;
    public DrawerLayout drawerLayout;
    public SwipeRefreshLayout swipeRefreshLayout;
    private TextView cityName;
    private ImageButton titleButton;
    private ImageButton uploadButton;
    private TextView degreeText;
    private TextView weatherText;
    private TextView humidityText;
    private TextView windText;
    private TextView aqiText;
    private TextView pm25Text;
    private TextView suggestionText;
    private TextView todayQly;
    private TextView todayTem;
    private TextView todayWea;
    private ImageView todayImage;
    private TextView tomoQly;
    private TextView tomoTem;
    private TextView tomoWea;
    private ImageView tomoImage;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page_wea_layout, container, false);
        locCity = MainActivity.locCity.substring(0, MainActivity.locCity.length() - 1);
        Log.e("MainActivity", "定位城市是"+locCity);
        Object o[] = ChooseAreaFragment.cityName.toArray();
        for (int i =0; i<o.length;i++) {
            Log.e("MainActivity", "城市集合中有：" + o[i]);
        }
        titleButton = (ImageButton) view.findViewById(R.id.title_nav);
        drawerLayout = (DrawerLayout) view.findViewById(R.id.drawer_layout);
        cityName = (TextView) view.findViewById(R.id.title_city);
        cityName.setText(locCity);
        titleButton = (ImageButton) view.findViewById(R.id.title_nav);
        uploadButton = (ImageButton) view.findViewById(R.id.upload);
        degreeText = (TextView) view.findViewById(R.id.degree_text);
        weatherText = (TextView) view.findViewById(R.id.weather_info_text);
        humidityText = (TextView) view.findViewById(R.id.humidity_text);
        windText = (TextView) view.findViewById(R.id.wind_text);
        aqiText = (TextView) view.findViewById(R.id.aqi_text);
        pm25Text = (TextView) view.findViewById(R.id.pm25_text);
        suggestionText = (TextView) view.findViewById(R.id.suggestion);
        todayQly = (TextView) view.findViewById(R.id.today_qlty);
        todayTem = (TextView) view.findViewById(R.id.today_tem);
        todayWea = (TextView) view.findViewById(R.id.today_weather);
        todayImage = (ImageView) view.findViewById(R.id.today_image);
        tomoQly = (TextView) view.findViewById(R.id.tomorrow_qlty);
        tomoTem = (TextView) view.findViewById(R.id.tomorrow_tem);
        tomoWea = (TextView) view.findViewById(R.id.tomorrow_weather);
        tomoImage = (ImageView) view.findViewById(R.id.tomorrow_image);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        titleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        uploadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getContext(), uploadButton);
                popup.getMenuInflater().inflate(R.menu.main, popup.getMenu());
                popup.show();
            }
        });
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getContext());
        String weatherString = prefs.getString("weather", null);
        if (weatherString != null) {
            Weather weather = Utility.handleWeatherResponse(weatherString);
            showWeatherInfo(weather);
        } else {
            Log.e("Mainactivity", "此城市无缓存：" + locCity);
            getWeather(locCity);
        }
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener(){
            @Override
            public void onRefresh() {
                getWeather(locCity);
            }
        });
        return view;
    }

    /**
     * 根据城市名获取数据
     */
    public void getWeather(final String weatherCity) {

        Log.e("Main", weatherCity);
        String weatherUrl = "https://free-api.heweather.com/v5/weather?city=" + weatherCity
                + "&key=97ec7e20861a40a7a39f0493da12ba54";
        HttpUtil.sendOkhttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Looper.prepare();
                Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                Looper.loop();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                final Weather weather = Utility.handleWeatherResponse(responseText);

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (weather != null && "ok".equals(weather.status)) {
                            SharedPreferences.Editor editor = PreferenceManager
                                    .getDefaultSharedPreferences(getContext()).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(weather);
                        } else {
                            Toast.makeText(getContext(), "获取天气失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });

    }

    /**
     * 处理天气实体中的weather数据
     */

    private void showWeatherInfo(Weather weather) {
        degreeText.setText(weather.now.tmp+"°");
        weatherText.setText(weather.now.cond.txt);
        humidityText.setText(weather.now.hum+"%");
        windText.setText(weather.now.wind.sc + "级");
        aqiText.setText(weather.aqi.city.aqi);
        pm25Text.setText(weather.aqi.city.pm25);
        suggestionText.setText("    " + weather.suggestion.drsg.txt + "\n" + "    " + weather.suggestion.flu.txt + "\n" + "    "+ weather.suggestion.sport.txt);
        todayQly.setText(weather.aqi.city.qlty);
        todayTem.setText(weather.forecastList.get(0).tmp.min + "/" + weather.forecastList.get(0).tmp.max + "℃");
        String cityTem = weather.forecastList.get(0).tmp.min + "/" + weather.forecastList.get(0).tmp.max + "℃";
        todayWea.setText(weather.forecastList.get(0).cond.txt_d);
        final String todayImageUrl = "https://cdn.heweather.com/cond_icon/" + weather.forecastList.get(0).cond.code_d + ".png";
        Glide.with(getContext()).load(todayImageUrl).into(todayImage);
        tomoQly.setText(weather.aqi.city.qlty);
        tomoTem.setText(weather.forecastList.get(1).tmp.min + "/" + weather.forecastList.get(1).tmp.max + "℃");
        tomoWea.setText(weather.forecastList.get(1).cond.txt_d);
        final String tomoImageUrl = "https://cdn.heweather.com/cond_icon/" + weather.forecastList.get(1).cond.code_d + ".png";
        Glide.with(getContext()).load(tomoImageUrl).into(tomoImage);
    }
}
