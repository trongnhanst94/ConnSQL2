package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.DuyetNhap;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;
import com.example.windows10gamer.connsql.Xuat_Nhap.Main_Duyetnhap;

import java.io.Serializable;
import java.util.List;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

/**
 * Created by EVRESTnhan on 10/1/2017.
 */

public class Adapter_Duyetnhap extends BaseAdapter implements Serializable{
    private Main_Duyetnhap context;
    private List<DuyetNhap> list;
    private int layout;

    public Adapter_Duyetnhap(Main_Duyetnhap context, int layout, List<DuyetNhap> list) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    private class ViewHolder{
        TextView tvAdapterSalesMa, tvAdapterSalesTen, tvAdapterSalesGia, tvAdapterSalesNguon, tvAdapterSalesBaohanh, tvAdapterSalesNgaynhap;
        CheckBox cbduyetnhap;
    }

    @Override
    public int getCount() {
        return list.size();
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
            holder.tvAdapterSalesMa = (TextView) view.findViewById(R.id.tvAdapterSalesMa);
            holder.tvAdapterSalesNguon = (TextView) view.findViewById(R.id.tvAdapterSalesNguon);
            holder.tvAdapterSalesBaohanh = (TextView) view.findViewById(R.id.tvAdapterSalesBaohanh);
            holder.tvAdapterSalesNgaynhap = (TextView) view.findViewById(R.id.tvAdapterSalesNgaynhap);
            holder.tvAdapterSalesTen = (TextView) view.findViewById(R.id.tvAdapterSalesTen);
            holder.tvAdapterSalesGia = (TextView) view.findViewById(R.id.tvAdapterSalesGia);
            holder.cbduyetnhap = (CheckBox) view.findViewById(R.id.cbduyetnhap);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvAdapterSalesTen.setText("SP: "+list.get(i).getTen());
        holder.tvAdapterSalesMa.setText("MSP: "+ list.get(i).getMa());
        holder.tvAdapterSalesGia.setText("Giá bán: "+ Keys.getFormatedAmount(Integer.parseInt(list.get(i).getGia())));
        holder.tvAdapterSalesBaohanh.setText("Bảo hành: " + list.get(i).getBaohanh());
        holder.tvAdapterSalesNgaynhap.setText("Quét lúc: "+list.get(i).getGio());
        holder.tvAdapterSalesNguon.setText("Nguồn: " + list.get(i).getNguon());
        if (list.get(i).getTrangthai().equals("1")){
            holder.cbduyetnhap.setChecked(TRUE);
        } else {
            holder.cbduyetnhap.setChecked(FALSE);
        }
        holder.cbduyetnhap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Check(i);
                } else {
                    Uncheck(i);
                }
            }
        });
        return view;
    }

    private void Check(int position) {
        context.check(position);
    }

    private void Uncheck(int position) {
        context.uncheck(position);
    }
}

