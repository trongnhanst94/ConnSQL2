package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 3/20/2018.
 */

public class Delete_Baohanh {
    private String id, maBH, dateToday, maNVToday, tenNVToday, gio, ten, gia;

    public Delete_Baohanh(String id, String maBH, String dateToday, String maNVToday, String tenNVToday, String gio, String ten, String gia) {
        this.id = id;
        this.maBH = maBH;
        this.dateToday = dateToday;
        this.maNVToday = maNVToday;
        this.tenNVToday = tenNVToday;
        this.gio = gio;
        this.ten = ten;
        this.gia = gia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaBH() {
        return maBH;
    }

    public void setMaBH(String maBH) {
        this.maBH = maBH;
    }

    public String getDateToday() {
        return dateToday;
    }

    public void setDateToday(String dateToday) {
        this.dateToday = dateToday;
    }

    public String getMaNVToday() {
        return maNVToday;
    }

    public void setMaNVToday(String maNVToday) {
        this.maNVToday = maNVToday;
    }

    public String getTenNVToday() {
        return tenNVToday;
    }

    public void setTenNVToday(String tenNVToday) {
        this.tenNVToday = tenNVToday;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
