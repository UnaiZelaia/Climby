package com.climby.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.climby.dao.CommentDAO;
import com.climby.dao.DBAccess;
import com.climby.model.Comment;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CommentRepo {
    CommentDAO commentDAO;
    Executor executor = Executors.newSingleThreadExecutor();

    public CommentRepo(Application application){
        commentDAO = DBAccess.obtainInstance(application).getCommentDAO();
    }

    public LiveData<List<Comment>> getRouteComments(int id){ return commentDAO.getRouteComments(id); }
    public LiveData<List<Comment>> getUserComments(int id){ return commentDAO.getUserComments(id); }

    public void insert(Comment comment) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                commentDAO.insert(comment);
            }
        });
    }

    public void delete(Comment comment) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                commentDAO.delete(comment);
            }
        });
    }

    public void update(Comment comment) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                commentDAO.update(comment);
            }
        });
    }

}
