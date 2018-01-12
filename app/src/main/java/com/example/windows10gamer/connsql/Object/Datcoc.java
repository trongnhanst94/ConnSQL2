package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EVRESTnhan on 12/29/2017.
 */

public class Datcoc implements Parcelable{
    String id, maDC, ngay, ca, chinhanh, maNV, tenNV, sotien, tenkhachhang, sodienthoai, ghichu, trangthai, ngaytra, catra, maNVtra, tenNVtra;

    public Datcoc(String id, String maDC, String ngay, String ca, String chinhanh, String maNV, String tenNV, String sotien, String tenkhachhang, String sodienthoai, String ghichu, String trangthai, String ngaytra, String catra, String maNVtra, String tenNVtra) {
        this.id = id;
        this.maDC = maDC;
        this.ngay = ngay;
        this.ca = ca;
        this.chinhanh = chinhanh;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.sotien = sotien;
        this.tenkhachhang = tenkhachhang;
        this.sodienthoai = sodienthoai;
        this.ghichu = ghichu;
        this.trangthai = trangthai;
        this.ngaytra = ngaytra;
        this.catra = catra;
        this.maNVtra = maNVtra;
        this.tenNVtra = tenNVtra;
    }

    protected Datcoc(Parcel in) {
        id = in.readString();
        maDC = in.readString();
        ngay = in.readString();
        ca = in.readString();
        chinhanh = in.readString();
        maNV = in.readString();
        tenNV = in.readString();
        sotien = in.readString();
        tenkhachhang = in.readString();
        sodienthoai = in.readString();
        ghichu = in.readString();
        trangthai = in.readString();
        ngaytra = in.readString();
        catra = in.readString();
        maNVtra = in.readString();
        tenNVtra = in.readString();
    }

    public static final Creator<Datcoc> CREATOR = new Creator<Datcoc>() {
        @Override
        public Datcoc createFromParcel(Parcel in) {
            return new Datcoc(in);
        }

        @Override
        public Datcoc[] newArray(int size) {
            return new Datcoc[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMaDC() {
        return maDC;
    }

    public void setMaDC(String maDC) {
        this.maDC = maDC;
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

    public String getSotien() {
        return sotien;
    }

    public void setSotien(String sotien) {
        this.sotien = sotien;
    }

    public String getTenkhachhang() {
        return tenkhachhang;
    }

    public void setTenkhachhang(String tenkhachhang) {
        this.tenkhachhang = tenkhachhang;
    }

    public String getSodienthoai() {
        return sodienthoai;
    }

    public void setSodienthoai(String sodienthoai) {
        this.sodienthoai = sodienthoai;
    }

    public String getGhichu() {
        return ghichu;
    }

    public void setGhichu(String ghichu) {
        this.ghichu = ghichu;
    }

    public String getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(String trangthai) {
        this.trangthai = trangthai;
    }

    public String getNgaytra() {
        return ngaytra;
    }

    public void setNgaytra(String ngaytra) {
        this.ngaytra = ngaytra;
    }

    public String getCatra() {
        return catra;
    }

    public void setCatra(String catra) {
        this.catra = catra;
    }

    public String getMaNVtra() {
        return maNVtra;
    }

    public void setMaNVtra(String maNVtra) {
        this.maNVtra = maNVtra;
    }

    public String getTenNVtra() {
        return tenNVtra;
    }

    public void setTenNVtra(String tenNVtra) {
        this.tenNVtra = tenNVtra;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(maDC);
        dest.writeString(ngay);
        dest.writeString(ca);
        dest.writeString(chinhanh);
        dest.writeString(maNV);
        dest.writeString(tenNV);
        dest.writeString(sotien);
        dest.writeString(tenkhachhang);
        dest.writeString(sodienthoai);
        dest.writeString(ghichu);
        dest.writeString(trangthai);
        dest.writeString(ngaytra);
        dest.writeString(catra);
        dest.writeString(maNVtra);
        dest.writeString(tenNVtra);
    }
}
