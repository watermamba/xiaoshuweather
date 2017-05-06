package com.water.xiaoshuweather.util;


import android.text.TextUtils;

import com.water.xiaoshuweather.db.ChinaCity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by water on 2017/5/6.
 */

public class Utility {

    /**
     * 处理返回的城市数据
     */

    public static boolean handleCityData(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray chinaCities = new JSONArray(response);
                for (int i = 0; i < chinaCities.length(); i++) {
                    JSONObject chinaCityObject = chinaCities.getJSONObject(i);
                    ChinaCity chinaCity = new ChinaCity();
                    chinaCity.setCityId(chinaCityObject.getString("id"));
                    chinaCity.setCityEn(chinaCityObject.getString("cityEn"));
                    chinaCity.setCityZh(chinaCityObject.getString("cityZh"));
                    chinaCity.setCountryCode(chinaCityObject.getString("countryCode"));
                    chinaCity.setCountryEn(chinaCityObject.getString("countryEn"));
                    chinaCity.setCountryZh(chinaCityObject.getString("countryZh"));
                    chinaCity.setProvinceEn(chinaCityObject.getString("provinceEn"));
                    chinaCity.setProvinceZh(chinaCityObject.getString("provinceZh"));
                    chinaCity.setLeaderEn(chinaCityObject.getString("leaderEn"));
                    chinaCity.setLeaderZh(chinaCityObject.getString("leaderZh"));
                    chinaCity.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
