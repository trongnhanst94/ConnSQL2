package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Kho.Main_AddKho;
import com.example.windows10gamer.connsql.Object.SanphamAmount;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EVRESTnhan on 4/3/2018.
 */

public class Adapter_AddKho extends BaseAdapter implements Serializable {
    private Main_AddKho context;
    private List<SanphamAmount> sanphams;
    private int layout;
    private List<String> strings;

    public Adapter_AddKho(Main_AddKho context, int layout, List<SanphamAmount> sanphams, List<String> strings) {
        this.context = context;
        this.sanphams = sanphams;
        this.layout = layout;
        this.strings = strings;
    }

    private class ViewHolder{
        TextView tvAdapterSalesMa, tvAdapterSalesTen, tvAdapterSalesGia, tvAdapterSalesBaohanh, tvAdapterSalesNgaynhap;
        EditText edsoluong;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        final ViewHolder holder;
        if(view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.tvAdapterSalesMa = view.findViewById(R.id.tvAdapterSalesMa);
            holder.edsoluong = view.findViewById(R.id.edsoluong);
            holder.tvAdapterSalesBaohanh = view.findViewById(R.id.tvAdapterSalesBaohanh);
            holder.tvAdapterSalesNgaynhap = view.findViewById(R.id.tvAdapterSalesNgaynhap);
            holder.tvAdapterSalesTen = view.findViewById(R.id.tvAdapterSalesTen);
            holder.tvAdapterSalesGia = view.findViewById(R.id.tvAdapterSalesGia);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final SanphamAmount sanpham = sanphams.get(i);
        holder.tvAdapterSalesTen.setText("SP: "+ sanpham.getTen());
        holder.tvAdapterSalesMa.setText("MSP: "+ sanpham.getMa());
        holder.tvAdapterSalesGia.setText("Giá bán: "+ Keys.setMoney(Integer.parseInt(sanpham.getGiaban())));
        holder.tvAdapterSalesBaohanh.setText("Bảo hành: " + sanpham.getBaohanh());
        holder.tvAdapterSalesNgaynhap.setText("Quét lúc: "+ sanpham.getGio());
        holder.edsoluong.setText(sanpham.getSoluong());
        holder.edsoluong.setInputType(InputType.TYPE_NULL);
        return view;
    }
}

