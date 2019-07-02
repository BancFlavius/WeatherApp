package com.pentastagiu.weatherapp.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pentastagiu.weatherapp.ApiConstants;
import com.pentastagiu.weatherapp.R;
import com.pentastagiu.weatherapp.RetrofitClient;
import com.pentastagiu.weatherapp.adapters.OSRecyclerViewAdapter;
import com.pentastagiu.weatherapp.database.AppDatabase;
import com.pentastagiu.weatherapp.holders.City;
import com.pentastagiu.weatherapp.holders.CityList;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MyPlacesActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private static final String TAG = MyPlacesActivity.class.getSimpleName();
    private static final String FROM_A_TO_Z = "a";
    private static final String FROM_Z_TO_A = "z";
    private static final String FROM_LOWEST_TO_HIGHEST = "lowest";
    private static final String FROM_HIGHEST_TO_LOWEST = "highest";

    private RetrofitClient retrofit;
    private RecyclerView rvOs;
    private OSRecyclerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private City[] cityList;
    private CityList listItems = new CityList();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.myplacesactivity_layout);

        retrofit = RetrofitClient.getInstance();

        getFavoriteCities();
    }

    private void getFavoriteCities(){
        List<String> favoriteCities;

        favoriteCities = AppDatabase.getInstance().cityDao().getCityNamesByUserId(ApiConstants.userId);

        for(String city : favoriteCities){
            Log.d(TAG, "//getFavoriteCities() " + city);
            try{
                displayFavoriteCities(city);
            }catch (NullPointerException e){
                Toast.makeText(getApplicationContext(), "Error, try again later", Toast.LENGTH_SHORT).show();
            }
        }
        initializeRecyclerView(listItems.getList());
    }

    private void initializeRecyclerView(City[] cityArray){
        rvOs = findViewById(R.id.rv_dev);
        layoutManager = new LinearLayoutManager(this);
        rvOs.setLayoutManager(layoutManager);

        adapter = new OSRecyclerViewAdapter(cityArray, this);
        rvOs.setAdapter(adapter);
    }

    private void read() {
        String fileContent = readFromTxt();

        listItems = convertJsonToObject(fileContent);
        cityList = listItems.getList();

        initializeRecyclerView(cityList);
    }

    private String readFromTxt() {
        StringBuilder buf = new StringBuilder();
        InputStream json;
        try {
            json = getAssets().open("dataSet.txt");
            BufferedReader in = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                in = new BufferedReader(new InputStreamReader(json, StandardCharsets.UTF_8));
            }
            String str;

            while ((str = in.readLine()) != null) {
                buf.append(str);
            }

            in.close();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        }
        Log.d(TAG, "buf = " + buf.toString());
        return buf.toString();
    }

    private CityList convertJsonToObject(String dataSet) {
        Gson gson = new Gson();

        CityList cities;
        cities = gson.fromJson(dataSet, new TypeToken<CityList>() {}.
                getType());

        Log.d(TAG, "cities= " + cities.toString());

        return cities;
    }

    public void displayFavoriteCities(String nameOfCity){
        Call<CityList> call = retrofit.getWeatherService().getWeatherByFavoriteCity(ApiConstants.API_KEY, nameOfCity, ApiConstants.METRIC_UNITS);

        call.enqueue(new Callback<CityList>() {
            @Override
            public void onResponse(Call<CityList> call, Response<CityList> response) {
                if (response.body() != null) {
                    Log.d(TAG, "body is NOT null");
                    listItems.concatenateCityList(response.body().getList());
                    Log.d(TAG, "//displayFavoriteCities listItems - " + listItems.toString());
                } else {
                    Log.d(TAG, "body is null");
                    Toast.makeText(getApplicationContext(), "ERROR: city doesn't exist", Toast.LENGTH_LONG).show();
                    MyPlacesActivity.this.startActivity(new Intent(MyPlacesActivity.this, SearchActivity.class));
                }
            }

            @Override
            public void onFailure(Call<CityList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR: try again later", Toast.LENGTH_LONG).show();
                MyPlacesActivity.this.startActivity(new Intent(MyPlacesActivity.this, SearchActivity.class));
            }
        });
    }

    public void showPopup(View v) {
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.sort_popup_menu);
        popupMenu.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.fromAtoZ:
                sortAlphabetically(FROM_A_TO_Z);
                Toast.makeText(this, "Sorted from A to Z", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.fromZtoA:
                sortAlphabetically(FROM_Z_TO_A);
                Toast.makeText(this, "Sorted from Z to A", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.fromLowest:
                sortByTemperature(FROM_LOWEST_TO_HIGHEST);
                Toast.makeText(this, "Sorted from lowest temperature", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.fromHighest:
                sortByTemperature(FROM_HIGHEST_TO_LOWEST);
                Toast.makeText(this, "Sorted from highest temperature", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }
    }

    private void sortAlphabetically(final String option){
        LinkedList<City> sortedList = new LinkedList<>(Arrays.asList(listItems.getList()));

        Collections.sort(sortedList, new Comparator<City>() {
            @Override
            public int compare(City o1, City o2) {
                if(option.equals(FROM_LOWEST_TO_HIGHEST)){
                    return o1.getName().compareTo(o2.getName());
                } else {
                    return o2.getName().compareTo(o1.getName());
                }
            }
        });

        listItems.setList(sortedList);

        initializeRecyclerView(listItems.getList());
    }

    private void sortByTemperature(final String option){
        LinkedList<City> sortedList = new LinkedList<>(Arrays.asList(listItems.getList()));

        Collections.sort(sortedList, new Comparator<City>() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public int compare(City o1, City o2) {
                if(option.equals(FROM_LOWEST_TO_HIGHEST)){
                    return Double.compare(Double.parseDouble(o1.getCurrentTemperature()), Double.parseDouble(o2.getCurrentTemperature()));
                } else {
                    return Double.compare(Double.parseDouble(o2.getCurrentTemperature()), Double.parseDouble(o1.getCurrentTemperature()));

                }
            }
        });

        listItems.setList(sortedList);

        initializeRecyclerView(listItems.getList());
    }

    public void toBufferActivity(View view) {
        MyPlacesActivity.this.startActivity(new Intent(MyPlacesActivity.this, BufferActivity.class));
    }
}
