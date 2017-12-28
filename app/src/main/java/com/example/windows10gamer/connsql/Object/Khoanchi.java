package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EVRESTnhan on 12/28/2017.
 */

public class Khoanchi implements Parcelable {
    String maKC, ngay, ca, chinhanh, maNV, tenNV,  noidung, sotien;

    public Khoanchi(String maKC, String ngay, String ca, String chinhanh, String maNV, String tenNV, String noidung, String sotien) {
        this.maKC = maKC;
        this.ngay = ngay;
        this.ca = ca;
        this.chinhanh = chinhanh;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.noidung = noidung;
        this.sotien = sotien;
    }

    protected Khoanchi(Parcel in) {
        maKC = in.readString();
        ngay = in.readString();
        ca = in.readString();
        chinhanh = in.readString();
        maNV = in.readString();
        tenNV = in.readString();
        noidung = in.readString();
        sotien = in.readString();
    }

    public static final Creator<Khoanchi> CREATOR = new Creator<Khoanchi>() {
        @Override
        public Khoanchi createFromParcel(Parcel in) {
            return new Khoanchi(in);
        }

        @Override
        public Khoanchi[] newArray(int size) {
            return new Khoanchi[size];
        }
    };

    public String getMaKC() {
        return maKC;
    }

    public void setMaKC(String maKC) {
        this.maKC = maKC;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getCa() {
        return ca;
    }

    public void setCa(String ca) {
        this.ca = ca;
    }

    public String getChinhanh() {
        return chinhanh;
    }

    public void setChinhanh(String chinhanh) {
        this.chinhanh = chinhanh;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getNoidung() {
        return noidung;
    }

    public void setNoidung(String noidung) {
        this.noidung = noidung;
    }

    public String getSotien() {
        return sotien;
    }

    public void setSotien(String sotien) {
        this.sotien = sotien;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maKC);
        dest.writeString(ngay);
        dest.writeString(ca);
        dest.writeString(chinhanh);
        dest.writeString(maNV);
        dest.writeString(tenNV);
        dest.writeString(noidung);
        dest.writeString(sotien);
    }
}

