package com.example.windows10gamer.connsql.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.XuatNhap_SL;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.util.List;

/**
 * Created by EVRESTnhan on 12/17/2017.
 */

public class Adapter_Nhaphang extends BaseAdapter{

    Context context;
    int layout;
    List<XuatNhap_SL> list;

    public Adapter_Nhaphang(Context context, int layout, List<XuatNhap_SL> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvchinhanhNhan = (TextView) view.findViewById(R.id.tvchinhanhNhan);
            holder.tvsoluong = (TextView) view.findViewById(R.id.tvsoluong);
            holder.tvngayXuat = (TextView) view.findViewById(R.id.tvngayXuat);
            holder.tvnguoiXuat = (TextView) view.findViewById(R.id.tvnguoiXuat);
            holder.tvduyetnhap = (TextView) view.findViewById(R.id.tvduyetnhap);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final XuatNhap_SL xuatNhap = list.get(position);
        int phantram = xuatNhap.getPhantram()*100/xuatNhap.getSoluong();
        holder.tvchinhanhNhan.setText(Keys.trimChinhanh(xuatNhap.getChinhanhToday())+ " xuất sang");
        if (phantram < 50){
            holder.tvsoluong.setBackgroundResource(R.drawable.ic_bad);
        } else if (phantram > 50 && phantram < 90){
            holder.tvsoluong.setBackgroundResource(R.drawable.ic_average);
        } else holder.tvsoluong.setBackgroundResource(R.drawable.ic_good);
        holder.tvsoluong.setText(phantram+"%\n("+xuatNhap.getPhantram()+"/"+xuatNhap.getSoluong()+")");
        holder.tvngayXuat.setText("lúc: "+xuatNhap.getGio()+" "+xuatNhap.getNgay());
        holder.tvnguoiXuat.setText("bởi "+xuatNhap.getTenNVToday());
        holder.tvduyetnhap.setVisibility(View.VISIBLE);
        holder.tvduyetnhap.setText("Duyệt Đơn");
        return view;
    }

    private class ViewHolder{
        TextView tvchinhanhNhan, tvsoluong, tvngayXuat, tvnguoiXuat, tvduyetnhap;
    }
}
