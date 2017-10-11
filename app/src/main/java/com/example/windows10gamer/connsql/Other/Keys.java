package com.example.windows10gamer.connsql.Other;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by EVRESTnhan on 9/29/2017.
 */

public class Keys {

    public static final String DANHSACHSANPHAM  = "Danh sách sản phẩm";
    public static final String DANHSACHKIEMKHO  = "Kiểm kho";
    public static final String TABLE            = "1KrIkqcjjqcoHbovhsQ5BbWNVDPOL3yxd9X37WuhziuQ";
    public static final String SCRIPT_DANHSACH  = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec";
    public static final String SCRIPT_KIEMKHO   = "https://script.google.com/macros/s/AKfycbxZ0sbLZkD0kHH81iBN234_YbGCA-C8SVFYeunFM1HaLV2BFyw/exec";
    public static final String SCRIPT_BANHANG   = "https://script.google.com/macros/s/AKfycbz9swph4DeDyk-_r2tMHH6g1kRLVyK6a8L2HJ28hXtWeb5KJX5S/exec";
    public static final String LOGIN            = "http://dealtichtac.com/android/user.php";
    public static final String SALE             = "bán hàng";
    public static final String RETROFIT_ORDER   = "https://script.googleusercontent.com/macros/";
    public static final String USER_CONTENT_KEY = "echo?user_content_key=2kMglZtPOfKBcr8h9QWRF-EM1f_Q_cJGbtD2Uefv-ZlJ2aklKH352KwgE3eVM4taw_nlADZUWKVUNtmzDAUq3JNwKgNujBe6OJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa1GhPSVukpSQTydEwAEXFXgt_wltjJcH3XHUaaPC1fv5o9XyvOto09QuWI89K6KjOu0SP2F-BdwUeJZgBO1CurgJDG6WZmYqXDtb7x2Qk_0FP_NtXc6PxLVKyiik53FWI0tkCmNREo_aKX_2MMxSu5TTyZ4LZOwEw8xIY9ui6FLpnFc0txhwKZ4&lib=MnrE7b2I2PjfH799VodkCPiQjIVyBAxva";

    public static final String getFormatedAmount(int amount){
        String number = NumberFormat.getNumberInstance(Locale.US).format(amount);
        String formatnumber = number.replace(",",".");
        return formatnumber;
    }


    public static final String getDateNow() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(new Date());
        return date;
    }

    public static final String setDate(String strCurrentDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T17:00:00.000Z'");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(strCurrentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);
        format = new SimpleDateFormat("dd/MM/yyyy");
        Date resultdate = new Date(c.getTimeInMillis());
        strCurrentDate = format.format(resultdate);
        return strCurrentDate;
    }

    public static final String pickDate(String strCurrentDate){
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T17:00:00.000Z'");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(format.parse(String.valueOf(strCurrentDate)));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 1);
            format = new SimpleDateFormat("dd/MM/yyyy");
            Date resultdate = new Date(c.getTimeInMillis());
            strCurrentDate = format.format(resultdate);
            return strCurrentDate;
    }
}
