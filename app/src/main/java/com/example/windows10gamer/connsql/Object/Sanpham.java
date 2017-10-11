package com.example.windows10gamer.connsql.Object;

import java.io.Serializable;

public class Sanpham implements Serializable{
    private String ma;
    private String ten;
    private String baohanh;
    private String nguon;
    private String giaban;
    private String ngaynhap;

    public Sanpham(String ma, String ten, String baohanh, String nguon, String ngaynhap, String giaban) {
        this.ma = ma;
        this.ten = ten;
        this.baohanh = baohanh;
        this.nguon = nguon;
        this.giaban = giaban;
        this.ngaynhap = ngaynhap;
    }

    public Sanpham() {

    }

    public String getNgaynhap() {
        return ngaynhap;
    }

    public void setNgaynhap(String ngaynhap) {
        this.ngaynhap = ngaynhap;
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

    public String getGiaban() {
        return giaban;
    }

    public void setGiaban(String giaban) {
        this.giaban = giaban;
    }

}
