package com.example.windows10gamer.connsql.Other;

import com.example.windows10gamer.connsql.Object.Order;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by EVRESTnhan on 10/8/2017.
 */

public class OrderList {

    @SerializedName(Keys.SALE)
    @Expose
    private ArrayList<Order> orders = new ArrayList<>();

    /**
     * @return The orders
     */
    public ArrayList<Order> getContacts() {

        return orders;

    }

    /**
     * @param orders The contacts
     */
    public void setContacts(ArrayList<Order> orders) {
        this.orders = orders;
    }
}