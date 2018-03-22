package com.example.windows10gamer.connsql.Khach_Hang;

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
import com.example.windows10gamer.connsql.Adapter.Adapter_Datcoc;
import com.example.windows10gamer.connsql.Object.Datcoc;
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

public class Main_Dat_Coc extends AppCompatActivity {

    Button btnthemcoc;
    TextView tvchitoday, tvtatca, tvdatcoc, tvdahoancoc;
    ListView lvdatcoc;
    String session_username, session_ma, chinhanh, ngay, ca, tenKH, sdtKH, sotien;
    SharedPreferences shared;
    private ProgressDialog dialog2;
    Adapter_Datcoc adapter;
    ArrayList<Datcoc> tatca;
    ArrayList<Datcoc> tatca_plus = new ArrayList<>();
    private String ghichu = "";
    String maMa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dat_coc);
        btnthemcoc = findViewById(R.id.btnthemcoc);
        lvdatcoc = findViewById(R.id.lvdatcoc);
        tvchitoday = findViewById(R.id.tvcoctoday);
        tvtatca = findViewById(R.id.tvtatca);
        tvdatcoc = findViewById(R.id.tvdatcoc);
        tvdahoancoc = findViewById(R.id.tvdahoancoc);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        shared = getSharedPreferences("login", MODE_PRIVATE);
        session_username = shared.getString("shortName", "");
        session_ma = shared.getString("ma", "");
        ngay = Keys.getDateNow();
        ca = Keys.getCalam(chinhanh);
        tvchitoday.setText("Ngày: "+ca+" "+ngay);
        tatca = new ArrayList<Datcoc>();
        adapter = new Adapter_Datcoc(Main_Dat_Coc.this, tatca_plus);
        lvdatcoc.setAdapter(adapter);
        new GetData().execute(chinhanh);
        tvtatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tatca_plus.clear();
                for (int i = 0; i < tatca.size(); i++){
                        tatca_plus.add(tatca.get(i));
                }
                adapter = new Adapter_Datcoc(Main_Dat_Coc.this, tatca_plus);
                lvdatcoc.setAdapter(adapter);
            }
        });
        tvdatcoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tatca_plus.clear();
                for (int i = 0; i < tatca.size(); i++){
                    if (tatca.get(i).getTrangthai().equals("0")){
                        tatca_plus.add(tatca.get(i));
                    }
                }
                if(tatca_plus.size() == 0) {
                    Toasty.info(Main_Dat_Coc.this, "Không có đơn Đặt cọc nào", Toast.LENGTH_LONG, true).show();
                }
                adapter = new Adapter_Datcoc(Main_Dat_Coc.this, tatca_plus);
                lvdatcoc.setAdapter(adapter);
            }
        });
        tvdahoancoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tatca_plus.clear();
                for (int i = 0; i < tatca.size(); i++){
                    if (tatca.get(i).getTrangthai().equals("1")){
                        tatca_plus.add(tatca.get(i));
                    }
                }
                if(tatca_plus.size() == 0) {
                    Toasty.info(Main_Dat_Coc.this, "Không có đơn Hoàn cọc nào", Toast.LENGTH_LONG, true).show();
                }
                adapter = new Adapter_Datcoc(Main_Dat_Coc.this, tatca_plus);
                lvdatcoc.setAdapter(adapter);
            }
        });
        lvdatcoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                GetUser(position, Main_Dat_Coc.this,tatca_plus.get(position).getMaNV());
            }
        });
        btnthemcoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = null;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    dialog = new AlertDialog.Builder(Main_Dat_Coc.this);
                } else {
                    dialog = new AlertDialog.Builder(Main_Dat_Coc.this);
                }
                dialog.setIcon(R.drawable.ic_addcoc)
                        .setTitle("Tạo đặt cọc");
                View mView = getLayoutInflater().inflate(R.layout.dialog_datcoc, null);
                final ImageView ivAvatar = mView.findViewById(R.id.ivAvatar);
                final TextView tvcocmanv = mView.findViewById(R.id.tvcocmanv);
                final TextView tvcoctennv = mView.findViewById(R.id.tvcoctennv);
                final TextView tvcocchinhanh = mView.findViewById(R.id.tvcocchinhanh);
                final EditText edcoctenkhachhang = mView.findViewById(R.id.edcoctenkhachhang);
                final EditText edcocsodienthoai = mView.findViewById(R.id.edcocsodienthoai);
                final EditText edcocsotien = mView.findViewById(R.id.edcocsotien);
                shared = getSharedPreferences("login", MODE_PRIVATE);
                Glide.with(Main_Dat_Coc.this).load(shared.getString("img", "")).override(300,300).fitCenter().into(ivAvatar);
                final EditText edcocghichu = mView.findViewById(R.id.edcocghichu);
                tvcocchinhanh.setText(chinhanh);
                tvcocmanv.setText("Mã số: "+session_ma);
                tvcoctennv.setText("Tên nhân viên: "+session_username);
                dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sdtKH = edcocsodienthoai.getText().toString().trim();
                        tenKH = edcoctenkhachhang.getText().toString().trim();
                        sotien = edcocsotien.getText().toString().trim();
                        ghichu = edcocghichu.getText().toString().trim();
                        maMa = Keys.MaDonhang();
                        if (sdtKH.equals("") && tenKH.equals("") && sotien.equals("") && sotien.equals("0")){
                            Toasty.warning(Main_Dat_Coc.this, "Phải nhập tất cả các trường", Toast.LENGTH_LONG, true).show();
                        } else {
                            new SendRequest().execute();
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
                            dialog = new AlertDialog.Builder(Main_Dat_Coc.this);
                        } else {
                            dialog = new AlertDialog.Builder(Main_Dat_Coc.this);
                        }
                        dialog.setIcon(R.drawable.ic_addcoc)
                                .setTitle("Thông tin đặt cọc");
                        View mView = getLayoutInflater().inflate(R.layout.dialog_datcoc, null);
                        final ImageView ivAvatar = mView.findViewById(R.id.ivAvatar);
                        final LinearLayout lnHidden3 = mView.findViewById(R.id.lnHidden3);
                        final LinearLayout lnHidden2 = mView.findViewById(R.id.lnHidden2);
                        final LinearLayout lnHidden = mView.findViewById(R.id.lnHidden);
                        final TextView tvcocngay = mView.findViewById(R.id.tvcocngay);
                        final TextView tvcocca = mView.findViewById(R.id.tvcocca);
                        final TextView tvcocmanv = mView.findViewById(R.id.tvcocmanv);
                        final TextView tvcoctennv = mView.findViewById(R.id.tvcoctennv);
                        final TextView tvcocchinhanh = mView.findViewById(R.id.tvcocchinhanh);
                        final EditText edcocghichu = mView.findViewById(R.id.edcocghichu);
                        final EditText edcoctenkhachhang = mView.findViewById(R.id.edcoctenkhachhang);
                        final EditText edcocsodienthoai = mView.findViewById(R.id.edcocsodienthoai);
                        final EditText edcocsotien = mView.findViewById(R.id.edcocsotien);
                        final Button btnhoantien = mView.findViewById(R.id.btnhoantien);
                        if (tatca_plus.get(position).getTrangthai().equals("0")){
                            lnHidden2.setVisibility(View.VISIBLE);
                        } else {
                            lnHidden3.setVisibility(View.VISIBLE);
                            final TextView tvcoccatraa = mView.findViewById(R.id.tvcoccatraa);
                            final TextView tvcocngaytra = mView.findViewById(R.id.tvcocngaytra);
                            final TextView tvcocmanvtra = mView.findViewById(R.id.tvcocmanvtra);
                            final TextView tvcoctennvtra = mView.findViewById(R.id.tvcoctennvtra);
                            tvcoccatraa.setText(tatca_plus.get(position).getCatra());
                            tvcocngaytra.setText(tatca_plus.get(position).getNgaytra());
                            tvcocmanvtra.setText("Mã số: "+tatca_plus.get(position).getMaNVtra());
                            tvcoctennvtra.setText("Tên nhân viên: "+tatca_plus.get(position).getTenNVtra());
                        }
                        lnHidden.setVisibility(View.VISIBLE);
                        tvcocngay.setText(tatca_plus.get(position).getNgay());
                        tvcocca.setText(tatca_plus.get(position).getCa());
                        tvcocchinhanh.setText(chinhanh);
                        tvcocmanv.setText("Mã số: "+tatca_plus.get(position).getMaNV());
                        tvcoctennv.setText("Tên nhân viên: "+tatca_plus.get(position).getTenNV());
                        edcoctenkhachhang.setEnabled(false);
                        edcocsodienthoai.setEnabled(false);
                        edcocsotien.setEnabled(false);
                        edcocghichu.setEnabled(false);
                        edcocghichu.setText(tatca_plus.get(position).getGhichu());
                        edcocsodienthoai.setText(tatca_plus.get(position).getSodienthoai());
                        edcoctenkhachhang.setText(tatca_plus.get(position).getTenkhachhang());
                        edcocsotien.setText(Keys.setMoney(Integer.valueOf(tatca_plus.get(position).getSotien())));
                        btnhoantien.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!Connect_Internet.checkConnection(getApplicationContext()))
                                    Connect_Internet.buildDialog(Main_Dat_Coc.this).show();
                                else {
                                    UpdateCocWeb(position, session_ma, session_username);
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

    public void ResetActivity(){
        Intent intentput = new Intent(Main_Dat_Coc.this, Main_Dat_Coc.class);
        intentput.putExtra("session_username", session_username);
        intentput.putExtra("session_ma", session_ma);
        startActivity(intentput);
        finish();
    }

    public class SendRequest extends AsyncTask<Void, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(Void... arg0) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main_Dat_Coc.this).show();
            else {
                addCocWeb(ngay, ca, chinhanh, session_ma, session_username, tenKH, sdtKH, sotien);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }


    public void addCocWeb(final String ngay, final String ca, final String chinhanh, final String session_ma, final String session_username, final String tenKH, final String sdtKH, final String sotien){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toasty.error(Main_Dat_Coc.this, "Thất bại, không kết nối được Server", Toast.LENGTH_LONG, true).show();
                        } else if (response.trim().equals("success")){
                            Toasty.success(Main_Dat_Coc.this, "Tạo đặt cọc thành công", Toast.LENGTH_LONG, true).show();
                            ResetActivity();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main_Dat_Coc.this, "Lỗi "+error, Toast.LENGTH_LONG, true).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_DATCOC_WEB);
                params.put("maDC", "COC_"+ maMa);
                params.put("ngay", ngay);
                params.put("ca", ca);
                params.put("chinhanh", chinhanh);
                params.put("maNV", session_ma);
                params.put("tenNV", session_username);
                params.put("sotien", sotien);
                params.put("tenkhachhang", tenKH);
                params.put("sodienthoai", sdtKH);
                params.put("ghichu", ghichu);
                Log.e("params", params.toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void UpdateCocWeb(final int position, final String session_ma, final String session_username){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toasty.error(Main_Dat_Coc.this, "Thất bại, không kết nối được Server", Toast.LENGTH_LONG, true).show();
                        } else if (response.trim().equals("success")){
                            Toasty.success(Main_Dat_Coc.this, "Hoàn cọc thành công", Toast.LENGTH_LONG, true).show();
                            ResetActivity();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main_Dat_Coc.this, "Lỗi "+error, Toast.LENGTH_LONG, true).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.UPDATE_DATCOC_WEB);
                params.put("maDC", tatca_plus.get(position).getMaDC());
                params.put("ngaytra", Keys.getDateNow());
                params.put("catra", Keys.getCalam(chinhanh));
                params.put("maNVtra", session_ma);
                params.put("tenNVtra", session_username);
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
            dialog2 = new ProgressDialog(Main_Dat_Coc.this);
            dialog2.setTitle("Hãy chờ...");
            dialog2.setMessage("Dữ liệu đang được tải xuống");
            dialog2.setCancelable(false);
            dialog2.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_DATCOC+"?chinhanh="+params[0]);
            try {
                if (jsonObject != null) {
                    tatca.clear();
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DATCOC);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    tatca.add(new Datcoc(
                                            object.getString("maDC"),
                                            object.getString("ngay"),
                                            object.getString("ca"),
                                            object.getString("chinhanh"),
                                            object.getString("maNV"),
                                            object.getString("tenNV"),
                                            object.getString("sotien"),
                                            object.getString("tenkhachhang"),
                                            object.getString("sodienthoai"),
                                            object.getString("ghichu"),
                                            object.getString("trangthai"),
                                            object.getString("ngaytra"),
                                            object.getString("catra"),
                                            object.getString("maNVtra"),
                                            object.getString("tenNVtra")
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
            if(tatca.size() > 0) {
                tatca_plus.clear();
                for (int i = 0; i < tatca.size(); i++){
                    tatca_plus.add(tatca.get(i));
                }
                adapter.notifyDataSetChanged();
            } else {
                Toasty.info(Main_Dat_Coc.this, "Không có đặt cọc nào", Toast.LENGTH_LONG, true).show();
            }
            dialog2.dismiss();
        }
    }
}
