package com.example.windows10gamer.connsql.Ban_Hang;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order_TD;
import com.example.windows10gamer.connsql.Adapter.Adapter_QuaTangInfo;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Object.Quatang;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Other.GiftList;
import com.example.windows10gamer.connsql.Other.JSONParser;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main_Information_Order extends AppCompatActivity {
    TextView tvifMaOrder;
    TextView tvifDateOrder;
    TextView tvifTimeOrder;
    TextView tvifMaNVOrder;
    TextView tvifTenNVOrder;
    TextView tvifChinhanhOrder;
    TextView tvifGhichuOrder;
    TextView tvifTenKH;
    TextView tvifSdtKH;
    TextView tvifGhichuKH;
    TextView tvifTotalOrder;
    TextView tvifgiamgia, tvifsaugiamgia;
    SwipeMenuListView lvInfoOrder;
    LinearLayout lnHide;
    GiftList lvKhuyenmai;
    ArrayList<Order> orderInfo = new ArrayList<>();
    ArrayList<Order> item = new ArrayList<>();
    ArrayList<Sanpham_gio> sanphamOrder = new ArrayList<>();
    int position, total, giamgia, saugiamgia;
    String keyMaOrder;
    Adapter_Info_Order_TD adapter;
    private ProgressDialog dialog2;
    Adapter_QuaTangInfo adapterKhuyenmai;
    private ArrayList<Quatang> arraylist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_information_order);
        lnHide = findViewById(R.id.lnHide);
        lvKhuyenmai = findViewById(R.id.lvKhuyenmai);
        tvifMaOrder = findViewById(R.id.tvifMaOrder);
        tvifDateOrder = findViewById(R.id.tvifDateOrder);
        tvifTimeOrder = findViewById(R.id.tvifTimeOrder);
        tvifMaNVOrder = findViewById(R.id.tvifMaNVOrder);
        tvifTenNVOrder = findViewById(R.id.tvifTenNVOrder);
        tvifChinhanhOrder = findViewById(R.id.tvifChinhanhOrder);
        tvifGhichuOrder = findViewById(R.id.tvifGhichuOrder);
        tvifTenKH = findViewById(R.id.tvifTenKH);
        tvifSdtKH = findViewById(R.id.tvifSdtKH);
        tvifGhichuKH = findViewById(R.id.tvifGhichuKH);
        tvifTotalOrder = findViewById(R.id.tvifTotalOrder);
        tvifgiamgia = findViewById(R.id.tvifgiamgia);
        tvifsaugiamgia = findViewById(R.id.tvifsaugiamgia);
        lvInfoOrder = findViewById(R.id.lvInfoOrder);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DataOrder");
        position  = bundle.getInt("position");
        keyMaOrder = bundle.getString("keyMaOrder");
        orderInfo = bundle.getParcelableArrayList("arrayOrder");
        for (int i = 0; i < orderInfo.size(); i++){
            if ((orderInfo.get(i).getMaDonhang()).equals(keyMaOrder)){
                item.add(orderInfo.get(i));
            }
        }
        for (int j = 0; j < item.size(); j++){
            sanphamOrder.add(new Sanpham_gio(
                    item.get(j).getGio(),
                    item.get(j).getMaSanpham(),
                    item.get(j).getTenSanpham(),
                    item.get(j).getBaohanhSanpham(),
                    item.get(j).getNguonSanpham(),
                    item.get(j).getNgaynhapSanpham(),
                    item.get(j).getVonSanpham(),
                    item.get(j).getGiaSanpham()));
            total+=Integer.parseInt(item.get(j).getGiaSanpham());
        }
        new GetData().execute();
        tvifMaOrder.setText(item.get(0).getMaDonhang());
        tvifDateOrder.setText("Ngày: "+item.get(0).getNgay());
        tvifTimeOrder.setText(" Ca: "+item.get(0).getCalam());
        tvifMaNVOrder.setText("Mã số: "+item.get(0).getMaNhanvien());
        tvifChinhanhOrder.setText(item.get(0).getChinhanh());
        tvifTenNVOrder.setText("Tên nhân viên: "+item.get(0).getTenNhanvien());
        if (item.get(0).getGhichuSanpham().equals("")){
            tvifGhichuOrder.setVisibility(View.GONE);
        } else {
            tvifGhichuOrder.setText("Ghi chú đơn hàng: "+item.get(0).getGhichuSanpham());
        }
        tvifTenKH.setText("Tên khách hàng: "+item.get(0).getTenKhachhang());
        tvifSdtKH.setText("SĐT khách hàng: "+item.get(0).getSodienthoaiKhachhang());
        if (item.get(0).getGhichuKhachhang().equals("")){
            tvifGhichuKH.setVisibility(View.GONE);
        } else {
            tvifGhichuKH.setText("Ghi chú khách hàng: "+item.get(0).getGhichuKhachhang());
        }
        tvifTotalOrder.setText(Keys.setMoney(total));
        tvifgiamgia.setText("- "+ Keys.setMoney(Integer.valueOf(item.get(0).getGiamgia())));
        tvifsaugiamgia.setText(Keys.setMoney(total - Integer.valueOf(item.get(0).getGiamgia())));

        adapter = new Adapter_Info_Order_TD(Main_Information_Order.this, item);
        lvInfoOrder.setAdapter(adapter);
    }

    class GetData extends AsyncTask<String, Void, Void> {
        int jIndex;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog2 = new ProgressDialog(Main_Information_Order.this);
            dialog2.setTitle("Hãy chờ...");
            dialog2.setMessage("Dữ liệu đang được tải xuống");
            dialog2.setCancelable(false);
            dialog2.show();
        }

        @Nullable
        @Override
        protected Void doInBackground(String... params) {
            JSONObject jsonObject = JSONParser.getDataFromWeb(Keys.MAIN_KHUYENMAI+"?MASALES="+item.get(0).getMaDonhang());
            try {
                if (jsonObject != null) {
                    arraylist.clear();
                    if(jsonObject.length() > 0) {
                        JSONArray array = jsonObject.getJSONArray(Keys.KHUYENMAI);
                        int lenArray = array.length();
                        if(lenArray > 0) {
                            for( ; jIndex < lenArray; jIndex++) {
                                try {
                                    JSONObject object = array.getJSONObject(jIndex);
                                    arraylist.add(new Quatang(
                                            object.getString("maSanpham"),
                                            object.getString("tenSanpham"),
                                            object.getString("baohanhSanpham"),
                                            object.getString("nguonSanpham"),
                                            object.getString("ngaynhapSanpham"),
                                            object.getInt("vonSanpham"),
                                            object.getInt("giaSanpham"),
                                            0,
                                            0
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
            if(arraylist.size() > 0) {
                lnHide.setVisibility(View.VISIBLE);
                adapterKhuyenmai = new Adapter_QuaTangInfo(Main_Information_Order.this, R.layout.adapter_quatang, arraylist);
                lvKhuyenmai.setAdapter(adapterKhuyenmai);
            }
            dialog2.dismiss();
        }
    }
}
