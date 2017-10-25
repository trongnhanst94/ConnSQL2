package com.example.windows10gamer.connsql;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Other.Keys;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main_Information_Order extends AppCompatActivity {

    @BindView(R.id.tvifMaOrder)
    TextView tvifMaOrder;
    @BindView(R.id.tvifDateOrder)
    TextView tvifDateOrder;
    @BindView(R.id.tvifTimeOrder)
    TextView tvifTimeOrder;
    @BindView(R.id.tvifMaNVOrder)
    TextView tvifMaNVOrder;
    @BindView(R.id.tvifTenNVOrder)
    TextView tvifTenNVOrder;
    TextView tvifGiaOrder;
    @BindView(R.id.tvifGhichuOrder)
    TextView tvifGhichuOrder;
    @BindView(R.id.tvifTenKH)
    TextView tvifTenKH;
    @BindView(R.id.tvifSdtKH)
    TextView tvifSdtKH;
    @BindView(R.id.tvifGhichuKH)
    TextView tvifGhichuKH;
    @BindView(R.id.tvifTotalOrder)
    TextView tvifTotalOrder;
    @BindView(R.id.tvifDoanhthutrensanpham)
    TextView tvifDoanhthutrensanpham;
    @BindView(R.id.lvInfoOrder)
    ListView lvInfoOrder;
    ArrayList<Order> orderInfo = new ArrayList<>();
    ArrayList<Order> item = new ArrayList<>();
    ArrayList<Sanpham> sanphamOrder = new ArrayList<>();
    int position, total;
    String keyMaOrder;
    Adapter_Info_Order adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_information_order);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DataOrder");
        position  = bundle.getInt("position");
        keyMaOrder = bundle.getString("keyMaOrder");
        Log.d("bbb",position+" "+keyMaOrder);
        orderInfo = bundle.getParcelableArrayList("arrayOrder");
        for (int i = 0; i < orderInfo.size(); i++){
            if ((orderInfo.get(i).getMaOrder()).equals(keyMaOrder)){
                item.add(orderInfo.get(i));
            }
        }
        for (int j = 0; j < item.size(); j++){
            sanphamOrder.add(new Sanpham(
                    item.get(j).getMaspOrder(),
                    item.get(j).getTenOrder(),
                    item.get(j).getBaohanhOrder(),
                    item.get(j).getNguonOrder(),
                    item.get(j).getNgaynhapOrder(),
                    item.get(j).getVonOrder(),
                    item.get(j).getGiaOrder()));
            total+=Integer.parseInt(item.get(j).getGiaOrder());
        }
        tvifMaOrder.setText(item.get(0).getMaOrder());
        tvifDateOrder.setText("Ngày: "+Keys.setDate(item.get(0).getDateOrder()));
        tvifTimeOrder.setText(" Ca: "+item.get(0).getTimeOrder());
        tvifMaNVOrder.setText("Mã nhân viên: "+item.get(0).getMaNVOrder());
        tvifTenNVOrder.setText("Tên nhân viên: "+item.get(0).getTenNVOrder());
        tvifGhichuOrder.setText("Ghi chú đơn hàng: "+item.get(0).getGhichuOrder());
        tvifTenKH.setText("Tên khách hàng: "+item.get(0).getTenKH());
        tvifSdtKH.setText("SĐT khách hàng: "+item.get(0).getSdtKH());
        tvifGhichuKH.setText("Ghi chú khách hàng: "+item.get(0).getGhichuKH());
        tvifTotalOrder.setText("Tổng: "+Keys.getFormatedAmount(total));
        tvifDoanhthutrensanpham.setText("Chỉ số KPI: "+ Keys.getFormatedAmount(total/item.size())+"/SP");

        adapter = new Adapter_Info_Order(Main_Information_Order.this, sanphamOrder);
        lvInfoOrder.setAdapter(adapter);
    }
}
