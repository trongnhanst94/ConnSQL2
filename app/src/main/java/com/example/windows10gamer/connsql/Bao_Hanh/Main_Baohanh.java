package com.example.windows10gamer.connsql.Bao_Hanh;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
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
import com.example.windows10gamer.connsql.Main_Information_Order;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Baohanh extends AppCompatActivity {
    private ListView listView;
    EditText edBenginBaohanh;
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
    String ten, ma, nguon, ngaynhap, baohanh, gia, ngay, gio, von, chinhanh;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_baohanh);
        rgBaohanh        = (RadioGroup) findViewById(R.id.rgBaohanh);
        rbSdtBaohanh     = (RadioButton) findViewById(R.id.rbSdtBaohanh);
        rbDateBaohanh    = (RadioButton) findViewById(R.id.rbDateBaohanh);
        rbScanBaohanh    = (RadioButton) findViewById(R.id.rbScanBaohanh);
        edBenginBaohanh  = (EditText) findViewById(R.id.edBeginBaohanh);
        edEndBaohanh     = (EditText) findViewById(R.id.edEndBaohanh);
        btnDateBaohanh   = (Button) findViewById(R.id.btnDateBaohanh);
        btnSdtBaohanh    = (Button) findViewById(R.id.btnSdtBaohanh);
        btnScanBaohanh   = (Button) findViewById(R.id.btnScanBaohanh);
        btnTenvaMa       = (Button) findViewById(R.id.btnTenvaMa);
        cbCasang         = (CheckBox) findViewById(R.id.cbCasangBaohanh);
        cbCachieu        = (CheckBox) findViewById(R.id.cbCachieuBaohanh);
        edSdtBaohanh     =  (EditText) findViewById(R.id.edSdtBaohanh);
        edTenkhachhang   =  (EditText) findViewById(R.id.edTenkhachhang);
        edMasanpham      =  (EditText) findViewById(R.id.edMasanpham);
        lnDateBaohanh    = (LinearLayout) findViewById(R.id.lnDateBaohanh);
        lnTenvaMa        = (LinearLayout) findViewById(R.id.lnTenvaMa);
        lnSdtBaohanh     = (LinearLayout) findViewById(R.id.lnSdtBaohanh);
        lnScanBaohanh    = (LinearLayout) findViewById(R.id.lnScanBaohanh);
        listView         = (ListView) findViewById(R.id.lvBaohanh);
        tvNoti           = (TextView) findViewById(R.id.tvNoti);
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Baohanh.this, new DatePickerDialog.OnDateSetListener() {
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
                DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Baohanh.this, new DatePickerDialog.OnDateSetListener() {
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
                    if (cbCasang.isChecked()) dateCasang = "Ca sáng";
                    else dateCasang = "FALSE";
                    if (cbCachieu.isChecked()) dateCachieu = "Ca chiều";
                    else dateCachieu = "FALSE";
                    tvNoti.setText("");
                    LoadJsonDATE(dateBegin, dateEnd, dateCasang, dateCachieu);
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
                    Log.d("qqq", edTenkhachhang.getText().toString().trim() + edMasanpham.getText().toString().trim());
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

    public void LoadJsonDATE(final String loadBegin, final String loadEnd, final String loadCasang, final String loadCachieu) {
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
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Date hom = null; Date loadBegin1 = null; Date loadEnd1 = null;
                        try {
                            loadBegin1 = (Date) simpleDateFormat.parse(loadBegin);
                            loadEnd1   = (Date) simpleDateFormat.parse(loadEnd);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < orignal.size(); i++) {
                            try {
                                hom = (Date) simpleDateFormat.parse(orignal.get(i).getNgay());
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if(hom.compareTo(loadBegin1) >= 0  && hom.compareTo(loadEnd1) <= 0) {
                                if (loadCasang.equals(orignal.get(i).getCalam())) {
                                    contactList.add(orignal.get(i));
                                }
                                if (loadCachieu.equals(orignal.get(i).getCalam())) {
                                    contactList.add(orignal.get(i));
                                }
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
                        Log.d("qqq", orignal.size()+ " orignal");
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
        integrator.setBeepEnabled(false);
        integrator.initiateScan();
    }
}