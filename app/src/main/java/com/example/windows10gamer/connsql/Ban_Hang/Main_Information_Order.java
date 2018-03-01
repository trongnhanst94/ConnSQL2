package com.example.windows10gamer.connsql.Ban_Hang;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.bumptech.glide.Glide;
import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order_TD;
import com.example.windows10gamer.connsql.Adapter.Adapter_QuaTangInfo;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Object.Quatang;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.GiftList;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_information_order);
        lnHide = findViewById(R.id.lnHide);
        fab = findViewById(R.id.fab);
        lnAn = findViewById(R.id.lnAn);
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
