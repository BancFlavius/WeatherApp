package com.pentastagiu.weatherapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;

import com.pentastagiu.weatherapp.RoomApp;
import com.pentastagiu.weatherapp.data.City;
import com.pentastagiu.weatherapp.data.User;

@Database(entities = {City.class, User.class}, version = AppDatabase.DB_VERSION, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    static final int DB_VERSION = 1;
    private static AppDatabase INSTANCE;
    private static final String DATABASE_NAME = "my_db";

    public abstract UserDao userDao();
    public abstract CityDao cityDao();

//    public static AppDatabase getDatabase(final Context context) {
//        if (INSTANCE == null) {
//            synchronized (AppDatabase.class) {
//                if (INSTANCE == null) {
//                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
//                            AppDatabase.class, DATABASE_NAME)
//                            .build();
//                }
//            }
//        }
//        return INSTANCE;
//    }

    public static AppDatabase getInstance() {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(RoomApp.getAppContext(), AppDatabase.class, DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }

        return INSTANCE;
    }


}
