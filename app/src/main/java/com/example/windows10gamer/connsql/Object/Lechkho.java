package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 11/15/2017.
 */

public class Lechkho {
    String STT, masp, tensp, baohanhsp, nguonsp, ngaynhapsp, vonsp, giasp, nvA, nvB;

    public Lechkho(String STT, String masp, String tensp, String baohanhsp, String nguonsp, String ngaynhapsp, String vonsp, String giasp, String nvA, String nvB) {
        this.STT = STT;
        this.masp = masp;
        this.tensp = tensp;
        this.baohanhsp = baohanhsp;
        this.nguonsp = nguonsp;
        this.ngaynhapsp = ngaynhapsp;
        this.vonsp = vonsp;
        this.giasp = giasp;
        this.nvA = nvA;
        this.nvB = nvB;
    }

    public String getSTT() {
        return STT;
    }

    public void setSTT(String STT) {
        this.STT = STT;
    }

    public String getMasp() {
        return masp;
    }

    public void setMasp(String masp) {
        this.masp = masp;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public String getBaohanhsp() {
        return baohanhsp;
    }

    public void setBaohanhsp(String baohanhsp) {
        this.baohanhsp = baohanhsp;
    }

    public String getNguonsp() {
        return nguonsp;
    }

    public void setNguonsp(String nguonsp) {
        this.nguonsp = nguonsp;
    }

    public String getNgaynhapsp() {
        return ngaynhapsp;
    }

    public void setNgaynhapsp(String ngaynhapsp) {
        this.ngaynhapsp = ngaynhapsp;
    }

    public String getVonsp() {
        return vonsp;
    }

    public void setVonsp(String vonsp) {
        this.vonsp = vonsp;
    }

    public String getGiasp() {
        return giasp;
    }

    public void setGiasp(String giasp) {
        this.giasp = giasp;
    }

    public String getNvA() {
        return nvA;
    }

    public void setNvA(String nvA) {
        this.nvA = nvA;
    }

    public String getNvB() {
        return nvB;
    }

    public void setNvB(String nvB) {
        this.nvB = nvB;
    }
}
