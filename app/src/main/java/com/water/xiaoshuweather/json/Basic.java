package com.water.xiaoshuweather.json;

import com.google.gson.annotations.SerializedName;

/**
 * Created by water on 2017/5/11.
 */

public class Basic {

    @SerializedName("city")
    public String cityName;

    @SerializedName("id")
    public String cityId;

    public Update update;

    public class Update {

        @SerializedName("loc")
        public String updateTime;
    }

}
