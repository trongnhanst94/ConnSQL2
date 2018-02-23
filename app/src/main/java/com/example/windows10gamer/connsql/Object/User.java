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
    String level;
    String chucdanh;
    String trangthai;
    String created;
    String updated;
    String img;

    public User(int id, String ma, String ten, String shortName, String username, String password, String level, String chucdanh, String trangthai, String created, String updated, String img) {
        this.id = id;
        this.ma = ma;
        this.ten = ten;
        this.shortName = shortName;
        this.username = username;
        this.password = password;
        this.level = level;
        this.chucdanh = chucdanh;
        this.trangthai = trangthai;
        this.created = created;
        this.updated = updated;
        this.img = img;
    }

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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getChucdanh() {
        return chucdanh;
    }

    public void setChucdanh(String chucdanh) {
        this.chucdanh = chucdanh;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
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
