package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.BH1D1;
import com.example.windows10gamer.connsql.R;

import java.util.List;

/**
 * Created by EVRESTnhan on 11/7/2017.
 */

public class Adapter_Report_BH1D1 extends BaseAdapter{
    private Context context;
    private List<BH1D1> list;
    private int layout;

    public Adapter_Report_BH1D1(Context context, int layout, List<BH1D1> list) {
        this.context = context;
        this.list = list;
        this.layout = layout;
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

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        final ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.maBH        = (TextView) view.findViewById(R.id.adapter_bh_maBH);
            holder.shortNameNV = (TextView) view.findViewById(R.id.adapter_bh_tennv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        BH1D1 bh = list.get(position);
        holder.maBH.setText(bh.getMaBH());
        holder.shortNameNV.setText(bh.getTenNVToday());
        return view;
    }

    private class ViewHolder{
        TextView maBH, shortNameNV;
    }
}
