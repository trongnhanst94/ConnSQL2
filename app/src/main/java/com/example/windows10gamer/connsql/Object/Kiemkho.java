package com.example.windows10gamer.connsql.Object;

import java.io.Serializable;

/**
 * Created by EVRESTnhan on 10/3/2017.
 */

public class Kiemkho implements Serializable {
    private String ngay,gio,maNhanvien,tenNhanvien,ma,ten,baohanh,nguon,ngaynhap,gia;

    public Kiemkho(String ngay, String gio, String maNhanvien, String tenNhanvien, String ma, String ten, String baohanh, String nguon, String ngaynhap, String gia) {
        this.ngay = ngay;
        this.gio = gio;
        this.maNhanvien = maNhanvien;
        this.tenNhanvien = tenNhanvien;
        this.ma = ma;
        this.ten = ten;
        this.baohanh = baohanh;
        this.nguon = nguon;
        this.gia = gia;
        this.ngaynhap = ngaynhap;
    }

    public String getNgaynhap() {
        return ngaynhap;
    }

    public void setNgaynhap(String ngaynhap) {
        this.ngaynhap = ngaynhap;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public String getMaNhanvien() {
        return maNhanvien;
    }

    public void setMaNhanvien(String maNhanvien) {
        this.maNhanvien = maNhanvien;
    }

    public String getTenNhanvien() {
        return tenNhanvien;
    }

    public void setTenNhanvien(String tenNhanvien) {
        this.tenNhanvien = tenNhanvien;
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

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
