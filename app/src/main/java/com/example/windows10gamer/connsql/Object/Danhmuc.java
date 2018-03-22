package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 3/12/2018.
 */

public class Danhmuc {
    private String id, ten, loai, manhanvien, tennhanvien;

    public Danhmuc(String id, String ten, String loai, String manhanvien, String tennhanvien) {
        this.id = id;
        this.ten = ten;
        this.loai = loai;
        this.manhanvien = manhanvien;
        this.tennhanvien = tennhanvien;
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

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public String getManhanvien() {
        return manhanvien;
    }

    public void setManhanvien(String manhanvien) {
        this.manhanvien = manhanvien;
    }

    public String getTennhanvien() {
        return tennhanvien;
    }

    public void setTennhanvien(String tennhanvien) {
        this.tennhanvien = tennhanvien;
    }
}
