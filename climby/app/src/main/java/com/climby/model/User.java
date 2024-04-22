package com.climby.model;


import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

@Entity(tableName = "user", indices = @Index(value = {"id"}, unique = true))
public class User {

    @PrimaryKey(autoGenerate = false)
    int id;
    String email;
    String username;
    String bio;
    String password;

    public User(int id, String email, String username, String bio, String password) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.bio = bio;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
