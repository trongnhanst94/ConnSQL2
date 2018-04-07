package com.example.windows10gamer.connsql.Kho;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.example.windows10gamer.connsql.Adapter.Adapter_MinStock;
import com.example.windows10gamer.connsql.Object.Minstock;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import es.dmoral.toasty.Toasty;

import static com.example.windows10gamer.connsql.Main.chinhanh;

public class Main_Minstock extends AppCompatActivity {

    TextView tvtoday;
    Button btnthem;
    ListView listview;
    String id, ma, ten, baohanh, nguon, von, gia, minstock;
    SharedPreferences shared;
    private ProgressDialog dialog2;
    Adapter_MinStock adapter;
    ArrayList<Minstock> arraylist;
    private EditText searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_minstock);
        btnthem = findViewById(R.id.btnthem);
        listview = findViewById(R.id.listview);
        tvtoday = findViewById(R.id.tvtoday);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        String chinhanh = shared.getString("chinhanh", "");
        String ngay = Keys.getDateNow();
        String ca = Keys.getCalam(chinhanh);
        tvtoday.setText("Ngày: "+ca+" "+ngay);
        arraylist = new ArrayList<Minstock>();
        adapter = new Adapter_MinStock(Main_Minstock.this, R.layout.adapter_minstock, arraylist);
        listview.setAdapter(adapter);
        new GetData().execute(chinhanh);
        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Minstock.this).show();
                else {
                    AlertDialog.Builder dialog = null;
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                        dialog = new AlertDialog.Builder(Main_Minstock.this);
                    } else {
                        dialog = new AlertDialog.Builder(Main_Minstock.this);
                    }
                    dialog.setIcon(R.drawable.ic_addchi)
                            .setTitle("Tạo Min Stock");
                    View mView = getLayoutInflater().inflate(R.layout.dialog_minstock, null);
                    dialog.setCancelable(false);
                    final EditText edma = mView.findViewById(R.id.ma);
                    final EditText edten = mView.findViewById(R.id.ten);
                    final EditText edbaohanh = mView.findViewById(R.id.baohanh);
                    final EditText ednguon = mView.findViewById(R.id.nguon);
                    final EditText edvon = mView.findViewById(R.id.von);
                    final EditText edgia = mView.findViewById(R.id.gia);
                    final EditText edminstock = mView.findViewById(R.id.minsotck);
                    dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ma = edma.getText().toString().trim();
                            ten = edten.getText().toString().trim();
                            baohanh = edbaohanh.getText().toString().trim();
                            nguon = ednguon.getText().toString().trim();
                            von = edvon.getText().toString().trim();
                            gia = edgia.getText().toString().trim();
                            minstock = edminstock.getText().toString().trim();
                            id = Keys.MaDonhang()+ma;
                            new SendRequest().execute();
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

    public void ClickItem(final String Cid) {
        Minstock minstocks = null;
        for (int i = 0; i < arraylist.size(); i++) {
            if (arraylist.get(i).getId().equals(Cid)){
                minstocks = arraylist.get(i);
                break;
            }
        }
        AlertDialog.Builder dialog = null;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            dialog = new AlertDialog.Builder(Main_Minstock.this);
        } else {
            dialog = new AlertDialog.Builder(Main_Minstock.this);
        }
        dialog.setIcon(R.drawable.ic_addchi)
                .setTitle("Chi tiết Minstock");
        View mView = getLayoutInflater().inflate(R.layout.dialog_minstock, null);
        dialog.setCancelable(false);
        final EditText edma = mView.findViewById(R.id.ma);
        final EditText edten = mView.findViewById(R.id.ten);
        final EditText edbaohanh = mView.findViewById(R.id.baohanh);
        final EditText ednguon = mView.findViewById(R.id.nguon);
        final EditText edvon = mView.findViewById(R.id.von);
        final EditText edgia = mView.findViewById(R.id.gia);
        final EditText edminstock = mView.findViewById(R.id.minsotck);
        edma.setText(minstocks.getMa());
        edten.setText(minstocks.getTen());
        edbaohanh.setText(minstocks.getBaohanh());
        ednguon.setText(minstocks.getNguon());
        edvon.setText(minstocks.getVon());
        edgia.setText(minstocks.getGia());
        edminstock.setText(minstocks.getMinstock());
        dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setPositiveButton("Cập nhật", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                updateWeb(
                        Cid,
                        edma.getText().toString().trim(),
                        edten.getText().toString().trim(),
                        edbaohanh.getText().toString().trim(),
                        ednguon.getText().toString().trim(),
                        edvon.getText().toString().trim(),
                        edgia.getText().toString().trim(),
                        edminstock.getText().toString().trim()
                        );
            }
        });
        dialog.setView(mView);
        AlertDialog al = dialog.create();
        al.show();
    }

    public void Delete(final String id) {
        if(!Connect_Internet.checkConnection(getApplicationContext()))
            Connect_Internet.buildDialog(Main_Minstock.this).show();
        else {
            AlertDialog.Builder builder = null;
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                builder = new AlertDialog.Builder(Main_Minstock.this);
            } else {
                builder = new AlertDialog.Builder(Main_Minstock.this, android.R.style.Theme_Holo_Light_Panel);
            }
            builder.setIcon(R.drawable.ic_settings);
            builder.setMessage("Bạn chắc chắn muốn xóa??");
            builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    deleteWeb(id);
                }
            });
            builder.show();
        }
    }

    public void deleteWeb(final String id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toasty.error(Main_Minstock.this, "Thất bại, không kết nối được Server", Toast.LENGTH_LONG, true).show();
                        } else if (response.trim().equals("success")){
                            new GetData().execute();
                            Toasty.success(Main_Minstock.this, "Xóa Minstock thành công", Toast.LENGTH_LONG, true).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main_Minstock.this, "Lỗi "+error, Toast.LENGTH_LONG, true).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.DELE_MINSTOCK_WEB);
                params.put("id", id);
                Log.e("params", params.toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public interface VolleyCallback{
        void onSuccess(ArrayList<String> result);
    }

    public class SendRequest extends AsyncTask<Void, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
            dialog2 = new ProgressDialog(Main_Minstock.this);
            dialog2.setTitle("Hãy chờ...");
            dialog2.setMessage("Dữ liệu đang được tải lên");
            dialog2.setCancelable(false);
            dialog2.show();
        }

        protected String doInBackground(Void... arg0) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main_Minstock.this).show();
            else {
                addWeb(id, ma, ten, baohanh, nguon, von, gia, minstock);
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

    public void addWeb(final String id, final String ma, final String ten, final String baohanh, final String nguon, final String von, final String gia, final String minstock){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toasty.error(Main_Minstock.this, "Thất bại, không kết nối được Server", Toast.LENGTH_LONG, true).show();
                        } else if (response.trim().equals("success")){
                            Toasty.success(Main_Minstock.this, "Tạo Minstock thành công", Toast.LENGTH_LONG, true).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main_Minstock.this, "Lỗi "+error, Toast.LENGTH_LONG, true).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_MINSTOCK_WEB);
                params.put("id", ma+Keys.MaDonhang());
                params.put("chinhanh", chinhanh);
                params.put("ma", ma);
                params.put("ten", ten);
                params.put("baohanh", baohanh);
                params.put("nguon", nguon);
                params.put("von", Keys.mahoagiavon(von));
                params.put("gia", gia);
                params.put("minstock", minstock);
                Log.e("params", params.toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void updateWeb(final String Uid, final String Uma, final String Uten, final String Ubaohanh, final String Unguon, final String Uvon, final String Ugia, final String Uminstock){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toasty.error(Main_Minstock.this, "Thất bại, không kết nối được Server", Toast.LENGTH_LONG, true).show();
                        } else if (response.trim().equals("success")){
                            new GetData().execute();
                            Toasty.success(Main_Minstock.this, "Cập nhật Minstock thành công", Toast.LENGTH_LONG, true).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main_Minstock.this, "Lỗi "+error, Toast.LENGTH_LONG, true).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.UPDATE_MINSTOCK_WEB);
                params.put("id", Uid);
                params.put("ma", Uma);
                params.put("ten", Uten);
                params.put("baohanh", Ubaohanh);
                params.put("nguon", Unguon);
                params.put("von", Keys.mahoagiavon(Uvon));
                params.put("gia", Ugia);
                params.put("minstock", Uminstock);
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
            dialog2 = new ProgressDialog(Main_Minstock.this);
            dialog2.setTitle("Hãy chờ...");
            dialog2.setMessage("Dữ liệu đang được tải xuống");
            dialog2.setCancelable(false);
            dialog2.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_MINSTOCK);
            try {
                if (jsonObject != null) {
                    arraylist.clear();
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.MINSTOCK);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    arraylist.add(new Minstock(
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
                Toasty.info(Main_Minstock.this, "Không có sản phẩm nào", Toast.LENGTH_LONG, true).show();
            }
            dialog2.dismiss();
        }
    }
}