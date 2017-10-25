package com.example.windows10gamer.connsql;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.CountSanpham;
import com.example.windows10gamer.connsql.Object.CountSanpham2;
import com.example.windows10gamer.connsql.Object.Kiemkho;
import com.example.windows10gamer.connsql.Other.CustomToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main_List_Lechkho extends AppCompatActivity {
    ArrayList<CountSanpham> giong, khac, ga, gb, fullga, fullgb, showa, showb;
    ArrayList<CountSanpham2> fullga2, fullgb2, fullgiong;
    String nameUserA, nameUserB;
    TableLayout stk;
    CheckBox cbGiong, cbKhac;
    Button btnLoc;
    ArrayList<Kiemkho> donhang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_lechkho);
        cbGiong = (CheckBox) findViewById(R.id.cbGiongLechkho);
        cbKhac  = (CheckBox) findViewById(R.id.cbKhacLechkho);
        btnLoc  = (Button)   findViewById(R.id.btnLocLechkho);
        donhang = new ArrayList<>();
        giong = new ArrayList<>();
        khac = new ArrayList<>();
        ga = new ArrayList<>();
        gb = new ArrayList<>();
        fullga = new ArrayList<>();
        fullgb = new ArrayList<>();
        fullga2 = new ArrayList<>();
        fullgb2 = new ArrayList<>();
        fullgiong = new ArrayList<>();
        showa = new ArrayList<>();
        showb = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("LechkhoBundle");
        donhang = bundle.getParcelableArrayList("donhang");
        giong = bundle.getParcelableArrayList("giong");
        khac = bundle.getParcelableArrayList("khac");
        ga = bundle.getParcelableArrayList("ga");
        gb = bundle.getParcelableArrayList("gb");
        nameUserA = bundle.getString("nameUserA");
        nameUserB = bundle.getString("nameUserB");
        fullga.addAll(FullList(khac, ga));
        fullgb.addAll(FullList(khac, gb));
        fullga2.addAll(iniTen(donhang, fullga));
        fullgb2.addAll(iniTen(donhang, fullgb));
        fullgiong.addAll(iniTen(donhang, giong));
        sortList(fullga2);
        sortList(fullgb2);
        init(fullga2, fullgb2);
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stk.removeAllViews();
                if (cbGiong.isChecked()){
                    if (cbKhac.isChecked()){
                        sortList(fullga2);
                        sortList(fullgb2);
                        init(fullga2, fullgb2);
                    } else {
                        sortList(fullgiong);
                        init(fullgiong, fullgiong );
                    }
                } else {
                    if (cbKhac.isChecked()){
                        sortList(fullga2);
                        sortList(fullgb2);
                        ArrayList<CountSanpham2> tamA, tamB;
                        tamA = new ArrayList<>(); tamB = new ArrayList<>();
                        tamA.addAll(fullga2); tamB.addAll(fullgb2);
                        for (int i = 0; i < fullgiong.size(); i++) {
                            for (int j = 0; j < tamA.size(); j++) {
                                if ((fullgiong.get(i).getMa()).equals(tamB.get(j).getMa()) && (fullgiong.get(i).getSoluong() == tamB.get(j).getSoluong())) {
                                    tamA.remove(j);
                                    tamB.remove(j);
                                }
                            }
                        }
                        init(tamA, tamB);
                    } else {
                        new CustomToast().Show_Toast(Main_List_Lechkho.this, v, "Không có dữ liệu!");
                    }
                }
            }
        });
    }

    private ArrayList<CountSanpham2> iniTen(ArrayList<Kiemkho> donhang, ArrayList<CountSanpham> list) {
        ArrayList<CountSanpham2> full = new ArrayList<>();
        for (int i = 0; i<list.size(); i++){
            int result = sosanh2(donhang, list.get(i).getMasanpham());
            if (result != -1){
                full.add(new CountSanpham2(list.get(i).getMasanpham(), donhang.get(result).getTen(), list.get(i).getNhanvien(), list.get(i).getSoluong()));
            }
        }
        return full;
    }

    private void sortList(ArrayList<CountSanpham2> list) {
        Collections.sort(list, new Comparator<CountSanpham2>() {
            @Override
            public int compare(CountSanpham2 lhs, CountSanpham2 rhs) {
                return lhs.getMa().compareTo(rhs.getMa());
            }
        });
    }

    private ArrayList<CountSanpham> FullList(ArrayList<CountSanpham> khac, ArrayList<CountSanpham> ga) {
        ArrayList<CountSanpham> full = new ArrayList<>();
        full.addAll(ga);
        for (int i = 0; i<khac.size(); i++){
            int result = sosanh(ga, khac.get(i).getMasanpham(), khac.get(i).getSoluong());
            if (result == -1){
                full.add(new CountSanpham(ga.get(0).getNhanvien(), khac.get(i).getMasanpham(), 0));
            }
        }
        return full;
    }

    private int sosanh2(ArrayList<Kiemkho> dem_h, String masanpham) {
        int result = -1;
        if (dem_h.size() != 0){
            for (int i = 0; i < dem_h.size(); i++){
                if (dem_h.get(i).getMa().equals(masanpham)){
                    result = i;
                }
            }
        }
        return result;
    }

    private int sosanh(ArrayList<CountSanpham> dem_h, String masanpham, int soluong) {
        int result = -1;
        if (dem_h.size() != 0){
            for (int i = 0; i < dem_h.size(); i++){
                if (dem_h.get(i).getMasanpham().equals(masanpham)){
                    result = i;
                }
            }
        }
        return result;
    }

    public void init(ArrayList<CountSanpham2> fullga, ArrayList<CountSanpham2> fullgb) {
        //TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(500, 500);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT);
        stk = (TableLayout) findViewById(R.id.tbLechkho);
  //      stk.setLayoutParams(tableParams);
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setLayoutParams(rowParams);
        TextView tv0 = new TextView(this);
        tv0.setText(" STT ");
        tv0.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv0.setBackgroundResource(R.drawable.cell_shape);
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" MSP ");
        tv1.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv1.setBackgroundResource(R.drawable.cell_shape);
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(" Tên sản phẩm ");
        tv2.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv2.setBackgroundResource(R.drawable.cell_shape);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(nameUserA);
        tv3.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv3.setBackgroundResource(R.drawable.cell_shape);
        tbrow0.addView(tv3);
        TextView tv4 = new TextView(this);
        tv4.setText(nameUserB);
        tv4.setTypeface(tv0.getTypeface(), Typeface.BOLD);
        tv4.setBackgroundResource(R.drawable.cell_shape);
        tbrow0.addView(tv4);
        stk.addView(tbrow0);
        int stt = 1;
        for (int i = 0; i < fullga.size(); i++) {
            if (fullga.get(i).getSoluong() == fullgb.get(i).getSoluong()){
                TableRow tbrow = new TableRow(this);
                tbrow.setLayoutParams(rowParams);
                TextView t1v = new TextView(this);
                t1v.setText(stt+"");
                t1v.setGravity(Gravity.CENTER);
                t1v.setBackgroundResource(R.drawable.cell_shape);
                t1v.setTextColor(Color.BLUE);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setText(fullga.get(i).getMa());
                t2v.setGravity(Gravity.CENTER);
                t2v.setBackgroundResource(R.drawable.cell_shape);
                t2v.setTextColor(Color.BLUE);
                tbrow.addView(t2v);
                TextView t3v = new TextView(this);
                t3v.setText(fullga.get(i).getTenSanpham());
                t3v.setGravity(Gravity.CENTER);
                t3v.setBackgroundResource(R.drawable.cell_shape);
                t3v.setTextColor(Color.BLUE);
                tbrow.addView(t3v);
                TextView t4v = new TextView(this);
                t4v.setText(fullga.get(i).getSoluong()+"");
                t4v.setGravity(Gravity.CENTER);
                t4v.setBackgroundResource(R.drawable.cell_shape);
                t4v.setTextColor(Color.BLUE);
                tbrow.addView(t4v);
                TextView t5v = new TextView(this);
                t5v.setText(fullgb.get(i).getSoluong()+"");
                t5v.setGravity(Gravity.CENTER);
                t5v.setBackgroundResource(R.drawable.cell_shape);
                t5v.setTextColor(Color.BLUE);
                tbrow.addView(t5v);
                stk.addView(tbrow);
                stt++;
            } else {
                TableRow tbrow = new TableRow(this);
                tbrow.setLayoutParams(rowParams);
                tbrow.setWeightSum(10);
                TextView t1v = new TextView(this);
                t1v.setText(stt+"");
                t1v.setGravity(Gravity.CENTER);
                t1v.setBackgroundResource(R.drawable.cell_shape);
                t1v.setTextColor(Color.RED);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setText(fullga.get(i).getMa());
                t2v.setGravity(Gravity.CENTER);
                t2v.setBackgroundResource(R.drawable.cell_shape);
                t2v.setTextColor(Color.RED);
                tbrow.addView(t2v);
                TextView t3v = new TextView(this);
                t3v.setText(fullga.get(i).getTenSanpham());
                t3v.setGravity(Gravity.CENTER);
                t3v.setBackgroundResource(R.drawable.cell_shape);
                t3v.setTextColor(Color.RED);
                tbrow.addView(t3v);
                TextView t4v = new TextView(this);
                t4v.setText(fullga.get(i).getSoluong()+"");
                t4v.setGravity(Gravity.CENTER);
                t4v.setBackgroundResource(R.drawable.cell_shape);
                t4v.setTextColor(Color.RED);
                tbrow.addView(t4v);
                TextView t5v = new TextView(this);
                t5v.setText(fullgb.get(i).getSoluong()+"");
                t5v.setGravity(Gravity.CENTER);
                t5v.setBackgroundResource(R.drawable.cell_shape);
                t5v.setTextColor(Color.RED);
                tbrow.addView(t5v);
                stk.addView(tbrow);
                stt++;
            }
        }
    }

}
