package com.pentastagiu.weatherapp;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.pentastagiu.weatherapp.data.City;
import com.pentastagiu.weatherapp.data.User;

import java.util.List;

public class WeatherViewModel extends AndroidViewModel {

    private WeatherRepository mWeatherRepository;

    private LiveData<List<City>> mAllCities;
    private LiveData<List<User>> mAllUsers;

    public WeatherViewModel(Application application){
        super(application);
        mWeatherRepository = new WeatherRepository(application);
        mAllCities = mWeatherRepository.getAllCities();
        mAllUsers = mWeatherRepository.getAllUsers();
    }

    public LiveData<List<City>> getAllCities(){
        return mAllCities;
    }

    public LiveData<List<User>> getAllUsers(){
        return  mAllUsers;
    }

    public void insertCity(City city){
        mWeatherRepository.insertCity(city);
    }

    public void insertUser(User user){
        mWeatherRepository.insertUser(user);
    }

}
