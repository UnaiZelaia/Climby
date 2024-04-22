package com.climby.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.climby.model.Comment;
import com.climby.repos.CommentRepo;

import java.util.List;

public class CommentViewmodel extends AndroidViewModel {
    CommentRepo commentRepo;
    MutableLiveData<Comment> commentSelected = new MutableLiveData<>();

    public CommentViewmodel(@NonNull Application application){
        super(application);
        MutableLiveData<Comment> commentSelected = new MutableLiveData<>();
    }

    public LiveData<List<Comment>> getUserComments(int id) { return commentRepo.getUserComments(id); }
    public LiveData<List<Comment>> getRouteComments(int id) { return commentRepo.getRouteComments(id); }

    public void add(Comment comment){
        commentRepo.insert(comment);
    }

    void delete(Comment comment){
        commentRepo.delete(comment);
    }

    void update(Comment comment){
        commentRepo.update(comment);
    }

    void select(Comment comment){
        commentSelected.setValue(comment);
    }

    MutableLiveData<Comment> selected(){
        return commentSelected;
    }

}
