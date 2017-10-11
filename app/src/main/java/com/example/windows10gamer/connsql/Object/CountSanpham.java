package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 10/4/2017.
 */

public class CountSanpham {
    String nhanvien;
    String masanpham;
    int soluong;

    public CountSanpham(String nhanvien, String masanpham, int soluong) {
        this.masanpham = masanpham;
        this.soluong = soluong;
        this.nhanvien = nhanvien;
    }

    public String getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(String nhanvien) {
        this.nhanvien = nhanvien;
    }

    public String getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(String masanpham) {
        this.masanpham = masanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
