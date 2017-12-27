package com.example.windows10gamer.connsql.Xuat_Nhap;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.windows10gamer.connsql.Object.XuatNhap;
import com.example.windows10gamer.connsql.Object.XuatNhap_SL;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main_XuatNhap extends AppCompatActivity {

    @BindView(R.id.ivtaoxuat)
    ImageView ivtaoxuat;
    @BindView(R.id.ivunchecknhan)
    ImageView ivunchecknhan;
    @BindView(R.id.ivchecknhan)
    ImageView ivchecknhan;
    @BindView(R.id.ivlistxuat)
    ImageView ivlistxuat;
    @BindView(R.id.ivlistnhan)
    ImageView ivlistnhan;
    ProgressDialog dialog;
    ArrayList<XuatNhap> list = new ArrayList<>();
    ArrayList<XuatNhap_SL> dem = new ArrayList<>();
    String session_username, session_ma;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_xuat_nhap);
        ButterKnife.bind(this);
        ivchecknhan = (ImageView) findViewById(R.id.ivchecknhan);
        ivunchecknhan = (ImageView) findViewById(R.id.ivunchecknhan);
        ivtaoxuat = (ImageView) findViewById(R.id.ivtaoxuat);
        ivlistnhan = (ImageView) findViewById(R.id.ivlistnhan);
        ivlistxuat = (ImageView) findViewById(R.id.ivlistxuat);
        SharedPreferences shared = getSharedPreferences("login", MODE_PRIVATE);
        session_ma = shared.getString("ma", "");
        session_username = shared.getString("shortName", "");
        new GetData().execute(session_ma);
        ivtaoxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_XuatNhap.this).show();
                else {
                    startActivity(new Intent(Main_XuatNhap.this, Main_Taoxuat.class));
                }
            }
        });
        ivlistxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_XuatNhap.this).show();
                else {
                    startActivity(new Intent(Main_XuatNhap.this, Main_Danhsach_Xuathang.class));
                }
            }
        });
        ivlistnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_XuatNhap.this).show();
                else {
                    startActivity(new Intent(Main_XuatNhap.this, Main_XNOpen.class));
                }
            }
        });
        ivchecknhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_XuatNhap.this).show();
                else {
                    startActivity(new Intent(Main_XuatNhap.this, Main_Danhsach_Nhaphang.class));
                }
            }
        });
        ivunchecknhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_XuatNhap.this).show();
                else {
                    startActivity(new Intent(Main_XuatNhap.this, Main_Danhsach_Nhaphang.class));
                }
            }
        });
    }

    class GetData extends AsyncTask<String, Void, Void> {

        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_XuatNhap.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_XUATNHAP+"?maNVnhap="+params[0]);
            try {
                list.clear();
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.XUATNHAP);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
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
            if(list.size() > 0) {
                for (int i = 0; i < list.size(); i++){
                    int tong = 0;
                    int resultMaXN = sosanhMaXN(dem, list.get(i).getMaXN());
                    if (resultMaXN == -1){
                        dem.add(new XuatNhap_SL(
                                list.get(i).getMaXN(),
                                list.get(i).getNgay(),
                                list.get(i).getCa(),
                                list.get(i).getGio(),
                                list.get(i).getChinhanhToday(),
                                list.get(i).getMaNVToday(),
                                list.get(i).getTenNVToday(),
                                list.get(i).getMa(),
                                list.get(i).getTen(),
                                list.get(i).getBaohanh(),
                                list.get(i).getNguon(),
                                list.get(i).getNgaynhap(),
                                list.get(i).getVon(),
                                list.get(i).getGia(),
                                list.get(i).getGhichu(),
                                list.get(i).getChinhanhNhan(),
                                list.get(i).getMaNVNhan(),
                                list.get(i).getTenNVNhan(),
                                list.get(i).getTrangthai(),
                                1,
                                Integer.valueOf(list.get(i).getTrangthai())));
                    } else {
                        tong = Integer.valueOf(list.get(i).getTrangthai());
                        dem.set( resultMaXN, new XuatNhap_SL(
                                dem.get(resultMaXN).getMaXN(),
                                dem.get(resultMaXN).getNgay(),
                                dem.get(resultMaXN).getCa(),
                                dem.get(resultMaXN).getGio(),
                                dem.get(resultMaXN).getChinhanhToday(),
                                dem.get(resultMaXN).getMaNVToday(),
                                dem.get(resultMaXN).getTenNVToday(),
                                dem.get(resultMaXN).getMa(),
                                dem.get(resultMaXN).getTen(),
                                dem.get(resultMaXN).getBaohanh(),
                                dem.get(resultMaXN).getNguon(),
                                dem.get(resultMaXN).getNgaynhap(),
                                dem.get(resultMaXN).getVon(),
                                dem.get(resultMaXN).getGia(),
                                dem.get(resultMaXN).getGhichu(),
                                dem.get(resultMaXN).getChinhanhNhan(),
                                dem.get(resultMaXN).getMaNVNhan(),
                                dem.get(resultMaXN).getTenNVNhan(),
                                dem.get(resultMaXN).getTrangthai(),
                                Integer.valueOf(dem.get(resultMaXN).getSoluong())+1,
                                tong+Integer.valueOf(dem.get(resultMaXN).getPhantram())));
                    }
                }
                for (int i = 0; i < dem.size(); i++){
                    if (dem.get(i).getPhantram() == 0){
                        ivchecknhan.setVisibility(View.GONE);
                        ivunchecknhan.setVisibility(View.VISIBLE);
                    }
                }
            }
            dialog.dismiss();
        }
    }

    private int sosanhMaXN(ArrayList<XuatNhap_SL> dem, String ma) {
        int result = -1;
        if (dem.size() != 0){
            for (int i = 0; i < dem.size(); i++){
                if (dem.get(i).getMaXN().equals(ma)){
                    result = i;
                }
            }
        }
        return result;
    }
}
