package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 11/15/2017.
 */

public class Lechkho {
    String STT, masp, tensp, nvA, nvB;

    public Lechkho(String STT, String masp, String tensp, String nvA, String nvB) {
        this.STT = STT;
        this.masp = masp;
        this.tensp = tensp;
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
