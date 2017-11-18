package com.example.windows10gamer.connsql.Bao_Hanh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order;
import com.example.windows10gamer.connsql.Object.BHHT;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.util.ArrayList;

public class Main_Info_BHHT extends AppCompatActivity {
    String maBH, dateToday, timeToday, chinhanhToday, tenNVToday, maNVToday, maOrder, date, time, chinhanh, tenNV, maNV, ten, ma, baohanh, nguon, ngaynhap, von, gia, ghichuOrder, tenKH, sdtKH, ghichuKH, gtConlai, lydo;
    TextView tvHtMaOrder,tvHtDate,tvHtTime,tvHtMaNV,tvHtTenNV,tvHtGhichuOrder,tvHtTenKH,tvgtConlai,tvlydoHT,
            tvHtSdtKH,tvHtGhichuKH,tvHtTenNVNhan,tvHtMaNVNhan,tvHtDatetoday,tvHtTimetoday,tvChinhanhHT,tvChinhanhHTOrder, tvHtMaBH ;
    ArrayList<BHHT> BHHT = new ArrayList<>();
    ListView lv;
    ArrayList<Sanpham> list = new ArrayList<>();
    Adapter_Info_Order adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info_bhht);
        Anhxa();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoBHHT");
        BHHT = bundle.getParcelableArrayList("listBHHT");
        maBH = BHHT.get(0).getMaBH();
        dateToday = BHHT.get(0).getDateToday();
        timeToday = BHHT.get(0).getTimeToday();
        chinhanhToday = BHHT.get(0).getChinhanhToday();
        maNVToday = BHHT.get(0).getMaNVToday();
        tenNVToday = BHHT.get(0).getTenNVToday();
        maOrder = BHHT.get(0).getMaOrder();
        date = BHHT.get(0).getDate();
        time = BHHT.get(0).getTime();
        chinhanh = BHHT.get(0).getChinhanh();
        tenNV = BHHT.get(0).getTenNV();
        maNV = BHHT.get(0).getMaNV();
        ten = BHHT.get(0).getTen();
        ma = BHHT.get(0).getMa();
        baohanh = BHHT.get(0).getBaohanh();
        nguon = BHHT.get(0).getNguon();
        ngaynhap = BHHT.get(0).getNgaynhap();
        von = BHHT.get(0).getVon();
        gia = BHHT.get(0).getGia();
        ghichuOrder = BHHT.get(0).getGhichuOrder();
        tenKH = BHHT.get(0).getTenKH();
        sdtKH = BHHT.get(0).getSdtKH();
        sdtKH = BHHT.get(0).getSdtKH();
        ghichuKH = BHHT.get(0).getGhichuKH();
        gtConlai = BHHT.get(0).getGtConlai();
        lydo = BHHT.get(0).getLydo();
        tvHtMaBH.setText("Mã BH: "+maBH);
        tvHtDatetoday.setText(Keys.setDate(dateToday));
        tvHtTimetoday.setText(timeToday);
        tvChinhanhHT.setText(chinhanhToday);
        tvHtTenNVNhan.setText("Mã NV bảo hành: "+ maNVToday);
        tvHtMaNVNhan.setText("Tên NV bảo hành: " + tenNVToday);
        tvHtMaOrder.setText("Mã đơn hàng: " + maOrder);
        tvHtDate.setText(Keys.setDate(date));
        tvHtTime.setText(time);
        tvChinhanhHTOrder.setText(chinhanh);
        tvHtMaNV.setText("Mã NV: " + maNV);
        tvHtTenNV.setText("Tên NV: " + tenNV);
        list.add(new Sanpham(ma, ten, baohanh, nguon, ngaynhap, von, gia));
        adapter = new Adapter_Info_Order(Main_Info_BHHT.this, list);
        lv.setAdapter(adapter);
        if (ghichuKH.equals("")) {
            tvHtGhichuKH.setVisibility(View.GONE);
        } else {
            tvHtGhichuKH.setText("Ghi chú KH: " + ghichuKH);
        }
        if (ghichuOrder.equals("")) {
            tvHtGhichuOrder.setVisibility(View.GONE);
        } else {
            tvHtGhichuOrder.setText("Ghi chú đơn hàng: " + ghichuOrder);
        }
        tvHtTenKH.setText("Tên KH: " + tenKH);
        tvHtSdtKH.setText("SĐT KH: " + sdtKH);
        tvlydoHT.setText("Lý do: " + lydo);
        tvgtConlai.setText(Keys.getFormatedAmount(Integer.parseInt(gtConlai)));
    }

    private void Anhxa() {
        tvgtConlai = (TextView) findViewById(R.id.tvgtConlai);
        tvlydoHT = (TextView) findViewById(R.id.tvlydoBHHT);
        tvHtMaOrder = (TextView) findViewById(R.id.tvHtMaOrder);
        tvHtDate = (TextView) findViewById(R.id.tvHtDate);
        tvHtTime = (TextView) findViewById(R.id.tvHtTime);
        tvHtDatetoday = (TextView) findViewById(R.id.tvHtDatetoday);
        tvHtTimetoday = (TextView) findViewById(R.id.tvHtTimetoday);
        tvHtMaNV = (TextView) findViewById(R.id.tvHtMaNV);
        tvHtTenNV = (TextView) findViewById(R.id.tvHtTenNV);
        tvHtGhichuOrder = (TextView) findViewById(R.id.tvHtGhichu);
        tvChinhanhHT = (TextView) findViewById(R.id.tvChinhanhHT);
        tvChinhanhHTOrder = (TextView) findViewById(R.id.tvChinhanhHTOrder);
        tvHtTenKH = (TextView) findViewById(R.id.tvHtTenKH);
        tvHtSdtKH = (TextView) findViewById(R.id.tvHtSdtKH);
        lv = (ListView) findViewById(R.id.lvBHHT);
        tvHtGhichuKH = (TextView) findViewById(R.id.tvHtGhichuKH);
        tvHtTenNVNhan = (TextView) findViewById(R.id.tvHtTenNVNhan);
        tvHtMaNVNhan = (TextView) findViewById(R.id.tvHtMaNVNhan);
        tvHtMaBH = (TextView) findViewById(R.id.tvHtMaBH);
    }
}
