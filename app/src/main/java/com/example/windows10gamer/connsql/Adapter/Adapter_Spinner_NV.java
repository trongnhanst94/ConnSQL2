package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.R;

import java.util.ArrayList;

/**
 * Created by EVRESTnhan on 12/5/2017.
 */

public class Adapter_Spinner_NV extends BaseAdapter {
    Context context;
    ArrayList<String> listma = new ArrayList<>();
    ArrayList<String> listten = new ArrayList<>();
    LayoutInflater inflter;

    public Adapter_Spinner_NV(Context applicationContext, ArrayList<String> listma, ArrayList<String> listten) {
        this.context = applicationContext;
        this.listma = listma;
        this.listten = listten;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return listma.size();
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
        view = inflter.inflate(R.layout.spinner_nv, null);
        TextView ma = (TextView) view.findViewById(R.id.spma);
        TextView ten = (TextView) view.findViewById(R.id.spten);
        ma.setText("Mã số: "+listma.get(i));
        ten.setText("Tên nhân viên: "+listten.get(i));
        return view;
    }
}