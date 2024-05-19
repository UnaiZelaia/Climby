package com.climby.climby.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalDateTime;

@Entity(tableName = "climb")
public class Climb {
    @PrimaryKey
    int id;
    LocalDateTime date;
    String comment;
    @ColumnInfo(name = "route_id")
    int routeId;
    @ColumnInfo(name = "user_id")
    int userProfileId;

    public Climb() {
    }

    public Climb(int id, LocalDateTime date, String comment, int routeId, int userProfileId) {
        this.id = id;
        this.date = date;
        this.comment = comment;
        this.routeId = routeId;
        this.userProfileId = userProfileId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
    }

    public int getUserProfileId() {
        return userProfileId;
    }

    public void setUserProfileId(int userProfileId) {
        this.userProfileId = userProfileId;
    }
}
