package com.climby.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.climby.model.User;
import com.climby.model.UserLikedRoutes;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM user")
    LiveData<List<User>> getUsers();

    @Query("SELECT * FROM user WHERE username = :user")
    LiveData<User> getUser(String user);

    @Query("SELECT * FROM user WHERE id = :userId")
    LiveData<UserLikedRoutes> getUserLikedRoutes(int userId);

    @Query("SELECT password FROM user WHERE UPPER(username) = UPPER(:user)")
    String getPasswordLogin(String user);

    @Query("SELECT id FROM user WHERE UPPER(username) = UPPER(:user)")
    int getUserId(String user);


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}
