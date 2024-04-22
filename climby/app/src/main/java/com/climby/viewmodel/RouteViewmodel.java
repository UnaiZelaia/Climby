package com.climby.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.climby.model.Route;
import com.climby.repos.RouteRepo;

import java.util.List;

public class RouteViewmodel extends AndroidViewModel {
    RouteRepo routeRepo;
    MutableLiveData<Route> routeSelected = new MutableLiveData<>();

    public RouteViewmodel(@NonNull Application application){
        super(application);
        routeRepo = new RouteRepo(application);
    }

    public LiveData<List<Route>> get(){ return routeRepo.get(); }
    public LiveData<List<Route>> getUserRoutes(int id) { return routeRepo.getUserRoutes(id); }

    public void add(Route route) {
        routeRepo.insert(route);
    }

    void delete(Route route){
        routeRepo.delete(route);
    }

    void update(Route route){
        routeRepo.update(route);
    }

    void select(Route route){
        routeSelected.setValue(route);
    }

    MutableLiveData<Route> selected(){
        return routeSelected;
    }

}
