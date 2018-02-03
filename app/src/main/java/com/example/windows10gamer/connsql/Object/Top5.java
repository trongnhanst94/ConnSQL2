package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 1/19/2018.
 */

public class Top5 {
    String stt, madonhang, ten, nhanvien, gia;

    public Top5(String stt, String madonhang, String ten, String nhanvien, String gia) {
        this.stt = stt;
        this.madonhang = madonhang;
        this.ten = ten;
        this.nhanvien = nhanvien;
        this.gia = gia;
    }

    public String getMadonhang() {
        return madonhang;
    }

    public void setMadonhang(String madonhang) {
        this.madonhang = madonhang;
    }

    public String getStt() {
        return stt;
    }

    public void setStt(String stt) {
        this.stt = stt;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(String nhanvien) {
        this.nhanvien = nhanvien;
    }

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }
}
