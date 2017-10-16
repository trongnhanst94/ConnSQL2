package com.example.windows10gamer.connsql;

import android.content.Intent;
import android.graphics.Color;
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
import com.example.windows10gamer.connsql.Other.CustomToast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class Main_List_Lechkho extends AppCompatActivity {
    ArrayList<CountSanpham> giong, khac, ga, gb, fullga, fullgb, showa, showb;
    String nameUserA, nameUserB;
    TableLayout stk;
    CheckBox cbGiong, cbKhac;
    Button btnLoc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list_lechkho);
        cbGiong = (CheckBox) findViewById(R.id.cbGiongLechkho);
        cbKhac  = (CheckBox) findViewById(R.id.cbKhacLechkho);
        btnLoc  = (Button)   findViewById(R.id.btnLocLechkho);
        giong = new ArrayList<>();
        khac = new ArrayList<>();
        ga = new ArrayList<>();
        gb = new ArrayList<>();
        fullga = new ArrayList<>();
        fullgb = new ArrayList<>();
        showa = new ArrayList<>();
        showb = new ArrayList<>();
        Intent intent = getIntent();
        Bundle bundle = intent.getBundleExtra("LechkhoBundle");
        giong = bundle.getParcelableArrayList("giong");
        khac = bundle.getParcelableArrayList("khac");
        ga = bundle.getParcelableArrayList("ga");
        gb = bundle.getParcelableArrayList("gb");
        nameUserA = bundle.getString("nameUserA");
        nameUserB = bundle.getString("nameUserB");
        fullga.addAll(FullList(khac, ga));
        fullgb.addAll(FullList(khac, gb));
        sortList(fullga);
        sortList(fullgb);
        init(fullga, fullgb );
        btnLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stk.removeAllViews();
                if (cbGiong.isChecked()){
                    if (cbKhac.isChecked()){
                        sortList(fullga);
                        sortList(fullgb);
                        init(fullga, fullgb );
                    } else {
                        sortList(giong);
                        init(giong, giong );
                    }
                } else {
                    if (cbKhac.isChecked()){
                        ArrayList<CountSanpham> tamA = new ArrayList<CountSanpham>();
                        ArrayList<CountSanpham> tamB = new ArrayList<CountSanpham>();
                        tamA.addAll(fullga); tamB.addAll(fullgb);
                        for (int i = 0; i<fullga.size(); i++){
                            if (fullga.get(i).getMasanpham().equals(fullgb.get(i).getMasanpham()) && fullga.get(i).getSoluong() == fullgb.get(i).getSoluong()){
                                tamA.remove(i);
                                tamB.remove(i);
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

    private void sortList(ArrayList<CountSanpham> list) {
        Collections.sort(list, new Comparator<CountSanpham>() {
            @Override
            public int compare(CountSanpham lhs, CountSanpham rhs) {
                return lhs.getMasanpham().compareTo(rhs.getMasanpham());
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

    public void init(ArrayList<CountSanpham> fullga, ArrayList<CountSanpham> fullgb) {
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT, 1f);
        TableRow.LayoutParams rowParams      = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.FILL_PARENT, 1f);
        TableRow.LayoutParams itemParams     = new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT,
                TableRow.LayoutParams.FILL_PARENT, 1f);
        stk = (TableLayout) findViewById(R.id.tbLechkho);
        stk.setLayoutParams(tableParams);
        TableRow tbrow0 = new TableRow(this);
        tbrow0.setLayoutParams(rowParams);
        TextView tv0 = new TextView(this);
        tv0.setText(" STT ");
        tbrow0.addView(tv0);
        TextView tv1 = new TextView(this);
        tv1.setText(" MSP ");
        tbrow0.addView(tv1);
        TextView tv2 = new TextView(this);
        tv2.setText(nameUserA);
        tbrow0.addView(tv2);
        TextView tv3 = new TextView(this);
        tv3.setText(nameUserB);
        tbrow0.addView(tv3);
        stk.addView(tbrow0);
        int stt = 1;
        for (int i = 0; i < fullga.size(); i++) {
            if (fullga.get(i).getSoluong() == fullgb.get(i).getSoluong()){
                TableRow tbrow = new TableRow(this);
                tbrow.setLayoutParams(rowParams);
                TextView t1v = new TextView(this);
                t1v.setText(stt+"");
                t1v.setGravity(Gravity.CENTER);
                t1v.setLayoutParams(itemParams);
                t1v.setTextColor(Color.GREEN);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setText(fullga.get(i).getMasanpham());
                t2v.setGravity(Gravity.CENTER);
                t2v.setLayoutParams(itemParams);
                t2v.setTextColor(Color.GREEN);
                tbrow.addView(t2v);
                TextView t3v = new TextView(this);
                t3v.setText(fullga.get(i).getSoluong()+"");
                t3v.setGravity(Gravity.CENTER);
                t3v.setLayoutParams(itemParams);
                t3v.setTextColor(Color.GREEN);
                tbrow.addView(t3v);
                TextView t4v = new TextView(this);
                t4v.setText(fullgb.get(i).getSoluong()+"");
                t4v.setGravity(Gravity.CENTER);
                t4v.setLayoutParams(itemParams);
                t4v.setTextColor(Color.GREEN);
                tbrow.addView(t4v);
                stk.addView(tbrow);
                stt++;
            } else {
                TableRow tbrow = new TableRow(this);
                TextView t1v = new TextView(this);
                t1v.setText(stt+"");
                t1v.setGravity(Gravity.CENTER);
                t1v.setLayoutParams(itemParams);
                t1v.setTextColor(Color.RED);
                tbrow.addView(t1v);
                TextView t2v = new TextView(this);
                t2v.setText(fullga.get(i).getMasanpham());
                t2v.setGravity(Gravity.CENTER);
                t2v.setLayoutParams(itemParams);
                t2v.setTextColor(Color.RED);
                tbrow.addView(t2v);
                TextView t3v = new TextView(this);
                t3v.setText(fullga.get(i).getSoluong()+"");
                t3v.setGravity(Gravity.CENTER);
                t3v.setLayoutParams(itemParams);
                t3v.setTextColor(Color.RED);
                tbrow.addView(t3v);
                TextView t4v = new TextView(this);
                t4v.setText(fullgb.get(i).getSoluong()+"");
                t4v.setGravity(Gravity.CENTER);
                t4v.setLayoutParams(itemParams);
                t4v.setTextColor(Color.RED);
                tbrow.addView(t4v);
                stk.addView(tbrow);
                stt++;
            }
        }
    }
}
