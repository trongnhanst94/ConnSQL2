package com.example.windows10gamer.connsql.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.windows10gamer.connsql.Object.KhachHang;
import com.example.windows10gamer.connsql.R;

import java.util.List;

/**
 * Created by EVRESTnhan on 3/7/2018.
 */

public class Adapter_ListCustomer extends BaseAdapter {
    Context context;
    int layout;
    List<KhachHang> list;

    public Adapter_ListCustomer(Context context, int layout, List<KhachHang> list) {
        this.context = context;
        this.layout = layout;
        this.list = list;
    }

    private class ViewHolder{
        TextView name, phone;
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
            vh.name = view.findViewById(R.id.tvname);
            vh.phone = view.findViewById(R.id.tvphone);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) view.getTag();
        }
        final KhachHang kh = list.get(i);
        vh.name.setText("Tên khách hàng: "+kh.getName());
        vh.phone.setText("Điện thoại: "+kh.getPhone());
        return view;
    }
}
