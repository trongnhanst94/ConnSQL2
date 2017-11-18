package com.example.windows10gamer.connsql.Bao_Hanh;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order;
import com.example.windows10gamer.connsql.Main_Menu;
import com.example.windows10gamer.connsql.Object.Sanpham;
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
            dateToday, timeToday, tenKH, sdtKH, ghichuOrder, ghichuKH, gtConlai, lydo, chinhanhOrder;
    ArrayList<Sanpham> sanpham = new ArrayList<>();
    ProgressDialog dialog;
    TextView tvHtMaOrder,tvHtDate,tvHtTime,tvHtMaNV,tvHtTenNV,tvHtGhichuOrder,tvHtTenKH,
            tvHtSdtKH,tvHtGhichuKH,tvHtTenNVNhan,tvHtMaNVNhan,tvHtDatetoday,tvHtTimetoday,tvChinhanhHT,tvChinhanhHTOrder ;
    int vitri;
    ListView lv;
    Adapter_Info_Order adapter_sales;
    ArrayList<Sanpham> arrayList;
    Button btnxacnhan;
    EditText edgtConlai, edlydoHT;
    String session_username = Main_Menu.session_username;
    String session_ma = Main_Menu.session_ma;
    String chinhanh = Main_Menu.chinhanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_hoantien);
        Anhxa();
        maBH = Keys.MaDonhang();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoOrder2");
        sanpham     = bundle.getParcelableArrayList("sanphamOrder");
        vitri       = bundle.getInt("vitri");
        maOrder     = bundle.getString("MaOrder");
        date        = bundle.getString("DateOrder");
        time        = bundle.getString("TimeOrder");
        chinhanhOrder = bundle.getString("ChinhanhOrder");
        tenNV       = bundle.getString("TenNVOrder");
        maNV        = bundle.getString("MaNVOrder");
        ten         = sanpham.get(vitri).getTen();
        ma          = sanpham.get(vitri).getMa();
        baohanh     = sanpham.get(vitri).getBaohanh();
        nguon       = sanpham.get(vitri).getNguon();
        ngaynhap    = sanpham.get(vitri).getNgaynhap();
        gia         = sanpham.get(vitri).getGiaban();
        von         = sanpham.get(vitri).getVon();
        tenKH       = bundle.getString("TenKH");
        sdtKH       = bundle.getString("SdtKH");
        ghichuKH    = bundle.getString("GhichuKH");
        ghichuOrder = bundle.getString("GhichuOrder");
        dateToday = Keys.getDateNow();
        timeToday = Keys.getCalam();
        arrayList = new ArrayList<>();
        arrayList.add(new Sanpham(ma, ten, baohanh, nguon, ngaynhap, von, gia));
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
        tvHtDate.setText(Keys.setDate(date));
        tvHtTime.setText(time);
        tvHtDatetoday.setText(Keys.setDate(dateToday));
        tvHtTimetoday.setText(timeToday);
        tvHtMaNV.setText("Mã NV: "+maNV);
        tvHtTenNV.setText("Tên NV: "+tenNV);
        tvHtTenKH.setText("Tên KH: "+tenKH);
        tvHtSdtKH.setText("SĐT KH: "+sdtKH);
        tvChinhanhHT.setText(chinhanh);
        tvChinhanhHTOrder.setText(chinhanhOrder);
        tvHtTenNVNhan.setText("Mã NV bảo hành: "+session_username);
        tvHtMaNVNhan.setText("Tên NV bảo hành: "+session_ma);
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edgtConlai.getText().toString().trim().equals("") && !edlydoHT.getText().toString().trim().equals("")) {
                    gtConlai = edgtConlai.getText().toString().trim();
                    lydo = edlydoHT.getText().toString().trim();
                    new SendRequestHT().execute();
                }
                else Snackbar.make(v, "Phải nhập tất cả các trường.", Snackbar.LENGTH_LONG).show();
            }
        });
        Button exit= (Button) findViewById(R.id.cancel);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Main_Hoantien.this);
                builder.setMessage("Bạn muốn hủy đơn bảo hành??");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();                        }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Main_Hoantien.this.finish();
                    }
                });
                builder.show();
            }
        });
    }

    private void Anhxa() {
        edgtConlai = (EditText) findViewById(R.id.edgtConlai);
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
            postDataParams.put("maBH", "BHHT_"+maBH);
            postDataParams.put("dateToday", dateToday);
            postDataParams.put("timeToday", timeToday);
            postDataParams.put("chinhanhToday", chinhanh);
            postDataParams.put("tenNVToday", session_username);
            postDataParams.put("maNVToday", session_ma);
            postDataParams.put("maOrder", maOrder);
            postDataParams.put("date", Keys.setDate(date));
            postDataParams.put("time", time);
            postDataParams.put("chinhanh", chinhanhOrder);
            postDataParams.put("time", time);
            postDataParams.put("tenNV", tenNV);
            postDataParams.put("maNV", maNV);
            postDataParams.put("ten", ten);
            postDataParams.put("ma", ma);
            postDataParams.put("baohanh", baohanh);
            postDataParams.put("nguon", nguon);
            postDataParams.put("ngaynhap", Keys.setNN(ngaynhap));
            postDataParams.put("von", von);
            postDataParams.put("gia", gia);
            postDataParams.put("ghichuOrder", ghichuOrder);
            postDataParams.put("tenKH", tenKH);
            postDataParams.put("sdtKH", sdtKH);
            postDataParams.put("ghichuKH", ghichuKH);
            postDataParams.put("gtConlai", gtConlai);
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
                            Toast.makeText(Main_Hoantien.this, "Lỗi ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Main_Hoantien.this, "Lỗi "+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_BHHT_WEB);
                params.put("maBH", "BHHT_"+maBH);
                params.put("dateToday", dateToday);
                params.put("timeToday", timeToday);
                params.put("chinhanhToday", chinhanh);
                params.put("tenNVToday", session_username);
                params.put("maNVToday", session_ma);
                params.put("maOrder", maOrder);
                params.put("date", Keys.setDate(date));
                params.put("time", time);
                params.put("chinhanh", chinhanhOrder);
                params.put("tenNV", tenNV);
                params.put("maNV", maNV);
                params.put("ten", ten);
                params.put("ma", ma);
                params.put("baohanh", baohanh);
                params.put("nguon", nguon);
                params.put("ngaynhap", Keys.setNN(ngaynhap));
                params.put("von", von);
                params.put("gia", gia);
                params.put("ghichuOrder", ghichuOrder);
                params.put("tenKH", tenKH);
                params.put("sdtKH", sdtKH);
                params.put("ghichuKH", ghichuKH);
                params.put("gtConlai", gtConlai);
                params.put("lydo", lydo);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}