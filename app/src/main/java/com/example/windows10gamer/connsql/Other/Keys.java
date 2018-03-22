package com.example.windows10gamer.connsql.Other;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.widget.DatePicker;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Object.Customer;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Object.ReportSales;
import com.example.windows10gamer.connsql.Object.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by EVRESTnhan on 9/29/2017.
 */

public class Keys {

    // Link server
    public static final String LINK_SERVER         = "http://dealtichtac.com/android/";
    public static final String LOGIN2              = LINK_SERVER + "login.php";
    public static final String DANHSACHLOGIN       = LINK_SERVER + "danhsachuser.php";
    public static final String LINK_WEB            = LINK_SERVER + "addproduct_json.php";
    public static final String MAIN_KIEMKHO_URL    = LINK_SERVER + "danhsach_kiemkho.php";
    public static final String MAIN_BH_BH1DOI1     = LINK_SERVER + "danhsach_bh1d1.php";
    public static final String MAIN_BH_BHHT        = LINK_SERVER + "danhsach_bhht.php";
    public static final String MAIN_BH_BHDLK       = LINK_SERVER + "danhsach_bhdlk.php";
    public static final String MAIN_MENU_DSCH      = LINK_SERVER + "danhsach_chinhanh.php";
    public static final String DANHSACHDICA        = LINK_SERVER + "danhsach_dica.php";
    public static final String MAIN_QUATANG        = LINK_SERVER + "danhsach_quatang.php";
    public static final String MAIN_CHITIEU_NV     = LINK_SERVER + "danhsach_chitieunhanvien.php";
    public static final String MAIN_KHUYENMAI      = LINK_SERVER + "danhsach_khuyenmai.php";
    public static final String MAIN_CHUONGTRINH    = LINK_SERVER + "danhsach_chuongtrinh.php";
    public static final String UPLOAD_AVATAR       = LINK_SERVER + "upload_avatar.php";
    public static final String LINK_AVATAR         = LINK_SERVER + "avatar/";
    public static final String MAIN_VERSION        = LINK_SERVER + "danhsach_version.php";
    public static final String MAIN_LINKAVATAR     = LINK_SERVER + "linkavatar.php";
    public static final String MAIN_TIENTRAVE      = LINK_SERVER + "danhsach_tientrave.php";
    public static final String MAIN_PHICOD         = LINK_SERVER + "danhsach_phicod.php";
    public static final String MAIN_BH_BHCXL       = LINK_SERVER + "danhsach_bhcxl.php";
    public static final String WEBSITE_URL         = "http://dealtichtac.com";
    public static final String MAIN_BH_BHDDT       = LINK_SERVER + "danhsach_bhddt.php";
    public static final String LINK_WEB_XUATNHAP   = LINK_SERVER + "addXuatnhap.php";
    public static final String LINK_WEB_V2         = LINK_SERVER + "addV2.php";
    public static final String MAIN_XUATNHAP       = LINK_SERVER + "danhsach_xuatnhap.php";
    public static final String MAIN_KHOANCHI       = LINK_SERVER + "danhsach_khoanchi.php";
    public static final String MAIN_DATCOC         = LINK_SERVER + "danhsach_datcoc.php";
    public static final String MAIN_DOANHTHU       = LINK_SERVER + "danhsach_doanhthu.php";
    public static final String MAIN_REALTIME       = LINK_SERVER + "danhsach_realtime_order.php";
    public static final String MAIN_MAGIAMGIA      = LINK_SERVER + "danhsach_magiamgia.php";
    public static final String MAIN_LISTSOLUONG    = LINK_SERVER + "danhsach_listsoluong.php";
    public static final String CHECKKHACHHANG      = LINK_SERVER + "danhsach_checkkhachhang.php";
    public static final String MAIN_DANHMUC        = LINK_SERVER + "danhsach_danhmuc.php";
    public static final String MAIN_DANHMUC_V2     = LINK_SERVER + "danhsach_danhmucv2.php";
    public static final String MAIN_MASTER         = LINK_SERVER + "danhsach_master.php";

    public static final int LEVEL_BH               = 2;
    public static final int LEVEL_QL               = 0;
    public static final int LEVEL_KHO              = 1;
    public static final int SOMA_GIAMGIA           = 5;
    public static final int GIOHETCASOL            = 15;
    public static final int MAX_LENGHT             = 22;
    public static final int[] MY_COLORS            = {Color.parseColor("#FF4444"), Color.parseColor("#FFBB33"), Color.parseColor("#AA66CC"), Color.parseColor("#33B5E5"), Color.parseColor("#99CC00")};
    public static final Random RANDOM             = new Random();
    public static final String[] HELLOS            = {"1","2","3","4","5","6","7","8","9","A","B","C","D","E","F","G","H","I","K","L","M","N","O","P","Q","S","T","U","V"};
    public static final String[] RAND              = {"1","2","3","4","5","6","7","8","9","0"};
    public static final String DANHSACHSANPHAM     = "Danh sách sản phẩm";
    public static final String DANHSACHKIEMKHO2    = "Kiem kho";
    public static final String TABLE               = "1KrIkqcjjqcoHbovhsQ5BbWNVDPOL3yxd9X37WuhziuQ";
    public static final String SALE                = "banhang";
    public static final String USER_SALES_KEY      = "main_sales.php";
    public static final String USER_KHO_KEY        = "danhsach_kho.php";
    public static final String CN_SOL              = "Chi nhánh SOL";
    public static final String CN_NVL              = "Chi nhánh Nguyễn Văn Linh";
    public static final String NOW_VERSION         = "Version 7.2";
    public static final String FIREBASE_API_LINK   = "https://fcm.googleapis.com/fcm/send";
    public static final String FIREBASE_TOKEN      = "/topics/all";
    public static final String FIREBASE_SERVER_KEY = "AAAAvlkWcQA:APA91bEqA621By39e_NeaScI3cuBiQe9ZPaxCqzQiY0cq5Ysvot9vEZIJ-HeI9n-a9JjO-gR8q9QDVYQzV9xE6d-6iB6k9E6po2dZiYl46rKIEXBNfk72wupRulJdCrCTaAphnWh_B2n";
    public static final String GHINO               = "Ghi nợ";
    public static final String TIENMAT             = "Tiền mặt";
    public static final String CA_SANG             = "Ca sáng";
    public static final String CA_CHIEU            = "Ca chiều";
    public static final long LOAD_REALTIME         = 10000;
    public static final List<String> DANHMUCLIST   = Arrays.asList("KHOANCHI", "TIENTRAVE");

    // Key Json for Server
    public static final String MAGIAMGIA           = "MAGIAMGIA";
    public static final String DANHSACHCUAHANG     = "CUAHANG";
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
    public static final String DELE_MAGIAMGIA_WEB  = "DELE_MAGIAMGIA_WEB";
    public static final String DELE_XEM_WEB        = "DELE_XEM_WEB";
    public static final String ADD_BHCXL_WEB       = "ADD_BHCXL_WEB";
    public static final String ADD_BHDDT_WEB       = "ADD_BHDDT_WEB";
    public static final String BHCXL_SHEET         = "BHCXL_SHEET";
    public static final String BHDDT_SHEET         = "BHDDT_SHEET";
    public static final String ADD_XUATHANG_WEB    = "ADD_XUATHANG_WEB";
    public static final String XUATNHAP            = "XUATNHAP";
    public static final String UPDATE_XUATHANG_WEB = "UPDATE_XUATHANG_WEB";
    public static final String ADD_KHOANCHI_WEB    = "ADD_KHOANCHI_WEB";
    public static final String ADD_DATCOC_WEB      = "ADD_DATCOC_WEB";
    public static final String DATCOC              = "DATCOC";
    public static final String UPDATE_DATCOC_WEB   = "UPDATE_DATCOC_WEB";
    public static final String DOANHTHU            = "DOANHTHU";
    public static final String ADD_DOANHTHU_WEB    = "ADD_DOANHTHU_WEB";
    public static final String DELE_DONHANG_WEB    = "DELE_DONHANG_WEB";
    public static final String DELE_KIEMKHO_WEB    = "DELE_KIEMKHO_WEB";
    public static final String KHO                 = "KHO";
    public static final String DELE_DOANHTHU_WEB   = "DELE_DOANHTHU_WEB";
    public static final String DELE_DATCOC_WEB     = "DELE_DATCOC_WEB";
    public static final String DELE_KHOANCHI_WEB   = "DELE_KHOANCHI_WEB";
    public static final String LISTSOLUONG         = "LISTSOLUONG";
    public static final String CHANGE_PASSWORD     = "CHANGE_PASSWORD";
    public static final String VERSION             = "VERSION";
    public static final String DI_CA               = "DI_CA";
    public static final String QUA_TANG            = "QUA_TANG";
    public static final String CHITIEU_NHANVIEN    = "CHITIEU_NHANVIEN";
    public static final String KHUYENMAI           = "KHUYEN_MAI";
    public static final String CHUONG_TRINH        = "CHUONG_TRINH";
    public static final String UPDATE_IMG          = "UPDATE_IMG";
    public static final String ADD_TIENTRAVE_WEB   = "ADD_TIENTRAVE_WEB";
    public static final String TIEN_TRA_VE         = "TIEN_TRA_VE";
    public static final String ADD_COD_WEB         = "ADD_COD_WEB";
    public static final String DELE_COD_WEB        = "DELE_COD_WEB";
    public static final String DELE_TIENTRAVE_WEB  = "DELE_TIENTRAVE_WEB";
    public static final String UPDATE_GHI_NO       = "UPDATE_GHI_NO";
    public static final String PHI_COD             = "PHI_COD";
    public static final String KHOANCHI            = "KHOANCHI";
    public static final String TIENTRAVE           = "TIENTRAVE";
    public static final String DANHMUC             = "DANHMUC";
    public static final String ADD_DANHMUC_WEB     = "ADD_DANHMUC_WEB";
    public static final String DELE_BH1D1_WEB      = "DELE_BH1D1_WEB";
    public static final String DELE_BHHT_WEB       = "DELE_BHHT_WEB";
    public static final String DELE_BHDLK_WEB      = "DELE_BHDLK_WEB";
    public static final String DELE_BHDDT_WEB      = "DELE_BHDDT_WEB";
    public static final String DELE_BHCXL_WEB      = "DELE_BHCXL_WEB";
    public static final String MASTER              = "MASTER";

    // link Google Script
    public static final String LINK_SCRIPT         = "https://script.google.com/macros/s/";
    // link NVL
    public static final String SCRIPT_UPDATE_NO_NVL    = LINK_SCRIPT + "AKfycbwteCZh8rNyU2-6Hi2dmFSKDkhlXWrZCe97rxoySgNA-diJyeNq/exec";
    public static final String SCRIPT_KHOANCHI_NVL     = LINK_SCRIPT + "AKfycbwqiwY-EcnnA0qvMowimdWaQJL2dmKllqpuXC5cIjTcCJ1W5v0/exec";
    public static final String SCRIPT_PHICOD_NVL       = LINK_SCRIPT + "AKfycbwYqQW-QAhxmtFK36a7zz5bdFslTts7RavaIxG7WUN3LymLzaB5/exec";
    public static final String SCRIPT_TIENTRAVE_NVL    = LINK_SCRIPT + "AKfycbxMNVxNxxQegcOq04zRuAqKwSqRls0i_cGZ3ckSMy7vEjUwIw0/exec";
    //public static final String SCRIPT_XUATHANG_NVL     = LINK_SCRIPT + "AKfycbxvWoPMt1SdiuIXzSZEM8j4aaHoH9lLGUc0M64vBSrNK60O-wM/exec";
    public static final String SCRIPT_KIEMKHO_NVL      = LINK_SCRIPT + "AKfycbzDXojfyStMUnCM4kqG7XOWzgWEgN0k0D3rXLwaQbXber7CtUs/exec";
    public static final String SCRIPT_KHUYENMAI_NVL    = LINK_SCRIPT + "AKfycbx-SLcIVuQixDZ-n5pmFGIrjFZux9iIl9hunwGdqna_rMZ9lFI/exec";
    public static final String SCRIPT_BANHANG_NVL      = LINK_SCRIPT + "AKfycbycEFBmH7gNRt70F6K9u5nycej_Tt4s4BJHDqQ0lmHCI9IbdBkm/exec";
    public static final String SCRIPT_BC_KK_NVL        = LINK_SCRIPT + "AKfycbzJhrsk7bRmzusNOCt9FPC0HtGpngjl1TLcIr1vsjMAkYvZfOaH/exec";
    public static final String SCRIPT_CREATEDTABLE_NVL = LINK_SCRIPT + "AKfycbzg_j3uTnZ35KqVm1gjUrgJAXwnvpkGlDsKEZSq332tW9kt_xg/exec";
    public static final String SCRIPT_BH_DDT_NVL       = LINK_SCRIPT + "AKfycbzCaKmPPe41LQmz5WxCokyc6otAjLKIxmLRngMz3NtMTYre5Nle/exec";
    public static final String SCRIPT_BH_CXL_NVL       = LINK_SCRIPT + "AKfycbwKVYH32Z627O3X1_06anwMsJUXOTsYaLvyQ39TrPFa-tARDw4R/exec";
    public static final String SCRIPT_BH_HT_NVL        = LINK_SCRIPT + "AKfycbwGtxm48MgPYzsagGOWjS0HliSCAEr_ODi0hNzhecjk2mvc98I/exec";
    public static final String SCRIPT_BH_1DOI1_NVL     = LINK_SCRIPT + "AKfycbyLeTwQKlRCUsN3mNWf3T7a4fBRJexVNyIaeu3B6ws_pp6oTcE/exec";
    public static final String SCRIPT_BH_DLK_NVL       = LINK_SCRIPT + "AKfycbw_Y1gJhdOWjeQGqj0UHoz6N2FbxmUXI0H3jqW83vOfe4l0BHG3/exec";

    //link SOL
    public static final String SCRIPT_UPDATE_NO_SOL    = LINK_SCRIPT + "AKfycbwlZRWmxnvgc05MfdFhej9uQl_dyLedDkiCIdvfageGkDMZqm64/exec";
    public static final String SCRIPT_KHOANCHI_SOL     = LINK_SCRIPT + "AKfycbyKpyAXBNzpZe43os6ql8BTdZyyiaFEwSZKc9ybmZexfpBbVkYB/exec";
    public static final String SCRIPT_PHICOD_SOL       = LINK_SCRIPT + "AKfycbyoeoJMI7wGnkTREzy0PFGlz6xJF1U8NULjJF788p6mKfATGsY/exec";
    public static final String SCRIPT_TIENTRAVE_SOL    = LINK_SCRIPT + "AKfycbx60k_wFSAYX5hCP9gNxP6ggi6F5ogCY9BreIZw8vPcaTI5zOA/exec";
    public static final String SCRIPT_KHUYENMAI_SOL    = LINK_SCRIPT + "AKfycbyMJHml5Ni-ojwd_sdVSk0gzhCjPnqBuN6aBOziHy59nupHbzU/exec";
    public static final String SCRIPT_BANHANG_SOL      = LINK_SCRIPT + "AKfycbwWCTvKS4Ywje_jMiD5qtLBpMOcehMkfeoWuSqG9PqUdYryI50/exec";
    public static final String SCRIPT_BC_KK_SOL        = LINK_SCRIPT + "AKfycbxErQQNYo5FbDVSXyWcysvmSochHSccZ_OUoB2EHgSZTISX-ss/exec";
    public static final String SCRIPT_CREATEDTABLE_SOL = LINK_SCRIPT + "AKfycbzg_j3uTnZ35KqVm1gjUrgJAXwnvpkGlDsKEZSq332tW9kt_xg/exec";
    public static final String SCRIPT_BH_DDT_SOL       = LINK_SCRIPT + "AKfycbwDB-Uu9_WENiHx9sZRBc_-t6cTMzQFWW8fyi4cm8zDvaO7SyK5/exec";
    public static final String SCRIPT_BH_CXL_SOL       = LINK_SCRIPT + "AKfycby4S3zm0OOXlOJEzv7H7WJT2zaAkXiWSQ9LKjJFKJ7LYaaR2K8/exec";
    public static final String SCRIPT_BH_HT_SOL        = LINK_SCRIPT + "AKfycbwVa7VR2NaHMfYVn5bUmtCeHY14R7Ya35T7kml4UjNyR01F2bA/exec";
    public static final String SCRIPT_BH_1DOI1_SOL     = LINK_SCRIPT + "AKfycbzrdDSV8Lt52MGD2m70kZPTujLjOTql5PW-1FAqrfaIpUEP9tY/exec";
    public static final String SCRIPT_BH_DLK_SOL       = LINK_SCRIPT + "AKfycbz5O8TlY5hPpshMuEx3Kr1OIIhuMNii69TyULp2Cuu_xRLp2Dnc/exec";

    public static int getDrawable(Context pContext, String pString){
        return pContext.getResources().getIdentifier(pString, "drawable", pContext.getPackageName());
    }

    public static String getSCRIPT_BH_DLK(String chinhanh) {
        String cipt = "";
        if (chinhanh.equals(CN_NVL)){
            cipt = SCRIPT_BH_DLK_NVL;
        } else if (chinhanh.equals(CN_SOL)){
            cipt = SCRIPT_BH_DLK_SOL;
        }
        return cipt;
    }

    public static String getSCRIPT_BH_1DOI1(String chinhanh) {
        String cipt = "";
        if (chinhanh.equals(CN_NVL)){
            cipt = SCRIPT_BH_1DOI1_NVL;
        } else if (chinhanh.equals(CN_SOL)){
            cipt = SCRIPT_BH_1DOI1_SOL;
        }
        return cipt;
    }

    public static String getSCRIPT_BH_HT(String chinhanh) {
        String cipt = "";
        if (chinhanh.equals(CN_NVL)){
            cipt = SCRIPT_BH_HT_NVL;
        } else if (chinhanh.equals(CN_SOL)){
            cipt = SCRIPT_BH_HT_SOL;
        }
        return cipt;
    }

    public static String getSCRIPT_BH_CXL(String chinhanh) {
        String cipt = "";
        if (chinhanh.equals(CN_NVL)){
            cipt = SCRIPT_BH_CXL_NVL;
        } else if (chinhanh.equals(CN_SOL)){
            cipt = SCRIPT_BH_CXL_SOL;
        }
        return cipt;
    }

    public static String getSCRIPT_BH_DDT(String chinhanh) {
        String cipt = "";
        if (chinhanh.equals(CN_NVL)){
            cipt = SCRIPT_BH_DDT_NVL;
        } else if (chinhanh.equals(CN_SOL)){
            cipt = SCRIPT_BH_DDT_SOL;
        }
        return cipt;
    }

    public static String getSCRIPT_CREATEDTABLE(String chinhanh) {
        String cipt = "";
        if (chinhanh.equals(CN_NVL)){
            cipt = SCRIPT_CREATEDTABLE_NVL;
        } else if (chinhanh.equals(CN_SOL)){
            cipt = SCRIPT_CREATEDTABLE_SOL;
        }
        return cipt;
    }

    public static String getSCRIPT_BC_KK(String chinhanh) {
        String cipt = "";
        if (chinhanh.equals(CN_NVL)){
            cipt = SCRIPT_BC_KK_NVL;
        } else if (chinhanh.equals(CN_SOL)){
            cipt = SCRIPT_BC_KK_SOL;
        }
        return cipt;
    }

    public static String getSCRIPT_BANHANG(String chinhanh) {
        String cipt = "";
        if (chinhanh.equals(CN_NVL)){
            cipt = SCRIPT_BANHANG_NVL;
        } else if (chinhanh.equals(CN_SOL)){
            cipt = SCRIPT_BANHANG_SOL;
        }
        return cipt;
    }

    public static String getSCRIPT_KHUYENMAI(String chinhanh) {
        String cipt = "";
        if (chinhanh.equals(CN_NVL)){
            cipt = SCRIPT_KHUYENMAI_NVL;
        } else if (chinhanh.equals(CN_SOL)){
            cipt = SCRIPT_KHUYENMAI_SOL;
        }
        return cipt;
    }

    public static String getSCRIPT_TIENTRAVE(String chinhanh) {
        String cipt = "";
        if (chinhanh.equals(CN_NVL)){
            cipt = SCRIPT_TIENTRAVE_NVL;
        } else if (chinhanh.equals(CN_SOL)){
            cipt = SCRIPT_TIENTRAVE_SOL;
        }
        return cipt;
    }
    public static String getSCRIPT_PHICOD(String chinhanh) {
        String cipt = "";
        if (chinhanh.equals(CN_NVL)){
            cipt = SCRIPT_PHICOD_NVL;
        } else if (chinhanh.equals(CN_SOL)){
            cipt = SCRIPT_PHICOD_SOL;
        }
        return cipt;
    }

    public static String getSCRIPT_KHOANCHI(String chinhanh) {
        String cipt = "";
        if (chinhanh.equals(CN_NVL)){
            cipt = SCRIPT_KHOANCHI_NVL;
        } else if (chinhanh.equals(CN_SOL)){
            cipt = SCRIPT_KHOANCHI_SOL;
        }
        return cipt;
    }

    public static String getSCRIPT_UPDATE_NO(String chinhanh) {
        String cipt = "";
        if (chinhanh.equals(CN_NVL)){
            cipt = SCRIPT_UPDATE_NO_NVL;
        } else if (chinhanh.equals(CN_SOL)){
            cipt = SCRIPT_UPDATE_NO_SOL;
        }
        return cipt;
    }

    public static final String setMoney(int amount){
        String number = NumberFormat.getNumberInstance(Locale.US).format(amount);
        String formatnumber = number.replace(",",".");
        return formatnumber+" đ";
    }

    public static final String setMoneyFloat(float amount){
        String number = NumberFormat.getNumberInstance(Locale.US).format(amount);
        String formatnumber = number.replace(",",".");
        return formatnumber+" đ";
    }

    public static final String MaDonhang() {
        DateFormat dateFormat = new SimpleDateFormat("ddMMyyyyHHmmss");
        String date = dateFormat.format(new Date());
        return date+RandomMa();
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

    public static final String getCalam(String chinhanh) {
        String ca, cas = ""; int gio = 0;
        int hours = new Time(System.currentTimeMillis()).getHours();
        int phut = new Time(System.currentTimeMillis()).getMinutes();
        if (chinhanh.equals(CN_SOL)){
            gio = GIOHETCASOL;
            if (hours < gio){
                cas = CA_SANG;
            } else {
                cas = CA_CHIEU;
            }
        } else if (chinhanh.equals(CN_NVL)){
            if (hours < 14){
                cas = CA_SANG;
            } else if (hours >= 15){
                cas = CA_CHIEU;
            } else {
                if (phut < 31){
                    cas = CA_SANG;
                } else {
                    cas = CA_CHIEU;
                }
            }
        } else if (chinhanh.equals("Chi nhánh Demo")){
            if (hours < 14){
                cas = CA_SANG;
            } else if (hours >= 15){
                cas = CA_CHIEU;
            } else {
                if (phut < 31){
                    cas = CA_SANG;
                } else {
                    cas = CA_CHIEU;
                }
            }
        }
        ca = cas;
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

    public static final String RandomMa() {
        int helloLength = RAND.length;
        String magiamgia = "";
        for (int i = 0; i < 1; i++){
            String hello = RAND[RANDOM.nextInt(helloLength)];
            magiamgia += hello;
        }
        return magiamgia;
    }

    public static final String TaoMa() {
        int helloLength = HELLOS.length;
        String magiamgia = "";
        for (int i = 0; i < SOMA_GIAMGIA; i++){
            String hello = HELLOS[RANDOM.nextInt(helloLength)];
            magiamgia += hello;
        }
        return magiamgia;
    }

    public static final String bodautiengviet(String s){
        if(!TextUtils.isEmpty(s)){
            s = s.replaceAll( "à|á|ạ|ả|ã|â|ầ|ấ|ậ|ẩ|ẫ|ă|ằ|ắ|ặ|ẳ|ẵ", "a");
            s = s.replaceAll( "è|é|ẹ|ẻ|ẽ|ê|ề|ế|ệ|ể|ễ", "e");
            s = s.replaceAll( "ì|í|ị|ỉ|ĩ", "i");
            s = s.replaceAll( "ò|ó|ọ|ỏ|õ|ô|ồ|ố|ộ|ổ|ỗ|ơ|ờ|ớ|ợ|ở|ỡ", "o");
            s = s.replaceAll( "ù|ú|ụ|ủ|ũ|ư|ừ|ứ|ự|ử|ữ", "u");
            s = s.replaceAll( "ỳ|ý|ỵ|ỷ|ỹ", "y");
            s = s.replaceAll( "đ", "d");
            s = s.replaceAll( "'|;", "");
        }
        return s;
    }

    public static final String giaimagiavon(String s){
        if(!TextUtils.isEmpty(s)){
            s = s.replaceAll( "`", 1+"");
            s = s.replaceAll( "&", 2+"");
            s = s.replaceAll( "/$", 3+"");
            s = s.replaceAll( "!", 4+"");
            s = s.replaceAll( "@", 5+"");
            s = s.replaceAll( "%", 6+"");
            s = s.replaceAll( "_", 7+"");
            s = s.replaceAll( "A", 8+"");
            s = s.replaceAll( "~", 9+"");
            s = s.replaceAll( "#", 0+"");
        }
        return s;
    }

    public static final String mahoagiavon(String s){
        if(!TextUtils.isEmpty(s)){
            s = s.replaceAll( 1+"", "`");
            s = s.replaceAll( 2+"", "&");
            s = s.replaceAll( 3+"", "$");
            s = s.replaceAll( 4+"", "!");
            s = s.replaceAll( 5+"", "@");
            s = s.replaceAll( 6+"", "%");
            s = s.replaceAll( 7+"", "_");
            s = s.replaceAll( 8+"", "A");
            s = s.replaceAll( 9+"", "~");
            s = s.replaceAll( 0+"", "#");
        }
        return s;
    }

    public static final String trimChinhanh(String s){
        if(!TextUtils.isEmpty(s)){
            return s.substring(10);
        }
        return s;
    }

    public static final String sothutu(int so){
        if(so < 10){
            return "0"+so;
        }
        return ""+so;
    }

    public static String trimTextGio(String s, int maxLength){
        if(!TextUtils.isEmpty(s)){
            if(s.length() >= maxLength){
                return s.substring(0, maxLength);
            } else {
                return s;
            }
        }
        return s;
    }

    public static int SoSanpham(ArrayList<Order> reportList, String ma) {
        int tong = 0;
        for (int i = 0; i < reportList.size(); i++){
            if (reportList.get(i).getMaNhanvien().equals(ma)){
                tong++;
            }
        }
        return tong;
    }
    public static int SoKhachhang(ArrayList<Customer> khachhang, String ma) {
        int tong = 0;
        for (int i = 0; i < khachhang.size(); i++){
            if (khachhang.get(i).getMaNhanvien().equals(ma)){
                tong++;
            }
        }
        return tong;
    }
    public static int DoanhthuCount(ArrayList<Order> reportList, String ma) {
        int tong = 0;
        for (int i = 0; i < reportList.size(); i++){
            if (reportList.get(i).getMaNhanvien().equals(ma)){
                tong += (Integer.valueOf(reportList.get(i).getGiaSanpham()));
            }
        }
        return tong;
    }
    public static int sosanhUser(ArrayList<User> user, String ma, String ten) {
        int result = -1;
        if (user.size() != 0){
            for (int i = 0; i < user.size(); i++){
                if (user.get(i).getMa().equals(ma) && user.get(i).getShortName().equals(ten)){
                    result = i;
                }
            }
        }
        return result;
    }
    public static int GiamgiaCount(ArrayList<Order> reportList, String ma) {
        int giamgia = 0;
        ArrayList<Order> listInt = new ArrayList<>();
        for (int i = 0; i < reportList.size(); i++) {
            if (reportList.get(i).getMaNhanvien().equals(ma)){
                int sosanh = ssgiamgia(listInt, reportList.get(i).getMaDonhang());
                if ( sosanh == -1){
                    listInt.add(reportList.get(i));
                    giamgia = Integer.parseInt(giamgia + reportList.get(i).getGiamgia());
                }
            }
        }
        return giamgia;
    }

    private static int ssgiamgia(ArrayList<Order> listInt, String maDonhang) {
        int result = -1;
        if (listInt.size() != 0){
            for (int i = 0; i < listInt.size(); i++){
                if (listInt.get(i).getMaDonhang().equals(maDonhang)){
                    result = i;
                }
            }
        }
        return result;
    }

    public static int sosanhCustomer(ArrayList<Customer> order_h, String orderName, String ma) {
        int result = -1;
        if (order_h.size() != 0){
            for (int i = 0; i < order_h.size(); i++){
                if (order_h.get(i).getTenCus().equals(orderName) && order_h.get(i).getOrderCus().equals(ma)){
                    result = i;
                }
            }
        }
        return result;
    }
    public static void sortDoanhthu(ArrayList<ReportSales> list) {
        Collections.sort(list, new Comparator<ReportSales>() {
            @Override
            public int compare(ReportSales lhs, ReportSales rhs) {
                return ((Float)rhs.getDoanhthu()).compareTo(lhs.getDoanhthu());
            }
        });
    }
    public static void sortSoKH(ArrayList<ReportSales> list) {
        Collections.sort(list, new Comparator<ReportSales>() {
            @Override
            public int compare(ReportSales lhs, ReportSales rhs) {
                return ((Float)rhs.getSoKhachhang()).compareTo(lhs.getSoKhachhang());
            }
        });
    }
    public static void sortSoSP(ArrayList<ReportSales> list) {
        Collections.sort(list, new Comparator<ReportSales>() {
            @Override
            public int compare(ReportSales lhs, ReportSales rhs) {
                return ((Float)rhs.getSoSanpham()).compareTo(lhs.getSoSanpham());
            }
        });
    }
    public static void sortDttkh(ArrayList<ReportSales> list) {
        Collections.sort(list, new Comparator<ReportSales>() {
            @Override
            public int compare(ReportSales lhs, ReportSales rhs) {
                return ((Float)rhs.getDttkh()).compareTo(lhs.getDttkh());
            }
        });
    }
    public static void sortDttsp(ArrayList<ReportSales> list) {
        Collections.sort(list, new Comparator<ReportSales>() {
            @Override
            public int compare(ReportSales lhs, ReportSales rhs) {
                return ((Float)rhs.getDttsp()).compareTo(lhs.getDttsp());
            }
        });
    }
    public static void sortSptkh(ArrayList<ReportSales> list) {
        Collections.sort(list, new Comparator<ReportSales>() {
            @Override
            public int compare(ReportSales lhs, ReportSales rhs) {
                return ((Float)rhs.getSptkh()).compareTo(lhs.getSptkh());
            }
        });
    }

    public static int trimgio(String s) {
        if(!TextUtils.isEmpty(s)){
            if(s.length() >= 2){
                return Integer.valueOf(s.substring(0, 2));
            } else {
                return Integer.valueOf(s);
            }
        }
        return Integer.valueOf(s);
    }

    public static String getDauthang() {
        DateFormat dateFormat = new SimpleDateFormat("-MM-yyyy");
        String date = dateFormat.format(new Date());
        return "01"+date;
    }

    public static final String getTimeNowSL() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        String date = dateFormat.format(new Date());
        return date+":0";
    }

    public static Integer getTotalSoca(Integer intsoca, Float inttangca, Integer intchitieu) {
        int result = 0;
        result = (int) ((intsoca + inttangca/7.5)*intchitieu);
        return result;
    }

    public static boolean checkMavach(String ma, String chinhanh) {
        boolean result = false;
        if (chinhanh.equals(CN_SOL) && ma.length() == 8){
            result = true;
        }
        if (chinhanh.equals(CN_NVL) && ma.length() < 7){
            result = true;
        }
        return result;
    }

    public static final String GetUser(Context c, final String manhanvien) {
        final ArrayList<User> usernames = new ArrayList<User>();
        final String[] link = {""};
        RequestQueue requestQueue = Volley.newRequestQueue(c);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Keys.MAIN_LINKAVATAR+"?manhanvien="+manhanvien, null,
                new Response.Listener<JSONArray>() {

                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                usernames.add(new User(
                                        object.getInt("id"),
                                        object.getString("ma_user"),
                                        object.getString("ten"),
                                        object.getString("shortName"),
                                        object.getString("username"),
                                        object.getString("password"),
                                        object.getString("level"),
                                        object.getString("chucdanh"),
                                        object.getString("trangthai"),
                                        object.getString("created"),
                                        object.getString("updated"),
                                        object.getString("img")
                                ));

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        return link[0];
    }

    public static final class FixedHoloDatePickerDialog extends DatePickerDialog {
        public FixedHoloDatePickerDialog(Context context, OnDateSetListener callBack,
                                          int year, int monthOfYear, int dayOfMonth) {
            super(context, callBack, year, monthOfYear, dayOfMonth);
            if (Build.VERSION.SDK_INT == 24) {
                try {
                    final Field field = this.findField(
                            DatePickerDialog.class,
                            DatePicker.class,
                            "mDatePicker"
                    );

                    final DatePicker datePicker = (DatePicker) field.get(this);
                    final Class<?> delegateClass = Class.forName(
                            "android.widget.DatePicker$DatePickerDelegate"
                    );
                    final Field delegateField = this.findField(
                            DatePicker.class,
                            delegateClass,
                            "mDelegate"
                    );

                    final Object delegate = delegateField.get(datePicker);
                    final Class<?> spinnerDelegateClass = Class.forName(
                            "android.widget.DatePickerSpinnerDelegate"
                    );

                    if (delegate.getClass() != spinnerDelegateClass) {
                        delegateField.set(datePicker, null);
                        datePicker.removeAllViews();

                        final Constructor spinnerDelegateConstructor =
                                spinnerDelegateClass.getDeclaredConstructor(
                                        DatePicker.class,
                                        Context.class,
                                        AttributeSet.class,
                                        int.class,
                                        int.class
                                );
                        spinnerDelegateConstructor.setAccessible(true);

                        final Object spinnerDelegate = spinnerDelegateConstructor.newInstance(
                                datePicker,
                                context,
                                null,
                                android.R.attr.datePickerStyle,
                                0
                        );
                        delegateField.set(datePicker, spinnerDelegate);

                        datePicker.init(year, monthOfYear, dayOfMonth, this);
                        datePicker.setCalendarViewShown(false);
                        datePicker.setSpinnersShown(true);
                    }
                } catch (Exception e) { /* Do nothing */ }
            }
        }

        public Field findField(Class objectClass, Class fieldClass, String expectedName) {
            try {
                final Field field = objectClass.getDeclaredField(expectedName);
                field.setAccessible(true);
                return field;
            } catch (NoSuchFieldException e) { /* Ignore */ }

            for (final Field field : objectClass.getDeclaredFields()) {
                if (field.getType() == fieldClass) {
                    field.setAccessible(true);
                    return field;
                }
            }

            return null;
        }
    }

}
