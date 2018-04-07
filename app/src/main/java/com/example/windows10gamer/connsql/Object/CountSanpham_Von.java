package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EVRESTnhan on 3/29/2018.
 */

public class CountSanpham_Von implements Parcelable {
    String nhanvien;
    String ma, ten, baohanh, nguon, ngaynhap, von;
    int totalvon;
    int soluong;

    public CountSanpham_Von(String nhanvien, String ma, String ten, String baohanh, String nguon, String ngaynhap, String von, int totalvon, int soluong) {
        this.nhanvien = nhanvien;
        this.ma = ma;
        this.ten = ten;
        this.baohanh = baohanh;
        this.nguon = nguon;
        this.ngaynhap = ngaynhap;
        this.von = von;
        this.totalvon = totalvon;
        this.soluong = soluong;
    }

    protected CountSanpham_Von(Parcel in) {
        nhanvien = in.readString();
        ma = in.readString();
        ten = in.readString();
        baohanh = in.readString();
        nguon = in.readString();
        ngaynhap = in.readString();
        von = in.readString();
        totalvon = in.readInt();
        soluong = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nhanvien);
        dest.writeString(ma);
        dest.writeString(ten);
        dest.writeString(baohanh);
        dest.writeString(nguon);
        dest.writeString(ngaynhap);
        dest.writeString(von);
        dest.writeInt(totalvon);
        dest.writeInt(soluong);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CountSanpham_Von> CREATOR = new Creator<CountSanpham_Von>() {
        @Override
        public CountSanpham_Von createFromParcel(Parcel in) {
            return new CountSanpham_Von(in);
        }

        @Override
        public CountSanpham_Von[] newArray(int size) {
            return new CountSanpham_Von[size];
        }
    };

    public String getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(String nhanvien) {
        this.nhanvien = nhanvien;
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

    public int getTotalvon() {
        return totalvon;
    }

    public void setTotalvon(int totalvon) {
        this.totalvon = totalvon;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }
}
