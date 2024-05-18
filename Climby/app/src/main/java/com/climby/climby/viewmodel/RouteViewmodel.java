package com.climby.climby.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.climby.climby.DataAccess;
import com.climby.climby.model.Route;
import com.climby.climby.model.UserProfile;
import com.climby.climby.repos.RouteRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RouteViewmodel extends AndroidViewModel {
    RouteRepo routeRepo;
    MutableLiveData<Route> routeSelected = new MutableLiveData<>();

    Executor executor = Executors.newSingleThreadExecutor();

    public RouteViewmodel(Application application) {
        super(application);
        routeRepo = new RouteRepo(application);
    }

    public LiveData<List<Route>> getAll() {
        return routeRepo.getAll();
    }

    public LiveData<Route> get(int id) {
        return routeRepo.get(id);
    }

    public int getMaxId() { return routeRepo.getMaxId(); }

    public void delete(Route route) {
        routeRepo.delete(route);
    }

    public void update(Route route) {
        routeRepo.update(route);
    }

    public void insert(Route route) {
        routeRepo.insert(route);
    }

    public void fetchRoutes(){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                List<Route> routes = DataAccess.getAllRoutes();
                if (routes != null){
                    for(int i = 0; i < routes.size(); i++){
                        insert(routes.get(i));
                    }
                }
            }
        });
    }

    public void select(Route route){
        routeSelected.setValue(route);
    }

    public MutableLiveData<Route> selected(){
        return routeSelected;
    }
}
