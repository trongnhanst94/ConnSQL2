package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EVRESTnhan on 11/9/2017.
 */

public class BHHT implements Parcelable {

    String maBH, dateToday, timeToday, chinhanhToday, maNVToday, tenNVToday, maOrder, date, time, chinhanh, tenNV, maNV, ten, ma, baohanh, nguon, ngaynhap, von, gia, ghichuOrder, tenKH, sdtKH, ghichuKH, gtConlai, lydo;

    public BHHT(String maBH, String dateToday, String timeToday, String chinhanhToday, String maNVToday, String tenNVToday, String maOrder, String date, String time, String chinhanh, String tenNV, String maNV, String ma,String ten,  String baohanh, String nguon, String ngaynhap, String von, String gia, String ghichuOrder, String tenKH, String sdtKH, String ghichuKH, String gtConlai, String lydo) {
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
        this.gtConlai = gtConlai;
        this.lydo = lydo;
    }

    protected BHHT(Parcel in) {
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
        gtConlai = in.readString();
        lydo = in.readString();
    }

    public static final Creator<BHHT> CREATOR = new Creator<BHHT>() {
        @Override
        public BHHT createFromParcel(Parcel in) {
            return new BHHT(in);
        }

        @Override
        public BHHT[] newArray(int size) {
            return new BHHT[size];
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

    public String getGtConlai() {
        return gtConlai;
    }

    public void setGtConlai(String gtConlai) {
        this.gtConlai = gtConlai;
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
        dest.writeString(gtConlai);
        dest.writeString(lydo);
    }
}
