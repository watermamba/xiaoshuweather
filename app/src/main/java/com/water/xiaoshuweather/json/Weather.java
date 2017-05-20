package com.water.xiaoshuweather.json;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by water on 2017/5/11.
 */

public class Weather {

    public String status;

    public AQI aqi;

    public Basic basic;

    @SerializedName("daily_forecast")
    public List<Forecast> forecastList;

    public Now now;

    public Suggestion suggestion;
}
