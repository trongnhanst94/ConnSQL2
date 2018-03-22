package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EVRESTnhan on 10/4/2017.
 */

public class CountSanpham implements Parcelable {
    String nhanvien;
    String ma, ten, baohanh, nguon, ngaynhap, von, gia;
    int soluong;

    public CountSanpham(String nhanvien, String ma, String ten, String baohanh, String nguon, String ngaynhap, String von, String gia, int soluong) {
        this.nhanvien = nhanvien;
        this.ma = ma;
        this.ten = ten;
        this.baohanh = baohanh;
        this.nguon = nguon;
        this.ngaynhap = ngaynhap;
        this.von = von;
        this.gia = gia;
        this.soluong = soluong;
    }

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

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    public static Creator<CountSanpham> getCREATOR() {
        return CREATOR;
    }

    protected CountSanpham(Parcel in) {
        nhanvien = in.readString();
        ma = in.readString();
        ten = in.readString();
        baohanh = in.readString();
        nguon = in.readString();
        ngaynhap = in.readString();
        von = in.readString();
        gia = in.readString();
        soluong = in.readInt();
    }

    public static final Creator<CountSanpham> CREATOR = new Creator<CountSanpham>() {
        @Override
        public CountSanpham createFromParcel(Parcel in) {
            return new CountSanpham(in);
        }

        @Override
        public CountSanpham[] newArray(int size) {
            return new CountSanpham[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
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
        dest.writeString(gia);
        dest.writeInt(soluong);
    }
}
