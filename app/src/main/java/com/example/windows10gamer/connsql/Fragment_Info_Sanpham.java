//package com.example.windows10gamer.connsql;
//
//import android.os.Bundle;
//import android.support.annotation.Nullable;
//import android.support.v4.app.Fragment;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
///**
// * Created by EVRESTnhan on 9/23/2017.
// */
//
//public class Fragment_Info_Sanpham extends Fragment {
//    TextView tvMaInfo, tvTenInfo, tvDesInfo, tvGiaInfo;
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        final View view = inflater.inflate(R.layout.info_sanpham, container, false);
//        tvMaInfo  = (TextView) view.findViewById(R.id.tvMaInfo);
//        tvTenInfo = (TextView) view.findViewById(R.id.tvTenInfo);
//        tvDesInfo = (TextView) view.findViewById(R.id.tvDesInfo);
//        tvGiaInfo = (TextView) view.findViewById(R.id.tvGiaInfo);
//        return view;
//    }
//
//    public void SetInfo(Sanpham sanpham){
//        tvMaInfo.setText("Mã sản phẩm: " + sanpham.getMa());
//        tvTenInfo.setText("Tên sản phẩm: " + sanpham.getTen());
//        tvGiaInfo.setText("Mã sản phẩm: " + sanpham.getGiaban() + "VNĐ");
//        tvDesInfo.setText("Thông số kỹ thuật: \n" + sanpham.getDes());
//    }
//}
