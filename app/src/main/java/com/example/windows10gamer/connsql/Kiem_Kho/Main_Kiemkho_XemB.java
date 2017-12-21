package com.example.windows10gamer.connsql.Kiem_Kho;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Adapter.Adapter_XemB;
import com.example.windows10gamer.connsql.Object.Sanpham_ID;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Main_Kiemkho_XemB extends AppCompatActivity {
    ArrayList<Sanpham_ID> sanphams = new ArrayList<>();
    String chinhanh, kho, nameUser, maUser;
    ListView listView;
    Adapter_XemB adapter;
    private EditText searchView;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_kiemkho_xemb);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("BundlexemB");
        chinhanh = bundle.getString("chinhanh");
        kho = bundle.getString("kho");
        nameUser = bundle.getString("nameUserB");
        maUser = bundle.getString("snUserB");
        new GetDataKho().execute();
        listView = (ListView) findViewById(R.id.lvXemB);
        adapter = new Adapter_XemB(Main_Kiemkho_XemB.this,R.layout.adapter_xem, sanphams);
        listView.setAdapter(adapter);
        searchView = (EditText) findViewById(R.id.svSearchItemXemB);

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

    public void DeleteSP(final String msp, final String id){
        for (int i =  0; i <= sanphams.size(); i++){
            if (sanphams.get(i).getMa() == msp) {
                sanphams.remove(i);
                new SendRequestHT().execute(msp);
                deleteXemB(id);
                break;
            }
        }
        adapter.notifyDataSetChanged();

    }

    public void deleteXemB(final String id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            new CustomToast().Show_Toast(Main_Kiemkho_XemB.this, findViewById(android.R.id.content), "Lỗi ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Kiemkho_XemB.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.DELE_XEM_WEB);
                params.put("id", id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public class SendRequestHT extends AsyncTask<String, Void, String> {
        protected void onPreExecute(){
            dialog = new ProgressDialog(Main_Kiemkho_XemB.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được xử lý");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... params) {

            try{

                // Link Script
                URL url = new URL(Keys.SCRIPT_DE_XEMA);

                // Load Json object
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("Ma", params[0]);
                postDataParams.put("chinhanh", chinhanh);
                postDataParams.put("kho", kho);
                postDataParams.put("nameUser", nameUser);
                Log.d("qqq", params[0]+chinhanh+kho+nameUser);

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
            new CustomToast().Show_Toast(Main_Kiemkho_XemB.this, findViewById(android.R.id.content), result);
            //ResetActivity();
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
        finish();
    }

    class GetDataKho extends AsyncTask<Void, Void, Void> {

        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            x = sanphams.size();
            if(x == 0)
                jIndex = 0;
            else
                jIndex = x;
            dialog = new ProgressDialog(Main_Kiemkho_XemB.this);
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
                                    if (chinhanh.equals(object.getString("vitri")) && (object.getString("kho").equals(kho)) && (object.getString("maNhanvien").equals(maUser))){
                                        sanphams.add(new Sanpham_ID(
                                                object.getString("id"),
                                                object.getString("gio"),
                                                object.getString("maSanpham"),
                                                object.getString("tenSanpham"),
                                                object.getString("baohanhSanpham"),
                                                object.getString("nguonSanpham"),
                                                object.getString("ngaynhapSanpham"),
                                                object.getString("vonSanpham"),
                                                object.getString("giaSanpham")
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
            if(sanphams.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                new CustomToast().Show_Toast(Main_Kiemkho_XemB.this, findViewById(android.R.id.content), "Không có dữ liệu!!");
            }
            dialog.dismiss();
        }
    }
}
