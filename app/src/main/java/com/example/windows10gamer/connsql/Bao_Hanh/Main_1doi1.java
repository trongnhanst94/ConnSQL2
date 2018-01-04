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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

public class Main_1doi1 extends AppCompatActivity {
    String maOrder, date, time, tenNV, maNV, ten, ma, baohanh, nguon, gia, ngaynhap, von,maBH,phibaohanh,
            dateToday, timeToday, tenKH, sdtKH, ghichuOrder, ghichuKH, chinhanhOrder, lydo, ma_moi, ten_moi, baohanh_moi, nguon_moi, ngaynhap_moi, von_moi, gia_moi;
    ArrayList<Sanpham_gio> sanpham = new ArrayList<>();
    ProgressDialog dialog;
    SharedPreferences shared, sp;
    TextView tvd1MaOrder,tvd1Date,tvd1Time,tvd1MaNV,tvd1TenNV,tvd1GhichuOrder,tvd1TenKH,
            tvd1SdtKH,tvd1GhichuKH,tvd1TenNVNhan,tvd1MaNVNhan,tvd1Datetoday,tvd1Timetoday,tvChinhanh1D1,tvChinhanh1D1Order ;
    int vitri;
    ListView lv, lv_moi;
    Adapter_Info_Order adapter_sales;
    ArrayList<Sanpham_gio> arrayList;
    Button btnxacnhan, btn1doi1;
    EditText edlydod1, edphibaohanh;
    Adapter_Info_Order adapter_moi;
    ArrayList<Sanpham_gio>  array_moi;
    String session_username;
    String chinhanh;
    String session_ma, gio, gio_moi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_1doi1);
        Anhxa();
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        sp = getSharedPreferences("login", MODE_PRIVATE);
        session_username = sp.getString("shortName", "");
        session_ma = sp.getString("ma", "");
        maBH = "BH1D1_"+Keys.MaDonhang();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoOrder4");
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
        btnxacnhan.setEnabled(false);
        btnxacnhan.setBackgroundColor(getResources().getColor(R.color.aaaaa));
        dateToday = Keys.getDateNow();
        timeToday = Keys.getCalam(chinhanh);
        array_moi = new ArrayList<>();
        arrayList = new ArrayList<>();
        arrayList.add(new Sanpham_gio(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia));
        adapter_sales = new Adapter_Info_Order(Main_1doi1.this, arrayList);
        lv.setAdapter(adapter_sales);
        adapter_moi = new Adapter_Info_Order(Main_1doi1.this, array_moi);
        lv_moi.setAdapter(adapter_moi);
        if (ghichuKH.equals("")){
            tvd1GhichuKH.setVisibility(View.GONE);
        } else {
            tvd1GhichuKH.setText("Ghi chú KH: "+ghichuKH);
        }
        if (ghichuOrder.equals("")){
            tvd1GhichuOrder.setVisibility(View.GONE);
        } else {
            tvd1GhichuOrder.setText("Ghi chú đơn hàng: "+ghichuOrder);
        }
        tvd1MaOrder.setText("Mã đơn hàng: "+maOrder);
        tvd1Date.setText(date);
        tvd1Time.setText(time);
        tvd1Datetoday.setText(dateToday);
        tvd1Timetoday.setText(timeToday);
        tvd1MaNV.setText("Mã số: "+maNV);
        tvd1TenNV.setText("Tên nhân viên: "+tenNV);
        tvd1TenKH.setText("Tên KH: "+tenKH);
        tvChinhanh1D1.setText(chinhanh);
        tvChinhanh1D1Order.setText(chinhanhOrder);
        tvd1SdtKH.setText("SĐT KH: "+sdtKH);
        tvd1TenNVNhan.setText("Mã NV bảo hành: "+session_username);
        tvd1MaNVNhan.setText("Tên NV bảo hành: "+session_ma);
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_1doi1.this).show();
                else {
                    if (!edlydod1.getText().toString().trim().equals("") && !edphibaohanh.getText().toString().trim().equals("")) {
                        phibaohanh = edphibaohanh.getText().toString().trim();
                        lydo = edlydod1.getText().toString().trim();
                        new SendRequestD1().execute();
                    } else
                        Snackbar.make(v, "Phải nhập tất cả các trường.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        btn1doi1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_1doi1.this).show();
                else {
                    scanSanpham_gio1doi1();
                }
            }
        });
        Button exit= (Button) findViewById(R.id.cancel);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_1doi1.this).show();
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Main_1doi1.this);
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
                            Main_1doi1.this.finish();
                        }
                    });
                    builder.show();
                }
            }
        });
    }

    private void scanSanpham_gio1doi1() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Quét mã code");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null) {
            String scannedData = result.getContents();
            if (scannedData != null) {
                try{
                    StringTokenizer st = new StringTokenizer(scannedData, ";");
                    ma_moi = st.nextToken();
                    ten_moi = st.nextToken();
                    baohanh_moi = st.nextToken();
                    nguon_moi = st.nextToken();
                    ngaynhap_moi = st.nextToken();
                    von_moi = st.nextToken();
                    gia_moi = st.nextToken();
                    gio_moi = Keys.getTimeNow();
                    array_moi.add(new Sanpham_gio(gio_moi, ma_moi, ten_moi, baohanh_moi, nguon_moi, ngaynhap_moi, von_moi, gia_moi));
                    btnxacnhan.setEnabled(true);
                    btnxacnhan.setBackgroundColor(getResources().getColor(R.color.cam));
                    btn1doi1.setEnabled(false);
                    btn1doi1.setBackgroundColor(getResources().getColor(R.color.aaaaa));
                    adapter_moi.notifyDataSetChanged();
                }   catch (NoSuchElementException nse) {
                    new CustomToast().Show_Toast(Main_1doi1.this, findViewById(android.R.id.content), "Lỗi định dạng nhãn");
                }
            }
        }
    }

    private void Anhxa() {
        btn1doi1 = (Button) findViewById(R.id.btn_1doi1_scan_now);
        edphibaohanh = (EditText) findViewById(R.id.edpbhBHD1);
        edlydod1 = (EditText) findViewById(R.id.edlydoBHD1);
        tvd1MaOrder = (TextView) findViewById(R.id.tvd1MaOrder);
        tvd1Date = (TextView) findViewById(R.id.tvd1Date);
        tvd1Time = (TextView) findViewById(R.id.tvd1Time);
        tvd1Datetoday = (TextView) findViewById(R.id.tvd1Datetoday);
        tvd1Timetoday = (TextView) findViewById(R.id.tvd1Timetoday);
        tvd1MaNV = (TextView) findViewById(R.id.tvd1MaNV);
        tvd1TenNV = (TextView) findViewById(R.id.tvd1TenNV);
        tvd1GhichuOrder = (TextView) findViewById(R.id.tvd1Ghichu);
        tvd1TenKH = (TextView) findViewById(R.id.tvd1TenKH);
        tvd1SdtKH = (TextView) findViewById(R.id.tvd1SdtKH);
        tvChinhanh1D1 = (TextView) findViewById(R.id.tvChinhanh1D1);
        tvChinhanh1D1Order = (TextView) findViewById(R.id.tvChinhanh1D1Order);
        lv = (ListView) findViewById(R.id.lvBHD1);
        tvd1GhichuKH = (TextView) findViewById(R.id.tvd1GhichuKH);
        tvd1TenNVNhan = (TextView) findViewById(R.id.tvd1TenNVNhan);
        tvd1MaNVNhan = (TextView) findViewById(R.id.tvd1MaNVNhan);
        btnxacnhan = (Button) findViewById(R.id.btnBHD1);
        lv_moi = (ListView) findViewById(R.id.lvBH1D1_moi);
    }

    public class SendRequestD1 extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){

            dialog = new ProgressDialog(Main_1doi1.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được xử lý");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... arg0) {
            putData();
            add1Doi1Web();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            new CustomToast().Show_Toast(Main_1doi1.this, findViewById(android.R.id.content), "Gửi dữ liệu thành công.");
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
            URL url = new URL(Keys.SCRIPT_BH_1DOI1);
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
            postDataParams.put("gio_moi", gio_moi);
            postDataParams.put("ma_moi", ma_moi);
            postDataParams.put("ten_moi", ten_moi);
            postDataParams.put("baohanh_moi", baohanh_moi);
            postDataParams.put("nguon_moi", nguon_moi);
            postDataParams.put("ngaynhap_moi", ngaynhap_moi);
            postDataParams.put("von_moi", von_moi);
            postDataParams.put("gia_moi", gia_moi);
            postDataParams.put("phibaohanh", phibaohanh);
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
        startActivity(new Intent(Main_1doi1.this, Main_Baohanh.class));
        finish();
    }

    public void add1Doi1Web(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            new CustomToast().Show_Toast(Main_1doi1.this, findViewById(android.R.id.content), "Lỗi ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_1doi1.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_BH1DOI1_WEB);
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
                params.put("gio_moi", gio_moi);
                params.put("ten_moi", ten_moi);
                params.put("ma_moi", ma_moi);
                params.put("baohanh_moi", baohanh_moi);
                params.put("nguon_moi", nguon_moi);
                params.put("ngaynhap_moi", ngaynhap_moi);
                params.put("von_moi", von_moi);
                params.put("gia_moi", gia_moi);
                params.put("phibaohanh", phibaohanh);
                params.put("lydo", lydo);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}