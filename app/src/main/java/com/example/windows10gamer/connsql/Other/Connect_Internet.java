package com.example.windows10gamer.connsql.Other;

/**
 * Created by EVRESTnhan on 9/29/2017.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;

import com.example.windows10gamer.connsql.R;


public class Connect_Internet {

    /** CHECK WHETHER INTERNET CONNECTION IS AVAILABLE OR NOT */
    public static boolean checkConnection(@NonNull Context context) {
        return  ((ConnectivityManager) context.getSystemService
                (Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo() != null;
    }

    public static AlertDialog.Builder buildDialog(@NonNull Context c) {

        AlertDialog.Builder builder = null;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            builder = new AlertDialog.Builder(c);
        } else {
            builder = new AlertDialog.Builder(c, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
        }
        builder.setIcon(R.drawable.ic_no_wifi);
        builder.setTitle("Không có kết nối Wifi");

        builder.setMessage("Bạn cần kiểm tra lại đường truyền mạng. Nhấn Xác nhận để thoát!");

        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                int pid = android.os.Process.myPid();
                android.os.Process.killProcess(pid);
                System.exit(0);
            }
        });

        return builder;
    }
}
