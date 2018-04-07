package com.example.windows10gamer.connsql.Settings;

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
import com.example.windows10gamer.connsql.Adapter.Adapter_Danhmuc;
import com.example.windows10gamer.connsql.Object.Danhmuc;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class Main_Danhmuc extends AppCompatActivity {

    TextView tvdanhmuctoday;
    Button btnthemdanhmuc;
    ListView lvdanhmuc;
    String session_username, session_ma, chinhanh, ngay, ca;
    SharedPreferences shared;
    private ProgressDialog dialog2;
    Adapter_Danhmuc adapter;
    ArrayList<Danhmuc> arraylist;
    private String maMa;
    String loai;
    private String ten;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_danhmuc);
        btnthemdanhmuc = findViewById(R.id.btnthemdanhmuc);
        lvdanhmuc = findViewById(R.id.lvdanhmuc);
        tvdanhmuctoday = findViewById(R.id.tvdanhmuctoday);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        shared = getSharedPreferences("login", MODE_PRIVATE);
        session_username = shared.getString("shortName", "");
        session_ma = shared.getString("ma", "");
        ngay = Keys.getDateNow();
        ca = Keys.getCalam(chinhanh);
        tvdanhmuctoday.setText("Ngày: "+ca+" "+ngay);
        arraylist = new ArrayList<Danhmuc>();
        adapter = new Adapter_Danhmuc(Main_Danhmuc.this, arraylist);
        lvdanhmuc.setAdapter(adapter);
        new GetData().execute(chinhanh);
        lvdanhmuc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetUser(position, Main_Danhmuc.this, arraylist.get(position).getManhanvien());
            }
        });
        btnthemdanhmuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Danhmuc.this).show();
                else {AlertDialog.Builder dialog = null;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        dialog = new AlertDialog.Builder(Main_Danhmuc.this);
                    } else {
                        dialog = new AlertDialog.Builder(Main_Danhmuc.this);
                    }
                    dialog.setIcon(R.drawable.ic_addchi).setTitle("Tạo danh mục");
                    View mView = getLayoutInflater().inflate(R.layout.dialog_danhmuc, null);
                    dialog.setCancelable(false);
                    final ImageView ivAvatar = mView.findViewById(R.id.ivAvatar);
                    final TextView tvdmngay = mView.findViewById(R.id.tvdmngay);
                    final TextView tvdmca = mView.findViewById(R.id.tvdmca);
                    final TextView tvdmmanv = mView.findViewById(R.id.tvdmmanv);
                    final TextView tvdmtennv = mView.findViewById(R.id.tvdmtennv);
                    final TextView tvdmchinhanh = mView.findViewById(R.id.tvdmchinhanh);
                    final EditText eddmten = mView.findViewById(R.id.eddmten);
                    final Spinner snloai = mView.findViewById(R.id.snloai);
                    ArrayAdapter mAdapter;
                    mAdapter = new ArrayAdapter<String>(Main_Danhmuc.this, android.R.layout.simple_spinner_item, Keys.DANHMUCLIST);
                    mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    snloai.setAdapter(mAdapter);
                    selectValue(snloai, chinhanh);
                    snloai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            loai = snloai.getSelectedItem().toString();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                    shared = getSharedPreferences("login", MODE_PRIVATE);
                    Glide.with(Main_Danhmuc.this).load(shared.getString("img", "")).override(300, 300).fitCenter().into(ivAvatar);
                    tvdmchinhanh.setText(chinhanh);
                    tvdmmanv.setText("Mã số: " + session_ma);
                    tvdmtennv.setText("Tên nhân viên: " + session_username);
                    dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (loai.equals("Chọn")){
                                Toasty.warning(Main_Danhmuc.this, "Bạn chưa chọn Loại chi", Toast.LENGTH_LONG, true).show();
                                dialog.dismiss();
                            } else {
                                maMa = Keys.MaDonhang();
                                ten = eddmten.getText().toString().trim();
                                if (!ten.equals("")) {
                                    new SendRequest().execute();
                                } else {
                                    Toasty.warning(Main_Danhmuc.this, "Phải nhập tất cả các trường", Toast.LENGTH_LONG, true).show();
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
            }
        });
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
                            dialog = new AlertDialog.Builder(Main_Danhmuc.this);
                        } else {
                            dialog = new AlertDialog.Builder(Main_Danhmuc.this);
                        }
                        dialog.setIcon(R.drawable.ic_addchi)
                                .setTitle("Chi tiết danh mục");
                        View mView = getLayoutInflater().inflate(R.layout.dialog_danhmuc, null);
                        dialog.setCancelable(false);
                        final ImageView ivAvatar = mView.findViewById(R.id.ivAvatar);
                        final LinearLayout lnHidden = mView.findViewById(R.id.lnHidden);
                        final TextView tvdmngay = mView.findViewById(R.id.tvdmngay);
                        final TextView tvdmca = mView.findViewById(R.id.tvdmca);
                        final TextView tvdmmanv = mView.findViewById(R.id.tvdmmanv);
                        final TextView tvdmtennv = mView.findViewById(R.id.tvdmtennv);
                        final TextView tvdmchinhanh = mView.findViewById(R.id.tvdmchinhanh);
                        final EditText eddmten = mView.findViewById(R.id.eddmten);
                        final Spinner snloai = mView.findViewById(R.id.snloai);
                        ArrayAdapter mAdapter;
                        ArrayList<String> string = new ArrayList<>();
                        string.add(arraylist.get(position).getLoai());
                        mAdapter = new ArrayAdapter<>(Main_Danhmuc.this, android.R.layout.simple_spinner_item, string);
                        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        snloai.setAdapter(mAdapter);
                        selectValue(snloai, chinhanh);
                        snloai.setEnabled(false);
                        lnHidden.setVisibility(View.GONE);

                        tvdmmanv.setText("Mã số: "+arraylist.get(position).getManhanvien());
                        tvdmtennv.setText("Tên nhân viên: "+arraylist.get(position).getTennhanvien());
                        eddmten.setEnabled(false);
                        eddmten.setText(arraylist.get(position).getTen());
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
            dialog2 = new ProgressDialog(Main_Danhmuc.this);
            dialog2.setTitle("Hãy chờ...");
            dialog2.setMessage("Dữ liệu đang được tải lên");
            dialog2.setCancelable(false);
            dialog2.show();
        }

        protected String doInBackground(Void... arg0) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main_Danhmuc.this).show();
            else {
                addDanhmuc(ten, loai, session_ma, session_username);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            dialog2.dismiss();
            new GetData().execute(chinhanh);
        }
    }

    public void ResetActivity(){
        Intent intentput = new Intent(Main_Danhmuc.this, Main_Danhmuc.class);
        intentput.putExtra("session_username", session_username);
        intentput.putExtra("session_ma", session_ma);
        startActivity(intentput);
        finish();
    }

    public void addDanhmuc(final String ten, final String loai, final String session_ma, final String session_username){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toasty.error(Main_Danhmuc.this, "Thất bại, không kết nối được Server", Toast.LENGTH_LONG, true).show();
                        } else if (response.trim().equals("success")){
                            Toasty.success(Main_Danhmuc.this, "Tạo danh mục thành công", Toast.LENGTH_LONG, true).show();
                            ResetActivity();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main_Danhmuc.this, "Lỗi "+error, Toast.LENGTH_LONG, true).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_DANHMUC_WEB);
                params.put("id", "DM_"+ maMa);
                params.put("ten", ten);
                params.put("loai", loai);
                params.put("manhanvien", session_ma);
                params.put("tennhanvien", session_username);
                Log.e("params", params.toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    class GetData extends AsyncTask<String, Void, Void> {

        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog2 = new ProgressDialog(Main_Danhmuc.this);
            dialog2.setTitle("Hãy chờ...");
            dialog2.setMessage("Dữ liệu đang được tải xuống");
            dialog2.setCancelable(false);
            dialog2.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_DANHMUC_V2);
            try {
                if (jsonObject != null) {
                    arraylist.clear();
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DANHMUC);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    arraylist.add(new Danhmuc(
                                            object.getString("id"),
                                            object.getString("ten"),
                                            object.getString("loai"),
                                            object.getString("manhanvien"),
                                            object.getString("tennhanvien")
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
                Toasty.info(Main_Danhmuc.this, "Không có danh mục nào", Toast.LENGTH_LONG, true).show();
            }
            dialog2.dismiss();
        }
    }
}
