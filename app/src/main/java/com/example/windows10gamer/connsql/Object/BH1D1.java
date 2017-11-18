package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EVRESTnhan on 11/9/2017.
 */

public class BH1D1 implements Parcelable {
    String maBH, dateToday, timeToday, chinhanhToday, maNVToday, tenNVToday, maOrder, date, time, chinhanh, tenNV, maNV, ten, ma, baohanh, nguon, ngaynhap, von, gia, ghichuOrder, tenKH, sdtKH, ghichuKH, ten_moi, ma_moi, baohanh_moi, nguon_moi, ngaynhap_moi, von_moi, gia_moi, lydo;

    public BH1D1(String maBH, String dateToday, String timeToday, String chinhanhToday, String maNVToday, String tenNVToday, String maOrder, String date, String time, String chinhanh, String tenNV, String maNV, String ma, String ten, String baohanh, String nguon, String ngaynhap, String von, String gia, String ghichuOrder, String tenKH, String sdtKH, String ghichuKH, String ma_moi, String ten_moi,  String baohanh_moi, String nguon_moi, String ngaynhap_moi, String von_moi, String gia_moi, String lydo) {
        this.maBH = maBH;
        this.dateToday = dateToday;
        this.timeToday = timeToday;
        this.chinhanhToday = chinhanhToday;
        this.maNVToday = maNVToday;
        this.tenNVToday = tenNVToday;
        this.maOrder = maOrder;
        this.date = date;
        this.time = time;
        this.chinhanh = chinhanh;
        this.tenNV = tenNV;
        this.maNV = maNV;
        this.ten = ten;
        this.ma = ma;
        this.baohanh = baohanh;
        this.nguon = nguon;
        this.ngaynhap = ngaynhap;
        this.von = von;
        this.gia = gia;
        this.ghichuOrder = ghichuOrder;
        this.tenKH = tenKH;
        this.sdtKH = sdtKH;
        this.ghichuKH = ghichuKH;
        this.ten_moi = ten_moi;
        this.ma_moi = ma_moi;
        this.baohanh_moi = baohanh_moi;
        this.nguon_moi = nguon_moi;
        this.ngaynhap_moi = ngaynhap_moi;
        this.von_moi = von_moi;
        this.gia_moi = gia_moi;
        this.lydo = lydo;
    }

    protected BH1D1(Parcel in) {
        maBH = in.readString();
        dateToday = in.readString();
        timeToday = in.readString();
        chinhanhToday = in.readString();
        maNVToday = in.readString();
        tenNVToday = in.readString();
        maOrder = in.readString();
        date = in.readString();
        time = in.readString();
        chinhanh = in.readString();
        tenNV = in.readString();
        maNV = in.readString();
        ten = in.readString();
        ma = in.readString();
        baohanh = in.readString();
        nguon = in.readString();
        ngaynhap = in.readString();
        von = in.readString();
        gia = in.readString();
        ghichuOrder = in.readString();
        tenKH = in.readString();
        sdtKH = in.readString();
        ghichuKH = in.readString();
        ten_moi = in.readString();
        ma_moi = in.readString();
        baohanh_moi = in.readString();
        nguon_moi = in.readString();
        ngaynhap_moi = in.readString();
        von_moi = in.readString();
        gia_moi = in.readString();
        lydo = in.readString();
    }

    public static final Creator<BH1D1> CREATOR = new Creator<BH1D1>() {
        @Override
        public BH1D1 createFromParcel(Parcel in) {
            return new BH1D1(in);
        }

        @Override
        public BH1D1[] newArray(int size) {
            return new BH1D1[size];
        }
    };

    public String getMaBH() {
        return maBH;
    }

    public void setMaBH(String maBH) {
        this.maBH = maBH;
    }

    public String getDateToday() {
        return dateToday;
    }

    public void setDateToday(String dateToday) {
        this.dateToday = dateToday;
    }

    public String getTimeToday() {
        return timeToday;
    }

    public void setTimeToday(String timeToday) {
        this.timeToday = timeToday;
    }

    public String getChinhanhToday() {
        return chinhanhToday;
    }

    public void setChinhanhToday(String chinhanhToday) {
        this.chinhanhToday = chinhanhToday;
    }

    public String getMaNVToday() {
        return maNVToday;
    }

    public void setMaNVToday(String maNVToday) {
        this.maNVToday = maNVToday;
    }

    public String getTenNVToday() {
        return tenNVToday;
    }

    public void setTenNVToday(String tenNVToday) {
        this.tenNVToday = tenNVToday;
    }

    public String getMaOrder() {
        return maOrder;
    }

    public void setMaOrder(String maOrder) {
        this.maOrder = maOrder;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getChinhanh() {
        return chinhanh;
    }

    public void setChinhanh(String chinhanh) {
        this.chinhanh = chinhanh;
    }

    public String getTenNV() {
        return tenNV;
    }

    public void setTenNV(String tenNV) {
        this.tenNV = tenNV;
    }

    public String getMaNV() {
        return maNV;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getMa() {
        return ma;
    }

    public void setMa(String ma) {
        this.ma = ma;
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

    public String getGhichuOrder() {
        return ghichuOrder;
    }

    public void setGhichuOrder(String ghichuOrder) {
        this.ghichuOrder = ghichuOrder;
    }

    public String getTenKH() {
        return tenKH;
    }

    public void setTenKH(String tenKH) {
        this.tenKH = tenKH;
    }

    public String getSdtKH() {
        return sdtKH;
    }

    public void setSdtKH(String sdtKH) {
        this.sdtKH = sdtKH;
    }

    public String getGhichuKH() {
        return ghichuKH;
    }

    public void setGhichuKH(String ghichuKH) {
        this.ghichuKH = ghichuKH;
    }

    public String getTen_moi() {
        return ten_moi;
    }

    public void setTen_moi(String ten_moi) {
        this.ten_moi = ten_moi;
    }

    public String getMa_moi() {
        return ma_moi;
    }

    public void setMa_moi(String ma_moi) {
        this.ma_moi = ma_moi;
    }

    public String getBaohanh_moi() {
        return baohanh_moi;
    }

    public void setBaohanh_moi(String baohanh_moi) {
        this.baohanh_moi = baohanh_moi;
    }

    public String getNguon_moi() {
        return nguon_moi;
    }

    public void setNguon_moi(String nguon_moi) {
        this.nguon_moi = nguon_moi;
    }

    public String getNgaynhap_moi() {
        return ngaynhap_moi;
    }

    public void setNgaynhap_moi(String ngaynhap_moi) {
        this.ngaynhap_moi = ngaynhap_moi;
    }

    public String getVon_moi() {
        return von_moi;
    }

    public void setVon_moi(String von_moi) {
        this.von_moi = von_moi;
    }

    public String getGia_moi() {
        return gia_moi;
    }

    public void setGia_moi(String gia_moi) {
        this.gia_moi = gia_moi;
    }

    public String getLydo() {
        return lydo;
    }

    public void setLydo(String lydo) {
        this.lydo = lydo;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maBH);
        dest.writeString(dateToday);
        dest.writeString(timeToday);
        dest.writeString(chinhanhToday);
        dest.writeString(maNVToday);
        dest.writeString(tenNVToday);
        dest.writeString(maOrder);
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(chinhanh);
        dest.writeString(tenNV);
        dest.writeString(maNV);
        dest.writeString(ten);
        dest.writeString(ma);
        dest.writeString(baohanh);
        dest.writeString(nguon);
        dest.writeString(ngaynhap);
        dest.writeString(von);
        dest.writeString(gia);
        dest.writeString(ghichuOrder);
        dest.writeString(tenKH);
        dest.writeString(sdtKH);
        dest.writeString(ghichuKH);
        dest.writeString(ten_moi);
        dest.writeString(ma_moi);
        dest.writeString(baohanh_moi);
        dest.writeString(nguon_moi);
        dest.writeString(ngaynhap_moi);
        dest.writeString(von_moi);
        dest.writeString(gia_moi);
        dest.writeString(lydo);
    }
}
