package com.example.windows10gamer.connsql.Other;

import com.example.windows10gamer.connsql.Object.Sanpham;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    public static final String DANHSACHLOGIN    = "http://dealtichtac.com/android/danhsachuser.php";
    public static final String SALE             = "bán hàng";
    public static final String RETROFIT_ORDER   = "https://script.googleusercontent.com/macros/";
    public static final String USER_CONTENT_KEY = "echo?user_content_key=2kMglZtPOfKBcr8h9QWRF-EM1f_Q_cJGbtD2Uefv-ZlJ2aklKH352KwgE3eVM4taw_nlADZUWKVUNtmzDAUq3JNwKgNujBe6OJmA1Yb3SEsKFZqtv3DaNYcMrmhZHmUMWojr9NvTBuBLhyHCd5hHa1GhPSVukpSQTydEwAEXFXgt_wltjJcH3XHUaaPC1fv5o9XyvOto09QuWI89K6KjOu0SP2F-BdwUeJZgBO1CurgJDG6WZmYqXDtb7x2Qk_0FP_NtXc6PxLVKyiik53FWI0tkCmNREo_aKX_2MMxSu5TTyZ4LZOwEw8xIY9ui6FLpnFc0txhwKZ4&lib=MnrE7b2I2PjfH799VodkCPiQjIVyBAxva";
    public static final String SCRIPT_KHUYENMAI = "https://script.google.com/macros/s/AKfycbx-SLcIVuQixDZ-n5pmFGIrjFZux9iIl9hunwGdqna_rMZ9lFI/exec";
    public static final String LINK_WEB         = "http://dealtichtac.com/android/addproduct_json.php";
    public static final String SCRIPT_BH_HT     = "https://script.google.com/macros/s/AKfycbwGtxm48MgPYzsagGOWjS0HliSCAEr_ODi0hNzhecjk2mvc98I/exec";
    public static final String SCRIPT_BH_1DOI1  = "https://script.google.com/macros/s/AKfycbyLeTwQKlRCUsN3mNWf3T7a4fBRJexVNyIaeu3B6ws_pp6oTcE/exec";
    public static final String SCRIPT_BH_DLK    = "https://script.google.com/macros/s/AKfycbw_Y1gJhdOWjeQGqj0UHoz6N2FbxmUXI0H3jqW83vOfe4l0BHG3/exec";
    public static final String ADD_SALES_WEB    = "ADD_SALES_WEB";
    public static final String ADD_KM_WEB       = "ADD_KM_WEB";
    public static final String ADD_KIEMKHO_WEB  = "ADD_KIEMKHO_WEB";
    public static final String ADD_BH1DOI1_WEB  = "ADD_BH1DOI1_WEB";
    public static final String ADD_BHHT_WEB     = "ADD_BHHT_WEB";
    public static final String ADD_BHDLK_WEB    = "ADD_BHDLK_WEB";
    public static final String BH1DOI1_SHEET    = "Bảo hành 1 đổi 1";
    public static final String BHHT_SHEET       = "Bảo hành - Hoàn tiền";
    public static final String BHDLK_SHEET      = "Bảo hành đổi lấy khác";
    public static final int LEVEL_BH            = 1;
    public static final String MAIN_KIEMKHO_URL    = Keys.SCRIPT_DANHSACH +"?id="+ Keys.TABLE +"&sheet="+ Keys.DANHSACHKIEMKHO;
    public static final String MAIN_KIEMKHO_URL2   = Keys.SCRIPT_DANHSACH +"?id="+ Keys.TABLE +"&sheet="+ Keys.DANHSACHCUAHANG;
    public static final String MAIN_BH_BH1DOI1     = Keys.SCRIPT_DANHSACH +"?id="+ Keys.TABLE +"&sheet="+ Keys.BH1DOI1_SHEET;
    public static final String MAIN_BH_BHHT        = Keys.SCRIPT_DANHSACH +"?id="+ Keys.TABLE +"&sheet="+ Keys.BHHT_SHEET;
    public static final String MAIN_BH_BHDLK       = Keys.SCRIPT_DANHSACH +"?id="+ Keys.TABLE +"&sheet="+ Keys.BHDLK_SHEET;
    public static final String MAIN_MENU_DSCH      = Keys.SCRIPT_DANHSACH +"?id="+ Keys.TABLE +"&sheet="+ Keys.DANHSACHCUAHANG;
    public static final String SCRIPT_DEBH         = "https://script.google.com/macros/s/AKfycbyaebG-6_tGK066iBLGlMYdCCtyCvITCcv8j0pDQ8-EPyq1fRs/exec";
    public static final String SCRIPT_BC_KK        = "https://script.google.com/macros/s/AKfycbzJhrsk7bRmzusNOCt9FPC0HtGpngjl1TLcIr1vsjMAkYvZfOaH/exec";
    public static final String SCRIPT_CREATEDTABLE = "https://script.google.com/macros/s/AKfycbzg_j3uTnZ35KqVm1gjUrgJAXwnvpkGlDsKEZSq332tW9kt_xg/exec";



    public static final String getFormatedAmount(int amount){
        String number = NumberFormat.getNumberInstance(Locale.US).format(amount);
        String formatnumber = number.replace(",",".");
        return formatnumber+"đ";
    }

    public static final String MaDonhang() {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        String date = dateFormat.format(new Date());
        return date;
    }

    public static final String getDateNow() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(new Date());
        return date;
    }

    public static final String md5(final String s) {
        final String MD5 = "MD5";
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
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
            format = new SimpleDateFormat("dd-MM-yyyy");
            Date resultdate = new Date(c.getTimeInMillis());
            strCurrentDate = format.format(resultdate);
            return strCurrentDate;
    }

    public static final String getCalam() {
        String ca;
        int hours = new Time(System.currentTimeMillis()).getHours();
        if (hours < 15 ){
            ca = "Ca sáng";
        } else {
            ca = "Ca chiều";
        }
        return ca;
    }

    public static final String setNN(String strCurrentDate) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T17:00:00.000Z'");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(strCurrentDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.add(Calendar.DATE, 1);
        format = new SimpleDateFormat("dd-MM-yyyy");
        Date resultdate = new Date(c.getTimeInMillis());
        strCurrentDate = format.format(resultdate);
        return strCurrentDate;
    }

    public static final String setDate2(String strCurrentDate) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T17:00:00.000Z'");
            Calendar c = Calendar.getInstance();
            try {
                c.setTime(format.parse(strCurrentDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            c.add(Calendar.DATE, 1);
            format = new SimpleDateFormat("dd-MM-yyyy");
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
            format = new SimpleDateFormat("dd-MM-yyyy");
            Date resultdate = new Date(c.getTimeInMillis());
            strCurrentDate = format.format(resultdate);
            return strCurrentDate;
    }

    public static final ArrayList<Sanpham> ChangName(ArrayList<Sanpham> sanpham){

        return sanpham;
    }
}
