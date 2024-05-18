package com.climby.climby.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.climby.climby.DataAccess;
import com.climby.climby.model.UserProfile;
import com.climby.climby.repos.UserProfileRepo;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class UserProfileViewmodel extends AndroidViewModel {
    UserProfileRepo userProfileRepo;
    Executor executor = Executors.newSingleThreadExecutor();

    MutableLiveData<UserProfile> userSelected = new MutableLiveData<>();

    public UserProfileViewmodel(Application application) {
        super(application);
        userProfileRepo = new UserProfileRepo(application);
    }

    public LiveData<List<UserProfile>> getAll() {
        return userProfileRepo.getAllUsers();
    }

    public LiveData<UserProfile> getById(int id) {
        return userProfileRepo.getUser(id);
    }

    public LiveData<UserProfile> getByUsername(String username) {
        return userProfileRepo.getUserByUsername(username);
    }

    public LiveData<UserProfile> getByEmail(String email) {
        return userProfileRepo.getUserByEmail(email);
    }

    public void insert(UserProfile userProfile) {
        userProfileRepo.insert(userProfile);
    }

    public void update(UserProfile userProfile) {
        userProfileRepo.update(userProfile);
    }

    public void delete(UserProfile userProfile) {
        userProfileRepo.delete(userProfile);
    }

    public void fetchUserData(int userId) {
        executor.execute(() -> {
            UserProfile userProfile = DataAccess.getMyUserData(userId);
            Log.d("USER DATA: ", userProfile.toString());
            if (userProfile != null) {
                insert(userProfile);
            }
        });
    }
}
