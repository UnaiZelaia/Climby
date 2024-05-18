package com.climby.climby.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.climby.climby.model.UserProfile;

import java.util.List;

@Dao
public interface UserProfileDAO {
    @Query("SELECT * FROM user_profile")
    LiveData<List<UserProfile>> getAllUsers();

    @Query("SELECT * FROM user_profile WHERE id = :id")
    LiveData<UserProfile> getUserById(int id);

    @Query("SELECT * FROM user_profile WHERE email = :email")
    LiveData<UserProfile> getUserByEmail(String email);

    @Query("SELECT * FROM user_profile WHERE username = :username")
    LiveData<UserProfile> getUserByUsername(String username);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserProfile userProfile);

    @Update
    void update(UserProfile userProfile);

    @Delete
    void delete(UserProfile userProfile);
}
