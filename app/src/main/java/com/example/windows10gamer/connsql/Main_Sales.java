package com.example.windows10gamer.connsql;

/**
 * Created by EVRESTnhan on 9/30/2017.
 */


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windows10gamer.connsql.Adapter.Adapter_Sales;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.NoScanResultException;
import com.example.windows10gamer.connsql.Other.ScanFragment;
import com.example.windows10gamer.connsql.Other.ScanResultReceiver;
import com.example.windows10gamer.connsql.Other.Validation;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Time;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Locale;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

public class Main_Sales extends ActionBarActivity implements ScanResultReceiver {
    TextView tvManhanvien, tvTongdonhang, tvTennhanvien, tvSalesDate, tvSalesTime;
    EditText edKhachhang, edSodienthoai, edGhichudonhang, edGhichukhachhang;
    ListView listView;
    ArrayList<Sanpham> arrayList;
    Adapter_Sales adapter;
    int total = 0;
    View view;
    ProgressDialog dialog, dialogEnd;
    Button btnXacnhan, btnCancel;
    String session_username, session_ma;
    String ten, ma, nguon, ngaynhap, baohanh, gia, ngay, gio, tenkhachhang, sodienthoaikhachhang, ghichukhachhang, ghichusanpham, madonhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sales);
        tvTongdonhang     = (TextView) findViewById(R.id.tvTongdonhang);
        tvManhanvien      = (TextView) findViewById(R.id.tvManhanvien);
        tvTennhanvien     = (TextView) findViewById(R.id.tvTennhanvien);
        tvSalesDate       = (TextView) findViewById(R.id.tvSalesDate);
        tvSalesTime       = (TextView) findViewById(R.id.tvSalesTime);
        edKhachhang       = (EditText) findViewById(R.id.edKhachhang);
        edSodienthoai     = (EditText) findViewById(R.id.edSodienthoai);
        edGhichudonhang   = (EditText) findViewById(R.id.edGhichudonhang);
        edGhichukhachhang = (EditText) findViewById(R.id.edGhichukhachhang);
        btnXacnhan        = (Button)   findViewById(R.id.submitSanpham) ;
        btnCancel         = (Button)   findViewById(R.id.cancelSanpham) ;
        listView          = (ListView) findViewById(R.id.lvSanpham);
        ngay = getDate();
        gio = getTime();
        madonhang = MaDonhang();
        tvSalesDate.setText(ngay);
        tvSalesTime.setText(gio);
        Intent intentput  = getIntent();
        session_username  = intentput.getStringExtra("session_username");
        session_ma        = intentput.getStringExtra("session_ma");
        tvManhanvien.setText("Mã số: " + session_ma);
        tvTennhanvien.setText("Tên nhân viên: " + session_username);
        edGhichudonhang.setSelected(false);
        arrayList     = new ArrayList<>();
        adapter       = new Adapter_Sales(Main_Sales.this, R.layout.adapter_sales, arrayList);
        listView.setAdapter(adapter);
        listView.setOnTouchListener(new ListView.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        v.getParent().requestDisallowInterceptTouchEvent(true);
                        break;

                    case MotionEvent.ACTION_UP:
                        v.getParent().requestDisallowInterceptTouchEvent(false);
                        break;
                }
                v.onTouchEvent(event);
                return true;
            }
        });
        edKhachhang.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(edKhachhang);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });
        edSodienthoai.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                Validation.hasText(edSodienthoai);
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}
            public void onTextChanged(CharSequence s, int start, int before, int count){}
        });

        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()){
                    tenkhachhang = edKhachhang.getText().toString();
                    sodienthoaikhachhang = edSodienthoai.getText().toString();
                    ghichukhachhang = edGhichukhachhang.getText().toString();
                    ghichusanpham = edGhichudonhang.getText().toString();
                    new SendRequest().execute();
                    ResetActivity();
                }
                else Snackbar.make(view, "Phải nhập tất cả các trường.", Snackbar.LENGTH_LONG).show();

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ResetActivity();
                finish();
            }
        });
    }

    public void scanSanpham(View view){
        ten = null; ma = null; nguon = null; ngaynhap = null; baohanh = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        ScanFragment scanFragment = new ScanFragment();
        fragmentTransaction.add(R.id.scan_fragment, scanFragment);
        fragmentTransaction.commit();
    }

    private boolean checkValidation() {
        boolean ret = true;
        if (!Validation.hasText(edKhachhang)) ret = false;
        if (!Validation.hasText(edSodienthoai)) ret = false;
        return ret;
    }

    @Override
    public void scanResultData(String codeFormat, String codeContent){

        try {
            if (codeContent != null && codeFormat != null){
                StringTokenizer st = new StringTokenizer(codeContent, ";");
                ma = st.nextToken();
                ten = st.nextToken();
                baohanh = st.nextToken();
                nguon = st.nextToken();
                ngaynhap = st.nextToken();
                gia = st.nextToken();
                arrayList.add(new Sanpham(ma, ten, baohanh, nguon, ngaynhap, gia));
                total = total + Integer.parseInt(gia);
                tvTongdonhang.setText(getFormatedAmount(total) + " đ");
            }
        }   catch (NoSuchElementException nse) {
        }
            adapter.notifyDataSetChanged();
    }

    private String getFormatedAmount(int amount){
        String number = NumberFormat.getNumberInstance(Locale.US).format(amount);
        String formatnumber = number.replace(",",".");
        return formatnumber;
    }

    @Override
    public void scanResultData(NoScanResultException noScanData) {
        Toast toast = Toast.makeText(this,noScanData.getMessage(), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void scanNhanvien(View view){

        ZxingOrient intentIntegrator = new ZxingOrient(Main_Sales.this);
        intentIntegrator.setIcon(R.drawable.icon)
                .setToolbarColor("#AA3F51B5")
                .setInfoBoxColor("#AA3F51B5")
                .setInfo("Scan a QR code Image.")
                .initiateScan(Barcode.QR_CODE);

        new ZxingOrient(Main_Sales.this)
                .showInfoBox(false)
                .setBeep(true)
                .setVibration(true)
                .initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ZxingOrientResult result = ZxingOrient.parseActivityResult(requestCode, resultCode, data);
        if(result!=null) {
            String scannedData = result.getContents();
            if (scannedData != null) {
                try{
                    StringTokenizer st = new StringTokenizer(scannedData, ";");
                    ma = st.nextToken();
                    ten = st.nextToken();
                    baohanh = st.nextToken();
                    nguon = st.nextToken();
                    ngaynhap = st.nextToken();
                    gia = st.nextToken();
                    arrayList.add(new Sanpham(ma, ten, baohanh, nguon, ngaynhap, gia));
                    total = total + Integer.parseInt(gia);
                    tvTongdonhang.setText(getFormatedAmount(total) + " đ");
                }   catch (NoSuchElementException nse) {
                    Toast.makeText(Main_Sales.this, "Lỗi định dạng nhãn", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
        adapter.notifyDataSetChanged();
    }

    public void DeleteSP(String msp){
        for (int i =  0; i <= arrayList.size(); i++){
            if (arrayList.get(i).getMa() == msp) {
                total = total - Integer.parseInt(arrayList.get(i).getGiaban());
                tvTongdonhang.setText(getFormatedAmount(total)+" đ");
                arrayList.remove(i);
                break;
            }
        }
        adapter.notifyDataSetChanged();
    }

    private String MaDonhang() {
        DateFormat dateFormat = new SimpleDateFormat("HHmmssddMMyyyy");
        String date = dateFormat.format(new Date());
        return date;
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(new Date());
        return date;
    }

    private String getTime() {
        String ca;
        int hours = new Time(System.currentTimeMillis()).getHours();
        if (hours < 15 ){
            ca = "Ca sáng";
        } else {
            ca = "Ca chiều";
        }
        return ca;
    }

    public class SendRequest extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){
            dialog = new ProgressDialog(Main_Sales.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Đơn hàng đang được xử lý");
            dialog.show();
        }

        protected String doInBackground(String... arg0) {
            int j = 0;
            while (j < arrayList.size()){
                putData(j);
                j++;
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
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

            postDataParams.put("salesMadonhang", madonhang);
            postDataParams.put("salesNgay", ngay);
            postDataParams.put("salesCa", gio);
            postDataParams.put("SalesTennhanvien", session_username);
            postDataParams.put("salesManhanvien", session_ma);
            postDataParams.put("salesMasanpham", arrayList.get(j).getMa());
            postDataParams.put("salesTensanpham", arrayList.get(j).getTen());
            postDataParams.put("salesBaohanhsanpham", arrayList.get(j).getBaohanh());
            postDataParams.put("salesNguonsanpham", arrayList.get(j).getNguon());
            postDataParams.put("salesNgaynhap", arrayList.get(j).getNgaynhap());
            postDataParams.put("salesGiasanpham", arrayList.get(j).getGiaban());
            postDataParams.put("salesGhichusanpham", ghichusanpham);
            postDataParams.put("salesTenkhachhang", tenkhachhang);
            postDataParams.put("salesSodienthoaikhachhang", sodienthoaikhachhang);
            postDataParams.put("salesGhichukhachhang", ghichukhachhang);
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
                return new String("false : " + responseCode);
            }
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    public void ResetActivity(){
        Intent intentget = getIntent();
        session_username = intentget.getStringExtra("session_username");
        session_ma = intentget.getStringExtra("session_ma");
        Intent intentput = new Intent(this, this.getClass());
        intentput.putExtra("session_username", session_username);
        intentput.putExtra("session_ma", session_ma);
        startActivity(intentput);
    }
}

