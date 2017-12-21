package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EVRESTnhan on 12/5/2017.
 */

public class Sanpham_ID implements Parcelable {
    private String id;
    private String gio;
    private String ma;
    private String ten;
    private String baohanh;
    private String nguon;
    private String ngaynhap;
    private String von;
    private String giaban;

    public Sanpham_ID(String id, String gio, String ma, String ten, String baohanh, String nguon, String ngaynhap, String von, String giaban) {
        this.id = id;
        this.gio = gio;
        this.ma = ma;
        this.ten = ten;
        this.baohanh = baohanh;
        this.nguon = nguon;
        this.ngaynhap = ngaynhap;
        this.von = von;
        this.giaban = giaban;
    }

    public Sanpham_ID() {

    }

    protected Sanpham_ID(Parcel in) {
        id = in.readString();
        gio = in.readString();
        ma = in.readString();
        ten = in.readString();
        baohanh = in.readString();
        nguon = in.readString();
        ngaynhap = in.readString();
        von = in.readString();
        giaban = in.readString();
    }

    public static final Creator<Sanpham_ID> CREATOR = new Creator<Sanpham_ID>() {
        @Override
        public Sanpham_ID createFromParcel(Parcel in) {
            return new Sanpham_ID(in);
        }

        @Override
        public Sanpham_ID[] newArray(int size) {
            return new Sanpham_ID[size];
        }
    };

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

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(gio);
        dest.writeString(ma);
        dest.writeString(ten);
        dest.writeString(baohanh);
        dest.writeString(nguon);
        dest.writeString(ngaynhap);
        dest.writeString(von);
        dest.writeString(giaban);
    }
}
