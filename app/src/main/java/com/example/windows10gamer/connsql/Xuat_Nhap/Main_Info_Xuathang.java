package com.example.windows10gamer.connsql.Xuat_Nhap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Adapter.Adapter_InfoXuatnhap;
import com.example.windows10gamer.connsql.Object.XuatNhap;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main_Info_Xuathang extends AppCompatActivity {
    String maXN, ngay, gio, ca, chinhanhToday, maNVToday, tenNVToday, chinhanhNhan, maNVNhan, tenNVNhan, ghichu;
    TextView tvdanhan, tvchuanhan, tvtatca, tvmaXN, tvngay, tvca, tvchinhanhToday, tvmaNVToday, tvtenNVToday, tvchinhanhNhan, tvmaNVNhan, tvtenNVNhan, tvghichu, tvphantram;
    ListView lv;
    int soluong, phantram;
    ArrayList<XuatNhap> list = new ArrayList<>();
    Adapter_InfoXuatnhap adapter;
    ProgressDialog dialog;
    FloatingActionButton fabAn, fabHien;
    LinearLayout lnghichu, lnHiden;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info_xuathang);
        tvmaXN = (TextView) findViewById(R.id.tvInfomaXN);
        tvngay = (TextView) findViewById(R.id.tvInfoDate);
        tvca = (TextView) findViewById(R.id.tvInfoTime);
        tvchinhanhToday = (TextView) findViewById(R.id.tvInfochinhanhToday);
        tvmaNVToday = (TextView) findViewById(R.id.tvInfomaNV);
        tvtenNVToday = (TextView) findViewById(R.id.tvInfotenNV);
        tvchinhanhNhan = (TextView) findViewById(R.id.tvInfochinhanhNhan);
        tvmaNVNhan = (TextView) findViewById(R.id.tvInfomaNVNhan);
        tvtenNVNhan = (TextView) findViewById(R.id.tvInfotenNVNhan);
        tvghichu = (TextView) findViewById(R.id.tvInfoghichu);
        tvdanhan = (TextView) findViewById(R.id.tvdanhan);
        tvchuanhan = (TextView) findViewById(R.id.tvchuanhan);
        tvtatca = (TextView) findViewById(R.id.tvtatca);
        lv = (ListView) findViewById(R.id.lvInfoxuathang);
        tvphantram = (TextView) findViewById(R.id.tvphantram);
        lnghichu = (LinearLayout) findViewById(R.id.lnghichu);
        lnHiden = (LinearLayout) findViewById(R.id.lnHiden);
        fabAn = (FloatingActionButton) findViewById(R.id.fabAn);
        fabHien = (FloatingActionButton) findViewById(R.id.fabHien);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Info_Xuathang");
        maXN =bundle.getString("maXN");
        maNVToday =bundle.getString("maNVToday");
        tenNVToday =bundle.getString("tenNVToday");
        chinhanhToday =bundle.getString("chinhanhToday");
        maNVNhan =bundle.getString("maNVNhan");
        tenNVNhan =bundle.getString("tenNVNhan");
        chinhanhNhan =bundle.getString("chinhanhNhan");
        ngay =bundle.getString("ngay");
        gio =bundle.getString("gio");
        ca =bundle.getString("ca");
        ghichu =bundle.getString("ghichu");
        soluong =bundle.getInt("soluong");
        phantram =bundle.getInt("phantram");
        tvmaXN.setText("Mã đơn xuất: "+maXN);
        tvngay.setText(gio+" "+ngay);
        tvca.setText(ca);
        tvchinhanhToday.setText(chinhanhToday);
        tvmaNVToday.setText(maNVToday);
        tvtenNVToday.setText(tenNVToday);
        tvchinhanhNhan.setText(chinhanhNhan);
        tvmaNVNhan.setText(maNVNhan);
        tvtenNVNhan.setText(tenNVNhan);
        fabAn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnHiden.setVisibility(View.GONE);
                fabAn.setVisibility(View.GONE);
                fabHien.setVisibility(View.VISIBLE);
            }
        });
        fabHien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lnHiden.setVisibility(View.VISIBLE);
                fabAn.setVisibility(View.VISIBLE);
                fabHien.setVisibility(View.GONE);
            }
        });
        if (ghichu.equals("")){
            lnghichu.setVisibility(View.GONE);
        } else {
            tvghichu.setText(ghichu);
        }
        tvphantram.setText("Tổng: "+phantram+"/"+soluong);
        adapter = new Adapter_InfoXuatnhap(Main_Info_Xuathang.this, R.layout.adapter_info_xuathang, list);
        lv.setAdapter(adapter);
        GetDT(new VolleyCallback(){
            @Override
            public void onSuccess(final ArrayList<XuatNhap> result) {
                final ArrayList<XuatNhap> danhan, chuanhan;
                danhan = new ArrayList<>();
                chuanhan = new ArrayList<>();
                for (int i = 0; i < result.size(); i++){
                    if (Integer.valueOf(result.get(i).getTrangthai()) == 1){
                        danhan.add(result.get(i));
                    } else if (Integer.valueOf(result.get(i).getTrangthai()) == 0){
                        chuanhan.add(result.get(i));
                    }
                }
                tvtatca.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter = new Adapter_InfoXuatnhap(Main_Info_Xuathang.this, R.layout.adapter_info_xuathang, result);
                        lv.setAdapter(adapter);
                        tvtatca.setBackgroundResource(R.drawable.bg125);
                        tvdanhan.setBackgroundColor(getResources().getColor(R.color.white));
                        tvchuanhan.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                });
                tvdanhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter = new Adapter_InfoXuatnhap(Main_Info_Xuathang.this, R.layout.adapter_info_xuathang, danhan);
                        lv.setAdapter(adapter);
                        tvdanhan.setBackgroundResource(R.drawable.bg125);
                        tvtatca.setBackgroundColor(getResources().getColor(R.color.white));
                        tvchuanhan.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                });
                tvchuanhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        adapter = new Adapter_InfoXuatnhap(Main_Info_Xuathang.this, R.layout.adapter_info_xuathang, chuanhan);
                        lv.setAdapter(adapter);
                        tvchuanhan.setBackgroundResource(R.drawable.bg125);
                        tvtatca.setBackgroundColor(getResources().getColor(R.color.white));
                        tvdanhan.setBackgroundColor(getResources().getColor(R.color.white));
                    }
                });
            }
        });
    }

    public ArrayList<XuatNhap> GetDT(final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, Keys.MAIN_XUATNHAP+"?maXN="+maXN, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                            try {
                                JSONArray ja = response.getJSONArray(Keys.XUATNHAP);
                                for(int i=0; i < ja.length(); i++){
                                JSONObject object = ja.getJSONObject(i);
                                    list.add(new XuatNhap(
                                            object.getString("maXN"),
                                            object.getString("ngay"),
                                            object.getString("ca"),
                                            object.getString("gio"),
                                            object.getString("chinhanhToday"),
                                            object.getString("maNVToday"),
                                            object.getString("tenNVToday"),
                                            object.getString("ma"),
                                            object.getString("ten"),
                                            object.getString("baohanh"),
                                            object.getString("nguon"),
                                            object.getString("ngaynhap"),
                                            object.getString("von"),
                                            object.getString("gia"),
                                            object.getString("ghichu"),
                                            object.getString("chinhanhNhan"),
                                            object.getString("maNVNhan"),
                                            object.getString("tenNVNhan"),
                                            object.getString("trangthai")
                                    ));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) { e.printStackTrace();}
                        callback.onSuccess(list);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onSuccess(list);
                    }
                }
        );
        requestQueue.add(jor);
        return list;
    }

    public interface VolleyCallback{
        void onSuccess(ArrayList<XuatNhap> result);
    }

}
