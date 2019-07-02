package com.pentastagiu.weatherapp.holders;

import com.google.android.gms.common.util.ArrayUtils;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;


public class CityList {
    @SerializedName("list")
    private LinkedList<City> list = new LinkedList<>();

    public void concatenateCityList(City[] newList){
        list.addAll(Arrays.asList(newList));
    }

    public CityList(){
        //do nothing
    }
    public CityList(LinkedList<City> list){
        this.list = list;
    }

    public City[] getList() {
        City[] cityList = new City[list.size()];
        int i = 0;
        for(City city : list){
            cityList[i] = city;
            i++;
        }
        return cityList;
    }

    public void setList(LinkedList<City> list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CityList{" +
                "list=" + list.toString() +
                '}';
    }
}
