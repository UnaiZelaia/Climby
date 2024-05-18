package com.climby.climby.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.climby.climby.converter.Converters;
import com.climby.climby.model.Climb;
import com.climby.climby.model.Comment;
import com.climby.climby.model.Route;
import com.climby.climby.model.UserLikedRoutes;
import com.climby.climby.model.UserProfile;

@Database(entities = {UserProfile.class, Route.class, Climb.class, Comment.class, UserLikedRoutes.class}, version = 3, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class DBAccess extends RoomDatabase {
    private static volatile DBAccess INSTANCE;

    public abstract UserProfileDAO getUserProfileDAO();
    public abstract RouteDAO getRouteDAO();
    public abstract  ClimbDAO getClimbDAO();
    public abstract CommentDAO getCommentDAO();
    public abstract UserLikedRoutesDAO getUserLikedRoutesDAO();

    public static DBAccess getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DBAccess.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context,
                                DBAccess.class, "climby.db")
                                .build();
                }
            }
        }
        return INSTANCE;
    }



}
