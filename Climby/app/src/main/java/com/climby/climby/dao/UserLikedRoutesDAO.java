package com.climby.climby.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.climby.climby.model.UserLikedRoutes;

import java.util.List;

@Dao
public interface UserLikedRoutesDAO {
    @Query("SELECT * FROM user_liked_routes")
    LiveData<List<UserLikedRoutes>> getAll();

    @Query("SELECT * FROM user_liked_routes WHERE user_id = :id")
    LiveData<List<UserLikedRoutes>> getByUserId(int id);

    @Query("SELECT * FROM user_liked_routes WHERE route_id = :id")
    LiveData<List<UserLikedRoutes>> getByRouteId(int id);

    @Query("SELECT * FROM user_liked_routes WHERE route_id = :id AND user_id = :userId")
    LiveData<UserLikedRoutes> getUserLikedRoute(int id, int userId);

    @Insert
    void insert(UserLikedRoutes userLikedRoutes);

    @Delete
    void delete(UserLikedRoutes userLikedRoutes);

    @Update
    void update(UserLikedRoutes userLikedRoutes);
}
