package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

public class Sanpham implements Parcelable {
    private String ma;
    private String ten;
    private String baohanh;
    private String nguon;
    private String ngaynhap;
    private String von;
    private String giaban;

    public Sanpham(String ma, String ten, String baohanh, String nguon, String ngaynhap, String von, String giaban) {
        this.ma = ma;
        this.ten = ten;
        this.baohanh = baohanh;
        this.nguon = nguon;
        this.ngaynhap = ngaynhap;
        this.von = von;
        this.giaban = giaban;
    }

    public Sanpham() {

    }

    protected Sanpham(Parcel in) {
        ma = in.readString();
        ten = in.readString();
        baohanh = in.readString();
        nguon = in.readString();
        ngaynhap = in.readString();
        von = in.readString();
        giaban = in.readString();
    }

    public static final Creator<Sanpham> CREATOR = new Creator<Sanpham>() {
        @Override
        public Sanpham createFromParcel(Parcel in) {
            return new Sanpham(in);
        }

        @Override
        public Sanpham[] newArray(int size) {
            return new Sanpham[size];
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ma);
        dest.writeString(ten);
        dest.writeString(baohanh);
        dest.writeString(nguon);
        dest.writeString(ngaynhap);
        dest.writeString(von);
        dest.writeString(giaban);
    }
}
