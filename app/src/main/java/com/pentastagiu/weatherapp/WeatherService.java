package com.pentastagiu.weatherapp;

import com.pentastagiu.weatherapp.holders.CityList;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET(ApiConstants.FORECAST)
    Call<CityList> getWeatherByCityName(@Query("APIKEY") String apiKey,
                                        @Query("q") String cityName,
                                        @Query("units") String units);

    @GET(ApiConstants.FORECAST)
    Call<CityList> getWeatherByCurrentLocation(@Query("APIKEY") String apiKey,
                                               @Query("lat") Double latitude,
                                               @Query("lon") Double longitude,
                                               @Query("units") String units);
    @GET(ApiConstants.WEATHER)
    Call<CityList> getWeatherByFavoriteCity(@Query("APIKEY") String apiKey,
                                            @Query("q") String cityName,
                                            @Query("units") String units);

    @GET(ApiConstants.GROUP)
    Call<CityList> getSeveralFavoriteCities(@Query("APIKEY") String apiKey,
                                            @Query("id") List<Integer> favoriteCities,
                                            @Query("units)") String units);
}
