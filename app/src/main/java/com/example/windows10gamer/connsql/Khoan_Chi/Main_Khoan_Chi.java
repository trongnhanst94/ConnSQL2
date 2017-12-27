package com.example.windows10gamer.connsql.Khoan_Chi;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.example.windows10gamer.connsql.Object.XuatNhap;
import com.example.windows10gamer.connsql.Object.XuatNhap_SL;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;
import com.example.windows10gamer.connsql.Xuat_Nhap.Main_XuatNhap;

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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Main_Khoan_Chi extends AppCompatActivity {

    Button btnthemchi;
    ListView lvkhoanchi;
    String session_username, session_ma, chinhanh, ngay, ca, noidung, sotien, madonhang;
    SharedPreferences shared;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_khoan_chi);
        btnthemchi = (Button) findViewById(R.id.btnthemchi);
        lvkhoanchi = (ListView) findViewById(R.id.lvkhoanchi);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        Intent intent = getIntent();
        new GetData().execute(chinhanh);
        session_username  = intent.getStringExtra("session_username");
        session_ma        = intent.getStringExtra("session_ma");
        ngay = Keys.getDateNow();
        ca = Keys.getCalam(chinhanh);
        madonhang = Keys.MaDonhang();
        btnthemchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = null;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    dialog = new AlertDialog.Builder(Main_Khoan_Chi.this);
                } else {
                    dialog = new AlertDialog.Builder(Main_Khoan_Chi.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
                }
                dialog.setIcon(R.drawable.ic_addchi)
                        .setTitle("Tạo phiếu chi");
                View mView = getLayoutInflater().inflate(R.layout.dialog_khoanchi, null);
                dialog.setCancelable(false);
                final TextView tvchimanv = (TextView) mView.findViewById(R.id.tvchimanv);
                final TextView tvchitennv = (TextView) mView.findViewById(R.id.tvchitennv);
                final TextView tvchichinhanh = (TextView) mView.findViewById(R.id.tvchichinhanh);
                final EditText edchinoidung = (EditText) mView.findViewById(R.id.edchinoidung);
                final EditText edchisotien = (EditText) mView.findViewById(R.id.edchisotien);
                tvchichinhanh.setText(chinhanh);
                tvchimanv.setText("Mã số: "+session_ma);
                tvchitennv.setText("Tên nhân viên: "+session_username);
                dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        noidung = edchinoidung.getText().toString().trim();
                        sotien = edchisotien.getText().toString().trim();
                        if (noidung.equals("") && sotien.equals("") && sotien.equals("0")){
                            new CustomToast().Show_Toast(Main_Khoan_Chi.this, findViewById(android.R.id.content), "Phải nhập tất cả các trường!!");
                        } else {
                            new SendRequestD1().execute();
                        }
                    }
                });
                dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.setView(mView);
                AlertDialog al = dialog.create();
                al.show();
            }
        });
    }

    public class SendRequestD1 extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(String... arg0) {
            addChiWeb(madonhang, ngay, ca, chinhanh, session_ma, session_username, noidung, sotien);
            putData();
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }


    public void addChiWeb(final String madonhang, final String ngay, final String ca, final String chinhanh, final String session_ma, final String session_username, final String noidung, final String sotien){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            new CustomToast().Show_Toast(Main_Khoan_Chi.this, findViewById(android.R.id.content), "Thất bại, không kết nối được Server!!");
                        } else if (response.trim().equals("success")){
                            new CustomToast().Show_Toast(Main_Khoan_Chi.this, findViewById(android.R.id.content), "Tạo phiếu chi thành công!!");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Khoan_Chi.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_CHI_WEB);
                params.put("maKC", "CHI_"+ madonhang);
                params.put("ngay", ngay);
                params.put("ca", ca);
                params.put("chinhanh", chinhanh);
                params.put("maNV", session_ma);
                params.put("tenNV", session_username);
                params.put("noidung", noidung);
                params.put("sotien", sotien);
                Log.e("params", params.toString());
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
        Toast.makeText(this, result.toString(), Toast.LENGTH_SHORT).show();
        return result.toString();
    }

    public String putData(){
        try {
            // Link Script
            URL url = new URL(Keys.SCRIPT_KHOANCHI);

            // Load Json object
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("maKC", "CHI_"+ madonhang);
            postDataParams.put("ngay", ngay);
            postDataParams.put("ca", ca);
            postDataParams.put("chinhanh", chinhanh);
            postDataParams.put("maNV", session_ma);
            postDataParams.put("tenNV", session_username);
            postDataParams.put("noidung", noidung);
            postDataParams.put("sotien", sotien);

            Log.e("postDataParams", postDataParams.toString());

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

    class GetData extends AsyncTask<String, Void, Void> {

        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_Khoan_Chi.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_KHOANCHI+"?chinhanh="+params[0]);
            try {
                //list.clear();
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.XUATNHAP);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
//                                    list.add(new XuatNhap(
//                                            object.getString("maXN"),
//                                            object.getString("ngay"),
//                                            object.getString("ca"),
//                                            object.getString("gio"),
//                                            object.getString("chinhanhToday"),
//                                            object.getString("maNVToday"),
//                                            object.getString("tenNVToday"),
//                                            object.getString("ma"),
//                                            object.getString("ten"),
//                                            object.getString("baohanh"),
//                                            object.getString("nguon"),
//                                            object.getString("ngaynhap"),
//                                            object.getString("von"),
//                                            object.getString("gia"),
//                                            object.getString("ghichu"),
//                                            object.getString("chinhanhNhan"),
//                                            object.getString("maNVNhan"),
//                                            object.getString("tenNVNhan"),
//                                            object.getString("trangthai")
//                                    ));
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
//            if(list.size() > 0) {
//            }
            dialog.dismiss();
        }
    }
}
