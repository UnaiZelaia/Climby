package com.climby.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.climby.dao.DBAccess;
import com.climby.dao.RouteDAO;
import com.climby.model.Route;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RouteRepo {
    RouteDAO routeDAO;
    Executor executor = Executors.newSingleThreadExecutor();

    public RouteRepo(Application application){
        routeDAO = DBAccess.obtainInstance(application).getRouteDAO();
    }

    public LiveData<List<Route>> get(){ return routeDAO.getRoutes();}

    public LiveData<List<Route>> getUserRoutes(int id){ return routeDAO.getUserRoutes(id); }

    public void insert(Route route){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                routeDAO.insert(route);
            }
        });
    }

    public void delete(Route route) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                routeDAO.delete(route);
            }
        });
    }

    public void update(Route route) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                routeDAO.update(route);
            }
        });
    }
}
