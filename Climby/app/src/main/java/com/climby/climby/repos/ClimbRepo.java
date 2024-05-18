package com.climby.climby.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.climby.climby.dao.ClimbDAO;
import com.climby.climby.dao.DBAccess;
import com.climby.climby.model.Climb;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ClimbRepo {
    ClimbDAO climbDAO;
    Executor executor = Executors.newSingleThreadExecutor();

    public ClimbRepo(Application application){
        climbDAO = DBAccess.getDatabase(application).getClimbDAO();
    }

    public LiveData<List<Climb>> getAll(){
        return climbDAO.getAll();
    }

    public LiveData<Climb> get(int id){
        return climbDAO.getClimb(id);
    }

    public LiveData<List<Climb>> getUserClimbs(int userId){
        return climbDAO.getUserClimbs(userId);
    }

    public LiveData<List<Climb>> getRouteClimbs(int routeId){
        return climbDAO.getRouteClimbs(routeId);
    }

    public void insert(Climb climb){
        executor.execute(() -> {
            climbDAO.insert(climb);
        });
    }

    public void delete(Climb climb){
        executor.execute(() -> {
            climbDAO.delete(climb);
        });
    }

    public void update(Climb climb){
        executor.execute(() -> {
            climbDAO.update(climb);
        });
    }
}
