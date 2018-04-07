package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 4/2/2018.
 */

public class Kho_Dialog {
    private String chinhanh;
    private int soluong;

    public Kho_Dialog(String chinhanh, int soluong) {
        this.chinhanh = chinhanh;
        this.soluong = soluong;
    }

    public String getChinhanh() {
        return chinhanh;
    }

    public void setChinhanh(String chinhanh) {
        this.chinhanh = chinhanh;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
