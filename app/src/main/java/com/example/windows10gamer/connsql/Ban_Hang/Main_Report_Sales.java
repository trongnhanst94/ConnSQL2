package com.example.windows10gamer.connsql.Ban_Hang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Customer;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Object.ReportSales;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.APIService_Sales;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.CustomToast;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.OrderList;
import com.example.windows10gamer.connsql.Other.RetrofitClient;
import com.example.windows10gamer.connsql.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Report_Sales extends AppCompatActivity {
    TextView tvNhanvienReport;
    TextView tvnoti;
    TextView tvdoanhthu;
    TextView tvsokhachhangmua;
    TextView tvsosanphamban;
    TextView tvdoanhthutrensanpham;
    TextView tvdoanhthutrenkhachhang;


    static ArrayList<Order> reportList = new ArrayList<>();
    String dateBegin, dateEnd, dateCasang, dateCachieu;
    int doanhthuTotal;
    ArrayList<ReportSales> reportSalesList = new ArrayList<>();
    String ma, ten;
    int doanhthu = 0, soKhachhang = 0, soSanpham = 0, dttkh = 0, dttsp = 0;
    TableLayout stk;
    ArrayList<String> arraySpiner = new ArrayList<>();
    ArrayAdapter<String> adapterSpiner;
    Spinner spinner;
    private String chinhanh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_report_sales);
        arraySpiner.add("Doanh thu");
        arraySpiner.add("Số khách hàng");
        arraySpiner.add("Số sản phẩm");
        arraySpiner.add("Doanh thu/ KH");
        arraySpiner.add("Doanh thu/ SP");
        spinner = findViewById(R.id.spinRS);
        tvNhanvienReport  = findViewById(R.id.tvNhanvienReport);
        tvnoti    = findViewById(R.id.tvnotiReport);
        tvdoanhthu = findViewById(R.id.tvdoanhthu);
        tvsokhachhangmua = findViewById(R.id.tvsokhachhangmua);
        tvsosanphamban       = findViewById(R.id.tvsosanphamban);
        tvdoanhthutrensanpham      = findViewById(R.id.tvdoanhthutrensanpham);
        tvdoanhthutrenkhachhang      = findViewById(R.id.tvdoanhthutrenkhachhang);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("ReportBundle");
        dateBegin = bundle.getString("dateBegin");
        dateEnd = bundle.getString("dateEnd");
        chinhanh = bundle.getString("chinhanh");
        dateCasang = bundle.getString("dateCasang");
        dateCachieu = bundle.getString("dateCachieu");
        doanhthuTotal = 0;
        LoadJson(dateBegin, dateEnd, dateCasang, dateCachieu);
    }

    private void xuly(ArrayList<Order> reportList) {
        Main_Report_Sales.reportList = reportList;
        for (int i = 0; i < reportList.size(); i++){
            doanhthuTotal = doanhthuTotal + Integer.parseInt(reportList.get(i).getGiaSanpham());
        }
        if (dateCasang.equals("FALSE")){
            if (dateCachieu.equals("FALSE")){

            } else {
                tvnoti.setText("Thời gian: "+dateCachieu+" từ "+dateBegin+" đến "+dateEnd);
            }
        } else {
            if (dateCachieu.equals("FALSE")){
                tvnoti.setText("Thời gian: "+dateCasang+" từ "+dateBegin+" đến "+dateEnd);
            } else {
                tvnoti.setText("Thời gian: "+dateCasang+" và "+dateCachieu+" từ "+dateBegin+" đến "+dateEnd);
            }
        }
        tvdoanhthu.setText( Keys.setMoney(doanhthuTotal));
        ArrayList<Customer> khachhang = new ArrayList<>();
        for (int i = 0; i < reportList.size(); i++){
            int result = Keys.sosanhCustomer(khachhang, reportList.get(i).getTenKhachhang(), reportList.get(i).getMaDonhang());
            if (result == -1){
                khachhang.add(new Customer( reportList.get(i).getMaDonhang()+"KH", reportList.get(i).getTenKhachhang(), reportList.get(i).getSodienthoaiKhachhang(), reportList.get(i).getGhichuKhachhang(), reportList.get(i).getMaDonhang(), reportList.get(i).getMaNhanvien(), reportList.get(i).getTenNhanvien()));
            }
        }
        tvsokhachhangmua.setText(khachhang.size()+"");
        tvsosanphamban.setText(reportList.size()+"");
        tvdoanhthutrensanpham.setText(Keys.setMoney(doanhthuTotal/reportList.size()));
        tvdoanhthutrenkhachhang.setText(Keys.setMoney(doanhthuTotal/khachhang.size()));
        tvNhanvienReport.setText("Danh sách nhân viên bán hàng: ");
        ArrayList<User> nhanVienList = new ArrayList<>();
        for (int i = 0; i < reportList.size(); i++){
            int result = Keys.sosanhUser(nhanVienList, reportList.get(i).getMaNhanvien(), reportList.get(i).getTenNhanvien());
            if (result == -1){
                nhanVienList.add(new User(reportList.get(i).getMaNhanvien(), reportList.get(i).getTenNhanvien()));
                tvNhanvienReport.append(reportList.get(i).getTenNhanvien()+". ");
            }
        }

        for (int i = 0; i < nhanVienList.size(); i++){
            ma = nhanVienList.get(i).getMa();
            ten = nhanVienList.get(i).getShortName();
            doanhthu    = Keys.DoanhthuCount(reportList, nhanVienList.get(i).getMa());
            soKhachhang = Keys.SoKhachhang(khachhang, nhanVienList.get(i).getMa());
            soSanpham   = Keys.SoSanpham(reportList, nhanVienList.get(i).getMa());
            dttkh       = doanhthu/soKhachhang;
            dttsp       = doanhthu/soSanpham;
            reportSalesList.add(new ReportSales(
                            ma,
                            ten,
                            doanhthu,
                            soKhachhang,
                            soSanpham,
                            dttkh,
                            dttsp
                    )
            );
        }
        init(reportSalesList);

        adapterSpiner = new ArrayAdapter<String>( Main_Report_Sales.this, android.R.layout.simple_spinner_item, arraySpiner);
        adapterSpiner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterSpiner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapter, View v, int position, long id) {
                stk.removeAllViews();
                if(position == 0) {
                    Keys.sortDoanhthu(reportSalesList);
                    init(reportSalesList);
                } else if(position == 1 ) {
                    Keys.sortSoKH(reportSalesList);
                    init(reportSalesList);
                } else if(position == 2) {
                    Keys.sortSoSP(reportSalesList);
                    init(reportSalesList);
                } else if(position == 3) {
                    Keys.sortDttkh(reportSalesList);
                    init(reportSalesList);
                } else if(position == 4) {
                    Keys.sortDttsp(reportSalesList);
                    init(reportSalesList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }

    public void LoadJson(final String loadBegin, final String loadEnd, final String loadCasang, final String loadCachieu) {
        if (Connect_Internet.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(Main_Report_Sales.this);
            dialog.setTitle("Hãy chờ...");
            dialog.setMessage("Dữ liệu đang được tải xuống");
            dialog.setCancelable(false);
            dialog.show();

            APIService_Sales api = RetrofitClient.getApiService();

            Call<OrderList> call = api.getOrder(loadBegin, loadEnd);

            call.enqueue(new Callback<OrderList>() {
                @Override
                public void onResponse(Call<OrderList> call, Response<OrderList> response) {
                    dialog.dismiss();
                    reportList.clear();
                    ArrayList<Order> orignal = new ArrayList<Order>();
                    if (response.isSuccessful()) {
                        orignal = response.body().getContacts();
                        for (int i = 0; i < orignal.size(); i++) {
                            if (orignal.get(i).getChinhanh().equals(chinhanh)) {
                                if (loadCasang.equals(orignal.get(i).getCalam())) {
                                    reportList.add(orignal.get(i));
                                }
                                if (loadCachieu.equals(orignal.get(i).getCalam())) {
                                    reportList.add(orignal.get(i));
                                }
                            }
                        }
                        xuly(reportList);
                    }
                }

                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            new CustomToast().Show_Toast(Main_Report_Sales.this, findViewById(android.R.id.content), "Không có Internet!");
        }
    }

    public void init(ArrayList<ReportSales> list) {
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
}