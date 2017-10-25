package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Kiemkho;
import com.example.windows10gamer.connsql.R;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by EVRESTnhan on 10/1/2017.
 */

public class Adapter_List_Kho extends BaseAdapter implements Serializable{
    private Context context;
    private List<Kiemkho> kiemkhos;
    private int layout;

    public Adapter_List_Kho(Context context, int layout, List<Kiemkho> kiemkho) {
        this.context = context;
        this.kiemkhos = kiemkho;
        this.layout = layout;
    }

    private class ViewHolder{
        TextView tvKhoNgay, tvKhoGio, tvKhoTennhanvien, tvKhoTen, tvKhoMa, tvKhoBaohanh, tvKhoGia, tvKhoNguon;
    }

    @Override
    public int getCount() {
        return kiemkhos.size();
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
            holder.tvKhoNgay = (TextView) view.findViewById(R.id.tvKhoNgay);
            holder.tvKhoTennhanvien = (TextView) view.findViewById(R.id.tvKhoTennhanvien);
            holder.tvKhoTen = (TextView) view.findViewById(R.id.tvKhoTen);
            holder.tvKhoMa = (TextView) view.findViewById(R.id.tvKhoMa);
            holder.tvKhoBaohanh = (TextView) view.findViewById(R.id.tvKhoBaohanh);
            holder.tvKhoGia = (TextView) view.findViewById(R.id.tvKhoGia);
            holder.tvKhoNguon = (TextView) view.findViewById(R.id.tvKhoNguon);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Kiemkho kiemkho = kiemkhos.get(i);

        holder.tvKhoNgay.setText(kiemkho.getGio()+" "+kiemkho.getNgay());
        holder.tvKhoTennhanvien.setText(kiemkho.getTenNhanvien());
        holder.tvKhoTen.setText(kiemkho.getTen());
        holder.tvKhoMa.setText("MSP: "+kiemkho.getMa());
        holder.tvKhoBaohanh.setText("BH: "+kiemkho.getBaohanh());
        holder.tvKhoNguon.setText("Nguồn: "+kiemkho.getNguon());
        if (kiemkho.getGia().equals("")){
            holder.tvKhoGia.setText("Giá bán: không có.");
        } else {
            holder.tvKhoGia.setText("Giá bán: "+ getFormatedAmount(Integer.parseInt(kiemkho.getGia())));
        }

        return view;
    }

    private String getFormatedAmount(int amount){
        String number = NumberFormat.getNumberInstance(Locale.US).format(amount);
        String formatnumber = number.replace(",",".");
        return formatnumber+"đ";
    }

}

