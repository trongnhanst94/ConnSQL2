package com.example.windows10gamer.connsql;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Adapter.Adapter_kho;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;


public class Main_Kiemkho extends AppCompatActivity {
    String scannedData;
    ListView lvScan;
    ArrayList<Sanpham> arrayList;
    ArrayList<String> position = new ArrayList<>();
    ArrayAdapter<String> mAdapter;
    Adapter_kho adapter;
    String session_username, session_ma, ngay, gio;
    private String ma, ten, nguon, baohanh, gia, ngaynhap, von, vitri;
    TextView tvScanManhanvien, tvScanTennhanvien;
    RadioGroup rgkho;
    RadioButton rbkhomoi;
    RadioButton rbkholoi;
    String kho = "Kho mới";
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // khai báo
        setContentView(R.layout.activity_main_kiem_kho);
        Button btnScan =(Button) findViewById(R.id.btnScan);
        lvScan = (ListView) findViewById(R.id.lvScan);
        arrayList = new ArrayList<>();
        final Activity activity = this;
        Intent intent = getIntent();
        session_username  = intent.getStringExtra("session_username");
        session_ma        = intent.getStringExtra("session_ma");
        tvScanManhanvien  = (TextView) findViewById(R.id.tvScanManhanvien);
        tvScanTennhanvien = (TextView) findViewById(R.id.tvScanTennhanvien);
        rgkho             = (RadioGroup) findViewById(R.id.rgkho);
        rbkhomoi          = (RadioButton) findViewById(R.id.rbkhomoi);
        rbkholoi          = (RadioButton) findViewById(R.id.rbkholoi);
        rbkhomoi.setChecked(true);
        tvScanManhanvien.setText("Mã số: " + session_ma);
        tvScanTennhanvien.setText("Tên nhân viên: " + session_username);
        ngay = getDate();
        gio = getTime();
        new GetDataKho().execute();
        new Getvitri().execute();
        adapter = new Adapter_kho(Main_Kiemkho.this, R.layout.adapter_list_excel, arrayList);
        lvScan.setAdapter(adapter);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StartScan();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabKiemkho);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {

                if (Connect_Internet.checkConnection(getApplicationContext())) {
                    ResetActivity();
                } else {
                    Snackbar.make(view, "Không có Internet", Snackbar.LENGTH_LONG).show();
                }
            }
        });
        rbkholoi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!rbkhomoi.isChecked() && isChecked == false){
                    rbkhomoi.setChecked(true);
                }
            }
        });
        rbkhomoi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!rbkholoi.isChecked() && isChecked == false){
                    rbkholoi.setChecked(true);
                }
            }
        });
        rgkho.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                doOnDifficultyLevelChanged(group, checkedId);
            }
        });
    }

    private void doOnDifficultyLevelChanged(RadioGroup group, int checkedId) {
        int checkedRadioId = group.getCheckedRadioButtonId();
        if(checkedRadioId== R.id.rbkhomoi) {
            kho = "Kho mới";
        } else if(checkedRadioId== R.id.rbkholoi ) {
            kho = "Kho lỗi";
        }
    }

    private void StartScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);        integrator.setPrompt("Quét mã code");        integrator.setOrientationLocked(false);        integrator.setBeepEnabled(false);        integrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null) {
            scannedData = result.getContents();
            if (scannedData != null) {
                try {
                StringTokenizer st = new StringTokenizer(scannedData, ";");
                ma = st.nextToken();
                ten = st.nextToken();
                baohanh = st.nextToken();
                nguon = st.nextToken();
                ngaynhap = st.nextToken();
                von = st.nextToken();
                gia = st.nextToken();
                arrayList.add(0, new Sanpham(ma, ten, baohanh, nguon, ngaynhap, von, gia));
                if(arrayList.size() > 0) {
                    adapter.notifyDataSetChanged();
                }
                new SendRequest().execute();
                addKiemkhoWeb();
                StartScan();
                }   catch (NoSuchElementException nse) {
                    Toast.makeText(Main_Kiemkho.this, "Lỗi định dạng nhãn", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    // Lấy dữ liệu từ internet
    public class SendRequest extends AsyncTask<String, Void, String> {
        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try{

                // Link Script
                URL url = new URL(Keys.SCRIPT_KIEMKHO);

                // Load Json object
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("salesNgay", ngay);
                postDataParams.put("salesGio", gio);
                postDataParams.put("salesVitri", vitri);
                postDataParams.put("salesKho", kho);
                postDataParams.put("SalesTennhanvien", session_username);
                postDataParams.put("salesManhanvien", session_ma);
                postDataParams.put("salesMasanpham", ma);
                postDataParams.put("salesTensanpham", ten);
                postDataParams.put("salesBaohanhsanpham", baohanh);
                postDataParams.put("salesNguonsanpham", nguon);
                postDataParams.put("salesNgaynhap", ngaynhap);
                postDataParams.put("salesVonsanpham", von);
                postDataParams.put("salesGiasanpham", gia);


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
            Toast.makeText(getApplicationContext(), result,Toast.LENGTH_SHORT).show();
        }
    }

    // Lấy dữ liệu từ internet
    class Getvitri extends AsyncTask<Void, Void, Void> {
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            x = arrayList.size();

            if(x == 0)
                jIndex = 0;
            else
                jIndex = x;
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_KIEMKHO_URL2);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DANHSACHCUAHANG);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                        position.add(
                                                object.getString("cuahang")
                                        );
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            setLisst(position);
        }
    }

    private void setLisst(ArrayList<String> position) {
        this.position = position;
        final Spinner spinnerKiemkho = (Spinner) findViewById(R.id.spinnerKiemkho);
        mAdapter = new ArrayAdapter<>(Main_Kiemkho.this, android.R.layout.simple_spinner_item, position);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKiemkho.setAdapter(mAdapter);
        spinnerKiemkho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = spinnerKiemkho.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        dialog.dismiss();
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

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(new Date());
        return date;
    }

    private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(new Date());
        return date;
    }

    class GetDataKho extends AsyncTask<Void, Void, Void> {
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */

            x = arrayList.size();

            if(x == 0)
                jIndex = 0;
            else
                jIndex = x;

            dialog = new ProgressDialog(Main_Kiemkho.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_KIEMKHO_URL);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DANHSACHKIEMKHO);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    if (object.getString("manhanvien").equals(session_ma)){
                                        arrayList.add(0, new Sanpham(
                                                object.getString("ma"),
                                                object.getString("ten"),
                                                object.getString("baohanh"),
                                                object.getString("nguon"),
                                                object.getString("ngaynhap"),
                                                object.getString("von"),
                                                object.getString("gia")
                                        ));
                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                } else {

                }
            } catch (JSONException je) {
                Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            if(arrayList.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(Main_Kiemkho.this, "Không có dữ liệu được tìm thấy", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void ResetActivity(){
        if (Build.VERSION.SDK_INT >= 11) {
            recreate();
        } else {
            Intent intent = getIntent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
            finish();
            overridePendingTransition(0, 0);
            startActivity(intent);
            overridePendingTransition(0, 0);
        }
    }

    public void addKiemkhoWeb(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toast.makeText(Main_Kiemkho.this, "Lỗi ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Main_Kiemkho.this, "Lỗi "+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_KIEMKHO_WEB);
                params.put("ngay", ngay);
                params.put("calam", gio);
                params.put("maNhanvien", session_username);
                params.put("tenNhanvien", session_ma);
                params.put("maSanpham", ma);
                params.put("tenSanpham", ten);
                params.put("baohanhSanpham", baohanh);
                params.put("nguonSanpham", nguon);
                params.put("ngaynhapSanpham", ngaynhap);
                params.put("vonSanpham", von);
                params.put("giaSanpham", gia);
                params.put("vitri", vitri);
                params.put("kho", kho);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}