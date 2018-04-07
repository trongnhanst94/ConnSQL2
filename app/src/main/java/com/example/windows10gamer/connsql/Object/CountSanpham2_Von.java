package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 3/29/2018.
 */

public class CountSanpham2_Von {

    String ma;
    String tenSanpham, baohanh, nguon, ngaynhap, von, totalvon;
    String tenNhanvien;
    int soluong;

    public CountSanpham2_Von(String ma, String tenSanpham, String baohanh, String nguon, String ngaynhap, String von, String totalvon, String tenNhanvien, int soluong) {
        this.ma = ma;
        this.tenSanpham = tenSanpham;
        this.baohanh = baohanh;
        this.nguon = nguon;
        this.ngaynhap = ngaynhap;
        this.von = von;
        this.totalvon = totalvon;
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

    public String getBaohanh() {
        return baohanh;
    }

    public void setBaohanh(String baohanh) {
        this.baohanh = baohanh;
    }

    public String getNguon() {
        return nguon;
    }

    public void setNguon(String nguon) {
        this.nguon = nguon;
    }

    public String getNgaynhap() {
        return ngaynhap;
    }

    public void setNgaynhap(String ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public String getVon() {
        return von;
    }

    public void setVon(String von) {
        this.von = von;
    }

    public String getTotalvon() {
        return totalvon;
    }

    public void setTotalvon(String totalvon) {
        this.totalvon = totalvon;
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
