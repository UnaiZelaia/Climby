package com.climby.climby.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.climby.climby.model.UserLikedRoutes;
import com.climby.climby.repos.UserLikedRoutesRepo;

import java.util.List;

public class UserLikedRoutesViewmodel extends AndroidViewModel {
    UserLikedRoutesRepo userLikedRoutesRepo;
    MutableLiveData<UserLikedRoutesRepo> UserLikedRouteSelected = new MutableLiveData<>();

    public UserLikedRoutesViewmodel(Application application) {
        super(application);
        userLikedRoutesRepo = new UserLikedRoutesRepo(application);
    }

    public LiveData<List<UserLikedRoutes>> getAll(){ return userLikedRoutesRepo.getAll();}

    public LiveData<List<UserLikedRoutes>> getByUserId(int userId){ return userLikedRoutesRepo.getByUserId(userId);}

    public LiveData<List<UserLikedRoutes>> getByRouteId(int routeId){ return userLikedRoutesRepo.getByRouteId(routeId);}

    public LiveData<UserLikedRoutes> getUserLikedRoute(int userId, int routeId){ return userLikedRoutesRepo.getUserLikedRoute(userId, routeId);}

    public void insert(UserLikedRoutes userLikedRoutes){ userLikedRoutesRepo.insert(userLikedRoutes);}

    public void update(UserLikedRoutes userLikedRoutes){ userLikedRoutesRepo.update(userLikedRoutes);}

    public void delete(UserLikedRoutes userLikedRoutes){ userLikedRoutesRepo.delete(userLikedRoutes);}
}
