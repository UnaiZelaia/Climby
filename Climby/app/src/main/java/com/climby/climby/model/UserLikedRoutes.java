package com.climby.climby.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;


@Entity(primaryKeys = {"user_id", "route_id"}, tableName = "user_liked_routes")
public class UserLikedRoutes {
    @ColumnInfo(name = "user_id")
    int userId;
    @ColumnInfo(name = "route_id")
    int routeId;

    public UserLikedRoutes() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }
}
