package com.example.windows10gamer.connsql.Object;

/**
 * Created by EVRESTnhan on 3/7/2018.
 */

public class KhachHang {
    private String name, phone;

    public KhachHang(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
