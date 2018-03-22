package com.example.windows10gamer.connsql.Remove_Data;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

public class Main_Remove_Data extends AppCompatActivity {
    TextView tvRmOrder, tvRmkiemkho, tvRmBCDT, tvRmDatcoc, tvRmKhoanchi, tvRmCOD, tvRmTientrave, tvRmBaohanh;
    String chinhanh;
    ArrayList<String> position = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_remove_data);
        tvRmOrder = findViewById(R.id.tvRmOrder);
        tvRmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Remove_Data.this).show();
                else {
                    startActivity(new Intent(Main_Remove_Data.this, Main_Remove_Order.class));
                }
            }
        });
        tvRmBaohanh = findViewById(R.id.tvRmBaohanh);
        tvRmBaohanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Remove_Data.this).show();
                else {
                    startActivity(new Intent(Main_Remove_Data.this, Main_Remove_Baohanh.class));
                }
            }
        });
        tvRmKhoanchi = findViewById(R.id.tvRmKhoanchi);
        tvRmKhoanchi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Remove_Data.this).show();
                else {
                    startActivity(new Intent(Main_Remove_Data.this, Main_Remove_Khoanchi.class));
                }
            }
        });
        tvRmCOD = findViewById(R.id.tvRmCOD);
        tvRmCOD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Remove_Data.this).show();
                else {
                    startActivity(new Intent(Main_Remove_Data.this, Main_Remove_COD.class));
                }
            }
        });
        tvRmTientrave = findViewById(R.id.tvRmTientrave);
        tvRmTientrave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Remove_Data.this).show();
                else {
                    startActivity(new Intent(Main_Remove_Data.this, Main_Remove_Trave.class));
                }
            }
        });
        tvRmkiemkho = findViewById(R.id.tvRmKiemkho);
        tvRmkiemkho.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Remove_Data.this).show();
                else {
                    new Getvitri().execute();
                }
            }
        });
        tvRmBCDT = findViewById(R.id.tvRmBCDT);
        tvRmBCDT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Remove_Data.this).show();
                else {
                    startActivity(new Intent(Main_Remove_Data.this, Main_Remove_BCDT.class));
                }
            }
        });
        tvRmDatcoc = findViewById(R.id.tvRmDatcoc);
        tvRmDatcoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Remove_Data.this).show();
                else {
                    startActivity(new Intent(Main_Remove_Data.this, Main_Remove_Datcoc.class));
                }
            }
        });
    }

    class Getvitri extends AsyncTask<Void, Void, Void> {
        ProgressDialog dialog;
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jIndex = 0;
            dialog = new ProgressDialog(Main_Remove_Data.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_MENU_DSCH);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DANHSACHCUAHANG);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            position.clear();
                            for( ; jIndex < lenArray; jIndex++) {

                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    position.add(
                                            object.getString("cuahang")
                                    );
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
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            dialog.dismiss();
            setLisst(position);
        }
    }

    private void setLisst(ArrayList<String> position) {
        if(!Connect_Internet.checkConnection(getApplicationContext()))
            Connect_Internet.buildDialog(Main_Remove_Data.this).show();
        else {
            this.position = position;
            AlertDialog.Builder dialog = null;
            if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                dialog = new AlertDialog.Builder(Main_Remove_Data.this);
            } else {
                dialog = new AlertDialog.Builder(Main_Remove_Data.this, android.R.style.Theme_Holo_Light_Dialog_NoActionBar_MinWidth);
            }
            dialog.setIcon(R.drawable.ic_settings)
                    .setTitle("Chọn Kho cửa hàng cần Clear?");
            View mView = getLayoutInflater().inflate(R.layout.dialog_spinner, null);
            dialog.setCancelable(false);
            final Spinner spinner = mView.findViewById(R.id.spinnerKM);
            ArrayAdapter mAdapter = new ArrayAdapter<>(Main_Remove_Data.this, android.R.layout.simple_spinner_item, position);
            mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(null);
            spinner.setAdapter(mAdapter);
            dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    chinhanh = String.valueOf(spinner.getSelectedItem());
                    Delete(chinhanh);
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

    public void Delete(final String chinhanh){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_V2,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("success")){
                            Toasty.success(Main_Remove_Data.this, "Clear kho thành công", Toast.LENGTH_LONG, true).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main_Remove_Data.this, "Không kết nối được Server", Toast.LENGTH_LONG, true).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.DELE_KIEMKHO_WEB);
                params.put("chinhanh", chinhanh);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
