package com.example.windows10gamer.connsql;

/**
 * Created by EVRESTnhan on 9/30/2017.
 */


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
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
import com.example.windows10gamer.connsql.Adapter.Adapter_Quatang;
import com.example.windows10gamer.connsql.Adapter.Adapter_Sales;
import com.example.windows10gamer.connsql.Object.Quatang;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.NoScanResultException;
import com.example.windows10gamer.connsql.Other.ScanFragment;
import com.example.windows10gamer.connsql.Other.ScanResultReceiver;
import com.example.windows10gamer.connsql.Other.Validation;

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
import java.sql.Time;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import me.sudar.zxingorient.Barcode;
import me.sudar.zxingorient.ZxingOrient;
import me.sudar.zxingorient.ZxingOrientResult;

public class Main_Sales extends ActionBarActivity implements ScanResultReceiver {
    EditText edGiamgia;
    @BindView(R.id.rgKhuyenmai)
    RadioGroup rgKhuyenmai;
    @BindView(R.id.rbGiamgia)
    RadioButton rbGiamgia;
    @BindView(R.id.rbTangqua)
    RadioButton rbTangqua;
    TextView tvManhanvien, tvTongdonhang, tvTennhanvien, tvSalesDate, tvSalesTime;
    EditText edKhachhang, edSodienthoai, edGhichudonhang, edGhichukhachhang;
    ListView listView, listKhuyenmai;
    ArrayList<Sanpham> arrayList, khuyenmaiList;
    ArrayList<Quatang> quatang;
    Adapter_Sales adapter;
    ArrayList<Sanpham> a = new ArrayList<Sanpham>();
    Adapter_Quatang adapter2;
    ArrayAdapter<String> mAdapter;
    int total = 0, giamgia = 0;
    View view;
    boolean[] checkedItem;
    final ArrayList<String> itemKM = new ArrayList<>();
    LinearLayout lnTangqua;
    ProgressDialog dialog;
    Button btnXacnhan, btnCancel;
    String session_username, session_ma;
    String url = Keys.SCRIPT_DANHSACH +"?id="+ Keys.TABLE +"&sheet="+ Keys.DANHSACHQUATANG;
    String ten, ma, nguon, ngaynhap, baohanh, gia, ngay, gio, von, tenkhachhang, sodienthoaikhachhang, ghichukhachhang, ghichusanpham, madonhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sales);
        ButterKnife.bind(this);
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
        listKhuyenmai     = (ListView) findViewById(R.id.lvKhuyenmai);
        lnTangqua         = (LinearLayout) findViewById(R.id.lnTangqua);
        edGiamgia = (EditText) findViewById(R.id.edGiamgia) ;
        edGiamgia.setVisibility(View.GONE);
        lnTangqua.setVisibility(View.GONE);
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
        khuyenmaiList = new ArrayList<>();
        quatang = new ArrayList<>();
        rbTangqua.setEnabled(false);
        rbGiamgia.setEnabled(false);
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
        new GetDataQuaTang().execute();
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
        rgKhuyenmai.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                doOnDifficultyLevelChanged(group, checkedId);
            }
        });
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkValidation()){
                    tenkhachhang = edKhachhang.getText().toString();
                    sodienthoaikhachhang = edSodienthoai.getText().toString();
                    ghichukhachhang = edGhichukhachhang.getText().toString();
                    ghichusanpham = edGhichudonhang.getText().toString();
                    if (rbGiamgia.isChecked() && !edGiamgia.getText().equals(null)){
                        giamgia = Integer.parseInt(edGiamgia.getText().toString());
                    } else {
                        giamgia = 0;
                    }
                    new SendRequest().execute();
                    ResetActivity();

                }
                else Snackbar.make(view, "Phải nhập tất cả các trường.", Snackbar.LENGTH_LONG).show();

            }
        });

        edGiamgia.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                int totalTam = total;
                if (edGiamgia.getText().toString().equals("")){
                    giamgia = 0;
                    tvTongdonhang.setText(getFormatedAmount(total));
                } else {
                    giamgia = Integer.parseInt(edGiamgia.getText().toString());
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
                ResetActivity();
            }
        });
    }

    private void doOnDifficultyLevelChanged(RadioGroup group, int checkedId) {
        int checkedRadioId = group.getCheckedRadioButtonId();
        if(checkedRadioId== R.id.rbGiamgia) {
            edGiamgia.setVisibility(View.VISIBLE);
            lnTangqua.setVisibility(View.GONE);
        } else if(checkedRadioId== R.id.rbTangqua ) {
            edGiamgia.setVisibility(View.GONE);
            lnTangqua.setVisibility(View.VISIBLE);
        }
    }

    public void scanSanphamFM(View view){
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
                von = st.nextToken();
                gia = st.nextToken();
                arrayList.add(new Sanpham(ma, ten, baohanh, nguon, ngaynhap, von, gia));
                total = total + Integer.parseInt(gia);
                tvTongdonhang.setText(getFormatedAmount(total));
            }
        }
        catch (NoSuchElementException nse) {
        }
            adapter.notifyDataSetChanged();
    }

    private String getFormatedAmount(int amount){
        String number = NumberFormat.getNumberInstance(Locale.US).format(amount);
        String formatnumber = number.replace(",",".");
        return formatnumber+"đ";
    }

    @Override
    public void scanResultData(NoScanResultException noScanData) {
        Toast toast = Toast.makeText(this,noScanData.getMessage(), Toast.LENGTH_SHORT);
        toast.show();
    }

    public void scanSanpham(View view){

        ZxingOrient intentIntegrator = new ZxingOrient(Main_Sales.this);
        intentIntegrator.setIcon(R.drawable.icon);
        intentIntegrator.setToolbarColor("#AA3F51B5");
        intentIntegrator.setInfoBoxColor("#AA3F51B5");
        intentIntegrator.setInfo("Scan a QR code Image.");
        intentIntegrator.initiateScan(Barcode.QR_CODE);

        new ZxingOrient(Main_Sales.this)
                .showInfoBox(false)
                .setBeep(true)
                .setVibration(true)
                .initiateScan();
    }

    public void scanKhuyenmai(View view){

        ZxingOrient intentIntegrator = new ZxingOrient(Main_Sales.this);
        intentIntegrator.setIcon(R.drawable.icon);
        intentIntegrator.setToolbarColor("#AA3F51B5");
        intentIntegrator.setInfoBoxColor("#AA3F51B5");
        intentIntegrator.setInfo("Scan a QR code Image.");
        intentIntegrator.initiateScan(Barcode.QR_CODE);

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
                    von = st.nextToken();
                    gia = st.nextToken();
                    arrayList.add(new Sanpham(ma, ten, baohanh, nguon, ngaynhap, von, gia));
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
                }   catch (NoSuchElementException nse) {
                    Toast.makeText(Main_Sales.this, "Lỗi định dạng nhãn", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void DeleteSP(String msp){
        for (int i =  0; i <= arrayList.size(); i++){
            if (arrayList.get(i).getMa() == msp) {
                total = total - Integer.parseInt(arrayList.get(i).getGiaban());
                tvTongdonhang.setText(getFormatedAmount(total));
                arrayList.remove(i);
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
            dialog.setCancelable(false);
            dialog.show();
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
            postDataParams.put("salesVonsanpham", arrayList.get(j).getVon());
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
//***************************************************************************************************************************
    class GetDataQuaTang extends AsyncTask<Void, Void, Void> {
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_Sales.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống!");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(url);
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
            dialog.dismiss();
            if(quatang.size() > 0) {
                setList(quatang);
            } else {
                Toast.makeText(Main_Sales.this, "Không có dữ liệu được tìm thấy", Toast.LENGTH_SHORT).show();
            }
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
                                        quatang.get(i).getMa(),
                                        quatang.get(i).getTen(),
                                        quatang.get(i).getBaohanh(),
                                        quatang.get(i).getNguon(),
                                        quatang.get(i).getNgaynhap(),
                                        quatang.get(i).getVon()+"",
                                        quatang.get(i).getGia()+""
                                ));
                            }
                        }
                        adapter2 = new Adapter_Quatang(Main_Sales.this, R.layout.adapter_quatang, a);
                        listKhuyenmai.setAdapter(adapter2);
                        listKhuyenmai.setOnTouchListener(new ListView.OnTouchListener() {
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

            postDataParamsKM.put("salesMadonhang", madonhang);
            postDataParamsKM.put("salesNgay", ngay);
            postDataParamsKM.put("salesCa", gio);
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
                return new String("false : " + responseCode2);
            }
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
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
        edKhachhang.setText("");
        edSodienthoai.setText("");
        edGhichudonhang.setText("");
        edGhichukhachhang.setText("");
        edGiamgia.setText("");
    }

    public void addOrderWeb(final int j){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toast.makeText(Main_Sales.this, "Lỗi ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Main_Sales.this, "Lỗi "+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_SALES_WEB);
                params.put("maDonhang", madonhang);
                params.put("ngay", ngay);
                params.put("calam", gio);
                params.put("maNhanvien", session_username);
                params.put("tenNhanvien", session_ma);
                params.put("maSanpham", arrayList.get(j).getMa());
                params.put("tenSanpham", arrayList.get(j).getTen());
                params.put("baohanhSanpham", arrayList.get(j).getBaohanh());
                params.put("nguonSanpham", arrayList.get(j).getNguon());
                params.put("ngaynhapSanpham", arrayList.get(j).getNgaynhap());
                params.put("vonSanpham", arrayList.get(j).getVon());
                params.put("giaSanpham", arrayList.get(j).getGiaban());
                params.put("ghichuSanpham", ghichusanpham);
                params.put("tenKhachhang", tenkhachhang);
                params.put("sodienthoaiKhachhang", sodienthoaikhachhang);
                params.put("ghichuKhachhang", ghichukhachhang);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void addKMWeb(final int j){
        RequestQueue requestQueue1 = Volley.newRequestQueue(this);
        StringRequest stringRequest1 = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toast.makeText(Main_Sales.this, "Lỗi ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Main_Sales.this, "Lỗi "+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params1 = new HashMap<>();
                params1.put("tacvu", Keys.ADD_KM_WEB);
                params1.put("maDonhang", madonhang);
                params1.put("ngay", ngay);
                params1.put("calam", gio);
                params1.put("maNhanvien", session_username);
                params1.put("tenNhanvien", session_ma);
                params1.put("giamgia", giamgia+"");
                params1.put("maSanpham", a.get(j).getMa());
                params1.put("tenSanpham", a.get(j).getTen());
                params1.put("baohanhSanpham", a.get(j).getBaohanh());
                params1.put("nguonSanpham", a.get(j).getNguon());
                params1.put("ngaynhapSanpham", a.get(j).getNgaynhap());
                params1.put("vonSanpham", a.get(j).getVon());
                params1.put("giaSanpham", a.get(j).getGiaban());
                params1.put("ghichuSanpham", ghichusanpham);
                params1.put("tenKhachhang", tenkhachhang);
                params1.put("sodienthoaiKhachhang", sodienthoaikhachhang);
                params1.put("ghichuKhachhang", ghichukhachhang);
                return params1;
            }
        };
        requestQueue1.add(stringRequest1);
    }
}

