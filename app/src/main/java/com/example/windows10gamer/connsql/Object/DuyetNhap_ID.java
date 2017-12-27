package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 12/21/2017.
 */

public class DuyetNhap_ID {
    String ma;
    int trangthai;

    public DuyetNhap_ID(String ma, int trangthai) {
        this.ma = ma;
        this.trangthai = trangthai;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
