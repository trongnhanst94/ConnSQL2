package com.example.windows10gamer.connsql.Khach_Hang;

import android.app.AlertDialog;
import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
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
import com.example.windows10gamer.connsql.Adapter.Adapter_Datcoc;
import com.example.windows10gamer.connsql.Object.Datcoc;
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

public class Main_Dat_Coc extends AppCompatActivity {

    Button btnthemcoc;
    TextView tvchitoday, tvtatca, tvdatcoc, tvdahoancoc;
    ListView lvdatcoc;
    String session_username, session_ma, chinhanh, ngay, ca, tenKH, sdtKH, sotien;
    SharedPreferences shared;
    private ProgressDialog dialog2;
    Adapter_Datcoc adapter;
    ArrayList<Datcoc> tatca;
    ArrayList<Datcoc> datcoc = new ArrayList<>();
    ArrayList<Datcoc> dahoancoc = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_dat_coc);
        btnthemcoc = (Button) findViewById(R.id.btnthemcoc);
        lvdatcoc = (ListView) findViewById(R.id.lvdatcoc);
        tvchitoday = (TextView) findViewById(R.id.tvcoctoday);
        tvtatca = (TextView) findViewById(R.id.tvtatca);
        tvdatcoc = (TextView) findViewById(R.id.tvdatcoc);
        tvdahoancoc = (TextView) findViewById(R.id.tvdahoancoc);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        Intent intent = getIntent();
        session_username  = intent.getStringExtra("session_username");
        session_ma        = intent.getStringExtra("session_ma");
        ngay = Keys.getDateNow();
        ca = Keys.getCalam(chinhanh);
        tvchitoday.setText("Ngày: "+ca+" "+ngay);
        tatca = new ArrayList<Datcoc>();
        adapter = new Adapter_Datcoc(Main_Dat_Coc.this, tatca);
        lvdatcoc.setAdapter(adapter);
        new GetData().execute(chinhanh);
        tvtatca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new Adapter_Datcoc(Main_Dat_Coc.this, tatca);
                lvdatcoc.setAdapter(adapter);
            }
        });
        tvdatcoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new Adapter_Datcoc(Main_Dat_Coc.this, datcoc);
                lvdatcoc.setAdapter(adapter);
            }
        });
        tvdahoancoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter = new Adapter_Datcoc(Main_Dat_Coc.this, dahoancoc);
                lvdatcoc.setAdapter(adapter);
            }
        });
        lvdatcoc.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AlertDialog.Builder dialog = null;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    dialog = new AlertDialog.Builder(Main_Dat_Coc.this);
                } else {
                    dialog = new AlertDialog.Builder(Main_Dat_Coc.this);
                }
                dialog.setIcon(R.drawable.ic_addcoc)
                        .setTitle("Tạo đặt cọc");
                View mView = getLayoutInflater().inflate(R.layout.dialog_datcoc, null);
                dialog.setCancelable(false);
                final LinearLayout lnHidden = (LinearLayout) mView.findViewById(R.id.lnHidden);
                final TextView tvcocngay = (TextView) mView.findViewById(R.id.tvcocngay);
                final TextView tvcocca = (TextView) mView.findViewById(R.id.tvcocca);
                final TextView tvcocmanv = (TextView) mView.findViewById(R.id.tvcocmanv);
                final TextView tvcoctennv = (TextView) mView.findViewById(R.id.tvcoctennv);
                final TextView tvcocchinhanh = (TextView) mView.findViewById(R.id.tvcocchinhanh);
                final EditText edcoctenkhachhang = (EditText) mView.findViewById(R.id.edcoctenkhachhang);
                final EditText edcocsodienthoai = (EditText) mView.findViewById(R.id.edcocsodienthoai);
                final EditText edcocsotien = (EditText) mView.findViewById(R.id.edcocsotien);
                lnHidden.setVisibility(View.VISIBLE);
                tvcocngay.setText(tatca.get(position).getNgay());
                tvcocca.setText(tatca.get(position).getCa());
                tvcocchinhanh.setText(chinhanh);
                tvcocmanv.setText("Mã số: "+tatca.get(position).getMaNV());
                tvcoctennv.setText("Tên nhân viên: "+tatca.get(position).getTenNV());
                edcoctenkhachhang.setEnabled(false);
                edcocsodienthoai.setEnabled(false);
                edcocsotien.setEnabled(false);
                edcocsodienthoai.setText(tatca.get(position).getSodienthoai());
                edcoctenkhachhang.setText(tatca.get(position).getTenkhachhang());
                edcocsotien.setText(Keys.getFormatedAmount(Integer.valueOf(tatca.get(position).getSotien())));
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
                dialog.setCancelable(false);
                final TextView tvcocmanv = (TextView) mView.findViewById(R.id.tvcocmanv);
                final TextView tvcoctennv = (TextView) mView.findViewById(R.id.tvcoctennv);
                final TextView tvcocchinhanh = (TextView) mView.findViewById(R.id.tvcocchinhanh);
                final EditText edcoctenkhachhang = (EditText) mView.findViewById(R.id.edcoctenkhachhang);
                final EditText edcocsodienthoai = (EditText) mView.findViewById(R.id.edcocsodienthoai);
                final EditText edcocsotien = (EditText) mView.findViewById(R.id.edcocsotien);
                tvcocchinhanh.setText(chinhanh);
                tvcocmanv.setText("Mã số: "+session_ma);
                tvcoctennv.setText("Tên nhân viên: "+session_username);
                dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        sdtKH = edcocsodienthoai.getText().toString().trim();
                        tenKH = edcoctenkhachhang.getText().toString().trim();
                        sotien = edcocsotien.getText().toString().trim();
                        if (sdtKH.equals("") && tenKH.equals("") && sotien.equals("") && sotien.equals("0")){
                            new CustomToast().Show_Toast(Main_Dat_Coc.this, findViewById(android.R.id.content), "Phải nhập tất cả các trường!!");
                        } else {
                            new SendRequest().execute();
                            new GetData().execute(chinhanh);
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

    public class SendRequest extends AsyncTask<Void, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
        }

        protected String doInBackground(Void... arg0) {
            tatca.clear();
            dahoancoc.clear();
            datcoc.clear();
            addCocWeb(ngay, ca, chinhanh, session_ma, session_username, tenKH, sdtKH, sotien);
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
                            new CustomToast().Show_Toast(Main_Dat_Coc.this, findViewById(android.R.id.content), "Thất bại, không kết nối được Server!!");
                        } else if (response.trim().equals("success")){
                            new CustomToast().Show_Toast(Main_Dat_Coc.this, findViewById(android.R.id.content), "Tạo đặt cọc thành công!!");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Dat_Coc.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_DATCOC_WEB);
                params.put("maDC", "COC_"+ Keys.MaDonhang());
                params.put("ngay", ngay);
                params.put("ca", ca);
                params.put("chinhanh", chinhanh);
                params.put("maNV", session_ma);
                params.put("tenNV", session_username);
                params.put("sotien", sotien);
                params.put("tenkhachhang", tenKH);
                params.put("sodienthoai", sdtKH);
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
            postDataParams.put("maKC", "CHI_"+ Keys.MaDonhang());
            postDataParams.put("ngay", ngay);
            postDataParams.put("ca", ca);
            postDataParams.put("chinhanh", chinhanh);
            postDataParams.put("maNV", session_ma);
            postDataParams.put("tenNV", session_username);
            postDataParams.put("sotien", sotien);
            postDataParams.put("tenkhachhang", tenKH);
            postDataParams.put("sodienthoai", sdtKH);

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
                                            object.getString("trangthai")
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
                adapter.notifyDataSetChanged();
                for (int i = 0; i < tatca.size(); i++){
                    if (tatca.get(i).getTrangthai().equals("0")){
                        datcoc.add(tatca.get(i));
                    } else {
                        dahoancoc.add(tatca.get(i));
                    }
                }
            } else {
                new CustomToast().Show_Toast(Main_Dat_Coc.this, findViewById(android.R.id.content), "Không có Đặt cọc nào!!");
            }
            dialog2.dismiss();
        }
    }
}
