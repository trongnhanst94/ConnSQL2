package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.BHDLK;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.util.List;

/**
 * Created by EVRESTnhan on 11/7/2017.
 */

public class Adapter_Report_BHDLK extends BaseAdapter{
    private Context context;
    private List<BHDLK> list;
    private int layout;

    public Adapter_Report_BHDLK(Context context, int layout, List<BHDLK> list) {
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            holder.maBH        = view.findViewById(R.id.adapter_bh_maBH);
            holder.shortNameNV = view.findViewById(R.id.adapter_bh_tennv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        BHDLK bh = list.get(position);
        holder.maBH.setText(Keys.trimText(bh.getTen(), 30));
        holder.shortNameNV.setText(bh.getTenKH());
        return view;
    }

    private class ViewHolder{
        TextView maBH, shortNameNV;
    }
}
