package com.climby.climby.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.climby.climby.model.Comment;
import com.climby.climby.repos.CommentRepo;

import java.util.List;

public class CommentViewmodel extends AndroidViewModel {
    CommentRepo commentRepo;

    MutableLiveData<Comment> commentSelected = new MutableLiveData<>();

    public CommentViewmodel(Application application) {
        super(application);
        commentRepo = new CommentRepo(application);
    }

    public LiveData<List<Comment>> getAll(){
        return commentRepo.getAll();
    }

    public LiveData<Comment> get(int id){
        return commentRepo.get(id);
    }

    public LiveData<List<Comment>> getRouteComments(int routeId){
        return commentRepo.getRouteComments(routeId);
    }

    public LiveData<List<Comment>> getUserComments(int userId){
        return commentRepo.getUserComments(userId);
    }

    public void insert(Comment comment){
        commentRepo.insert(comment);
    }

    public void update(Comment comment){
        commentRepo.update(comment);
    }

    public void delete(Comment comment){
        commentRepo.delete(comment);
    }

}
