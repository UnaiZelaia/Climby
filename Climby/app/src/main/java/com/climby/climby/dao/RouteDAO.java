package com.climby.climby.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.climby.climby.model.Route;

import java.util.List;

@Dao
public interface RouteDAO {
    @Query("SELECT * FROM Route")
    public LiveData<List<Route>> getAll();

    @Query("SELECT * FROM Route WHERE id = :id")
    public LiveData<Route> get(int id);

    @Query("SELECT MAX(id) AS max_id FROM Route")
    public int getMaxId();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void insert(Route route);

    @Delete
    public void delete(Route route);

    @Update
    public void update(Route route);
}
