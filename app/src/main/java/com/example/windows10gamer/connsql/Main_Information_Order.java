package com.example.windows10gamer.connsql;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_1doi1;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_Doilaykhac;
import com.example.windows10gamer.connsql.Bao_Hanh.Main_Hoantien;
import com.example.windows10gamer.connsql.Object.Order;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Other.Keys;

import java.util.ArrayList;

public class Main_Information_Order extends AppCompatActivity {
    TextView tvifMaOrder;
    TextView tvifDateOrder;
    TextView tvifTimeOrder;
    TextView tvifMaNVOrder;
    TextView tvifTenNVOrder;
    TextView tvifChinhanhOrder;
    TextView tvifGiaOrder;
    TextView tvifGhichuOrder;
    TextView tvifTenKH;
    TextView tvifSdtKH;
    TextView tvifGhichuKH;
    TextView tvifTotalOrder;
    TextView tvifDoanhthutrensanpham;
    SwipeMenuListView lvInfoOrder;

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
        tvifMaOrder = (TextView) findViewById(R.id.tvifMaOrder);
        tvifDateOrder = (TextView) findViewById(R.id.tvifDateOrder);
        tvifTimeOrder = (TextView) findViewById(R.id.tvifTimeOrder);
        tvifMaNVOrder = (TextView) findViewById(R.id.tvifMaNVOrder);
        tvifTenNVOrder = (TextView) findViewById(R.id.tvifTenNVOrder);
        tvifChinhanhOrder = (TextView) findViewById(R.id.tvifChinhanhOrder);
        tvifGhichuOrder = (TextView) findViewById(R.id.tvifGhichuOrder);
        tvifTenKH = (TextView) findViewById(R.id.tvifTenKH);
        tvifSdtKH = (TextView) findViewById(R.id.tvifSdtKH);
        tvifGhichuKH = (TextView) findViewById(R.id.tvifGhichuKH);
        tvifTotalOrder = (TextView) findViewById(R.id.tvifTotalOrder);
        tvifDoanhthutrensanpham = (TextView) findViewById(R.id.tvifDoanhthutrensanpham);
        lvInfoOrder = (SwipeMenuListView) findViewById(R.id.lvInfoOrder);
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("DataOrder");
        position  = bundle.getInt("position");
        keyMaOrder = bundle.getString("keyMaOrder");
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
        tvifChinhanhOrder.setText(item.get(0).getChinhanhOrder());
        tvifTenNVOrder.setText("Tên nhân viên: "+item.get(0).getTenNVOrder());
        if (item.get(0).getGhichuOrder().equals("")){
            tvifGhichuOrder.setVisibility(View.GONE);
        } else {
            tvifGhichuOrder.setText("Ghi chú đơn hàng: "+item.get(0).getGhichuOrder());
        }
        tvifTenKH.setText("Tên khách hàng: "+item.get(0).getTenKH());
        tvifSdtKH.setText("SĐT khách hàng: "+item.get(0).getSdtKH());
        if (item.get(0).getGhichuKH().equals("")){
            tvifGhichuKH.setVisibility(View.GONE);
        } else {
            tvifGhichuKH.setText("Ghi chú đơn hàng: "+item.get(0).getGhichuKH());
        }
        tvifGhichuKH.setText("Ghi chú khách hàng: "+item.get(0).getGhichuKH());
        tvifTotalOrder.setText("Tổng: "+Keys.getFormatedAmount(total));
        tvifDoanhthutrensanpham.setText("Chỉ số KPI: "+ Keys.getFormatedAmount(total/item.size())+"/SP");

        adapter = new Adapter_Info_Order(Main_Information_Order.this, sanphamOrder);
        lvInfoOrder.setAdapter(adapter);

        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                int totalWidth = findViewById(R.id.lvInfoOrder).getMeasuredWidth() - 40;

//                Display display=getWindowManager().getDefaultDisplay();
//                int width=display.getWidth();

                SwipeMenuItem hoantien = new SwipeMenuItem(getApplicationContext());
                hoantien.setWidth(totalWidth/3);
                hoantien.setTitle("HOÀN TIỀN");
                hoantien.setIcon(R.mipmap.ic_ht);
                hoantien.setTitleSize(10);
                hoantien.setTitleColor(R.color.cam);
                hoantien.setBackground(R.drawable.bg123);
                menu.addMenuItem(hoantien);

                SwipeMenuItem khacloai = new SwipeMenuItem(getApplicationContext());
                khacloai.setWidth(totalWidth/3);
                khacloai.setTitle("ĐỔI KHÁC");
                khacloai.setIcon(R.mipmap.ic_dkl);
                khacloai.setTitleSize(10);
                khacloai.setTitleColor(R.color.cam);
                khacloai.setBackground(R.drawable.bg123);
                menu.addMenuItem(khacloai);

                SwipeMenuItem doi1 = new SwipeMenuItem(getApplicationContext());
                doi1.setWidth(totalWidth/3);
                doi1.setTitle("1 ĐỔI 1");
                doi1.setIcon(R.mipmap.ic_d1);
                doi1.setTitleSize(10);
                doi1.setTitleColor(R.color.cam);
                doi1.setBackground(R.drawable.bg123);
                menu.addMenuItem(doi1);
            }
        };

        lvInfoOrder.setMenuCreator(creator);

        lvInfoOrder.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Intent intent2 = new Intent(Main_Information_Order.this, Main_Hoantien.class);
                        Bundle bundle2 = new Bundle();
                        bundle2.putString("MaOrder", item.get(0).getMaOrder());
                        bundle2.putString("DateOrder", item.get(0).getDateOrder());
                        bundle2.putString("TimeOrder", item.get(0).getTimeOrder());
                        bundle2.putString("ChinhanhOrder", item.get(0).getChinhanhOrder());
                        bundle2.putString("MaNVOrder", item.get(0).getMaNVOrder());
                        bundle2.putString("TenNVOrder", item.get(0).getTenNVOrder());
                        bundle2.putString("GhichuOrder", item.get(0).getGhichuOrder());
                        bundle2.putString("TenKH", item.get(0).getTenKH());
                        bundle2.putString("SdtKH", item.get(0).getSdtKH());
                        bundle2.putString("GhichuKH", item.get(0).getGhichuKH());
                        bundle2.putInt("vitri", position);
                        bundle2.putParcelableArrayList("sanphamOrder", sanphamOrder);
                        intent2.putExtra("InfoOrder2", bundle2);
                        startActivity(intent2);
                        break;
                    case 1:
                        Intent intent3 = new Intent(Main_Information_Order.this, Main_Doilaykhac.class);
                        Bundle bundle3 = new Bundle();
                        bundle3.putString("MaOrder", item.get(0).getMaOrder());
                        bundle3.putString("DateOrder", item.get(0).getDateOrder());
                        bundle3.putString("TimeOrder", item.get(0).getTimeOrder());
                        bundle3.putString("ChinhanhOrder", item.get(0).getChinhanhOrder());
                        bundle3.putString("MaNVOrder", item.get(0).getMaNVOrder());
                        bundle3.putString("TenNVOrder", item.get(0).getTenNVOrder());
                        bundle3.putString("GhichuOrder", item.get(0).getGhichuOrder());
                        bundle3.putString("TenKH", item.get(0).getTenKH());
                        bundle3.putString("SdtKH", item.get(0).getSdtKH());
                        bundle3.putString("GhichuKH", item.get(0).getGhichuKH());
                        bundle3.putInt("vitri", position);
                        bundle3.putParcelableArrayList("sanphamOrder", sanphamOrder);
                        intent3.putExtra("InfoOrder3", bundle3);
                        startActivity(intent3);
                        break;
                    case 2:
                        Intent intent4 = new Intent(Main_Information_Order.this, Main_1doi1.class);
                        Bundle bundle4 = new Bundle();
                        bundle4.putString("MaOrder", item.get(0).getMaOrder());
                        bundle4.putString("DateOrder", item.get(0).getDateOrder());
                        bundle4.putString("TimeOrder", item.get(0).getTimeOrder());
                        bundle4.putString("ChinhanhOrder", item.get(0).getChinhanhOrder());
                        bundle4.putString("MaNVOrder", item.get(0).getMaNVOrder());
                        bundle4.putString("TenNVOrder", item.get(0).getTenNVOrder());
                        bundle4.putString("GhichuOrder", item.get(0).getGhichuOrder());
                        bundle4.putString("TenKH", item.get(0).getTenKH());
                        bundle4.putString("SdtKH", item.get(0).getSdtKH());
                        bundle4.putString("GhichuKH", item.get(0).getGhichuKH());
                        bundle4.putInt("vitri", position);
                        bundle4.putParcelableArrayList("sanphamOrder", sanphamOrder);
                        intent4.putExtra("InfoOrder4", bundle4);
                        startActivity(intent4);
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });
    }
}
