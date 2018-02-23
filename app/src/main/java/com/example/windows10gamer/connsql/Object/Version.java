package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 2/6/2018.
 */

public class Version {
    String id, ten, mota, created;

    public Version(String id, String ten, String mota, String created) {
        this.id = id;
        this.ten = ten;
        this.mota = mota;
        this.created = created;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMota() {
        return mota;
    }

    public void setMota(String mota) {
        this.mota = mota;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
