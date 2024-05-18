package com.climby.climby.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.climby.climby.dao.DBAccess;
import com.climby.climby.dao.RouteDAO;
import com.climby.climby.model.Route;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class RouteRepo {
    RouteDAO routeDAO;
    Executor executor = Executors.newSingleThreadExecutor();

    public RouteRepo(Application application) {
        routeDAO = DBAccess.getDatabase(application).getRouteDAO();
    }

    public LiveData<List<Route>> getAll() {
        return routeDAO.getAll();
    }

    public LiveData<Route> get(int id) {
        return routeDAO.get(id);
    }

    public int getMaxId(){ return routeDAO.getMaxId(); }

    public void insert(Route route) {
        executor.execute(() -> {
            routeDAO.insert(route);
        });
    }

    public void update(Route route) {
        executor.execute(() -> {
            routeDAO.update(route);
        });
    }

    public void delete(Route route) {
        executor.execute(() -> {
            routeDAO.delete(route);
        });
    }
}
