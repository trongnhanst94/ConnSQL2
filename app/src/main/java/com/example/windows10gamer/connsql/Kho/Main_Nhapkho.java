package com.example.windows10gamer.connsql.Kho;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Nhapkho;
import com.example.windows10gamer.connsql.Object.Kho;
import com.example.windows10gamer.connsql.Object.Kho_Soluong;
import com.example.windows10gamer.connsql.Object.Minstock;
import com.example.windows10gamer.connsql.Object.Nhapkho;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

public class Main_Nhapkho extends AppCompatActivity {

    ArrayList<Kho> kholist = new ArrayList<>();
    ArrayList<Kho_Soluong> khoplus = new ArrayList<>();
    ArrayList<Minstock> minstocklist = new ArrayList<>();
    ArrayList<Nhapkho> nhapkholist = new ArrayList<>();
    private ProgressDialog dialog;
    private SharedPreferences shared;
    private String chinhanh;
    Adapter_Nhapkho adapter;
    ListView listView;
    EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_nhapkho);
        listView = findViewById(R.id.listView);
        searchView = findViewById(R.id.searchView);
        adapter = new Adapter_Nhapkho(Main_Nhapkho.this, R.layout.adapter_nhapkho, nhapkholist);
        listView.setAdapter(adapter);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        new GetMinstock().execute();
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

    // TODO: 4/7/2018 Get Míntock
    class GetMinstock extends AsyncTask<String, Void, Void> {

        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_Nhapkho.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_MINSTOCK);
            try {
                if (jsonObject != null) {
                    minstocklist.clear();
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.MINSTOCK);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    if (object.getString("chinhanh").equals(chinhanh)){
                                        minstocklist.add(new Minstock(
                                                object.getString("id"),
                                                object.getString("chinhanh"),
                                                object.getString("ma"),
                                                object.getString("ten"),
                                                object.getString("baohanh"),
                                                object.getString("nguon"),
                                                Keys.giaimagiavon(object.getString("von")),
                                                object.getString("gia"),
                                                object.getString("minstock")
                                        ));
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
            if(minstocklist.size() > 0) {
                new GetKho().execute();
            }
        }
    }

    // TODO: 4/7/2018 Get kho
    class GetKho extends AsyncTask<Void, Integer, String> {

        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected String doInBackground(Void... params) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main_Nhapkho.this).show();
            else {
                JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_KHO);
                try {
                    if (jsonObject != null) {
                        kholist.clear();
                        if(jsonObject.length() > 0) {
                            JSONArray array = jsonObject.getJSONArray(Keys.KHO);
                            int lenArray = array.length();
                            if(lenArray > 0) {
                                for( ; jIndex < lenArray; jIndex++) {
                                    try {
                                        JSONObject object = array.getJSONObject(jIndex);
                                        if (object.getString("chinhanh").equals(chinhanh)){
                                            kholist.add(new Kho(
                                                    object.getString("id"),
                                                    object.getString("chinhanh"),
                                                    object.getString("kho"),
                                                    object.getString("maNV"),
                                                    object.getString("tenNV"),
                                                    object.getString("ma"),
                                                    object.getString("ten"),
                                                    object.getString("baohanh"),
                                                    Keys.giaimagiavon(object.getString("nguon")),
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
                    }
                } catch (JSONException je) {
                    Log.i(JSONParser.TAG, "" + je.getLocalizedMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (kholist.size() > 0) {
                for (int i = 0; i < kholist.size(); i++) {
                    int sosanh = iniSosanh(khoplus, kholist.get(i).getMa());
                    if (sosanh == -1){
                        khoplus.add(new Kho_Soluong(
                                kholist.get(i).getChinhanh(),
                                kholist.get(i).getKho(),
                                kholist.get(i).getMa(),
                                kholist.get(i).getTen(),
                                kholist.get(i).getBaohanh(),
                                kholist.get(i).getNguon(),
                                kholist.get(i).getNgaynhap(),
                                kholist.get(i).getVon(),
                                kholist.get(i).getGia(),
                                1
                        ));
                    } else {
                        khoplus.set(sosanh, new Kho_Soluong(
                                khoplus.get(sosanh).getChinhanh(),
                                khoplus.get(sosanh).getKho(),
                                khoplus.get(sosanh).getMa(),
                                khoplus.get(sosanh).getTen(),
                                khoplus.get(sosanh).getBaohanh(),
                                khoplus.get(sosanh).getNguon(),
                                khoplus.get(sosanh).getNgaynhap(),
                                khoplus.get(sosanh).getVon(),
                                khoplus.get(sosanh).getGia(),
                                khoplus.get(sosanh).getSoluong()+1)
                        );
                    }
                }
                for (int i = 0; i < minstocklist.size(); i++) {
                    for (int j = 0; j < khoplus.size(); j++) {
                        if ((minstocklist.get(i).getMa().equals(khoplus.get(j).getMa())) && (minstocklist.get(i).getNguon().equals(khoplus.get(j).getNguon()))){
                            nhapkholist.add(new Nhapkho(
                                    minstocklist.get(i).getMa(),
                                    minstocklist.get(i).getTen(),
                                    minstocklist.get(i).getBaohanh(),
                                    minstocklist.get(i).getNguon(),
                                    minstocklist.get(i).getVon(),
                                    minstocklist.get(i).getGia(),
                                    khoplus.get(j).getSoluong() - Integer.valueOf(minstocklist.get(i).getMinstock()),
                                    0
                            ));
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }
            dialog.dismiss();
        }
    }

    public void check(int position) {
        nhapkholist.get(position).setTrangthai(1);
        adapter.notifyDataSetChanged();
    }

    public void uncheck(int position) {
        nhapkholist.get(position).setTrangthai(0);
        adapter.notifyDataSetChanged();
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
