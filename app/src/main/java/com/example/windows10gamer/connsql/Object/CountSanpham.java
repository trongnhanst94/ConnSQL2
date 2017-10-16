package com.example.windows10gamer.connsql.Object;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by EVRESTnhan on 10/4/2017.
 */

public class CountSanpham implements Parcelable {
    String nhanvien;
    String masanpham;
    int soluong;

    public CountSanpham(String nhanvien, String masanpham, int soluong) {
        this.masanpham = masanpham;
        this.soluong = soluong;
        this.nhanvien = nhanvien;
    }

    protected CountSanpham(Parcel in) {
        nhanvien = in.readString();
        masanpham = in.readString();
        soluong = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nhanvien);
        dest.writeString(masanpham);
        dest.writeInt(soluong);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<CountSanpham> CREATOR = new Creator<CountSanpham>() {
        @Override
        public CountSanpham createFromParcel(Parcel in) {
            return new CountSanpham(in);
        }

        @Override
        public CountSanpham[] newArray(int size) {
            return new CountSanpham[size];
        }
    };

    public String getNhanvien() {
        return nhanvien;
    }

    public void setNhanvien(String nhanvien) {
        this.nhanvien = nhanvien;
    }

    public String getMasanpham() {
        return masanpham;
    }

    public void setMasanpham(String masanpham) {
        this.masanpham = masanpham;
    }

    public int getSoluong() {
        return soluong;
    }

    public void setSoluong(int soluong) {
        this.soluong = soluong;
    }

}
