package com.example.windows10gamer.connsql.Object;

import java.io.Serializable;

/**
 * Created by EVRESTnhan on 9/22/2017.
 */

public class User implements Serializable{
    int id;
    String ma;
    String ten;
    String shortName;
    String username;
    String password;

    public User(int id, String ma, String ten, String shortName, String username, String password) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.shortName = shortName;
        this.username = username;
        this.password = password;
    }

    public User(String ma, String shortName) {
        this.ma = ma;
        this.shortName = shortName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
