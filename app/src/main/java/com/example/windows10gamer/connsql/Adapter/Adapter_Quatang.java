package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Main_Sales;
import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EVRESTnhan on 10/23/2017.
 */

public class Adapter_Quatang extends BaseAdapter implements Serializable {
    private Main_Sales context;
    private List<Sanpham> sanphams;
    private int layout;

    public Adapter_Quatang(Main_Sales context, int layout, List<Sanpham> sanphams) {
        this.context = context;
        this.sanphams = sanphams;
        this.layout = layout;
    }

    private class ViewHolder{
        TextView tvAdapterSalesMa, tvAdapterSalesTen, tvAdapterSalesGia;
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
            holder.tvAdapterSalesTen = (TextView) view.findViewById(R.id.tvAdapterSalesTenKM);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        final Sanpham sanpham = sanphams.get(i);

        holder.tvAdapterSalesTen.setText("MSP: "+ sanpham.getMa()+" - "+sanpham.getTen());
        return view;
    }
}

