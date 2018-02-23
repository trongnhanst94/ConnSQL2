package com.example.windows10gamer.connsql.Bao_Hanh;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order;
import com.example.windows10gamer.connsql.Adapter.Adapter_Spinner_NV;
import com.example.windows10gamer.connsql.Kiem_Kho.Main_Ketqua_Kiemkho;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Main_Choxuly extends AppCompatActivity {
    String maOrder, date, time, tenNV, maNV, ten, ma, baohanh, nguon, gia, ngaynhap, von,maBH,
            dateToday, timeToday, tenKH, sdtKH, ghichuOrder, ghichuKH, thoigianhen, chinhanhOrder, maNVxuly, tenNVxuly;
    ArrayList<Sanpham_gio> sanpham = new ArrayList<>();
    ProgressDialog dialog;
    TextView tvCxlMaOrder,tvCxlDate,tvCxlTime,tvCxlMaNV,tvCxlTenNV,tvCxlGhichuOrder,tvCxlTenKH,
            tvCxlSdtKH,tvCxlGhichuKH,tvCxlTenNVNhan,tvCxlMaNVNhan,tvCxlDatetoday,tvCxlTimetoday,tvChinhanhHT,tvChinhanhHTOrder ;
    int vitri;
    ListView lv;
    Adapter_Info_Order adapter_sales;
    ArrayList<Sanpham_gio> arrayList;
    Button btnxacnhan;
    EditText edthoigianhen;
    String session_username;
    String session_ma;
    String chinhanh, gio;
    SharedPreferences shared, sp;
    Spinner snA;
    ImageView ivAvatar, ivAvatar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_choxuly);
        Anhxa();
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        sp = getSharedPreferences("login", MODE_PRIVATE);
        session_username = sp.getString("shortName", "");
        session_ma = sp.getString("ma", "");
        Glide.with(Main_Choxuly.this).load(shared.getString("img", "")).override(300,300).fitCenter().into(ivAvatar);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoOrder5");
        sanpham     = bundle.getParcelableArrayList("sanphamOrder");
        maOrder     = bundle.getString("MaOrder");
        date        = bundle.getString("DateOrder");
        time        = bundle.getString("TimeOrder");
        chinhanhOrder = bundle.getString("ChinhanhOrder");
        tenNV       = bundle.getString("TenNVOrder");
        maNV        = bundle.getString("MaNVOrder");
        ten         = bundle.getString("Ten");
        ma          = bundle.getString("Ma");
        baohanh     = bundle.getString("Baohanh");
        nguon       = bundle.getString("Nguon");
        ngaynhap    = bundle.getString("Ngaynhap");
        gia         = bundle.getString("Giaban");
        von         = bundle.getString("Von");
        gio         = bundle.getString("Gio");
        tenKH       = bundle.getString("TenKH");
        sdtKH       = bundle.getString("SdtKH");
        ghichuKH    = bundle.getString("GhichuKH");
        ghichuOrder = bundle.getString("GhichuOrder");
        dateToday = Keys.getDateNow();
        timeToday = Keys.getCalam(chinhanh);
        arrayList = new ArrayList<>();
        arrayList.add(new Sanpham_gio(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia));
        adapter_sales = new Adapter_Info_Order(Main_Choxuly.this, arrayList);
        lv.setAdapter(adapter_sales);
        if (ghichuKH.equals("")){
            tvCxlGhichuKH.setVisibility(View.GONE);
        } else {
            tvCxlGhichuKH.setText("Ghi chú KH: "+ghichuKH);
        }
        if (ghichuOrder.equals("")){
            tvCxlGhichuOrder.setVisibility(View.GONE);
        } else {
            tvCxlGhichuOrder.setText("Ghi chú đơn hàng: "+ghichuOrder);
        }
        tvCxlMaOrder.setText("Mã đơn hàng: "+maOrder);
        tvCxlDate.setText(date);
        tvCxlTime.setText(time);
        tvCxlDatetoday.setText(dateToday);
        tvCxlTimetoday.setText(timeToday);
        tvCxlMaNV.setText("Mã số: "+maNV);
        tvCxlTenNV.setText("Tên nhân viên: "+tenNV);
        tvCxlTenKH.setText("Tên KH: "+tenKH);
        tvCxlSdtKH.setText("SĐT KH: "+sdtKH);
        tvChinhanhHT.setText(chinhanh);
        tvChinhanhHTOrder.setText(chinhanhOrder);
        tvCxlTenNVNhan.setText("Mã NV bảo hành: "+session_username);
        tvCxlMaNVNhan.setText("Tên NV bảo hành: "+session_ma);
        GetUser(Main_Choxuly.this, maNV);
        edthoigianhen.setInputType(InputType.TYPE_NULL);
        edthoigianhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = java.util.Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Choxuly.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        edthoigianhen.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });

        GetUser(Keys.DANHSACHLOGIN, new Main_Ketqua_Kiemkho.VolleyCallback(){
            @Override
            public void onSuccess(final ArrayList<User> result) {
                final ArrayList<String> resultName, resultMa;
                resultName = new ArrayList<String>();
                resultMa = new ArrayList<String>();
                for (int i = 0; i < result.size(); i++){
                    resultName.add(result.get(i).getShortName());
                    resultMa.add(result.get(i).getMa());
                }

                Adapter_Spinner_NV customAdapter=new Adapter_Spinner_NV(getApplicationContext(),resultMa,resultName);
                snA.setAdapter(customAdapter);

                snA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                        maNVxuly = result.get(position).getMa();
                        tenNVxuly = result.get(position).getShortName();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {}
                });

                btnxacnhan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(!Connect_Internet.checkConnection(getApplicationContext()))
                            Connect_Internet.buildDialog(Main_Choxuly.this).show();
                        else {
                            btnxacnhan.setEnabled(false);
                            btnxacnhan.setBackgroundColor(getResources().getColor(R.color.aaaaa));
                            maBH = "BHCXL_"+Keys.MaDonhang();
//                  if (gtConlai > 0) {
//                    if (!edlydoHT.getText().toString().trim().equals("") && !edphitrahang.getText().toString().trim().equals("")) {
                            thoigianhen = edthoigianhen.getText().toString().trim();
                            new SendRequestHT().execute();
                        }
//                    }
//                    else Snackbar.make(v, "Phải nhập tất cả các trường.", Snackbar.LENGTH_LONG).show();
//                }
//                else Snackbar.make(v, "Phí đổi hàng quá cao.", Snackbar.LENGTH_LONG).show();
                    }
                });
            }
        });

        Button exit= findViewById(R.id.cancel);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Choxuly.this).show();
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Main_Choxuly.this);
                    builder.setMessage("Bạn muốn hủy đơn bảo hành??");
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Main_Choxuly.this.finish();
                        }
                    });
                    builder.show();
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
        snA = findViewById(R.id.spCXL);
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
        btnxacnhan = findViewById(R.id.btnBHHT);
        edthoigianhen = findViewById(R.id.edthoigianhen);
    }

    public ArrayList<User> GetUser(String urlUser, final Main_Ketqua_Kiemkho.VolleyCallback callback) {
        final ArrayList<User> usernames = new ArrayList<User>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlUser, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if (object.getInt("level") == Keys.LEVEL_BH){
                                    usernames.add(new User(
                                            object.getInt("id"),
                                            object.getString("ma_user"),
                                            object.getString("ten"),
                                            object.getString("shortName"),
                                            object.getString("username"),
                                            object.getString("password")
                                    ));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.onSuccess(usernames);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onSuccess(usernames);
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        return usernames;
    }

    public class SendRequestHT extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){

            dialog = new ProgressDialog(Main_Choxuly.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được xử lý");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... arg0) {
            putData();
            addChoxulyWeb();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            new CustomToast().Show_Toast(Main_Choxuly.this, findViewById(android.R.id.content), "Gửi dữ liệu thành công.");
            ResetActivity();
        }
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

    public String putData(){
        try {
            URL url = new URL(Keys.SCRIPT_BH_CXL);
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("maBH", maBH);
            postDataParams.put("dateToday", dateToday);
            postDataParams.put("timeToday", timeToday);
            postDataParams.put("gio", gio);
            postDataParams.put("chinhanhToday", chinhanh);
            postDataParams.put("tenNVToday", session_username);
            postDataParams.put("maNVToday", session_ma);
            postDataParams.put("maOrder", maOrder);
            postDataParams.put("date", date);
            postDataParams.put("time", time);
            postDataParams.put("chinhanh", chinhanhOrder);
            postDataParams.put("time", time);
            postDataParams.put("tenNV", tenNV);
            postDataParams.put("maNV", maNV);
            postDataParams.put("ten", ten);
            postDataParams.put("ma", ma);
            postDataParams.put("baohanh", baohanh);
            postDataParams.put("nguon", nguon);
            postDataParams.put("ngaynhap", ngaynhap);
            postDataParams.put("von", von);
            postDataParams.put("gia", gia);
            postDataParams.put("ghichuOrder", ghichuOrder);
            postDataParams.put("tenKH", tenKH);
            postDataParams.put("sdtKH", sdtKH);
            postDataParams.put("ghichuKH", ghichuKH);
            postDataParams.put("thoigianhen", thoigianhen);
            postDataParams.put("maNVxuly", maNVxuly);
            postDataParams.put("tenNVxuly", tenNVxuly);

            Log.e("params", postDataParams.toString());
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
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
                return new String("false : " + responseCode);
            }
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    public void ResetActivity(){
        startActivity(new Intent(Main_Choxuly.this, Main_Baohanh.class));
        finish();
    }

    public void addChoxulyWeb(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            new CustomToast().Show_Toast(Main_Choxuly.this, findViewById(android.R.id.content), "Lỗi ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Choxuly.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_BHCXL_WEB);
                params.put("maBH", maBH);
                params.put("dateToday", dateToday);
                params.put("timeToday", timeToday);
                params.put("gio", gio);
                params.put("chinhanhToday", chinhanh);
                params.put("tenNVToday", session_username);
                params.put("maNVToday", session_ma);
                params.put("maOrder", maOrder);
                params.put("date", date);
                params.put("time", time);
                params.put("chinhanh", chinhanhOrder);
                params.put("tenNV", tenNV);
                params.put("maNV", maNV);
                params.put("ten", ten);
                params.put("ma", ma);
                params.put("baohanh", baohanh);
                params.put("nguon", nguon);
                params.put("ngaynhap", ngaynhap);
                params.put("von", von);
                params.put("gia", gia);
                params.put("ghichuOrder", ghichuOrder);
                params.put("tenKH", tenKH);
                params.put("sdtKH", sdtKH);
                params.put("ghichuKH", ghichuKH);
                params.put("thoigianhen", thoigianhen);
                params.put("maNVxuly", maNVxuly);
                params.put("tenNVxuly", tenNVxuly);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}