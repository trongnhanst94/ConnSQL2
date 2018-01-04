package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EVRESTnhan on 1/3/2018.
 */

public class Doanhthu implements Parcelable {
    String id, maDT, ngay, ca, chinhanh, maNV, tenNV, tiendauca, tientrave, doanhthu;

    public Doanhthu(String id, String maDT, String ngay, String ca, String chinhanh, String maNV, String tenNV, String tiendauca, String tientrave, String doanhthu) {
        this.id = id;
        this.maDT = maDT;
        this.ngay = ngay;
        this.ca = ca;
        this.chinhanh = chinhanh;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.tiendauca = tiendauca;
        this.tientrave = tientrave;
        this.doanhthu = doanhthu;
    }

    protected Doanhthu(Parcel in) {
        id = in.readString();
        maDT = in.readString();
        ngay = in.readString();
        ca = in.readString();
        chinhanh = in.readString();
        maNV = in.readString();
        tenNV = in.readString();
        tiendauca = in.readString();
        tientrave = in.readString();
        doanhthu = in.readString();
    }

    public static final Creator<Doanhthu> CREATOR = new Creator<Doanhthu>() {
        @Override
        public Doanhthu createFromParcel(Parcel in) {
            return new Doanhthu(in);
        }

        @Override
        public Doanhthu[] newArray(int size) {
            return new Doanhthu[size];
        }
    };

    public String getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(String doanhthu) {
        this.doanhthu = doanhthu;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaDT() {
        return maDT;
    }

    public void setMaDT(String maDT) {
        this.maDT = maDT;
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

    public String getTiendauca() {
        return tiendauca;
    }

    public void setTiendauca(String tiendauca) {
        this.tiendauca = tiendauca;
    }

    public String getTientrave() {
        return tientrave;
    }

    public void setTientrave(String tientrave) {
        this.tientrave = tientrave;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(maDT);
        dest.writeString(ngay);
        dest.writeString(ca);
        dest.writeString(chinhanh);
        dest.writeString(maNV);
        dest.writeString(tenNV);
        dest.writeString(tiendauca);
        dest.writeString(tientrave);
        dest.writeString(doanhthu);
    }
}
