package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Doanhthu;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.io.Serializable;
import java.util.List;

/**
 * Created by EVRESTnhan on 1/3/2018.
 */

public class Adapter_Doanhthu extends BaseAdapter implements Serializable {
    private Context context;
    private List<Doanhthu> list;
    private int layout;

    public Adapter_Doanhthu(Context context, int layout, List<Doanhthu> list) {
        this.context = context;
        this.list = list;
        this.layout = layout;
    }

    private class ViewHolder{
        TextView tvtime, tvchinhanh, tvtennv, tvdoanhthu;
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
            holder.tvtime = (TextView) view.findViewById(R.id.tvtime);
            holder.tvchinhanh = (TextView) view.findViewById(R.id.tvchinhanh);
            holder.tvtennv = (TextView) view.findViewById(R.id.tvtennv);
            holder.tvdoanhthu = (TextView) view.findViewById(R.id.tvdoanhthu);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.tvtime.setText(list.get(i).getCa()+" "+list.get(i).getNgay());
        holder.tvchinhanh.setText(list.get(i).getChinhanh());
        holder.tvdoanhthu.setText(Keys.setMoney(Integer.parseInt(list.get(i).getDoanhthu())));
        holder.tvtennv.setText(list.get(i).getTenNV());
        return view;
    }
}
