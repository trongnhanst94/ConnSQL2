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
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Object.BHDDT;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.Mylistview;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main_Info_BHDDT extends AppCompatActivity {
    String maBH, dateToday, gio, gio_moi, timeToday, chinhanhToday, tenNVToday, maNVToday, maOrder, date, time, chinhanh, tenNV, maNV, ten, ma, baohanh, nguon, ngaynhap, von, gia, ghichuOrder, tenKH, sdtKH, ghichuKH, phidoiSP, lydo, chechlech;
    TextView tvDlkMaOrder,tvDlkDate,tvDlkTime,tvDlkMaNV,tvDlkTenNV,tvDlkGhichuOrder,tvDlkTenKH,tvphidoiSP,tvlydoDLK,tvmaBHDDT,
            tvDlkSdtKH, tvDlkChenhlech, tvDlkGhichuKH,tvDlkTenNVNhan,tvDlkMaNVNhan,tvDlkDatetoday,tvDlkTimetoday, tvTongdonhang,tvChinhanhDLK,tvChinhanhDLKOrder ;
    ListView lv;
    Mylistview lv_moi;
    ArrayList<BHDDT> BHDDT = new ArrayList<>();
    ArrayList<Sanpham_gio> list, list_moi;
    Adapter_Info_Order adapter, adapter_moi;
    ImageView ivAvatar, ivAvatar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info_bhddt);
        Anhxa();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoBHDDT");
        BHDDT = bundle.getParcelableArrayList("listBHDDT");
        list = new ArrayList<>();
        list_moi = new ArrayList<>();
        maBH = BHDDT.get(0).getMaBH();
        dateToday = BHDDT.get(0).getDateToday();
        timeToday = BHDDT.get(0).getTimeToday();
        chinhanhToday = BHDDT.get(0).getChinhanhToday();
        maNVToday = BHDDT.get(0).getMaNVToday();
        tenNVToday = BHDDT.get(0).getTenNVToday();
        maOrder = BHDDT.get(0).getMaOrder();
        date = BHDDT.get(0).getDate();
        time = BHDDT.get(0).getTime();
        gio = BHDDT.get(0).getGio();
        phidoiSP = BHDDT.get(0).getPhidoisp();
        chinhanh = BHDDT.get(0).getChinhanh();
        tenNV = BHDDT.get(0).getTenNV();
        maNV = BHDDT.get(0).getMaNV();
        ten = BHDDT.get(0).getTen();
        ma = BHDDT.get(0).getMa();
        baohanh = BHDDT.get(0).getBaohanh();
        nguon = BHDDT.get(0).getNguon();
        ngaynhap = BHDDT.get(0).getNgaynhap();
        von = BHDDT.get(0).getVon();
        chechlech = BHDDT.get(0).getChenhlech();
        gia = BHDDT.get(0).getGia();
        list.add(new Sanpham_gio(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia));
        ghichuOrder = BHDDT.get(0).getGhichuOrder();
        tenKH = BHDDT.get(0).getTenKH();
        sdtKH = BHDDT.get(0).getSdtKH();
        ghichuKH = BHDDT.get(0).getGhichuKH();
        gio_moi = BHDDT.get(0).getGio_moi();
        int tongdon = 0;
        for (int i = 0; i < BHDDT.size(); i++){
            list_moi.add(new Sanpham_gio(BHDDT.get(i).getGio_moi(), BHDDT.get(i).getMa_moi(), BHDDT.get(i).getTen_moi(), BHDDT.get(i).getBaohanh_moi(), BHDDT.get(i).getNguon_moi(), BHDDT.get(i).getNgaynhap_moi(), BHDDT.get(i).getVon_moi(), BHDDT.get(i).getGia_moi()));
            tongdon = tongdon + Integer.parseInt(BHDDT.get(i).getGia_moi());
        }
        lydo = BHDDT.get(0).getLydo();
        tvmaBHDDT.setText("Mã BH: "+maBH);
        tvDlkDatetoday.setText(dateToday);
        tvDlkTimetoday.setText(timeToday);
        tvChinhanhDLK.setText(chinhanhToday);
        tvDlkTenNVNhan.setText("Mã NV bảo hành: "+ maNVToday);
        tvDlkMaNVNhan.setText("Tên NV bảo hành: " + tenNVToday);
        tvDlkMaOrder.setText("Mã đơn hàng: " + maOrder);
        tvDlkDate.setText(date);
        tvDlkTime.setText(time);
        tvChinhanhDLKOrder.setText(chinhanh);
        tvDlkMaNV.setText("Mã số: " + maNV);
        tvDlkTenNV.setText("Tên nhân viên: " + tenNV);
        tvTongdonhang.setText(Keys.setMoney(tongdon));
        adapter = new Adapter_Info_Order(Main_Info_BHDDT.this, list);
        lv.setAdapter(adapter);
        if (ghichuKH.equals("")) {
            tvDlkGhichuKH.setVisibility(View.GONE);
        } else {
            tvDlkGhichuKH.setText("Ghi chú KH: " + ghichuKH);
        }
        if (ghichuOrder.equals("")) {
            tvDlkGhichuOrder.setVisibility(View.GONE);
        } else {
            tvDlkGhichuOrder.setText("Ghi chú đơn hàng: " + ghichuOrder);
        }
        tvDlkTenKH.setText("Tên KH: " + tenKH);
        tvDlkSdtKH.setText("SĐT KH: " + sdtKH);
        adapter_moi = new Adapter_Info_Order(Main_Info_BHDDT.this, list_moi);
        lv_moi.setAdapter(adapter_moi);
        tvlydoDLK.setText("Lý do: " + lydo);
        tvDlkChenhlech.setText(Keys.setMoney(Integer.valueOf(chechlech)));
        tvphidoiSP.setText(Keys.setMoney(Integer.valueOf(phidoiSP)));
        GetUser(Main_Info_BHDDT.this, maNVToday);
        GetUser2(Main_Info_BHDDT.this, maNV);
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
        tvDlkChenhlech = findViewById(R.id.tvDlkChenhlech);
        tvphidoiSP = findViewById(R.id.tvphidoiSP);
        tvlydoDLK = findViewById(R.id.tvlydoBHDLK);
        tvDlkMaOrder = findViewById(R.id.tvDlkMaOrder);
        tvDlkDate = findViewById(R.id.tvDlkDate);
        tvDlkTime = findViewById(R.id.tvDlkTime);
        tvTongdonhang     = findViewById(R.id.tvTongdonhang);
        tvDlkDatetoday = findViewById(R.id.tvDlkDatetoday);
        tvDlkTimetoday = findViewById(R.id.tvDlkTimetoday);
        tvDlkMaNV = findViewById(R.id.tvDlkMaNV);
        tvChinhanhDLK = findViewById(R.id.tvChinhanhDLK);
        tvChinhanhDLKOrder = findViewById(R.id.tvChinhanhDLKOrder);
        tvDlkTenNV = findViewById(R.id.tvDlkTenNV);
        tvDlkGhichuOrder = findViewById(R.id.tvDlkGhichu);
        tvDlkTenKH = findViewById(R.id.tvDlkTenKH);
        tvDlkSdtKH = findViewById(R.id.tvDlkSdtKH);
        lv = findViewById(R.id.lvBHDLK);
        lv_moi = findViewById(R.id.lvBHDLK_moi);
        tvDlkGhichuKH = findViewById(R.id.tvDlkGhichuKH);
        tvDlkTenNVNhan = findViewById(R.id.tvDlkTenNVNhan);
        tvDlkMaNVNhan = findViewById(R.id.tvDlkMaNVNhan);
        tvmaBHDDT = findViewById(R.id.tvmaBHDLK);
    }
}