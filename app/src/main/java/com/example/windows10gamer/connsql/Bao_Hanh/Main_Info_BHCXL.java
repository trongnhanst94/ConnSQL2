package com.example.windows10gamer.connsql.Bao_Hanh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order;
import com.example.windows10gamer.connsql.Object.BHCXL;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.R;

import java.util.ArrayList;

public class Main_Info_BHCXL extends AppCompatActivity {
    String maBH, dateToday, gio, timeToday, chinhanhToday, tenNVToday, maNVToday, maOrder,thoigianhen,maNVxuly,tenNVxuly, date, time, chinhanh, tenNV, maNV, ten, ma, baohanh, nguon, ngaynhap, von, gia, ghichuOrder, tenKH, sdtKH, ghichuKH, gtConlai, lydo;
    TextView tvCxlMaOrder,tvCxlDate,tvCxlTime,tvCxlMaNV,tvCxlTenNV,tvCxlGhichuOrder,tvCxlTenKH,tvthoigianhen,tvmaNVxuly,tvtenNVxuly,
            tvCxlSdtKH,tvCxlGhichuKH,tvCxlTenNVNhan,tvCxlMaNVNhan,tvCxlDatetoday,tvCxlTimetoday,tvChinhanhHT,tvChinhanhHTOrder, tvCxlMaBH ;
    ArrayList<BHCXL> BHCXL = new ArrayList<>();
    ListView lv;
    ArrayList<Sanpham_gio> list = new ArrayList<>();
    Adapter_Info_Order adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info_bhcxl);
        Anhxa();

        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoBHCXL");
        BHCXL = bundle.getParcelableArrayList("listBHCXL");
        maBH = BHCXL.get(0).getMaBH();
        dateToday = BHCXL.get(0).getDateToday();
        timeToday = BHCXL.get(0).getTimeToday();
        chinhanhToday = BHCXL.get(0).getChinhanhToday();
        maNVToday = BHCXL.get(0).getMaNVToday();
        tenNVToday = BHCXL.get(0).getTenNVToday();
        maOrder = BHCXL.get(0).getMaOrder();
        date = BHCXL.get(0).getDate();
        time = BHCXL.get(0).getTime();
        chinhanh = BHCXL.get(0).getChinhanh();
        tenNV = BHCXL.get(0).getTenNV();
        maNV = BHCXL.get(0).getMaNV();
        ten = BHCXL.get(0).getTen();
        ma = BHCXL.get(0).getMa();
        gio = BHCXL.get(0).getGio();
        baohanh = BHCXL.get(0).getBaohanh();
        nguon = BHCXL.get(0).getNguon();
        ngaynhap = BHCXL.get(0).getNgaynhap();
        von = BHCXL.get(0).getVon();
        gia = BHCXL.get(0).getGia();
        ghichuOrder = BHCXL.get(0).getGhichuOrder();
        tenKH = BHCXL.get(0).getTenKH();
        sdtKH = BHCXL.get(0).getSdtKH();
        sdtKH = BHCXL.get(0).getSdtKH();
        ghichuKH = BHCXL.get(0).getGhichuKH();
        thoigianhen = BHCXL.get(0).getThoigianhen();
        maNVxuly = BHCXL.get(0).getMaNVxuly();
        tenNVxuly = BHCXL.get(0).getTenNVxuly();
        tvCxlMaBH.setText("Mã BH: "+maBH);
        tvCxlDatetoday.setText(dateToday);
        tvCxlTimetoday.setText(timeToday);
        tvChinhanhHT.setText(chinhanhToday);
        tvCxlTenNVNhan.setText("Mã NV bảo hành: "+ maNVToday);
        tvCxlMaNVNhan.setText("Tên NV bảo hành: " + tenNVToday);
        tvCxlMaOrder.setText("Mã đơn hàng: " + maOrder);
        tvCxlDate.setText(date);
        tvCxlTime.setText(time);
        tvChinhanhHTOrder.setText(chinhanh);
        tvCxlMaNV.setText("Mã số: " + maNV);
        tvCxlTenNV.setText("Tên nhân viên: " + tenNV);
        list.add(new Sanpham_gio(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia));
        adapter = new Adapter_Info_Order(Main_Info_BHCXL.this, list);
        lv.setAdapter(adapter);
        if (ghichuKH.equals("")) {
            tvCxlGhichuKH.setVisibility(View.GONE);
        } else {
            tvCxlGhichuKH.setText("Ghi chú KH: " + ghichuKH);
        }
        if (ghichuOrder.equals("")) {
            tvCxlGhichuOrder.setVisibility(View.GONE);
        } else {
            tvCxlGhichuOrder.setText("Ghi chú đơn hàng: " + ghichuOrder);
        }
        tvCxlTenKH.setText("Tên KH: " + tenKH);
        tvCxlSdtKH.setText("SĐT KH: " + sdtKH);
        tvthoigianhen.setText(thoigianhen);
        tvmaNVxuly.setText("Mã số: " +maNVxuly);
        tvtenNVxuly.setText("Tên nhân viên: " +tenNVxuly);
    }

    private void Anhxa() {
        tvthoigianhen = (TextView) findViewById(R.id.tvthoigianhen);
        tvmaNVxuly = (TextView) findViewById(R.id.tvmaNVxuly);
        tvtenNVxuly = (TextView) findViewById(R.id.tvtenNVxuly);
        tvCxlMaOrder = (TextView) findViewById(R.id.tvCxlMaOrder);
        tvCxlDate = (TextView) findViewById(R.id.tvCxlDate);
        tvCxlTime = (TextView) findViewById(R.id.tvCxlTime);
        tvCxlDatetoday = (TextView) findViewById(R.id.tvCxlDatetoday);
        tvCxlTimetoday = (TextView) findViewById(R.id.tvCxlTimetoday);
        tvCxlMaNV = (TextView) findViewById(R.id.tvCxlMaNV);
        tvCxlTenNV = (TextView) findViewById(R.id.tvCxlTenNV);
        tvCxlGhichuOrder = (TextView) findViewById(R.id.tvCxlGhichu);
        tvChinhanhHT = (TextView) findViewById(R.id.tvChinhanhHT);
        tvChinhanhHTOrder = (TextView) findViewById(R.id.tvChinhanhHTOrder);
        tvCxlTenKH = (TextView) findViewById(R.id.tvCxlTenKH);
        tvCxlSdtKH = (TextView) findViewById(R.id.tvCxlSdtKH);
        lv = (ListView) findViewById(R.id.lvBHCXL);
        tvCxlGhichuKH = (TextView) findViewById(R.id.tvCxlGhichuKH);
        tvCxlTenNVNhan = (TextView) findViewById(R.id.tvCxlTenNVNhan);
        tvCxlMaNVNhan = (TextView) findViewById(R.id.tvCxlMaNVNhan);
        tvCxlMaBH = (TextView) findViewById(R.id.tvCxlMaBH);
    }
}
