package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.Top5;
import com.example.windows10gamer.connsql.Other.Keys;
import com.example.windows10gamer.connsql.R;

import java.util.ArrayList;

/**
 * Created by EVRESTnhan on 1/19/2018.
 */

public class Adapter_Top5 extends BaseAdapter{
    Context context;
    ArrayList<Top5> list;

    public Adapter_Top5(Context context, ArrayList<Top5> list) {
        this.context = context;
        this.list = list;
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

    private class Viewholder{
        TextView ten;
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Viewholder vh;
        if (view == null){
            vh = new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.adapter_top5, null);
            vh.ten = view.findViewById(R.id.tvten);
            view.setTag(vh);
        } else {
            vh = (Viewholder) view.getTag();
        }
        Top5 top5 = list.get(i);
        vh.ten.setText(top5.getStt()+". "+ top5.getTen() );
        return view;
    }
}
