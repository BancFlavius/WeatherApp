package com.pentastagiu.weatherapp.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import io.reactivex.annotations.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "city_table", foreignKeys = @ForeignKey(entity =  User.class,
        parentColumns = "uId",
        childColumns = "userId",
        onDelete = CASCADE))
public class City {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "city_name")
    private String cityName;

    @NonNull
    @ColumnInfo(name = "userId", index = true)
    private int userId;

    public City(int id, String cityName, int userId){
        this.id = id;
        this.cityName = cityName;
        this.userId = userId;
    }

    public int getId() {
        return this.id;
    }

    public String getCityName() {
        return this.cityName;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", cityName='" + cityName + '\'' +
                ", userId=" + userId +
                '}';
    }
}
