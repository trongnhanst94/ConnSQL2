package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by EVRESTnhan on 1/6/2018.
 */

public class Kho implements Parcelable {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("chinhanh")
    @Expose
    private String chinhanh;
    @SerializedName("kho")
    @Expose
    private String kho;
    @SerializedName("maNV")
    @Expose
    private String maNV;
    @SerializedName("tenNV")
    @Expose
    private String tenNV;
    @SerializedName("ma")
    @Expose
    private String ma;
    @SerializedName("ten")
    @Expose
    private String ten;
    @SerializedName("baohanh")
    @Expose
    private String baohanh;
    @SerializedName("nguon")
    @Expose
    private String nguon;
    @SerializedName("ngaynhap")
    @Expose
    private String ngaynhap;
    @SerializedName("von")
    @Expose
    private String von;
    @SerializedName("gia")
    @Expose
    private String gia;

    public Kho(String id, String chinhanh, String kho, String maNV, String tenNV, String ma, String ten, String baohanh, String nguon, String ngaynhap, String von, String gia) {
        this.id = id;
        this.chinhanh = chinhanh;
        this.kho = kho;
        this.maNV = maNV;
        this.tenNV = tenNV;
        this.ma = ma;
        this.ten = ten;
        this.baohanh = baohanh;
        this.nguon = nguon;
        this.ngaynhap = ngaynhap;
        this.von = von;
        this.gia = gia;
    }

    protected Kho(Parcel in) {
        id = in.readString();
        chinhanh = in.readString();
        kho = in.readString();
        maNV = in.readString();
        tenNV = in.readString();
        ma = in.readString();
        ten = in.readString();
        baohanh = in.readString();
        nguon = in.readString();
        ngaynhap = in.readString();
        von = in.readString();
        gia = in.readString();
    }

    public static final Creator<Kho> CREATOR = new Creator<Kho>() {
        @Override
        public Kho createFromParcel(Parcel in) {
            return new Kho(in);
        }

        @Override
        public Kho[] newArray(int size) {
            return new Kho[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChinhanh() {
        return chinhanh;
    }

    public void setChinhanh(String chinhanh) {
        this.chinhanh = chinhanh;
    }

    public String getKho() {
        return kho;
    }

    public void setKho(String kho) {
        this.kho = kho;
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

    public String getGia() {
        return gia;
    }

    public void setGia(String gia) {
        this.gia = gia;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(chinhanh);
        dest.writeString(kho);
        dest.writeString(maNV);
        dest.writeString(tenNV);
        dest.writeString(ma);
        dest.writeString(ten);
        dest.writeString(baohanh);
        dest.writeString(nguon);
        dest.writeString(ngaynhap);
        dest.writeString(von);
        dest.writeString(gia);
    }
}
