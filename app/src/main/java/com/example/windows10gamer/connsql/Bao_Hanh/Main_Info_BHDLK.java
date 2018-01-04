package com.example.windows10gamer.connsql.Bao_Hanh;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Adapter.Adapter_Info_Order;
import com.example.windows10gamer.connsql.Object.BHDLK;
import com.example.windows10gamer.connsql.Object.Sanpham_gio;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.Other.Mylistview;
import com.example.windows10gamer.connsql.R;

import java.util.ArrayList;

public class Main_Info_BHDLK extends AppCompatActivity {
    String maBH, dateToday, gio, gio_moi, timeToday, chinhanhToday, tenNVToday, maNVToday, maOrder, date, time, chinhanh, tenNV, maNV, ten, ma, baohanh, nguon, ngaynhap, von, gia, ghichuOrder, tenKH, sdtKH, ghichuKH, phidoiSP, lydo, chechlech;
    TextView tvDlkMaOrder,tvDlkDate,tvDlkTime,tvDlkMaNV,tvDlkTenNV,tvDlkGhichuOrder,tvDlkTenKH,tvphidoiSP,tvlydoDLK,tvmaBHDLK,
            tvDlkSdtKH, tvDlkChenhlech, tvDlkGhichuKH,tvDlkTenNVNhan,tvDlkMaNVNhan,tvDlkDatetoday,tvDlkTimetoday, tvTongdonhang,tvChinhanhDLK,tvChinhanhDLKOrder ;
    ListView lv;
    Mylistview lv_moi;
    ArrayList<BHDLK> BHDLK = new ArrayList<>();
    ArrayList<Sanpham_gio> list, list_moi;
    Adapter_Info_Order adapter, adapter_moi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_info_bhdlk);
        Anhxa();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("InfoBHDLK");
        BHDLK = bundle.getParcelableArrayList("listBHDLK");
        list = new ArrayList<>();
        list_moi = new ArrayList<>();
        maBH = BHDLK.get(0).getMaBH();
        dateToday = BHDLK.get(0).getDateToday();
        timeToday = BHDLK.get(0).getTimeToday();
        chinhanhToday = BHDLK.get(0).getChinhanhToday();
        maNVToday = BHDLK.get(0).getMaNVToday();
        tenNVToday = BHDLK.get(0).getTenNVToday();
        maOrder = BHDLK.get(0).getMaOrder();
        date = BHDLK.get(0).getDate();
        time = BHDLK.get(0).getTime();
        gio = BHDLK.get(0).getGio();
        phidoiSP = BHDLK.get(0).getPhidoiSP();
        chinhanh = BHDLK.get(0).getChinhanh();
        tenNV = BHDLK.get(0).getTenNV();
        maNV = BHDLK.get(0).getMaNV();
        ten = BHDLK.get(0).getTen();
        ma = BHDLK.get(0).getMa();
        baohanh = BHDLK.get(0).getBaohanh();
        nguon = BHDLK.get(0).getNguon();
        ngaynhap = BHDLK.get(0).getNgaynhap();
        von = BHDLK.get(0).getVon();
        chechlech = BHDLK.get(0).getChechlech();
        gia = BHDLK.get(0).getGia();
        list.add(new Sanpham_gio(gio, ma, ten, baohanh, nguon, ngaynhap, von, gia));
        ghichuOrder = BHDLK.get(0).getGhichuOrder();
        tenKH = BHDLK.get(0).getTenKH();
        sdtKH = BHDLK.get(0).getSdtKH();
        ghichuKH = BHDLK.get(0).getGhichuKH();
        gio_moi = BHDLK.get(0).getGio_moi();
        int tongdon = 0;
        for (int i = 0; i < BHDLK.size(); i++){
            list_moi.add(new Sanpham_gio(BHDLK.get(i).getGio_moi(), BHDLK.get(i).getMa_moi(), BHDLK.get(i).getTen_moi(), BHDLK.get(i).getBaohanh_moi(), BHDLK.get(i).getNguon_moi(), BHDLK.get(i).getNgaynhap_moi(), BHDLK.get(i).getVon_moi(), BHDLK.get(i).getGia_moi()));
            tongdon = tongdon + Integer.parseInt(BHDLK.get(i).getGia_moi());
        }
        lydo = BHDLK.get(0).getLydo();
        tvmaBHDLK.setText("Mã BH: "+maBH);
        tvDlkDatetoday.setText(dateToday);
        tvDlkTimetoday.setText(timeToday);
        tvChinhanhDLK.setText(chinhanhToday);
        tvDlkTenNVNhan.setText("Mã NV bảo hành: "+ maNVToday);
        tvDlkMaNVNhan.setText("Tên NV bảo hành: " + tenNVToday);
        tvDlkMaOrder.setText("Mã đơn hàng: " + maOrder);
        tvDlkDate.setText(date);
        tvDlkTime.setText(time);
        tvChinhanhDLKOrder.setText(chinhanh);
        tvDlkMaNV.setText("Mã số: " + maNV);
        tvDlkTenNV.setText("Tên nhân viên: " + tenNV);
        tvTongdonhang.setText(Keys.setMoney(tongdon));
        adapter = new Adapter_Info_Order(Main_Info_BHDLK.this, list);
        lv.setAdapter(adapter);
        if (ghichuKH.equals("")) {
            tvDlkGhichuKH.setVisibility(View.GONE);
        } else {
            tvDlkGhichuKH.setText("Ghi chú KH: " + ghichuKH);
        }
        if (ghichuOrder.equals("")) {
            tvDlkGhichuOrder.setVisibility(View.GONE);
        } else {
            tvDlkGhichuOrder.setText("Ghi chú đơn hàng: " + ghichuOrder);
        }
        tvDlkTenKH.setText("Tên KH: " + tenKH);
        tvDlkSdtKH.setText("SĐT KH: " + sdtKH);
        adapter_moi = new Adapter_Info_Order(Main_Info_BHDLK.this, list_moi);
        lv_moi.setAdapter(adapter_moi);
        tvlydoDLK.setText("Lý do: " + lydo);
        tvDlkChenhlech.setText(Keys.setMoney(Integer.valueOf(chechlech)));
        tvphidoiSP.setText(Keys.setMoney(Integer.valueOf(phidoiSP)));
    }

    private void Anhxa() {
        tvDlkChenhlech = (TextView) findViewById(R.id.tvDlkChenhlech);
        tvphidoiSP = (TextView) findViewById(R.id.tvphidoiSP);
        tvlydoDLK = (TextView) findViewById(R.id.tvlydoBHDLK);
        tvDlkMaOrder = (TextView) findViewById(R.id.tvDlkMaOrder);
        tvDlkDate = (TextView) findViewById(R.id.tvDlkDate);
        tvDlkTime = (TextView) findViewById(R.id.tvDlkTime);
        tvTongdonhang     = (TextView) findViewById(R.id.tvTongdonhang);
        tvDlkDatetoday = (TextView) findViewById(R.id.tvDlkDatetoday);
        tvDlkTimetoday = (TextView) findViewById(R.id.tvDlkTimetoday);
        tvDlkMaNV = (TextView) findViewById(R.id.tvDlkMaNV);
        tvChinhanhDLK = (TextView) findViewById(R.id.tvChinhanhDLK);
        tvChinhanhDLKOrder = (TextView) findViewById(R.id.tvChinhanhDLKOrder);
        tvDlkTenNV = (TextView) findViewById(R.id.tvDlkTenNV);
        tvDlkGhichuOrder = (TextView) findViewById(R.id.tvDlkGhichu);
        tvDlkTenKH = (TextView) findViewById(R.id.tvDlkTenKH);
        tvDlkSdtKH = (TextView) findViewById(R.id.tvDlkSdtKH);
        lv = (ListView) findViewById(R.id.lvBHDLK);
        lv_moi = (Mylistview) findViewById(R.id.lvBHDLK_moi);
        tvDlkGhichuKH = (TextView) findViewById(R.id.tvDlkGhichuKH);
        tvDlkTenNVNhan = (TextView) findViewById(R.id.tvDlkTenNVNhan);
        tvDlkMaNVNhan = (TextView) findViewById(R.id.tvDlkMaNVNhan);
        tvmaBHDLK = (TextView) findViewById(R.id.tvmaBHDLK);
    }
}
