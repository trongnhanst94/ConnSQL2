package com.example.windows10gamer.connsql.Kiem_Kho;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.windows10gamer.connsql.Adapter.Adapter_KiemKho;
import com.example.windows10gamer.connsql.Object.SanphamAmount;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
    Button btndanhsach;
    ListView lvScan;
    ArrayList<SanphamAmount> totalList = new ArrayList<>();
    ArrayList<SanphamAmount> arrayList;
    ArrayList<String> position = new ArrayList<>();
    ArrayAdapter<String> mAdapter;
    Adapter_KiemKho adapter;
    String session_username, session_ma, ngay, gio;
    private String ma, ten, nguon, baohanh, gia, ngaynhap, von, vitri;
    TextView tvScanManhanvien, tvScanTennhanvien, tvchinhanhkiemkho;
    RadioGroup rgkho;
    RadioButton rbkhomoi;
    RadioButton rbkholoi;
    String kho = "Kho mới";
    ProgressDialog dialog;
    private SharedPreferences shared, sp;
    private String chinhanh;
    private ProgressDialog slPro;
    private ArrayList<String> listsoluong =  new ArrayList<>();
    private int soluong;
    private ProgressDialog nPro;
    ImageView ivAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kiem_kho);
        Button btnScan = findViewById(R.id.btnScan);
        Button btndanhsach = findViewById(R.id.btndanhsach);
        lvScan = findViewById(R.id.lvScan);
        arrayList = new ArrayList<>();
        final Activity activity = this;
        Intent intent = getIntent();
        ivAvatar = findViewById(R.id.ivAvatar);
        tvchinhanhkiemkho = findViewById(R.id.tvchinhanhkiemkho);
        session_username  = intent.getStringExtra("session_username");
        session_ma        = intent.getStringExtra("session_ma");
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        sp = getSharedPreferences("login", MODE_PRIVATE);
        Glide.with(Main_Kiemkho.this).load(sp.getString("img", "")).override(300,300).fitCenter().into(ivAvatar);
        tvchinhanhkiemkho.setText(chinhanh);
        tvScanManhanvien  = findViewById(R.id.tvScanManhanvien);
        tvScanTennhanvien = findViewById(R.id.tvScanTennhanvien);
        rgkho             = findViewById(R.id.rgkho);
        rbkhomoi          = findViewById(R.id.rbkhomoi);
        rbkholoi          = findViewById(R.id.rbkholoi);
        rbkhomoi.setChecked(true);
        tvScanManhanvien.setText("Mã số: " + session_ma);
        tvScanTennhanvien.setText("Tên nhân viên: " + session_username);
        ngay = getDate();
        new GetListSoluong().execute();
        new Getvitri().execute();
        adapter = new Adapter_KiemKho(Main_Kiemkho.this, R.layout.adapter_kiemkho_amount, totalList, listsoluong);
        lvScan.setAdapter(adapter);
        new GetDataKho().execute();
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Kiemkho.this).show();
                else {
                    StartScan();
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
        btndanhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Kiemkho.this).show();
                else {
                    Intent intent = new Intent(Main_Kiemkho.this, Main_Kiemkho_XemA.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("nameUserA", session_username);
                    bundle.putString("snUserA", session_ma);
                    bundle.putString("chinhanh", chinhanh);
                    bundle.putString("kho", kho);
                    intent.putExtra("BundlexemA", bundle);
                    startActivity(intent);
                }
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
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Quét mã code");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null) {
            scannedData = result.getContents();
            if (scannedData != null) {
                try {
                    arrayList.clear();
                    StringTokenizer st = new StringTokenizer(scannedData, ";");
                    ma = st.nextToken();
                    ten = st.nextToken();
                    baohanh = st.nextToken();
                    nguon = st.nextToken();
                    gio = getTime();
                    ngaynhap = st.nextToken();
                    von = st.nextToken();
                    gia = st.nextToken();
                    soluong = 1;
                    int checksoluong = check(ma, listsoluong);
                    if (checksoluong != -1){
                        android.app.AlertDialog.Builder dialog = null;
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                            dialog = new android.app.AlertDialog.Builder(Main_Kiemkho.this);
                        } else {
                            dialog = new android.app.AlertDialog.Builder(Main_Kiemkho.this);
                        }
                        dialog.setIcon(R.drawable.ic_settings).setTitle("Nhập số lượng");
                        View mView = Main_Kiemkho.this.getLayoutInflater().inflate(R.layout.dialog_soluongsales, null);
                        final EditText edsoluong = mView.findViewById(R.id.edsoluong);
                        final ImageView ivtru = mView.findViewById(R.id.ivtru);
                        final ImageView ivcong = mView.findViewById(R.id.ivcong);
                        edsoluong.setText(soluong+"");
                        ivtru.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (Integer.valueOf(edsoluong.getText().toString()) > 1)
                                    soluong = Integer.valueOf(edsoluong.getText().toString()) - 1;
                                edsoluong.setText(soluong+"");
                            }
                        });
                        ivcong.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                soluong = Integer.valueOf(edsoluong.getText().toString()) + 1;
                                edsoluong.setText(soluong+"");
                            }
                        });
                        dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                soluong = Integer.parseInt(edsoluong.getText().toString().trim());
                                totalList.add(0, new SanphamAmount(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia, soluong+""));
                                arrayList.add(0, new SanphamAmount(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia, soluong+""));
                                adapter.notifyDataSetChanged();
                                if (!Connect_Internet.checkConnection(getApplicationContext()))
                                    Connect_Internet.buildDialog(Main_Kiemkho.this).show();
                                else {
                                    new SendRequest().execute();
                                }
                            }
                        });
                        dialog.setView(mView);
                        android.app.AlertDialog al = dialog.create();
                        al.show();
                    } else {
                        totalList.add(0, new SanphamAmount(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia, 1 + ""));
                        arrayList.add(0, new SanphamAmount(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia, 1 + ""));
                        appendLog(Keys.getTimeNow()+": "+gio+" - "+ ma+" - "+ ten+" - "+ baohanh+" - "+ nguon+" - "+ ngaynhap+" - "+ von+" - "+ gia);
                        if (!Connect_Internet.checkConnection(getApplicationContext()))
                            Connect_Internet.buildDialog(Main_Kiemkho.this).show();
                        else {
                            new SendRequest().execute();
                        }
                        if (arrayList.size() > 0) {
                            adapter.notifyDataSetChanged();
                        }
                    }
                }   catch (NoSuchElementException nse) {
                    new CustomToast().Show_Toast(Main_Kiemkho.this, findViewById(android.R.id.content), "Lỗi định dạng nhãn");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void appendLog(String text)
    {
        File logFile = new File("sdcard/log_iDeal.txt");
        if (!logFile.exists())
        {
            try
            {
                logFile.createNewFile();
            }
            catch (IOException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        try
        {
            //BufferedWriter for performance, true to set append to file flag
            BufferedWriter buf = new BufferedWriter(new FileWriter(logFile, true));
            buf.append(text);
            buf.newLine();
            buf.close();
        }
        catch (IOException e)
        {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private int check(String ma, ArrayList<String> listsoluong) {
        int dem = -1;
        for (int i = 0; i < listsoluong.size(); i++) {
            if (ma.equals(listsoluong.get(i))){
                dem = i;
            }
        }
        return dem;
    }

    public class SendRequest extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            if (Integer.valueOf(arrayList.get(0).getSoluong()) > 1){
                nPro = new ProgressDialog(Main_Kiemkho.this);
                nPro.setTitle("Đang tải lên!");
                nPro.setMax(arrayList.size());
                nPro.setCancelable(false);
                nPro.setProgress(0);
                nPro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                nPro.show();
            }
        }

        @Override
        protected String doInBackground(Void... params) {
            int progress = 1;
            if (Integer.valueOf(arrayList.get(0).getSoluong()) == 1) {
                addKiemkhoWeb(gio);
            } else {
                while (progress <= Integer.valueOf(arrayList.get(0).getSoluong())){
                    addKiemkhoWeb(gio+progress);
                    publishProgress(progress);
                    progress++;
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int prog = values[0];
            if (Integer.valueOf(arrayList.get(0).getSoluong()) > 1) {
                nPro.setProgress(prog);
                nPro.setMessage("Đã tải lên " + prog + " trên " + arrayList.get(0).getSoluong() + " sản phẩm...");
            }
        }

        @Override
        protected void onPostExecute(String s) {
            if (Integer.valueOf(arrayList.get(0).getSoluong()) > 1){
                nPro.hide();
            } else {
                StartScan();
            }

        }
    }

    public String putData(){
        try {
            URL url = new URL(Keys.SCRIPT_KIEMKHO);
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("key", session_username+gio+ngay);
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

    public void addKiemkhoWeb(final String gios){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_KIEMKHO_WEB);
                params.put("ngay", ngay);
                params.put("id", session_username+gios+ngay);
                params.put("gio", gios);
                params.put("maNhanvien", session_ma );
                params.put("tenNhanvien", session_username);
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

    class GetListSoluong extends AsyncTask<Void, Integer, String> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            slPro = new ProgressDialog(Main_Kiemkho.this);
            slPro.setTitle("Hãy chờ!");
            slPro.setCancelable(false);
            slPro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            slPro.show();

        }

        @Nullable
        @Override
        protected String doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_LISTSOLUONG);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.LISTSOLUONG);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    listsoluong.add(object.getString("masanpham"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                publishProgress(jIndex);
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
        protected void onProgressUpdate(Integer... values) {
            int prog = values[0];
            slPro.setMessage("Đang tải xuống "+prog+" sản phẩm...");
        }

        @Override
        protected void onPostExecute(String s) {
            adapter.notifyDataSetChanged();
            slPro.hide();
        }
    }

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
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_MENU_DSCH);
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
        final Spinner spinnerKiemkho = findViewById(R.id.spinnerKiemkho);
        mAdapter = new ArrayAdapter<>(Main_Kiemkho.this, android.R.layout.simple_spinner_item, position);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKiemkho.setAdapter(mAdapter);
        selectValue(spinnerKiemkho, chinhanh);
        spinnerKiemkho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                vitri = spinnerKiemkho.getSelectedItem().toString();
                tvchinhanhkiemkho.setText(vitri);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void selectValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
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
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(new Date());
        return date;
    }

    private String getTime() {
        DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        String date = dateFormat.format(new Date());
        return date;
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

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    class GetDataKho extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            x = totalList.size();
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
        protected Void doInBackground(Void... strings) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_KIEMKHO_URL);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DANHSACHKIEMKHO2);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    if (chinhanh.equals(object.getString("vitri")) && (object.getString("kho").equals(kho)) && (object.getString("maNhanvien").equals(session_ma))){
                                        totalList.add(new SanphamAmount(
                                                        object.getString("gio"),
                                                        object.getString("maSanpham"),
                                                        object.getString("tenSanpham"),
                                                        object.getString("baohanhSanpham"),
                                                        object.getString("nguonSanpham"),
                                                        object.getString("ngaynhapSanpham"),
                                                        object.getString("vonSanpham"),
                                                        object.getString("giaSanpham"),
                                                        "1"
                                                )
                                        );
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
        protected void onPostExecute(Void string) {
            super.onPostExecute(string);
            if(totalList.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                new CustomToast().Show_Toast(Main_Kiemkho.this, findViewById(android.R.id.content), "Không có dữ liệu!!");
            }
            dialog.dismiss();
        }
    }
}