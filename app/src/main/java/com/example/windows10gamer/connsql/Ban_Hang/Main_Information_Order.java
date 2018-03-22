package com.example.windows10gamer.connsql.Ban_Hang;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableStringBuilder;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bumptech.glide.Glide;
import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order_TD;
import com.example.windows10gamer.connsql.Adapter.Adapter_QuaTangInfo;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Object.Quatang;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.GiftList;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class Main_Information_Order extends AppCompatActivity {
    TextView tvifMaOrder;
    TextView tvifDateOrder;
    TextView tvifTimeOrder;
    TextView tvifMaNVOrder;
    TextView tvifTenNVOrder;
    TextView tvifChinhanhOrder;
    TextView tvifGhichuOrder;
    TextView tvifTenKH;
    TextView tvifSdtKH;
    TextView tvifGhichuKH;
    TextView tvifTotalOrder;
    TextView tvifgiamgia, tvifsaugiamgia;
    SwipeMenuListView lvInfoOrder;
    LinearLayout lnHide;
    GiftList lvKhuyenmai;
    ArrayList<Order> orderInfo = new ArrayList<>();
    ArrayList<Order> item = new ArrayList<>();
    ArrayList<Sanpham_gio> sanphamOrder = new ArrayList<>();
    int position, total, giamgia, saugiamgia;
    String keyMaOrder;
    Adapter_Info_Order_TD adapter;
    private ProgressDialog dialog2;
    Adapter_QuaTangInfo adapterKhuyenmai;
    private ArrayList<Quatang> arraylist = new ArrayList<>();
    CircleImageView ivAvatar;
    FloatingActionButton fab;
    LinearLayout lnAn;
    TextView tvThanhtoan;
    Button btnDuyet;
    LinearLayout lnHiddenInfo;
    private String updateTT;
    String chinhanh;
    private SharedPreferences shared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_information_order);
        lnHide = findViewById(R.id.lnHide);
        fab = findViewById(R.id.fab);
        lnAn = findViewById(R.id.lnAn);
        lnHiddenInfo = findViewById(R.id.lnHiddenInfo);
        btnDuyet = findViewById(R.id.btnDuyet);
        tvThanhtoan = findViewById(R.id.tvThanhtoan);
        lvKhuyenmai = findViewById(R.id.lvKhuyenmai);
        tvifMaOrder = findViewById(R.id.tvifMaOrder);
        tvifDateOrder = findViewById(R.id.tvifDateOrder);
        tvifTimeOrder = findViewById(R.id.tvifTimeOrder);
        tvifMaNVOrder = findViewById(R.id.tvifMaNVOrder);
        tvifTenNVOrder = findViewById(R.id.tvifTenNVOrder);
        tvifChinhanhOrder = findViewById(R.id.tvifChinhanhOrder);
        tvifGhichuOrder = findViewById(R.id.tvifGhichuOrder);
        tvifTenKH = findViewById(R.id.tvifTenKH);
        tvifSdtKH = findViewById(R.id.tvifSdtKH);
        tvifGhichuKH = findViewById(R.id.tvifGhichuKH);
        tvifTotalOrder = findViewById(R.id.tvifTotalOrder);
        tvifgiamgia = findViewById(R.id.tvifgiamgia);
        tvifsaugiamgia = findViewById(R.id.tvifsaugiamgia);
        lvInfoOrder = findViewById(R.id.lvInfoOrder);
        ivAvatar = findViewById(R.id.ivAvatar);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DataOrder");
        position  = bundle.getInt("position");
        keyMaOrder = bundle.getString("keyMaOrder");
        orderInfo = bundle.getParcelableArrayList("arrayOrder");
        for (int i = 0; i < orderInfo.size(); i++){
            if ((orderInfo.get(i).getMaDonhang()).equals(keyMaOrder)){
                item.add(orderInfo.get(i));
            }
        }
        final int[] ii = {1};
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ii[0] == 1){
                    lnAn.setVisibility(View.GONE);
                    ii[0] = 0;
                } else {
                    lnAn.setVisibility(View.VISIBLE);
                    ii[0] = 1;
                }
            }
        });
        for (int j = 0; j < item.size(); j++){
            sanphamOrder.add(new Sanpham_gio(
                    item.get(j).getGio(),
                    item.get(j).getMaSanpham(),
                    item.get(j).getTenSanpham(),
                    item.get(j).getBaohanhSanpham(),
                    item.get(j).getNguonSanpham(),
                    item.get(j).getNgaynhapSanpham(),
                    item.get(j).getVonSanpham(),
                    item.get(j).getGiaSanpham()));
            total+=Integer.parseInt(item.get(j).getGiaSanpham());
        }
        new GetData().execute();
        if (item.get(0).getThanhtoan().equals(Keys.TIENMAT)){
            tvThanhtoan.setText(item.get(0).getThanhtoan());
            btnDuyet.setEnabled(false);
            btnDuyet.setBackgroundColor(getResources().getColor(R.color.aaaaa));
        } else {
            tvThanhtoan.setText(item.get(0).getThanhtoan()+" - "+item.get(0).getNguoino());
        }
        tvifMaOrder.setText(item.get(0).getMaDonhang());
        tvifDateOrder.setText("Ngày: "+item.get(0).getNgay());
        tvifTimeOrder.setText(" Ca: "+item.get(0).getCalam());
        tvifMaNVOrder.setText("Mã số: "+item.get(0).getMaNhanvien());
        tvifChinhanhOrder.setText(item.get(0).getChinhanh());
        tvifTenNVOrder.setText("Tên nhân viên: "+item.get(0).getTenNhanvien());
        if (item.get(0).getGhichuSanpham().equals("")){
            tvifGhichuOrder.setVisibility(View.GONE);
        } else {
            tvifGhichuOrder.setText("Ghi chú đơn hàng: "+item.get(0).getGhichuSanpham());
        }
        tvifTenKH.setText("Tên khách hàng: "+item.get(0).getTenKhachhang());
        tvifSdtKH.setText("SĐT khách hàng: "+item.get(0).getSodienthoaiKhachhang());
        if (item.get(0).getGhichuKhachhang().equals("")){
            tvifGhichuKH.setVisibility(View.GONE);
        } else {
            tvifGhichuKH.setText("Ghi chú khách hàng: "+item.get(0).getGhichuKhachhang());
        }
        tvifTotalOrder.setText(Keys.setMoney(total));
        tvifgiamgia.setText("- "+ Keys.setMoney(Integer.valueOf(item.get(0).getGiamgia())));
        tvifsaugiamgia.setText(Keys.setMoney(total - Integer.valueOf(item.get(0).getGiamgia())));

        adapter = new Adapter_Info_Order_TD(Main_Information_Order.this, item);
        lvInfoOrder.setAdapter(adapter);
        GetUser(Main_Information_Order.this, item.get(0).getMaNhanvien());

        btnDuyet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(Main_Information_Order.this))
                    Connect_Internet.buildDialog(Main_Information_Order.this).show();
                else {
                    PopupMenu popup = new PopupMenu(Main_Information_Order.this, findViewById(android.R.id.content));
                    final PopupMenu popmenu = new PopupMenu(Main_Information_Order.this, btnDuyet);
                    popmenu.getMenuInflater().inflate(R.menu.menu_duyetno, popmenu.getMenu());
                    insertMenuItemIcons(Main_Information_Order.this, popmenu);
                    popmenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                        @SuppressLint("RestrictedApi")
                        public boolean onMenuItemClick(MenuItem menuItem) {
                            if (menuItem.getTitle().toString().trim().equals("Đã thanh toán")) {
                                updateTT = "Đã thanh toán";
                                new SendRequest().execute();
                                tvThanhtoan.setText(updateTT+" - "+item.get(0).getNguoino());
                            } else if (menuItem.getTitle().toString().trim().equals("Hoàn trả hàng")){
                                updateTT = "Hoàn trả hàng";
                                new SendRequest().execute();
                                tvThanhtoan.setText(updateTT+" - "+item.get(0).getNguoino());
                            }
                            return true;
                        }
                    });
                    popmenu.show();
                }
            }
        });
    }

    public class SendRequest extends AsyncTask<Void, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(Void... arg0) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main_Information_Order.this).show();
            else {
                int jIndex = 0;
                UpdateCocWeb();
                while (jIndex < item.size()){
                    putData(jIndex);
                    jIndex++;
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Toasty.success(Main_Information_Order.this, "Thành công", Toast.LENGTH_LONG, true).show();
        }
    }

    public void UpdateCocWeb(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toasty.error(Main_Information_Order.this, "Thất bại, không kết nối được Server", Toast.LENGTH_LONG, true).show();
                        } else if (response.trim().equals("success")){
                            btnDuyet.setEnabled(false);
                            btnDuyet.setBackgroundColor(getResources().getColor(R.color.aaaaa));
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main_Information_Order.this, "Lỗi "+error, Toast.LENGTH_LONG, true).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.UPDATE_GHI_NO);
                params.put("thanhtoan", updateTT);
                params.put("maDonhang", item.get(0).getMaDonhang());
                Log.e("params", params.toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public String getPostDataString(JSONObject params) throws Exception {

        StringBuilder result = new StringBuilder();
        boolean first = true;

        Iterator<String> itr = params.keys();

        while(itr.hasNext()){

            String key= itr.next();
            Object value = params.get(key);

            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));

        }
        return result.toString();
    }

    public String putData(final int j){
        try {
            // Link Script
            URL url = new URL(Keys.getSCRIPT_UPDATE_NO(chinhanh));

            // Load Json object
            JSONObject postDataParams = new JSONObject();

            postDataParams.put("salesMadonhang", item.get(0).getMaDonhang());
            postDataParams.put("salesNgay", item.get(0).getNgay());
            postDataParams.put("salesCa", item.get(0).getCalam());
            postDataParams.put("salesGio", item.get(j).getGio());
            postDataParams.put("salesChinhanh",  item.get(0).getChinhanh());
            postDataParams.put("SalesTennhanvien", item.get(0).getMaNhanvien());
            postDataParams.put("salesManhanvien", item.get(0).getTenNhanvien());
            postDataParams.put("salesMasanpham", item.get(j).getMaSanpham());
            postDataParams.put("salesTensanpham", item.get(j).getTenSanpham());
            postDataParams.put("salesBaohanhsanpham", item.get(j).getBaohanhSanpham());
            postDataParams.put("salesNguonsanpham", item.get(j).getNguonSanpham());
            postDataParams.put("salesNgaynhap", item.get(j).getNgaynhapSanpham());
            postDataParams.put("salesVonsanpham", item.get(j).getVonSanpham());
            postDataParams.put("salesGiasanpham", item.get(j).getGiaSanpham());
            postDataParams.put("salesGiamgia", item.get(0).getGiamgia());
            postDataParams.put("salesGhichusanpham", item.get(0).getGhichuSanpham());
            postDataParams.put("salesTenkhachhang", item.get(0).getTenKhachhang());
            postDataParams.put("salesSodienthoaikhachhang", item.get(0).getSodienthoaiKhachhang());
            postDataParams.put("salesGhichukhachhang", item.get(0).getGhichuKhachhang());
            postDataParams.put("salesTennhanvienbandum", item.get(0).getTenNhanvienbandum());
            postDataParams.put("salesManhanvienbandum", item.get(0).getMaNhanvienbandum());
            postDataParams.put("salesThanhtoan", updateTT);
            postDataParams.put("salesNguoino", item.get(0).getNguoino());

            Log.e("postDataParams", postDataParams.toString());

            // Kết nối HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParams));

            writer.flush();
            writer.close();
            os.close();

            int responseCode = conn.getResponseCode();
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb = new StringBuffer("");
                String line = "";

                while ((line = in.readLine()) != null) {

                    sb.append(line);
                    break;
                }
                in.close();
                return sb.toString();
            } else {
                return new String("");
            }
        } catch (Exception e) {
            return new String("");
        }
    }

    public static void insertMenuItemIcons(Context context, PopupMenu popupMenu) {
        Menu menu = popupMenu.getMenu();
        if (hasIcon(menu)) {
            for (int i = 0; i < menu.size(); i++) {
                insertMenuItemIcon(context, menu.getItem(i));
            }
        }
    }

    /**
     * @return true if the menu has at least one MenuItem with an icon.
     */
    private static boolean hasIcon(Menu menu) {
        for (int i = 0; i < menu.size(); i++) {
            if (menu.getItem(i).getIcon() != null) return true;
        }
        return false;
    }

    /**
     * Converts the given MenuItem's title into a Spannable containing both its icon and title.
     */
    private static void insertMenuItemIcon(Context context, MenuItem menuItem) {
        Drawable icon = menuItem.getIcon();

        // If there's no icon, we insert a transparent one to keep the title aligned with the items
        // which do have icons.
        if (icon == null) icon = new ColorDrawable(Color.TRANSPARENT);

        int iconSize = context.getResources().getDimensionPixelSize(R.dimen.menu_icon);
        icon.setBounds(0, 0, iconSize, iconSize);
        ImageSpan imageSpan = new ImageSpan(icon);

        // Add a space placeholder for the icon, before the title.
        SpannableStringBuilder ssb = new SpannableStringBuilder("       " + menuItem.getTitle());

        // Replace the space placeholder with the icon.
        ssb.setSpan(imageSpan, 1, 2, 0);
        menuItem.setTitle(ssb);
        // Set the icon to null just in case, on some weird devices, they've customized Android to display
        // the icon in the menu... we don't want two icons to appear.
        menuItem.setIcon(null);
    }

    private void GetUser(final Context c, final String manhanvien) {
        final ArrayList<User> usernames = new ArrayList<User>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
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
                                Glide.with(c).load(object.getString("img")).override(300,300).fitCenter().into(ivAvatar);
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
    }

    class GetData extends AsyncTask<String, Void, Void> {
        int jIndex;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog2 = new ProgressDialog(Main_Information_Order.this);
            dialog2.setTitle("Hãy chờ...");
            dialog2.setMessage("Dữ liệu đang được tải xuống");
            dialog2.setCancelable(false);
            dialog2.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_KHUYENMAI+"?MASALES="+item.get(0).getMaDonhang());
            try {
                if (jsonObject != null) {
                    arraylist.clear();
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.KHUYENMAI);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    arraylist.add(new Quatang(
                                            object.getString("maSanpham"),
                                            object.getString("tenSanpham"),
                                            object.getString("baohanhSanpham"),
                                            object.getString("nguonSanpham"),
                                            object.getString("ngaynhapSanpham"),
                                            object.getInt("vonSanpham"),
                                            object.getInt("giaSanpham"),
                                            0,
                                            0
                                    ));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(arraylist.size() > 0) {
                lnHide.setVisibility(View.VISIBLE);
                adapterKhuyenmai = new Adapter_QuaTangInfo(Main_Information_Order.this, R.layout.adapter_quatang, arraylist);
                lvKhuyenmai.setAdapter(adapterKhuyenmai);
            } else {
                lnHide.setVisibility(View.GONE);
            }
            dialog2.dismiss();
        }
    }


}
