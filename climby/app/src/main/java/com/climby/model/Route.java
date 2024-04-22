package com.climby.model;

import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "route", indices = @Index(value = {"routeId"}, unique = true))
public class Route {
    int routeId;
    String type;
    String name;
    String difficulty;
    String Images;
    String description;
    String location;
    int author;

    public Route(int routeId, String type, String name, String difficulty, String images, String description, String location, int author) {
        this.routeId = routeId;
        this.type = type;
        this.name = name;
        this.difficulty = difficulty;
        Images = images;
        this.description = description;
        this.location = location;
        this.author = author;
    }

    public int getRouteId() {
        return routeId;
    }

    public void setRouteId(int routeId) {
        this.routeId = routeId;
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

    public String getImages() {
        return Images;
    }

    public void setImages(String images) {
        Images = images;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getAuthor() {
        return author;
    }

    public void setAuthor(int author) {
        this.author = author;
    }
}
