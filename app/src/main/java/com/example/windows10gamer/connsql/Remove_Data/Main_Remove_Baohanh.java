package com.example.windows10gamer.connsql.Remove_Data;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.R;

public class Main_Remove_Baohanh extends AppCompatActivity {

    TextView tvht, tv1d1, tvdlk, tvcxl, tvddt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_remove_baohanh);
        tvht = findViewById(R.id.tvht);
        tvht.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Remove_Baohanh.this).show();
                else {
                    startActivity(new Intent(Main_Remove_Baohanh.this, Main_Remove_BHHT.class));
                }
            }
        });
        tvcxl = findViewById(R.id.tvcxl);
        tvcxl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Remove_Baohanh.this).show();
                else {
                    startActivity(new Intent(Main_Remove_Baohanh.this, Main_Remove_BHCXL.class));
                }
            }
        });
        tvddt = findViewById(R.id.tvddt);
        tvddt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Remove_Baohanh.this).show();
                else {
                    startActivity(new Intent(Main_Remove_Baohanh.this, Main_Remove_BHDDT.class));
                }
            }
        });
        tv1d1 = findViewById(R.id.tv1d1);
        tv1d1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Remove_Baohanh.this).show();
                else {
                    startActivity(new Intent(Main_Remove_Baohanh.this, Main_Remove_BH1D1.class));
                }
            }
        });
        tvdlk = findViewById(R.id.tvdlk);
        tvdlk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_Remove_Baohanh.this).show();
                else {
                    startActivity(new Intent(Main_Remove_Baohanh.this, Main_Remove_BHDLK.class));
                }
            }
        });
    }
}
