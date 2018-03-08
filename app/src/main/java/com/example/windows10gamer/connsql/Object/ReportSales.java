package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 10/18/2017.
 */

public class ReportSales {
    String maNhanvien;
    String tenNhanvien;
    float doanhthu;
    float soKhachhang;
    float soSanpham;
    float dttkh;
    float dttsp;
    float sptkh;

    public ReportSales(String maNhanvien, String tenNhanvien, float doanhthu, float soKhachhang, float soSanpham, float sptkh, float dttkh, float dttsp) {
        this.maNhanvien = maNhanvien;
        this.tenNhanvien = tenNhanvien;
        this.doanhthu = doanhthu;
        this.soKhachhang = soKhachhang;
        this.soSanpham = soSanpham;
        this.dttkh = dttkh;
        this.dttsp = dttsp;
        this.sptkh = sptkh;
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

    public float getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(float doanhthu) {
        this.doanhthu = doanhthu;
    }

    public float getSoKhachhang() {
        return soKhachhang;
    }

    public void setSoKhachhang(float soKhachhang) {
        this.soKhachhang = soKhachhang;
    }

    public float getSoSanpham() {
        return soSanpham;
    }

    public void setSoSanpham(float soSanpham) {
        this.soSanpham = soSanpham;
    }

    public float getDttkh() {
        return dttkh;
    }

    public void setDttkh(float dttkh) {
        this.dttkh = dttkh;
    }

    public float getDttsp() {
        return dttsp;
    }

    public void setDttsp(float dttsp) {
        this.dttsp = dttsp;
    }

    public float getSptkh() {
        return sptkh;
    }

    public void setSptkh(float sptkh) {
        this.sptkh = sptkh;
    }
}
