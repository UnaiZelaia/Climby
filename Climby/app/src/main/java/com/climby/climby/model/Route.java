package com.climby.climby.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "route")
public class Route {
    @PrimaryKey
    int id;

    String type;
    String name;
    String difficulty;
    String description;
    String Location;

    int userId;

    public Route() {
    }

    public Route(int id, String type, String name, String difficulty, String description, String location, int userId) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.difficulty = difficulty;
        this.description = description;
        Location = location;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
