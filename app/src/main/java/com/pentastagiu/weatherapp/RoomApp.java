package com.pentastagiu.weatherapp;

import android.app.Application;
import android.content.Context;

import com.pentastagiu.weatherapp.database.AppDatabase;

import java.lang.ref.WeakReference;

public class RoomApp extends Application {
    private static WeakReference<RoomApp> appContext;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = new WeakReference<>(this);

        AppDatabase.getInstance(); //this will init & fill db
    }

    public static Context getAppContext() {
        return appContext != null ? appContext.get() : null;
    }
}
