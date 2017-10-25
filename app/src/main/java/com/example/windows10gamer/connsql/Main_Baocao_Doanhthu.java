package com.example.windows10gamer.connsql;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Customer;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Object.ReportSales;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.OrderList;
import com.example.windows10gamer.connsql.Other.RetrofitClient;
import com.example.windows10gamer.connsql.service.APIService_Sales;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Baocao_Doanhthu extends AppCompatActivity {
    @BindView(R.id.tvNhanvienBCDT)
    TextView tvNhanvienBCDT;
    @BindView(R.id.tvnotiBCDT)
    TextView tvnoti;
    @BindView(R.id.tvdoanhthuBCDT)
    TextView tvdoanhthuBCDT;
    @BindView(R.id.tvsokhachhangmuaBCDT)
    TextView tvsokhachhangmuaBCDT;
    @BindView(R.id.tvsosanphambanBCDT)
    TextView tvsosanphambanBCDT;
    @BindView(R.id.tvdoanhthutrensanphamBCDT)
    TextView tvdoanhthutrensanphamBCDT;
    @BindView(R.id.tvdoanhthutrenkhachhangBCDT)
    TextView tvdoanhthutrenkhachhangBCDT;
    ArrayList<Order> contactList, temp;
    private View parentView;
    String ma, ten;
    ArrayList<ReportSales> reportSalesList = new ArrayList<>();
    int doanhthu = 0, soKhachhang = 0, soSanpham = 0, dttkh = 0, dttsp = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_baocao_doanhthu);
        ButterKnife.bind(this);
        contactList = new ArrayList<>();
        temp = new ArrayList<>();

        LoadJson(getDate(), getTime());
    }

    private String getTime() {
        String ca;
        int hours = new Time(System.currentTimeMillis()).getHours();
        if (hours < 15 ){
            ca = "Ca sáng";
        } else {
            ca = "Ca chiều";
        }
        return ca;
    }

    private String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String date = dateFormat.format(new Date());
        return date;
    }

    public void LoadJson(final String date, final String time) {
        if (Connect_Internet.checkConnection(getApplicationContext())) {
            final ProgressDialog dialog;
            dialog = new ProgressDialog(Main_Baocao_Doanhthu.this);
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
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        Date hom = null; Date date1 = null; Date loadEnd1 = null;
                        try {
                            date1 = (Date) simpleDateFormat.parse(date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        for (int i = 0; i < orignal.size(); i++) {
                            try {
                                hom = (Date) simpleDateFormat.parse(Keys.setDate(orignal.get(i).getDateOrder()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            if(hom.compareTo(date1) == 0) {
                                if (time.equals(orignal.get(i).getTimeOrder())) {
                                    contactList.add(orignal.get(i));
                                }
                            }
                        }
                        setList(contactList);
                    } else {
                        //new CustomToast().Show_Toast(Main_Baocao_Doanhthu.this, parentView, "Không có response!");
                    }
                }

                @Override
                public void onFailure(Call<OrderList> call, Throwable t) {
                    dialog.dismiss();
                }
            });

        } else {
            //new CustomToast().Show_Toast(Main_Baocao_Doanhthu.this,parentView, "Không có Internet!");
        }
    }

    private void setList(ArrayList<Order> temp) {
        this.contactList = temp;
        int doanhthuTotal = 0;
        for (int i = 0; i < contactList.size(); i++){
            doanhthuTotal = doanhthuTotal + Integer.parseInt(contactList.get(i).getGiaOrder());
        }
        tvdoanhthuBCDT.setText( Keys.getFormatedAmount(doanhthuTotal));
        ArrayList<Customer> khachhang = new ArrayList<>();
        for (int i = 0; i < contactList.size(); i++){
            int result = sosanhCustomer(khachhang, contactList.get(i).getTenKH(), contactList.get(i).getMaOrder());
            if (result == -1){
                khachhang.add(new Customer( contactList.get(i).getMaOrder()+"KH", contactList.get(i).getTenKH(), contactList.get(i).getSdtKH(), contactList.get(i).getGhichuKH(), contactList.get(i).getMaOrder(), contactList.get(i).getMaNVOrder(), contactList.get(i).getTenNVOrder()));
            }
        }
        tvsokhachhangmuaBCDT.setText(khachhang.size()+"");
        tvsosanphambanBCDT.setText(contactList.size()+"");
        tvdoanhthutrensanphamBCDT.setText(Keys.getFormatedAmount(doanhthuTotal/contactList.size()));
        tvdoanhthutrenkhachhangBCDT.setText(Keys.getFormatedAmount(doanhthuTotal/khachhang.size()));
        tvNhanvienBCDT.setText("Danh sách nhân viên bán hàng: ");
        ArrayList<User> nhanVienList = new ArrayList<>();
        for (int i = 0; i < contactList.size(); i++){
            int result = sosanhUser(nhanVienList, contactList.get(i).getMaNVOrder(), contactList.get(i).getTenNVOrder());
            if (result == -1){
                nhanVienList.add(new User(contactList.get(i).getMaNVOrder(), contactList.get(i).getTenNVOrder()));
                tvNhanvienBCDT.append(contactList.get(i).getTenNVOrder()+". ");
            }
        }

        for (int i = 0; i < nhanVienList.size(); i++){
            ma = nhanVienList.get(i).getMa();
            ten = nhanVienList.get(i).getTen();
            doanhthu    = DoanhthuCount(contactList, nhanVienList.get(i).getMa());
            soKhachhang = SoKhachhang(khachhang, nhanVienList.get(i).getMa());
            soSanpham   = SoSanpham(contactList, nhanVienList.get(i).getMa());
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
    }

    public void init(ArrayList<ReportSales> list) {
        TableLayout stk = (TableLayout) findViewById(R.id.tbBCDT);
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
            if (reportList.get(i).getMaNVOrder().equals(ma)){
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
            if (reportList.get(i).getMaNVOrder().equals(ma)){
                tong += Integer.valueOf(reportList.get(i).getGiaOrder());
            }
        }
        return tong;
    }

    private int sosanhUser(ArrayList<User> user, String ma, String ten) {
        int result = -1;
        if (user.size() != 0){
            for (int i = 0; i < user.size(); i++){
                if (user.get(i).getMa().equals(ma) && user.get(i).getTen().equals(ten)){
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
