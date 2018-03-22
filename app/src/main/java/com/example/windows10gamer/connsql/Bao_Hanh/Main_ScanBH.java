package com.example.windows10gamer.connsql.Bao_Hanh;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order;
import com.example.windows10gamer.connsql.Adapter.Adapter_Report_BH1D1;
import com.example.windows10gamer.connsql.Adapter.Adapter_Report_BHCXL;
import com.example.windows10gamer.connsql.Adapter.Adapter_Report_BHDDT;
import com.example.windows10gamer.connsql.Adapter.Adapter_Report_BHDLK;
import com.example.windows10gamer.connsql.Adapter.Adapter_Report_BHHT;
import com.example.windows10gamer.connsql.Object.BH1D1;
import com.example.windows10gamer.connsql.Object.BHCXL;
import com.example.windows10gamer.connsql.Object.BHDDT;
import com.example.windows10gamer.connsql.Object.BHDLK;
import com.example.windows10gamer.connsql.Object.BHHT;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.Mylistview;
import com.example.windows10gamer.connsql.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import es.dmoral.toasty.Toasty;

public class Main_ScanBH extends AppCompatActivity {

    Activity activity;
    Mylistview lvReportBHHT, lvReportBH1D1, lvReportBHDLK, lvReportBHCXL, lvReportBHDDT;
    ArrayList<BHHT> listHT = new ArrayList<>();
    ArrayList<BH1D1> list1D1 = new ArrayList<>();
    ArrayList<BHDLK> listDLK = new ArrayList<>();
    ArrayList<BHDLK> listDLKLoc = new ArrayList<>();
    ArrayList<BHDDT> listDDT = new ArrayList<>();
    ArrayList<BHCXL> listCXL = new ArrayList<>();
    Adapter_Report_BHHT adapterHT;
    Adapter_Report_BH1D1 adapter1D1;
    Adapter_Report_BHDLK adapterDLK;
    Adapter_Report_BHDDT adapterDDT;
    Adapter_Report_BHCXL adapterCXL;
    String scannedData;
    LinearLayout llHT, llDLK, ll1D1, llDDT, llCXL;
    ArrayList<BH1D1> BH1D1 = new ArrayList<>();
    ArrayList<BHHT> BHHT = new ArrayList<>();
    ArrayList<BHDLK> BHDLK = new ArrayList<>();
    ArrayList<BHDDT> BHDDT = new ArrayList<>();
    ArrayList<BHCXL> BHCXL = new ArrayList<>();
    ArrayList<Sanpham_gio> arrayList = new ArrayList();
    String ma, ten, baohanh, nguon, ngaynhap, von, gia;
    int dem = 0;
    TextView tvScanBhNoti;
    Mylistview lvInfoScan;
    Adapter_Info_Order adapterInfoOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_scan_bh);
        lvReportBHHT = findViewById(R.id.lvReportBHHT);
        lvInfoScan = findViewById(R.id.lvInfoScan);
        ll1D1 = findViewById(R.id.ll1D1);
        llDLK = findViewById(R.id.llDLK);
        llHT = findViewById(R.id.llHT);
        llDDT = findViewById(R.id.llDDT);
        llCXL = findViewById(R.id.llCXL);
        tvScanBhNoti = findViewById(R.id.tvScanBhNoti);
        adapterInfoOrder = new Adapter_Info_Order(Main_ScanBH.this, arrayList);
        lvInfoScan.setAdapter(adapterInfoOrder);
        adapterHT = new Adapter_Report_BHHT(Main_ScanBH.this, R.layout.adapter_report_bh, listHT);
        lvReportBHHT.setAdapter(adapterHT);
        lvReportBHHT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_ScanBH.this).show();
                else {
                    ArrayList<BHHT> list_intent = new ArrayList<>();
                    list_intent.add(listHT.get(position));
                    Intent intent = new Intent(Main_ScanBH.this, Main_Info_BHHT.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("listBHHT", list_intent);
                    intent.putExtra("InfoBHHT", bundle);
                    startActivity(intent);
                }
            }
        });
        lvReportBH1D1 = findViewById(R.id.lvReportBH1D1);
        adapter1D1 = new Adapter_Report_BH1D1(Main_ScanBH.this, R.layout.adapter_report_bh, list1D1);
        lvReportBH1D1.setAdapter(adapter1D1);
        lvReportBH1D1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_ScanBH.this).show();
                else {
                    ArrayList<BH1D1> list_intent = new ArrayList<>();
                    list_intent.add(list1D1.get(position));
                    Intent intent = new Intent(Main_ScanBH.this, Main_Info_BH1D1.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("listBH1D1", list_intent);
                    intent.putExtra("InfoBH1D1", bundle);
                    startActivity(intent);
                }
            }
        });
        lvReportBHDLK = findViewById(R.id.lvReportBHDLK);
        adapterDLK = new Adapter_Report_BHDLK(Main_ScanBH.this, R.layout.adapter_report_bh, listDLKLoc);
        lvReportBHDLK.setAdapter(adapterDLK);
        lvReportBHDLK.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_ScanBH.this).show();
                else {
                    ArrayList<BHDLK> list_intent = new ArrayList<>();
                    for (int i = 0; i < listDLK.size(); i++) {
                        if (listDLKLoc.get(position).getMaBH().equals(listDLK.get(i).getMaBH())) {
                            list_intent.add(listDLK.get(i));
                        }
                    }
                    Intent intent = new Intent(Main_ScanBH.this, Main_Info_BHDLK.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("listBHDLK", list_intent);
                    intent.putExtra("InfoBHDLK", bundle);
                    startActivity(intent);
                }
            }
        });
        lvReportBHDDT = findViewById(R.id.lvReportBHDDT);
        adapterDDT = new Adapter_Report_BHDDT(Main_ScanBH.this, R.layout.adapter_report_bh, listDDT);
        lvReportBHDDT.setAdapter(adapterDDT);
        lvReportBHDDT.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_ScanBH.this).show();
                else {
                    ArrayList<BHDDT> list_intent = new ArrayList<>();
                    list_intent.add(listDDT.get(position));
                    Intent intent = new Intent(Main_ScanBH.this, Main_Info_BHDDT.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("listBHDDT", list_intent);
                    intent.putExtra("InfoBHDDT", bundle);
                    startActivity(intent);
                }
            }
        });
        lvReportBHCXL = findViewById(R.id.lvReportBHCXL);
        adapterCXL = new Adapter_Report_BHCXL(Main_ScanBH.this, R.layout.adapter_report_bh, listCXL);
        lvReportBHCXL.setAdapter(adapterCXL);
        lvReportBHCXL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_ScanBH.this).show();
                else {
                    ArrayList<BHCXL> list_intent = new ArrayList<>();
                    list_intent.add(listCXL.get(position));
                    Intent intent = new Intent(Main_ScanBH.this, Main_Info_BHCXL.class);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("listBHCXL", list_intent);
                    intent.putExtra("InfoBHCXL", bundle);
                    startActivity(intent);
                }
            }
        });
        ll1D1.setVisibility(View.GONE);
        llDLK.setVisibility(View.GONE);
        llHT.setVisibility(View.GONE);
        llDDT.setVisibility(View.GONE);
        llCXL.setVisibility(View.GONE);
        StartScan(activity);
    }

    private void StartScan(Activity activity ) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Quét mã code");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

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
                    arrayList.add(0, new Sanpham_gio(Keys.getTimeNow(), ma, ten, baohanh, nguon, ngaynhap, von, gia));
                    adapterInfoOrder.notifyDataSetChanged();
                    new GetBHHT().execute();
                    new GetBHDLK().execute();
                    new GetBH1D1().execute();
                    new GetBHDDT().execute();
                    new GetBHCXL().execute();
                }   catch (NoSuchElementException nse) {
                    Toasty.error(this, "Lỗi định dạng mã vạch", Toast.LENGTH_LONG, true).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    class GetBH1D1 extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_ScanBH.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_BH_BH1DOI1);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.BH1DOI1_SHEET);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    BH1D1.add(new BH1D1(
                                            object.getString("maBH"),
                                            object.getString("dateToday"),
                                            object.getString("timeToday"),
                                            object.getString("gio"),
                                            object.getString("chinhanhToday"),
                                            object.getString("maNVToday"),
                                            object.getString("tenNVToday"),
                                            object.getString("maOrder"),
                                            object.getString("date"),
                                            object.getString("time"),
                                            object.getString("chinhanh"),
                                            object.getString("tenNV"),
                                            object.getString("maNV"),
                                            object.getString("ma"),
                                            object.getString("ten"),
                                            object.getString("baohanh"),
                                            object.getString("nguon"),
                                            object.getString("ngaynhap"),
                                            object.getString("von"),
                                            object.getString("gia"),
                                            object.getString("ghichuOrder"),
                                            object.getString("tenKH"),
                                            object.getString("sdtKH"),
                                            object.getString("ghichuKH"),
                                            object.getString("gio_moi"),
                                            object.getString("ma_moi"),
                                            object.getString("ten_moi"),
                                            object.getString("baohanh_moi"),
                                            object.getString("nguon_moi"),
                                            object.getString("ngaynhap_moi"),
                                            object.getString("von_moi"),
                                            object.getString("gia_moi"),
                                            object.getString("phibaohanh"),
                                            object.getString("lydo")
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
            if(BH1D1.size() > 0) {
                String sosanh;
                for (int i = 0; i < BH1D1.size(); i++){
                    sosanh = BH1D1.get(i).getMa()+";"+BH1D1.get(i).getTen()+";"+BH1D1.get(i).getBaohanh()+";"+BH1D1.get(i).getNguon()+";"+BH1D1.get(i).getNgaynhap()+";"+BH1D1.get(i).getVon()+";"+BH1D1.get(i).getGia();
                    if (scannedData.equals(sosanh)){
                        list1D1.add(BH1D1.get(i));
                    }
                }
                adapterHT.notifyDataSetChanged();
                if (list1D1.size() != 0){
                    ll1D1.setVisibility(View.VISIBLE);dem++;
                }
            }
        }
    }

    class GetBHHT extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_ScanBH.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_BH_BHHT);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.BHHT_SHEET);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    BHHT.add(new BHHT(
                                            object.getString("maBH"),
                                            object.getString("dateToday"),
                                            object.getString("timeToday"),
                                            object.getString("gio"),
                                            object.getString("chinhanhToday"),
                                            object.getString("maNVToday"),
                                            object.getString("tenNVToday"),
                                            object.getString("maOrder"),
                                            object.getString("date"),
                                            object.getString("time"),
                                            object.getString("chinhanh"),
                                            object.getString("tenNV"),
                                            object.getString("maNV"),
                                            object.getString("ma"),
                                            object.getString("ten"),
                                            object.getString("baohanh"),
                                            object.getString("nguon"),
                                            object.getString("ngaynhap"),
                                            object.getString("von"),
                                            object.getString("gia"),
                                            object.getString("ghichuOrder"),
                                            object.getString("tenKH"),
                                            object.getString("sdtKH"),
                                            object.getString("ghichuKH"),
                                            object.getString("gtConlai"),
                                            object.getString("phitrahang"),
                                            object.getString("lydo")
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
            if(BHHT.size() > 0) {
                String sosanh;
                for (int i = 0; i < BHHT.size(); i++){
                    sosanh = BHHT.get(i).getMa()+";"+BHHT.get(i).getTen()+";"+BHHT.get(i).getBaohanh()+";"+BHHT.get(i).getNguon()+";"+BHHT.get(i).getNgaynhap()+";"+BHHT.get(i).getVon()+";"+BHHT.get(i).getGia();
                    if (scannedData.equals(sosanh)){
                        listHT.add(BHHT.get(i));
                    }
                }
                adapterHT.notifyDataSetChanged();
                if (listHT.size() != 0){
                    llHT.setVisibility(View.VISIBLE);dem++;
                }
            }
        }
    }

    class GetBHDLK extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_ScanBH.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_BH_BHDLK);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.BHDLK_SHEET);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    BHDLK.add(new BHDLK(
                                            object.getString("maBH"),
                                            object.getString("dateToday"),
                                            object.getString("timeToday"),
                                            object.getString("gio"),
                                            object.getString("chinhanhToday"),
                                            object.getString("maNVToday"),
                                            object.getString("tenNVToday"),
                                            object.getString("maOrder"),
                                            object.getString("date"),
                                            object.getString("time"),
                                            object.getString("chinhanh"),
                                            object.getString("tenNV"),
                                            object.getString("maNV"),
                                            object.getString("ma"),
                                            object.getString("ten"),
                                            object.getString("baohanh"),
                                            object.getString("nguon"),
                                            object.getString("ngaynhap"),
                                            object.getString("von"),
                                            object.getString("gia"),
                                            object.getString("ghichuOrder"),
                                            object.getString("tenKH"),
                                            object.getString("sdtKH"),
                                            object.getString("ghichuKH"),
                                            object.getString("gio_moi"),
                                            object.getString("ma_moi"),
                                            object.getString("ten_moi"),
                                            object.getString("baohanh_moi"),
                                            object.getString("nguon_moi"),
                                            object.getString("ngaynhap_moi"),
                                            object.getString("von_moi"),
                                            object.getString("gia_moi"),
                                            object.getString("phidoiSP"),
                                            object.getString("chenhlech"),
                                            object.getString("lydo")
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
            if(BHDLK.size() > 0) {
                String sosanh;
                for (int i = 0; i < BHDLK.size(); i++){
                    sosanh = BHDLK.get(i).getMa()+";"+BHDLK.get(i).getTen()+";"+BHDLK.get(i).getBaohanh()+";"+BHDLK.get(i).getNguon()+";"+BHDLK.get(i).getNgaynhap()+";"+BHDLK.get(i).getVon()+";"+BHDLK.get(i).getGia();
                    if (scannedData.equals(sosanh)){
                        listDLK.add(BHDLK.get(i));
                    }
                }
                for (int i = 0; i < listDLK.size(); i++) {
                    int resultNV = sosanhBH(listDLKLoc, listDLK.get(i).getMaBH());
                    if (resultNV == -1){
                        listDLKLoc.add(listDLK.get(i));
                    }
                }
                adapterDLK.notifyDataSetChanged();
                if (listDLKLoc.size() != 0){
                    llDLK.setVisibility(View.VISIBLE);dem++;
                }
            }
        }
    }


    class GetBHDDT extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_ScanBH.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_BH_BHDDT);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.BHDDT_SHEET);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    BHDDT.add(new BHDDT(
                                            object.getString("maBH"),
                                            object.getString("dateToday"),
                                            object.getString("timeToday"),
                                            object.getString("gio"),
                                            object.getString("chinhanhToday"),
                                            object.getString("maNVToday"),
                                            object.getString("tenNVToday"),
                                            object.getString("maOrder"),
                                            object.getString("date"),
                                            object.getString("time"),
                                            object.getString("chinhanh"),
                                            object.getString("tenNV"),
                                            object.getString("maNV"),
                                            object.getString("ma"),
                                            object.getString("ten"),
                                            object.getString("baohanh"),
                                            object.getString("nguon"),
                                            object.getString("ngaynhap"),
                                            object.getString("von"),
                                            object.getString("gia"),
                                            object.getString("ghichuOrder"),
                                            object.getString("tenKH"),
                                            object.getString("sdtKH"),
                                            object.getString("ghichuKH"),
                                            object.getString("gio_moi"),
                                            object.getString("ma_moi"),
                                            object.getString("ten_moi"),
                                            object.getString("baohanh_moi"),
                                            object.getString("nguon_moi"),
                                            object.getString("ngaynhap_moi"),
                                            object.getString("von_moi"),
                                            object.getString("gia_moi"),
                                            object.getString("phidoiSP"),
                                            object.getString("chenhlech"),
                                            object.getString("lydo")
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
            if(BHDDT.size() > 0) {
                String sosanh;
                for (int i = 0; i < BHDDT.size(); i++){
                    sosanh = BHDDT.get(i).getMa()+";"+BHDDT.get(i).getTen()+";"+BHDDT.get(i).getBaohanh()+";"+BHDDT.get(i).getNguon()+";"+BHDDT.get(i).getNgaynhap()+";"+BHDDT.get(i).getVon()+";"+BHDDT.get(i).getGia();
                    if (scannedData.equals(sosanh)){
                        listDDT.add(BHDDT.get(i));
                    }
                }
                adapterDDT.notifyDataSetChanged();
                if (listDDT.size() != 0){
                    llDDT.setVisibility(View.VISIBLE);dem++;
                }
            }
        }
    }

    class GetBHCXL extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_ScanBH.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_BH_BHCXL);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.BHCXL_SHEET);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    BHCXL.add(new BHCXL(
                                            object.getString("maBH"),
                                            object.getString("dateToday"),
                                            object.getString("timeToday"),
                                            object.getString("gio"),
                                            object.getString("chinhanhToday"),
                                            object.getString("maNVToday"),
                                            object.getString("tenNVToday"),
                                            object.getString("maOrder"),
                                            object.getString("date"),
                                            object.getString("time"),
                                            object.getString("chinhanh"),
                                            object.getString("tenNV"),
                                            object.getString("maNV"),
                                            object.getString("ma"),
                                            object.getString("ten"),
                                            object.getString("baohanh"),
                                            object.getString("nguon"),
                                            object.getString("ngaynhap"),
                                            object.getString("von"),
                                            object.getString("gia"),
                                            object.getString("ghichuOrder"),
                                            object.getString("tenKH"),
                                            object.getString("sdtKH"),
                                            object.getString("ghichuKH"),
                                            object.getString("thoigianhen"),
                                            object.getString("maNVxuly"),
                                            object.getString("tenNVxuly")
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
            if(BHCXL.size() > 0) {
                String sosanh;
                for (int i = 0; i < BHCXL.size(); i++){
                    sosanh = BHCXL.get(i).getMa()+";"+BHCXL.get(i).getTen()+";"+BHCXL.get(i).getBaohanh()+";"+BHCXL.get(i).getNguon()+";"+BHCXL.get(i).getNgaynhap()+";"+BHCXL.get(i).getVon()+";"+BHCXL.get(i).getGia();
                    if (scannedData.equals(sosanh)){
                        listCXL.add(BHCXL.get(i));
                    }
                }
                adapterCXL.notifyDataSetChanged();
                if (listCXL.size() != 0){
                    llCXL.setVisibility(View.VISIBLE);dem++;
                }
            }
            if (dem == 0){
                tvScanBhNoti.setText("Không tìm thấy!");
            }
        }
    }


    private int sosanhBH(ArrayList<BHDLK> baohanh, String ma) {
        int result = -1;
        if (baohanh.size() != 0){
            for (int i = 0; i < baohanh.size(); i++){
                if (baohanh.get(i).getMaBH().equals(ma)){
                    result = i;
                }
            }
        }
        return result;
    }
}
