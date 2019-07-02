package com.pentastagiu.weatherapp.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.pentastagiu.weatherapp.data.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(User... users);

    @Insert
    void insert(User user);

    @Query("DELETE FROM user_table")
    void deleteUserTable();

    @Query("SELECT uId FROM user_table WHERE email_address = :email")
    int getUserIdByEmail(String email);

    @Query("SELECT * FROM user_table")
    List<User> getUserTable();

    @Query("SELECT * FROM user_table WHERE uId = :id")
    User getUserByiId(int id);

    @Query("SELECT * FROM user_table WHERE email_address = :email")
    User getUserByEmail(String email);

    @Query("SELECT * FROM user_table WHERE full_name = :name")
    List<User> getUserByName(String name);

    @Delete
    void delete(User user);

    @Update
    void updateUsers(User... users);
}
