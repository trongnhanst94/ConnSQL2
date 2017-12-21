package com.example.windows10gamer.connsql.Bao_Hanh;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Main_Hoantien extends AppCompatActivity {
    String maOrder, date, time, tenNV, maNV, ten, ma, baohanh, nguon, gia, ngaynhap, von,maBH,
            dateToday, timeToday, tenKH, sdtKH, ghichuOrder, ghichuKH, lydo, chinhanhOrder, phitrahang;
    ArrayList<Sanpham_gio> sanpham = new ArrayList<>();
    int gtConlai = 0;
    ProgressDialog dialog;
    TextView tvHtMaOrder,tvHtDate,tvHtTime,tvHtMaNV,tvHtTenNV,tvHtGhichuOrder,tvHtTenKH,tvgtConlai,
            tvHtSdtKH,tvHtGhichuKH,tvHtTenNVNhan,tvHtMaNVNhan,tvHtDatetoday,tvHtTimetoday,tvChinhanhHT,tvChinhanhHTOrder ;
    int vitri;
    ListView lv;
    Adapter_Info_Order adapter_sales;
    ArrayList<Sanpham_gio> arrayList;
    Button btnxacnhan;
    EditText edlydoHT, edphitrahang;
    String session_username;
    String session_ma;
    String chinhanh, gio;
    SharedPreferences shared, sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hoantien);
        Anhxa();
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        sp = getSharedPreferences("login", MODE_PRIVATE);
        session_username = sp.getString("shortName", "");
        session_ma = sp.getString("ma", "");
        maBH = "BHHT_"+Keys.MaDonhang();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoOrder2");
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
        adapter_sales = new Adapter_Info_Order(Main_Hoantien.this, arrayList);
        lv.setAdapter(adapter_sales);
        if (ghichuKH.equals("")){
            tvHtGhichuKH.setVisibility(View.GONE);
        } else {
            tvHtGhichuKH.setText("Ghi chú KH: "+ghichuKH);
        }
        if (ghichuOrder.equals("")){
            tvHtGhichuOrder.setVisibility(View.GONE);
        } else {
            tvHtGhichuOrder.setText("Ghi chú đơn hàng: "+ghichuOrder);
        }
        tvHtMaOrder.setText("Mã đơn hàng: "+maOrder);
        tvHtDate.setText(date);
        tvHtTime.setText(time);
        tvHtDatetoday.setText(dateToday);
        tvHtTimetoday.setText(timeToday);
        tvHtMaNV.setText("Mã NV: "+maNV);
        tvHtTenNV.setText("Tên NV: "+tenNV);
        tvHtTenKH.setText("Tên KH: "+tenKH);
        tvHtSdtKH.setText("SĐT KH: "+sdtKH);
        tvChinhanhHT.setText(chinhanh);
        tvChinhanhHTOrder.setText(chinhanhOrder);
        tvHtTenNVNhan.setText("Mã NV bảo hành: "+session_username);
        tvHtMaNVNhan.setText("Tên NV bảo hành: "+session_ma);
        edphitrahang.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (edphitrahang.getText().toString().trim().equals("")){
                    tvgtConlai.setText(Keys.getFormatedAmount(0));
                } else {
                    phitrahang = edphitrahang.getText().toString().trim();
                    gtConlai = Integer.valueOf(gia) - Integer.valueOf(phitrahang);
                    tvgtConlai.setText(Keys.getFormatedAmount(gtConlai));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Hoantien.this).show();
                else {
                    if (gtConlai > 0) {
                        if (!edlydoHT.getText().toString().trim().equals("") && !edphitrahang.getText().toString().trim().equals("")) {
                            phitrahang = edphitrahang.getText().toString().trim();
                            lydo = edlydoHT.getText().toString().trim();
                            new SendRequestHT().execute();
                        } else
                            Snackbar.make(v, "Phải nhập tất cả các trường.", Snackbar.LENGTH_LONG).show();
                    } else Snackbar.make(v, "Phí đổi hàng quá cao.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        Button exit= (Button) findViewById(R.id.cancel);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Hoantien.this).show();
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Main_Hoantien.this);
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
                            Main_Hoantien.this.finish();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    private void Anhxa() {
        tvgtConlai = (TextView) findViewById(R.id.tvgtConlai);
        edphitrahang = (EditText) findViewById(R.id.edpthBHHT);
        edlydoHT = (EditText) findViewById(R.id.edlydoBHHT);
        tvHtMaOrder = (TextView) findViewById(R.id.tvHtMaOrder);
        tvHtDate = (TextView) findViewById(R.id.tvHtDate);
        tvHtTime = (TextView) findViewById(R.id.tvHtTime);
        tvHtDatetoday = (TextView) findViewById(R.id.tvHtDatetoday);
        tvHtTimetoday = (TextView) findViewById(R.id.tvHtTimetoday);
        tvHtMaNV = (TextView) findViewById(R.id.tvHtMaNV);
        tvHtTenNV = (TextView) findViewById(R.id.tvHtTenNV);
        tvHtGhichuOrder = (TextView) findViewById(R.id.tvHtGhichu);
        tvChinhanhHT = (TextView) findViewById(R.id.tvChinhanhHT);
        tvChinhanhHTOrder = (TextView) findViewById(R.id.tvChinhanhHTOrder);
        tvHtTenKH = (TextView) findViewById(R.id.tvHtTenKH);
        tvHtSdtKH = (TextView) findViewById(R.id.tvHtSdtKH);
        lv = (ListView) findViewById(R.id.lvBHHT);
        tvHtGhichuKH = (TextView) findViewById(R.id.tvHtGhichuKH);
        tvHtTenNVNhan = (TextView) findViewById(R.id.tvHtTenNVNhan);
        tvHtMaNVNhan = (TextView) findViewById(R.id.tvHtMaNVNhan);
        btnxacnhan = (Button) findViewById(R.id.btnBHHT);
    }

    public class SendRequestHT extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){

            dialog = new ProgressDialog(Main_Hoantien.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được xử lý");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... arg0) {
            putData();
            addHoantienWeb();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            new CustomToast().Show_Toast(Main_Hoantien.this, findViewById(android.R.id.content), "Gửi dữ liệu thành công.");
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
            URL url = new URL(Keys.SCRIPT_BH_HT);
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
            postDataParams.put("gtConlai", gtConlai+"");
            postDataParams.put("phitrahang", phitrahang);
            postDataParams.put("lydo", lydo);

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
        startActivity(new Intent(Main_Hoantien.this, Main_Baohanh.class));
        finish();
    }

    public void addHoantienWeb(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            new CustomToast().Show_Toast(Main_Hoantien.this, findViewById(android.R.id.content), "Lỗi ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Hoantien.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_BHHT_WEB);
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
                params.put("gtConlai", gtConlai+"");
                params.put("phitrahang", phitrahang);
                params.put("lydo", lydo);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}