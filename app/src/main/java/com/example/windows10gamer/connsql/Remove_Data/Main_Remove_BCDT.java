package com.example.windows10gamer.connsql.Remove_Data;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Adapter.Adapter_Remove_BCDT;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_Baohanh;
import com.example.windows10gamer.connsql.Object.Doanhthu;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Main_Remove_BCDT  extends AppCompatActivity {
    EditText edRMngay;
    private SharedPreferences shared;
    private String chinhanh;
    ArrayList<Doanhthu> contactList = new ArrayList<>();
    ArrayList<Doanhthu> temp = new ArrayList<>();
    Adapter_Remove_BCDT adapter;
    ListView listView;
    String ngay = "";
    Button btnRmOrder;
    private ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_remove_bcdt);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        edRMngay = findViewById(R.id.edRmngay);
        listView = findViewById(R.id.lvRmOrder);
        btnRmOrder = findViewById(R.id.btnRmOrder);
        adapter = new Adapter_Remove_BCDT(Main_Remove_BCDT.this, contactList);
        listView.setAdapter(adapter);
        edRMngay.setInputType(InputType.TYPE_NULL);
        edRMngay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = java.util.Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                if (Build.VERSION.SDK_INT == 24) {
                    final Context contextThemeWrapper =
                            new ContextThemeWrapper(Main_Remove_BCDT.this, android.R.style.Theme_Holo_Light_Dialog);
                    try {
                        DatePickerDialog datePickerDialog = new Keys.FixedHoloDatePickerDialog(contextThemeWrapper, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year, month, dayOfMonth);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                edRMngay.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    } catch ( Fragment.InstantiationException e) {
                        e.printStackTrace();
                    }
                } else {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Remove_BCDT.this, android.R.style.Theme_Holo_Light_Panel, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year, month, dayOfMonth);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            ngay = simpleDateFormat.format(calendar.getTime());
                            edRMngay.setText("Ngày bán: " + simpleDateFormat.format(calendar.getTime()));
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });
        btnRmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ngay.equals("")){
                    new CustomToast().Show_Toast(Main_Remove_BCDT.this,findViewById(android.R.id.content), "Chưa chọn ngày!");
                } else {
                    new SendRequest().execute();
                }
            }
        });
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
    }

    public class SendRequest extends AsyncTask<Void, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(Main_Remove_BCDT.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(Void... arg0) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main_Remove_BCDT.this).show();
            else {
                new Main_Remove_BCDT.GETDOANHTHU().execute(chinhanh);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    class GETDOANHTHU extends AsyncTask<String, Void, Void> {

        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_DOANHTHU+"?dateBegin="+ngay+"&dateEnd="+ngay+"&dateCasang="+Keys.CA_SANG+"&dateCachieu="+Keys.CA_CHIEU);
            try {
                if (jsonObject != null) {
                    contactList.clear();
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DOANHTHU);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    contactList.add(new Doanhthu(
                                            object.getString("maDT"),
                                            object.getString("ngay"),
                                            object.getString("ca"),
                                            object.getString("chinhanh"),
                                            object.getString("maNV"),
                                            object.getString("tenNV"),
                                            object.getString("tiendauca"),
                                            object.getString("tientrave"),
                                            object.getString("doanhthu"),
                                            object.getString("lechcuoica"),
                                            object.getString("tienthucte")
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
            if (contactList.size() > 0){
                sortList(contactList);
                adapter.notifyDataSetChanged();
            } else new CustomToast().Show_Toast(Main_Remove_BCDT.this, findViewById(android.R.id.content), "Không có báo cáo!");
            dialog.dismiss();
        }
    }

    private void sortList(ArrayList<Doanhthu> list) {
        Collections.sort(list, new Comparator<Doanhthu>() {
            @Override
            public int compare(Doanhthu lhs, Doanhthu rhs) {
                return rhs.getMaDT().compareTo(lhs.getMaDT());
            }
        });
    }

    public void Delete(final String id){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            new CustomToast().Show_Toast(Main_Remove_BCDT.this, findViewById(android.R.id.content), "Xóa thành công!");
                            new SendRequest().execute();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Remove_BCDT.this, findViewById(android.R.id.content), "Không kết nối được Server!");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.DELE_DOANHTHU_WEB);
                params.put("maDT", id);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
