package com.example.windows10gamer.connsql;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.Other.Put_Sanpham;

public class MainActivity extends AppCompatActivity implements Put_Sanpham {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void DataSanpham(Sanpham sanpham) {
//        Fragment_Info_Sanpham fragment_info_sanpham = (Fragment_Info_Sanpham)
//                getSupportFragmentManager().findFragmentById(R.id.fragmentInfo);
//        if (fragment_info_sanpham != null && fragment_info_sanpham.isInLayout()){
//            fragment_info_sanpham.SetInfo(sanpham);
//        } else {
//
//            Intent intent = new Intent(this, Activity_Info_Sanpham.class);
//            intent.putExtra("thongtinsanpham", sanpham);
//            startActivity(intent);
//        }
    }
}
