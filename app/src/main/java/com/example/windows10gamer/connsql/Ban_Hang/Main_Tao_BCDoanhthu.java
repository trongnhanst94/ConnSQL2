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
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windows10gamer.connsql.Object.BH1D1;
import com.example.windows10gamer.connsql.Object.BHDDT;
import com.example.windows10gamer.connsql.Object.BHDLK;
import com.example.windows10gamer.connsql.Object.BHHT;
import com.example.windows10gamer.connsql.Object.Customer;
import com.example.windows10gamer.connsql.Object.Datcoc;
import com.example.windows10gamer.connsql.Object.Doanhthu;
import com.example.windows10gamer.connsql.Object.Khoanchi;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Object.ReportSales;
import com.example.windows10gamer.connsql.Object.User;
import com.example.windows10gamer.connsql.Other.APIService_Sales;
import com.example.windows10gamer.connsql.Other.Connect_Internet;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Main_Tao_BCDoanhthu extends AppCompatActivity {
    TextView tvnhanvien, tvthongbao, tvtiendauca, tvtientrave, tvtiencuoica, tvsokhachhang, tvsosanpham, tvdoanhthu, tvgiamgia, tvdoanhthusaugiam, tvtongchi, tvdatcoc, tvhoancoc;
    TextView tvdtddt, tvpddt, tvnddt, tvdtdlk, tvpdlk, tvndlk, tvtht, tvdt1d1, tvp1d1;
    Button btnGuibaocao;
    ArrayList<Order> donhanglist = new ArrayList<>();
    ArrayList<Khoanchi> khoanchilist = new ArrayList<>();
    ArrayList<Datcoc> datcoclist = new ArrayList<>();
    ArrayList<Datcoc> hoancoclist = new ArrayList<>();
    ArrayList<BH1D1> BH1D1 = new ArrayList<>();
    ArrayList<BHHT> BHHT = new ArrayList<>();
    ArrayList<BHDLK> BHDLK = new ArrayList<>();
    ArrayList<BHDDT> BHDDT = new ArrayList<>();
    SharedPreferences shared;
    String chinhanh;
    int tiencuoica= 0, doanhthu = 0, giamgia = 0, tongchi = 0, datcoc = 0, hoancoc = 0, dttsp = 0, dtddt = 0, pddt = 0, nddt = 0, dtdlk = 0, pdlk = 0, ndlk = 0, tht = 0, dt1d1 = 0, p1d1 = 0;
    int tiendauca, tientrave;
    private ProgressDialog dialog;
    private String ma, ten;
    int soSanpham, soKhachhang, dttkh;
    String ngay, ca;
    int check = 0;
    private ArrayList<ReportSales> reportSalesList = new ArrayList<>();
    ArrayList<Doanhthu> intentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_tao_bcdoanhthu);
        anhxa();
        shared = getSharedPreferences("chinhanh", MODE_PRIVATE);
        chinhanh = shared.getString("chinhanh", "");
        ca = Keys.getCalam(chinhanh);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DataDoanhthu");
        check  = Integer.valueOf(bundle.getString("check"));
        intentList = bundle.getParcelableArrayList("list");
        if (check == 1){
            btnGuibaocao.setVisibility(View.GONE);
            ngay = intentList.get(0).getNgay();
            ca = intentList.get(0).getCa();
            chinhanh = intentList.get(0).getChinhanh();
            tiendauca = Integer.valueOf(intentList.get(0).getTiendauca());
            tientrave = Integer.valueOf(intentList.get(0).getTientrave());
            tvtiendauca.setText(Keys.setMoney(Integer.valueOf(tiendauca)));
            tvtientrave.setText(Keys.setMoney(Integer.valueOf(tientrave)));
            new SendRequest().execute();
        } else {
            ShowDialog();
            ngay = Keys.getDateNow();
            ca = Keys.getCalam(chinhanh);
        }
        tvthongbao.setText("Báo cáo doanh thu "+ca+" ngày: "+ngay);
        btnGuibaocao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = null;
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
                    dialog = new AlertDialog.Builder(Main_Tao_BCDoanhthu.this);
                } else {
                    dialog = new AlertDialog.Builder(Main_Tao_BCDoanhthu.this);
                }
                dialog.setIcon(R.drawable.ic_settings).setTitle("Thông báo");
                dialog.setMessage("Bạn có chắc muốn báo cáo doanh thu?");
                dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Main_Tao_BCDoanhthu.this, "đã gửi", Toast.LENGTH_SHORT).show();
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
        });
    }

    private void anhxa() {
        btnGuibaocao = (Button) findViewById(R.id.btnGuibaocao);
        tvtiencuoica = (TextView) findViewById(R.id.tvtiencuoica);
        tvdtddt = (TextView) findViewById(R.id.tvbcdtddt);
        tvpddt = (TextView) findViewById(R.id.tvbcpddt);
        tvnddt = (TextView) findViewById(R.id.tvbcnddt);
        tvdtdlk = (TextView) findViewById(R.id.tvbcdtdlk);
        tvpdlk = (TextView) findViewById(R.id.tvbcpdlk);
        tvndlk = (TextView) findViewById(R.id.tvbcndlk);
        tvtht = (TextView) findViewById(R.id.tvbctht);
        tvdt1d1 = (TextView) findViewById(R.id.tvbcdt1d1);
        tvp1d1 = (TextView) findViewById(R.id.tvbcp1d1);
        tvdatcoc = (TextView) findViewById(R.id.tvbcdatcoc);
        tvhoancoc = (TextView) findViewById(R.id.tvbchoancoc);
        tvtongchi = (TextView) findViewById(R.id.tvbctongchi);
        tvnhanvien = (TextView) findViewById(R.id.tvbcnhanvien);
        tvthongbao = (TextView) findViewById(R.id.tvbcthongbao);
        tvtiendauca = (TextView) findViewById(R.id.tvbctiendauca);
        tvtientrave = (TextView) findViewById(R.id.tvbctientrave);
        tvgiamgia = (TextView) findViewById(R.id.tvbcgiamgia);
        tvdoanhthusaugiam = (TextView) findViewById(R.id.tvbcdoanhthusaugiam);
        tvdoanhthu = (TextView) findViewById(R.id.tvbcdoanhthu);
    }

    private void ShowDialog() {
        AlertDialog.Builder dialog = null;
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            dialog = new AlertDialog.Builder(Main_Tao_BCDoanhthu.this);
        } else {
            dialog = new AlertDialog.Builder(Main_Tao_BCDoanhthu.this);
        }
        dialog.setIcon(R.drawable.ic_settings).setTitle("Nhập liệu");
        View mView = getLayoutInflater().inflate(R.layout.dialog_baocaodoanhthu, null);
        final EditText edtiendauca = (EditText) mView.findViewById(R.id.edtiendauca);
        final EditText edtientrave = (EditText) mView.findViewById(R.id.edtientrave);
        dialog.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tiendauca = Integer.valueOf(edtiendauca.getText().toString().trim());
                tientrave = Integer.valueOf(edtientrave.getText().toString().trim());
                tvtiendauca.setText(Keys.setMoney(Integer.valueOf(tiendauca)));
                tvtientrave.setText(Keys.setMoney(Integer.valueOf(tientrave)));
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
                tvnhanvien.append(donhanglist.get(i).getTenNhanvien()+". ");
            }
        }

        for (int i = 0; i < nhanVienList.size(); i++){
            ma = nhanVienList.get(i).getMa();
            ten = nhanVienList.get(i).getShortName();
            doanhthu    = DoanhthuCount(donhanglist, nhanVienList.get(i).getMa());
            soKhachhang = SoKhachhang(khachhang, nhanVienList.get(i).getMa());
            soSanpham   = SoSanpham(donhanglist, nhanVienList.get(i).getMa());
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
                    if(response.isSuccessful()) {
                        orignal = response.body().getContacts();
                        Log.d("qqq", orignal.size()+" "+ngay+ca+chinhanh);
                        donhanglist.clear();
                        for (int i = 0; i < orignal.size(); i++) {
                            donhanglist.add(orignal.get(i));
                            doanhthu += Integer.valueOf(orignal.get(i).getGiaSanpham());
                            giamgia += Integer.valueOf(orignal.get(i).getGiamgia());
                        }
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
                                            object.getString("id"),
                                            object.getString("maDC"),
                                            object.getString("ngay"),
                                            object.getString("ca"),
                                            object.getString("chinhanh"),
                                            object.getString("maNV"),
                                            object.getString("tenNV"),
                                            object.getString("sotien"),
                                            object.getString("tenkhachhang"),
                                            object.getString("sodienthoai"),
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
                                            object.getString("id"),
                                            object.getString("maDC"),
                                            object.getString("ngay"),
                                            object.getString("ca"),
                                            object.getString("chinhanh"),
                                            object.getString("maNV"),
                                            object.getString("tenNV"),
                                            object.getString("sotien"),
                                            object.getString("tenkhachhang"),
                                            object.getString("sodienthoai"),
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
                for (int i = 0; i < BHDDT.size(); i++){
                    dtddt += Integer.valueOf(BHDDT.get(i).getGia_moi());
                    pddt += Integer.valueOf(BHDDT.get(i).getPhidoisp());
                    nddt += Integer.valueOf(BHDDT.get(i).getGia());
                }
            }
            tvdtddt.setText(Keys.setMoney(dtddt));
            tvpddt.setText(Keys.setMoney(pddt));
            tvnddt.setText(Keys.setMoney(nddt));
        }
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
                for (int i = 0; i < BHDLK.size(); i++){
                    dtdlk += Integer.valueOf(BHDLK.get(i).getGia_moi());
                    pdlk += Integer.valueOf(BHDLK.get(i).getPhidoiSP());
                    ndlk += Integer.valueOf(BHDLK.get(i).getGia());
                }
            }
            tvdtdlk.setText(Keys.setMoney(dtdlk));
            tvpdlk.setText(Keys.setMoney(pdlk));
            tvndlk.setText(Keys.setMoney(ndlk));
        }
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
            tiencuoica = tiendauca+tientrave+doanhthu+giamgia-tongchi+datcoc-hoancoc+dtddt+pddt-nddt+dtdlk+pdlk-ndlk-tht+dt1d1+p1d1;
            tvtiencuoica.setText(Keys.setMoney(tiencuoica));
        }
    }

}

