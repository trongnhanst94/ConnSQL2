package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 10/17/2017.
 */

public class Customer {
    String maCus;
    String tenCus;
    String sdtCus;
    String ghichuCus;
    String OrderCus;
    String maNhanvien;
    String tenNhanvien;

    public Customer(String maCus, String tenCus, String sdtCus, String ghichuCus, String orderCus, String maNhanvien, String tenNhanvien) {
        this.maCus = maCus;
        this.tenCus = tenCus;
        this.sdtCus = sdtCus;
        this.ghichuCus = ghichuCus;
        OrderCus = orderCus;
        this.maNhanvien = maNhanvien;
        this.tenNhanvien = tenNhanvien;
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

    public String getMaCus() {
        return maCus;
    }

    public void setMaCus(String maCus) {
        this.maCus = maCus;
    }

    public String getTenCus() {
        return tenCus;
    }

    public void setTenCus(String tenCus) {
        this.tenCus = tenCus;
    }

    public String getSdtCus() {
        return sdtCus;
    }

    public void setSdtCus(String sdtCus) {
        this.sdtCus = sdtCus;
    }

    public String getGhichuCus() {
        return ghichuCus;
    }

    public void setGhichuCus(String ghichuCus) {
        this.ghichuCus = ghichuCus;
    }

    public String getOrderCus() {
        return OrderCus;
    }

    public void setOrderCus(String orderCus) {
        OrderCus = orderCus;
    }
}
