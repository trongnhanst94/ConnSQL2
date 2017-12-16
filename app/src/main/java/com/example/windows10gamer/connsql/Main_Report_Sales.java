package com.example.windows10gamer.connsql;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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
import com.example.windows10gamer.connsql.Other.Keys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main_Report_Sales extends AppCompatActivity {
    TextView tvNhanvienReport;
    TextView tvnoti;
    TextView tvdoanhthu;
    TextView tvsokhachhangmua;
    TextView tvsosanphamban;
    TextView tvdoanhthutrensanpham;
    TextView tvdoanhthutrenkhachhang;


    static ArrayList<Order> reportList;
    String dateBegin, dateEnd, dateCasang, dateCachieu;
    int doanhthuTotal;
    ArrayList<ReportSales> reportSalesList = new ArrayList<>();
    String ma, ten;
    int doanhthu = 0, soKhachhang = 0, soSanpham = 0, dttkh = 0, dttsp = 0;
    TableLayout stk;
    ArrayList<String> arraySpiner = new ArrayList<>();
    ArrayAdapter<String> adapterSpiner;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_report_sales);
        arraySpiner.add("Doanh thu");
        arraySpiner.add("Số khách hàng");
        arraySpiner.add("Số sản phẩm");
        arraySpiner.add("Doanh thu/ KH");
        arraySpiner.add("Doanh thu/ SP");
        spinner = (Spinner) findViewById(R.id.spinRS);
        tvNhanvienReport  = (TextView) findViewById(R.id.tvNhanvienReport);
        tvnoti    = (TextView) findViewById(R.id.tvnotiReport);
        tvdoanhthu = (TextView) findViewById(R.id.tvdoanhthu);
        tvsokhachhangmua = (TextView) findViewById(R.id.tvsokhachhangmua);
        tvsosanphamban       = (TextView) findViewById(R.id.tvsosanphamban);
        tvdoanhthutrensanpham      = (TextView) findViewById(R.id.tvdoanhthutrensanpham);
        tvdoanhthutrenkhachhang      = (TextView) findViewById(R.id.tvdoanhthutrenkhachhang);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("ReportBundle");
        reportList = bundle.getParcelableArrayList("ReportList");
        dateBegin = bundle.getString("dateBegin");
        dateEnd = bundle.getString("dateEnd");
        dateCasang = bundle.getString("dateCasang");
        dateCachieu = bundle.getString("dateCachieu");
        doanhthuTotal = 0;
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
        tvdoanhthu.setText( Keys.getFormatedAmount(doanhthuTotal));
        ArrayList<Customer> khachhang = new ArrayList<>();
        for (int i = 0; i < reportList.size(); i++){
            int result = sosanhCustomer(khachhang, reportList.get(i).getTenKhachhang(), reportList.get(i).getMaDonhang());
            if (result == -1){
                khachhang.add(new Customer( reportList.get(i).getMaDonhang()+"KH", reportList.get(i).getTenKhachhang(), reportList.get(i).getSodienthoaiKhachhang(), reportList.get(i).getGhichuKhachhang(), reportList.get(i).getMaDonhang(), reportList.get(i).getMaNhanvien(), reportList.get(i).getTenNhanvien()));
            }
        }
        tvsokhachhangmua.setText(khachhang.size()+"");
        tvsosanphamban.setText(reportList.size()+"");
        tvdoanhthutrensanpham.setText(Keys.getFormatedAmount(doanhthuTotal/reportList.size()));
        tvdoanhthutrenkhachhang.setText(Keys.getFormatedAmount(doanhthuTotal/khachhang.size()));
        tvNhanvienReport.setText("Danh sách nhân viên bán hàng: ");
        ArrayList<User> nhanVienList = new ArrayList<>();
        for (int i = 0; i < reportList.size(); i++){
            int result = sosanhUser(nhanVienList, reportList.get(i).getMaNhanvien(), reportList.get(i).getTenNhanvien());
            if (result == -1){
                nhanVienList.add(new User(reportList.get(i).getMaNhanvien(), reportList.get(i).getTenNhanvien()));
                tvNhanvienReport.append(reportList.get(i).getTenNhanvien()+". ");
            }
        }

        for (int i = 0; i < nhanVienList.size(); i++){
            ma = nhanVienList.get(i).getMa();
            ten = nhanVienList.get(i).getShortName();
            doanhthu    = DoanhthuCount(reportList, nhanVienList.get(i).getMa());
            soKhachhang = SoKhachhang(khachhang, nhanVienList.get(i).getMa());
            soSanpham   = SoSanpham(reportList, nhanVienList.get(i).getMa());
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
                    sortDoanhthu(reportSalesList);
                    init(reportSalesList);
                } else if(position == 1 ) {
                    sortSoKH(reportSalesList);
                    init(reportSalesList);
                } else if(position == 2) {
                    sortSoSP(reportSalesList);
                    init(reportSalesList);
                } else if(position == 3) {
                    sortDttkh(reportSalesList);
                    init(reportSalesList);
                } else if(position == 4) {
                    sortDttsp(reportSalesList);
                    init(reportSalesList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {}
        });
    }

    public void init(ArrayList<ReportSales> list) {
        stk = (TableLayout) findViewById(R.id.tbReport);
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
            t2v.setText(Keys.getFormatedAmount(list.get(i).getDoanhthu()));
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
            t5v.setText(Keys.getFormatedAmount(list.get(i).getDttkh()));
            t5v.setGravity(Gravity.CENTER);
            t5v.setBackgroundResource(R.drawable.cell_shape);
            t5v.setTextColor(Color.DKGRAY);
            tbrow.addView(t5v);
            TextView t6v = new TextView(this);
            t6v.setText(Keys.getFormatedAmount(list.get(i).getDttsp()));
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

    private int sosanhUser(ArrayList<User> user, String ma, String ten) {
        int result = -1;
        if (user.size() != 0){
            for (int i = 0; i < user.size(); i++){
                Log.d("qqq", user.get(i).getMa()+" - "+(ma)+" - "+user.get(i).getShortName()+" - "+(ten));
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
}