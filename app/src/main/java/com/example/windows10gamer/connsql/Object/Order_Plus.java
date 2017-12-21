package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by EVRESTnhan on 10/7/2017.
 */

public class Order_Plus implements Parcelable {

    @SerializedName("maDonhang")
    @Expose
    private String maDonhang;
    @SerializedName("ngay")
    @Expose
    private String ngay;
    @SerializedName("calam")
    @Expose
    private String calam;
    @SerializedName("gio")
    @Expose
    private String gio;
    @SerializedName("chinhanh")
    @Expose
    private String chinhanh;
    @SerializedName("maNhanvien")
    @Expose
    private String maNhanvien;
    @SerializedName("tenNhanvien")
    @Expose
    private String tenNhanvien;
    @SerializedName("maSanpham")
    @Expose
    private String maSanpham;
    @SerializedName("tenSanpham")
    @Expose
    private String tenSanpham;
    @SerializedName("baohanhSanpham")
    @Expose
    private String baohanhSanpham;
    @SerializedName("nguonSanpham")
    @Expose
    private String nguonSanpham;
    @SerializedName("ngaynhapSanpham")
    @Expose
    private String ngaynhapSanpham;
    @SerializedName("vonSanpham")
    @Expose
    private String vonSanpham;
    @SerializedName("giaSanpham")
    @Expose
    private String giaSanpham;
    @SerializedName("giamgia")
    @Expose
    private String giamgia;
    @SerializedName("ghichuSanpham")
    @Expose
    private String ghichuSanpham;
    @SerializedName("tenKhachhang")
    @Expose
    private String tenKhachhang;
    @SerializedName("sodienthoaiKhachhang")
    @Expose
    private String sodienthoaiKhachhang;
    @SerializedName("ghichuKhachhang")
    @Expose
    private String ghichuKhachhang;
    @SerializedName("hinhthuc")
    @Expose
    private String hinhthuc;
    private int soluong;

    public Order_Plus(String maDonhang, String ngay, String calam, String gio, String chinhanh, String maNhanvien, String tenNhanvien, String maSanpham, String tenSanpham, String baohanhSanpham, String nguonSanpham, String ngaynhapSanpham, String vonSanpham, String giaSanpham, String giamgia, String ghichuSanpham, String tenKhachhang, String sodienthoaiKhachhang, String ghichuKhachhang, String hinhthuc, int soluong) {
        this.maDonhang = maDonhang;
        this.ngay = ngay;
        this.calam = calam;
        this.gio = gio;
        this.chinhanh = chinhanh;
        this.maNhanvien = maNhanvien;
        this.tenNhanvien = tenNhanvien;
        this.maSanpham = maSanpham;
        this.tenSanpham = tenSanpham;
        this.baohanhSanpham = baohanhSanpham;
        this.nguonSanpham = nguonSanpham;
        this.ngaynhapSanpham = ngaynhapSanpham;
        this.vonSanpham = vonSanpham;
        this.giaSanpham = giaSanpham;
        this.giamgia = giamgia;
        this.ghichuSanpham = ghichuSanpham;
        this.tenKhachhang = tenKhachhang;
        this.sodienthoaiKhachhang = sodienthoaiKhachhang;
        this.ghichuKhachhang = ghichuKhachhang;
        this.hinhthuc = hinhthuc;
        this.soluong = soluong;
    }

    protected Order_Plus(Parcel in) {
        maDonhang = in.readString();
        ngay = in.readString();
        calam = in.readString();
        gio = in.readString();
        chinhanh = in.readString();
        maNhanvien = in.readString();
        tenNhanvien = in.readString();
        maSanpham = in.readString();
        tenSanpham = in.readString();
        baohanhSanpham = in.readString();
        nguonSanpham = in.readString();
        ngaynhapSanpham = in.readString();
        vonSanpham = in.readString();
        giaSanpham = in.readString();
        giamgia = in.readString();
        ghichuSanpham = in.readString();
        tenKhachhang = in.readString();
        sodienthoaiKhachhang = in.readString();
        ghichuKhachhang = in.readString();
        hinhthuc = in.readString();
        soluong = in.readInt();
    }

    public static final Creator<Order_Plus> CREATOR = new Creator<Order_Plus>() {
        @Override
        public Order_Plus createFromParcel(Parcel in) {
            return new Order_Plus(in);
        }

        @Override
        public Order_Plus[] newArray(int size) {
            return new Order_Plus[size];
        }
    };

    public String getMaDonhang() {
        return maDonhang;
    }

    public void setMaDonhang(String maDonhang) {
        this.maDonhang = maDonhang;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }

    public String getCalam() {
        return calam;
    }

    public void setCalam(String calam) {
        this.calam = calam;
    }

    public String getGio() {
        return gio;
    }

    public void setGio(String gio) {
        this.gio = gio;
    }

    public String getChinhanh() {
        return chinhanh;
    }

    public void setChinhanh(String chinhanh) {
        this.chinhanh = chinhanh;
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

    public String getMaSanpham() {
        return maSanpham;
    }

    public void setMaSanpham(String maSanpham) {
        this.maSanpham = maSanpham;
    }

    public String getTenSanpham() {
        return tenSanpham;
    }

    public void setTenSanpham(String tenSanpham) {
        this.tenSanpham = tenSanpham;
    }

    public String getBaohanhSanpham() {
        return baohanhSanpham;
    }

    public void setBaohanhSanpham(String baohanhSanpham) {
        this.baohanhSanpham = baohanhSanpham;
    }

    public String getNguonSanpham() {
        return nguonSanpham;
    }

    public void setNguonSanpham(String nguonSanpham) {
        this.nguonSanpham = nguonSanpham;
    }

    public String getNgaynhapSanpham() {
        return ngaynhapSanpham;
    }

    public void setNgaynhapSanpham(String ngaynhapSanpham) {
        this.ngaynhapSanpham = ngaynhapSanpham;
    }

    public String getVonSanpham() {
        return vonSanpham;
    }

    public void setVonSanpham(String vonSanpham) {
        this.vonSanpham = vonSanpham;
    }

    public String getGiaSanpham() {
        return giaSanpham;
    }

    public void setGiaSanpham(String giaSanpham) {
        this.giaSanpham = giaSanpham;
    }

    public String getGiamgia() {
        return giamgia;
    }

    public void setGiamgia(String giamgia) {
        this.giamgia = giamgia;
    }

    public String getGhichuSanpham() {
        return ghichuSanpham;
    }

    public void setGhichuSanpham(String ghichuSanpham) {
        this.ghichuSanpham = ghichuSanpham;
    }

    public String getTenKhachhang() {
        return tenKhachhang;
    }

    public void setTenKhachhang(String tenKhachhang) {
        this.tenKhachhang = tenKhachhang;
    }

    public String getSodienthoaiKhachhang() {
        return sodienthoaiKhachhang;
    }

    public void setSodienthoaiKhachhang(String sodienthoaiKhachhang) {
        this.sodienthoaiKhachhang = sodienthoaiKhachhang;
    }

    public String getGhichuKhachhang() {
        return ghichuKhachhang;
    }

    public void setGhichuKhachhang(String ghichuKhachhang) {
        this.ghichuKhachhang = ghichuKhachhang;
    }

    public String getHinhthuc() {
        return hinhthuc;
    }

    public void setHinhthuc(String hinhthuc) {
        this.hinhthuc = hinhthuc;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maDonhang);
        dest.writeString(ngay);
        dest.writeString(calam);
        dest.writeString(gio);
        dest.writeString(chinhanh);
        dest.writeString(maNhanvien);
        dest.writeString(tenNhanvien);
        dest.writeString(maSanpham);
        dest.writeString(tenSanpham);
        dest.writeString(baohanhSanpham);
        dest.writeString(nguonSanpham);
        dest.writeString(ngaynhapSanpham);
        dest.writeString(vonSanpham);
        dest.writeString(giaSanpham);
        dest.writeString(giamgia);
        dest.writeString(ghichuSanpham);
        dest.writeString(tenKhachhang);
        dest.writeString(sodienthoaiKhachhang);
        dest.writeString(ghichuKhachhang);
        dest.writeString(hinhthuc);
        dest.writeInt(soluong);
    }
}