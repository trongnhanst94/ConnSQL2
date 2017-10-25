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
    public static final String DANHSACHCUAHANG  = "Danh sách cửa hàng";
    public static final String DANHSACHQUATANG  = "Danh sách quà tặng";
    public static final String TABLE            = "1KrIkqcjjqcoHbovhsQ5BbWNVDPOL3yxd9X37WuhziuQ";
    public static final String SCRIPT_DANHSACH  = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec";
    public static final String SCRIPT_KIEMKHO   = "https://script.google.com/macros/s/AKfycbzDXojfyStMUnCM4kqG7XOWzgWEgN0k0D3rXLwaQbXber7CtUs/exec";
    public static final String SCRIPT_BANHANG   = "https://script.google.com/macros/s/AKfycbycEFBmH7gNRt70F6K9u5nycej_Tt4s4BJHDqQ0lmHCI9IbdBkm/exec";
    public static final String LOGIN            = "http://dealtichtac.com/android/user.php";
    public static final String SALE             = "Bán hàng";
    public static final String RETROFIT_ORDER   = "https://script.googleusercontent.com/macros/";
    public static final String USER_CONTENT_KEY = "echo?user_content_key=2kMglZtPOfKBcr8h9QWRF-EM1f_Q_cJGbtD2Uefv-ZlJ2aklKH352KwgE3eVM4taw_nlADZUWKVUNtmzDAUq3JNwKgNujBe6OJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa1GhPSVukpSQTydEwAEXFXgt_wltjJcH3XHUaaPC1fv5o9XyvOto09QuWI89K6KjOu0SP2F-BdwUeJZgBO1CurgJDG6WZmYqXDtb7x2Qk_0FP_NtXc6PxLVKyiik53FWI0tkCmNREo_aKX_2MMxSu5TTyZ4LZOwEw8xIY9ui6FLpnFc0txhwKZ4&lib=MnrE7b2I2PjfH799VodkCPiQjIVyBAxva";
    public static final String SCRIPT_KHUYENMAI = "https://script.google.com/macros/s/AKfycbx-SLcIVuQixDZ-n5pmFGIrjFZux9iIl9hunwGdqna_rMZ9lFI/exec";
    public static final String LINK_WEB         = "http://dealtichtac.com/android/addproduct_json.php";
    public static final String ADD_SALES_WEB    = "ADD_SALES_WEB";
    public static final String ADD_KM_WEB       = "ADD_KM_WEB";
    public static final String ADD_KIEMKHO_WEB  = "ADD_KIEMKHO_WEB";

    public static final String getFormatedAmount(int amount){
        String number = NumberFormat.getNumberInstance(Locale.US).format(amount);
        String formatnumber = number.replace(",",".");
        return formatnumber+"đ";
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
