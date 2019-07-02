package com.pentastagiu.weatherapp.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.pentastagiu.weatherapp.ApiConstants;
import com.pentastagiu.weatherapp.R;
import com.pentastagiu.weatherapp.RetrofitClient;
import com.pentastagiu.weatherapp.database.AppDatabase;
import com.pentastagiu.weatherapp.dialogs.AddCityToFavorites;
import com.pentastagiu.weatherapp.dialogs.PermissionDeniedDialog;
import com.pentastagiu.weatherapp.holders.City;
import com.pentastagiu.weatherapp.holders.CityList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForecastActivity extends AppCompatActivity {
    private static final String TAG = ForecastActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 1234;
    private final int LOCATION_FOUND = 1;
    private final int LOCATION_NOT_FOUND = 0;
    private final String SWITCH_TO_F = "Switch to: F";
    private final String SWITCH_TO_C = "Switch to: C";
    private City[] cityList;
    private RetrofitClient retrofit;
    private Intent intent;
    public static String cityName;
    public static String cityId;
    private String switchTempString;
    private final String CURRENT_WEATHER = "Current Weather";


    private FusedLocationProviderClient client;
    private Double latitude;
    private Double longitude;

    private ImageView imgAddToFavorites;
    private TextView switchUnits;
    private TextView cityNameTextView;
    private TextView currentTemperature;
    private TextView tomorrowsDate;
    private TextView afterTomorrowDate;
    private TextView thirdDate;
    private TextView tomorrowsTemp;
    private TextView afterTomorrowTemp;
    private TextView thirdTemp;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forecastactivity_layout);

        retrofit = RetrofitClient.getInstance();
        intent = getIntent();
        initUi();
        if (!sentBySearchActivity(intent, returnFahrenheitOrCelsius(switchTempString), false)) {
            requestLocationPermission();
        }
    }

    private boolean sentBySearchActivity(Intent intent, String currentTempUnit, boolean sentToSwitchUnits) {
        imgAddToFavorites.setVisibility(View.GONE);
        boolean isSentBySearchActivity = false;
        cityName = intent.getStringExtra(ApiConstants.INTENT_CITY_LIST);
        if (cityName != null) {
            isSentBySearchActivity = true;

            Call<CityList> call = retrofit.getWeatherService().getWeatherByCityName(ApiConstants.API_KEY, cityName, currentTempUnit);

            call.enqueue(new Callback<CityList>() {
                @Override
                public void onResponse(Call<CityList> call, Response<CityList> response) {
                    if (response.body() != null) {
                        imgAddToFavorites.setVisibility(View.VISIBLE);
                        cityList = response.body().getList();
                        displayWeatherInformation(cityList, cityName.toUpperCase());
                    } else {
                        Toast.makeText(getApplicationContext(), "ERROR: city doesn't exist", Toast.LENGTH_LONG).show();
                        ForecastActivity.this.startActivity(new Intent(ForecastActivity.this, SearchActivity.class));
                    }
                }

                @Override
                public void onFailure(Call<CityList> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), "ERROR: try again later", Toast.LENGTH_LONG).show();
                    ForecastActivity.this.startActivity(new Intent(ForecastActivity.this, SearchActivity.class));
                }
            });
        } else if(sentToSwitchUnits) {
            switchTemperatureUnits(currentTemperature);
            switchTemperatureUnits(tomorrowsTemp);
            switchTemperatureUnits(afterTomorrowTemp);
            switchTemperatureUnits(thirdTemp);
        }

        return isSentBySearchActivity;
    }

    private void initUi() {
        imgAddToFavorites = findViewById(R.id.img_addToFavorites);
        switchUnits = findViewById(R.id.tv_temperatureSwitch);
        getSwitchTempToString(switchUnits);
        cityNameTextView = findViewById(R.id.tv_cityName);
        currentTemperature = findViewById(R.id.tv_currentTemperature);
        tomorrowsDate = findViewById(R.id.tv_tomorrow);
        afterTomorrowDate = findViewById(R.id.tv_afterTomorrowDate);
        thirdDate = findViewById(R.id.tv_thirdDay);
        tomorrowsTemp = findViewById(R.id.tv_tomorrow_temp);
        afterTomorrowTemp = findViewById(R.id.tv_afterTomorrowTemp);
        thirdTemp = findViewById(R.id.tv_third_day_temp);
    }

    private void displayWeatherInformation(City[] cityList, String cityName) {
        for (int i = 0; i < cityList.length; i++) {
            Log.d(TAG, "//displayWeatherInformation");
            switch (i) {
                case 0:
                    cityNameTextView.setText(cityName);
                    currentTemperature.setText(cityList[i].getCurrentTemperature());
                    break;
                case 1:
                    tomorrowsDate.setText(cityList[i].getDate());
                    tomorrowsTemp.setText(cityList[i].getCurrentTemperature());
                    break;
                case 2:
                    afterTomorrowDate.setText(cityList[i].getDate());
                    afterTomorrowTemp.setText(cityList[i].getCurrentTemperature());
                    break;
                case 3:
                    thirdDate.setText(cityList[i].getDate());
                    thirdTemp.setText(cityList[i].getCurrentTemperature());
                    break;
                default:
                    break;
            }
        }
    }

    private String returnFahrenheitOrCelsius(String input) {
        if (input.equals(SWITCH_TO_F)) {
            return ApiConstants.METRIC_UNITS;
        } else if (input.equals(SWITCH_TO_C)) {
            return ApiConstants.IMPERIAL_UNITS;
        } else {
            return ApiConstants.DEFAULT_UNITS;
        }
    }

    private void getSwitchTempToString(TextView tv) {
        switchTempString = tv.getText().toString();
        Log.d(TAG, switchTempString);
    }

    private void requestLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
            printCurrentWeather();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    printCurrentWeather();
                } else {
                    openPermissionDeniedDialog();
                }
            }
        }
    }

    private void loadWeather(String currentTempUnit) {
        Call<CityList> call = retrofit.getWeatherService().getWeatherByCurrentLocation(ApiConstants.API_KEY, latitude, longitude , currentTempUnit);

        call.enqueue(new Callback<CityList>() {
            @Override
            public void onResponse(Call<CityList> call, Response<CityList> response) {
                if (response.body() != null) {
                    Log.d(TAG, "SearchByGPS" + response.body().toString());
                    cityList = response.body().getList();
                    displayWeatherInformation(cityList, CURRENT_WEATHER);
                } else {
                    Log.d(TAG, "SearchByGPS// " + "response is null");
                }
            }

            @Override
            public void onFailure(Call<CityList> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "ERROR: try again later", Toast.LENGTH_LONG).show();
                ForecastActivity.this.startActivity(new Intent(ForecastActivity.this, SearchActivity.class));
            }
        });
    }

    private int getLocation() {
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(ForecastActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return LOCATION_NOT_FOUND;
        } else
        client.getLastLocation().addOnSuccessListener(ForecastActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                latitude = location.getLatitude();
                longitude = location.getLongitude();
                Log.d(TAG, "getLocation// " + latitude + " -lat long- "+  longitude );
                loadWeather(returnFahrenheitOrCelsius(switchTempString));
            }
        });
        return LOCATION_FOUND;
    }

    private void openPermissionDeniedDialog(){
        PermissionDeniedDialog permissionDeniedDialog = new PermissionDeniedDialog();
        permissionDeniedDialog.show(getSupportFragmentManager(), "location permission denied");
    }

    private void printCurrentWeather(){
        if(getLocation() == LOCATION_NOT_FOUND) {
            openPermissionDeniedDialog();
        }
    }

    public void switchTempUnit(View v){
        if(switchTempString.equals(SWITCH_TO_F)){
            switchUnits.setText(SWITCH_TO_C);
            getSwitchTempToString(switchUnits);
            sentBySearchActivity(intent, returnFahrenheitOrCelsius(switchTempString), true);
        } else if(switchTempString.equals(SWITCH_TO_C)){
            switchUnits.setText(SWITCH_TO_F);
            getSwitchTempToString(switchUnits);
            sentBySearchActivity(intent, returnFahrenheitOrCelsius(switchTempString), true);
        }
    }

    public void addCityToFavorites(View v){
        AddCityToFavorites addCityToFavorites = new AddCityToFavorites();
        addCityToFavorites.show(getSupportFragmentManager(), TAG);
    }

    private void switchTemperatureUnits(TextView view){
        if(switchUnits.getText().toString().equals(SWITCH_TO_C)){
            String[] FtoC;
            double dFtoC;
            FtoC = view.getText().toString().split(" ");
            dFtoC = (9f /5) * Double.parseDouble(FtoC[0]) + 32;
            FtoC[0] = String.format ("%.2f", dFtoC);
            view.setText(FtoC[0] + " " + FtoC[1]);
        } else {
            String[] CtoF;
            double dCtoF;
            CtoF = view.getText().toString().split(" ");
            dCtoF = 5f /9 * (Double.parseDouble(CtoF[0]) - 32);
            CtoF[0] = String.format ("%.2f", dCtoF);
            view.setText(CtoF[0] + " " + CtoF[1]);
        }
    }

    public void toBufferActivity(View view) {
        ForecastActivity.this.startActivity(new Intent(ForecastActivity.this, BufferActivity.class));
    }
}
