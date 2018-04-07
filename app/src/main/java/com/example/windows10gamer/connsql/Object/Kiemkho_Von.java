package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EVRESTnhan on 3/29/2018.
 */

public class Kiemkho_Von implements Parcelable {
    private String ngay, gio, maNhanvien, tenNhanvien, ma, ten, baohanh, nguon, ngaynhap, von, totalvon;

    public Kiemkho_Von(String ngay, String gio, String maNhanvien, String tenNhanvien, String ma, String ten, String baohanh, String nguon, String ngaynhap, String von, String totalvon) {
        this.ngay = ngay;
        this.gio = gio;
        this.maNhanvien = maNhanvien;
        this.tenNhanvien = tenNhanvien;
        this.ma = ma;
        this.ten = ten;
        this.baohanh = baohanh;
        this.nguon = nguon;
        this.ngaynhap = ngaynhap;
        this.von = von;
        this.totalvon = totalvon;
    }

    protected Kiemkho_Von(Parcel in) {
        ngay = in.readString();
        gio = in.readString();
        maNhanvien = in.readString();
        tenNhanvien = in.readString();
        ma = in.readString();
        ten = in.readString();
        baohanh = in.readString();
        nguon = in.readString();
        ngaynhap = in.readString();
        von = in.readString();
        totalvon = in.readString();
    }

    public static final Creator<Kiemkho_Von> CREATOR = new Creator<Kiemkho_Von>() {
        @Override
        public Kiemkho_Von createFromParcel(Parcel in) {
            return new Kiemkho_Von(in);
        }

        @Override
        public Kiemkho_Von[] newArray(int size) {
            return new Kiemkho_Von[size];
        }
    };

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
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

    public String getTotalvon() {
        return totalvon;
    }

    public void setTotalvon(String totalvon) {
        this.totalvon = totalvon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(ngay);
        dest.writeString(gio);
        dest.writeString(maNhanvien);
        dest.writeString(tenNhanvien);
        dest.writeString(ma);
        dest.writeString(ten);
        dest.writeString(baohanh);
        dest.writeString(nguon);
        dest.writeString(ngaynhap);
        dest.writeString(von);
        dest.writeString(totalvon);
    }
}