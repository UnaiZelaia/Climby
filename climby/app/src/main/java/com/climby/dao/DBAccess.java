package com.climby.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.climby.model.Climb;
import com.climby.model.User;

import org.w3c.dom.Comment;

import okhttp3.Route;

@Database(entities = {User.class, Route.class, Comment.class, Climb.class}, version = 1, exportSchema = false)
public abstract class DBAccess extends RoomDatabase {

    private static volatile DBAccess INSTANCE;

    public abstract UserDAO getUserDAO();
    public abstract RouteDAO getRouteDAO();
    public abstract CommentDAO getCommentDAO();
    public abstract ClimbDAO getClimbDAO();

    public static DBAccess obtainInstance(final Context context){
        if (INSTANCE == null) {
            synchronized ((DBAccess.class)) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                                    DBAccess.class, "climby.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
