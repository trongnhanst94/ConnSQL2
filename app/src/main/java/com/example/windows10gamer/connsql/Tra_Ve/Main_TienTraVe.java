package com.example.windows10gamer.connsql.Tra_Ve;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.windows10gamer.connsql.Adapter.Adapter_Tientrave;
import com.example.windows10gamer.connsql.Object.TienTraVe;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
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
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class Main_TienTraVe extends AppCompatActivity {
    TextView tvtoday;
    Button btnthemtientrave;
    ListView lvtientrave;
    String session_username, session_ma, chinhanh, ngay, ca, noidung, sotien;
    SharedPreferences shared;
    private ProgressDialog dialog2;
    Adapter_Tientrave adapter;
    ArrayList<TienTraVe> arraylist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tien_tra_ve);
        btnthemtientrave = findViewById(R.id.btnthemtrave);
        lvtientrave = findViewById(R.id.lvtientrave);
        tvtoday = findViewById(R.id.tvtoday);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        Intent intent = getIntent();
        session_username  = intent.getStringExtra("session_username");
        session_ma        = intent.getStringExtra("session_ma");
        ngay = Keys.getDateNow();
        ca = Keys.getCalam(chinhanh);
        tvtoday.setText("Ngày: "+ca+" "+ngay);
        arraylist = new ArrayList<TienTraVe>();
        adapter = new Adapter_Tientrave(Main_TienTraVe.this, arraylist);
        lvtientrave.setAdapter(adapter);
        new GetData().execute(chinhanh);
        lvtientrave.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetUser(position, Main_TienTraVe.this,arraylist.get(position).getMaNV());
            }
        });
        btnthemtientrave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = null;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    dialog = new AlertDialog.Builder(Main_TienTraVe.this);
                } else {
                    dialog = new AlertDialog.Builder(Main_TienTraVe.this);
                }
                dialog.setIcon(R.drawable.ic_addchi)
                        .setTitle("Tạo phiếu tiền trả về");
                View mView = getLayoutInflater().inflate(R.layout.dialog_tientrave, null);
                dialog.setCancelable(false);
                final ImageView ivAvatar = mView.findViewById(R.id.ivAvatar);
                final TextView tvchimanv = mView.findViewById(R.id.tvchimanv);
                final TextView tvchitennv = mView.findViewById(R.id.tvchitennv);
                final TextView tvchichinhanh = mView.findViewById(R.id.tvchichinhanh);
                final EditText edchinoidung = mView.findViewById(R.id.edchinoidung);
                final EditText edchisotien = mView.findViewById(R.id.edchisotien);
                shared = getSharedPreferences("login", MODE_PRIVATE);
                Glide.with(Main_TienTraVe.this).load(shared.getString("img", "")).override(300,300).fitCenter().into(ivAvatar);
                tvchichinhanh.setText(chinhanh);
                tvchimanv.setText("Mã số: "+session_ma);
                tvchitennv.setText("Tên nhân viên: "+session_username);
                dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        noidung = edchinoidung.getText().toString().trim();
                        sotien = edchisotien.getText().toString().trim();
                        if (!noidung.equals("") && !sotien.equals("") && !sotien.equals("0")){
                            new SendRequest().execute();
                            putData();
                            new GetData().execute(chinhanh);
                        } else {
                            new CustomToast().Show_Toast(Main_TienTraVe.this, findViewById(android.R.id.content), "Phải nhập tất cả các trường!!");
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

    private void GetUser(final int position, final Context c, final String manhanvien) {
        final ArrayList<User> usernames = new ArrayList<User>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Keys.MAIN_LINKAVATAR+"?manhanvien="+manhanvien, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        AlertDialog.Builder dialog = null;
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                            dialog = new AlertDialog.Builder(Main_TienTraVe.this);
                        } else {
                            dialog = new AlertDialog.Builder(Main_TienTraVe.this);
                        }
                        dialog.setIcon(R.drawable.ic_addchi)
                                .setTitle("Tạo phiếu tiền trả về");
                        View mView = getLayoutInflater().inflate(R.layout.dialog_tientrave, null);
                        dialog.setCancelable(false);
                        final ImageView ivAvatar = mView.findViewById(R.id.ivAvatar);
                        final LinearLayout lnHidden = mView.findViewById(R.id.lnHidden);
                        final TextView tvchingay = mView.findViewById(R.id.tvchingay);
                        final TextView tvchica = mView.findViewById(R.id.tvchica);
                        final TextView tvchimanv = mView.findViewById(R.id.tvchimanv);
                        final TextView tvchitennv = mView.findViewById(R.id.tvchitennv);
                        final TextView tvchichinhanh = mView.findViewById(R.id.tvchichinhanh);
                        final EditText edchinoidung = mView.findViewById(R.id.edchinoidung);
                        final EditText edchisotien = mView.findViewById(R.id.edchisotien);
                        lnHidden.setVisibility(View.VISIBLE);
                        tvchingay.setText(arraylist.get(position).getNgay());
                        tvchica.setText(arraylist.get(position).getCa());
                        tvchichinhanh.setText(chinhanh);
                        tvchimanv.setText("Mã số: "+arraylist.get(position).getMaNV());
                        tvchitennv.setText("Tên nhân viên: "+arraylist.get(position).getTenNV());
                        edchinoidung.setEnabled(false);
                        edchisotien.setEnabled(false);
                        edchinoidung.setText(arraylist.get(position).getNoidung());
                        edchisotien.setText(Keys.setMoney(Integer.valueOf(arraylist.get(position).getSotien())));
                        dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.setView(mView);
                        AlertDialog al = dialog.create();
                        al.show();
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                usernames.add(new User(
                                        object.getInt("id"),
                                        object.getString("ma_user"),
                                        object.getString("ten"),
                                        object.getString("shortName"),
                                        object.getString("username"),
                                        object.getString("password"),
                                        object.getString("level"),
                                        object.getString("chucdanh"),
                                        object.getString("trangthai"),
                                        object.getString("created"),
                                        object.getString("updated"),
                                        object.getString("img")
                                ));
                                Glide.with(c).load(object.getString("img")).override(300,300).fitCenter().into(ivAvatar);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
    }

    public class SendRequest extends AsyncTask<Void, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(Void... arg0) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main_TienTraVe.this).show();
            else {
                addTientraveWeb(ngay, ca, chinhanh, session_ma, session_username, noidung, sotien);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    public void ResetActivity(){
        Intent intentput = new Intent(Main_TienTraVe.this, Main_TienTraVe.class);
        intentput.putExtra("session_username", session_username);
        intentput.putExtra("session_ma", session_ma);
        startActivity(intentput);
        finish();
    }

    public void addTientraveWeb(final String ngay, final String ca, final String chinhanh, final String session_ma, final String session_username, final String noidung, final String sotien){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            new CustomToast().Show_Toast(Main_TienTraVe.this, findViewById(android.R.id.content), "Thất bại, không kết nối được Server!!");
                        } else if (response.trim().equals("success")){
                            new CustomToast().Show_Toast(Main_TienTraVe.this, findViewById(android.R.id.content), "Tạo phiếu chi thành công!!");
                            ResetActivity();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_TienTraVe.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_TIENTRAVE_WEB);
                params.put("maTV", "TV_"+ Keys.MaDonhang());
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
            URL url = new URL(Keys.SCRIPT_TIENTRAVE);

            // Load Json object
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("maTV", "TV_"+ Keys.MaDonhang());
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

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog2 = new ProgressDialog(Main_TienTraVe.this);
            dialog2.setTitle("Hãy chờ...");
            dialog2.setMessage("Dữ liệu đang được tải xuống");
            dialog2.setCancelable(false);
            dialog2.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_TIENTRAVE+"?chinhanh="+params[0]);
            try {
                if (jsonObject != null) {
                    arraylist.clear();
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.TIEN_TRA_VE);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    arraylist.add(new TienTraVe(
                                            object.getString("maTV"),
                                            object.getString("ngay"),
                                            object.getString("ca"),
                                            object.getString("chinhanh"),
                                            object.getString("maNV"),
                                            object.getString("tenNV"),
                                            object.getString("noidung"),
                                            object.getString("sotien")
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
            if(arraylist.size() > 0) {
                adapter.notifyDataSetChanged();
            } else {
                new CustomToast().Show_Toast(Main_TienTraVe.this, findViewById(android.R.id.content), "Không có phiếu trả về nào!!");
            }
            dialog2.dismiss();
        }
    }
}
