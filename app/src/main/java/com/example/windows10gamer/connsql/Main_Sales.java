package com.example.windows10gamer.connsql;

/**
 * Created by EVRESTnhan on 9/30/2017.
 */


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Adapter.Adapter_Quatang;
import com.example.windows10gamer.connsql.Adapter.Adapter_Sales;
import com.example.windows10gamer.connsql.Adapter.Adapter_Spinner_NV;
import com.example.windows10gamer.connsql.Kiem_Kho.Main_Ketqua_Kiemkho;
import com.example.windows10gamer.connsql.Object.Quatang;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.Mylistview;
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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

public class Main_Sales extends AppCompatActivity {
    EditText edGiamgia;
    CheckBox rbGiamgia;
    CheckBox rbTangqua;
    TextView tvManhanvien, tvTongdonhang, tvTennhanvien, tvSalesDate, tvSalesTime, tvChinhanhSales, tvgiatriMagiam, tvphaithu;
    EditText edKhachhang, edSodienthoai, edGhichudonhang, edGhichukhachhang;
    ListView  listKhuyenmai;
    ImageView ivDoinv;
    Mylistview listView;
    ArrayList<Sanpham_gio> arrayList, khuyenmaiList;
    ArrayList<Quatang> quatang;
    ArrayAdapter<String> adapterA;
    Adapter_Sales adapter;
    ArrayList<Sanpham> a = new ArrayList<Sanpham>();
    Adapter_Quatang adapter2;
    ArrayAdapter<String> mAdapter;
    int total = 0, giamgia = 0;
    View view;
    Switch switchChangeNV;
    boolean[] checkedItem;
    final ArrayList<String> itemKM = new ArrayList<>();
    ArrayList<String> realtime = new ArrayList<>();
    LinearLayout lnTangqua, lnGiamgia, ln1, ln2, lnNVDefault, lnNVChange;
    ProgressDialog pDialog;
    SharedPreferences shared;
    Button btnXacnhan, btnCancel, btn_scan_now, btnkiemtraMGG;
    String session_username, session_ma, session_username1, session_ma1, hinhthuc = "None";
    String maGiamgia, giatriMagiamgia = "";
    String ten, ma, nguon, ca, ngaynhap, baohanh, gia, ngay, gio, chinhanh, von, tenkhachhang, sodienthoaikhachhang, ghichukhachhang, ghichusanpham, madonhang;
    Spinner snA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sales);
        tvTongdonhang     = (TextView) findViewById(R.id.tvTongdonhang);
        tvManhanvien      = (TextView) findViewById(R.id.tvManhanvien);
        tvTennhanvien     = (TextView) findViewById(R.id.tvTennhanvien);
        rbGiamgia         = (CheckBox) findViewById(R.id.rbGiamgia);
        rbTangqua         = (CheckBox) findViewById(R.id.rbTangqua);
        tvSalesDate       = (TextView) findViewById(R.id.tvSalesDate);
        tvSalesTime       = (TextView) findViewById(R.id.tvSalesTime);
        tvChinhanhSales   = (TextView) findViewById(R.id.tvChinhanhSales);
        edKhachhang       = (EditText) findViewById(R.id.edKhachhang);
        edSodienthoai     = (EditText) findViewById(R.id.edSodienthoai);
        edGhichudonhang   = (EditText) findViewById(R.id.edGhichudonhang);
        edGhichukhachhang = (EditText) findViewById(R.id.edGhichukhachhang);
        btn_scan_now      = (Button)   findViewById(R.id.btn_scan_now) ;
        btnXacnhan        = (Button)   findViewById(R.id.submitSanpham) ;
        btnkiemtraMGG     = (Button)   findViewById(R.id.btnkiemtraMGG) ;
        btnCancel         = (Button)   findViewById(R.id.cancelSanpham) ;
        listView          = (Mylistview) findViewById(R.id.lvSanpham);
        listKhuyenmai     = (ListView) findViewById(R.id.lvKhuyenmai);
        lnTangqua         = (LinearLayout) findViewById(R.id.lnTangqua);
        lnGiamgia         = (LinearLayout) findViewById(R.id.lngiamgia);
        edGiamgia         = (EditText) findViewById(R.id.edGiamgia) ;
        tvgiatriMagiam    = (TextView) findViewById(R.id.tvgiatriMagiam);
        tvphaithu         = (TextView) findViewById(R.id.tvphaithu);
        ln1               = (LinearLayout) findViewById(R.id.ln1);
        ln2               = (LinearLayout) findViewById(R.id.ln2);
        ivDoinv           = (ImageView) findViewById(R.id.ivDoinv);
        snA               = (Spinner) findViewById(R.id.spChange);
        lnNVDefault       = (LinearLayout) findViewById(R.id.lnNVDefault);
        lnNVChange        = (LinearLayout) findViewById(R.id.lnNVChange);
        switchChangeNV    = (Switch) findViewById(R.id.switchChangeNV);
        lnGiamgia.setVisibility(View.GONE);
        lnTangqua.setVisibility(View.GONE);
        ln1.setVisibility(View.GONE);
        ln2.setVisibility(View.GONE);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        ngay = getDate();
        ca = Keys.getCalam(chinhanh);
        madonhang = Keys.MaDonhang();
        tvSalesDate.setText(ngay);
        tvSalesTime.setText(ca);
        Intent intentput  = getIntent();
        session_username1  = intentput.getStringExtra("session_username");
        session_ma1        = intentput.getStringExtra("session_ma");
        tvManhanvien.setText("Mã số: " + session_ma1);
        tvTennhanvien.setText("Tên nhân viên: " + session_username1);
        tvChinhanhSales.setText(chinhanh);
        edGhichudonhang.setSelected(false);
        arrayList     = new ArrayList<>();
        khuyenmaiList = new ArrayList<>();
        quatang = new ArrayList<>();
        rbTangqua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rbGiamgia.setChecked(false);
                    lnGiamgia.setVisibility(View.GONE);
                    lnTangqua.setVisibility(View.VISIBLE);
                } else {
                    lnTangqua.setVisibility(View.GONE);
                }
            }
        });
        rbGiamgia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rbTangqua.setChecked(false);
                    lnGiamgia.setVisibility(View.VISIBLE);
                    lnTangqua.setVisibility(View.GONE);
                } else {
                    lnGiamgia.setVisibility(View.GONE);
                    ln1.setVisibility(View.GONE);
                    ln2.setVisibility(View.GONE);
                    giatriMagiamgia = "0";
                    maGiamgia = "";
                }
            }
        });
        rbTangqua.setEnabled(false);
        adapter       = new Adapter_Sales(Main_Sales.this, R.layout.adapter_sales, arrayList);
        listView.setAdapter(adapter);
        new GetDataQuaTang().execute();
        btn_scan_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scanSanpham();
            }
        });
        edGiamgia.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                int totalTam = total;
                if (edGiamgia.getText().toString().equals("")){
                    giamgia = 0;
                    tvTongdonhang.setText(getFormatedAmount(total));
                } else {
                    totalTam = totalTam - giamgia;
                    tvTongdonhang.setText(getFormatedAmount(totalTam));
                    if (totalTam < 0 ){
                        btnXacnhan.setEnabled(false);
                        btnXacnhan.setBackgroundColor(getResources().getColor(R.color.aaaaa));
                    } else {
                        btnXacnhan.setEnabled(true);
                        btnXacnhan.setBackgroundColor(getResources().getColor(R.color.cam));
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(Main_Sales.this);
                    builder.setMessage("Bạn muốn hủy đơn hàng??");
                    builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();                        }
                    });
                    builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Main_Sales.this.finish();
                        }
                    });
                    builder.show();
                }
            });
        btnkiemtraMGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edGiamgia.getText().toString().trim().equals("")){
                    maGiamgia = edGiamgia.getText().toString().trim();
                    giatriMagiamgia = "0";
                    new GetDataGiamGia().execute();
                } else {
                    giatriMagiamgia = "0";
                    maGiamgia = "";
                    new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Chưa nhập mã giảm giá!");
                }
            }
        });
        session_ma = session_ma1;
        session_username = session_username1;
        switchChangeNV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    lnNVDefault.setVisibility(View.GONE);
                    lnNVChange.setVisibility(View.VISIBLE);
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
                                    session_ma = result.get(position).getMa();
                                    session_username = result.get(position).getShortName();
                                    hinhthuc = "Bán hộ bởi "+session_username1;
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> arg0) {}
                            });

                            btnXacnhan.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (arrayList.size() != 0){
                                        tenkhachhang = edKhachhang.getText().toString();
                                        sodienthoaikhachhang = edSodienthoai.getText().toString();
                                        ghichukhachhang = edGhichukhachhang.getText().toString();
                                        ghichusanpham = edGhichudonhang.getText().toString();
                                        new SendRequest().execute();
                                        ResetActivity();
                                    }
                                    else new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Chưa nhập mã giảm giá!");

                                }
                            });
                        }
                    });
                } else {
                    lnNVDefault.setVisibility(View.VISIBLE);
                    lnNVChange.setVisibility(View.GONE);
                    session_ma = session_ma1;
                    session_username = session_username1;
                    hinhthuc = "None";
                }
            }
        });
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (arrayList.size() != 0){
                    tenkhachhang = edKhachhang.getText().toString();
                    sodienthoaikhachhang = edSodienthoai.getText().toString();
                    ghichukhachhang = edGhichukhachhang.getText().toString();
                    ghichusanpham = edGhichudonhang.getText().toString();
                    new SendRequest().execute();
                    ResetActivity();
                    new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Tạo đơn hàng thành công!");
                }
                else new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Chưa nhập mã giảm giá!");
            }
        });
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

    public interface VolleyCallback{
        void onSuccess(ArrayList<User> result);
    }

    private void scanSanpham() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Quét mã code");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    private String getFormatedAmount(int amount){
        String number = NumberFormat.getNumberInstance(Locale.US).format(amount);
        String formatnumber = number.replace(",",".");
        return formatnumber+"đ";
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null) {
            String scannedData = result.getContents();
            if (scannedData != null) {
                try{
                    realtime.add(Keys.getTimeNow());
                    StringTokenizer st = new StringTokenizer(scannedData, ";");
                    ma = st.nextToken();
                    ten = st.nextToken();
                    gio = Keys.getTimeNow();
                    baohanh = st.nextToken();
                    nguon = st.nextToken();
                    ngaynhap = st.nextToken();
                    von = st.nextToken();
                    gia = st.nextToken();
                    arrayList.add(new Sanpham_gio(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia));
                    total = total + Integer.parseInt(gia);
                    tvTongdonhang.setText(getFormatedAmount(total));
                    itemKM.clear();
                    for (int i = 0; i<quatang.size(); i++){
                        if (total >= quatang.get(i).getToPrice() && total < quatang.get(i).getFromPrice()){
                            itemKM.add(quatang.get(i).getTen());
                        }
                    }
                    if (itemKM.size() == 0){
                        rbTangqua.setEnabled(false);
                    } else {
                        rbTangqua.setEnabled(true);
                    }
                    rbTangqua.setChecked(false);
                    listKhuyenmai.setAdapter(null);
                    adapter.notifyDataSetChanged();
                    rbGiamgia.setChecked(false);
                    lnGiamgia.setVisibility(View.GONE);
                    ln1.setVisibility(View.GONE);
                    ln2.setVisibility(View.GONE);
                    giatriMagiamgia = "0";
                    maGiamgia = "";
                    edGiamgia.setText("");
                }   catch (NoSuchElementException nse) {
                    new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Không đúng mã!");
                }
            }
        }
    }

    public void DeleteSP(final String msp){
        for (int i =  0; i <= arrayList.size(); i++){
            if (arrayList.get(i).getMa() == msp) {
                total = total - Integer.parseInt(arrayList.get(i).getGiaban());
                tvTongdonhang.setText(getFormatedAmount(total));
                arrayList.remove(i);
                realtime.remove(i);
                break;
            }
        }
        itemKM.clear();
        for (int i = 0; i<quatang.size(); i++){
            if (total >= quatang.get(i).getToPrice() && total < quatang.get(i).getFromPrice()){
                itemKM.add(quatang.get(i).getTen());
            }
        }
        if (itemKM.size() == 0){
            rbTangqua.setEnabled(false);
        } else {
            rbTangqua.setEnabled(true);
        }
        rbTangqua.setChecked(false);
        listKhuyenmai.setAdapter(null);
        adapter.notifyDataSetChanged();

    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(new Date());
        return date;
    }

    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){
            showProgressDialog();
        }

        protected String doInBackground(String... arg0) {
            int j = 0;
            int i = 0;
            while (j < arrayList.size()){
                putData(j);
                addOrderWeb(j);
                j++;
            }
            if (a.size() > 0 || giamgia!=0){
                if (giamgia != 0){
                    a.add(new Sanpham("","","","","","",""));
                }
                while (i < a.size()){
                    putDataKM(i);
                    addKMWeb(i);
                    i++;
                }
            }
            if (!maGiamgia.equals("")){
                new DeleteMGG().execute();
                deleteMagiamgiaWeb();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dismissProgressDialog();
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
            // Link Script
            URL url = new URL(Keys.SCRIPT_BANHANG);

            // Load Json object
            JSONObject postDataParams = new JSONObject();

            postDataParams.put("salesMadonhang", "SA_"+madonhang);
            postDataParams.put("salesNgay", ngay);
            postDataParams.put("salesCa", ca);
            postDataParams.put("salesGio", arrayList.get(j).getGio());
            postDataParams.put("salesChinhanh", chinhanh);
            postDataParams.put("SalesTennhanvien", session_username);
            postDataParams.put("salesManhanvien", session_ma);
            postDataParams.put("salesMasanpham", arrayList.get(j).getMa());
            postDataParams.put("salesTensanpham", arrayList.get(j).getTen());
            postDataParams.put("salesBaohanhsanpham", arrayList.get(j).getBaohanh());
            postDataParams.put("salesNguonsanpham", arrayList.get(j).getNguon());
            postDataParams.put("salesNgaynhap", arrayList.get(j).getNgaynhap());
            postDataParams.put("salesVonsanpham", arrayList.get(j).getVon());
            postDataParams.put("salesGiasanpham", arrayList.get(j).getGiaban());
            postDataParams.put("salesGiamgia", giatriMagiamgia);
            postDataParams.put("salesGhichusanpham", ghichusanpham);
            postDataParams.put("salesTenkhachhang", tenkhachhang);
            postDataParams.put("salesSodienthoaikhachhang", sodienthoaikhachhang);
            postDataParams.put("salesGhichukhachhang", ghichukhachhang);
            postDataParams.put("salesHinhthuc", hinhthuc);
            j++;

            Log.e("params", postDataParams.toString());

            // Kết nối HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
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
                return new String("");
            }
        } catch (Exception e) {
            return new String("");
        }
    }
//***************************************************************************************************************************
    class GetDataQuaTang extends AsyncTask<Void, Void, Void> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.urlQT);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DANHSACHQUATANG);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    quatang.add(new Quatang(
                                            object.getString("msp"),
                                            object.getString("ten"),
                                            object.getString("baohanh"),
                                            object.getString("nguon"),
                                            object.getString("ngaynhap"),
                                            object.getInt("von"),
                                            object.getInt("giaban"),
                                            object.getInt("toPrice"),
                                            object.getInt("fromPrice")
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
            if(quatang.size() > 0) {
                setList(quatang);
            }
        }
    }
//***************************************************************************************************************************

    class GetDataGiamGia extends AsyncTask<Void, Void, Void> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            showProgressDialog();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.urlGG);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.MAGIAMGIA);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    if (maGiamgia.equals(object.getString("maGiamgia")) && (object.getString("trangThai").equals("On"))){
                                        giatriMagiamgia = object.getString("giaTri");
                                    }
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
            if(giatriMagiamgia.equals("") || giatriMagiamgia.equals("0")) {
                new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Mã không hợp lệ!");
                edGiamgia.setText("");
                giatriMagiamgia = "0";
                maGiamgia = "";
            } else {
                ln1.setVisibility(View.VISIBLE);
                ln2.setVisibility(View.VISIBLE);
            }
            tvgiatriMagiam.setText(Keys.getFormatedAmount(Integer.parseInt(giatriMagiamgia)));
            tvphaithu.setText(Keys.getFormatedAmount(total - Integer.parseInt(giatriMagiamgia)));
            dismissProgressDialog();
        }
    }

    private void setList(final ArrayList<Quatang> quatang) {
        this.quatang = quatang;
        checkedItem = new boolean[quatang.size()];

        rbTangqua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                a.clear();
                AlertDialog.Builder dialog = new AlertDialog.Builder(Main_Sales.this);
                View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
                dialog.setTitle("Chọn quà tặng");
                final Spinner spinner = (Spinner) mView.findViewById(R.id.spinnerKM);
                mAdapter = new ArrayAdapter<>(Main_Sales.this, android.R.layout.simple_spinner_item, itemKM);
                mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(mAdapter);
                dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i = 0; i<quatang.size(); i++){
                            if (spinner.getSelectedItem().equals(quatang.get(i).getTen())){
                                a.add(new Sanpham(
                                        quatang.get(i).getMa()+"",
                                        quatang.get(i).getTen()+"",
                                        quatang.get(i).getBaohanh()+"",
                                        quatang.get(i).getNguon()+"",
                                        quatang.get(i).getNgaynhap()+"",
                                        quatang.get(i).getVon()+"",
                                        quatang.get(i).getGia()+""
                                ));
                            }
                        }
                        adapter2 = new Adapter_Quatang(Main_Sales.this, R.layout.adapter_quatang, a);
                        listKhuyenmai.setAdapter(adapter2);
                        itemKM.clear();
                    }
                });
                dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        itemKM.clear();
                    }
                });
                dialog.setView(mView);
                AlertDialog al = dialog.create();
                al.show();
            }
        });

    }
//***************************************************************************************************************************
//***************************************************************************************************************************

    public String putDataKM(int j){
        try {
            // Link Script
            URL url = new URL(Keys.SCRIPT_KHUYENMAI);

            // Load Json object
            JSONObject postDataParamsKM = new JSONObject();

            postDataParamsKM.put("salesMadonhang", "KM_"+madonhang);
            postDataParamsKM.put("salesNgay", ngay);
            postDataParamsKM.put("salesCa", ca);
            postDataParamsKM.put("salesChinhanh", chinhanh);
            postDataParamsKM.put("salesTennhanvien", session_username);
            postDataParamsKM.put("salesManhanvien", session_ma);
            postDataParamsKM.put("salesGiamgia", giamgia);
            postDataParamsKM.put("salesMasanpham", a.get(j).getMa());
            postDataParamsKM.put("salesTensanpham", a.get(j).getTen());
            postDataParamsKM.put("salesBaohanhsanpham", a.get(j).getBaohanh());
            postDataParamsKM.put("salesNguonsanpham", a.get(j).getNguon());
            postDataParamsKM.put("salesNgaynhap", a.get(j).getNgaynhap());
            postDataParamsKM.put("salesVonsanpham", a.get(j).getVon());
            postDataParamsKM.put("salesGiasanpham", a.get(j).getGiaban());
            postDataParamsKM.put("salesGhichusanpham", ghichusanpham);
            postDataParamsKM.put("salesTenkhachhang", tenkhachhang);
            postDataParamsKM.put("salesSodienthoaikhachhang", sodienthoaikhachhang);
            postDataParamsKM.put("salesGhichukhachhang", ghichukhachhang);
            j++;

            // Kết nối HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParamsKM));

            writer.flush();
            writer.close();
            os.close();
            int responseCode2 = conn.getResponseCode();
            if (responseCode2 == HttpsURLConnection.HTTP_OK) {
                BufferedReader in2 = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb2 = new StringBuffer("");
                String line2 = "";

                while ((line2 = in2.readLine()) != null) {

                    sb2.append(line2);
                    break;
                }
                in2.close();
                return sb2.toString();
            } else {
                return new String("");
            }
        } catch (Exception e) {
            return new String("");
        }
    }



    public void ResetActivity(){
        Intent intentput = new Intent(Main_Sales.this, Main_Sales.class);
        intentput.putExtra("session_username", session_username1);
        intentput.putExtra("session_ma", session_ma1);
        startActivity(intentput);
        finish();
    }

    public void addOrderWeb(final int j){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Lỗi ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       // new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_SALES_WEB);
                params.put("maDonhang", "SA_"+madonhang);
                params.put("ngay", ngay);
                params.put("calam", ca);
                params.put("gio", arrayList.get(j).getGio());
                params.put("chinhanh", chinhanh);
                params.put("maNhanvien", session_ma);
                params.put("tenNhanvien", session_username);
                params.put("maSanpham", arrayList.get(j).getMa());
                params.put("tenSanpham", arrayList.get(j).getTen());
                params.put("baohanhSanpham", arrayList.get(j).getBaohanh());
                params.put("nguonSanpham", arrayList.get(j).getNguon());
                params.put("ngaynhapSanpham", arrayList.get(j).getNgaynhap());
                params.put("vonSanpham", arrayList.get(j).getVon());
                params.put("giaSanpham", arrayList.get(j).getGiaban());
                params.put("giatriMagiamgia", giatriMagiamgia);
                params.put("ghichuSanpham", ghichusanpham);
                params.put("tenKhachhang", tenkhachhang);
                params.put("sodienthoaiKhachhang", sodienthoaikhachhang);
                params.put("ghichuKhachhang", ghichukhachhang);
                params.put("hinhthuc", hinhthuc);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void addKMWeb(final int j){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Lỗi ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //  new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_KM_WEB);
                params.put("maDonhang", "KM_"+madonhang);
                params.put("ngay", ngay);
                params.put("calam", gio);
                params.put("chinhanh", chinhanh);
                params.put("maNhanvien", session_ma);
                params.put("tenNhanvien", session_username);
                params.put("giamgia", giamgia+"");
                params.put("maSanpham", a.get(j).getMa());
                params.put("tenSanpham", a.get(j).getTen());
                params.put("baohanhSanpham", a.get(j).getBaohanh());
                params.put("nguonSanpham", a.get(j).getNguon());
                params.put("ngaynhapSanpham", a.get(j).getNgaynhap());
                params.put("vonSanpham", a.get(j).getVon());
                params.put("giaSanpham", a.get(j).getGiaban());
                params.put("ghichuSanpham", ghichusanpham);
                params.put("tenKhachhang", tenkhachhang);
                params.put("sodienthoaiKhachhang", sodienthoaikhachhang);
                params.put("ghichuKhachhang", ghichukhachhang);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(Main_Sales.this);
            pDialog.setTitle("Hãy chờ...");
            pDialog.setMessage("Dữ liệu đang được xử lý.");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }

    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    public class DeleteMGG extends AsyncTask<String, Void, String> {
        protected void onPreExecute(){
        }

        protected String doInBackground(String... params) {
            try{
                // Link Script
                URL url = new URL(Keys.SCRIPT_DE_MAGIAMGIA);

                // Load Json object
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("maGiamgia", maGiamgia);

                Log.e("params",postDataParams.toString());

                // Kết nối HTTP
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                // Nếu kết nối được
                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }
                    //
                    in.close();
                    // Trả dữ liệu cho về để ghi lên Excel
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }

    public void deleteMagiamgiaWeb(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Lỗi ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.DELE_MAGIAMGIA_WEB);
                params.put("maGiamgia", maGiamgia);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}




