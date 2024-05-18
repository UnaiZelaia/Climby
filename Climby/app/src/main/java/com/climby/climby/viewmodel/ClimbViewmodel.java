package com.climby.climby.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.climby.climby.model.Climb;
import com.climby.climby.repos.ClimbRepo;

import java.util.List;

public class ClimbViewmodel extends AndroidViewModel {
    ClimbRepo climbRepo;
    MutableLiveData<Climb> climbSelected = new MutableLiveData<>();;

    public ClimbViewmodel(Application application){
        super(application);
        climbRepo = new ClimbRepo(application);
    }

    public LiveData<List<Climb>> getAll(){
        return climbRepo.getAll();
    }

    public LiveData<Climb> get(int id){
        return climbRepo.get(id);
    }

    public LiveData<List<Climb>> getUserClimbs(int userId){
        return climbRepo.getUserClimbs(userId);
    }

    public LiveData<List<Climb>> getRouteClimbs(int routeId){
        return climbRepo.getRouteClimbs(routeId);
    }

    public void insert(Climb climb){
        climbRepo.insert(climb);
    }

    public void update(Climb climb){
        climbRepo.update(climb);
    }

    public void delete(Climb climb){
        climbRepo.delete(climb);
    }
}
