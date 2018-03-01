package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EVRESTnhan on 2/24/2018.
 */

public class PhiCOD implements Parcelable {
    private String maCOD, ngay, ca, chinhanh, maNV, tenNV,  noidung, sotien;

    public PhiCOD(String maCOD, String ngay, String ca, String chinhanh, String maNV, String tenNV, String noidung, String sotien) {
        this.maCOD = maCOD;
        this.ngay = ngay;
        this.ca = ca;
        this.chinhanh = chinhanh;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.noidung = noidung;
        this.sotien = sotien;
    }

    protected PhiCOD(Parcel in) {
        maCOD = in.readString();
        ngay = in.readString();
        ca = in.readString();
        chinhanh = in.readString();
        maNV = in.readString();
        tenNV = in.readString();
        noidung = in.readString();
        sotien = in.readString();
    }

    public static final Creator<PhiCOD> CREATOR = new Creator<PhiCOD>() {
        @Override
        public PhiCOD createFromParcel(Parcel in) {
            return new PhiCOD(in);
        }

        @Override
        public PhiCOD[] newArray(int size) {
            return new PhiCOD[size];
        }
    };

    public String getMaCOD() {
        return maCOD;
    }

    public void setMaCOD(String maCOD) {
        this.maCOD = maCOD;
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
        dest.writeString(maCOD);
        dest.writeString(ngay);
        dest.writeString(ca);
        dest.writeString(chinhanh);
        dest.writeString(maNV);
        dest.writeString(tenNV);
        dest.writeString(noidung);
        dest.writeString(sotien);
    }
}
