package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 2/23/2018.
 */

public class TienTraVe {
    private String maTV, ngay, ca, chinhanh, maNV, tenNV,  noidung, sotien;

    public TienTraVe(String maTV, String ngay, String ca, String chinhanh, String maNV, String tenNV, String noidung, String sotien) {
        this.maTV = maTV;
        this.ngay = ngay;
        this.ca = ca;
        this.chinhanh = chinhanh;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.noidung = noidung;
        this.sotien = sotien;
    }

    public String getMaTV() {
        return maTV;
    }

    public void setMaTV(String maTV) {
        this.maTV = maTV;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getChinhanh() {
        return chinhanh;
    }

    public void setChinhanh(String chinhanh) {
        this.chinhanh = chinhanh;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getSotien() {
        return sotien;
    }

    public void setSotien(String sotien) {
        this.sotien = sotien;
    }
}
