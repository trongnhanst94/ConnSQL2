package com.example.windows10gamer.connsql.Kho;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.bumptech.glide.Glide;
import com.example.windows10gamer.connsql.Adapter.Adapter_AddKho;
import com.example.windows10gamer.connsql.Object.SanphamAmount;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import es.dmoral.toasty.Toasty;

public class Main_AddKho extends AppCompatActivity {
    String scannedData;
    Button btndanhsach;
    ListView lvScan;
    ArrayList<SanphamAmount> totalList = new ArrayList<>();
    ArrayList<SanphamAmount> arrayList;
    ArrayList<String> position = new ArrayList<>();
    ArrayAdapter<String> mAdapter, mAdapterKho;
    Adapter_AddKho adapter;
    String session_username, session_ma, ngay, gio;
    private String ma, ten, nguon, baohanh, gia, ngaynhap, von, vitri;
    TextView tvScanManhanvien, tvScanTennhanvien, tvchinhanhkiemkho;
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
        setContentView(R.layout.activity_main_addkho);
        Button btnScan = findViewById(R.id.btnScan);
        Button btndanhsach = findViewById(R.id.btndanhsach);
        lvScan = findViewById(R.id.lvScan);
        arrayList = new ArrayList<>();
        final Activity activity = this;
        Intent intent = getIntent();
        ivAvatar = findViewById(R.id.ivAvatar);
        tvchinhanhkiemkho = findViewById(R.id.tvchinhanhkiemkho);
        shared = getSharedPreferences("login", MODE_PRIVATE);
        session_username = shared.getString("shortName", "");
        session_ma = shared.getString("ma", "");
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        sp = getSharedPreferences("login", MODE_PRIVATE);
        Glide.with(Main_AddKho.this).load(sp.getString("img", "")).override(300,300).fitCenter().into(ivAvatar);
        tvchinhanhkiemkho.setText(chinhanh);
        tvScanManhanvien  = findViewById(R.id.tvScanManhanvien);
        tvScanTennhanvien = findViewById(R.id.tvScanTennhanvien);
        tvScanManhanvien.setText("Mã số: " + session_ma);
        tvScanTennhanvien.setText("Tên nhân viên: " + session_username);
        ngay = getDate();
        new GetListSoluong().execute();
        new Getvitri().execute();
        adapter = new Adapter_AddKho(Main_AddKho.this, R.layout.adapter_kiemkho_amount, totalList, listsoluong);
        lvScan.setAdapter(adapter);
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_AddKho.this).show();
                else {
                    if (!kho.equals("- Chọn loại nhập -")){
                        StartScan();
                    } else {
                        Toasty.warning(Main_AddKho.this, "Chưa chọn loại nhập", Toast.LENGTH_LONG, true).show();
                    }
                }
            }
        });
    }

    class GetListSoluong extends AsyncTask<Void, Integer, String> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            slPro = new ProgressDialog(Main_AddKho.this);
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
                    android.app.AlertDialog.Builder dialog = null;
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        dialog = new android.app.AlertDialog.Builder(Main_AddKho.this);
                    } else {
                        dialog = new android.app.AlertDialog.Builder(Main_AddKho.this);
                    }
                    dialog.setIcon(R.drawable.ic_settings).setTitle("Nhập số lượng");
                    View mView = Main_AddKho.this.getLayoutInflater().inflate(R.layout.dialog_soluongsales, null);
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
                                Connect_Internet.buildDialog(Main_AddKho.this).show();
                            else {
                                new SendRequest().execute();
                            }
                        }
                    });
                    dialog.setView(mView);
                    android.app.AlertDialog al = dialog.create();
                    al.show();
                }   catch (NoSuchElementException nse) {
                    Toasty.warning(Main_AddKho.this, "Lỗi định dạng mã vạch", Toast.LENGTH_LONG, true).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public class SendRequest extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            if (Integer.valueOf(arrayList.get(0).getSoluong()) > 1){
                nPro = new ProgressDialog(Main_AddKho.this);
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
            if (Integer.valueOf(arrayList.get(0).getSoluong()) == 1) {
                addKiemkhoWeb(gio);
            } else {
                addKiemkhoWebPlus(gio, Integer.valueOf(arrayList.get(0).getSoluong()), 1);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            if (Integer.valueOf(arrayList.get(0).getSoluong()) > 1){

            } else {
                StartScan();
            }

        }
    }

    public void addKiemkhoWeb(final String gios){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
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
                params.put("tacvu", Keys.ADD_KHOONLINE_WEB);
                params.put("id", session_username+gios+ngay);
                params.put("chinhanh", vitri);
                params.put("kho", kho);
                params.put("maNV", session_ma );
                params.put("tenNV", session_username);
                params.put("ma", ma);
                params.put("ten", ten);
                params.put("baohanh", baohanh);
                params.put("nguon", nguon);
                params.put("ngaynhap", ngaynhap);
                params.put("von", von);
                params.put("gia", gia);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void addKiemkhoWebPlus(final String gios, final int soluong, final int stt){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (stt != soluong){
                            addKiemkhoWebPlus(gios, soluong, stt+1);
                            nPro.setMessage("Đã tải lên " + stt + " trên " + arrayList.get(0).getSoluong() + " sản phẩm...");
                        } else {
                            nPro.hide();
                        }
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
                params.put("tacvu", Keys.ADD_KHOONLINE_WEB);
                params.put("id", session_username+gios+stt+ngay);
                params.put("chinhanh", vitri);
                params.put("kho", kho);
                params.put("maNV", session_ma );
                params.put("tenNV", session_username);
                params.put("ma", ma);
                params.put("ten", ten);
                params.put("baohanh", baohanh);
                params.put("nguon", nguon);
                params.put("ngaynhap", ngaynhap);
                params.put("von", von);
                params.put("gia", gia);
                return params;
            }
        };
        requestQueue.add(stringRequest);
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
        mAdapter = new ArrayAdapter<>(Main_AddKho.this, android.R.layout.simple_spinner_item, position);
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

        final Spinner spinnerkho = findViewById(R.id.spinnerkho);
        mAdapterKho = new ArrayAdapter<>(Main_AddKho.this, android.R.layout.simple_spinner_item, Keys.KHOLIST);
        mAdapterKho.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerkho.setAdapter(mAdapterKho);
        selectValue(spinnerkho, kho);
        spinnerkho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kho = spinnerkho.getSelectedItem().toString();
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

}