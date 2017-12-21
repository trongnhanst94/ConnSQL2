package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.XuatNhap;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;
import com.example.windows10gamer.connsql.Xuat_Nhap.Main_Info_Xuathang;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EVRESTnhan on 10/1/2017.
 */

public class Adapter_InfoXuatnhap extends BaseAdapter implements Serializable{
    private Main_Info_Xuathang context;
    private List<XuatNhap> sanphams;
    private int layout;

    public Adapter_InfoXuatnhap(Main_Info_Xuathang context, int layout, List<XuatNhap> sanphams) {
        this.context = context;
        this.sanphams = sanphams;
        this.layout = layout;
    }

    private class ViewHolder{
        TextView tvAdapterSalesMa, tvAdapterSalesTen, tvAdapterSalesGia, tvAdapterSalesNguon, tvAdapterSalesBaohanh, tvAdapterSalesNgaynhap;
        ImageView ivSalesCheck, ivSalesUncheck;
    }

    @Override
    public int getCount() {
        return sanphams.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvAdapterSalesMa = (TextView) view.findViewById(R.id.tvAdapterSalesMa);
            holder.tvAdapterSalesNguon = (TextView) view.findViewById(R.id.tvAdapterSalesNguon);
            holder.tvAdapterSalesBaohanh = (TextView) view.findViewById(R.id.tvAdapterSalesBaohanh);
            holder.tvAdapterSalesNgaynhap = (TextView) view.findViewById(R.id.tvAdapterSalesNgaynhap);
            holder.tvAdapterSalesTen = (TextView) view.findViewById(R.id.tvAdapterSalesTen);
            holder.tvAdapterSalesGia = (TextView) view.findViewById(R.id.tvAdapterSalesGia);
            holder.ivSalesUncheck = (ImageView) view.findViewById(R.id.ivSalesUncheck);
            holder.ivSalesCheck = (ImageView) view.findViewById(R.id.ivSalesCheck);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final XuatNhap sanpham = sanphams.get(i);

        holder.tvAdapterSalesTen.setText("SP: "+sanpham.getTen());
        holder.tvAdapterSalesMa.setText("MSP: "+ sanpham.getMa());
        holder.tvAdapterSalesGia.setText("Giá bán: "+ Keys.getFormatedAmount(Integer.parseInt(sanpham.getGia())));
        holder.tvAdapterSalesBaohanh.setText("Bảo hành: " + sanpham.getBaohanh());
        holder.tvAdapterSalesNgaynhap.setText("Quét lúc: "+sanpham.getGio());
        holder.tvAdapterSalesNguon.setText("Nguồn: " + sanpham.getNguon());
        if (Integer.valueOf(sanpham.getTrangthai())== 1){
            holder.ivSalesCheck.setVisibility(View.VISIBLE);
            holder.ivSalesUncheck.setVisibility(View.GONE);
        } else {
            holder.ivSalesUncheck.setVisibility(View.VISIBLE);
            holder.ivSalesCheck.setVisibility(View.GONE);
        }

        return view;
    }

}

