package com.example.windows10gamer.connsql.Ban_Hang;

/**
 * Created by EVRESTnhan on 9/30/2017.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.example.windows10gamer.connsql.Adapter.Adapter_ListCustomer;
import com.example.windows10gamer.connsql.Adapter.Adapter_Quatang;
import com.example.windows10gamer.connsql.Adapter.Adapter_Sales;
import com.example.windows10gamer.connsql.Adapter.Adapter_Spinner_NV;
import com.example.windows10gamer.connsql.Object.Chuongtrinh;
import com.example.windows10gamer.connsql.Object.KhachHang;
import com.example.windows10gamer.connsql.Object.Quatang;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Object.SanphamAmount;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.GiftList;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.Mylistview;
import com.example.windows10gamer.connsql.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

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
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;

import de.hdodenhof.circleimageview.CircleImageView;

public class Main_Sales extends AppCompatActivity {
    EditText edGiamgia;
    CheckBox rbGiamgia;
    CheckBox rbTangqua;
    RadioGroup rgThanhtoan;
    RadioButton rbTienmat, rbGhino;
    EditText edNguoino;
    LinearLayout lnThanhtoan;
    TextView tvManhanvien, tvTongdonhang, tvTennhanvien, tvSalesDate, tvSalesTime, tvChinhanhSales, tvgiatriMagiam, tvphaithu;
    EditText edKhachhang, edSodienthoai, edGhichudonhang, edGhichukhachhang;
    ImageView ivDoinv;
    GiftList lvKhuyenmai;
    Mylistview listView;
    ArrayList<SanphamAmount> arrayListAmount = new ArrayList<>();
    ArrayList<SanphamAmount> arrayList;
    ArrayList<Sanpham_gio> khuyenmaiList;
    ArrayList<Quatang> quatang;
    ArrayAdapter<String> adapterA;
    Adapter_Sales adapter;
    ArrayList<Sanpham> a = new ArrayList<Sanpham>();
    Adapter_Quatang adapter2;
    ArrayAdapter<String> mAdapter;
    int total = 0, giamgia = 0;
    View view;
    final ArrayList seletedItems=new ArrayList();
    Switch switchChangeNV;
    boolean[] checkedItem;
    final ArrayList<String> itemKM = new ArrayList<>();
    LinearLayout lnTangqua, lnGiamgia, ln1, ln2, lnNVDefault, lnNVChange, lnHidden;
    ProgressDialog pDialog;
    SharedPreferences shared;
    Button btnXacnhan, btnCancel, btn_scan_now, btnkiemtraMGG;
    String session_username, session_ma, session_username1, session_ma1;
    String maGiamgia, giatriMagiamgia;
    String ten, ma, nguon, ca, ngaynhap, baohanh, gia, ngay, gio, chinhanh, von, tenkhachhang, sodienthoaikhachhang, ghichukhachhang, ghichusanpham, madonhang;
    Spinner snA;
    ProgressDialog nPro, slPro;
    private ArrayList<String> stringDica = new ArrayList<>();
    private ArrayList<Quatang> arrayQuatang = new ArrayList<>();
    private ArrayList<String> listsoluong = new ArrayList<>();
    String maNhanvienban, tenNhanvienban;
    private ArrayList<Quatang> quatangFilter = new ArrayList<>();
    private ArrayList<Chuongtrinh> listChuongtrinh = new ArrayList<>();
    private String linkAvatar;
    private CircleImageView ivAvatar;
    String thanhtoan = "Tiền mặt", nguoino = "";
    TextView btncheckname, btncheckphone;
    ListView dialog_ListView;
    Adapter_ListCustomer adapter_listCustomer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_sales);
        btncheckname      = findViewById(R.id.btncheckname);
        btncheckphone     = findViewById(R.id.btncheckphone);
        lnThanhtoan       = findViewById(R.id.lnThanhtoan);
        edNguoino         = findViewById(R.id.edNguoino);
        rgThanhtoan       = findViewById(R.id.rgThanhtoan);
        rbGhino           = findViewById(R.id.rbGhino);
        rbTienmat         = findViewById(R.id.rbTienmat);
        tvTongdonhang     = findViewById(R.id.tvTongdonhang);
        tvManhanvien      = findViewById(R.id.tvManhanvien);
        tvTennhanvien     = findViewById(R.id.tvTennhanvien);
        rbGiamgia         = findViewById(R.id.rbGiamgia);
        rbTangqua         = findViewById(R.id.rbTangqua);
        tvSalesDate       = findViewById(R.id.tvSalesDate);
        tvSalesTime       = findViewById(R.id.tvSalesTime);
        tvChinhanhSales   = findViewById(R.id.tvChinhanhSales);
        edKhachhang       = findViewById(R.id.edKhachhang);
        edSodienthoai     = findViewById(R.id.edSodienthoai);
        edGhichudonhang   = findViewById(R.id.edGhichudonhang);
        edGhichukhachhang = findViewById(R.id.edGhichukhachhang);
        btn_scan_now      = findViewById(R.id.btn_scan_now);
        btnXacnhan        = findViewById(R.id.submitSanpham);
        btnkiemtraMGG     = findViewById(R.id.btnkiemtraMGG);
        btnCancel         = findViewById(R.id.cancelSanpham);
        listView          = findViewById(R.id.lvSanpham);
        lvKhuyenmai        = findViewById(R.id.lvKhuyenmai);
        lnTangqua         = findViewById(R.id.lnTangqua);
        lnGiamgia         = findViewById(R.id.lngiamgia);
        edGiamgia         = findViewById(R.id.edGiamgia);
        tvgiatriMagiam    = findViewById(R.id.tvgiatriMagiam);
        tvphaithu         = findViewById(R.id.tvphaithu);
        ln1               = findViewById(R.id.ln1);
        ln2               = findViewById(R.id.ln2);
        snA               = findViewById(R.id.spChange);
        lnNVDefault       = findViewById(R.id.lnNVDefault);
        lnNVChange        = findViewById(R.id.lnNVChange);
        lnHidden          = findViewById(R.id.lnHidden);
        switchChangeNV    = findViewById(R.id.switchChangeNV);
        ivAvatar          = findViewById(R.id.ivAvatar);
        rbTienmat.setChecked(true);
        lnThanhtoan.setVisibility(View.GONE);
        lnGiamgia.setVisibility(View.GONE);
        lnTangqua.setVisibility(View.GONE);
        lnHidden.setVisibility(View.GONE);
        ln1.setVisibility(View.GONE);
        ln2.setVisibility(View.GONE);
        giatriMagiamgia = "0";
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        ngay = getDate();
        ca = Keys.getCalam(chinhanh);
        tvSalesDate.setText(ngay);
        tvSalesTime.setText(ca);
        shared = getSharedPreferences("login", MODE_PRIVATE);
        linkAvatar = shared.getString("img", "");
        Glide.with(Main_Sales.this).load(linkAvatar).into(ivAvatar);
        Intent intentput  = getIntent();
        session_username1  = intentput.getStringExtra("session_username");
        session_ma1        = intentput.getStringExtra("session_ma");
        tvManhanvien.setText("Mã số: " + session_ma1);
        tvTennhanvien.setText("Tên nhân viên: " + session_username1);
        tvChinhanhSales.setText(chinhanh);
        edGhichudonhang.setSelected(false);
        arrayList     = new ArrayList<>();
        khuyenmaiList = new ArrayList<>();
        quatang = new ArrayList<>();
        if (chinhanh.equals(Keys.CN_SOL)){
            rbTangqua.setEnabled(false);
        }
        rbTangqua.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rbGiamgia.setChecked(false);
                    lnGiamgia.setVisibility(View.GONE);
                    lnTangqua.setVisibility(View.VISIBLE);
                } else {
                    lnTangqua.setVisibility(View.GONE);
                }
            }
        });
        rbGiamgia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rbTangqua.setChecked(false);
                    lnGiamgia.setVisibility(View.VISIBLE);
                    lnTangqua.setVisibility(View.GONE);
                } else {
                    lnGiamgia.setVisibility(View.GONE);
                    ln1.setVisibility(View.GONE);
                    ln2.setVisibility(View.GONE);
                    giatriMagiamgia = "0";
                    maGiamgia = "";
                }
            }
        });
        adapter = new Adapter_Sales(Main_Sales.this, R.layout.adapter_sanpham_amount, arrayListAmount, listsoluong);
        listView.setAdapter(adapter);
        new GetListSoluong().execute();
        new GetDataQuaTang().execute();
        new GetDataChuongtrinh().execute();
        btn_scan_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Sales.this).show();
                else {
                    scanSanpham();
                }
            }
        });
        edGiamgia.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
                int totalTam = total;
                if (edGiamgia.getText().toString().equals("")){
                    giamgia = 0;
                    tvTongdonhang.setText(setMoney(total));
                } else {
                    totalTam = totalTam - giamgia;
                    tvTongdonhang.setText(setMoney(totalTam));
                    if (totalTam < 0 ){
                        btnXacnhan.setEnabled(false);
                        btnXacnhan.setBackgroundColor(getResources().getColor(R.color.aaaaa));
                    } else {
                        btnXacnhan.setEnabled(true);
                        btnXacnhan.setBackgroundColor(getResources().getColor(R.color.colorMain));
                    }
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(!Connect_Internet.checkConnection(getApplicationContext()))
                        Connect_Internet.buildDialog(Main_Sales.this).show();
                    else {
                        AlertDialog.Builder builder = null;
                        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                            builder = new AlertDialog.Builder(Main_Sales.this);
                        } else {
                            builder = new AlertDialog.Builder(Main_Sales.this, android.R.style.Theme_Holo_Light_Panel);
                        }
                        builder.setIcon(R.drawable.ic_settings);
                        builder.setMessage("Bạn muốn hủy đơn hàng??");
                        builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Main_Sales.this.finish();
                            }
                        });
                        builder.show();
                    }
                }
            });
        rgThanhtoan.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                doOnDifficultyLevelChanged(group, checkedId);
            }
        });
        btnkiemtraMGG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Sales.this).show();
                else {
                    if (!edGiamgia.getText().toString().trim().equals("")) {
                        maGiamgia = edGiamgia.getText().toString().trim();
                        giatriMagiamgia = "0";
                        new GetDataGiamGia().execute();
                    } else {
                        giatriMagiamgia = "0";
                        maGiamgia = "";
                        new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Chưa nhập mã giảm giá!");
                    }
                }
            }
        });
        session_ma = session_ma1;
        session_username = session_username1;
        switchChangeNV.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    Glide.with(Main_Sales.this).load(linkAvatar).into(ivAvatar);
                }
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Sales.this).show();
                else {
                    if (isChecked) {
                        lnNVDefault.setVisibility(View.GONE);
                        lnNVChange.setVisibility(View.VISIBLE);
                        GetUser(Keys.DANHSACHLOGIN, new VolleyCallback() {
                            @Override
                            public void onSuccess(final ArrayList<User> result) {
                                final ArrayList<String> resultName, resultMa, resultImg;
                                resultName = new ArrayList<String>();
                                resultMa = new ArrayList<String>();
                                resultImg = new ArrayList<String>();
                                for (int i = 0; i < result.size(); i++) {
                                    resultName.add(result.get(i).getShortName());
                                    resultMa.add(result.get(i).getMa());
                                    resultImg.add(result.get(i).getImg());
                                }

                                Adapter_Spinner_NV customAdapter = new Adapter_Spinner_NV(getApplicationContext(), resultMa, resultName);
                                snA.setAdapter(customAdapter);

                                snA.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                                    @Override
                                    public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                                        session_ma = result.get(position).getMa();
                                        session_username = result.get(position).getShortName();
                                        Glide.with(Main_Sales.this).load(result.get(position).getImg()).into(ivAvatar);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> arg0) {
                                    }
                                });

                                btnXacnhan.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        madonhang = Keys.MaDonhang();
                                        if (session_ma.equals("Chọn")){
                                            new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Chưa chọn nhân viên bán!");
                                        } else {
                                            if (arrayList.size() != 0) {
                                                btnXacnhan.setEnabled(false);
                                                btnXacnhan.setBackgroundColor(getResources().getColor(R.color.aaaaa));
                                                ngay = getDate();
                                                ca = Keys.getCalam(chinhanh);
                                                tenkhachhang = edKhachhang.getText().toString();
                                                sodienthoaikhachhang = edSodienthoai.getText().toString();
                                                ghichukhachhang = edGhichukhachhang.getText().toString();
                                                ghichusanpham = edGhichudonhang.getText().toString();
                                                nguoino = edNguoino.getText().toString();
                                                maNhanvienban = session_ma1;
                                                tenNhanvienban = session_username1;
                                                setSoluong();
                                                new SendRequest().execute();
                                            } else {
                                                new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Chưa nhập mã giảm giá!");
                                            }
                                        }

                                    }
                                });
                            }
                        });
                    } else {
                        lnNVDefault.setVisibility(View.VISIBLE);
                        lnNVChange.setVisibility(View.GONE);
                        session_ma = session_ma1;
                        session_username = session_username1;
                        maNhanvienban = session_ma1;
                        tenNhanvienban = session_username1;
                    }
                }
            }
        });
        btnXacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Sales.this).show();
                else {
                    madonhang = Keys.MaDonhang();
                    if (arrayList.size() != 0) {
                        btnXacnhan.setEnabled(false);
                        btnXacnhan.setBackgroundColor(getResources().getColor(R.color.aaaaa));
                        ngay = getDate();
                        ca = Keys.getCalam(chinhanh);
                        tenkhachhang = edKhachhang.getText().toString();
                        sodienthoaikhachhang = edSodienthoai.getText().toString();
                        ghichukhachhang = edGhichukhachhang.getText().toString();
                        ghichusanpham = edGhichudonhang.getText().toString();
                        nguoino = edNguoino.getText().toString();
                        maNhanvienban = session_ma1;
                        tenNhanvienban = session_username1;
                        setSoluong();
                        new SendRequest().execute();
                    } else
                        new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Chưa quét sản phẩm!");
                }
            }
        });
        btncheckname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Sales.this).show();
                else {
                    if (edKhachhang.getText().toString().trim().length() == 0){
                        new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Không được rỗng!!");
                    } else {
                        GetName();
                    }
                }
            }
        });

        btncheckphone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Sales.this).show();
                else {
                    if (edSodienthoai.getText().toString().trim().length() < 4){
                        new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Ít nhất phải 4 chữ số");
                    } else {
                        GetPhone();
                    }
                }
            }
        });
    }

    // TODO: 3/7/2018 check name
    private ArrayList<KhachHang> GetName() {
        final ArrayList<KhachHang> customer = new ArrayList<KhachHang>();
        final ArrayList<KhachHang> customerplus = new ArrayList<KhachHang>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Keys.CHECKKHACHHANG+"?tenkhachhang="+edKhachhang.getText().toString().trim(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                customer.add(new KhachHang(
                                        object.getString("tenkhachhang"),
                                        object.getString("sodienthoai")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int i = 0; i < customer.size(); i++) {
                            int sosanh = sosanhkh(customer.get(i).getPhone(), customerplus);
                            if (sosanh == -1){
                                customerplus.add(customer.get(i));
                            }
                        }
                        ShowListCustomer(customerplus);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Không kết nối được Server");
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        return customer;
    }

    private int sosanhkh(String phone, ArrayList<KhachHang> customerplus) {
        int result = -1;
        if (customerplus.size() > 0){
            for (int i = 0; i < customerplus.size(); i++) {
                if (phone.equals(customerplus.get(i).getPhone())){
                    result = i;
                }
            }
        }
        return result;
    }

    // TODO: 3/7/2018 check phone
    private ArrayList<KhachHang> GetPhone() {
        final ArrayList<KhachHang> customer = new ArrayList<KhachHang>();
        final ArrayList<KhachHang> customerplus = new ArrayList<KhachHang>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, Keys.CHECKKHACHHANG+"?sodienthoai="+edSodienthoai.getText().toString().trim(), null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                customer.add(new KhachHang(
                                        object.getString("tenkhachhang"),
                                        object.getString("sodienthoai")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        for (int i = 0; i < customer.size(); i++) {
                            int sosanh = sosanhkh(customer.get(i).getPhone(), customerplus);
                            if (sosanh == -1){
                                customerplus.add(customer.get(i));
                            }
                        }
                        ShowListCustomer(customerplus);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Không kết nối được Server");
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        return customer;
    }


    private void ShowListCustomer(final ArrayList<KhachHang> customer) {
        Dialog dialog = null;
        dialog = new Dialog(Main_Sales.this);
        dialog.setContentView(R.layout.dialog_listcustomer);
        dialog_ListView = dialog.findViewById(R.id.lvcustomer);
        adapter_listCustomer = new Adapter_ListCustomer(this, R.layout.adapter_listcustomer, customer);
        dialog_ListView.setAdapter(adapter_listCustomer);
        final Dialog finalDialog = dialog;
        dialog_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                edKhachhang.setText(customer.get(position).getName());
                edSodienthoai.setText(customer.get(position).getPhone());
                finalDialog.dismiss();
            }});
        dialog.show();
    }

    // TODO: 2/27/2018 check Thanh toan
    private void doOnDifficultyLevelChanged(RadioGroup group, int checkedId) {
        int checkedRadioId = group.getCheckedRadioButtonId();
        if(checkedRadioId== R.id.rbTienmat) {
            lnThanhtoan.setVisibility(View.GONE);
            thanhtoan = "Tiền mặt";
        } else if(checkedRadioId== R.id.rbGhino ) {
            lnThanhtoan.setVisibility(View.VISIBLE);
            thanhtoan = "Ghi nợ";
        }
    }

    private void setSoluong() {
        ArrayList<String> stringsl = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); i++) {
        }
        for(int i = arrayList.size() - 1; i >= 0; i--) {
            int kksl = checksl(arrayList.get(i).getGio(), stringsl);
            if(kksl != -1){
                arrayList.remove(i);
            } else {
                stringsl.add(arrayList.get(i).getGio());
            }
        }
    }

    private int checksl(String gio, ArrayList<String> stringsl) {
        int dem = -1;
        if (stringsl.size() != 0){
            for (int i = 0; i < stringsl.size(); i++) {
                if (gio.equals(stringsl.get(i))){
                    dem = i;
                }
            }
        }
        return dem;
    }

    // TODO: 1/28/2018 getusser 
    public ArrayList<User> GetUser(String urlUser, final VolleyCallback callback) {
        final ArrayList<User> usernames = new ArrayList<User>();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, urlUser, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        usernames.add(new User(1, "Chọn", "Chọn", "Chọn", "Chọn", "Chọn", "Chọn", "Chọn", "Chọn", "Chọn", "Chọn","Chọn"));
                        for (int i = 0; i < response.length(); i++){
                            try {
                                JSONObject object = response.getJSONObject(i);
                                if (object.getInt("level") > Keys.LEVEL_QL && object.getInt("trangthai") == 1){
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
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        callback.onSuccess(usernames);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onSuccess(usernames);
                    }
                }
        );
        requestQueue.add(jsonArrayRequest);
        return usernames;
    }

    public interface VolleyCallback{
        void onSuccess(ArrayList<User> result);
    }

    private void scanSanpham() {
        lnHidden.setVisibility(View.VISIBLE);
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Quét mã code");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }

    private String setMoney(int amount){
        String number = NumberFormat.getNumberInstance(Locale.US).format(amount);
        String formatnumber = number.replace(",",".");
        return formatnumber+"đ";
    }

    // TODO: 1/27/2018 quét input
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null) {
            String scannedData = result.getContents();
            if (scannedData != null) {
                try{
                    StringTokenizer st = new StringTokenizer(scannedData, ";");
                    ma = st.nextToken();
                    ten = st.nextToken();
                    gio = Keys.getTimeNow();
                    baohanh = st.nextToken();
                    nguon = st.nextToken();
                    ngaynhap = st.nextToken();
                    von = st.nextToken();
                    gia = st.nextToken();
                    if (listChuongtrinh.size() > 0){
                        for (int i = 0; i < listChuongtrinh.size(); i++) {
                            if (ma.equals(listChuongtrinh.get(i).getMa())){
                                gia = listChuongtrinh.get(i).getGiaChuongtrinh();
                                edGhichudonhang.append(listChuongtrinh.get(i).getTenChuongtrinh());
                            }
                        }
                    }
                    if (Keys.checkMavach(ma, chinhanh)){
                        arrayList.add(new SanphamAmount(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia, 1+""));
                        int kiemtra = kiemtra(ma, arrayListAmount);
                        if (kiemtra == -1){
                            arrayListAmount.add(new SanphamAmount(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia, 1+""));
                        } else {
                            arrayListAmount.get(kiemtra).setSoluong(Integer.valueOf(arrayListAmount.get(kiemtra).getSoluong())+1+"");
                        }
                        Capnhatsoluong();
                        itemKM.clear();
                        for (int i = 0; i<quatang.size(); i++){
                            if (total >= quatang.get(i).getToPrice() && total < quatang.get(i).getFromPrice()){
                                itemKM.add(quatang.get(i).getTen());
                            }
                        }
                        if (itemKM.size() == 0){
                            rbTangqua.setEnabled(false);
                        } else {
                            rbTangqua.setEnabled(true);
                        }
                        rbTangqua.setChecked(false);
                        lvKhuyenmai.setAdapter(null);
                        adapter.notifyDataSetChanged();
                    } else {
                        new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Không phải mã của chi nhánh hiện tại!");
                    }
                }   catch (NoSuchElementException nse) {
                    new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Không đúng mã!");
                }
            }
        }
    }

    private int kiemtra(String ma, ArrayList<SanphamAmount> arrayList1) {
        int result = -1;
        for (int i =  0; i < arrayList1.size(); i++){
            if (arrayList1.get(i).getMa().equals(ma)) {
                result = i;
            }
        }
        return result;
    }

    public void clearsanpham(SanphamAmount sanpham, Integer integer) {
        showProgressDialog();
        if (integer > 0){ //tăng lên
            for (int i =  0; i < integer; i++){
                arrayList.add(new SanphamAmount(
                        Keys.getTimeNow()+i,
                        sanpham.getMa(),
                        sanpham.getTen(),
                        sanpham.getBaohanh(),
                        sanpham.getNguon(),
                        sanpham.getNgaynhap(),
                        sanpham.getVon(),
                        sanpham.getGiaban(),
                        1+""));
            }
        } else if (integer < 0) {
            integer = integer*(-1);
            ArrayList<SanphamAmount> listso =new ArrayList<>();
            for (int i =  0; i < arrayList.size(); i++){
                if (arrayList.get(i).getMa().equals(sanpham.getMa()) && integer != 0){
                    integer--;
                } else {
                    listso.add(new SanphamAmount(
                            Keys.getTimeNow()+i,
                            arrayList.get(i).getMa(),
                            arrayList.get(i).getTen(),
                            arrayList.get(i).getBaohanh(),
                            arrayList.get(i).getNguon(),
                            arrayList.get(i).getNgaynhap(),
                            arrayList.get(i).getVon(),
                            arrayList.get(i).getGiaban(),
                            1+""));
                }
            }
            arrayList.clear();
            arrayList.addAll(listso);
        }
        dismissProgressDialog();
    }

    // TODO: 1/28/2018 cập nhật số lượng 
    public void Capnhatsoluong() {
        showProgressDialog();
        total = 0;
        for (int i = 0; i < arrayListAmount.size(); i++){
            total = total + (Integer.parseInt(arrayListAmount.get(i).getGiaban()) * (Integer.parseInt(arrayListAmount.get(i).getSoluong())));
        }
        itemKM.clear();
        for (int i = 0; i<quatang.size(); i++){
            if (total >= quatang.get(i).getToPrice() && total < quatang.get(i).getFromPrice()){
                itemKM.add(quatang.get(i).getTen());
            }
        }
        if (itemKM.size() == 0){
            rbTangqua.setEnabled(false);
        } else {
            rbTangqua.setEnabled(true);
        }
        tvTongdonhang.setText(setMoney(total));
        rbGiamgia.setChecked(false);
        lnGiamgia.setVisibility(View.GONE);
        ln1.setVisibility(View.GONE);
        ln2.setVisibility(View.GONE);
        giatriMagiamgia = "0";
        maGiamgia = "";
        edGiamgia.setText("");
        dismissProgressDialog();
    }

    // TODO: 1/28/2018 delete 
    public void DeleteSP(final String msp){
        if (arrayListAmount.size() != 1){
            showProgressDialog();
            for (int i =  0; i < arrayListAmount.size(); i++){
                if (arrayListAmount.get(i).getMa() == msp) {
                    arrayListAmount.remove(i);
                }
            }
            ArrayList<SanphamAmount> tam = new ArrayList<>();
            for (int i =  0; i < arrayList.size(); i++){
                if (!arrayList.get(i).getMa().equals(msp)) {
                    tam.add(arrayList.get(i));
                }
            }
            arrayList.clear();
            arrayList.addAll(tam);
            Capnhatsoluong();
            itemKM.clear();
            for (int i = 0; i<quatang.size(); i++){
                if (total >= quatang.get(i).getToPrice() && total < quatang.get(i).getFromPrice()){
                    itemKM.add(quatang.get(i).getTen());
                }
            }
            if (itemKM.size() == 0){
                rbTangqua.setEnabled(false);
            } else {
                rbTangqua.setEnabled(true);
            }
            rbTangqua.setChecked(false);
            lvKhuyenmai.setAdapter(null);
            adapter.notifyDataSetChanged();
            dismissProgressDialog();
        } else {
            new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Yêu cầu tạo đơn hàng mới!");
        }
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = dateFormat.format(new Date());
        return date;
    }

    // TODO: 1/28/2018 progress start 
    public class SendRequest extends AsyncTask<Void, Integer, String> {

        @Override
        protected void onPreExecute() {
            nPro = new ProgressDialog(Main_Sales.this);
            nPro.setTitle("Đang tạo đơn hàng!");
            nPro.setMax(arrayList.size());
            nPro.setCancelable(false);
            nPro.setProgress(0);
            nPro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            nPro.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            int progress = 1;
            int i = 0;
            if (arrayQuatang.size() > 0){
                for (int i1 = 0; i1 < arrayQuatang.size(); i1++) {
                    putDataKM(i1);
                    addKMWeb(i1);
                }
            }
            while (progress <= arrayList.size()){
                putData(progress-1);
                addOrderWeb(progress-1);
                publishProgress(progress);
                progress++;
            }
//            if (!maGiamgia.equals("")){
//                new DeleteMGG().execute();
//                DeleteMGGWeb();
//            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            int prog = values[0];
            nPro.setProgress(prog);
            nPro.setMessage("Đã tải lên "+prog+" trên "+arrayList.size()+" sản phẩm...");
        }

        @Override
        protected void onPostExecute(String s) {
            nPro.hide();
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

    // TODO: 1/28/2018 put sheet excel 
    public String putData(int j){
        try {
            // Link Script
            URL url = new URL(Keys.getScript_Banhang(chinhanh));

            // Load Json object
            JSONObject postDataParams = new JSONObject();

            postDataParams.put("salesMadonhang", "SA_"+madonhang);
            postDataParams.put("salesNgay", ngay);
            postDataParams.put("salesCa", ca);
            postDataParams.put("salesGio", arrayList.get(j).getGio());
            postDataParams.put("salesChinhanh", chinhanh);
            postDataParams.put("SalesTennhanvien", session_username);
            postDataParams.put("salesManhanvien", session_ma);
            postDataParams.put("salesMasanpham", arrayList.get(j).getMa());
            postDataParams.put("salesTensanpham", arrayList.get(j).getTen());
            postDataParams.put("salesBaohanhsanpham", arrayList.get(j).getBaohanh());
            postDataParams.put("salesNguonsanpham", arrayList.get(j).getNguon());
            postDataParams.put("salesNgaynhap", arrayList.get(j).getNgaynhap());
            postDataParams.put("salesVonsanpham", arrayList.get(j).getVon());
            postDataParams.put("salesGiasanpham", arrayList.get(j).getGiaban());
            postDataParams.put("salesGiamgia", giatriMagiamgia);
            postDataParams.put("salesGhichusanpham", ghichusanpham);
            postDataParams.put("salesTenkhachhang", tenkhachhang);
            postDataParams.put("salesSodienthoaikhachhang", sodienthoaikhachhang);
            postDataParams.put("salesGhichukhachhang", ghichukhachhang);
            postDataParams.put("salesTennhanvienbandum", tenNhanvienban);
            postDataParams.put("salesManhanvienbandum", maNhanvienban);
            postDataParams.put("salesThanhtoan", thanhtoan);
            postDataParams.put("salesNguoino", nguoino);
            j++;

            Log.e("params", postDataParams.toString());

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

    // TODO: 1/28/2018 get Số lượng 
    class GetListSoluong extends AsyncTask<Void, Integer, String> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            slPro = new ProgressDialog(Main_Sales.this);
            slPro.setTitle("Hãy chờ!");
            slPro.setCancelable(false);
            slPro.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            slPro.show();

        }

        @Nullable
        @Override
        protected String doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_LISTSOLUONG);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.LISTSOLUONG);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    listsoluong.add(object.getString("masanpham"));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                publishProgress(jIndex);
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
        protected void onProgressUpdate(Integer... values) {
            int prog = values[0];
            slPro.setMessage("Đang tải xuống "+prog+" sản phẩm...");
        }

        @Override
        protected void onPostExecute(String s) {
            adapter.notifyDataSetChanged();
            slPro.hide();
        }
    }

    // TODO: 1/28/2018 get Mã giảm giá 
    class GetDataGiamGia extends AsyncTask<Void, Void, Void> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            showProgressDialog();
        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_MAGIAMGIA);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.MAGIAMGIA);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    if (maGiamgia.equals(object.getString("maGiamgia")) && (object.getString("trangThai").equals("On"))){
                                        giatriMagiamgia = object.getString("giaTri");
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
            if(giatriMagiamgia.equals("") || giatriMagiamgia.equals("0")) {
                new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Mã không hợp lệ!");
                edGiamgia.setText("");
                giatriMagiamgia = "0";
                maGiamgia = "";
            } else {
                ln1.setVisibility(View.VISIBLE);
                ln2.setVisibility(View.VISIBLE);
            }
            tvgiatriMagiam.setText(Keys.setMoney(Integer.parseInt(giatriMagiamgia)));
            tvphaithu.setText(Keys.setMoney(total - Integer.parseInt(giatriMagiamgia)));
            dismissProgressDialog();
        }
    }

    // TODO: 1/28/2018 get List quà tặng 
    class GetDataQuaTang extends AsyncTask<Void, Void, Void> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_QUATANG);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.QUA_TANG);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    if (chinhanh.equals(object.getString("chinhanh"))){
                                        quatang.add(new Quatang(
                                                object.getString("msp"),
                                                object.getString("ten"),
                                                object.getString("baohanh"),
                                                object.getString("nguon"),
                                                object.getString("ngaynhap"),
                                                object.getInt("von"),
                                                object.getInt("giaban"),
                                                object.getInt("toPrice"),
                                                object.getInt("fromPrice")
                                        ));
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
            if(quatang.size() > 0) {
                setList(quatang);
            }
        }
    }
    private void setList(final ArrayList<Quatang> quatang) {
        this.quatang = quatang;
        checkedItem = new boolean[quatang.size()];

        rbTangqua.setOnCheckedChangeListener(
                new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        btn_scan_now.setEnabled(false);
                        btn_scan_now.setBackgroundColor(getResources().getColor(R.color.aaaaa));
                        if (isChecked) {
                            if (total != 0 ){
                                rbGiamgia.setChecked(false);
                                lnGiamgia.setVisibility(View.GONE);
                                lnTangqua.setVisibility(View.VISIBLE);
                                seletedItems.clear();
                                stringDica.clear();
                                arrayQuatang.clear();
                                quatangFilter.clear();
                                for (int i = 0; i < quatang.size(); i++) {
                                    if (quatang.get(i).getToPrice() <= total){
                                        quatangFilter.add(quatang.get(i));
                                    }
                                }
                                final String[] items = new String[quatangFilter.size()];
                                for (int i = 0; i < quatangFilter.size(); i++) {
                                    items[i] = String.valueOf(quatangFilter.get(i).getTen());
                                }
                                AlertDialog dialog = new AlertDialog.Builder(Main_Sales.this)
                                        .setTitle("Chọn quà tặng: ").setCancelable(false)
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
                                                if (seletedItems.size() > 0){
                                                    for (int i = 0; i < seletedItems.size(); i++) {
                                                        stringDica.add(items[(int) seletedItems.get(i)].toString());
                                                    }
                                                    for (int i = 0; i < stringDica.size(); i++) {
                                                        for (int i1 = 0; i1 < quatangFilter.size(); i1++) {
                                                            if (quatangFilter.get(i1).getTen().equals(stringDica.get(i))) {
                                                                arrayQuatang.add(quatangFilter.get(i1));
                                                            }
                                                        }
                                                    }
                                                    adapter2 = new Adapter_Quatang(Main_Sales.this, R.layout.adapter_quatang, arrayQuatang);
                                                    lvKhuyenmai.setAdapter(adapter2);
                                                }
                                            }
                                        }).setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int id) {
                                                arrayQuatang.clear();
                                                rbTangqua.setChecked(false);
                                                lnTangqua.setVisibility(View.GONE);
                                                dialog.dismiss();
                                            }
                                        }).create();
                                dialog.show();
                            } else {
                                rbTangqua.setChecked(false);
                                new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Chưa quét sản phẩm!!");
                            }
                        } else {
                            arrayQuatang.clear();
                            lnTangqua.setVisibility(View.GONE);
                        }
                    }
                });
    }

    // TODO: 2/2/2018 get chương trình
    class GetDataChuongtrinh extends AsyncTask<Void, Void, Void> {
        int jIndex;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Nullable
        @Override
        protected Void doInBackground(Void... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_CHUONGTRINH+"?cn="+chinhanh);
            try {
                if (jsonObject != null) {
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.CHUONG_TRINH);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {

                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    if (chinhanh.equals(object.getString("chinhanh"))){
                                        listChuongtrinh.add(new Chuongtrinh(
                                                object.getString("id"),
                                                object.getString("maCT"),
                                                object.getString("tenChuongtrinh"),
                                                object.getString("giaChuongtrinh"),
                                                object.getString("ngaybatdau"),
                                                object.getString("ngayketthuc"),
                                                object.getString("chinhanh"),
                                                object.getString("ma"),
                                                object.getString("ten"),
                                                object.getString("baohanh"),
                                                object.getString("nguon"),
                                                object.getString("ngaynhap"),
                                                object.getString("von"),
                                                object.getString("gia")
                                        ));
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
        }
    }

    // TODO: 1/28/2018 put server 
    public void addOrderWeb(final int j){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            if (j == (arrayList.size()-1)) {
                                //new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Thất bại, không kết nối được Server!!");
                            }
                        } else if (response.trim().equals("success")){
                            if (j == (arrayList.size()-1)){
                                new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Tạo đơn hàng thành công!!");
                                ResetActivity();
                            }
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                       new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_SALES_WEB);
                params.put("id", madonhang+arrayList.get(j).getGio());
                params.put("maDonhang", "SA_"+madonhang);
                params.put("ngay", ngay);
                params.put("calam", ca);
                params.put("gio", arrayList.get(j).getGio());
                params.put("chinhanh", chinhanh);
                params.put("maNhanvien", session_ma);
                params.put("tenNhanvien", session_username);
                params.put("maSanpham", arrayList.get(j).getMa());
                params.put("tenSanpham", arrayList.get(j).getTen());
                params.put("baohanhSanpham", arrayList.get(j).getBaohanh());
                params.put("nguonSanpham", arrayList.get(j).getNguon());
                params.put("ngaynhapSanpham", arrayList.get(j).getNgaynhap());
                params.put("vonSanpham", arrayList.get(j).getVon());
                params.put("giaSanpham", arrayList.get(j).getGiaban());
                params.put("giatriMagiamgia", giatriMagiamgia);
                params.put("ghichuSanpham", ghichusanpham);
                params.put("tenKhachhang", tenkhachhang);
                params.put("sodienthoaiKhachhang", sodienthoaikhachhang);
                params.put("ghichuKhachhang", ghichukhachhang);
                params.put("maNhanvienbandum", session_ma);
                params.put("tenNhanvienbandum", session_username);
                params.put("thanhtoan", thanhtoan);
                params.put("nguoino", nguoino);
                Log.e("qqq", "params: "+params.toString());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void showProgressDialog() {
        if (pDialog == null) {
            pDialog = new ProgressDialog(Main_Sales.this);
            pDialog.setTitle("Hãy chờ...");
            pDialog.setMessage("Dữ liệu đang được xử lý.");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
        }
        pDialog.show();
    }
    private void dismissProgressDialog() {
        if (pDialog != null && pDialog.isShowing()) {
            pDialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        super.onDestroy();
    }

    // TODO: 1/28/2018 xóa mã giảm giá 
    public class DeleteMGG extends AsyncTask<String, Void, String> {
        protected void onPreExecute(){
        }

        protected String doInBackground(String... params) {
            try{
                // Link Script
                URL url = new URL(Keys.SCRIPT_DE_MAGIAMGIA);

                // Load Json object
                JSONObject postDataParams = new JSONObject();

                postDataParams.put("maGiamgia", maGiamgia);

                Log.e("params",postDataParams.toString());

                // Kết nối HTTP
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(getPostDataString(postDataParams));

                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                // Nếu kết nối được
                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {

                        sb.append(line);
                        break;
                    }
                    //
                    in.close();
                    // Trả dữ liệu cho về để ghi lên Excel
                    return sb.toString();

                }
                else {
                    return new String("false : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }
        }

        @Override
        protected void onPostExecute(String result) {
        }
    }
    public void DeleteMGGWeb(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            AlertDialog.Builder dialog = new AlertDialog.Builder(Main_Sales.this);
                            dialog.setTitle("Thông báo");
                            dialog.setMessage("Không kết nối được với Server!");
                            dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                            dialog.show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlertDialog.Builder dialog = new AlertDialog.Builder(Main_Sales.this);
                        dialog.setTitle("Thông báo");
                        dialog.setMessage("Không kết nối được với Server! \n Mã lỗi: "+error);
                        dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.DELE_MAGIAMGIA_WEB);
                params.put("maGiamgia", maGiamgia);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    // TODO: 1/28/2018 put Khuyến mãi 
    public String putDataKM(int j){
        try {
            // Link Script
            URL url = new URL(Keys.SCRIPT_KHUYENMAI);

            // Load Json object
            JSONObject postDataParamsKM = new JSONObject();

            postDataParamsKM.put("salesMadonhang", "KM_"+madonhang);
            postDataParamsKM.put("salesNgay", ngay);
            postDataParamsKM.put("salesCa", ca);
            postDataParamsKM.put("salesChinhanh", chinhanh);
            postDataParamsKM.put("salesTennhanvien", session_username);
            postDataParamsKM.put("salesManhanvien", session_ma);
            postDataParamsKM.put("salesGiamgia", "SA_"+madonhang);
            postDataParamsKM.put("salesMasanpham", arrayQuatang.get(j).getMa());
            postDataParamsKM.put("salesTensanpham", arrayQuatang.get(j).getTen());
            postDataParamsKM.put("salesBaohanhsanpham", arrayQuatang.get(j).getBaohanh());
            postDataParamsKM.put("salesNguonsanpham", arrayQuatang.get(j).getNguon());
            postDataParamsKM.put("salesNgaynhap", arrayQuatang.get(j).getNgaynhap());
            postDataParamsKM.put("salesVonsanpham", arrayQuatang.get(j).getVon());
            postDataParamsKM.put("salesGiasanpham", arrayQuatang.get(j).getGia());
            postDataParamsKM.put("salesGhichusanpham", ghichusanpham);
            postDataParamsKM.put("salesTenkhachhang", tenkhachhang);
            postDataParamsKM.put("salesSodienthoaikhachhang", sodienthoaikhachhang);
            postDataParamsKM.put("salesGhichukhachhang", ghichukhachhang);
            j++;

            // Kết nối HTTP
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(15000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);
            conn.setDoOutput(true);

            OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(getPostDataString(postDataParamsKM));

            writer.flush();
            writer.close();
            os.close();
            int responseCode2 = conn.getResponseCode();
            if (responseCode2 == HttpsURLConnection.HTTP_OK) {
                BufferedReader in2 = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuffer sb2 = new StringBuffer("");
                String line2 = "";

                while ((line2 = in2.readLine()) != null) {

                    sb2.append(line2);
                    break;
                }
                in2.close();
                return sb2.toString();
            } else {
                return new String("");
            }
        } catch (Exception e) {
            return new String("");
        }
    }

    // TODO: 1/28/2018 add Khuyến mãi Web 
    public void addKMWeb(final int j){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Keys.LINK_WEB,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("error")){
                            new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Lỗi ");
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //  new CustomToast().Show_Toast(Main_Sales.this, findViewById(android.R.id.content), "Lỗi "+error);
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tacvu", Keys.ADD_KM_WEB);
                params.put("maKhuyenmai", "KM_"+madonhang);
                params.put("ngay", ngay);
                params.put("calam", gio);
                params.put("chinhanh", chinhanh);
                params.put("maNhanvien", session_ma);
                params.put("tenNhanvien", session_username);
                params.put("maDonhang", "SA_"+madonhang);
                params.put("maSanpham", arrayQuatang.get(j).getMa());
                params.put("tenSanpham", arrayQuatang.get(j).getTen());
                params.put("baohanhSanpham", arrayQuatang.get(j).getBaohanh());
                params.put("nguonSanpham", arrayQuatang.get(j).getNguon());
                params.put("ngaynhapSanpham", arrayQuatang.get(j).getNgaynhap());
                params.put("vonSanpham", arrayQuatang.get(j).getVon()+"");
                params.put("giaSanpham", arrayQuatang.get(j).getGia()+"");
                params.put("ghichuSanpham", ghichusanpham);
                params.put("tenKhachhang", tenkhachhang);
                params.put("sodienthoaiKhachhang", sodienthoaikhachhang);
                params.put("ghichuKhachhang", ghichukhachhang);
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

    public void ResetActivity(){
        Intent intentput = new Intent(Main_Sales.this, Main_Sales.class);
        intentput.putExtra("session_username", session_username1);
        intentput.putExtra("session_ma", session_ma1);
        startActivity(intentput);
        finish();
    }
}




