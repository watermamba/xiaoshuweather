package com.water.xiaoshuweather.entity;

/**
 * Created by water on 2017/5/9.
 */

public class City {
    private String cityName;
    private int imageId;
    private String temperature;

    public City(String cityName, int imageId, String temperature) {
        this.cityName = cityName;
        this.imageId = imageId;
        this.temperature = temperature;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }
}
