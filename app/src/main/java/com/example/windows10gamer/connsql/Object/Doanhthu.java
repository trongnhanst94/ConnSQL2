package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EVRESTnhan on 1/3/2018.
 */

public class Doanhthu implements Parcelable {
    String maDT, ngay, ca, chinhanh, maNV, tenNV, tiendauca, tientrave, doanhthu, lechcuoica, tienthucte;

    public Doanhthu(String maDT, String ngay, String ca, String chinhanh, String maNV, String tenNV, String tiendauca, String tientrave, String doanhthu, String lechcuoica, String tienthucte) {
        this.maDT = maDT;
        this.ngay = ngay;
        this.ca = ca;
        this.chinhanh = chinhanh;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.tiendauca = tiendauca;
        this.tientrave = tientrave;
        this.doanhthu = doanhthu;
        this.lechcuoica = lechcuoica;
        this.tienthucte = tienthucte;
    }

    protected Doanhthu(Parcel in) {
        maDT = in.readString();
        ngay = in.readString();
        ca = in.readString();
        chinhanh = in.readString();
        maNV = in.readString();
        tenNV = in.readString();
        tiendauca = in.readString();
        tientrave = in.readString();
        doanhthu = in.readString();
        lechcuoica = in.readString();
        tienthucte = in.readString();
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

    public String getDoanhthu() {
        return doanhthu;
    }

    public void setDoanhthu(String doanhthu) {
        this.doanhthu = doanhthu;
    }

    public String getLechcuoica() {
        return lechcuoica;
    }

    public void setLechcuoica(String lechcuoica) {
        this.lechcuoica = lechcuoica;
    }

    public String getTienthucte() {
        return tienthucte;
    }

    public void setTienthucte(String tienthucte) {
        this.tienthucte = tienthucte;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maDT);
        dest.writeString(ngay);
        dest.writeString(ca);
        dest.writeString(chinhanh);
        dest.writeString(maNV);
        dest.writeString(tenNV);
        dest.writeString(tiendauca);
        dest.writeString(tientrave);
        dest.writeString(doanhthu);
        dest.writeString(lechcuoica);
        dest.writeString(tienthucte);
    }
}
