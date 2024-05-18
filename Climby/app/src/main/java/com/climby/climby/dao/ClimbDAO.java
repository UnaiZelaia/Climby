package com.climby.climby.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.climby.climby.model.Climb;

import java.util.List;

@Dao
public interface ClimbDAO {

    @Query("SELECT * FROM climb")
    public LiveData<List<Climb>> getAll();

    @Query("SELECT * FROM climb WHERE id = :climbId")
    public LiveData<Climb> getClimb(int climbId);

    @Query("SELECT * FROM climb WHERE user_id = :userProfileId")
    public LiveData<List<Climb>> getUserClimbs(int userProfileId);

    @Query("SELECT * FROM climb WHERE route_id = :routeId")
    public LiveData<List<Climb>> getRouteClimbs(int routeId);

    @Insert
    public void insert(Climb climb);

    @Update
    public void update(Climb climb);

    @Delete
    public void delete(Climb climb);

}
