package com.climby.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.climby.dao.ClimbDAO;
import com.climby.dao.DBAccess;
import com.climby.model.Climb;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ClimbRepo {
    ClimbDAO climbDAO;
    Executor executor = Executors.newSingleThreadExecutor();

    public ClimbRepo(Application application){
        climbDAO = DBAccess.obtainInstance(application).getClimbDAO();
    }

    public LiveData<List<Climb>> getUserClimbs(int id) { return climbDAO.getUserClimbs(id); }
    public LiveData<List<Climb>> getRouteClimbs(int id) { return climbDAO.getRouteClimbs(id); }

    public void insert(Climb climb) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                climbDAO.insert(climb);
            }
        });
    }

    public void delete(Climb climb) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                climbDAO.delete(climb);
            }
        });
    }

    public void update(Climb climb) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                climbDAO.update(climb);
            }
        });
    }
}
