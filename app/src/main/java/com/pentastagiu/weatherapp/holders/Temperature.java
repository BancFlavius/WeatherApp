package com.pentastagiu.weatherapp.holders;

import com.google.gson.annotations.SerializedName;

public class Temperature {
    @SerializedName("temp")
    private String currentTemperature;
    @SerializedName("temp_min")
    private String minimumTemperature;
    @SerializedName("temp_max")
    private String maximumTemperature;

    public Temperature(){
        //do nothing
    }

    public Temperature(String currentTemperature, String minimumTemperature, String maximumTemperature){
        this.currentTemperature = currentTemperature;
        this.minimumTemperature = minimumTemperature;
        this.maximumTemperature = maximumTemperature;
    }

    public String getCurrentTemperature() {
        return currentTemperature;
    }

    public void setCurrentTemperature(String currentTemperature) {
        this.currentTemperature = currentTemperature;
    }

    public String getMinimumTemperature() {
        return minimumTemperature;
    }

    public void setMinimumTemperature(String minimumTemperature) {
        this.minimumTemperature = minimumTemperature;
    }

    public String getMaximumTemperature() {
        return maximumTemperature;
    }

    public void setMaximumTemperature(String maximumTemperature) {
        this.maximumTemperature = maximumTemperature;
    }

    @Override
    public String toString() {
        return "Temperature{" +
                "currentTemperature=" + currentTemperature +
                ", minimumTemperature=" + minimumTemperature +
                ", maximumTemperature=" + maximumTemperature +
                '}';
    }
}
