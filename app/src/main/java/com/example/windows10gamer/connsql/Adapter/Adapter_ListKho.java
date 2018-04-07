package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Kho_Dialog;
import com.example.windows10gamer.connsql.R;

import java.util.List;

/**
 * Created by EVRESTnhan on 4/2/2018.
 */

public class Adapter_ListKho extends BaseAdapter {
    Context context;
    int layout;
    List<Kho_Dialog> list;

    public Adapter_ListKho(Context context, int layout, List<Kho_Dialog> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    private class ViewHolder{
        TextView tvchinhanh, tvsoluong;
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
    public View getView(int i, View view, ViewGroup parent) {
        ViewHolder vh;
        if (view == null){
            vh = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout, null);
            vh.tvchinhanh = view.findViewById(R.id.tvchinhanh);
            vh.tvsoluong = view.findViewById(R.id.tvsoluong);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        final Kho_Dialog kh = list.get(i);
        vh.tvchinhanh.setText(""+kh.getChinhanh());
        vh.tvsoluong.setText(""+kh.getSoluong());
        return view;
    }


}