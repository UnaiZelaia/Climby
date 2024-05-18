package com.climby.climby.repos;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.climby.climby.dao.CommentDAO;
import com.climby.climby.dao.DBAccess;
import com.climby.climby.model.Comment;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class CommentRepo {

    CommentDAO commentDAO;
    Executor executor = Executors.newSingleThreadExecutor();

    public CommentRepo(Application application){
        commentDAO = DBAccess.getDatabase(application).getCommentDAO();
    }

    public LiveData<List<Comment>> getAll(){
        return commentDAO.getAll();
    }

    public LiveData<Comment> get(int id) {
        return commentDAO.get(id);
    }

    public LiveData<List<Comment>> getUserComments(int user_id){
        return commentDAO.getUserComments(user_id);
    }

    public LiveData<List<Comment>> getRouteComments(int route_id){
        return commentDAO.getRouteComments(route_id);
    }

    public void insert(Comment comment){
        executor.execute(() -> {
            commentDAO.insert(comment);
        });
    }

    public void update(Comment comment){
        executor.execute(() -> {
            commentDAO.update(comment);
        });
    }

    public void delete(Comment comment){
        executor.execute(() -> {
            commentDAO.delete(comment);
        });
    }
}
