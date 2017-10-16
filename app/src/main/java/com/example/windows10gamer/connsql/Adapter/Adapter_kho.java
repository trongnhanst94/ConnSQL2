package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;
import com.example.windows10gamer.connsql.Object.Sanpham;

import java.io.Serializable;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by EVRESTnhan on 10/1/2017.
 */

public class Adapter_kho extends BaseAdapter implements Serializable{
    private Context context;
    private List<Sanpham> sanphams;
    private int layout;

    public Adapter_kho(Context context, int layout, List<Sanpham> sanphams) {
        this.context = context;
        this.sanphams = sanphams;
        this.layout = layout;
    }

    private class ViewHolder{
        TextView tvAdapterSalesMa, tvAdapterSalesTen, tvAdapterSalesGia, tvAdapterSalesBaohanh, tvAdapterSalesNguon, tvAdapterSalesNgaynhap;
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
            holder.tvAdapterSalesMa = (TextView) view.findViewById(R.id.textViewma);
            holder.tvAdapterSalesTen = (TextView) view.findViewById(R.id.textViewten);
            holder.tvAdapterSalesGia = (TextView) view.findViewById(R.id.textViewgia);
            holder.tvAdapterSalesBaohanh = (TextView) view.findViewById(R.id.textViewbaohanh);
            holder.tvAdapterSalesNguon = (TextView) view.findViewById(R.id.textViewnguon);
            holder.tvAdapterSalesNgaynhap = (TextView) view.findViewById(R.id.textViewngaynhap);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Sanpham sanpham = sanphams.get(i);

        holder.tvAdapterSalesMa.setText("MSP: " + sanpham.getMa());
        holder.tvAdapterSalesTen.setText("Tên sản phẩm: " + sanpham.getTen());
        holder.tvAdapterSalesNguon.setText("Nguồn: " + sanpham.getNguon());
        holder.tvAdapterSalesNgaynhap.setText("Ngày nhập: " + Keys.setDate(sanpham.getNgaynhap()));
        holder.tvAdapterSalesBaohanh.setText("Bảo hành: " + sanpham.getBaohanh());
        if (sanpham.getGiaban().equals("")){
            holder.tvAdapterSalesGia.setText("Giá bán: không có.");
        } else {
            holder.tvAdapterSalesGia.setText("Giá bán: "+ getFormatedAmount(Integer.parseInt(sanpham.getGiaban())) +" đ");
        }

        return view;
    }

    private String getFormatedAmount(int amount){
        String number = NumberFormat.getNumberInstance(Locale.US).format(amount);
        String formatnumber = number.replace(",",".");
        return formatnumber;
    }

}

