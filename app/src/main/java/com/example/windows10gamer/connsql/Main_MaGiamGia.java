package com.example.windows10gamer.connsql;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Adapter.Adapter_Magiamgia;
import com.example.windows10gamer.connsql.Object.Magiamgia;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Main_MaGiamGia extends AppCompatActivity {

    EditText edSotiengiamgia;
    TextView tvMagiamgia;
    Button btnGiamgia, btnSudungma;
    String maGiamgia, giaTri,session_username,session_ma;
    ProgressDialog dialog;
    FloatingActionButton fab;
    Intent intentget;
    ArrayList<Magiamgia> contactList = new ArrayList<>();
    Adapter_Magiamgia adapter;
    ListView lvmagiamgia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ma_giam_gia);
        edSotiengiamgia = findViewById(R.id.edSotiengiamgia);
        tvMagiamgia = findViewById(R.id.tvMagiamgia);
        btnGiamgia = findViewById(R.id.btnGiamgia);
        btnSudungma = findViewById(R.id.btnSudungma);
        fab = findViewById(R.id.fabmgg);
        lvmagiamgia = findViewById(R.id.lvmagiamgia);
        btnSudungma.setVisibility(View.INVISIBLE);
        intentget = getIntent();
        session_username = intentget.getStringExtra("session_username");
        session_ma = intentget.getStringExtra("session_ma");
        adapter = new Adapter_Magiamgia(Main_MaGiamGia.this, contactList);
        lvmagiamgia.setAdapter(adapter);
        new SendRequest().execute();
        edSotiengiamgia.setRawInputType(Configuration.KEYBOARD_12KEY);
        edSotiengiamgia.addTextChangedListener(new TextWatcher(){
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count){
                if(s.toString().length() > 0){
                    edSotiengiamgia.removeTextChangedListener(this);
                    String numbers = removeCharacters(s.toString());
                    int money = 0;
                    try{
                        money = Integer.parseInt(numbers);
                    }
                    catch(Exception ex){
                        money = 0;
                    }

                    edSotiengiamgia.setText(getMoney(money));
                    //Set cursor on correct position
                    int selection = start;
                    if(count > 0){
                        selection++;
                        if(edSotiengiamgia.getText().toString().length() == 2 || edSotiengiamgia.getText().toString().length() == 6 || edSotiengiamgia.getText().toString().length() == 10){
                            selection++;
                        }
                    }
                    else{
                        if(edSotiengiamgia.getText().toString().length() == 4 || edSotiengiamgia.getText().toString().length() == 8){
                            selection--;
                        }
                    }

                    if(selection > edSotiengiamgia.getText().toString().length()){
                        selection = edSotiengiamgia.getText().toString().length();
                    }

                    edSotiengiamgia.setSelection(selection);
                    edSotiengiamgia.addTextChangedListener(this);
                }
                if(s.toString().length() == 1 && count < 1 && start == 1){
                    edSotiengiamgia.removeTextChangedListener(this);
                    edSotiengiamgia.setText("");
                    edSotiengiamgia.addTextChangedListener(this);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after){}

            @Override
            public void afterTextChanged(Editable s){}
        });
        btnGiamgia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_MaGiamGia.this).show();
                else {
                    if ((!edSotiengiamgia.getText().toString().trim().equals("0 VNĐ")) && (!edSotiengiamgia.getText().toString().trim().equals(""))) {
                        btnSudungma.setVisibility(View.VISIBLE);
                        maGiamgia = Keys.TaoMa();
                        tvMagiamgia.setText(maGiamgia);
                        btnSudungma.setEnabled(true);
                    } else {
                        btnSudungma.setVisibility(View.INVISIBLE);
                        Toasty.warning(Main_MaGiamGia.this, "Chưa nhập số tiền giảm giá", Toast.LENGTH_LONG, true).show();
                    }
                }
            }
        });

        btnSudungma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_MaGiamGia.this).show();
                else {
                    AlertDialog.Builder dialog = null;
                    if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        dialog = new AlertDialog.Builder(Main_MaGiamGia.this);
                    } else {
                        dialog = new AlertDialog.Builder(Main_MaGiamGia.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                    }
                    dialog.setIcon(R.drawable.ic_settings);
                    dialog.setMessage("Bạn muốn sử dụng mã giảm giá này?");
                    dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            //new putDataMGG().execute();
                            addMagiamgiaWeb();
                            btnSudungma.setVisibility(View.GONE);
                        }
                    });
                    dialog.show();
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ResetActivity();
            }
        });
    }

    public void Delete(final String id) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toasty.success(Main_MaGiamGia.this, "Xóa thành công", Toast.LENGTH_LONG, true).show();
                            ResetActivity();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main_MaGiamGia.this, "Lỗi "+error, Toast.LENGTH_LONG, true).show();
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

    public String removeCharacters(String money){

        int i=0;
        while (i<money.length())
        {
            Character c = money.charAt(i);
            if (Character.isDigit(c) && c != '.')
            {
                i++;
            }
            else
            {
                money = money.replace(c.toString(), "");
            }
        }
        return money;
    }

    public String getMoney(int value){
        String money = "";
        giaTri = String.valueOf(value);
        NumberFormat numberFormatter;
        numberFormatter = NumberFormat.getNumberInstance(Locale.GERMAN);
        money += numberFormatter.format(value);
        return money + "  VNĐ";
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
        edSotiengiamgia.setText("");
        tvMagiamgia.setText("0000");
    }

    public void addMagiamgiaWeb(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toasty.error(Main_MaGiamGia.this, "Lỗi ", Toast.LENGTH_LONG, true).show();
                        } else if (response.trim().equals("success")){
                            Toasty.success(Main_MaGiamGia.this, "Tạo mã thành công", Toast.LENGTH_LONG, true).show();
                            ResetActivity();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main_MaGiamGia.this, "Lỗi "+error, Toast.LENGTH_LONG, true).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_MAGIAMGIA_WEB);
                params.put("maGiamgia", maGiamgia);
                params.put("giaTri", giaTri);
                params.put("nguoiTao", session_ma);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public class SendRequest extends AsyncTask<Void, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(Main_MaGiamGia.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(Void... arg0) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main_MaGiamGia.this).show();
            else {
                new GetMagiamgia().execute();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    class GetMagiamgia extends AsyncTask<String, Void, Void> {

        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_MAGIAMGIA);
            try {
                if (jsonObject != null) {
                    contactList.clear();
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.MAGIAMGIA);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    contactList.add(new Magiamgia(
                                            object.getString("maGiamgia"),
                                            object.getString("giaTri"),
                                            object.getString("nguoiTao"),
                                            object.getString("trangThai"),
                                            object.getString("created")
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
            if (contactList.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                Toasty.warning(Main_MaGiamGia.this, "Không có mã giảm giá", Toast.LENGTH_LONG, true).show();
            }
            dialog.dismiss();
        }
    }
}
