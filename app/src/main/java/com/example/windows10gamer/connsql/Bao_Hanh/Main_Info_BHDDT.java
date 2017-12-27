package com.example.windows10gamer.connsql.Bao_Hanh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order;
import com.example.windows10gamer.connsql.Object.BHDDT;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.util.ArrayList;

public class Main_Info_BHDDT extends AppCompatActivity {
    String maBH, dateToday, timeToday, gio, gio_moi, chinhanhToday, maNVToday, tenNVToday, maOrder, date, time, chinhanh, tenNV, maNV, ten, ma, baohanh, nguon, ngaynhap, von, gia, ghichuOrder, tenKH, sdtKH, ghichuKH, ten_moi, ma_moi, baohanh_moi, nguon_moi, ngaynhap_moi, von_moi, gia_moi, lydo;
    ArrayList<BHDDT> BHDDT = new ArrayList<>();
    TextView tvd1MaOrder,tvmaBHDDT, tvd1Date, tvd1Time, tvd1MaNV, tvd1TenNV, tvd1GhichuOrder, tvd1TenKH,tvlydod1, tvphibaohanh,
            tvd1SdtKH, tvd1GhichuKH, tvd1TenNVToday, tvd1MaNVToday, tvd1Datetoday, tvd1Timetoday, tvChinhanhDDT, tvChinhanhDDTOrder;
    ListView lvBHDDT_moi, lvBHDDT;
    ArrayList<Sanpham_gio> list, list_moi;
    Adapter_Info_Order adapter, adapter_moi;
    TextView tvDlkChenhlech, tvphidoiSP;
    String chechlech, phidoiSP;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info_bhddt);
        Anhxa();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoBHDDT");
        BHDDT = bundle.getParcelableArrayList("listBHDDT");
        list = new ArrayList<>();
        list_moi = new ArrayList<>();
        maBH = BHDDT.get(0).getMaBH();
        dateToday = BHDDT.get(0).getDateToday();
        timeToday = BHDDT.get(0).getTimeToday();
        chinhanhToday = BHDDT.get(0).getChinhanhToday();
        maNVToday = BHDDT.get(0).getMaNVToday();
        tenNVToday = BHDDT.get(0).getTenNVToday();
        maOrder = BHDDT.get(0).getMaOrder();
        date = BHDDT.get(0).getDate();
        time = BHDDT.get(0).getTime();
        chinhanh = BHDDT.get(0).getChinhanh();
        tenNV = BHDDT.get(0).getTenNV();
        maNV = BHDDT.get(0).getMaNV();
        ten = BHDDT.get(0).getTen();
        ma = BHDDT.get(0).getMa();
        gio = BHDDT.get(0).getGio();
        baohanh = BHDDT.get(0).getBaohanh();
        nguon = BHDDT.get(0).getNguon();
        ngaynhap = BHDDT.get(0).getNgaynhap();
        von = BHDDT.get(0).getVon();
        gia = BHDDT.get(0).getGia();
        ghichuOrder = BHDDT.get(0).getGhichuOrder();
        tenKH = BHDDT.get(0).getTenKH();
        sdtKH = BHDDT.get(0).getSdtKH();
        ghichuKH = BHDDT.get(0).getGhichuKH();
        ten_moi = BHDDT.get(0).getTen_moi();
        ma_moi = BHDDT.get(0).getMa_moi();
        baohanh_moi = BHDDT.get(0).getBaohanh_moi();
        nguon_moi = BHDDT.get(0).getNguon_moi();
        ngaynhap_moi = BHDDT.get(0).getNgaynhap_moi();
        von_moi = BHDDT.get(0).getVon_moi();
        gia_moi = BHDDT.get(0).getGia_moi();
        lydo = BHDDT.get(0).getLydo();
        gio_moi = BHDDT.get(0).getGio_moi();
        phidoiSP = BHDDT.get(0).getPhidoisp();
        chechlech = BHDDT.get(0).getChenhlech();
        tvmaBHDDT.setText("Mã BH: "+maBH);
        tvd1Datetoday.setText(dateToday);
        tvd1Timetoday.setText(timeToday);
        tvChinhanhDDT.setText(chinhanhToday);
        tvd1TenNVToday.setText("Mã NV bảo hành: "+ maNVToday);
        tvd1MaNVToday.setText("Tên NV bảo hành: " + tenNVToday);
        tvd1MaOrder.setText("Mã đơn hàng: " + maOrder);
        tvd1Date.setText(date);
        tvd1Time.setText(time);
        tvChinhanhDDTOrder.setText(chinhanh);
        tvd1MaNV.setText("Mã NV: " + maNV);
        tvd1TenNV.setText("Tên NV: " + tenNV);
        list.add(new Sanpham_gio(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia));
        adapter = new Adapter_Info_Order(Main_Info_BHDDT.this, list);
        lvBHDDT.setAdapter(adapter);
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
        list_moi.add(new Sanpham_gio(gio_moi, ma_moi, ten_moi, baohanh_moi, nguon_moi, ngaynhap_moi, von_moi, gia_moi));
        adapter_moi = new Adapter_Info_Order(Main_Info_BHDDT.this, list_moi);
        lvBHDDT_moi.setAdapter(adapter_moi);
        tvlydod1.setText("Lý do: " + lydo);
        tvDlkChenhlech.setText(Keys.getFormatedAmount(Integer.valueOf(chechlech)));
        tvphidoiSP.setText(Keys.getFormatedAmount(Integer.valueOf(phidoiSP)));
    }

    private void Anhxa() {
        tvDlkChenhlech = (TextView) findViewById(R.id.tvDlkChenhlech);
        tvphidoiSP = (TextView) findViewById(R.id.tvphidoiSP);
        lvBHDDT = (ListView) findViewById(R.id.lvBHD1);
        lvBHDDT_moi = (ListView) findViewById(R.id.lvBHDDT_moi);
        tvlydod1 = (TextView) findViewById(R.id.tvlydoBHD1);
        tvmaBHDDT = (TextView) findViewById(R.id.tvmaBHDDT);
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
        tvChinhanhDDT = (TextView) findViewById(R.id.tvChinhanhDDT);
        tvChinhanhDDTOrder = (TextView) findViewById(R.id.tvChinhanhDDTOrder);
        tvd1GhichuKH = (TextView) findViewById(R.id.tvd1GhichuKH);
        tvd1TenNVToday = (TextView) findViewById(R.id.tvd1TenNVNhan);
        tvd1MaNVToday = (TextView) findViewById(R.id.tvd1MaNVNhan);
        tvphibaohanh = (TextView) findViewById(R.id.tvphibaohanh);
    }

}
