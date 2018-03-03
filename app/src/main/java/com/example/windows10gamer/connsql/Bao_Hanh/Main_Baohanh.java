package com.example.windows10gamer.connsql.Bao_Hanh;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Order;
import com.example.windows10gamer.connsql.Ban_Hang.Main_Information_Order;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Other.APIService_Sales;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.OrderList;
import com.example.windows10gamer.connsql.Other.RetrofitClient;
import com.example.windows10gamer.connsql.R;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Baohanh extends AppCompatActivity {
    private ListView listView;
    EditText edBenginBaohanh;
    EditText edmasanphamdo;
    CheckBox cbmasanphamdo;
    EditText edEndBaohanh, edSdtBaohanh, edTenkhachhang, edMasanpham;
    Button btnDateBaohanh, btnSdtBaohanh, btnScanBaohanh, btnTenvaMa;
    RadioGroup rgBaohanh;
    RadioButton rbSdtBaohanh;
    SharedPreferences shared;
    RadioButton rbDateBaohanh;
    RadioButton rbTenvaMa;
    RadioButton rbScanBaohanh;
    CheckBox cbCasang, cbCachieu;
    private ArrayList<Order> contactList, reportList;
    private Adapter_Order adapter;
    ArrayList<Order> temp;
    View view;
    TextView tvNoti;
    LinearLayout lnDateBaohanh, lnSdtBaohanh, lnScanBaohanh, lnTenvaMa;
    String dateBegin, dateEnd, dateCasang, dateCachieu, tenKhachhang, maSanpham;
    String ten, ma, nguon, ngaynhap, baohanh, gia, ngay, gio, von, chinhanh, checkdo, editdo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_baohanh);
        rgBaohanh        = findViewById(R.id.rgBaohanh);
        rbSdtBaohanh     = findViewById(R.id.rbSdtBaohanh);
        rbDateBaohanh    = findViewById(R.id.rbDateBaohanh);
        rbScanBaohanh    = findViewById(R.id.rbScanBaohanh);
        edBenginBaohanh  = findViewById(R.id.edBeginBaohanh);
        edEndBaohanh     = findViewById(R.id.edEndBaohanh);
        btnDateBaohanh   = findViewById(R.id.btnDateBaohanh);
        btnSdtBaohanh    = findViewById(R.id.btnSdtBaohanh);
        btnScanBaohanh   = findViewById(R.id.btnScanBaohanh);
        btnTenvaMa       = findViewById(R.id.btnTenvaMa);
        cbCasang         = findViewById(R.id.cbCasangBaohanh);
        cbCachieu        = findViewById(R.id.cbCachieuBaohanh);
        edSdtBaohanh     = findViewById(R.id.edSdtBaohanh);
        edTenkhachhang   = findViewById(R.id.edTenkhachhang);
        edMasanpham      = findViewById(R.id.edMasanpham);
        lnDateBaohanh    = findViewById(R.id.lnDateBaohanh);
        lnTenvaMa        = findViewById(R.id.lnTenvaMa);
        lnSdtBaohanh     = findViewById(R.id.lnSdtBaohanh);
        lnScanBaohanh    = findViewById(R.id.lnScanBaohanh);
        listView         = findViewById(R.id.lvBaohanh);
        tvNoti           = findViewById(R.id.tvNoti);
        edmasanphamdo    = findViewById(R.id.edmasanphamdo);
        cbmasanphamdo    = findViewById(R.id.cbmasanphamdo);
        cbCasang.setChecked(true);
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        lnSdtBaohanh.setVisibility(View.GONE);
        lnDateBaohanh.setVisibility(View.GONE);
        lnScanBaohanh.setVisibility(View.GONE);
        lnTenvaMa.setVisibility(View.GONE);
        listView.setVisibility(View.GONE);
        cbCachieu.setChecked(true);
        edBenginBaohanh.setText(Keys.getDateNow());
        edEndBaohanh.setText(Keys.getDateNow());
        contactList = new ArrayList<>();
        temp = new ArrayList<>();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Baohanh.this).show();
                else {
                    Intent intent = new Intent(Main_Baohanh.this, Main_Information_Order.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("position", position);
                    bundle.putString("keyMaOrder", temp.get(position).getMaDonhang());
                    bundle.putParcelableArrayList("arrayOrder", contactList);
                    intent.putExtra("DataOrder", bundle);
                    startActivity(intent);
                }
            }
        });
        edBenginBaohanh.setInputType(InputType.TYPE_NULL);
        edBenginBaohanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = java.util.Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Baohanh.this, android.R.style.Theme_Holo_Light_Panel, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        edBenginBaohanh.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });
        edEndBaohanh.setInputType(InputType.TYPE_NULL);
        edEndBaohanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Baohanh.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth,  new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        calendar.set(year, month, dayOfMonth);
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        edEndBaohanh.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },year, month, day);
                datePickerDialog.show();
            }
        });
        btnDateBaohanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Baohanh.this).show();
                else {

                        listView.setVisibility(View.GONE);
                        listView.setBackgroundResource(R.drawable.list_border);
                        dateBegin = String.valueOf(edBenginBaohanh.getText());
                        dateEnd = String.valueOf(edEndBaohanh.getText());
                        if (cbCasang.isChecked()) dateCasang = Keys.CA_SANG;
                        else dateCasang = "FALSE";
                        if (cbCachieu.isChecked()) dateCachieu = Keys.CA_CHIEU;
                        else dateCachieu = "FALSE";
                        if (cbmasanphamdo.isChecked()) checkdo = "YES";
                        else checkdo = "NO";
                        editdo = edmasanphamdo.getText().toString().trim();
                        tvNoti.setText("");
                        LoadJsonDATE(dateBegin, dateEnd, dateCasang, dateCachieu, editdo);
                }
            }
        });

        btnSdtBaohanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Baohanh.this).show();
                else {
                    listView.setVisibility(View.GONE);
                    listView.setBackgroundResource(R.drawable.list_border);
                    tvNoti.setText("");
                    LoadJsonSDT(edSdtBaohanh.getText().toString().trim());
                }
            }
        });

        btnTenvaMa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Baohanh.this).show();
                else {
                    listView.setVisibility(View.GONE);
                    listView.setBackgroundResource(R.drawable.list_border);
                    tvNoti.setText("");
                    LoadJsonTenvaMa(edTenkhachhang.getText().toString().trim(), edMasanpham.getText().toString().trim());
                }
            }
        });

        btnScanBaohanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Baohanh.this).show();
                else {
                    listView.setVisibility(View.GONE);
                    listView.setBackgroundResource(R.drawable.list_border);
                    tvNoti.setText("");
                    scanSanpham(v);
                }
            }
        });

        cbCasang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!cbCachieu.isChecked() && isChecked == false){
                    cbCachieu.setChecked(true);
                }
            }
        });
        cbCachieu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!cbCasang.isChecked() && isChecked == false){
                    cbCasang.setChecked(true);
                }
            }
        });
        rgBaohanh.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                doOnDifficultyLevelChanged(group, checkedId);
            }
        });
    }

    private void doOnDifficultyLevelChanged(RadioGroup group, int checkedId) {
        int checkedRadioId = group.getCheckedRadioButtonId();
        if(checkedRadioId== R.id.rbSdtBaohanh) {
            listView.setAdapter(null);
            lnSdtBaohanh.setVisibility(View.VISIBLE);
            lnDateBaohanh.setVisibility(View.GONE);
            lnScanBaohanh.setVisibility(View.GONE);
            lnTenvaMa.setVisibility(View.GONE);
        } else if(checkedRadioId== R.id.rbDateBaohanh ) {
            listView.setAdapter(null);
            lnDateBaohanh.setVisibility(View.VISIBLE);
            lnSdtBaohanh.setVisibility(View.GONE);
            lnScanBaohanh.setVisibility(View.GONE);
            lnTenvaMa.setVisibility(View.GONE);
        } else if(checkedRadioId== R.id.rbScanBaohanh ) {
            listView.setAdapter(null);
            lnScanBaohanh.setVisibility(View.VISIBLE);
            lnSdtBaohanh.setVisibility(View.GONE);
            lnTenvaMa.setVisibility(View.GONE);
            lnDateBaohanh.setVisibility(View.GONE);
        } else if(checkedRadioId== R.id.rbTenvaMa ) {
            listView.setAdapter(null);
            lnScanBaohanh.setVisibility(View.GONE);
            lnSdtBaohanh.setVisibility(View.GONE);
            lnTenvaMa.setVisibility(View.VISIBLE);
            lnDateBaohanh.setVisibility(View.GONE);
        }
    }

    public void getList(ArrayList<Order> List){
        this.reportList = List;
    }

    public void LoadJsonDATE(final String loadBegin, final String loadEnd, final String loadCasang, final String loadCachieu, final String editdo) {
        if (Connect_Internet.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(Main_Baohanh.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();
            Call<OrderList> call;
            APIService_Sales api = RetrofitClient.getApiService();
            if (edmasanphamdo.getText().toString().trim().equals("")){
                call = api.getOrder(loadBegin, loadEnd);
            } else {
                if (checkdo.equals("NO")) {
                    call = api.getBaohanhCo(loadBegin, loadEnd, editdo, "NO");
                } else {
                    call = api.getBaohanhKo(editdo, "YES");
                }
            }
            call.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                    dialog.dismiss();
                    ArrayList<Order> orignal = new ArrayList<Order>();
                    if(response.isSuccessful()) {
                        orignal = response.body().getContacts();
                        contactList.clear();
                        temp.clear();
                        for (int i = 0; i < orignal.size(); i++) {
                            if (loadCasang.equals(orignal.get(i).getCalam())) {
                                contactList.add(orignal.get(i));
                            }
                            if (loadCachieu.equals(orignal.get(i).getCalam())) {
                                contactList.add(orignal.get(i));
                            }
                        }
                        for (int j = 0; j < contactList.size(); j++) {
                            if (checkdo.equals("NO")) {
                                if (sosanhBaohanh(temp, contactList.get(j).getMaDonhang()) == -1) {
                                    temp.add(contactList.get(j));
                                }
                            } else {
                                temp.add(contactList.get(j));
                            }
                        }
                        getList(contactList);
                        if (temp.size() == 0){
                            tvNoti.setText("Không tìm thấy!");
                        }
                        if (temp.size() == 1){
                            Intent intent = new Intent(Main_Baohanh.this, Main_Information_Order.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("position", 0);
                            bundle.putString("keyMaOrder", temp.get(0).getMaDonhang());
                            bundle.putParcelableArrayList("arrayOrder", temp);
                            intent.putExtra("DataOrder", bundle);
                            startActivity(intent);
                        } else {
                            adapter = new Adapter_Order(Main_Baohanh.this, temp);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            if (temp.size() > 1){
                                listView.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        new CustomToast().Show_Toast(Main_Baohanh.this, findViewById(android.R.id.content), "Không có response!");
                    }
                }

                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            new CustomToast().Show_Toast(Main_Baohanh.this,findViewById(android.R.id.content), "Không có Internet!");
        }
    }

    public void LoadJsonSDT(final String sdt) {
        if (Connect_Internet.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(Main_Baohanh.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();

            APIService_Sales api = RetrofitClient.getApiService();

            Call<OrderList> call = api.getAllProduct();

            call.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                    dialog.dismiss();
                    ArrayList<Order> orignal = new ArrayList<Order>();
                    if(response.isSuccessful()) {
                        orignal = response.body().getContacts();
                        contactList.clear();
                        temp.clear();
                        for (int i = 0; i < orignal.size(); i++) {
                            if(sdt.equals(orignal.get(i).getSodienthoaiKhachhang())) {
                                contactList.add(orignal.get(i));
                            }
                        }
                        for (int j = 0; j < contactList.size(); j++) {
                            if (contactList.get(j).getChinhanh().equals(chinhanh)){
                                if (sosanhBaohanh(temp, contactList.get(j).getMaDonhang()) == -1){
                                    temp.add(contactList.get(j));
                                }
                            }
                        }
                        getList(contactList);
                        if (temp.size() == 0){
                            tvNoti.setText("Không tìm thấy!");
                        }
                        if (temp.size() == 1){
                            Intent intent = new Intent(Main_Baohanh.this, Main_Information_Order.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("position", 0);
                            bundle.putString("keyMaOrder", temp.get(0).getMaDonhang());
                            bundle.putParcelableArrayList("arrayOrder", contactList);
                            intent.putExtra("DataOrder", bundle);
                            startActivity(intent);
                        } else {
                            adapter = new Adapter_Order(Main_Baohanh.this, temp);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            if (temp.size() > 1){
                                listView.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        new CustomToast().Show_Toast(Main_Baohanh.this, findViewById(android.R.id.content), "Không có response!");
                    }
                }

                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            new CustomToast().Show_Toast(Main_Baohanh.this,findViewById(android.R.id.content), "Không có Internet!");
        }
    }

    public void LoadJsonTenvaMa(final String tenKhachhang, final String maSanpham) {
        if (Connect_Internet.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(Main_Baohanh.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();

            APIService_Sales api = RetrofitClient.getApiService();

            Call<OrderList> call = api.getTenvaMa(tenKhachhang, maSanpham);

            call.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                    dialog.dismiss();
                    ArrayList<Order> orignal = new ArrayList<Order>();
                    if(response.isSuccessful()) {
                        orignal = response.body().getContacts();
                        contactList.clear();
                        temp.clear();
                        for (int i = 0; i < orignal.size(); i++) {
                            contactList.add(orignal.get(i));
                        }
                        for (int j = 0; j < contactList.size(); j++) {
                            if (contactList.get(j).getChinhanh().equals(chinhanh)){
                                if (sosanhBaohanh(temp, contactList.get(j).getMaDonhang()) == -1){
                                    temp.add(contactList.get(j));
                                }
                            }
                        }
                        getList(contactList);
                        if (temp.size() == 0){
                            tvNoti.setText("Không tìm thấy!");
                        }
                        if (temp.size() == 1){
                            Intent intent = new Intent(Main_Baohanh.this, Main_Information_Order.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("position", 0);
                            bundle.putString("keyMaOrder", temp.get(0).getMaDonhang());
                            bundle.putParcelableArrayList("arrayOrder", contactList);
                            intent.putExtra("DataOrder", bundle);
                            startActivity(intent);
                        } else {
                            adapter = new Adapter_Order(Main_Baohanh.this, temp);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            if (temp.size() > 1){
                                listView.setVisibility(View.VISIBLE);
                            }
                        }
                    } else {
                        new CustomToast().Show_Toast(Main_Baohanh.this, findViewById(android.R.id.content), "Không có response!");
                    }
                }

                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            new CustomToast().Show_Toast(Main_Baohanh.this,findViewById(android.R.id.content), "Không có Internet!");
        }
    }

    public void LoadJsonScan(final String tonghop) {
        if (Connect_Internet.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(Main_Baohanh.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();

            APIService_Sales api = RetrofitClient.getApiService();

            Call<OrderList> call = api.getAllProduct();
            call.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                    dialog.dismiss();
                    ArrayList<Order> orignal = new ArrayList<Order>();
                    if(response.isSuccessful()) {
                        orignal = response.body().getContacts();
                        contactList.clear();
                        temp.clear();
                        for (int i = 0; i < orignal.size(); i++) {
                            if(tonghop.equals(orignal.get(i).getMaSanpham()+orignal.get(i).getTenSanpham()+orignal.get(i).getBaohanhSanpham()+orignal.get(i).getNguonSanpham()+orignal.get(i).getNgaynhapSanpham()+orignal.get(i).getVonSanpham()+orignal.get(i).getGiaSanpham()+"")) {
                                contactList.add(orignal.get(i));
                            }
                        }
                        for (int j = 0; j < contactList.size(); j++) {
                            if (contactList.get(j).getChinhanh().equals(chinhanh)){
                                if (sosanhBaohanh(temp, contactList.get(j).getMaDonhang()) == -1){
                                    temp.add(contactList.get(j));
                                }
                            }
                        }
                        getList(contactList);
                        if (temp.size() == 0){
                            tvNoti.setText("Không tìm thấy!");
                        }
                        if (temp.size() == 1){
                            Intent intent = new Intent(Main_Baohanh.this, Main_Information_Order.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("position", 0);
                            bundle.putString("keyMaOrder", temp.get(0).getMaDonhang());
                            bundle.putParcelableArrayList("arrayOrder", contactList);
                            intent.putExtra("DataOrder", bundle);
                            startActivity(intent);
                        } else {
                            adapter = new Adapter_Order(Main_Baohanh.this, temp);
                            listView.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            if (temp.size() > 1){
                                listView.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            new CustomToast().Show_Toast(Main_Baohanh.this,findViewById(android.R.id.content), "Không có Internet!");
        }
    }

    private int sosanhBaohanh(ArrayList<Order> order_h, String orderName) {
        int result = -1;
        if (order_h.size() != 0){
            for (int i = 0; i < order_h.size(); i++){
                if (order_h.get(i).getMaDonhang().equals(orderName)){
                    result = i;
                }
            }
        }
        return result;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result!=null) {
            String scannedData = result.getContents();
            if (scannedData != null) {
                try{
                    StringTokenizer st = new StringTokenizer(scannedData, ";");
                    ma = st.nextToken();
                    ten = st.nextToken();
                    baohanh = st.nextToken();
                    nguon = st.nextToken();
                    ngaynhap = st.nextToken();
                    von = st.nextToken();
                    gia = st.nextToken();
                    String tonghop = ma+ten+baohanh+nguon+ngaynhap+von+gia;
                    LoadJsonScan(tonghop);
                }   catch (NoSuchElementException nse) {
                    new CustomToast().Show_Toast(Main_Baohanh.this, findViewById(android.R.id.content), "Lỗi định dạng nhãn");
                }
            }
        }
    }

    public void scanSanpham(View view){
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Quét mã code");
        integrator.setOrientationLocked(false);
        integrator.setBeepEnabled(true);
        integrator.initiateScan();
    }
}