package com.water.xiaoshuweather.json;


import com.google.gson.annotations.SerializedName;

/**
 * Created by water on 2017/5/11.
 */

public class AQI {

    public AQICity city;

    public class AQICity {
        public String aqi;

        public String pm25;

        public String qlty;
    }
}
