package com.pentastagiu.weatherapp.holders;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class City {
    private String name;
    private List<Weather> weather;
    @SerializedName("main")
    private Temperature temperature;
    @SerializedName("dt_txt")
    private String date;
    private int id;


    public City(){
        //do nothing
    }

    public City(List<Weather> weather, Temperature temperature, String date){
        this.weather = weather;
        this.temperature = temperature;
        this.date = date;
    }

    public City(String name, List<Weather> weather, Temperature temperature){
        this.name = name;
        this.weather = weather;
        this.temperature = temperature;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getWeather() {
        String description = "";
        for(Weather weather : this.weather){
            description = weather.getDescription();
            break;
        }
        return description;
    }
    public String getCityId(){
        String cityId = "";
        for(Weather weather : this.weather){
            cityId = String.valueOf(weather.getId());
        }
        return cityId;
    }
    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }
    public String getCurrentTemperature() {
        return temperature.getCurrentTemperature()+" °";
    }
    public String getMinimumTemperature() {
        return temperature.getMinimumTemperature()+" °";
    }
    public String getMaximumTemperature() {
        return temperature.getMaximumTemperature()+" °";
    }

    @Override
    public String toString() {
        return "City{" +
                "name='" + name + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate() {
        String[] currentDate = date.split(" ");
        return currentDate[1];
    }

    public void setDate(String date) {
        this.date = date;
    }
}
