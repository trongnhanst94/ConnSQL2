package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by EVRESTnhan on 10/7/2017.
 */

public class Order implements Parcelable {

    @SerializedName("maDonhang")
    @Expose
    private  String maOrder;
    @SerializedName("ngay")
    @Expose
    private  String dateOrder;
    @SerializedName("calam")
    @Expose
    private  String timeOrder;
    @SerializedName("maNhanvien")
    @Expose
    private  String maNVOrder;
    @SerializedName("tenNhanvien")
    @Expose
    private  String tenNVOrder;
    @SerializedName("maSanpham")
    @Expose
    private  String maspOrder;
    @SerializedName("tenSanpham")
    @Expose
    private  String tenOrder;
    @SerializedName("baohanhSanpham")
    @Expose
    private  String baohanhOrder;
    @SerializedName("nguonSanpham")
    @Expose
    private  String nguonOrder;
    @SerializedName("ngaynhapSanpham")
    @Expose
    private  String ngaynhapOrder;
    @SerializedName("giaSanpham")
    @Expose
    private  String giaOrder;
    @SerializedName("ghichuSanpham")
    @Expose
    private  String ghichuOrder;
    @SerializedName("tenKhachhang")
    @Expose
    private  String tenKH;
    @SerializedName("sodienthoaiKhachhang")
    @Expose
    private  String sdtKH;
    @SerializedName("ghichuKhachhang")
    @Expose
    private  String ghichuKH;


//    private String maOrder, dateOrder,timeOrder,maNVOrder,tenNVOrder, maspOrder, tenOrder, baohanhOrder,
//            nguonOrder, ngaynhapOrder, giaOrder, ghichuOrder, tenKH, sdtKH, ghichuKH;

    public Order(String maOrder, String dateOrder, String timeOrder, String maNVOrder, String tenNVOrder, String maspOrder, String tenOrder, String baohanhOrder, String nguonOrder,
                 String ngaynhapOrder, String giaOrder, String ghichuOrder, String tenKH, String sdtKH, String ghichuKH) {
        this.maOrder = maOrder;
        this.dateOrder = dateOrder;
        this.timeOrder = timeOrder;
        this.maNVOrder = maNVOrder;
        this.tenNVOrder = tenNVOrder;
        this.maspOrder = maspOrder;
        this.tenOrder = tenOrder;
        this.baohanhOrder = baohanhOrder;
        this.nguonOrder = nguonOrder;
        this.ngaynhapOrder = ngaynhapOrder;
        this.giaOrder = giaOrder;
        this.ghichuOrder = ghichuOrder;
        this.tenKH = tenKH;
        this.sdtKH = sdtKH;
        this.ghichuKH = ghichuKH;
    }

    protected Order(Parcel in) {
        maOrder = in.readString();
        dateOrder = in.readString();
        timeOrder = in.readString();
        maNVOrder = in.readString();
        tenNVOrder = in.readString();
        maspOrder = in.readString();
        tenOrder = in.readString();
        baohanhOrder = in.readString();
        nguonOrder = in.readString();
        ngaynhapOrder = in.readString();
        giaOrder = in.readString();
        ghichuOrder = in.readString();
        tenKH = in.readString();
        sdtKH = in.readString();
        ghichuKH = in.readString();
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getMaOrder() {
        return maOrder;
    }

    public void setMaOrder(String maOrder) {
        this.maOrder = maOrder;
    }

    public String getDateOrder() {
        return dateOrder;
    }

    public void setDateOrder(String dateOrder) {
        this.dateOrder = dateOrder;
    }

    public String getTimeOrder() {
        return timeOrder;
    }

    public void setTimeOrder(String timeOrder) {
        this.timeOrder = timeOrder;
    }

    public String getMaNVOrder() {
        return maNVOrder;
    }

    public void setMaNVOrder(String maNVOrder) {
        this.maNVOrder = maNVOrder;
    }

    public String getTenNVOrder() {
        return tenNVOrder;
    }

    public void setTenNVOrder(String tenNVOrder) {
        this.tenNVOrder = tenNVOrder;
    }

    public String getMaspOrder() {
        return maspOrder;
    }

    public void setMaspOrder(String maspOrder) {
        this.maspOrder = maspOrder;
    }

    public String getTenOrder() {
        return tenOrder;
    }

    public void setTenOrder(String tenOrder) {
        this.tenOrder = tenOrder;
    }

    public String getBaohanhOrder() {
        return baohanhOrder;
    }

    public void setBaohanhOrder(String baohanhOrder) {
        this.baohanhOrder = baohanhOrder;
    }

    public String getNguonOrder() {
        return nguonOrder;
    }

    public void setNguonOrder(String nguonOrder) {
        this.nguonOrder = nguonOrder;
    }

    public String getNgaynhapOrder() {
        return ngaynhapOrder;
    }

    public void setNgaynhapOrder(String ngaynhapOrder) {
        this.ngaynhapOrder = ngaynhapOrder;
    }

    public String getGiaOrder() {
        return giaOrder;
    }

    public void setGiaOrder(String giaOrder) {
        this.giaOrder = giaOrder;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(maOrder);
        dest.writeString(dateOrder);
        dest.writeString(timeOrder);
        dest.writeString(maNVOrder);
        dest.writeString(tenNVOrder);
        dest.writeString(maspOrder);
        dest.writeString(tenOrder);
        dest.writeString(baohanhOrder);
        dest.writeString(nguonOrder);
        dest.writeString(ngaynhapOrder);
        dest.writeString(giaOrder);
        dest.writeString(ghichuOrder);
        dest.writeString(tenKH);
        dest.writeString(sdtKH);
        dest.writeString(ghichuKH);
    }
}
