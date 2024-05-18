package com.climby.climby.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.climby.climby.model.Comment;

import java.util.List;

@Dao
public interface CommentDAO {
    @Query("SELECT * FROM comments")
    LiveData<List<Comment>> getAll();

    @Query("SELECT * FROM comments WHERE id = :id")
    LiveData<Comment> get(int id);

    @Query("SELECT * FROM comments WHERE user_id = :id")
    LiveData<List<Comment>> getUserComments(int id);

    @Query("SELECT * FROM comments WHERE route_id = :id")
    LiveData<List<Comment>> getRouteComments(int id);

    @Insert
    void insert(Comment comment);

    @Update
    void update(Comment comment);

    @Delete
    void delete(Comment comment);

}
