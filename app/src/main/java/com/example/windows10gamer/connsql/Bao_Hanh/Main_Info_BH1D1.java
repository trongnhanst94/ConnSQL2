package com.example.windows10gamer.connsql.Bao_Hanh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order;
import com.example.windows10gamer.connsql.Object.BH1D1;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.util.ArrayList;

public class Main_Info_BH1D1 extends AppCompatActivity {
    String maBH, dateToday, timeToday, chinhanhToday, maNVToday, tenNVToday, maOrder, date, time, chinhanh, tenNV, maNV, ten, ma, baohanh, nguon, ngaynhap, von, gia, ghichuOrder, tenKH, sdtKH, ghichuKH, ten_moi, ma_moi, baohanh_moi, nguon_moi, ngaynhap_moi, von_moi, gia_moi, lydo;
    ArrayList<BH1D1> BH1D1 = new ArrayList<>();
    TextView tvd1MaOrder,tvmaBH1D1, tvd1Date, tvd1Time, tvd1MaNV, tvd1TenNV, tvd1GhichuOrder, tvd1TenKH,tvlydod1,
            tvd1SdtKH, tvd1GhichuKH, tvd1TenNVToday, tvd1MaNVToday, tvd1Datetoday, tvd1Timetoday, tvChinhanh1D1, tvChinhanh1D1Order;
    ListView lvBH1D1_moi, lvBH1D1;
    ArrayList<Sanpham> list, list_moi;
    Adapter_Info_Order adapter, adapter_moi;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info_bh1d1);
        Anhxa();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoBH1D1");
        BH1D1 = bundle.getParcelableArrayList("listBH1D1");
        list = new ArrayList<>();
        list_moi = new ArrayList<>();
        maBH = BH1D1.get(0).getMaBH();
        dateToday = BH1D1.get(0).getDateToday();
        timeToday = BH1D1.get(0).getTimeToday();
        chinhanhToday = BH1D1.get(0).getChinhanhToday();
        maNVToday = BH1D1.get(0).getMaNVToday();
        tenNVToday = BH1D1.get(0).getTenNVToday();
        maOrder = BH1D1.get(0).getMaOrder();
        date = BH1D1.get(0).getDate();
        time = BH1D1.get(0).getTime();
        chinhanh = BH1D1.get(0).getChinhanh();
        tenNV = BH1D1.get(0).getTenNV();
        maNV = BH1D1.get(0).getMaNV();
        ten = BH1D1.get(0).getTen();
        ma = BH1D1.get(0).getMa();
        baohanh = BH1D1.get(0).getBaohanh();
        nguon = BH1D1.get(0).getNguon();
        ngaynhap = BH1D1.get(0).getNgaynhap();
        von = BH1D1.get(0).getVon();
        gia = BH1D1.get(0).getGia();
        ghichuOrder = BH1D1.get(0).getGhichuOrder();
        tenKH = BH1D1.get(0).getTenKH();
        sdtKH = BH1D1.get(0).getSdtKH();
        ghichuKH = BH1D1.get(0).getGhichuKH();
        ten_moi = BH1D1.get(0).getTen_moi();
        ma_moi = BH1D1.get(0).getMa_moi();
        baohanh_moi = BH1D1.get(0).getBaohanh_moi();
        nguon_moi = BH1D1.get(0).getNguon_moi();
        ngaynhap_moi = BH1D1.get(0).getNgaynhap_moi();
        von_moi = BH1D1.get(0).getVon_moi();
        gia_moi = BH1D1.get(0).getGia_moi();
        lydo = BH1D1.get(0).getLydo();
        //Log.d("qqq", maBH+", "+dateToday+", "+timeToday+", "+chinhanhToday+", "+maNVToday+", "+tenNVToday+", "+maOrder+", "+date+", "+time+", "+chinhanh+", "+tenNV+", "+maNV+", "+ten+", "+ma+", "+baohanh+", "+nguon+", "+ngaynhap+", "+von+", "+gia+", "+ghichuOrder+", "+tenKH+", "+sdtKH+", "+ghichuKH+", "+ten_moi+", "+ma_moi+", "+baohanh_moi+", "+nguon_moi+", "+ngaynhap_moi+", "+von_moi+", "+gia_moi+", "+lydo);
        tvmaBH1D1.setText("Mã BH: "+maBH);
        tvd1Datetoday.setText(Keys.setDate(dateToday));
        tvd1Timetoday.setText(timeToday);
        tvChinhanh1D1.setText(chinhanhToday);
        tvd1TenNVToday.setText("Mã NV bảo hành: "+ maNVToday);
        tvd1MaNVToday.setText("Tên NV bảo hành: " + tenNVToday);
        tvd1MaOrder.setText("Mã đơn hàng: " + maOrder);
        tvd1Date.setText(Keys.setDate(date));
        tvd1Time.setText(time);
        tvChinhanh1D1Order.setText(chinhanh);
        tvd1MaNV.setText("Mã NV: " + maNV);
        tvd1TenNV.setText("Tên NV: " + tenNV);
        list.add(new Sanpham(ma, ten, baohanh, nguon, Keys.setNN(ngaynhap), von, gia));
        adapter = new Adapter_Info_Order(Main_Info_BH1D1.this, list);
        lvBH1D1.setAdapter(adapter);
        if (ghichuKH.equals("")) {
            tvd1GhichuKH.setVisibility(View.GONE);
        } else {
            tvd1GhichuKH.setText("Ghi chú KH: " + ghichuKH);
        }
        if (ghichuOrder.equals("")) {
            tvd1GhichuOrder.setVisibility(View.GONE);
        } else {
            tvd1GhichuOrder.setText("Ghi chú đơn hàng: " + ghichuOrder);
        }
        tvd1TenKH.setText("Tên KH: " + tenKH);
        tvd1SdtKH.setText("SĐT KH: " + sdtKH);
        list_moi.add(new Sanpham(ma_moi, ten_moi, baohanh_moi, nguon_moi, Keys.setNN(ngaynhap_moi), von_moi, gia_moi));
        adapter_moi = new Adapter_Info_Order(Main_Info_BH1D1.this, list_moi);
        lvBH1D1_moi.setAdapter(adapter_moi);
        tvlydod1.setText("Lý do: " + lydo);
    }

    private void Anhxa() {
        lvBH1D1 = (ListView) findViewById(R.id.lvBHD1);
        lvBH1D1_moi = (ListView) findViewById(R.id.lvBH1D1_moi);
        tvlydod1 = (TextView) findViewById(R.id.tvlydoBHD1);
        tvmaBH1D1 = (TextView) findViewById(R.id.tvmaBH1D1);
        tvd1MaOrder = (TextView) findViewById(R.id.tvd1MaOrder);
        tvd1Date = (TextView) findViewById(R.id.tvd1Date);
        tvd1Time = (TextView) findViewById(R.id.tvd1Time);
        tvd1Datetoday = (TextView) findViewById(R.id.tvd1Datetoday);
        tvd1Timetoday = (TextView) findViewById(R.id.tvd1Timetoday);
        tvd1MaNV = (TextView) findViewById(R.id.tvd1MaNV);
        tvd1TenNV = (TextView) findViewById(R.id.tvd1TenNV);
        tvd1GhichuOrder = (TextView) findViewById(R.id.tvd1Ghichu);
        tvd1TenKH = (TextView) findViewById(R.id.tvd1TenKH);
        tvd1SdtKH = (TextView) findViewById(R.id.tvd1SdtKH);
        tvChinhanh1D1 = (TextView) findViewById(R.id.tvChinhanh1D1);
        tvChinhanh1D1Order = (TextView) findViewById(R.id.tvChinhanh1D1Order);
        tvd1GhichuKH = (TextView) findViewById(R.id.tvd1GhichuKH);
        tvd1TenNVToday = (TextView) findViewById(R.id.tvd1TenNVNhan);
        tvd1MaNVToday = (TextView) findViewById(R.id.tvd1MaNVNhan);
    }

}
