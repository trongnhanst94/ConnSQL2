package com.example.windows10gamer.connsql.Ban_Hang;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.windows10gamer.connsql.Object.BH1D1;
import com.example.windows10gamer.connsql.Object.BHDDT;
import com.example.windows10gamer.connsql.Object.BHDLK;
import com.example.windows10gamer.connsql.Object.BHHT;
import com.example.windows10gamer.connsql.Object.Customer;
import com.example.windows10gamer.connsql.Object.Datcoc;
import com.example.windows10gamer.connsql.Object.Doanhthu;
import com.example.windows10gamer.connsql.Object.Khoanchi;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Object.PhiCOD;
import com.example.windows10gamer.connsql.Object.ReportSales;
import com.example.windows10gamer.connsql.Object.TienTraVe;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.APIService_Sales;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.OrderList;
import com.example.windows10gamer.connsql.Other.RetrofitClient;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Tao_BCDoanhthu extends AppCompatActivity {
    private TextView tvcod, tvthongbao, tvdoanhthubanno, tvtiendauca, tvtientrave, tvtiencuoica, tvsokhachhang, tvsosanpham, tvdoanhthu, tvgiamgia, tvdoanhthusaugiam, tvtongchi, tvdatcoc, tvhoancoc;
    TextView tvdtddt, tvpddt, tvnddt, tvdtdlk, tvpdlk, tvndlk, tvtht, tvdt1d1, tvp1d1, tvlechdoanhthu;
    TextView tvbcnhanvien;
    EditText edtienthucte;
    Button btnGuibaocao;
    ArrayList<Order> donhanglist = new ArrayList<>();
    ArrayList<PhiCOD> codlist = new ArrayList<>();
    ArrayList<Khoanchi> khoanchilist = new ArrayList<>();
    ArrayList<Datcoc> datcoclist = new ArrayList<>();
    ArrayList<Datcoc> hoancoclist = new ArrayList<>();
    ArrayList<BH1D1> BH1D1 = new ArrayList<>();
    ArrayList<BHHT> BHHT = new ArrayList<>();
    ArrayList<BHDLK> BHDLK = new ArrayList<>();
    ArrayList<BHDDT> BHDDT = new ArrayList<>();
    SharedPreferences shared;
    String chinhanh;
    int tiencuoica= 0, doanhthu = 0, giamgia = 0, tongchi = 0, doanhthubanno = 0, giamgiasing = 0, datcoc = 0, hoancoc = 0, tamungcod = 0, dttsp = 0, dtddt = 0, pddt = 0, nddt = 0, dtdlk = 0, pdlk = 0, ndlk = 0, tht = 0, dt1d1 = 0, p1d1 = 0;
    int tiendauca, tientrave;
    private ProgressDialog dialog;
    private String ma, ten;
    int soSanpham, soKhachhang, dttkh;
    String ngay, ca, madoanhthu, manhanvien, tennhanvien;
    SharedPreferences getShared;
    int check = 0, tienthucte = 0, lechcuoica = 0;
    private ArrayList<ReportSales> reportSalesList = new ArrayList<>();
    ArrayList<Doanhthu> intentList = new ArrayList<>();
    private int doanhthusing;
    private Integer position;
    final ArrayList seletedItems=new ArrayList();
    final ArrayList<User> usernames = new ArrayList<User>();
    Button btnnv;
    private ArrayList<String> stringDica = new ArrayList<>();
    private ArrayList<User> arrayDica = new ArrayList<>();
    private String madica;
    private ProgressDialog nPro;
    private ArrayList<String> listDica = new ArrayList<>();
    private String mamaBCDT;
    private Integer tongtrave = 0;
    ArrayList<TienTraVe> travelist = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tao_bcdoanhthu);
        anhxa();
        madica = Keys.MaDonhang();
        getShared = getSharedPreferences("login", MODE_PRIVATE);
        tennhanvien = getShared.getString("shortName", "");
        manhanvien = getShared.getString("ma", "");
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DataDoanhthu");
        position  = Integer.valueOf(bundle.getInt("position"));
        check  = Integer.valueOf(bundle.getString("check"));
        intentList = bundle.getParcelableArrayList("list");
        if (check == 1){
            btnGuibaocao.setVisibility(View.GONE);
            ngay = intentList.get(position).getNgay();
            ca = intentList.get(position).getCa();
            chinhanh = intentList.get(position).getChinhanh();
            tiendauca = Integer.valueOf(intentList.get(position).getTiendauca());
            tientrave = Integer.valueOf(intentList.get(position).getTientrave());
            lechcuoica = Integer.valueOf(intentList.get(position).getLechcuoica());
            tienthucte = Integer.valueOf(intentList.get(position).getTienthucte());
            mamaBCDT = String.valueOf(intentList.get(position).getMaDT());
            tvtiendauca.setText(Keys.setMoney(Integer.valueOf(tiendauca)));
            tvtientrave.setText(Keys.setMoney(Integer.valueOf(tientrave)));
            btnnv.setEnabled(false);
            btnnv.setBackgroundColor(getResources().getColor(R.color.aaaaa));
            new SendRequest().execute();
            tvlechdoanhthu.setText(Keys.setMoney(Integer.valueOf(lechcuoica)));
            edtienthucte.setText(Keys.setMoney(Integer.valueOf(tienthucte)));
            edtienthucte.setEnabled(false);
            tvbcnhanvien.setText("Nhân viên trong ca: ");
            new getDica().execute();
        } else {
            ShowDialog();
            ngay = Keys.getDateNow();
            ca = Keys.getCalam(chinhanh);
        }
        tvthongbao.setText("Báo cáo doanh thu "+ca+" ngày: "+ngay);
        edtienthucte.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (edtienthucte.getText().toString().trim().equals("")){
                    tvlechdoanhthu.setText(Keys.setMoney(0));
                } else {
                    tienthucte = Integer.valueOf(edtienthucte.getText().toString().trim());
                    lechcuoica = (tienthucte - tiencuoica);
                    tvlechdoanhthu.setText(Keys.setMoney(lechcuoica));
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        btnGuibaocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtienthucte.getText().toString().trim().equals("")){
                    new CustomToast().Show_Toast(Main_Tao_BCDoanhthu.this, findViewById(android.R.id.content), "Chưa nhập tiền thực tế!");
                } else  {
                    if (arrayDica.size() == 0){
                        new CustomToast().Show_Toast(Main_Tao_BCDoanhthu.this, findViewById(android.R.id.content), "Chưa chọn nhân viên đi ca!");
                    } else {
                        AlertDialog.Builder dialog = null;
                        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                            dialog = new AlertDialog.Builder(Main_Tao_BCDoanhthu.this);
                        } else {
                            dialog = new AlertDialog.Builder(Main_Tao_BCDoanhthu.this);
                        }
                        dialog.setIcon(R.drawable.ic_settings).setTitle("Thông báo");
                        dialog.setMessage("Bạn có chắc muốn báo cáo doanh thu?");
                        dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tienthucte = Integer.valueOf(edtienthucte.getText().toString().trim());
                                if (tienthucte == 0) {
                                } else {
                                    btnGuibaocao.setEnabled(false);
                                    btnGuibaocao.setBackgroundColor(getResources().getColor(R.color.aaaaa));
                                    madoanhthu = Keys.MaDonhang();
                                    new SendRequestweb().execute();
                                }
                            }
                        });
                        dialog.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                }
            }
        });
        GetUser(Keys.DANHSACHLOGIN, new VolleyCallback() {
            @Override
            public void onSuccess(final ArrayList<User> result) {
                btnnv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        arrayDica.clear();
                        seletedItems.clear();
                        stringDica.clear();
                        final String[] items = new String[usernames.size()];
                        for (int i = 0; i<usernames.size(); i++) {
                            items[i] = String.valueOf(usernames.get(i).getShortName());
                        }
                        AlertDialog dialog = new AlertDialog.Builder(Main_Tao_BCDoanhthu.this)
                                .setTitle("Chọn nhân viên trong ca:").setCancelable(false)
                                .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                                        if (isChecked) {
                                            seletedItems.add(indexSelected);
                                        } else if (seletedItems.contains(indexSelected)) {
                                            seletedItems.remove(Integer.valueOf(indexSelected));
                                        }
                                    }
                                }).setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        tvbcnhanvien.setText("Nhân viên trong ca: ");
                                        for (int i = 0; i < seletedItems.size(); i++) {
                                            if (i == seletedItems.size()-1){
                                                tvbcnhanvien.append(items[(int) seletedItems.get(i)].toString()+".");
                                            } else {
                                                tvbcnhanvien.append(items[(int) seletedItems.get(i)].toString()+ " - ");
                                            }
                                            stringDica.add(items[(int) seletedItems.get(i)].toString());
                                        }
                                        for (int i = 0; i < stringDica.size(); i++) {
                                            for (int i1 = 0; i1 < usernames.size(); i1++) {
                                                if (usernames.get(i1).getShortName().equals(stringDica.get(i))){
                                                    arrayDica.add(usernames.get(i1));
                                                }
                                            }
                                        }
                                    }
                                }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int id) {
                                        //  Your code when user clicked on Cancel
                                    }
                                }).create();
                        dialog.show();
                    }
                });
            }
        });
    }

    public interface VolleyCallback{
        void onSuccess(ArrayList<User> result);
    }

    public class SendRequestweb extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            nPro = new ProgressDialog(Main_Tao_BCDoanhthu.this);
            nPro.setTitle("Đang tạo báo cáo doanh thu!");
            nPro.setMax(arrayDica.size());
            nPro.setCancelable(false);
            nPro.setProgress(0);
            nPro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            nPro.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            int progress = 1;
            int i = 0;
            addDoanhthuWeb();
            while (progress <= arrayDica.size()){
                addDicaWeb(progress-1);
                publishProgress(progress);
                progress++;
            }
            sendFCMPush( "Doanh thu: "+Keys.setMoney(tienthucte)+" - "+ca,"Báo cáo "+chinhanh);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int prog = values[0];
            nPro.setProgress(prog);
        }

        @Override
        protected void onPostExecute(String s) {
            nPro.hide();
        }
    }

    private void anhxa() {
        tvdoanhthubanno = findViewById(R.id.tvdoanhthubanno);
        tvcod = findViewById(R.id.tvbccod);
        tvbcnhanvien = findViewById(R.id.tvbcnhanvien);
        edtienthucte = findViewById(R.id.edtienthucte);
        tvlechdoanhthu = findViewById(R.id.tvlechdoanhthu);
        btnGuibaocao = findViewById(R.id.btnGuibaocao);
        tvtiencuoica = findViewById(R.id.tvtiencuoica);
        tvdtddt = findViewById(R.id.tvbcdtddt);
        tvpddt = findViewById(R.id.tvbcpddt);
        tvnddt = findViewById(R.id.tvbcnddt);
        tvdtdlk = findViewById(R.id.tvbcdtdlk);
        tvpdlk = findViewById(R.id.tvbcpdlk);
        tvndlk = findViewById(R.id.tvbcndlk);
        btnnv = findViewById(R.id.btnnv);
        tvtht = findViewById(R.id.tvbctht);
        tvdt1d1 = findViewById(R.id.tvbcdt1d1);
        tvp1d1 = findViewById(R.id.tvbcp1d1);
        tvdatcoc = findViewById(R.id.tvbcdatcoc);
        tvhoancoc = findViewById(R.id.tvbchoancoc);
        tvtongchi = findViewById(R.id.tvbctongchi);
        tvthongbao = findViewById(R.id.tvbcthongbao);
        tvtiendauca = findViewById(R.id.tvbctiendauca);
        tvtientrave = findViewById(R.id.tvbctientrave);
        tvgiamgia = findViewById(R.id.tvbcgiamgia);
        tvdoanhthusaugiam = findViewById(R.id.tvbcdoanhthusaugiam);
        tvdoanhthu = findViewById(R.id.tvbcdoanhthu);
    }

    public ArrayList<User> GetUser(String urlUser, final VolleyCallback callback) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(com.android.volley.Request.Method.GET, urlUser, null,
                new com.android.volley.Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if (object.getInt("level") >= Keys.LEVEL_KHO && !object.getString("trangthai").equals("0")){
                                    usernames.add(new User(
                                            object.getInt("id"),
                                            object.getString("ma_user"),
                                            object.getString("ten"),
                                            object.getString("shortName"),
                                            object.getString("username"),
                                            object.getString("password")
                                    ));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.onSuccess(usernames);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onSuccess(usernames);
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        return usernames;
    }

    class getDica extends AsyncTask<String, Void, Void> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.DANHSACHDICA+"?mamaBCDT="+mamaBCDT);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DI_CA);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    if (chinhanh.equals(object.getString("chinhanh"))){
                                        listDica.add(object.getString("tennhanvien"));
                                    }
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
            if(listDica.size() > 0) {
                for (int i = 0; i < listDica.size(); i++) {
                    if (i == listDica.size()-1){
                        tvbcnhanvien.append(listDica.get(i)+".");
                    } else {
                        tvbcnhanvien.append(listDica.get(i)+ " - ");
                    }
                }
            }
        }
    }

    private void ShowDialog() {
        AlertDialog.Builder dialog = null;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            dialog = new AlertDialog.Builder(Main_Tao_BCDoanhthu.this);
        } else {
            dialog = new AlertDialog.Builder(Main_Tao_BCDoanhthu.this);
        }
        dialog.setCancelable(false);
        dialog.setIcon(R.drawable.ic_settings).setTitle("Nhập liệu");
        View mView = getLayoutInflater().inflate(R.layout.dialog_baocaodoanhthu, null);
        final EditText edtiendauca = mView.findViewById(R.id.edtiendauca);
        final RadioButton rbcasang = mView.findViewById(R.id.rbcasang);
        final RadioButton rbcachieu = mView.findViewById(R.id.rbcachieu);
        rbcasang.setChecked(true);
        rbcasang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!rbcachieu.isChecked() && isChecked == false){
                    rbcachieu.setChecked(true);
                } else if (isChecked == true){
                    rbcachieu.setChecked(false);
                }
            }
        });
        rbcachieu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!rbcasang.isChecked() && isChecked == false){
                    rbcasang.setChecked(true);
                } else if (isChecked == true){
                    rbcasang.setChecked(false);
                }
            }
        });
        dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (rbcasang.isChecked()){
                    ca = Keys.CA_SANG;
                } else ca = Keys.CA_CHIEU;
                if (edtiendauca.getText().toString().trim().equals("")){
                    tiendauca = 0;
                } else {
                    tiendauca = Integer.valueOf(edtiendauca.getText().toString().trim());
                }
                tvtiendauca.setText(Keys.setMoney(Integer.valueOf(tiendauca)));
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

    public void addDicaWeb(final int j){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, Keys.LINK_WEB_V2,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            if (j == (arrayDica.size()-1)) {
                                new CustomToast().Show_Toast(Main_Tao_BCDoanhthu.this, findViewById(android.R.id.content), "Thất bại, không kết nối được Server!!");
                            }
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Tao_BCDoanhthu.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.DI_CA);
                params.put("id", madica+arrayDica.get(j).getMa());
                params.put("maDica", "Dica_"+madica);
                params.put("ngay", ngay);
                params.put("ca", ca);
                params.put("chinhanh", chinhanh);
                params.put("manhanvien", arrayDica.get(j).getMa());
                params.put("tennhanvien", arrayDica.get(j).getShortName());
                params.put("maBCDT", "DT_"+madoanhthu);
                Log.e("getParams: ", params.toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void setList(ArrayList<Order> temp) {
        this.donhanglist = temp;
        int doanhthuTotal = 0;
        for (int i = 0; i < donhanglist.size(); i++){
            doanhthuTotal = doanhthuTotal + Integer.parseInt(donhanglist.get(i).getGiaSanpham());
        }
        ArrayList<Customer> khachhang = new ArrayList<>();
        for (int i = 0; i < donhanglist.size(); i++){
            int result = sosanhCustomer(khachhang, donhanglist.get(i).getTenKhachhang(), donhanglist.get(i).getMaDonhang());
            if (result == -1){
                khachhang.add(new Customer( donhanglist.get(i).getMaDonhang()+"KH", donhanglist.get(i).getTenKhachhang(), donhanglist.get(i).getSodienthoaiKhachhang(), donhanglist.get(i).getGhichuKhachhang(), donhanglist.get(i).getMaDonhang(), donhanglist.get(i).getMaNhanvien(), donhanglist.get(i).getTenNhanvien()));
            }
        }
        ArrayList<User> nhanVienList = new ArrayList<>();
        for (int i = 0; i < donhanglist.size(); i++){
            int result = sosanhUser(nhanVienList, donhanglist.get(i).getMaNhanvien(), donhanglist.get(i).getTenNhanvien());
            if (result == -1){
                nhanVienList.add(new User(donhanglist.get(i).getMaNhanvien(), donhanglist.get(i).getTenNhanvien()));
            }
        }

        for (int i = 0; i < nhanVienList.size(); i++){
            ma = nhanVienList.get(i).getMa();
            ten = nhanVienList.get(i).getShortName();
            doanhthusing = DoanhthuCount(donhanglist, nhanVienList.get(i).getMa());
            giamgiasing = GiamgiaCount(donhanglist, nhanVienList.get(i).getMa());
            soKhachhang = SoKhachhang(khachhang, nhanVienList.get(i).getMa());
            soSanpham   = SoSanpham(donhanglist, nhanVienList.get(i).getMa());
            dttkh       = doanhthusing/soKhachhang;
            dttsp       = doanhthusing/soSanpham;
            reportSalesList.add(
                    new ReportSales(ma, ten, doanhthusing - giamgiasing, soKhachhang, soSanpham, dttkh, dttsp)
            );
        }
        init(reportSalesList);
    }

    public void init(ArrayList<ReportSales> list) {
        TableLayout stk = findViewById(R.id.tbBCDT);
        TableRow tbrow0 = new TableRow(this);
        TextView tv = new TextView(this);
        tv.setText(" # ");
        tv.setTypeface(tv.getTypeface(), Typeface.BOLD);
        tv.setBackgroundResource(R.drawable.cell_shape);
        tv.setTextColor(Color.RED);
        tbrow0.addView(tv);
        TextView tv0 = new TextView(this);
        tv0.setText(" Tên NV ");
        tv0.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv0.setBackgroundResource(R.drawable.cell_shape);
        tv0.setTextColor(Color.RED);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" Doanhthu ");
        tv1.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv1.setBackgroundResource(R.drawable.cell_shape);
        tv1.setTextColor(Color.RED);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Số KH ");
        tv2.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv2.setBackgroundResource(R.drawable.cell_shape);
        tv2.setTextColor(Color.RED);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(" Số SP ");
        tv3.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv3.setBackgroundResource(R.drawable.cell_shape);
        tv3.setTextColor(Color.RED);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText(" DT/KH ");
        tv4.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv4.setBackgroundResource(R.drawable.cell_shape);
        tv4.setTextColor(Color.RED);
        tbrow0.addView(tv4);
        TextView tv5 = new TextView(this);
        tv5.setText(" DT/SP ");
        tv5.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv5.setBackgroundResource(R.drawable.cell_shape);
        tv5.setTextColor(Color.RED);
        tbrow0.addView(tv5);
        stk.addView(tbrow0);
        int stt = 1;
        for (int i = 0; i < list.size(); i++) {
            TableRow tbrow = new TableRow(this);
            TextView t0v = new TextView(this);
            t0v.setText(i+1+"");
            t0v.setGravity(Gravity.CENTER);
            t0v.setBackgroundResource(R.drawable.cell_shape);
            t0v.setTextColor(Color.DKGRAY);
            tbrow.addView(t0v);
            TextView t1v = new TextView(this);
            t1v.setText(list.get(i).getTenNhanvien());
            t1v.setGravity(Gravity.CENTER);
            t1v.setBackgroundResource(R.drawable.cell_shape);
            t1v.setTextColor(Color.DKGRAY);
            tbrow.addView(t1v);
            TextView t2v = new TextView(this);
            t2v.setText(Keys.setMoney(list.get(i).getDoanhthu()));
            t2v.setGravity(Gravity.CENTER);
            t2v.setBackgroundResource(R.drawable.cell_shape);
            t2v.setTextColor(Color.DKGRAY);
            tbrow.addView(t2v);
            TextView t3v = new TextView(this);
            t3v.setText(list.get(i).getSoKhachhang()+"");
            t3v.setGravity(Gravity.CENTER);
            t3v.setBackgroundResource(R.drawable.cell_shape);
            t3v.setTextColor(Color.DKGRAY);
            tbrow.addView(t3v);
            TextView t4v = new TextView(this);
            t4v.setText(list.get(i).getSoSanpham()+"");
            t4v.setGravity(Gravity.CENTER);
            t4v.setBackgroundResource(R.drawable.cell_shape);
            t4v.setTextColor(Color.DKGRAY);
            tbrow.addView(t4v);
            TextView t5v = new TextView(this);
            t5v.setText(Keys.setMoney(list.get(i).getDttkh()));
            t5v.setGravity(Gravity.CENTER);
            t5v.setBackgroundResource(R.drawable.cell_shape);
            t5v.setTextColor(Color.DKGRAY);
            tbrow.addView(t5v);
            TextView t6v = new TextView(this);
            t6v.setText(Keys.setMoney(list.get(i).getDttsp()));
            t6v.setGravity(Gravity.CENTER);
            t6v.setBackgroundResource(R.drawable.cell_shape);
            t6v.setTextColor(Color.DKGRAY);
            tbrow.addView(t6v);
            stk.addView(tbrow);
            stt++;
        }
    }
    private int SoSanpham(ArrayList<Order> reportList, String ma) {
        int tong = 0;
        for (int i = 0; i < reportList.size(); i++){
            if (reportList.get(i).getMaNhanvien().equals(ma)){
                tong++;
            }
        }
        return tong;
    }
    private int SoKhachhang(ArrayList<Customer> khachhang, String ma) {
        int tong = 0;
        for (int i = 0; i < khachhang.size(); i++){
            if (khachhang.get(i).getMaNhanvien().equals(ma)){
                tong++;
            }
        }
        return tong;
    }
    private int DoanhthuCount(ArrayList<Order> reportList, String ma) {
        int tong = 0;
        for (int i = 0; i < reportList.size(); i++){
            if (reportList.get(i).getMaNhanvien().equals(ma)){
                tong += Integer.valueOf(reportList.get(i).getGiaSanpham());
            }
        }
        return tong;
    }

    public static int GiamgiaCount(ArrayList<Order> reportList, String ma) {
        int giamgia = 0;
        ArrayList<Order> listInt = new ArrayList<>();
        for (int i = 0; i < reportList.size(); i++) {
            if (reportList.get(i).getMaNhanvien().equals(ma)){
                int sosanh = ssgiamgia(listInt, reportList.get(i).getMaDonhang());
                if ( sosanh == -1){
                    listInt.add(reportList.get(i));
                    giamgia = giamgia + Integer.parseInt(reportList.get(i).getGiamgia());
                }
            }
        }
        return giamgia;
    }

    private static int ssgiamgia(ArrayList<Order> listInt, String maDonhang) {
        int result = -1;
        if (listInt.size() != 0){
            for (int i = 0; i < listInt.size(); i++){
                if (listInt.get(i).getMaDonhang().equals(maDonhang)){
                    result = i;
                }
            }
        }
        return result;
    }

    private int sosanhUser(ArrayList<User> user, String ma, String ten) {
        int result = -1;
        if (user.size() != 0){
            for (int i = 0; i < user.size(); i++){
                if (user.get(i).getMa().equals(ma) && user.get(i).getShortName().equals(ten)){
                    result = i;
                }
            }
        }
        return result;
    }
    private int sosanhCustomer(ArrayList<Customer> order_h, String orderName, String ma) {
        int result = -1;
        if (order_h.size() != 0){
            for (int i = 0; i < order_h.size(); i++){
                if (order_h.get(i).getTenCus().equals(orderName) && order_h.get(i).getOrderCus().equals(ma)){
                    result = i;
                }
            }
        }
        return result;
    }
    private void sortDoanhthu(ArrayList<ReportSales> list) {
        Collections.sort(list, new Comparator<ReportSales>() {
            @Override
            public int compare(ReportSales lhs, ReportSales rhs) {
                return ((Integer)rhs.getDoanhthu()).compareTo(lhs.getDoanhthu());
            }
        });
    }
    private void sortSoKH(ArrayList<ReportSales> list) {
        Collections.sort(list, new Comparator<ReportSales>() {
            @Override
            public int compare(ReportSales lhs, ReportSales rhs) {
                return ((Integer)rhs.getSoKhachhang()).compareTo(lhs.getSoKhachhang());
            }
        });
    }
    private void sortSoSP(ArrayList<ReportSales> list) {
        Collections.sort(list, new Comparator<ReportSales>() {
            @Override
            public int compare(ReportSales lhs, ReportSales rhs) {
                return ((Integer)rhs.getSoSanpham()).compareTo(lhs.getSoSanpham());
            }
        });
    }
    private void sortDttkh(ArrayList<ReportSales> list) {
        Collections.sort(list, new Comparator<ReportSales>() {
            @Override
            public int compare(ReportSales lhs, ReportSales rhs) {
                return ((Integer)rhs.getDttkh()).compareTo(lhs.getDttkh());
            }
        });
    }
    private void sortDttsp(ArrayList<ReportSales> list) {
        Collections.sort(list, new Comparator<ReportSales>() {
            @Override
            public int compare(ReportSales lhs, ReportSales rhs) {
                return ((Integer)rhs.getDttsp()).compareTo(lhs.getDttsp());
            }
        });
    }

    public class SendRequest extends AsyncTask<Void, Void, String> {

        protected void onPreExecute(){
            super.onPreExecute();
            dialog = new ProgressDialog(Main_Tao_BCDoanhthu.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
        }

        protected String doInBackground(Void... arg0) {
            if(!Connect_Internet.checkConnection(getApplicationContext()))
                Connect_Internet.buildDialog(Main_Tao_BCDoanhthu.this).show();
            else {
                GetDonhang();
                new GetKhoanchi().execute(chinhanh);
                if (check != 1){
                    new GetTientrave().execute(chinhanh);
                }
                new GetCOD().execute(chinhanh);
                new GetDatcoc().execute(chinhanh);
                new GetHoancoc().execute(chinhanh);
                new GetBHDDT().execute(chinhanh);
                new GetBHDLK().execute(chinhanh);
                new GetBHHT().execute(chinhanh);
                new GetBH1D1().execute(chinhanh);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
        }
    }

    public void GetDonhang() {
        if (Connect_Internet.checkConnection(getApplicationContext())) {
            APIService_Sales api = RetrofitClient.getApiService();
            Call<OrderList> call = api.getDoanhthu(chinhanh, ngay, ca);
            call.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                    ArrayList<Order> orignal = new ArrayList<Order>();
                    ArrayList<String> tempgiamgia = new ArrayList<>();
                    if(response.isSuccessful()) {
                        orignal = response.body().getContacts();
                        donhanglist.clear();
                        for (int i = 0; i < orignal.size(); i++) {
                            if (orignal.get(i).getThanhtoan().equals(Keys.TIENMAT)){
                                donhanglist.add(orignal.get(i));
                                doanhthu += Integer.valueOf(orignal.get(i).getGiaSanpham());
                                int sosanhgiamgia = sosanhgiam(tempgiamgia, orignal.get(i).getMaDonhang());
                                if (sosanhgiamgia == -1) {
                                    tempgiamgia.add(orignal.get(i).getMaDonhang());
                                    giamgia += Integer.valueOf(orignal.get(i).getGiamgia());
                                }
                            } else if (orignal.get(i).getThanhtoan().equals(Keys.GHINO)) {
                                doanhthubanno += Integer.valueOf(orignal.get(i).getGiaSanpham());
                            }
                        }
                        tvdoanhthubanno.setText(Keys.setMoney(doanhthubanno));
                        tvdoanhthu.setText(Keys.setMoney(doanhthu));
                        tvgiamgia.setText(Keys.setMoney(giamgia));
                        tvdoanhthusaugiam.setText(Keys.setMoney(doanhthu-giamgia));
                        setList(donhanglist);
                    }
                }
                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {
                }
            });
        }
    }

    private int sosanhgiam(ArrayList<String> tempgiamgia, String maDonhang) {
        int result = -1;
        if (tempgiamgia.size() != 0){
            for (int i = 0; i < tempgiamgia.size(); i++){
                if (tempgiamgia.get(i).equals(maDonhang)){
                    result = i;
                }
            }
        }
        return result;
    }

    class GetKhoanchi extends AsyncTask<String, Void, Void> {

        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_KHOANCHI+"?chinhanh="+params[0]+"&ngay="+ngay+"&ca="+ca);
            try {
                if (jsonObject != null) {
                    khoanchilist.clear();
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.KHOANCHI);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    khoanchilist.add(new Khoanchi(
                                            object.getString("maKC"),
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
            if(khoanchilist.size() > 0) {
                for (int i = 0; i < khoanchilist.size(); i++) {
                    tongchi += Integer.valueOf(khoanchilist.get(i).getSotien());
                }
            }
            tvtongchi.setText(Keys.setMoney(tongchi));
        }
    }

    class GetCOD extends AsyncTask<String, Void, Void> {

        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_PHICOD+"?chinhanh="+params[0]+"&ngay="+ngay+"&ca="+ca);
            try {
                if (jsonObject != null) {
                    codlist.clear();
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.PHI_COD);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    codlist.add(new PhiCOD(
                                            object.getString("maCOD"),
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
            if(codlist.size() > 0) {
                for (int i = 0; i < codlist.size(); i++) {
                    tamungcod += Integer.valueOf(codlist.get(i).getSotien());
                }
            }
            tvcod.setText(Keys.setMoney(tamungcod));
        }
    }

    class GetTientrave extends AsyncTask<String, Void, Void> {

        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_TIENTRAVE+"?chinhanh="+params[0]+"&ngay="+ngay+"&ca="+ca);
            try {
                if (jsonObject != null) {
                    travelist.clear();
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.TIEN_TRA_VE);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    travelist.add(new TienTraVe(
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
            if(travelist.size() > 0) {
                for (int i = 0; i < travelist.size(); i++) {
                    tientrave += Integer.valueOf(travelist.get(i).getSotien());
                }
            }
            tvtientrave.setText(Keys.setMoney(tientrave));
        }
    }

    class GetDatcoc extends AsyncTask<String, Void, Void> {

        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_DATCOC+"?chinhanh="+params[0]+"&tacvu=datcoc&ngay="+ngay+"&ca="+ca);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DATCOC);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    datcoclist.add(new Datcoc(
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
            if(datcoclist.size() > 0) {
                for (int i = 0; i < datcoclist.size(); i++){
                    datcoc += Integer.valueOf(datcoclist.get(i).getSotien());
                }
            }
            tvdatcoc.setText(Keys.setMoney(datcoc));
        }
    }

    class GetHoancoc extends AsyncTask<String, Void, Void> {

        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_DATCOC+"?chinhanh="+params[0]+"&tacvu=hoancoc&ngaytra="+ngay+"&catra="+ca);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.DATCOC);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    hoancoclist.add(new Datcoc(
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
            if(hoancoclist.size() > 0) {
                for (int i = 0; i < hoancoclist.size(); i++){
                    hoancoc += Integer.valueOf(hoancoclist.get(i).getSotien());
                }
            }
            tvhoancoc.setText(Keys.setMoney(hoancoc));
        }
    }

    class GetBHDDT extends AsyncTask<String, Void, Void> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_BH_BHDDT+"?chinhanh="+params[0]+"&ngay="+ngay+"&ca="+ca);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.BHDDT_SHEET);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    BHDDT.add(new BHDDT(
                                            object.getString("maBH"),
                                            object.getString("dateToday"),
                                            object.getString("timeToday"),
                                            object.getString("gio"),
                                            object.getString("chinhanhToday"),
                                            object.getString("maNVToday"),
                                            object.getString("tenNVToday"),
                                            object.getString("maOrder"),
                                            object.getString("date"),
                                            object.getString("time"),
                                            object.getString("chinhanh"),
                                            object.getString("tenNV"),
                                            object.getString("maNV"),
                                            object.getString("ma"),
                                            object.getString("ten"),
                                            object.getString("baohanh"),
                                            object.getString("nguon"),
                                            object.getString("ngaynhap"),
                                            object.getString("von"),
                                            object.getString("gia"),
                                            object.getString("ghichuOrder"),
                                            object.getString("tenKH"),
                                            object.getString("sdtKH"),
                                            object.getString("ghichuKH"),
                                            object.getString("gio_moi"),
                                            object.getString("ma_moi"),
                                            object.getString("ten_moi"),
                                            object.getString("baohanh_moi"),
                                            object.getString("nguon_moi"),
                                            object.getString("ngaynhap_moi"),
                                            object.getString("von_moi"),
                                            object.getString("gia_moi"),
                                            object.getString("phidoiSP"),
                                            object.getString("chenhlech"),
                                            object.getString("lydo")
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
            if(BHDDT.size() > 0) {
                ArrayList<String> ssBHDDT = new ArrayList<>();
                for (int i = 0; i < BHDDT.size(); i++){
                    int demddt = ssddt(BHDDT.get(i).getMaBH(), ssBHDDT);
                    dtddt += Integer.valueOf(BHDDT.get(i).getGia_moi());
                    if (demddt == -1){
                        nddt += Integer.valueOf(BHDDT.get(i).getGia());
                        pddt += Integer.valueOf(BHDDT.get(i).getPhidoisp());
                        ssBHDDT.add(BHDDT.get(i).getMaBH());
                    }
                }
            }

            tvdtddt.setText(Keys.setMoney(dtddt));
            tvpddt.setText(Keys.setMoney(pddt));
            tvnddt.setText(Keys.setMoney(nddt));
        }
    }

    private int ssddt(String maBH, ArrayList<String> ssBHDDT) {
        int dem = -1;
        for (int i = 0; i < ssBHDDT.size(); i++) {
            if (ssBHDDT.get(i).equals(maBH)){
                dem = i;
            }
        }
        return dem;
    }

    class GetBHDLK extends AsyncTask<String, Void, Void> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_BH_BHDLK+"?chinhanh="+params[0]+"&ngay="+ngay+"&ca="+ca);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.BHDLK_SHEET);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    BHDLK.add(new BHDLK(
                                            object.getString("maBH"),
                                            object.getString("dateToday"),
                                            object.getString("timeToday"),
                                            object.getString("gio"),
                                            object.getString("chinhanhToday"),
                                            object.getString("maNVToday"),
                                            object.getString("tenNVToday"),
                                            object.getString("maOrder"),
                                            object.getString("date"),
                                            object.getString("time"),
                                            object.getString("chinhanh"),
                                            object.getString("tenNV"),
                                            object.getString("maNV"),
                                            object.getString("ma"),
                                            object.getString("ten"),
                                            object.getString("baohanh"),
                                            object.getString("nguon"),
                                            object.getString("ngaynhap"),
                                            object.getString("von"),
                                            object.getString("gia"),
                                            object.getString("ghichuOrder"),
                                            object.getString("tenKH"),
                                            object.getString("sdtKH"),
                                            object.getString("ghichuKH"),
                                            object.getString("gio_moi"),
                                            object.getString("ma_moi"),
                                            object.getString("ten_moi"),
                                            object.getString("baohanh_moi"),
                                            object.getString("nguon_moi"),
                                            object.getString("ngaynhap_moi"),
                                            object.getString("von_moi"),
                                            object.getString("gia_moi"),
                                            object.getString("phidoiSP"),
                                            object.getString("chenhlech"),
                                            object.getString("lydo")
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
            if(BHDLK.size() > 0) {
                ArrayList<String> ssBHDLK = new ArrayList<>();
                for (int i = 0; i < BHDLK.size(); i++){
                    int demdlk = ssdlk(BHDLK.get(i).getMaBH(), ssBHDLK);
                    dtdlk += Integer.valueOf(BHDLK.get(i).getGia_moi());
                    if (demdlk == -1){
                        pdlk += Integer.valueOf(BHDLK.get(i).getPhidoiSP());
                        ndlk += Integer.valueOf(BHDLK.get(i).getGia());
                        ssBHDLK.add(BHDLK.get(i).getMaBH());
                    }
                }
            }
            tvdtdlk.setText(Keys.setMoney(dtdlk));
            tvpdlk.setText(Keys.setMoney(pdlk));
            tvndlk.setText(Keys.setMoney(ndlk));
        }
    }

    private int ssdlk(String maBH, ArrayList<String> ssBHDLK) {
        int dem = -1;
        for (int i = 0; i < ssBHDLK.size(); i++) {
            if (ssBHDLK.get(i).equals(maBH)){
                dem = i;
            }
        }
        return dem;
    }

    class GetBHHT extends AsyncTask<String, Void, Void> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_BH_BHHT+"?chinhanh="+params[0]+"&ngay="+ngay+"&ca="+ca);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.BHHT_SHEET);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    BHHT.add(new BHHT(
                                            object.getString("maBH"),
                                            object.getString("dateToday"),
                                            object.getString("timeToday"),
                                            object.getString("gio"),
                                            object.getString("chinhanhToday"),
                                            object.getString("maNVToday"),
                                            object.getString("tenNVToday"),
                                            object.getString("maOrder"),
                                            object.getString("date"),
                                            object.getString("time"),
                                            object.getString("chinhanh"),
                                            object.getString("tenNV"),
                                            object.getString("maNV"),
                                            object.getString("ma"),
                                            object.getString("ten"),
                                            object.getString("baohanh"),
                                            object.getString("nguon"),
                                            object.getString("ngaynhap"),
                                            object.getString("von"),
                                            object.getString("gia"),
                                            object.getString("ghichuOrder"),
                                            object.getString("tenKH"),
                                            object.getString("sdtKH"),
                                            object.getString("ghichuKH"),
                                            object.getString("gtConlai"),
                                            object.getString("phitrahang"),
                                            object.getString("lydo")
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
            if(BHHT.size() > 0) {
                for (int i = 0; i < BHHT.size(); i++){
                    tht += Integer.valueOf(BHHT.get(i).getGtConlai());
                }
            }
            tvtht.setText(Keys.setMoney(tht));
        }
    }

    class GetBH1D1 extends AsyncTask<String, Void, Void> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_BH_BH1DOI1+"?chinhanh="+params[0]+"&ngay="+ngay+"&ca="+ca);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.BH1DOI1_SHEET);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    BH1D1.add(new BH1D1(
                                            object.getString("maBH"),
                                            object.getString("dateToday"),
                                            object.getString("timeToday"),
                                            object.getString("gio"),
                                            object.getString("chinhanhToday"),
                                            object.getString("maNVToday"),
                                            object.getString("tenNVToday"),
                                            object.getString("maOrder"),
                                            object.getString("date"),
                                            object.getString("time"),
                                            object.getString("chinhanh"),
                                            object.getString("tenNV"),
                                            object.getString("maNV"),
                                            object.getString("ma"),
                                            object.getString("ten"),
                                            object.getString("baohanh"),
                                            object.getString("nguon"),
                                            object.getString("ngaynhap"),
                                            object.getString("von"),
                                            object.getString("gia"),
                                            object.getString("ghichuOrder"),
                                            object.getString("tenKH"),
                                            object.getString("sdtKH"),
                                            object.getString("ghichuKH"),
                                            object.getString("gio_moi"),
                                            object.getString("ma_moi"),
                                            object.getString("ten_moi"),
                                            object.getString("baohanh_moi"),
                                            object.getString("nguon_moi"),
                                            object.getString("ngaynhap_moi"),
                                            object.getString("von_moi"),
                                            object.getString("gia_moi"),
                                            object.getString("phibaohanh"),
                                            object.getString("lydo")
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
            if(BH1D1.size() > 0) {
                for (int i = 0; i < BH1D1.size(); i++){
                    dt1d1 += Integer.valueOf(BH1D1.get(i).getGia_moi());
                    p1d1 += Integer.valueOf(BH1D1.get(i).getPhibaohanh());
                }
            }
            tvdt1d1.setText(Keys.setMoney(dt1d1));
            tvp1d1.setText(Keys.setMoney(p1d1));
            dialog.dismiss();
            tiencuoica = tiendauca+tientrave-tamungcod+doanhthu-giamgia-tongchi+datcoc-hoancoc+dtddt+pddt-nddt+dtdlk+pdlk-ndlk-tht+p1d1;
            tvtiencuoica.setText(Keys.setMoney(tiencuoica));
        }
    }

    private void sendFCMPush(String msg, String title) {

        JSONObject obj = null;
        JSONObject dataobjData = null;

        try {
            obj = new JSONObject();

            dataobjData = new JSONObject();
            dataobjData.put("message", msg);
            dataobjData.put("title", title);

            obj.put("to", Keys.FIREBASE_TOKEN);
            obj.put("data", dataobjData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsObjRequest = new JsonObjectRequest(Request.Method.POST, Keys.FIREBASE_API_LINK, obj,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "key=" + Keys.FIREBASE_SERVER_KEY);
                params.put("Content-Type", "application/json");
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        int socketTimeout = 1000 * 60;// 60 seconds
        RetryPolicy policy = new DefaultRetryPolicy(socketTimeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsObjRequest.setRetryPolicy(policy);
        requestQueue.add(jsObjRequest);
    }

    public void addDoanhthuWeb(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.POST, Keys.LINK_WEB_V2,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            new CustomToast().Show_Toast(Main_Tao_BCDoanhthu.this, findViewById(android.R.id.content), "Thất bại, không kết nối được Server!!");
                        } else if (response.trim().equals("success")){
                            new CustomToast().Show_Toast(Main_Tao_BCDoanhthu.this, findViewById(android.R.id.content), "Tạo đơn hàng thành công!!");
                        }
                        startActivity(new Intent(Main_Tao_BCDoanhthu.this, Main_Doanhthu.class));
                        finish();
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Tao_BCDoanhthu.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_DOANHTHU_WEB);
                params.put("maDT", "DT_"+madoanhthu);
                params.put("ngay", ngay);
                params.put("ca", ca);
                params.put("chinhanh", chinhanh);
                params.put("maNV", manhanvien);
                params.put("tenNV", tennhanvien);
                params.put("tiendauca", tiendauca+"");
                params.put("tientrave", tientrave+"");
                params.put("doanhthu", tiencuoica+"");
                params.put("lechcuoica", lechcuoica+"");
                params.put("tienthucte", tienthucte+"");
                Log.e("qqq", "getParams: "+params.toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}

