package com.climby.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.climby.model.User;
import com.climby.repos.UserRepo;

public class UserViewmodel extends AndroidViewModel {
    UserRepo usersRepo;
    //MutableLiveData<List<Client>> listClientsMutableLiveData = new MutableLiveData<>();
    MutableLiveData<User> userSelected = new MutableLiveData<>();

    public UserViewmodel(@NonNull Application application){
        super(application);
        usersRepo = new UserRepo(application);
    }

    public LiveData<User> get(String user){ return usersRepo.get(user); }

    public String getPasswordLogin(String user){ return usersRepo.getPasswordLogin(user); }

    public int getUserId(String user){ return usersRepo.getUserId(user); }

    public void add(User user) {
        usersRepo.insert(user);
    }

    void delete(User user) {
        usersRepo.delete(user);
    }

    void update(User user) {
        usersRepo.update(user);
    }

    void select(User user) {
        userSelected.setValue(user);
    }

    MutableLiveData<User> selected() {
        return userSelected;
    }
}
