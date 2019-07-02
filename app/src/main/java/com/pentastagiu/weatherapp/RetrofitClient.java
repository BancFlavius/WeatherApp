package com.pentastagiu.weatherapp;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static RetrofitClient instance = null;
    private Retrofit retrofit;
    private OkHttpClient client;
    private WeatherService weatherService;

    private RetrofitClient(){
        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        client = okHttpBuilder.build();

        retrofit = new Retrofit.Builder().baseUrl(ApiConstants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        weatherService = retrofit.create(WeatherService.class);
    }

    public static RetrofitClient getInstance(){
        if(instance == null) instance = new RetrofitClient();

        return instance;
    }

    public WeatherService getWeatherService() {
        return weatherService;
    }
}
