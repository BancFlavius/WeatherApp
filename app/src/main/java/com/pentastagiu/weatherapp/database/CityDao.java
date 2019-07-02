package com.pentastagiu.weatherapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pentastagiu.weatherapp.data.City;

import java.util.List;

@Dao
public interface CityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(City... cities);

    @Insert
    void insert(City city);

    @Query("DELETE FROM city_table")
    void deleteCityTable();

    @Query("DELETE FROM city_table WHERE userId = :userId")
    void deleteCityByUserId(int userId);

    @Query("SELECT * FROM city_table")
    List<City> getCityTable();

    @Query("SELECT city_name FROM city_table WHERE userId = :userId")
    List<String> getCityNamesByUserId(int userId);

    @Delete
    void delete(City city);

    @Update
    void updateUsers(City... cities);
}
