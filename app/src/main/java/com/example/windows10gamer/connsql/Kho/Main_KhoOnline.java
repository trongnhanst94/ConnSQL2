package com.example.windows10gamer.connsql.Kho;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.windows10gamer.connsql.Adapter.Adapter_Kho;
import com.example.windows10gamer.connsql.Object.Kho;
import com.example.windows10gamer.connsql.Object.Kho_Dialog;
import com.example.windows10gamer.connsql.Object.Kho_Soluong;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import es.dmoral.toasty.Toasty;

public class Main_KhoOnline extends AppCompatActivity {
    ListView listView;
    Adapter_Kho adapter;
    ArrayList<Kho_Soluong> khoList = new ArrayList<>();
    private ProgressDialog dialog;
    private EditText searchView;
    ArrayList<Kho> orinal = new ArrayList<>();
    ArrayList<Kho_Dialog> kho_dialog = new ArrayList<>();
    ArrayList<Kho_Dialog> kho_item = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_khoonline);
        listView = findViewById(R.id.listview);
        new GetKho().execute();
        adapter = new Adapter_Kho(Main_KhoOnline.this, R.layout.adapter_kho, khoList, orinal);
        listView.setAdapter(adapter);
        searchView = findViewById(R.id.svSearchItem);
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable arg0) {
                String text = searchView.getText().toString().toLowerCase(Locale.getDefault());
                adapter.getFilter().filter(text);
            }
            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
            }
        });
    }

    class GetKho extends AsyncTask<Void, Integer, String> {

        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_KhoOnline.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setCancelable(false);
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.show();
        }

        @Nullable
        @Override
        protected String doInBackground(Void... params) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main_KhoOnline.this).show();
            else {
                JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_KHO);
                try {
                    if (jsonObject != null) {
                        orinal.clear();
                        if(jsonObject.length() > 0) {
                            JSONArray array = jsonObject.getJSONArray(Keys.KHO);
                            int lenArray = array.length();
                            if(lenArray > 0) {
                                for( ; jIndex < lenArray; jIndex++) {
                                    try {
                                        JSONObject object = array.getJSONObject(jIndex);
                                        orinal.add(new Kho(
                                                object.getString("id"),
                                                object.getString("chinhanh"),
                                                object.getString("kho"),
                                                object.getString("maNV"),
                                                object.getString("tenNV"),
                                                object.getString("ma"),
                                                object.getString("ten"),
                                                object.getString("baohanh"),
                                                object.getString("nguon"),
                                                object.getString("ngaynhap"),
                                                object.getString("von"),
                                                object.getString("gia")
                                        ));
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
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int prog = values[0];
            dialog.setMessage("Đang tải xuống "+prog+" sản phẩm...");
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (orinal.size() > 0) {
                for (int i = 0; i < orinal.size(); i++) {
                    int sosanh = iniSosanh(khoList, orinal.get(i).getMa());
                    if (sosanh == -1){
                        khoList.add(new Kho_Soluong(
                                orinal.get(i).getChinhanh(),
                                orinal.get(i).getKho(),
                                orinal.get(i).getMa(),
                                orinal.get(i).getTen(),
                                orinal.get(i).getBaohanh(),
                                orinal.get(i).getNguon(),
                                orinal.get(i).getNgaynhap(),
                                orinal.get(i).getVon(),
                                orinal.get(i).getGia(),
                                1
                        ));
                    } else {
                        khoList.set(sosanh, new Kho_Soluong(
                                khoList.get(sosanh).getChinhanh(),
                                khoList.get(sosanh).getKho(),
                                khoList.get(sosanh).getMa(),
                                khoList.get(sosanh).getTen(),
                                khoList.get(sosanh).getBaohanh(),
                                khoList.get(sosanh).getNguon(),
                                khoList.get(sosanh).getNgaynhap(),
                                khoList.get(sosanh).getVon(),
                                khoList.get(sosanh).getGia(),
                                khoList.get(sosanh).getSoluong()+1)
                        );
                    }
                }
                adapter.notifyDataSetChanged();
            } else {
                Toasty.info(Main_KhoOnline.this, "Không có sản phẩm", Toast.LENGTH_LONG, true).show();
            }
            dialog.dismiss();
        }
    }

    private int iniSosanh(ArrayList<Kho_Soluong> khoList, String ma) {
        int result = -1;
        if (khoList.size() > 0){
            for (int i = 0; i < khoList.size(); i++) {
                if (khoList.get(i).getMa().equals(ma)){
                    result = i;
                }
            }
        }
        return result;
    }

}
