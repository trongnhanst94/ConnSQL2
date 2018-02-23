package com.example.windows10gamer.connsql.Other.notifications;

/**
 * Created by EVRESTnhan on 2/22/2018.
 */

import com.google.gson.annotations.SerializedName;

import javax.annotation.Generated;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class NotificationData {

    @SerializedName("detail")
    private String mDetail;

    @SerializedName("title")
    private String mTitle;

    public String getDetail() {
        return mDetail;
    }

    public void setDetail(String detail) {
        mDetail = detail;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }
}
