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
import com.example.windows10gamer.connsql.Object.BH1D1;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main_Info_BH1D1 extends AppCompatActivity {
    String maBH, dateToday, timeToday, gio, gio_moi, chinhanhToday,phibaohanh, maNVToday, tenNVToday, maOrder, date, time, chinhanh, tenNV, maNV, ten, ma, baohanh, nguon, ngaynhap, von, gia, ghichuOrder, tenKH, sdtKH, ghichuKH, ten_moi, ma_moi, baohanh_moi, nguon_moi, ngaynhap_moi, von_moi, gia_moi, lydo;
    ArrayList<BH1D1> BH1D1 = new ArrayList<>();
    TextView tvd1MaOrder,tvmaBH1D1, tvd1Date, tvd1Time, tvd1MaNV, tvd1TenNV, tvd1GhichuOrder, tvd1TenKH,tvlydod1, tvphibaohanh,
            tvd1SdtKH, tvd1GhichuKH, tvd1TenNVToday, tvd1MaNVToday, tvd1Datetoday, tvd1Timetoday, tvChinhanh1D1, tvChinhanh1D1Order;
    ListView lvBH1D1_moi, lvBH1D1;
    ArrayList<Sanpham_gio> list, list_moi;
    Adapter_Info_Order adapter, adapter_moi;
    ImageView ivAvatar, ivAvatar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info_bh1d1);
        Anhxa();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoBH1D1");
        BH1D1 = bundle.getParcelableArrayList("listBH1D1");
        list = new ArrayList<>();
        list_moi = new ArrayList<>();
        maBH = BH1D1.get(0).getMaBH();
        dateToday = BH1D1.get(0).getDateToday();
        timeToday = BH1D1.get(0).getTimeToday();
        chinhanhToday = BH1D1.get(0).getChinhanhToday();
        maNVToday = BH1D1.get(0).getMaNVToday();
        tenNVToday = BH1D1.get(0).getTenNVToday();
        maOrder = BH1D1.get(0).getMaOrder();
        date = BH1D1.get(0).getDate();
        time = BH1D1.get(0).getTime();
        chinhanh = BH1D1.get(0).getChinhanh();
        tenNV = BH1D1.get(0).getTenNV();
        maNV = BH1D1.get(0).getMaNV();
        ten = BH1D1.get(0).getTen();
        ma = BH1D1.get(0).getMa();
        gio = BH1D1.get(0).getGio();
        baohanh = BH1D1.get(0).getBaohanh();
        nguon = BH1D1.get(0).getNguon();
        ngaynhap = BH1D1.get(0).getNgaynhap();
        von = BH1D1.get(0).getVon();
        gia = BH1D1.get(0).getGia();
        ghichuOrder = BH1D1.get(0).getGhichuOrder();
        tenKH = BH1D1.get(0).getTenKH();
        sdtKH = BH1D1.get(0).getSdtKH();
        ghichuKH = BH1D1.get(0).getGhichuKH();
        ten_moi = BH1D1.get(0).getTen_moi();
        ma_moi = BH1D1.get(0).getMa_moi();
        baohanh_moi = BH1D1.get(0).getBaohanh_moi();
        nguon_moi = BH1D1.get(0).getNguon_moi();
        ngaynhap_moi = BH1D1.get(0).getNgaynhap_moi();
        von_moi = BH1D1.get(0).getVon_moi();
        gia_moi = BH1D1.get(0).getGia_moi();
        lydo = BH1D1.get(0).getLydo();
        gio_moi = BH1D1.get(0).getGio_moi();
        phibaohanh = BH1D1.get(0).getPhibaohanh();
        tvmaBH1D1.setText("Mã BH: "+maBH);
        tvd1Datetoday.setText(dateToday);
        tvd1Timetoday.setText(timeToday);
        tvChinhanh1D1.setText(chinhanhToday);
        tvd1TenNVToday.setText("Mã NV bảo hành: "+ maNVToday);
        tvd1MaNVToday.setText("Tên NV bảo hành: " + tenNVToday);
        tvd1MaOrder.setText("Mã đơn hàng: " + maOrder);
        tvd1Date.setText(date);
        tvd1Time.setText(time);
        tvChinhanh1D1Order.setText(chinhanh);
        tvd1MaNV.setText("Mã số: " + maNV);
        tvd1TenNV.setText("Tên nhân viên: " + tenNV);
        list.add(new Sanpham_gio(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia));
        adapter = new Adapter_Info_Order(Main_Info_BH1D1.this, list);
        lvBH1D1.setAdapter(adapter);
        if (ghichuKH.equals("")) {
            tvd1GhichuKH.setVisibility(View.GONE);
        } else {
            tvd1GhichuKH.setText("Ghi chú KH: " + ghichuKH);
        }
        if (ghichuOrder.equals("")) {
            tvd1GhichuOrder.setVisibility(View.GONE);
        } else {
            tvd1GhichuOrder.setText("Ghi chú đơn hàng: " + ghichuOrder);
        }
        tvd1TenKH.setText("Tên KH: " + tenKH);
        tvd1SdtKH.setText("SĐT KH: " + sdtKH);
        list_moi.add(new Sanpham_gio(gio_moi, ma_moi, ten_moi, baohanh_moi, nguon_moi, ngaynhap_moi, von_moi, gia_moi));
        adapter_moi = new Adapter_Info_Order(Main_Info_BH1D1.this, list_moi);
        lvBH1D1_moi.setAdapter(adapter_moi);
        tvlydod1.setText("Lý do: " + lydo);
        tvphibaohanh.setText(Keys.setMoney(Integer.parseInt(phibaohanh)));
        GetUser(Main_Info_BH1D1.this, maNVToday);
        GetUser2(Main_Info_BH1D1.this, maNV);
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
        lvBH1D1 = findViewById(R.id.lvBHD1);
        lvBH1D1_moi = findViewById(R.id.lvBH1D1_moi);
        tvlydod1 = findViewById(R.id.tvlydoBHD1);
        tvmaBH1D1 = findViewById(R.id.tvmaBH1D1);
        tvd1MaOrder = findViewById(R.id.tvd1MaOrder);
        tvd1Date = findViewById(R.id.tvd1Date);
        tvd1Time = findViewById(R.id.tvd1Time);
        tvd1Datetoday = findViewById(R.id.tvd1Datetoday);
        tvd1Timetoday = findViewById(R.id.tvd1Timetoday);
        tvd1MaNV = findViewById(R.id.tvd1MaNV);
        tvd1TenNV = findViewById(R.id.tvd1TenNV);
        tvd1GhichuOrder = findViewById(R.id.tvd1Ghichu);
        tvd1TenKH = findViewById(R.id.tvd1TenKH);
        tvd1SdtKH = findViewById(R.id.tvd1SdtKH);
        tvChinhanh1D1 = findViewById(R.id.tvChinhanh1D1);
        tvChinhanh1D1Order = findViewById(R.id.tvChinhanh1D1Order);
        tvd1GhichuKH = findViewById(R.id.tvd1GhichuKH);
        tvd1TenNVToday = findViewById(R.id.tvd1TenNVNhan);
        tvd1MaNVToday = findViewById(R.id.tvd1MaNVNhan);
        tvphibaohanh = findViewById(R.id.tvphibaohanh);
    }

}
