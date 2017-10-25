package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 10/17/2017.
 */

public class CountSanpham2 {
    String ma;
    String tenSanpham;
    String tenNhanvien;
    int soluong;

    public CountSanpham2(String ma, String tenSanpham, String tenNhanvien, int soluong) {
        this.ma = ma;
        this.tenSanpham = tenSanpham;
        this.tenNhanvien = tenNhanvien;
        this.soluong = soluong;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public String getTenSanpham() {
        return tenSanpham;
    }

    public void setTenSanpham(String tenSanpham) {
        this.tenSanpham = tenSanpham;
    }

    public String getTenNhanvien() {
        return tenNhanvien;
    }

    public void setTenNhanvien(String tenNhanvien) {
        this.tenNhanvien = tenNhanvien;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
