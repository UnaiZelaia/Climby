package com.climby.climby.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.climby.climby.dao.DBAccess;
import com.climby.climby.dao.UserProfileDAO;
import com.climby.climby.model.UserProfile;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserProfileRepo {
    UserProfileDAO userProfileDAO;
    Executor executor = Executors.newSingleThreadExecutor();

    public UserProfileRepo(Application application) {
        userProfileDAO = DBAccess.getDatabase(application).getUserProfileDAO();
    }

    public LiveData<List<UserProfile>> getAllUsers() {
        return userProfileDAO.getAllUsers();
    }

    public LiveData<UserProfile> getUser(int id) {
        return userProfileDAO.getUserById(id);
    }

    public LiveData<UserProfile> getUserByEmail(String email) {
        return userProfileDAO.getUserByEmail(email);
    }

    public LiveData<UserProfile> getUserByUsername(String username) {
        return userProfileDAO.getUserByUsername(username);
    }

    public void insert(UserProfile userProfile) {
        executor.execute(() -> userProfileDAO.insert(userProfile));
    }

    public void update(UserProfile userProfile) {
        executor.execute(() -> userProfileDAO.update(userProfile));
    }

    public void delete(UserProfile userProfile) {
        executor.execute(() -> userProfileDAO.delete(userProfile));
    }
}
