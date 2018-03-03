package com.example.windows10gamer.connsql.Bao_Hanh;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.Mylistview;
import com.example.windows10gamer.connsql.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

public class Main_Doidungthu extends AppCompatActivity {
    String maOrder, date, time, tenNV, maNV, ten, ma, baohanh, nguon, gia, ngaynhap, von,maBH,
            dateToday, timeToday, tenKH, sdtKH, ghichuOrder, chinhanhOrder, ghichuKH, lydo,ma_moi, ten_moi, baohanh_moi, nguon_moi, ngaynhap_moi, von_moi, gia_moi;
    ArrayList<Sanpham_gio> sanpham = new ArrayList<>();
    ProgressDialog dialog;
    TextView tvDlkMaOrder,tvDlkDate,tvDlkTime,tvDlkMaNV,tvDlkTenNV,tvDlkGhichuOrder,tvDlkTenKH, tvDlkChenhlech,
            tvDlkSdtKH,tvDlkGhichuKH,tvDlkTenNVNhan,tvDlkMaNVNhan,tvDlkDatetoday,tvDlkTimetoday, tvTongdonhang,tvChinhanhDLK,tvChinhanhDLKOrder ;
    int vitri;
    Mylistview lv_moi;
    ListView lv;
    Adapter_Info_Order adapter_sales;
    Adapter_Info_Order adapter_moi;
    ArrayList<Sanpham_gio> arrayList, array_moi;
    Button btnxacnhan;
    EditText edlydoDLK, edphidoiSP;
    String session_username;
    String session_ma;
    int total = 0;int chenhlech = 0, phidoiSP = 0;
    String chinhanh, gio, gio_moi;
    SharedPreferences shared, sp;
    ImageView ivAvatar, ivAvatar2;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doidungthu);
        Anhxa();
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        sp = getSharedPreferences("login", MODE_PRIVATE);
        session_username = sp.getString("shortName", "");
        session_ma = sp.getString("ma", "");
        Glide.with(Main_Doidungthu.this).load(shared.getString("img", "")).override(300,300).fitCenter().into(ivAvatar);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoOrder6");
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
        array_moi = new ArrayList<>();
        btnxacnhan.setEnabled(false);
        btnxacnhan.setBackgroundColor(getResources().getColor(R.color.aaaaa));
        arrayList.add(new Sanpham_gio(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia));
        adapter_sales = new Adapter_Info_Order(Main_Doidungthu.this, arrayList);
        lv.setAdapter(adapter_sales);
        adapter_moi = new Adapter_Info_Order(Main_Doidungthu.this, array_moi);
        lv_moi.setAdapter(adapter_moi);
        if (ghichuKH.equals("")){
            tvDlkGhichuKH.setVisibility(View.GONE);
        } else {
            tvDlkGhichuKH.setText("Ghi chú KH: "+ghichuKH);
        }
        if (ghichuOrder.equals("")){
            tvDlkGhichuOrder.setVisibility(View.GONE);
        } else {
            tvDlkGhichuOrder.setText("Ghi chú đơn hàng: "+ghichuOrder);
        }
        tvDlkMaOrder.setText("Mã đơn hàng: "+maOrder);
        tvDlkDate.setText(date);
        tvDlkTime.setText(time);
        tvDlkDatetoday.setText(dateToday);
        tvDlkTimetoday.setText(timeToday);
        tvDlkMaNV.setText("Mã số: "+maNV);
        tvDlkTenNV.setText("Tên nhân viên: "+tenNV);
        tvDlkTenKH.setText("Tên KH: "+tenKH);
        tvDlkSdtKH.setText("SĐT KH: "+sdtKH);
        tvChinhanhDLK.setText(chinhanh);
        tvChinhanhDLKOrder.setText(chinhanhOrder);
        tvDlkTenNVNhan.setText("Mã NV bảo hành: "+session_username);
        tvDlkMaNVNhan.setText("Tên NV bảo hành: "+session_ma);
        GetUser(Main_Doidungthu.this, maNV);
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Doidungthu.this).show();
                else {
                    if (!edlydoDLK.getText().toString().trim().equals("") && !edphidoiSP.getText().toString().trim().equals("")) {
                        btnxacnhan.setEnabled(false);
                        btnxacnhan.setBackgroundColor(getResources().getColor(R.color.aaaaa));
                        maBH = "BHDLK_"+ Keys.MaDonhang();
                        phidoiSP = Integer.valueOf(edphidoiSP.getText().toString().trim());
                        lydo = edlydoDLK.getText().toString().trim();
                        chenhlech = total - Integer.valueOf(gia) + phidoiSP;
                        new SendRequestDLK().execute();
                    } else
                        Snackbar.make(v, "Phải nhập tất cả các trường.", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        Button exit= findViewById(R.id.cancel);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Doidungthu.this).show();
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Main_Doidungthu.this);
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
                            Main_Doidungthu.this.finish();
                        }
                    });
                    builder.show();
                }
            }
        });
        tvDlkChenhlech.setText(Keys.setMoney(0));
        edphidoiSP.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (edphidoiSP.getText().toString().trim().equals("")){
                    tvDlkChenhlech.setText(Keys.setMoney(chenhlech));
                } else {
                    phidoiSP = Integer.valueOf(edphidoiSP.getText().toString().trim());
                    tvDlkChenhlech.setText(Keys.setMoney(chenhlech + phidoiSP));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
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
        edphidoiSP = findViewById(R.id.edphidoiSP);
        edlydoDLK = findViewById(R.id.edlydoBHDLK);
        tvDlkMaOrder = findViewById(R.id.tvDlkMaOrder);
        tvDlkDate = findViewById(R.id.tvDlkDate);
        tvDlkChenhlech = findViewById(R.id.tvDlkChenhlech);
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
        btnxacnhan = findViewById(R.id.btnBHDLK);
    }

    public void scanSanpham(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Quét mã code");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(true);
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
                    total = total + Integer.parseInt(gia_moi);
                    tvTongdonhang.setText(Keys.setMoney(total));
                    btnxacnhan.setEnabled(true);
                    btnxacnhan.setBackgroundColor(getResources().getColor(R.color.cam));
                    chenhlech = total-Integer.valueOf(gia);
                    tvDlkChenhlech.setText(Keys.setMoney(chenhlech));
                    adapter_moi.notifyDataSetChanged();
                    scanSanpham(findViewById(android.R.id.content));
                }   catch (NoSuchElementException nse) {
                    new CustomToast().Show_Toast(Main_Doidungthu.this, findViewById(android.R.id.content), "Lỗi định dạng nhãn");
                }
            }
        }
    }

    public void huy(){
        finish();
    }

    public class SendRequestDLK extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(Main_Doidungthu.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được xử lý");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... arg0) {
            int j;
            for (j = 0 ;j < array_moi.size(); j++){
                putData(j);
                addDlkWeb(j);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            new CustomToast().Show_Toast(Main_Doidungthu.this, findViewById(android.R.id.content), "Gửi dữ liệu thành công.");
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

    public String putData(int j){
        try {

            URL url = new URL(Keys.SCRIPT_BH_DDT);
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
            postDataParams.put("phidoiSP", phidoiSP);
            postDataParams.put("gio_moi", array_moi.get(j).getGio());
            postDataParams.put("ten_moi", array_moi.get(j).getTen());
            postDataParams.put("ma_moi", array_moi.get(j).getMa());
            postDataParams.put("baohanh_moi", array_moi.get(j).getBaohanh());
            postDataParams.put("nguon_moi", array_moi.get(j).getNguon());
            postDataParams.put("ngaynhap_moi", array_moi.get(j).getNgaynhap());
            postDataParams.put("von_moi", array_moi.get(j).getVon());
            postDataParams.put("gia_moi", array_moi.get(j).getGiaban());
            postDataParams.put("lydo", lydo);
            postDataParams.put("chenhlech", chenhlech);

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
        startActivity(new Intent(Main_Doidungthu.this, Main_Baohanh.class));
        finish();
    }

    public void addDlkWeb(final int j){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            new CustomToast().Show_Toast(Main_Doidungthu.this, findViewById(android.R.id.content), "Lỗi ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Doidungthu.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_BHDDT_WEB);
                params.put("id", maBH+array_moi.get(j).getGio());
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
                params.put("phidoiSP", phidoiSP+"");
                params.put("gio_moi", array_moi.get(j).getGio());
                params.put("ten_moi", array_moi.get(j).getTen());
                params.put("ma_moi", array_moi.get(j).getMa());
                params.put("baohanh_moi", array_moi.get(j).getBaohanh());
                params.put("nguon_moi", array_moi.get(j).getNguon());
                params.put("ngaynhap_moi", array_moi.get(j).getNgaynhap());
                params.put("von_moi", array_moi.get(j).getVon());
                params.put("gia_moi", array_moi.get(j).getGiaban());
                params.put("lydo", lydo);
                params.put("chenhlech", chenhlech+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}


