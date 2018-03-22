package com.example.windows10gamer.connsql.Xuat_Nhap;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.example.windows10gamer.connsql.Adapter.Adapter_Spinner_NV;
import com.example.windows10gamer.connsql.Adapter.Adapter_Taoxuat;
import com.example.windows10gamer.connsql.Kiem_Kho.Main_Ketqua_Kiemkho;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import es.dmoral.toasty.Toasty;

public class Main_Taoxuat extends AppCompatActivity {

    TextView tvTaoxuattenNV, tvTaoxuatmaNV, tvChuaco;
    Spinner spinnerTaoxuat;
    FloatingActionButton fabTaoxuat;
    Button btnTaoxuat;
    ListView lvTaoxuat;
    private String ma, ten, nguon, baohanh, gia, ngaynhap, von, chinhanhNhap, scannedData, chinhanhNow;
    ArrayList<String> position = new ArrayList<>();
    ArrayAdapter<String> mAdapter;
    ArrayList<Sanpham_gio> arrayList = new ArrayList<>();
    String session_username, session_ma, ngay, gio, maNVnhan, tenNVnhan, ca;
    ProgressDialog dialog;
    Adapter_Taoxuat adapter_kho;
    SharedPreferences shared;
    Spinner snA;
    String maXN;
    EditText edTaoxuatghichu;
    TextView tvchinhanh;
    String ghichu, tentrang;
    ImageView ivAvatar, ivAvatar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_taoxuat);
        tvchinhanh = findViewById(R.id.tvchinhanh);
        ivAvatar2 = findViewById(R.id.ivAvatar2);
        ivAvatar = findViewById(R.id.ivAvatar);
        tvChuaco = findViewById(R.id.tvChuaco);
        tvTaoxuatmaNV = findViewById(R.id.tvTaoxuatmaNV);
        tvTaoxuattenNV = findViewById(R.id.tvTaoxuattenNV);
        spinnerTaoxuat = findViewById(R.id.spinnerTaoxuat);
        btnTaoxuat = findViewById(R.id.btnScanTaoxuat);
        lvTaoxuat = findViewById(R.id.lvtaoxuat);
        edTaoxuatghichu = findViewById(R.id.edTaoxuatghichu);
        snA = findViewById(R.id.spChange);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanhNow = shared.getString("chinhanh", "");
        tvchinhanh.setText(chinhanhNow);
        ngay = Keys.getDateNow();
        ca = Keys.getCalam(chinhanhNow);
        new Getvitri().execute();
        adapter_kho = new Adapter_Taoxuat(Main_Taoxuat.this, R.layout.adapter_sales, arrayList);
        lvTaoxuat.setAdapter(adapter_kho);
        fabTaoxuat = findViewById(R.id.fabTaoxuat);
        shared = getSharedPreferences("login", MODE_PRIVATE);
        session_ma = shared.getString("ma", "");
        session_username = shared.getString("shortName", "");
        tvTaoxuatmaNV.setText("Mã số: " +session_ma);
        tvTaoxuattenNV.setText("Tên nhân viên: " +session_username);
        btnTaoxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Taoxuat.this).show();
                else {
                    StartScan();
                }
            }
        });
        fabTaoxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Taoxuat.this).show();
                else {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(Main_Taoxuat.this);
                    dialog.setMessage("Bạn có chắc muốn báo cáo?");
                    View mView = getLayoutInflater().inflate(R.layout.spinner_lk, null);
                    final EditText input = mView.findViewById(R.id.input);
                    dialog.setView(input);
                    input.setText(ngay + "_" + chinhanhNow);
                    dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (input.getText().toString().trim().equals("")) {
                                Toasty.error(Main_Taoxuat.this, "Không được để trống tên trang!", Toast.LENGTH_LONG, true).show();
                            } else {
                                maXN = "XNHH_"+ Keys.MaDonhang();
                                tentrang = input.getText().toString().trim();
                                ghichu = edTaoxuatghichu.getText().toString().trim();
                                new SendRequestXuatHang().execute();
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

        GetUser(Keys.DANHSACHLOGIN, new Main_Ketqua_Kiemkho.VolleyCallback(){
            @Override
            public void onSuccess(final ArrayList<User> result) {
                final ArrayList<String> resultName, resultMa, resultImg;
                resultName = new ArrayList<String>();
                resultMa = new ArrayList<String>();
                resultImg = new ArrayList<String>();
                for (int i = 0; i < result.size(); i++){
                    resultName.add(result.get(i).getShortName());
                    resultMa.add(result.get(i).getMa());
                }

                Adapter_Spinner_NV customAdapter=new Adapter_Spinner_NV(getApplicationContext(),resultMa,resultName);
                snA.setAdapter(customAdapter);

                snA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                        maNVnhan = result.get(position).getMa();
                        tenNVnhan = result.get(position).getShortName();
                        Glide.with(Main_Taoxuat.this).load(result.get(position).getImg()).override(300,300).fitCenter().into(ivAvatar2);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {}
                });
            }
        });
        GetUser(Main_Taoxuat.this, session_ma);
    }

    private void GetUser(final Context c, final String manhanvien) {
        final ArrayList<User> usernames = new ArrayList<User>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Keys.MAIN_LINKAVATAR+"?manhanvien="+manhanvien, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
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

    public void DeleteSP(final String msp){
        for (int i =  0; i <= arrayList.size(); i++){
            if (arrayList.get(i).getMa() == msp) {
                arrayList.remove(i);
                break;
            }
        }
        adapter_kho.notifyDataSetChanged();
    }

    class Getvitri extends AsyncTask<Void, Void, Void> {
        int jIndex;
        int x;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            jIndex = 0;
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
            setLisst(position);
        }
    }

    private void setLisst(ArrayList<String> position) {
        this.position = position;
        final Spinner spinnerKiemkho = findViewById(R.id.spinnerTaoxuat);
        mAdapter = new ArrayAdapter<>(Main_Taoxuat.this, R.layout.spinner_taoxuat, position);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKiemkho.setAdapter(mAdapter);
        spinnerKiemkho.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                chinhanhNhap = spinnerTaoxuat.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void StartScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Quét mã code");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null) {
            scannedData = result.getContents();
            if (scannedData != null) {
                try {
                    StringTokenizer st = new StringTokenizer(scannedData, ";");
                    ma = st.nextToken();
                    ten = st.nextToken();
                    baohanh = st.nextToken();
                    nguon = st.nextToken();
                    gio = Keys.getTimeNow();
                    ngaynhap = st.nextToken();
                    von = st.nextToken();
                    gia = st.nextToken();
                    arrayList.add(0, new Sanpham_gio(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia));
                    if(arrayList.size() > 0) {
                        adapter_kho.notifyDataSetChanged();
                    }
                    StartScan();
                    tvChuaco.setVisibility(View.GONE);
                    lvTaoxuat.setVisibility(View.VISIBLE);
                }   catch (NoSuchElementException nse) {
                    Toasty.error(this, "Lỗi định dạng mã vạch", Toast.LENGTH_LONG, true).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public ArrayList<User> GetUser(String urlUser, final Main_Ketqua_Kiemkho.VolleyCallback callback) {
        final ArrayList<User> usernames2 = new ArrayList<User>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlUser, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if (object.getInt("level") == Keys.LEVEL_BH){
                                    usernames2.add(new User(
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
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.onSuccess(usernames2);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onSuccess(usernames2);
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        return usernames2;
    }

    public interface VolleyCallback{
        void onSuccess(ArrayList<User> result);
    }

    public class SendRequestXuatHang extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){

            dialog = new ProgressDialog(Main_Taoxuat.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được xử lý");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... arg0) {
            int j;
            for (j = 0 ;j < arrayList.size(); j++){
                //putData(j);
                addXuathang(j);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            Toasty.success(Main_Taoxuat.this, "Gửi dữ liệu thành công", Toast.LENGTH_LONG, true).show();
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

//    public String putData(int j){
//        try {
//
//            URL url = new URL(Keys.getSCRIPT_XUATHANG);
//            JSONObject postDataParams = new JSONObject();
//                postDataParams.put("tentrang", tentrang);
//                postDataParams.put("maXN", maXN);
//                postDataParams.put("ngay", ngay);
//                postDataParams.put("ca", ca);
//                postDataParams.put("gio", arrayList.get(j).getGio());
//                postDataParams.put("chinhanhToday", chinhanhNow);
//                postDataParams.put("maNVToday", session_ma);
//                postDataParams.put("tenNVToday", session_username);
//                postDataParams.put("ma", arrayList.get(j).getMa());
//                postDataParams.put("ten", arrayList.get(j).getTen());
//                postDataParams.put("baohanh", arrayList.get(j).getBaohanh());
//                postDataParams.put("nguon", arrayList.get(j).getNguon());
//                postDataParams.put("ngaynhap", arrayList.get(j).getNgaynhap());
//                postDataParams.put("von", arrayList.get(j).getVon());
//                postDataParams.put("gia", arrayList.get(j).getGiaban());
//                postDataParams.put("ghichu", ghichu);
//                postDataParams.put("chinhanhNhan", chinhanhNhap);
//                postDataParams.put("maNVNhan", maNVnhan);
//                postDataParams.put("tenNVNhan", tenNVnhan);
//
//            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
//            conn.setReadTimeout(15000);
//            conn.setConnectTimeout(15000);
//            conn.setRequestMethod("GET");
//            conn.setDoInput(true);
//            conn.setDoOutput(true);
//
//            OutputStream os = conn.getOutputStream();
//            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
//            writer.write(getPostDataString(postDataParams));
//
//            writer.flush();
//            writer.close();
//            os.close();
//
//            int responseCode = conn.getResponseCode();
//            if (responseCode == HttpsURLConnection.HTTP_OK) {
//
//                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
//                StringBuffer sb = new StringBuffer("");
//                String line = "";
//
//                while ((line = in.readLine()) != null) {
//
//                    sb.append(line);
//                    break;
//                }
//                in.close();
//                return sb.toString();
//            } else {
//                return new String("false : " + responseCode);
//            }
//        } catch (Exception e) {
//            return new String("Exception: " + e.getMessage());
//        }
//    }

    public void ResetActivity(){
        startActivity(new Intent(Main_Taoxuat.this, Main_XuatNhap.class));
        finish();
    }

    public void addXuathang(final int j){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB_XUATNHAP,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toasty.error(Main_Taoxuat.this, "Lỗi", Toast.LENGTH_LONG, true).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toasty.error(Main_Taoxuat.this, "Lỗi "+error, Toast.LENGTH_LONG, true).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_XUATHANG_WEB);
                params.put("maXN", maXN);
                params.put("ngay", ngay);
                params.put("ca", ca);
                params.put("gio", arrayList.get(j).getGio());
                params.put("chinhanhToday", chinhanhNow);
                params.put("maNVToday", session_ma);
                params.put("tenNVToday", session_username);
                params.put("ma", arrayList.get(j).getMa());
                params.put("ten", arrayList.get(j).getTen());
                params.put("baohanh", arrayList.get(j).getBaohanh());
                params.put("nguon", arrayList.get(j).getNguon());
                params.put("ngaynhap", arrayList.get(j).getNgaynhap());
                params.put("von", arrayList.get(j).getVon());
                params.put("gia", arrayList.get(j).getGiaban());
                params.put("ghichu", ghichu);
                params.put("chinhanhNhan", chinhanhNhap);
                params.put("maNVNhan", maNVnhan);
                params.put("tenNVNhan", tenNVnhan);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
