package com.climby.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.climby.model.Climb;
import com.climby.repos.ClimbRepo;

import java.util.List;

public class ClimbViewmodel extends AndroidViewModel {
    ClimbRepo climbRepo;
    MutableLiveData<Climb> climbSelected = new MutableLiveData<>();

    public ClimbViewmodel(@NonNull Application application){
        super(application);
        climbRepo = new ClimbRepo(application);
    }

    public LiveData<List<Climb>> getUserClimbs(int id){ return climbRepo.getUserClimbs(id); }
    public LiveData<List<Climb>> getRouteClimbs(int id){ return climbRepo.getRouteClimbs(id); }

    public void add(Climb climb){
        climbRepo.insert(climb);
    }

    void delete(Climb climb){
        climbRepo.delete(climb);
    }

    void update(Climb climb){
        climbRepo.update(climb);
    }

    void select(Climb climb){
        climbSelected.setValue(climb);
    }

    MutableLiveData<Climb> selected(){
        return climbSelected;
    }
}
