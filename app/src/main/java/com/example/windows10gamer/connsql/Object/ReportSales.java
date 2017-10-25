package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 10/18/2017.
 */

public class ReportSales {
    String maNhanvien;
    String tenNhanvien;
    int doanhthu;
    int soKhachhang;
    int soSanpham;
    int dttkh;
    int dttsp;

    public ReportSales(String maNhanvien, String tenNhanvien, int doanhthu, int soKhachhang, int soSanpham, int dttkh, int dttsp) {
        this.maNhanvien = maNhanvien;
        this.tenNhanvien = tenNhanvien;
        this.doanhthu = doanhthu;
        this.soKhachhang = soKhachhang;
        this.soSanpham = soSanpham;
        this.dttkh = dttkh;
        this.dttsp = dttsp;
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

    public int getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(int doanhthu) {
        this.doanhthu = doanhthu;
    }

    public int getSoKhachhang() {
        return soKhachhang;
    }

    public void setSoKhachhang(int soKhachhang) {
        this.soKhachhang = soKhachhang;
    }

    public int getSoSanpham() {
        return soSanpham;
    }

    public void setSoSanpham(int soSanpham) {
        this.soSanpham = soSanpham;
    }

    public int getDttkh() {
        return dttkh;
    }

    public void setDttkh(int dttkh) {
        this.dttkh = dttkh;
    }

    public int getDttsp() {
        return dttsp;
    }

    public void setDttsp(int dttsp) {
        this.dttsp = dttsp;
    }
}
