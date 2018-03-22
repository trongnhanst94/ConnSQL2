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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
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

import es.dmoral.toasty.Toasty;

public class Main_TienTraVe extends AppCompatActivity {
    TextView tvtoday;
    Button btnthemtientrave;
    ListView lvtientrave;
    String session_username, session_ma, chinhanh, ngay, ca, noidung, sotien;
    SharedPreferences shared;
    ProgressDialog dialog;
    Adapter_Tientrave adapter;
    ArrayList<TienTraVe> arraylist;
    private String maMa;
    String loai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tien_tra_ve);
        btnthemtientrave = findViewById(R.id.btnthemtrave);
        lvtientrave = findViewById(R.id.lvtientrave);
        tvtoday = findViewById(R.id.tvtoday);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        shared = getSharedPreferences("login", MODE_PRIVATE);
        session_username = shared.getString("shortName", "");
        session_ma = shared.getString("ma", "");
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
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_TienTraVe.this).show();
                else {
                    GetDanhmuc(new VolleyCallback() {
                        @Override
                        public void onSuccess(final ArrayList<String> danhmucList) {
                            AlertDialog.Builder dialog = null;
                            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
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
                            final Spinner snchi = mView.findViewById(R.id.snchi);
                            ArrayAdapter mAdapter;
                            mAdapter = new ArrayAdapter<>(Main_TienTraVe.this, android.R.layout.simple_spinner_item, danhmucList);
                            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            snchi.setAdapter(mAdapter);
                            selectValue(snchi, chinhanh);
                            snchi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    loai = snchi.getSelectedItem().toString();
                                }
                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });
                            shared = getSharedPreferences("login", MODE_PRIVATE);
                            Glide.with(Main_TienTraVe.this).load(shared.getString("img", "")).override(300, 300).fitCenter().into(ivAvatar);
                            tvchichinhanh.setText(chinhanh);
                            tvchimanv.setText("Mã số: " + session_ma);
                            tvchitennv.setText("Tên nhân viên: " + session_username);
                            dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    if (loai.equals("Chọn")){
                                        Toasty.warning(Main_TienTraVe.this, "Bạn chưa chọn Loại tiền trả về", Toast.LENGTH_LONG, true).show();
                                        dialog.dismiss();
                                    } else {
                                        noidung = edchinoidung.getText().toString().trim();
                                        sotien = edchisotien.getText().toString().trim();
                                        maMa = Keys.MaDonhang();
                                        if (!noidung.equals("") && !sotien.equals("") && !sotien.equals("0")) {
                                            new SendRequest().execute();
                                        } else {
                                            Toasty.warning(Main_TienTraVe.this, "Phải nhập tất cả các trường", Toast.LENGTH_LONG, true).show();
                                        }
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
            }
        });
    }

    public ArrayList<String> GetDanhmuc(final VolleyCallback callback) {
        final ArrayList<String> danhmuc = new ArrayList<String>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Keys.MAIN_DANHMUC+"?loai="+Keys.TIENTRAVE, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        danhmuc.add("Chọn");
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                danhmuc.add(object.getString("ten"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.onSuccess(danhmuc);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onSuccess(danhmuc);
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        return danhmuc;
    }

    public interface VolleyCallback{
        void onSuccess(ArrayList<String> result);
    }

    private void selectValue(Spinner spinner, String value) {
        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).equals(value)) {
                spinner.setSelection(i);
                break;
            }
        }
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
                                .setTitle("Chi tiết phiếu tiền trả về");
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
                        final Spinner snchi = mView.findViewById(R.id.snchi);
                        ArrayAdapter mAdapter;
                        ArrayList<String> string = new ArrayList<>();
                        string.add(arraylist.get(position).getLoai());
                        mAdapter = new ArrayAdapter<>(Main_TienTraVe.this, android.R.layout.simple_spinner_item, string);
                        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        snchi.setAdapter(mAdapter);
                        selectValue(snchi, chinhanh);
                        snchi.setEnabled(false);
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
            dialog = new ProgressDialog(Main_TienTraVe.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải lên");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(Void... arg0) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main_TienTraVe.this).show();
            else {
                putData();
                addTientraveWeb(ngay, ca, chinhanh, session_ma, session_username, noidung, sotien);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog.dismiss();
            ResetActivity();
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
                            Toasty.error(Main_TienTraVe.this, "Thất bại, không kết nối được Server", Toast.LENGTH_LONG, true).show();
                        } else if (response.trim().equals("success")){
                            Toasty.success(Main_TienTraVe.this, "Tạo phiếu chi thành công", Toast.LENGTH_LONG, true).show();
                            ResetActivity();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main_TienTraVe.this, "Lỗi "+error, Toast.LENGTH_LONG, true).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_TIENTRAVE_WEB);
                params.put("maTV", "TV_"+ maMa);
                params.put("ngay", ngay);
                params.put("ca", ca);
                params.put("chinhanh", chinhanh);
                params.put("maNV", session_ma);
                params.put("tenNV", session_username);
                params.put("loai", loai);
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
        return result.toString();
    }

    public String putData(){
        try {
            URL url = new URL(Keys.getSCRIPT_TIENTRAVE(chinhanh));
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("maTV", "TV_"+ maMa);
            postDataParams.put("ngay", ngay);
            postDataParams.put("ca", ca);
            postDataParams.put("chinhanh", chinhanh);
            postDataParams.put("maNV", session_ma);
            postDataParams.put("tenNV", session_username);
            postDataParams.put("loai", loai);
            postDataParams.put("noidung", noidung);
            postDataParams.put("sotien", sotien);

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

    class GetData extends AsyncTask<String, Void, Void> {

        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new ProgressDialog(Main_TienTraVe.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
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
                                            object.getString("loai"),
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
                Toasty.info(Main_TienTraVe.this, "Không có phiếu trả về nào", Toast.LENGTH_LONG, true).show();
            }
            dialog.dismiss();
        }
    }
}
