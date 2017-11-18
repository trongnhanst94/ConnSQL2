package com.example.windows10gamer.connsql.Bao_Hanh;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
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
import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order;
import com.example.windows10gamer.connsql.Main_Menu;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.Mylistview;
import com.example.windows10gamer.connsql.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

public class Main_Doilaykhac extends AppCompatActivity {

    String maOrder, date, time, tenNV, maNV, ten, ma, baohanh, nguon, gia, ngaynhap, von,maBH,
            dateToday, timeToday, tenKH, sdtKH, ghichuOrder, chinhanhOrder, ghichuKH, phidoiSP, lydo,ma_moi, ten_moi, baohanh_moi, nguon_moi, ngaynhap_moi, von_moi, gia_moi;
    ArrayList<Sanpham> sanpham = new ArrayList<>();
    ProgressDialog dialog;
    TextView tvDlkMaOrder,tvDlkDate,tvDlkTime,tvDlkMaNV,tvDlkTenNV,tvDlkGhichuOrder,tvDlkTenKH,edphidoiSP,
            tvDlkSdtKH,tvDlkGhichuKH,tvDlkTenNVNhan,tvDlkMaNVNhan,tvDlkDatetoday,tvDlkTimetoday, tvTongdonhang,tvChinhanhDLK,tvChinhanhDLKOrder ;
    int vitri;
    Mylistview lv_moi;
    ListView lv;
    Adapter_Info_Order adapter_sales;
    Adapter_Info_Order adapter_moi;
    ArrayList<Sanpham> arrayList, array_moi;
    Button btnxacnhan;
    EditText edlydoDLK;
    String session_username = Main_Menu.session_username;
    String session_ma = Main_Menu.session_ma;
    int total = 0;int chenhlech = 0;
    String chinhanh = Main_Menu.chinhanh;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_doilaykhac);
        Anhxa();
        maBH = Keys.MaDonhang();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoOrder3");
        sanpham     = bundle.getParcelableArrayList("sanphamOrder");
        vitri       = bundle.getInt("vitri");
        maOrder     = bundle.getString("MaOrder");
        date        = bundle.getString("DateOrder");
        time        = bundle.getString("TimeOrder");
        chinhanhOrder = bundle.getString("ChinhanhOrder");
        tenNV       = bundle.getString("TenNVOrder");
        maNV        = bundle.getString("MaNVOrder");
        ten         = sanpham.get(vitri).getTen();
        ma          = sanpham.get(vitri).getMa();
        baohanh     = sanpham.get(vitri).getBaohanh();
        nguon       = sanpham.get(vitri).getNguon();
        ngaynhap    = sanpham.get(vitri).getNgaynhap();
        gia         = sanpham.get(vitri).getGiaban();
        von         = sanpham.get(vitri).getVon();
        tenKH       = bundle.getString("TenKH");
        sdtKH       = bundle.getString("SdtKH");
        ghichuKH    = bundle.getString("GhichuKH");
        ghichuOrder = bundle.getString("GhichuOrder");
        dateToday = Keys.getDateNow();
        timeToday = Keys.getCalam();
        arrayList = new ArrayList<>();
        array_moi = new ArrayList<>();
        btnxacnhan.setEnabled(false);
        btnxacnhan.setBackgroundColor(getResources().getColor(R.color.aaaaa));
        arrayList.add(new Sanpham(ma, ten, baohanh, nguon, ngaynhap, von, gia));
        adapter_sales = new Adapter_Info_Order(Main_Doilaykhac.this, arrayList);
        lv.setAdapter(adapter_sales);
        adapter_moi = new Adapter_Info_Order(Main_Doilaykhac.this, array_moi);
        lv_moi.setAdapter(adapter_moi);
        if (ghichuKH.equals("")){
            tvDlkGhichuKH.setVisibility(View.GONE);
        } else {
            tvDlkGhichuKH.setText("Ghi chú KH: "+ghichuKH);
        }
        if (ghichuOrder.equals("")){
            tvDlkGhichuOrder.setVisibility(View.GONE);
        } else {
            tvDlkGhichuOrder.setText("Ghi chú đơn hàng: "+ghichuOrder);
        }
        tvDlkMaOrder.setText("Mã đơn hàng: "+maOrder);
        tvDlkDate.setText(Keys.setDate(date));
        tvDlkTime.setText(time);
        tvDlkDatetoday.setText(Keys.setDate(dateToday));
        tvDlkTimetoday.setText(timeToday);
        tvDlkMaNV.setText("Mã NV: "+maNV);
        tvDlkTenNV.setText("Tên NV: "+tenNV);
        tvDlkTenKH.setText("Tên KH: "+tenKH);
        tvDlkSdtKH.setText("SĐT KH: "+sdtKH);
        tvChinhanhDLK.setText(chinhanh);
        tvChinhanhDLKOrder.setText(chinhanhOrder);
        tvDlkTenNVNhan.setText("Mã NV bảo hành: "+session_username);
        tvDlkMaNVNhan.setText("Tên NV bảo hành: "+session_ma);
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!edlydoDLK.getText().toString().trim().equals("") && !edphidoiSP.getText().toString().trim().equals("")) {
                    phidoiSP = edphidoiSP.getText().toString().trim();
                    lydo = edlydoDLK.getText().toString().trim();
                    new SendRequestDLK().execute();
                }
                else Snackbar.make(v, "Phải nhập tất cả các trường.", Snackbar.LENGTH_LONG).show();
            }
        });
        Button exit= (Button) findViewById(R.id.cancel);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(Main_Doilaykhac.this);
                builder.setMessage("Bạn muốn hủy đơn bảo hành??");
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();                        }
                });
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Main_Doilaykhac.this.finish();
                    }
                });
                builder.show();
            }
        });
    }

    private void Anhxa() {
        edphidoiSP = (TextView) findViewById(R.id.edphidoiSP);
        edlydoDLK = (EditText) findViewById(R.id.edlydoBHDLK);
        tvDlkMaOrder = (TextView) findViewById(R.id.tvDlkMaOrder);
        tvDlkDate = (TextView) findViewById(R.id.tvDlkDate);
        tvDlkTime = (TextView) findViewById(R.id.tvDlkTime);
        tvTongdonhang     = (TextView) findViewById(R.id.tvTongdonhang);
        tvDlkDatetoday = (TextView) findViewById(R.id.tvDlkDatetoday);
        tvDlkTimetoday = (TextView) findViewById(R.id.tvDlkTimetoday);
        tvDlkMaNV = (TextView) findViewById(R.id.tvDlkMaNV);
        tvChinhanhDLK = (TextView) findViewById(R.id.tvChinhanhDLK);
        tvChinhanhDLKOrder = (TextView) findViewById(R.id.tvChinhanhDLKOrder);
        tvDlkTenNV = (TextView) findViewById(R.id.tvDlkTenNV);
        tvDlkGhichuOrder = (TextView) findViewById(R.id.tvDlkGhichu);
        tvDlkTenKH = (TextView) findViewById(R.id.tvDlkTenKH);
        tvDlkSdtKH = (TextView) findViewById(R.id.tvDlkSdtKH);
        lv = (ListView) findViewById(R.id.lvBHDLK);
        lv_moi = (Mylistview) findViewById(R.id.lvBHDLK_moi);
        tvDlkGhichuKH = (TextView) findViewById(R.id.tvDlkGhichuKH);
        tvDlkTenNVNhan = (TextView) findViewById(R.id.tvDlkTenNVNhan);
        tvDlkMaNVNhan = (TextView) findViewById(R.id.tvDlkMaNVNhan);
        btnxacnhan = (Button) findViewById(R.id.btnBHDLK);
    }

    public void scanSanpham(View view){

//        ZxingOrient intentIntegrator = new ZxingOrient(Main_Doilaykhac.this);
//        intentIntegrator.setIcon(R.drawable.icon);
//        intentIntegrator.setToolbarColor("#AA3F51B5");
//        intentIntegrator.setInfoBoxColor("#AA3F51B5");
//        intentIntegrator.setInfo("Scan a QR code Image.");
//        intentIntegrator.setCaptureActivity(ToolbarCaptureActivity.class).initiateScan(Barcode.QR_CODE);
//
//        new ZxingOrient(Main_Doilaykhac.this)
//                .showInfoBox(false)
//                .setBeep(true)
//                .setVibration(true)
//                .setCaptureActivity(ToolbarCaptureActivity.class).initiateScan();
        IntentIntegrator integrator = new IntentIntegrator(this);        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);        integrator.setPrompt("Quét mã code");        integrator.setOrientationLocked(false);        integrator.setBeepEnabled(false);        integrator.initiateScan();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null) {
            String scannedData = result.getContents();
            if (scannedData != null) {
                try{
                    StringTokenizer st = new StringTokenizer(scannedData, ";");
                    ma_moi = st.nextToken();
                    ten_moi = st.nextToken();
                    baohanh_moi = st.nextToken();
                    nguon_moi = st.nextToken();
                    ngaynhap_moi = st.nextToken();
                    von_moi = st.nextToken();
                    gia_moi = st.nextToken();
                    array_moi.add(new Sanpham(ma_moi, ten_moi, baohanh_moi, nguon_moi, ngaynhap_moi, von_moi, gia_moi));
                    total = total + Integer.parseInt(gia_moi);
                    tvTongdonhang.setText(Keys.getFormatedAmount(total));
                    btnxacnhan.setEnabled(true);
                    btnxacnhan.setBackgroundColor(getResources().getColor(R.color.cam));
                    edphidoiSP.setText(Keys.getFormatedAmount(Integer.valueOf(gia)-total));
                    adapter_moi.notifyDataSetChanged();
                    scanSanpham(findViewById(android.R.id.content));
                }   catch (NoSuchElementException nse) {
                    Toast.makeText(Main_Doilaykhac.this, "Lỗi định dạng nhãn", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void huy(){
        finish();
    }

    public class SendRequestDLK extends AsyncTask<String, Void, String> {


        protected void onPreExecute(){

            dialog = new ProgressDialog(Main_Doilaykhac.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được xử lý");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(String... arg0) {
            int j;
            for (j = 0 ;j < array_moi.size(); j++){
                putData(j);
                addDlkWeb(j);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            dialog.dismiss();
            new CustomToast().Show_Toast(Main_Doilaykhac.this, findViewById(android.R.id.content), "Gửi dữ liệu thành công.");
            ResetActivity();
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

    public String putData(int j){
        try {

            URL url = new URL(Keys.SCRIPT_BH_DLK);
            JSONObject postDataParams = new JSONObject();
            postDataParams.put("maBH", "BHDLK_"+maBH);
            postDataParams.put("dateToday", dateToday);
            postDataParams.put("timeToday", timeToday);
            postDataParams.put("chinhanhToday", chinhanh);
            postDataParams.put("tenNVToday", session_username);
            postDataParams.put("maNVToday", session_ma);
            postDataParams.put("maOrder", maOrder);
            postDataParams.put("date", Keys.setDate(date));
            postDataParams.put("time", time);
            postDataParams.put("chinhanh", chinhanhOrder);
            postDataParams.put("tenNV", tenNV);
            postDataParams.put("maNV", maNV);
            postDataParams.put("ten", ten);
            postDataParams.put("ma", ma);
            postDataParams.put("baohanh", baohanh);
            postDataParams.put("nguon", nguon);
            postDataParams.put("ngaynhap", Keys.setNN(ngaynhap));
            postDataParams.put("von", von);
            postDataParams.put("gia", gia);
            postDataParams.put("ghichuOrder", ghichuOrder);
            postDataParams.put("tenKH", tenKH);
            postDataParams.put("sdtKH", sdtKH);
            postDataParams.put("ghichuKH", ghichuKH);
            postDataParams.put("phidoiSP", phidoiSP);
            postDataParams.put("ten_moi", array_moi.get(j).getTen());
            postDataParams.put("ma_moi", array_moi.get(j).getMa());
            postDataParams.put("baohanh_moi", array_moi.get(j).getBaohanh());
            postDataParams.put("nguon_moi", array_moi.get(j).getNguon());
            postDataParams.put("ngaynhap_moi", array_moi.get(j).getNgaynhap());
            postDataParams.put("von_moi", array_moi.get(j).getVon());
            postDataParams.put("gia_moi", array_moi.get(j).getGiaban());
            postDataParams.put("lydo", lydo);
            postDataParams.put("chechlech", 0+"");

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000);
            conn.setConnectTimeout(15000);
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
                return new String("false : " + responseCode);
            }
        } catch (Exception e) {
            return new String("Exception: " + e.getMessage());
        }
    }

    public void ResetActivity(){
        startActivity(new Intent(Main_Doilaykhac.this, Main_Baohanh.class));
        finish();
    }

    public void addDlkWeb(final int j){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            Toast.makeText(Main_Doilaykhac.this, "Lỗi ", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Main_Doilaykhac.this, "Lỗi "+error, Toast.LENGTH_SHORT).show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_BHDLK_WEB);
                params.put("maBH", "BHDLK_"+ maBH);
                params.put("dateToday", dateToday);
                params.put("timeToday", timeToday);
                params.put("chinhanhToday", chinhanh);
                params.put("tenNVToday", session_username);
                params.put("maNVToday", session_ma);
                params.put("maOrder", maOrder);
                params.put("date", Keys.setDate(date));
                params.put("time", time);
                params.put("chinhanh", chinhanhOrder);
                params.put("tenNV", tenNV);
                params.put("maNV", maNV);
                params.put("ten", ten);
                params.put("ma", ma);
                params.put("baohanh", baohanh);
                params.put("nguon", nguon);
                params.put("ngaynhap", Keys.setNN(ngaynhap));
                params.put("von", von);
                params.put("gia", gia);
                params.put("ghichuOrder", ghichuOrder);
                params.put("tenKH", tenKH);
                params.put("sdtKH", sdtKH);
                params.put("ghichuKH", ghichuKH);
                params.put("phidoiSP", phidoiSP);
                params.put("ten_moi", array_moi.get(j).getTen());
                params.put("ma_moi", array_moi.get(j).getMa());
                params.put("baohanh_moi", array_moi.get(j).getBaohanh());
                params.put("nguon_moi", array_moi.get(j).getNguon());
                params.put("ngaynhap_moi", array_moi.get(j).getNgaynhap());
                params.put("von_moi", array_moi.get(j).getVon());
                params.put("gia_moi", array_moi.get(j).getGiaban());
                params.put("lydo", lydo);
                params.put("chechlech", 0+"");
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}


