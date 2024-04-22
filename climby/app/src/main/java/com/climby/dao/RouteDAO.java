package com.climby.dao;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.climby.model.Route;

import java.util.List;

@Dao
public interface RouteDAO {
    @Query("SELECT * FROM route")
    LiveData<List<Route>> getRoutes();

    @Query("SELECT * FROM route WHERE author = :id")
    LiveData<List<Route>> getUserRoutes(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Route route);

    @Update
    void update(Route route);

    @Delete
    void delete(Route route);
}
