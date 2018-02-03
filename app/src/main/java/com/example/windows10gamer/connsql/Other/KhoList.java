package com.example.windows10gamer.connsql.Other;

import com.example.windows10gamer.connsql.Object.Kho;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by EVRESTnhan on 1/6/2018.
 */

public class KhoList {

    @SerializedName(Keys.KHO)
    @Expose
    private ArrayList<Kho> khos = new ArrayList<>();

    /**
     * @return The khos
     */
    public ArrayList<Kho> getContacts_kho() {

        return khos;

    }

    /**
     * @param khos The contacts
     */
    public void setContacts_kho(ArrayList<Kho> khos) {
        this.khos = khos;
    }
}
