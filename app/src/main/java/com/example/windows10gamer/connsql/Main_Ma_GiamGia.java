package com.example.windows10gamer.connsql;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.NumberFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;

public class Main_Ma_GiamGia extends AppCompatActivity {

    EditText edSotiengiamgia;
    TextView tvMagiamgia;
    Button btnGiamgia, btnSudungma;
    String maGiamgia, giaTri,session_username,session_ma;
    private static final Random RANDOM = new Random();
    ProgressDialog dialog;
    FloatingActionButton fab;
    Intent intentget;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_ma_giam_gia);
        edSotiengiamgia = (EditText) findViewById(R.id.edSotiengiamgia);
        tvMagiamgia = (TextView) findViewById(R.id.tvMagiamgia);
        btnGiamgia = (Button) findViewById(R.id.btnGiamgia);
        btnSudungma = (Button) findViewById(R.id.btnSudungma);
        fab = (FloatingActionButton) findViewById(R.id.fabmgg);
        btnSudungma.setVisibility(View.INVISIBLE);
        intentget = getIntent();
        session_username = intentget.getStringExtra("session_username");
        session_ma = intentget.getStringExtra("session_ma");
        edSotiengiamgia.setRawInputType(Configuration.KEYBOARD_12KEY);
        edSotiengiamgia.addTextChangedListener(new TextWatcher()

        {
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
                    Connect_Internet.buildDialog(Main_Ma_GiamGia.this).show();
                else {
                    if ((!edSotiengiamgia.getText().toString().trim().equals("0 VNĐ")) && (!edSotiengiamgia.getText().toString().trim().equals(""))) {
                        btnSudungma.setVisibility(View.VISIBLE);
                        maGiamgia = TaoMa();
                        tvMagiamgia.setText(maGiamgia);
                        btnSudungma.setEnabled(true);
                    } else {
                        btnSudungma.setVisibility(View.INVISIBLE);
                        new CustomToast().Show_Toast(Main_Ma_GiamGia.this, findViewById(android.R.id.content), "Chưa nhập số tiền giảm giá!!");
                    }
                }
            }
        });

        btnSudungma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Ma_GiamGia.this).show();
                else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Main_Ma_GiamGia.this);
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
                            new putDataMGG().execute();
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



    public class putDataMGG extends AsyncTask<String, Void, String> {
        protected void onPreExecute(){
            dialog = new ProgressDialog(Main_Ma_GiamGia.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Mã giảm giá đang được tạo.");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... arg0) {

            try{

                // Link Script
                URL url = new URL(Keys.SCRIPT_MAGIAMGIA);

                // Load Json object
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("maGiamgia", maGiamgia);
                postDataParams.put("giaTri", giaTri);


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
            dialog.dismiss();
            new CustomToast().Show_Toast(getApplicationContext(), findViewById(android.R.id.content), result );
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

    public String TaoMa() {
        int helloLength = Keys.HELLOS.length;
        String magiamgia = "";
        for (int i = 0; i < Keys.SOMA_GIAMGIA; i++){
            String hello = Keys.HELLOS[RANDOM.nextInt(helloLength)];
            magiamgia += hello;
        }
        return magiamgia;
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
                            new CustomToast().Show_Toast(Main_Ma_GiamGia.this, findViewById(android.R.id.content), "Lỗi ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Ma_GiamGia.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_MAGIAMGIA_WEB);
                params.put("maGiamgia", maGiamgia);
                params.put("giaTri", giaTri);
                params.put("nguoiTao", session_username);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
