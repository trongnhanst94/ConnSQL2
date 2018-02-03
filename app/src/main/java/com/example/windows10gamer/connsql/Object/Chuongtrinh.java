package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 2/2/2018.
 */

public class Chuongtrinh {
    private String id, maCT, tenChuongtrinh, giaChuongtrinh, ngaybatdau, ngayketthuc, chinhanh, ma, ten, baohanh, nguon, ngaynhap, von, gia;

    public Chuongtrinh(String id, String maCT, String tenChuongtrinh, String giaChuongtrinh, String ngaybatdau, String ngayketthuc, String chinhanh, String ma, String ten, String baohanh, String nguon, String ngaynhap, String von, String gia) {
        this.id = id;
        this.maCT = maCT;
        this.tenChuongtrinh = tenChuongtrinh;
        this.giaChuongtrinh = giaChuongtrinh;
        this.ngaybatdau = ngaybatdau;
        this.ngayketthuc = ngayketthuc;
        this.chinhanh = chinhanh;
        this.ma = ma;
        this.ten = ten;
        this.baohanh = baohanh;
        this.nguon = nguon;
        this.ngaynhap = ngaynhap;
        this.von = von;
        this.gia = gia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaCT() {
        return maCT;
    }

    public void setMaCT(String maCT) {
        this.maCT = maCT;
    }

    public String getTenChuongtrinh() {
        return tenChuongtrinh;
    }

    public void setTenChuongtrinh(String tenChuongtrinh) {
        this.tenChuongtrinh = tenChuongtrinh;
    }

    public String getGiaChuongtrinh() {
        return giaChuongtrinh;
    }

    public void setGiaChuongtrinh(String giaChuongtrinh) {
        this.giaChuongtrinh = giaChuongtrinh;
    }

    public String getNgaybatdau() {
        return ngaybatdau;
    }

    public void setNgaybatdau(String ngaybatdau) {
        this.ngaybatdau = ngaybatdau;
    }

    public String getNgayketthuc() {
        return ngayketthuc;
    }

    public void setNgayketthuc(String ngayketthuc) {
        this.ngayketthuc = ngayketthuc;
    }

    public String getChinhanh() {
        return chinhanh;
    }

    public void setChinhanh(String chinhanh) {
        this.chinhanh = chinhanh;
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

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
