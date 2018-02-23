package com.example.windows10gamer.connsql.Bao_Hanh;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order;
import com.example.windows10gamer.connsql.Object.BHHT;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main_Info_BHHT extends AppCompatActivity {
    String maBH, dateToday, gio, phitrahang, timeToday, chinhanhToday, tenNVToday, maNVToday, maOrder, date, time, chinhanh, tenNV, maNV, ten, ma, baohanh, nguon, ngaynhap, von, gia, ghichuOrder, tenKH, sdtKH, ghichuKH, gtConlai, lydo;
    TextView tvHtMaOrder,tvHtDate,tvHtTime,tvHtMaNV,tvHtTenNV,tvHtGhichuOrder,tvHtTenKH,tvgtConlai,tvlydoHT,tvphitrahang,
            tvHtSdtKH,tvHtGhichuKH,tvHtTenNVNhan,tvHtMaNVNhan,tvHtDatetoday,tvHtTimetoday,tvChinhanhHT,tvChinhanhHTOrder, tvHtMaBH ;
    ArrayList<BHHT> BHHT = new ArrayList<>();
    ListView lv;
    ArrayList<Sanpham_gio> list = new ArrayList<>();
    Adapter_Info_Order adapter;
    ImageView ivAvatar, ivAvatar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info_bhht);
        Anhxa();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoBHHT");
        BHHT = bundle.getParcelableArrayList("listBHHT");
        maBH = BHHT.get(0).getMaBH();
        dateToday = BHHT.get(0).getDateToday();
        timeToday = BHHT.get(0).getTimeToday();
        chinhanhToday = BHHT.get(0).getChinhanhToday();
        maNVToday = BHHT.get(0).getMaNVToday();
        tenNVToday = BHHT.get(0).getTenNVToday();
        maOrder = BHHT.get(0).getMaOrder();
        date = BHHT.get(0).getDate();
        time = BHHT.get(0).getTime();
        chinhanh = BHHT.get(0).getChinhanh();
        tenNV = BHHT.get(0).getTenNV();
        maNV = BHHT.get(0).getMaNV();
        ten = BHHT.get(0).getTen();
        ma = BHHT.get(0).getMa();
        gio = BHHT.get(0).getGio();
        baohanh = BHHT.get(0).getBaohanh();
        nguon = BHHT.get(0).getNguon();
        ngaynhap = BHHT.get(0).getNgaynhap();
        von = BHHT.get(0).getVon();
        gia = BHHT.get(0).getGia();
        ghichuOrder = BHHT.get(0).getGhichuOrder();
        tenKH = BHHT.get(0).getTenKH();
        sdtKH = BHHT.get(0).getSdtKH();
        sdtKH = BHHT.get(0).getSdtKH();
        ghichuKH = BHHT.get(0).getGhichuKH();
        gtConlai = BHHT.get(0).getGtConlai();
        phitrahang = BHHT.get(0).getPhitrahang();
        lydo = BHHT.get(0).getLydo();
        tvHtMaBH.setText("Mã BH: "+maBH);
        tvHtDatetoday.setText(dateToday);
        tvHtTimetoday.setText(timeToday);
        tvChinhanhHT.setText(chinhanhToday);
        tvHtTenNVNhan.setText("Mã NV bảo hành: "+ maNVToday);
        tvHtMaNVNhan.setText("Tên NV bảo hành: " + tenNVToday);
        tvHtMaOrder.setText("Mã đơn hàng: " + maOrder);
        tvHtDate.setText(date);
        tvHtTime.setText(time);
        tvChinhanhHTOrder.setText(chinhanh);
        tvHtMaNV.setText("Mã số: " + maNV);
        tvHtTenNV.setText("Tên nhân viên: " + tenNV);
        list.add(new Sanpham_gio(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia));
        adapter = new Adapter_Info_Order(Main_Info_BHHT.this, list);
        lv.setAdapter(adapter);
        if (ghichuKH.equals("")) {
            tvHtGhichuKH.setVisibility(View.GONE);
        } else {
            tvHtGhichuKH.setText("Ghi chú KH: " + ghichuKH);
        }
        if (ghichuOrder.equals("")) {
            tvHtGhichuOrder.setVisibility(View.GONE);
        } else {
            tvHtGhichuOrder.setText("Ghi chú đơn hàng: " + ghichuOrder);
        }
        tvHtTenKH.setText("Tên KH: " + tenKH);
        tvHtSdtKH.setText("SĐT KH: " + sdtKH);
        tvlydoHT.setText("Lý do: " + lydo);
        tvphitrahang.setText(Keys.setMoney(Integer.parseInt(phitrahang)));
        tvgtConlai.setText(Keys.setMoney(Integer.parseInt(gtConlai)));
        GetUser(Main_Info_BHHT.this, maNVToday);
        GetUser2(Main_Info_BHHT.this, maNV);
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

    private void GetUser2(final Context c, final String manhanvien) {
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
                                Glide.with(c).load(object.getString("img")).override(300,300).fitCenter().into(ivAvatar2);
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

    private void Anhxa() {
        ivAvatar2 = findViewById(R.id.ivAvatar2);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvgtConlai = findViewById(R.id.tvgtConlai);
        tvphitrahang = findViewById(R.id.tvphitrahang);
        tvlydoHT = findViewById(R.id.tvlydoBHHT);
        tvHtMaOrder = findViewById(R.id.tvHtMaOrder);
        tvHtDate = findViewById(R.id.tvHtDate);
        tvHtTime = findViewById(R.id.tvHtTime);
        tvHtDatetoday = findViewById(R.id.tvHtDatetoday);
        tvHtTimetoday = findViewById(R.id.tvHtTimetoday);
        tvHtMaNV = findViewById(R.id.tvHtMaNV);
        tvHtTenNV = findViewById(R.id.tvHtTenNV);
        tvHtGhichuOrder = findViewById(R.id.tvHtGhichu);
        tvChinhanhHT = findViewById(R.id.tvChinhanhHT);
        tvChinhanhHTOrder = findViewById(R.id.tvChinhanhHTOrder);
        tvHtTenKH = findViewById(R.id.tvHtTenKH);
        tvHtSdtKH = findViewById(R.id.tvHtSdtKH);
        lv = findViewById(R.id.lvBHHT);
        tvHtGhichuKH = findViewById(R.id.tvHtGhichuKH);
        tvHtTenNVNhan = findViewById(R.id.tvHtTenNVNhan);
        tvHtMaNVNhan = findViewById(R.id.tvHtMaNVNhan);
        tvHtMaBH = findViewById(R.id.tvHtMaBH);
    }
}
