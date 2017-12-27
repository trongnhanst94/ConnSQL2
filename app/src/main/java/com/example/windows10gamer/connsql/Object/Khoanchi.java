package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 12/27/2017.
 */

public class Khoanchi {
    String maKC, ngay, ca, chinhanh, maNV, tenNV, noidung, sotien;

    public Khoanchi(String maKC, String ngay, String ca, String chinhanh, String maNV, String tenNV, String noidung, String sotien) {
        this.maKC = maKC;
        this.ngay = ngay;
        this.ca = ca;
        this.chinhanh = chinhanh;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.noidung = noidung;
        this.sotien = sotien;
    }

    public java.lang.String getMaKC() {
        return maKC;
    }

    public void setMaKC(java.lang.String maKC) {
        this.maKC = maKC;
    }

    public java.lang.String getNgay() {
        return ngay;
    }

    public void setNgay(java.lang.String ngay) {
        this.ngay = ngay;
    }

    public java.lang.String getCa() {
        return ca;
    }

    public void setCa(java.lang.String ca) {
        this.ca = ca;
    }

    public java.lang.String getChinhanh() {
        return chinhanh;
    }

    public void setChinhanh(java.lang.String chinhanh) {
        this.chinhanh = chinhanh;
    }

    public java.lang.String getMaNV() {
        return maNV;
    }

    public void setMaNV(java.lang.String maNV) {
        this.maNV = maNV;
    }

    public java.lang.String getTenNV() {
        return tenNV;
    }

    public void setTenNV(java.lang.String tenNV) {
        this.tenNV = tenNV;
    }

    public java.lang.String getNoidung() {
        return noidung;
    }

    public void setNoidung(java.lang.String noidung) {
        this.noidung = noidung;
    }

    public java.lang.String getSotien() {
        return sotien;
    }

    public void setSotien(java.lang.String sotien) {
        this.sotien = sotien;
    }
}
