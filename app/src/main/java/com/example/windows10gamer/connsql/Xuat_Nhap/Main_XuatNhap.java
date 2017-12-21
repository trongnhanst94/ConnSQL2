package com.example.windows10gamer.connsql.Xuat_Nhap;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.windows10gamer.connsql.Other.Connect_Internet;
import com.example.windows10gamer.connsql.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Main_XuatNhap extends AppCompatActivity {

    @BindView(R.id.ivtaoxuat)
    ImageView ivtaoxuat;
    @BindView(R.id.ivunchecknhan)
    ImageView ivunchecknhan;
    @BindView(R.id.ivchecknhan)
    ImageView ivchecknhan;
    @BindView(R.id.ivlistxuat)
    ImageView ivlistxuat;
    @BindView(R.id.ivlistnhan)
    ImageView ivlistnhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_xuat_nhap);
        ButterKnife.bind(this);
        ivchecknhan = (ImageView) findViewById(R.id.ivchecknhan);
        ivunchecknhan = (ImageView) findViewById(R.id.ivunchecknhan);
        ivtaoxuat = (ImageView) findViewById(R.id.ivtaoxuat);
        ivlistnhan = (ImageView) findViewById(R.id.ivlistnhan);
        ivlistxuat = (ImageView) findViewById(R.id.ivlistxuat);
        ivtaoxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_XuatNhap.this).show();
                else {
                    startActivity(new Intent(Main_XuatNhap.this, Main_Taoxuat.class));
                }
            }
        });
        ivlistxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_XuatNhap.this).show();
                else {
                    startActivity(new Intent(Main_XuatNhap.this, Main_Danhsach_Xuathang.class));
                }
            }
        });
        ivlistnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_XuatNhap.this).show();
                else {
                    startActivity(new Intent(Main_XuatNhap.this, Main_XNOpen.class));
                }
            }
        });
        ivchecknhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!Connect_Internet.checkConnection(getApplicationContext()))
                    Connect_Internet.buildDialog(Main_XuatNhap.this).show();
                else {
                    startActivity(new Intent(Main_XuatNhap.this, Main_Danhsach_Nhaphang.class));
                }
            }
        });
    }
}
