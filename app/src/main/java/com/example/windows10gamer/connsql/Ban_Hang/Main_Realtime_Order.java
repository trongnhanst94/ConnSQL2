package com.example.windows10gamer.connsql.Ban_Hang;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Realtime_Order;
import com.example.windows10gamer.connsql.Adapter.Adapter_Top5;
import com.example.windows10gamer.connsql.Object.Customer;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Object.ReportSales;
import com.example.windows10gamer.connsql.Object.TimeSoluong;
import com.example.windows10gamer.connsql.Object.Top5;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.APIService_Sales;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.Mylistview;
import com.example.windows10gamer.connsql.Other.OrderList;
import com.example.windows10gamer.connsql.Other.RetrofitClient;
import com.example.windows10gamer.connsql.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class Main_Realtime_Order extends AppCompatActivity implements OnChartValueSelectedListener {

    private Mylistview listView;
    ListView lvtop5;
    ArrayList<Top5> top5 = new ArrayList<>();
    Adapter_Top5 adapter_top5;
    private View parentView;
    Button btnSearchOrder;
    private ArrayList<Order> contactList;
    private ArrayList<Order> contactList_plus = new ArrayList<>();
    private Adapter_Realtime_Order adapter;
    ArrayList<Order> temp;
    ArrayList<Order> temp_plus = new ArrayList<>();
    int doanhthuTotal = 0;
    ArrayList<ReportSales> reportSalesList = new ArrayList<>();
    String ma, ten;
    int doanhthu = 0, soKhachhang = 0, soSanpham = 0, dttkh = 0, dttsp = 0;
    TableLayout stk;
    SharedPreferences shared ;
    String chinhanh;
    TextView tvreload;
    final Handler handler = new Handler();
    Runnable runnable;
    BarChart chart ;
    PieChart tronkh, tronsp;
    Spinner spinner;
    int totalNow = 0;
    Button btnBieudo, btnDanhsach, btnChiso, btnrealtime;
    LinearLayout lnBieudo, lnDanhsach, lnChiso;
    TextView tvdoanhthu, tvsokhachhangmua, tvsosanphamban, tvdoanhthutrensanpham, tvdoanhthutrenkhachhang, tvnhanvien;
    private String daynow;
    EditText edrealtime;
    private int totalgia = 0;
    CheckBox rbsang, rbchieu;
    String caRealtime = "all";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_realtime_order);
        btnSearchOrder = findViewById(R.id.btnDateOrder);
        rbsang = findViewById(R.id.rbsang);
        rbchieu = findViewById(R.id.rbchieu);
        btnBieudo = findViewById(R.id.btnBieudo);
        tvnhanvien = findViewById(R.id.tvnhanvien);
        btnDanhsach = findViewById(R.id.btnDanhsach);
        btnChiso = findViewById(R.id.btnChiso);
        edrealtime = findViewById(R.id.edrealtime);
        btnrealtime = findViewById(R.id.btnrealtime);
        lnBieudo = findViewById(R.id.lnBieudo);
        lnDanhsach = findViewById(R.id.lnDanhsach);
        lnChiso = findViewById(R.id.lnChiso);
        lnBieudo.setVisibility(View.GONE);
        lnDanhsach.setVisibility(View.GONE);
        lnChiso.setVisibility(View.GONE);
        spinner = findViewById(R.id.spinRS);
        tvdoanhthu = findViewById(R.id.tvdoanhthu);
        tvsokhachhangmua = findViewById(R.id.tvsokhachhangmua);
        tvsosanphamban = findViewById(R.id.tvsosanphamban);
        tvdoanhthutrensanpham = findViewById(R.id.tvdoanhthutrensanpham);
        tvdoanhthutrenkhachhang = findViewById(R.id.tvdoanhthutrenkhachhang);
        contactList = new ArrayList<>();
        temp = new ArrayList<>();
        lvtop5 = findViewById(R.id.lvtop5);
        listView = findViewById(R.id.listView);
        tvreload = findViewById(R.id.tvreload);
        daynow = Keys.getDateNow();
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        if (Keys.getCalam(chinhanh).equals(Keys.CA_SANG)){
            rbsang.setChecked(true);
            caRealtime = Keys.CA_SANG;
        } else {
            rbchieu.setChecked(true);
            caRealtime = Keys.CA_CHIEU;
        }
        edrealtime.setInputType(InputType.TYPE_NULL);
        edrealtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = java.util.Calendar.getInstance();
                int day = calendar.get(Calendar.DATE);
                int month = calendar.get(Calendar.MONTH);
                int year = calendar.get(Calendar.YEAR);
                if (Build.VERSION.SDK_INT == 24) {
                    final Context contextThemeWrapper =
                            new ContextThemeWrapper(Main_Realtime_Order.this, android.R.style.Theme_Holo_Light_Dialog);
                    try {
                        DatePickerDialog datePickerDialog = new Keys.FixedHoloDatePickerDialog(contextThemeWrapper, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                calendar.set(year, month, dayOfMonth);
                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                                edrealtime.setText(simpleDateFormat.format(calendar.getTime()));
                            }
                        }, year, month, day);
                        datePickerDialog.show();
                    } catch ( Fragment.InstantiationException e) {
                        e.printStackTrace();
                    }
                } else {
                    DatePickerDialog datePickerDialog = new DatePickerDialog(Main_Realtime_Order.this, android.R.style.Theme_Holo_Light_Panel, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                            calendar.set(year, month, dayOfMonth);
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
                            daynow = simpleDateFormat.format(calendar.getTime());
                            edrealtime.setText("Ngày: " + simpleDateFormat.format(calendar.getTime()));
                        }
                    }, year, month, day);
                    datePickerDialog.show();
                }
            }
        });
        rbsang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (rbchieu.isChecked()){
                        caRealtime = "all";
                    } else {
                        caRealtime = Keys.CA_SANG;
                    }
                } else {
                    if (rbchieu.isChecked()){
                        caRealtime = Keys.CA_CHIEU;
                    } else {
                        caRealtime = Keys.CA_CHIEU;
                        rbchieu.setChecked(true);
                    }
                }
            }
        });

        rbchieu.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    if (rbsang.isChecked()){
                        caRealtime = "all";
                    } else {
                        caRealtime = Keys.CA_CHIEU;
                    }
                } else {
                    if (rbsang.isChecked()){
                        caRealtime = Keys.CA_SANG;
                    } else {
                        caRealtime = Keys.CA_SANG;
                        rbsang.setChecked(true);
                    }
                }
            }
        });
        btnrealtime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Realtime_Order.this).show();
                else {
                    if (edrealtime.toString().trim().equals("")){
                        new CustomToast().Show_Toast(Main_Realtime_Order.this, findViewById(android.R.id.content), "Chưa nhập ngày!");
                    } else {
                        if (daynow.equals(Keys.getDateNow())){
                            handler.postDelayed(runnable, 1000);
                        } else {
                            handler.removeCallbacks(runnable);
                        }
                        LoadJson(daynow);
                    }
                }
            }
        });
        final int[] a = {2}, b = {2}, c = {2};
        btnBieudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (a[0] == 1){lnBieudo.setVisibility(View.GONE);a[0] = 2;} else {lnBieudo.setVisibility(View.VISIBLE);a[0] = 1;}
            }
        });
        btnChiso.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (b[0] == 1){lnChiso.setVisibility(View.GONE);b[0] = 2;} else {lnChiso.setVisibility(View.VISIBLE);b[0] = 1;}
            }
        });
        btnDanhsach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c[0] == 1){lnDanhsach.setVisibility(View.GONE);c[0] = 2;} else {lnDanhsach.setVisibility(View.VISIBLE);c[0] = 1;}
            }
        });
        if(!Connect_Internet.checkConnection(getApplicationContext()))
            Connect_Internet.buildDialog(Main_Realtime_Order.this).show();
        else {
            LoadJson(daynow);
        }
            runnable = new Runnable() {
                public void run() {
                    if(!Connect_Internet.checkConnection(getApplicationContext()))
                        Connect_Internet.buildDialog(Main_Realtime_Order.this).show();
                    else { LoadJson(daynow);}
                    handler.postDelayed(this, Keys.LOAD_REALTIME);
                }
            };
            handler.postDelayed(runnable, 1000);
        chart = findViewById(R.id.bieudobanhang);
        tronkh = findViewById(R.id.tronkhachhang);
        tronkh.setUsePercentValues(true);
    }

    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

        if (e == null)
            return;
    }

    @Override
    public void onNothingSelected() {}

    @Override
    protected void onStop() {
        super.onStop();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        handler.postDelayed(runnable, 1000);
    }

    private void sortList(ArrayList<Order> list) {
        Collections.sort(list, new Comparator<Order>() {
            @Override
            public int compare(Order lhs, Order rhs) {
                return rhs.getMaDonhang().compareTo(lhs.getMaDonhang());
            }
        });
    }

    private void sortPrice(ArrayList<Order> list) {
        Collections.sort(list, new Comparator<Order>() {
            @Override
            public int compare(Order lhs, Order rhs) {
                return Integer.signum(Integer.valueOf(rhs.getGiaSanpham()) - Integer.valueOf(lhs.getGiaSanpham()));
            }
        });
    }

    public void LoadJson(String ngay) {
        if (Connect_Internet.checkConnection(getApplicationContext())) {
            APIService_Sales api = RetrofitClient.getApiService();
            Call<OrderList> call = api.getDoanhthu(chinhanh, ngay, caRealtime);
            call.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                    ArrayList<Order> orignal = new ArrayList<Order>();
                    if(response.isSuccessful()) {
                        orignal = response.body().getContacts();
                        contactList.clear();
                        temp.clear();
                        totalgia = 0;
                        int dem = 0;
                        if (orignal.size() > 0){
                            for (int i = 0; i < orignal.size(); i++) {
                                contactList.add(orignal.get(i));
                                if (i != 0 ){
                                    if (contactList.get(i).getMaDonhang().equals(contactList.get(i-1).getMaDonhang())){
                                        if (dem == 0) {
                                            contactList.get(i).setId(0+"");
                                            dem = 0;
                                        } else {
                                            contactList.get(i).setId(1+"");
                                            dem = 1;
                                        }
                                    } else {
                                        if (dem == 1) {
                                            contactList.get(i).setId(0+"");
                                            dem = 0;
                                        } else {
                                            contactList.get(i).setId(1+"");
                                            dem = 1;
                                        }
                                    }
                                }
                            }
                            contactList.get(0).setId(0+"");
                            for (int j = 0; j < contactList.size(); j++) {
                                if (contactList.get(j).getChinhanh().equals(chinhanh)){
                                    temp.add(contactList.get(j));
                                    totalgia += Integer.valueOf(contactList.get(j).getGiaSanpham());
                                }
                            }
                            if (temp.size() == 0){
                                new CustomToast().Show_Toast(Main_Realtime_Order.this, findViewById(android.R.id.content), "Không có đơn hàng!");
                            }
                            if (totalgia != totalNow) {
                                top5.clear();
                                temp_plus.clear();
                                contactList_plus.clear();
                                temp_plus.addAll(temp);
                                contactList_plus.addAll(contactList);
                                totalNow = totalgia;
                                sortList(temp);
                                xuly(temp);
                                adapter = new Adapter_Realtime_Order(Main_Realtime_Order.this, temp_plus);
                                listView.setAdapter(adapter);
                                adapter_top5 = new Adapter_Top5(Main_Realtime_Order.this, top5);
                                lvtop5.setAdapter(adapter_top5);
                                ArrayList<Order> settop = new ArrayList<>();
                                settop.addAll(temp_plus);
                                sortPrice(settop);
                                if (settop.size() > 9){
                                    for (int i = 0; i < 9; i++) {
                                        top5.add(new Top5(i+1+"", settop.get(i).getMaDonhang(), settop.get(i).getTenSanpham(), settop.get(i).getTenNhanvien(), settop.get(i).getGiaSanpham()));
                                    }
                                }
                                adapter_top5.notifyDataSetChanged();
                                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        if(!Connect_Internet.checkConnection(getApplicationContext()))
                                            Connect_Internet.buildDialog(Main_Realtime_Order.this).show();
                                        else {
                                            if (contactList.size() > 0){
                                                Intent intent = new Intent(Main_Realtime_Order.this, Main_Information_Order.class);
                                                Bundle bundle = new Bundle();
                                                bundle.putInt("position", position);
                                                bundle.putString("keyMaOrder", temp_plus.get(position).getMaDonhang());
                                                bundle.putParcelableArrayList("arrayOrder", contactList_plus);
                                                intent.putExtra("DataOrder", bundle);
                                                startActivity(intent);
                                            } else {
                                                new CustomToast().Show_Toast(Main_Realtime_Order.this, findViewById(android.R.id.content), "Không có dữ liệu!");
                                            }
                                        }
                                    }
                                });
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }

                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {
                }
            });

        } else {
            new CustomToast().Show_Toast(Main_Realtime_Order.this,parentView, "Không có Internet!");
        }
    }

    private void xuly(ArrayList<Order> temp) {
        if (stk != null){
            stk.removeAllViews();
            reportSalesList.clear();
            doanhthuTotal = 0;
        }
        this.temp = temp;
        for (int i = 0; i < temp.size(); i++){
            doanhthuTotal = doanhthuTotal + Integer.parseInt(temp.get(i).getGiaSanpham());
        }
        tvdoanhthu.setText( Keys.setMoney(doanhthuTotal));
        ArrayList<Customer> khachhang = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++){
            int result = Keys.sosanhCustomer(khachhang, temp.get(i).getTenKhachhang(), temp.get(i).getMaDonhang());
            if (result == -1){
                khachhang.add(new Customer( temp.get(i).getMaDonhang()+"KH", temp.get(i).getTenKhachhang(), temp.get(i).getSodienthoaiKhachhang(), temp.get(i).getGhichuKhachhang(), temp.get(i).getMaDonhang(), temp.get(i).getMaNhanvien(), temp.get(i).getTenNhanvien()));
            }
        }
        tvsokhachhangmua.setText(khachhang.size()+"");
        tvsosanphamban.setText(temp.size()+"");
        tvdoanhthutrensanpham.setText(Keys.setMoney(doanhthuTotal/temp.size()));
        tvdoanhthutrenkhachhang.setText(Keys.setMoney(doanhthuTotal/khachhang.size()));
        ArrayList<User> nhanVienList = new ArrayList<>();
        for (int i = 0; i < temp.size(); i++){
            int result = Keys.sosanhUser(nhanVienList, temp.get(i).getMaNhanvien(), temp.get(i).getTenNhanvien());
            if (result == -1){
                nhanVienList.add(new User(temp.get(i).getMaNhanvien(), temp.get(i).getTenNhanvien()));
            }
        }
        setbieudotron(nhanVienList, temp);
        setbieudocot(temp);
        tvnhanvien.setText(nhanVienList.size()+"");
        for (int i = 0; i < nhanVienList.size(); i++){
            ma = nhanVienList.get(i).getMa();
            ten = nhanVienList.get(i).getShortName();
            doanhthu    = Keys.DoanhthuCount(temp, nhanVienList.get(i).getMa());
            soKhachhang = Keys.SoKhachhang(khachhang, nhanVienList.get(i).getMa());
            soSanpham   = Keys.SoSanpham(temp, nhanVienList.get(i).getMa());
            dttkh       = doanhthu/soKhachhang;
            dttsp       = doanhthu/soSanpham;
            reportSalesList.add(new ReportSales(ma, ten, doanhthu, soKhachhang, soSanpham,soSanpham/soKhachhang, dttkh, dttsp));
        }
        init(reportSalesList);
    }

    private void sortTime(ArrayList<Integer> list) {
        Collections.sort(list, new Comparator<Integer>() {
            @Override
            public int compare(Integer lhs, Integer rhs) {
                return Integer.signum(lhs - rhs);
            }
        });
    }

    private void setbieudotron(ArrayList<User> nhanVienList, ArrayList<Order> temp) {
        ArrayList<Entry> yvalues = new ArrayList<Entry>();
        PieDataSet dataSet = new PieDataSet(yvalues, "");
        ArrayList<String> xVals = new ArrayList<String>();
        yvalues.clear();
        xVals.clear();
        for (int i = 0; i < nhanVienList.size(); i++) {
            yvalues.add(new Entry(Keys.DoanhthuCount(temp, nhanVienList.get(i).getMa()), i));
            xVals.add(nhanVienList.get(i).getShortName());
        }
        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        tronkh.setData(data);
        tronkh.invalidate();
        tronkh.setDescription("");
        tronkh.setDrawHoleEnabled(true);
        tronkh.getLegend().setEnabled(false);
        dataSet.setColors(Keys.MY_COLORS);
        data.setValueTextSize(5f);
        data.setValueTextColor(Color.WHITE);
        tronkh.setOnChartValueSelectedListener(this);
    }

    private void setbieudocot(ArrayList<Order> temp) {
        ArrayList<Integer> gioKhach = new ArrayList<>();
        ArrayList<BarEntry> BARENTRY = new ArrayList<>();
        ArrayList<String> BarEntryLabels = new ArrayList<>();
        ArrayList<Order> maTam = new ArrayList<>();
        ArrayList<TimeSoluong> timeSoluongs = new ArrayList<>();
        gioKhach.clear(); BARENTRY.clear(); BarEntryLabels.clear(); timeSoluongs.clear();maTam.clear();
        for (int i = 0; i < temp.size(); i++) {
            int so = gettheoma(temp.get(i).getMaDonhang(), maTam);
            if (so == -1){
                gioKhach.add(Keys.trimgio(temp.get(i).getGio()));
                maTam.add(temp.get(i));
            }
        }
        sortTime(gioKhach);
        for (int i = 0; i < gioKhach.size(); i++) {
            int dem = getsoluong(gioKhach.get(i), timeSoluongs);
            if (dem == -1){
                timeSoluongs.add(new TimeSoluong(gioKhach.get(i), 1));
            } else {
                timeSoluongs.set(dem, new TimeSoluong(timeSoluongs.get(dem).getGio(), timeSoluongs.get(dem).getSoluong()+1));
            }
        }
        for (int i = 0; i < timeSoluongs.size(); i++) {
            BARENTRY.add(new BarEntry(timeSoluongs.get(i).getSoluong(), i));
            BarEntryLabels.add(timeSoluongs.get(i).getGio()+"");
        }
        int[] colors = {Color.parseColor("#FF8800")};
        BarDataSet barDataSet = new BarDataSet(BARENTRY, "Khách hàng");
        chart.setDescription("");
        chart.invalidate();
        barDataSet.setColors(colors);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(barDataSet);
        BarData Data = new BarData(BarEntryLabels, dataSets);
        chart.setData(Data);
    }

    private int gettheoma(String maDonhang, ArrayList<Order> maTam) {
        int dem = -1;
        if (maTam.size() != 0){
            for (int i = 0; i < maTam.size(); i++) {
                if (maDonhang.equals(maTam.get(i).getMaDonhang())){
                    dem = i;
                    break;
                }
            }
        }
        return dem;
    }

    private int getsoluong(Integer integer, ArrayList<TimeSoluong> timeSoluong) {
        int dem = -1;
        if (timeSoluong.size() != 0){
            for (int i = 0; i < timeSoluong.size(); i++) {
                if (integer == timeSoluong.get(i).getGio()){
                    dem = i;
                    break;
                }
            }
        }
        return dem;
    }

    public void init(ArrayList<ReportSales> list) {
        DecimalFormat df = new DecimalFormat("0.0");
        stk = findViewById(R.id.tbReport);
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
        tv4.setText(" SP/KH ");
        tv4.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv4.setBackgroundResource(R.drawable.cell_shape);
        tv4.setTextColor(Color.RED);
        tbrow0.addView(tv4);
        TextView tv5 = new TextView(this);
        tv5.setText(" DT/KH ");
        tv5.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv5.setBackgroundResource(R.drawable.cell_shape);
        tv5.setTextColor(Color.RED);
        tbrow0.addView(tv5);
        TextView tv6 = new TextView(this);
        tv6.setText(" DT/SP ");
        tv6.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv6.setBackgroundResource(R.drawable.cell_shape);
        tv6.setTextColor(Color.RED);
        tbrow0.addView(tv6);
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
            t2v.setText(Keys.setMoneyFloat(list.get(i).getDoanhthu()));
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
            t5v.setText(df.format(list.get(i).getSoSanpham()/list.get(i).getSoKhachhang())+"");
            t5v.setGravity(Gravity.CENTER);
            t5v.setBackgroundResource(R.drawable.cell_shape);
            t5v.setTextColor(Color.DKGRAY);
            tbrow.addView(t5v);
            TextView t6v = new TextView(this);
            t6v.setText(Keys.setMoneyFloat(list.get(i).getDttkh()));
            t6v.setGravity(Gravity.CENTER);
            t6v.setBackgroundResource(R.drawable.cell_shape);
            t6v.setTextColor(Color.DKGRAY);
            tbrow.addView(t6v);
            TextView t7v = new TextView(this);
            t7v.setText(Keys.setMoneyFloat(list.get(i).getDttsp()));
            t7v.setGravity(Gravity.CENTER);
            t7v.setBackgroundResource(R.drawable.cell_shape);
            t7v.setTextColor(Color.DKGRAY);
            tbrow.addView(t7v);
            stk.addView(tbrow);
            stt++;
        }
    }
}