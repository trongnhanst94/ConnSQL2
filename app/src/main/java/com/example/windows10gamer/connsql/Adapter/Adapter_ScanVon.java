package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Sanpham;
import com.example.windows10gamer.connsql.R;

import java.util.ArrayList;

/**
 * Created by EVRESTnhan on 1/18/2018.
 */

public class Adapter_ScanVon extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayList;

    public Adapter_ScanVon(Context context, ArrayList<Sanpham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.adapter_scan_von, null);
            holder.ma = convertView.findViewById(R.id.ma);
            holder.ten = convertView.findViewById(R.id.ten);
            holder.baohanh = convertView.findViewById(R.id.baohanh);
            holder.nguon = convertView.findViewById(R.id.nguon);
            holder.ngaynhap = convertView.findViewById(R.id.ngaynhap);
            holder.von = convertView.findViewById(R.id.von);
            holder.gia = convertView.findViewById(R.id.gia);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Sanpham sanpham = arrayList.get(position);
        holder.ma.setText(sanpham.getMa());
        holder.ten.setText(sanpham.getTen());
        holder.baohanh.setText(sanpham.getBaohanh());
        holder.nguon.setText(sanpham.getNguon());
        holder.ngaynhap.setText(sanpham.getNgaynhap());
        holder.von.setText(sanpham.getVon());
        holder.gia.setText(sanpham.getGiaban());

        return convertView;
    }

    private class ViewHolder {
        TextView ma, ten, baohanh, nguon, ngaynhap, von, gia;
    }
}
