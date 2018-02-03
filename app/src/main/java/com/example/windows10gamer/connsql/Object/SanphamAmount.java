package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by EVRESTnhan on 1/11/2018.
 */

public class SanphamAmount implements Parcelable {

    String gio;
    String ma;
    String ten;
    String baohanh;
    String nguon;
    String ngaynhap;
    String von;
    String giaban;
    String soluong;

    public SanphamAmount(String gio, String ma, String ten, String baohanh, String nguon, String ngaynhap, String von, String giaban, String soluong) {
        this.gio = gio;
        this.ma = ma;
        this.ten = ten;
        this.baohanh = baohanh;
        this.nguon = nguon;
        this.ngaynhap = ngaynhap;
        this.von = von;
        this.giaban = giaban;
        this.soluong = soluong;
    }

    protected SanphamAmount(Parcel in) {
        gio = in.readString();
        ma = in.readString();
        ten = in.readString();
        baohanh = in.readString();
        nguon = in.readString();
        ngaynhap = in.readString();
        von = in.readString();
        giaban = in.readString();
        soluong = in.readString();
    }

    public static final Creator<SanphamAmount> CREATOR = new Creator<SanphamAmount>() {
        @Override
        public SanphamAmount createFromParcel(Parcel in) {
            return new SanphamAmount(in);
        }

        @Override
        public SanphamAmount[] newArray(int size) {
            return new SanphamAmount[size];
        }
    };

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
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

    public String getGiaban() {
        return giaban;
    }

    public void setGiaban(String giaban) {
        this.giaban = giaban;
    }

    public String getSoluong() {
        return soluong;
    }

    public void setSoluong(String soluong) {
        this.soluong = soluong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(gio);
        dest.writeString(ma);
        dest.writeString(ten);
        dest.writeString(baohanh);
        dest.writeString(nguon);
        dest.writeString(ngaynhap);
        dest.writeString(von);
        dest.writeString(giaban);
        dest.writeString(soluong);
    }
}