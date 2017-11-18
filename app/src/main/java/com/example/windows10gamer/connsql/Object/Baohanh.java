package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 11/7/2017.
 */

public class Baohanh {
    String maBH;
    String shortName;
    String date;

    public Baohanh(String maBH,  String date, String shortName) {
        this.maBH = maBH;
        this.shortName = shortName;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getMaBH() {
        return maBH;
    }

    public void setMaBH(String maBH) {
        this.maBH = maBH;
    }
}

