package com.climby.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.climby.model.Comment;

import java.util.List;

public interface CommentDAO {

    @Query("SELECT * FROM comment WHERE route = :id")
    LiveData<List<Comment>> getRouteComments(int id);

    @Query("SELECT * FROM comment WHERE author = :id")
    LiveData<List<Comment>> getUserComments(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Comment comment);

    @Update
    void update(Comment comment);

    @Delete
    void delete(Comment comment);
}
