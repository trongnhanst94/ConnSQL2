package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.BHCXL;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.util.List;

/**
 * Created by EVRESTnhan on 12/10/2017.
 */

public class Adapter_Report_BHCXL extends BaseAdapter {
    private Context context;
    private List<BHCXL> list;
    private int layout;

    public Adapter_Report_BHCXL(Context context, int layout, List<BHCXL> list) {
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

        BHCXL bh = list.get(position);
        holder.maBH.setText(Keys.trimText(bh.getTen(), 30));
        holder.shortNameNV.setText(bh.getTenKH());
        return view;
    }

    private class ViewHolder{
        TextView maBH, shortNameNV;
    }
}
