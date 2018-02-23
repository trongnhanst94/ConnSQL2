package com.example.windows10gamer.connsql.Xuat_Nhap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.windows10gamer.connsql.Adapter.Adapter_Duyetnhap;
import com.example.windows10gamer.connsql.Object.DuyetNhap;
import com.example.windows10gamer.connsql.Object.DuyetNhap_ID;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Main_Duyetnhap extends AppCompatActivity {
    boolean T = TRUE, F = FALSE;
    String maXN, ngay, gio, ca, chinhanhToday, maNVToday, tenNVToday, chinhanhNhan, maNVNhan, tenNVNhan, ghichu;
    TextView tvdanhan, tvchuanhan, tvtatca, tvmaXN, tvngay, tvca, tvchinhanhToday, tvmaNVToday, tvtenNVToday, tvchinhanhNhan, tvmaNVNhan, tvtenNVNhan, tvghichu, tvphantram;
    ListView lv;
    int soluong, phantram;
    ArrayList<DuyetNhap> list = new ArrayList<>();
    Adapter_Duyetnhap adapter;
    ProgressDialog dialog;
    FloatingActionButton fabSubmit;
    TextView fabAn, fabHien;
    LinearLayout lnghichu, lnHiden;
    ArrayList<String> mspList = new ArrayList<>();
    private ListView listview;
    private int count;
    private boolean[] thumbnailsselection;
    ArrayList<DuyetNhap_ID> str = new ArrayList<>();
    ImageView ivAvatar, ivAvatar2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_duyetnhap);
        ivAvatar2 = findViewById(R.id.ivAvatar2);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvmaXN = findViewById(R.id.tvInfomaXN);
        tvngay = findViewById(R.id.tvInfoDate);
        tvca = findViewById(R.id.tvInfoTime);
        tvchinhanhToday = findViewById(R.id.tvInfochinhanhToday);
        tvmaNVToday = findViewById(R.id.tvInfomaNV);
        tvtenNVToday = findViewById(R.id.tvInfotenNV);
        tvchinhanhNhan = findViewById(R.id.tvInfochinhanhNhan);
        tvmaNVNhan = findViewById(R.id.tvInfomaNVNhan);
        tvtenNVNhan = findViewById(R.id.tvInfotenNVNhan);
        tvghichu = findViewById(R.id.tvInfoghichu);
        tvdanhan = findViewById(R.id.tvdanhan);
        tvchuanhan = findViewById(R.id.tvchuanhan);
        tvtatca = findViewById(R.id.tvtatca);
        lv = findViewById(R.id.lvInfoxuathang);
        tvphantram = findViewById(R.id.tvphantram);
        lnghichu = findViewById(R.id.lnghichu);
        lnHiden = findViewById(R.id.lnHiden);
        fabAn = findViewById(R.id.fabAn);
        fabHien = findViewById(R.id.fabHien);
        fabSubmit = findViewById(R.id.fabSubmit);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("Main_Duyetnhap");
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
        tvmaNVToday.setText("Mã số: "+maNVToday);
        tvtenNVToday.setText("Tên nhân viên: "+tenNVToday);
        tvchinhanhNhan.setText(chinhanhNhan);
        tvmaNVNhan.setText("Mã số: "+maNVNhan);
        tvtenNVNhan.setText("Tên nhân viên: "+tenNVNhan);
        if (ghichu.equals("")){
            lnghichu.setVisibility(View.GONE);
        } else {
            tvghichu.setText(ghichu);
        }
        tvphantram.setText("Tổng: "+phantram+"/"+soluong);
        count = list.size();
        thumbnailsselection = new boolean[count];
        GetDT(new VolleyCallback(){
            @Override
            public void onSuccess(final ArrayList<DuyetNhap> result) {
                adapter = new Adapter_Duyetnhap(Main_Duyetnhap.this, R.layout.adapter_duyetnhap, result);
                lv.setAdapter(adapter);
                fabSubmit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        str.clear();
                        for (int i=0; i<list.size(); i++) {
                            if (list.get(i).getTrangthai().equals("1")) {
                                str.add(new DuyetNhap_ID(list.get(i).getId(), 1));
                            } else {
                                str.add(new DuyetNhap_ID(list.get(i).getId(), 0));
                            }
                        }
                        new SendRequest().execute();
                    }
                });
            }
        });
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
        GetUser(Main_Duyetnhap.this, maNVToday);
        GetUser2(Main_Duyetnhap.this, maNVNhan);
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
    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(Main_Duyetnhap.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được xử lý.");
            dialog.setIndeterminate(false);
            dialog.setCancelable(false);
        }

        protected String doInBackground(String... arg0) {
            int j = 0;
            while (j < str.size()){
                UpdateXuathangWeb(j);
                j++;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
       }
    }

    public ArrayList<DuyetNhap> GetDT(final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jor = new JsonObjectRequest(Request.Method.POST, Keys.MAIN_XUATNHAP+"?maXN="+maXN, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray ja = response.getJSONArray(Keys.XUATNHAP);
                            for(int i=0; i < ja.length(); i++){
                                JSONObject object = ja.getJSONObject(i);
                                list.add(new DuyetNhap(
                                        object.getString("id"),
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

    public void check(int position) {
        list.get(position).setTrangthai("1");
        adapter.notifyDataSetChanged();
    }

    public void uncheck(int position) {
        list.get(position).setTrangthai("0");
        adapter.notifyDataSetChanged();
    }

    public interface VolleyCallback{
        void onSuccess(ArrayList<DuyetNhap> result);
    }

    public void UpdateXuathangWeb(final int j){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_XUATNHAP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            new CustomToast().Show_Toast(Main_Duyetnhap.this, findViewById(android.R.id.content), "Lỗi kết nối");
                        } else if (response.trim().equals("success")){
                            if (j == (str.size()-1)){
                                dialog.dismiss();
                                new CustomToast().Show_Toast(Main_Duyetnhap.this, findViewById(android.R.id.content), "Check đơn thành công!!");
                                startActivity(new Intent(Main_Duyetnhap.this, Main_Danhsach_Nhaphang.class));
                                finish();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Duyetnhap.this, findViewById(android.R.id.content), "Không kết nối được Server!");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.UPDATE_XUATHANG_WEB);
                params.put("id", str.get(j).getMa());
                params.put("trangthai", str.get(j).getTrangthai()+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
