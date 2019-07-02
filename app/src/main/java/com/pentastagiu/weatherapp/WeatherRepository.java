package com.pentastagiu.weatherapp;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.pentastagiu.weatherapp.data.City;
import com.pentastagiu.weatherapp.data.User;
import com.pentastagiu.weatherapp.database.AppDatabase;
import com.pentastagiu.weatherapp.database.CityDao;
import com.pentastagiu.weatherapp.database.UserDao;

import java.util.List;

public class WeatherRepository {

    private CityDao mCityDao;
    private UserDao mUserDao;
    private LiveData<List<City>> mAllCities;
    private LiveData<List<User>> mAllUsers;

    WeatherRepository(Application application) {
//        AppDatabase db = AppDatabase.getDatabase(application);
//        mCityDao = db.cityDao();
//        mUserDao = db.userDao();
//        mAllUsers = mUserDao.getUserTable();
//        mAllCities = mCityDao.getCityTable();
    }

    LiveData<List<City>> getAllCities() {
        return mAllCities;
    }

    public void insertCity(City city) {
        new insertCityAsyncTask(mCityDao).execute(city);
    }

    LiveData<List<User>> getAllUsers(){
        return mAllUsers;
    }

    public void insertUser(User user){
        new insertUserAsyncTask(mUserDao).execute(user);
    }



    private static class insertCityAsyncTask extends AsyncTask<City, User, Void> {

        private CityDao mAsyncTaskCityDao;



        insertCityAsyncTask(CityDao dao) {
            mAsyncTaskCityDao = dao;
        }

        @Override
        protected Void doInBackground(final City... params) {
            mAsyncTaskCityDao.insert(params[0]);
            return null;
        }
    }

    private static class insertUserAsyncTask extends AsyncTask<User, Void, Void>{
        private UserDao mAsyncTaskUserDao;

        insertUserAsyncTask(UserDao dao){
            mAsyncTaskUserDao = dao;
        }

        @Override
        protected Void doInBackground(final User... params) {
            mAsyncTaskUserDao.insert(params[0]);
            return null;
        }
    }
}
