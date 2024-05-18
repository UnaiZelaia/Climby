package com.climby.climby.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.climby.climby.dao.DBAccess;
import com.climby.climby.dao.UserLikedRoutesDAO;
import com.climby.climby.model.UserLikedRoutes;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserLikedRoutesRepo {
    UserLikedRoutesDAO userLikedRoutesDAO;

    Executor executor = Executors.newSingleThreadExecutor();

    public UserLikedRoutesRepo(Application application) {
        userLikedRoutesDAO = DBAccess.getDatabase(application).getUserLikedRoutesDAO();
    }

    public LiveData<List<UserLikedRoutes>> getAll() {
        return userLikedRoutesDAO.getAll();
    }

    public LiveData<List<UserLikedRoutes>> getByUserId(int userId) {
        return userLikedRoutesDAO.getByUserId(userId);
    }

    public LiveData<List<UserLikedRoutes>> getByRouteId(int routeId) {
        return userLikedRoutesDAO.getByRouteId(routeId);
    }

    public LiveData<UserLikedRoutes> getUserLikedRoute(int routeId, int userId) {
        return userLikedRoutesDAO.getUserLikedRoute(routeId, userId);
    }

    public void insert(UserLikedRoutes userLikedRoutes) {
        executor.execute(() -> {
            userLikedRoutesDAO.insert(userLikedRoutes);
        });
    }

    public void delete(UserLikedRoutes userLikedRoutes) {
        executor.execute(() -> {
            userLikedRoutesDAO.delete(userLikedRoutes);
        });
    }

    public void update(UserLikedRoutes userLikedRoutes) {
        executor.execute(() -> {
            userLikedRoutesDAO.update(userLikedRoutes);
        });
    }
}
