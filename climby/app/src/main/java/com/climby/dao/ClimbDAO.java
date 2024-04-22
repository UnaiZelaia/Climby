package com.climby.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.climby.model.Climb;

import java.util.List;

public interface ClimbDAO {

    @Query("SELECT * FROM climb WHERE route = :id")
    LiveData<List<Climb>> getRouteClimbs(int id);

    @Query("SELECT * FROM climb WHERE author = :id")
    LiveData<List<Climb>> getUserClimbs(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Climb climb);

    @Update
    void update(Climb climb);

    @Delete
    void delete(Climb climb);
}
