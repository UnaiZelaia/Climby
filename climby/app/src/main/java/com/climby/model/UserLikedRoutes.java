package com.climby.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class UserLikedRoutes {
    @Embedded public User User;
    @Relation(
            parentColumn = "id",
            entityColumn = "user_id",
            entity = Route.class
    )
    public List<Route> likedRoutes;
}
