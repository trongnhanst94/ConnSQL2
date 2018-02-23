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
import com.example.windows10gamer.connsql.Object.BHCXL;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main_Info_BHCXL extends AppCompatActivity {
    String maBH, dateToday, gio, timeToday, chinhanhToday, tenNVToday, maNVToday, maOrder,thoigianhen,maNVxuly,tenNVxuly, date, time, chinhanh, tenNV, maNV, ten, ma, baohanh, nguon, ngaynhap, von, gia, ghichuOrder, tenKH, sdtKH, ghichuKH, gtConlai, lydo;
    TextView tvCxlMaOrder,tvCxlDate,tvCxlTime,tvCxlMaNV,tvCxlTenNV,tvCxlGhichuOrder,tvCxlTenKH,tvthoigianhen,tvmaNVxuly,tvtenNVxuly,
            tvCxlSdtKH,tvCxlGhichuKH,tvCxlTenNVNhan,tvCxlMaNVNhan,tvCxlDatetoday,tvCxlTimetoday,tvChinhanhHT,tvChinhanhHTOrder, tvCxlMaBH ;
    ArrayList<BHCXL> BHCXL = new ArrayList<>();
    ListView lv;
    ArrayList<Sanpham_gio> list = new ArrayList<>();
    Adapter_Info_Order adapter;
    ImageView ivAvatar, ivAvatar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info_bhcxl);
        Anhxa();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoBHCXL");
        BHCXL = bundle.getParcelableArrayList("listBHCXL");
        maBH = BHCXL.get(0).getMaBH();
        dateToday = BHCXL.get(0).getDateToday();
        timeToday = BHCXL.get(0).getTimeToday();
        chinhanhToday = BHCXL.get(0).getChinhanhToday();
        maNVToday = BHCXL.get(0).getMaNVToday();
        tenNVToday = BHCXL.get(0).getTenNVToday();
        maOrder = BHCXL.get(0).getMaOrder();
        date = BHCXL.get(0).getDate();
        time = BHCXL.get(0).getTime();
        chinhanh = BHCXL.get(0).getChinhanh();
        tenNV = BHCXL.get(0).getTenNV();
        maNV = BHCXL.get(0).getMaNV();
        ten = BHCXL.get(0).getTen();
        ma = BHCXL.get(0).getMa();
        gio = BHCXL.get(0).getGio();
        baohanh = BHCXL.get(0).getBaohanh();
        nguon = BHCXL.get(0).getNguon();
        ngaynhap = BHCXL.get(0).getNgaynhap();
        von = BHCXL.get(0).getVon();
        gia = BHCXL.get(0).getGia();
        ghichuOrder = BHCXL.get(0).getGhichuOrder();
        tenKH = BHCXL.get(0).getTenKH();
        sdtKH = BHCXL.get(0).getSdtKH();
        sdtKH = BHCXL.get(0).getSdtKH();
        ghichuKH = BHCXL.get(0).getGhichuKH();
        thoigianhen = BHCXL.get(0).getThoigianhen();
        maNVxuly = BHCXL.get(0).getMaNVxuly();
        tenNVxuly = BHCXL.get(0).getTenNVxuly();
        tvCxlMaBH.setText("Mã BH: "+maBH);
        tvCxlDatetoday.setText(dateToday);
        tvCxlTimetoday.setText(timeToday);
        tvChinhanhHT.setText(chinhanhToday);
        tvCxlTenNVNhan.setText("Mã NV bảo hành: "+ maNVToday);
        tvCxlMaNVNhan.setText("Tên NV bảo hành: " + tenNVToday);
        tvCxlMaOrder.setText("Mã đơn hàng: " + maOrder);
        tvCxlDate.setText(date);
        tvCxlTime.setText(time);
        tvChinhanhHTOrder.setText(chinhanh);
        tvCxlMaNV.setText("Mã số: " + maNV);
        tvCxlTenNV.setText("Tên nhân viên: " + tenNV);
        list.add(new Sanpham_gio(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia));
        adapter = new Adapter_Info_Order(Main_Info_BHCXL.this, list);
        lv.setAdapter(adapter);
        if (ghichuKH.equals("")) {
            tvCxlGhichuKH.setVisibility(View.GONE);
        } else {
            tvCxlGhichuKH.setText("Ghi chú KH: " + ghichuKH);
        }
        if (ghichuOrder.equals("")) {
            tvCxlGhichuOrder.setVisibility(View.GONE);
        } else {
            tvCxlGhichuOrder.setText("Ghi chú đơn hàng: " + ghichuOrder);
        }
        tvCxlTenKH.setText("Tên KH: " + tenKH);
        tvCxlSdtKH.setText("SĐT KH: " + sdtKH);
        tvthoigianhen.setText(thoigianhen);
        tvmaNVxuly.setText("Mã số: " +maNVxuly);
        tvtenNVxuly.setText("Tên nhân viên: " +tenNVxuly);
        GetUser(Main_Info_BHCXL.this, maNVToday);
        GetUser2(Main_Info_BHCXL.this, maNV);
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
        tvthoigianhen = findViewById(R.id.tvthoigianhen);
        tvmaNVxuly = findViewById(R.id.tvmaNVxuly);
        tvtenNVxuly = findViewById(R.id.tvtenNVxuly);
        tvCxlMaOrder = findViewById(R.id.tvCxlMaOrder);
        tvCxlDate = findViewById(R.id.tvCxlDate);
        tvCxlTime = findViewById(R.id.tvCxlTime);
        tvCxlDatetoday = findViewById(R.id.tvCxlDatetoday);
        tvCxlTimetoday = findViewById(R.id.tvCxlTimetoday);
        tvCxlMaNV = findViewById(R.id.tvCxlMaNV);
        tvCxlTenNV = findViewById(R.id.tvCxlTenNV);
        tvCxlGhichuOrder = findViewById(R.id.tvCxlGhichu);
        tvChinhanhHT = findViewById(R.id.tvChinhanhHT);
        tvChinhanhHTOrder = findViewById(R.id.tvChinhanhHTOrder);
        tvCxlTenKH = findViewById(R.id.tvCxlTenKH);
        tvCxlSdtKH = findViewById(R.id.tvCxlSdtKH);
        lv = findViewById(R.id.lvBHCXL);
        tvCxlGhichuKH = findViewById(R.id.tvCxlGhichuKH);
        tvCxlTenNVNhan = findViewById(R.id.tvCxlTenNVNhan);
        tvCxlMaNVNhan = findViewById(R.id.tvCxlMaNVNhan);
        tvCxlMaBH = findViewById(R.id.tvCxlMaBH);
    }
}
