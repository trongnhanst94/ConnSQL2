//package com.example.windows10gamer.connsql;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v7.app.AppCompatActivity;
//
//public class Activity_Info_Sanpham extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_info_sanpham);
//
//        Intent intent = getIntent();
//        Sanpham sanpham = (Sanpham) intent.getSerializableExtra("thongtinsanpham");
//        Fragment_Info_Sanpham fragment_info_sanpham = (Fragment_Info_Sanpham)
//                getSupportFragmentManager().findFragmentById(R.id.activity_info);
//        fragment_info_sanpham.SetInfo( sanpham);
//    }
//}
