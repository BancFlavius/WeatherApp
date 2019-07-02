package com.pentastagiu.weatherapp.activities;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.pentastagiu.weatherapp.ApiConstants;
import com.pentastagiu.weatherapp.R;
import com.pentastagiu.weatherapp.data.City;
import com.pentastagiu.weatherapp.data.User;
import com.pentastagiu.weatherapp.database.AppDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class BufferActivity extends AppCompatActivity {
    private String TAG = BufferActivity.class.getSimpleName();
    private TextView tv_weather;
    private TextView tv_myPlaces;
    private TextView tv_logout;
    private TextView tv_maps;
    private TextView tv_myProfile;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bufferactivity_layout);

        initUi();
        addApplicationLogo();
        listeners();

        getAllUsers();
        getAllCities();
    }

    private void getAllUsers(){
        Log.d(TAG, "//getAllUsers() " + AppDatabase.getInstance().userDao().getUserTable().toString());
    }

    private void getAllCities(){
        Log.d(TAG, "//getAllCities() " + AppDatabase.getInstance().cityDao().getCityTable().toString());
    }

    private void initUi(){
        tv_weather = findViewById(R.id.tv_weather);
        tv_myPlaces = findViewById(R.id.tv_myPlaces);
        tv_logout = findViewById(R.id.tv_logout);
        tv_maps = findViewById(R.id.tv_maps);
        tv_myProfile = findViewById(R.id.tv_myProfile);
    }

    private void listeners(){
        tv_weather.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BufferActivity.this.startActivity(new Intent(BufferActivity.this, ForecastActivity.class));
            }
        });

        tv_myPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BufferActivity.this.startActivity(new Intent(BufferActivity.this, MyPlacesActivity.class));
            }
        });

        tv_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                ApiConstants.userId = -1;
                BufferActivity.this.startActivity(new Intent(BufferActivity.this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            }
        });

        tv_maps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Intent intent = new Intent(BufferActivity.this, AutocompleteSupportFragmentActivity.class);
                startActivity(intent);
            }
        });

        tv_myProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                Toast comingSoon = Toast.makeText(getApplicationContext(), R.string.coming_soon, Toast.LENGTH_SHORT);
                comingSoon.show();
            }
        });
    }


    private void addApplicationLogo(){
        ImageView imgTest = findViewById(R.id.img_logo);
        Glide.with(this)
                .load("https://i.imgur.com/QQ9CXHJ.png")
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(imgTest);
    }
}
