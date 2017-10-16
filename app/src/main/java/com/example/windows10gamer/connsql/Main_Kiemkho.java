package com.example.windows10gamer.connsql;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windows10gamer.connsql.Adapter.Adapter_kho;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;

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
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;


public class Main_Kiemkho extends AppCompatActivity {
    String scannedData;
    ListView lvScan;
    ArrayList<Sanpham> arrayList;
    Adapter_kho adapter;
    String session_username, session_ma, ngay, gio;
    private String ma, ten, nguon, baohanh, gia, ngaynhap;
    TextView tvScanManhanvien, tvScanTennhanvien;
    String url = Keys.SCRIPT_DANHSACH +"?id="+ Keys.TABLE +"&sheet="+ Keys.DANHSACHKIEMKHO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // khai báo
        setContentView(R.layout.activity_main_scan_qr);
        Button btnScan =(Button) findViewById(R.id.btnScan);
        lvScan = (ListView) findViewById(R.id.lvScan);
        arrayList = new ArrayList<>();
        final Activity activity = this;
        Intent intent = getIntent();
        session_username  = intent.getStringExtra("session_username");
        session_ma        = intent.getStringExtra("session_ma");
        tvScanManhanvien  = (TextView) findViewById(R.id.tvScanManhanvien);
        tvScanTennhanvien = (TextView) findViewById(R.id.tvScanTennhanvien);
        tvScanManhanvien.setText("Mã số: " + session_ma);
        tvScanTennhanvien.setText("Tên nhân viên: " + session_username);
        ngay = getDate();
        gio = getTime();
        new GetDataKho().execute();
        adapter = new Adapter_kho(Main_Kiemkho.this, R.layout.adapter_list_excel, arrayList);
        lvScan.setAdapter(adapter);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // chạy function Scaner
                StartScan(activity);
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabKiemkho);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {

                if (Connect_Internet.checkConnection(getApplicationContext())) {
                    new GetDataKho().execute();
                } else {
                    Snackbar.make(view, "Không có Internet", Snackbar.LENGTH_LONG).show();
                }
            }
        });
    }

    // Function Scanner
    private void StartScan(Activity activity ) {
        ZxingOrient intentIntegrator = new ZxingOrient(Main_Kiemkho.this);
        intentIntegrator.setIcon(R.drawable.ic_launcher)   // Sets the custom icon
                .setToolbarColor("#AA3F51B5")       // Sets Tool bar Color
                .setInfoBoxColor("#AA3F51B5")       // Sets Info box color
                .setInfo("Scan a QR code Image.")   // Sets info message in the info box
                .initiateScan(Barcode.QR_CODE);

        new ZxingOrient(Main_Kiemkho.this)
                .showInfoBox(false) // Doesn't display the info box
                .setBeep(true)  // Doesn't play beep sound
                .setVibration(true)  // Enables the vibration
                .initiateScan();
    }

    // xác nhận dữ liệu trả về
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        ZxingOrientResult result = ZxingOrient.parseActivityResult(requestCode, resultCode, data);
        if(result!=null) {
            scannedData = result.getContents();
            if (scannedData != null) {
                StringTokenizer st = new StringTokenizer(scannedData, ";");
                ma = st.nextToken();
                ten = st.nextToken();
                baohanh = st.nextToken();
                nguon = st.nextToken();
                ngaynhap = st.nextToken();
                gia = st.nextToken();
                arrayList.add(0, new Sanpham(ma, ten, baohanh, nguon, ngaynhap, gia));
                if(arrayList.size() > 0) {
                    adapter.notifyDataSetChanged();
                }
                new SendRequest().execute();
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
                postDataParams.put("SalesTennhanvien", session_username);
                postDataParams.put("salesManhanvien", session_ma);
                postDataParams.put("salesMasanpham", ma);
                postDataParams.put("salesTensanpham", ten);
                postDataParams.put("salesBaohanhsanpham", baohanh);
                postDataParams.put("salesNguonsanpham", nguon);
                postDataParams.put("salesNgaynhap", ngaynhap);
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
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(new Date());
        return date;
    }

    private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(new Date());
        return date;
    }

    class GetDataKho extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
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
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(url);
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
            dialog.dismiss();
            if(arrayList.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(Main_Kiemkho.this, "Không có dữ liệu được tìm thấy", Toast.LENGTH_SHORT).show();
            }
        }
    }
}