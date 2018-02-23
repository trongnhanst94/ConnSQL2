package com.example.windows10gamer.connsql.Xuat_Nhap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.windows10gamer.connsql.Adapter.Adapter_XuatNhap;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Object.XuatNhap;
import com.example.windows10gamer.connsql.Object.XuatNhap_SL;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main_Danhsach_Xuathang extends AppCompatActivity {

    ProgressDialog dialog;
    ArrayList<XuatNhap> list = new ArrayList<>();
    Adapter_XuatNhap adapter;
    ListView listView;
    ArrayList<XuatNhap_SL> dem = new ArrayList<>();
    private SharedPreferences shared;
    private String session_ma, session_username;
    private String chinhanh;
    TextView tvDSxuatmaNV, tvDSxuattenNV, tvDSxuatchinhanh;
    ImageView ivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_danhsach_xuathang);
        ivAvatar = findViewById(R.id.ivAvatar);
        listView = findViewById(R.id.lvXuathang);
        tvDSxuatchinhanh = findViewById(R.id.tvDSxuatchinhanh);
        tvDSxuattenNV = findViewById(R.id.tvDSxuattenNV);
        tvDSxuatmaNV = findViewById(R.id.tvDSxuatmaNV);
        adapter = new Adapter_XuatNhap(Main_Danhsach_Xuathang.this, R.layout.adapter_xuatnhap, dem);
        listView.setAdapter(adapter);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        shared = getSharedPreferences("login", MODE_PRIVATE);
        session_ma = shared.getString("ma", "");
        session_username = shared.getString("shortName", "");
        Glide.with(Main_Danhsach_Xuathang.this).load(shared.getString("img", "")).override(300,300).fitCenter().into(ivAvatar);
        tvDSxuatchinhanh.setText(chinhanh);
        tvDSxuatmaNV.setText("Mã số: "+session_ma);
        tvDSxuattenNV.setText("Tên nhân viên: "+session_username);
        new GetData().execute(session_ma);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Danhsach_Xuathang.this).show();
                else {
                    Intent intent = new Intent(Main_Danhsach_Xuathang.this, Main_Info_Xuathang.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("maXN", dem.get(position).getMaXN());
                    bundle.putString("maNVToday", dem.get(position).getMaNVToday());
                    bundle.putString("tenNVToday", dem.get(position).getTenNVToday());
                    bundle.putString("chinhanhToday", dem.get(position).getChinhanhToday());
                    bundle.putString("tenNVNhan", dem.get(position).getTenNVNhan());
                    bundle.putString("maNVNhan", dem.get(position).getMaNVNhan());
                    bundle.putString("chinhanhNhan", dem.get(position).getChinhanhNhan());
                    bundle.putString("ghichu", dem.get(position).getGhichu());
                    bundle.putInt("soluong", dem.get(position).getSoluong());
                    bundle.putInt("phantram", dem.get(position).getPhantram());
                    bundle.putString("ngay", dem.get(position).getNgay());
                    bundle.putString("gio", dem.get(position).getGio());
                    bundle.putString("ca", dem.get(position).getCa());
                    intent.putExtra("Info_Xuathang", bundle);
                    startActivity(intent);
                }
            }
        });
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
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_Danhsach_Xuathang.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_XUATNHAP+"?maNVxuat="+params[0]);
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
            } else {
                new CustomToast().Show_Toast(Main_Danhsach_Xuathang.this, findViewById(android.R.id.content), "Không tìm thấy đơn Xuất hàng!");
            }
            dialog.dismiss();
            adapter.notifyDataSetChanged();
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
