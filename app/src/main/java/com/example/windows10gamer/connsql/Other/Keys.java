package com.example.windows10gamer.connsql.Other;

import android.text.TextUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
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
    public static final String DANHSACHSANPHAM     = "Danh sách sản phẩm";
    public static final String MAGIAMGIA           = "MAGIAMGIA";
    public static final String DANHSACHKIEMKHO     = "Kiểm kho";
    public static final String DANHSACHKIEMKHO2    = "Kiem kho";
    public static final String DANHSACHCUAHANG     = "CUAHANG";
    public static final String DANHSACHQUATANG     = "Danh sách quà tặng";
    public static final String TABLE               = "1KrIkqcjjqcoHbovhsQ5BbWNVDPOL3yxd9X37WuhziuQ";
    public static final String SCRIPT_DANHSACH     = "https://script.google.com/macros/s/AKfycbxOLElujQcy1-ZUer1KgEvK16gkTLUqYftApjNCM_IRTL3HSuDk/exec";
    public static final String SCRIPT_KIEMKHO      = "https://script.google.com/macros/s/AKfycbzDXojfyStMUnCM4kqG7XOWzgWEgN0k0D3rXLwaQbXber7CtUs/exec";
    public static final String SCRIPT_BANHANG      = "https://script.google.com/macros/s/AKfycbyqUuHPIFcsIQYFj55EcjWrRZG8AzdabIxnKmd4pIQE8yjbV1h5/exec";
    public static final String LOGIN               = "http://dealtichtac.com/android/user.php";
    public static final String DANHSACHLOGIN       = "http://dealtichtac.com/android/danhsachuser.php";
    public static final String SALE                = "banhang";
    public static final String RETROFIT_ORDER      = "http://dealtichtac.com/android/";
    public static final String USER_CONTENT_KEY    = "main_sales.php";
    public static final String SCRIPT_KHUYENMAI    = "https://script.google.com/macros/s/AKfycbx-SLcIVuQixDZ-n5pmFGIrjFZux9iIl9hunwGdqna_rMZ9lFI/exec";
    public static final String LINK_WEB            = "http://dealtichtac.com/android/addproduct_json.php";
    public static final String SCRIPT_BH_HT        = "https://script.google.com/macros/s/AKfycbwGtxm48MgPYzsagGOWjS0HliSCAEr_ODi0hNzhecjk2mvc98I/exec";
    public static final String SCRIPT_BH_1DOI1     = "https://script.google.com/macros/s/AKfycbyLeTwQKlRCUsN3mNWf3T7a4fBRJexVNyIaeu3B6ws_pp6oTcE/exec";
    public static final String SCRIPT_BH_DLK       = "https://script.google.com/macros/s/AKfycbw_Y1gJhdOWjeQGqj0UHoz6N2FbxmUXI0H3jqW83vOfe4l0BHG3/exec";
    public static final String SCRIPT_DE_XEMA      = "https://script.google.com/macros/s/AKfycbzvnwBIA1K8Pteu-ABzZ24WzwuJerMIVoiw_GR2lFW7pP-y7qw/exec";
    public static final String ADD_SALES_WEB       = "ADD_SALES_WEB";
    public static final String ADD_KM_WEB          = "ADD_KM_WEB";
    public static final String ADD_KIEMKHO_WEB     = "ADD_KIEMKHO_WEB";
    public static final String ADD_BH1DOI1_WEB     = "ADD_BH1DOI1_WEB";
    public static final String ADD_BHHT_WEB        = "ADD_BHHT_WEB";
    public static final String ADD_BHDLK_WEB       = "ADD_BHDLK_WEB";
    public static final String ADD_MAGIAMGIA_WEB   = "ADD_MAGIAMGIA_WEB";
    public static final String BH1DOI1_SHEET       = "BH1DOI1_SHEET";
    public static final String BHHT_SHEET          = "BHHT_SHEET";
    public static final String BHDLK_SHEET         = "BHDLK_SHEET";
    public static final int LEVEL_BH               = 1;
    public static final String MAIN_KIEMKHO_URL    = "http://dealtichtac.com/android/danhsach_kiemkho.php";
    public static final String MAIN_BH_BH1DOI1     = "http://dealtichtac.com/android/danhsach_bh1d1.php";
    public static final String MAIN_BH_BHHT        = "http://dealtichtac.com/android/danhsach_bhht.php";
    public static final String MAIN_BH_BHDLK       = "http://dealtichtac.com/android/danhsach_bhdlk.php";
    public static final String MAIN_MENU_DSCH      = "http://dealtichtac.com/android/danhsach_chinhanh.php";
    public static final String SCRIPT_DEBH         = "https://script.google.com/macros/s/AKfycbyaebG-6_tGK066iBLGlMYdCCtyCvITCcv8j0pDQ8-EPyq1fRs/exec";
    public static final String SCRIPT_BC_KK        = "https://script.google.com/macros/s/AKfycbzJhrsk7bRmzusNOCt9FPC0HtGpngjl1TLcIr1vsjMAkYvZfOaH/exec";
    public static final String SCRIPT_CREATEDTABLE = "https://script.google.com/macros/s/AKfycbzg_j3uTnZ35KqVm1gjUrgJAXwnvpkGlDsKEZSq332tW9kt_xg/exec";
    public static final String NameApp             = "iDeal";
    public static final String donhang             = "donhang";
    public static final String[] HELLOS            = {"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","K","L","M","N","O","P","Q","S","T","U","V"};
    public static final int SOMA_GIAMGIA           = 5;
    public static final String SCRIPT_MAGIAMGIA    = "https://script.google.com/macros/s/AKfycbymUIoVxGJ2NEo2h0IAtrxqp3lIE16Fj8i7IbjbSvHCekQPU60/exec";
    public static final String SCRIPT_DE_MAGIAMGIA = "https://script.google.com/macros/s/AKfycbwOh4Lzy65vgtyQ-FjpM4doyXgZk-ocREyf0MBd2yhA14lNslGv/exec";
    public static final String DELE_MAGIAMGIA_WEB  = "DELE_MAGIAMGIA_WEB";
    public static final String DELE_XEM_WEB        = "DELE_XEM_WEB";
    public static final String urlQT               = SCRIPT_DANHSACH +"?id="+ TABLE +"&sheet="+ DANHSACHQUATANG;
    public static final String urlGG               = "http://dealtichtac.com/android/danhsach_magiamgia.php";
    public static final String BH_TENVAMA          = "baohanh_TenvaMa.php";
    public static final int GIOHETCANVL            = 15;
    public static final int GIOHETCASOL            = 17;
    public static final int GIOHETCA3_2            = 15;
    public static final int GIOHETCABIGC           = 15;
    public static final String TENCASANG           = "Ca sáng";
    public static final String TENCACHIEU          = "Ca chiều";
    public static final long LOAD_REALTIME         = 5000;
    public static final String SCRIPT_BH_CXL       = "https://script.google.com/macros/s/AKfycbwKVYH32Z627O3X1_06anwMsJUXOTsYaLvyQ39TrPFa-tARDw4R/exec";
    public static final String ADD_BHCXL_WEB       = "ADD_BHCXL_WEB";
    public static final String ADD_BHDDT_WEB       = "ADD_BHDDT_WEB";
    public static final String BHCXL_SHEET         = "BHCXL_SHEET";
    public static final String MAIN_BH_BHCXL       = "http://dealtichtac.com/android/danhsach_bhcxl.php";
    public static final String WEBSITE_URL         = "http://dealtichtac.com";
    public static final String SCRIPT_BH_DDT       = "https://script.google.com/macros/s/AKfycbzCaKmPPe41LQmz5WxCokyc6otAjLKIxmLRngMz3NtMTYre5Nle/exec";
    public static final String MAIN_BH_BHDDT       = "http://dealtichtac.com/android/danhsach_bhddt.php";
    public static final String BHDDT_SHEET         = "BHDDT_SHEET";


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

    public static final String getTimeNow() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(new Date());
        return date;
    }

    public static final String getDateNowPlus(final int songay) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, songay);
        Date tomorrow = calendar.getTime();
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String todayAsString = dateFormat.format(tomorrow);
        return todayAsString;
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
            c.add(Calendar.DATE, 0);
            format = new SimpleDateFormat("dd-MM-yyyy");
            Date resultdate = new Date(c.getTimeInMillis());
            strCurrentDate = format.format(resultdate);
            return strCurrentDate;
    }

    public static final String getCalam(String chinhanh) {
        String ca; int gio = 0;
        if (chinhanh.equals("Chi nhánh SOL")){
            gio = GIOHETCASOL;
        } else if (chinhanh.equals("Chi nhánh 3/2")){
            gio = GIOHETCA3_2;
        } else if (chinhanh.equals("Chi nhánh Nguyễn Văn Linh")){
            gio = GIOHETCANVL;
        } else if (chinhanh.equals("Chi nhánh Big C")){
            gio = GIOHETCABIGC;
        }
        int hours = new Time(System.currentTimeMillis()).getHours();
        if (hours < gio){
            ca = "Ca sáng";
        } else {
            ca = "Ca chiều";
        }
        return ca;
    }

    public static final String trimText(String s, int maxLength){
        if(!TextUtils.isEmpty(s)){
            if(s.length() >= maxLength){
                return s.substring(0, maxLength)+"...";
            } else {
                return s;
            }
        }
        return s;
    }



}
