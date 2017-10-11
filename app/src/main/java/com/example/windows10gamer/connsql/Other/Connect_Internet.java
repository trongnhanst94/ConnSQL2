package com.example.windows10gamer.connsql.Other;

/**
 * Created by EVRESTnhan on 9/29/2017.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;


public class Connect_Internet {

    /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
    public static boolean checkConnection(@NonNull Context context) {
        return  ((ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }
}
